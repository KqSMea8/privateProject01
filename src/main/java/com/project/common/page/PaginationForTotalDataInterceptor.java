package com.project.common.page;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.factory.DefaultObjectFactory;
import org.apache.ibatis.reflection.factory.ObjectFactory;
import org.apache.ibatis.reflection.wrapper.DefaultObjectWrapperFactory;
import org.apache.ibatis.reflection.wrapper.ObjectWrapperFactory;
import org.apache.ibatis.scripting.defaults.DefaultParameterHandler;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.project.common.plugins.Dialect;
import com.project.common.plugins.MySqlDialect;
import com.project.common.plugins.OracleDialect;
import com.project.common.util.ReflectUtil;

@Intercepts({ @Signature(type = StatementHandler.class, method = "prepare", args = { Connection.class }) })
public class PaginationForTotalDataInterceptor implements Interceptor {

	static final Logger log = LoggerFactory.getLogger(PaginationForTotalDataInterceptor.class);

	private String dialect;

	private static final ObjectFactory DEFAULT_OBJECT_FACTORY = new DefaultObjectFactory();
	private static final ObjectWrapperFactory DEFAULT_OBJECT_WRAPPER_FACTORY = new DefaultObjectWrapperFactory();

	@Override
	public Object intercept(Invocation invocation) throws Throwable {
		StatementHandler statementHandler = (StatementHandler) invocation.getTarget();
		MetaObject metaStatementHandler = MetaObject.forObject(statementHandler, DEFAULT_OBJECT_FACTORY, DEFAULT_OBJECT_WRAPPER_FACTORY);

		// 得到分页对象
		DefaultParameterHandler defaultParameterHandler = (DefaultParameterHandler) metaStatementHandler.getValue("delegate.parameterHandler");
		Object obj = defaultParameterHandler.getParameterObject();
		Page page = null;
		if (obj instanceof Map) {
			@SuppressWarnings("unchecked")
			Map<String, Object> parameterMap = (Map<String, Object>) obj;
			for (Entry<String, Object> en : parameterMap.entrySet()) {
				if (en.getValue() instanceof Page) {
					page = (Page) en.getValue();
					break;
				}
			}
		} else if (obj instanceof Page)
			page = (Page) obj;
		if (page == null) {
			return invocation.proceed();
		}

		// 方言
		Dialect dialect = null;
		if (this.dialect.equalsIgnoreCase("mysql"))
			dialect = new MySqlDialect();
		else if (this.dialect.equalsIgnoreCase("oracle"))
			dialect = new OracleDialect();
		if (dialect == null)
			throw new RuntimeException("数据库方言无法确定！");

		// 分页操作
		String sql = (String) metaStatementHandler.getValue("delegate.boundSql.sql");// sql语句
		// 总页数
		Connection connection = (Connection) invocation.getArgs()[0];
		MappedStatement mappedStatement = (MappedStatement) metaStatementHandler.getValue("delegate.mappedStatement");
		BoundSql boundSql = (BoundSql) metaStatementHandler.getValue("delegate.boundSql");
		setPageTotoalData(sql, connection, mappedStatement, boundSql, page);
		// 排序
		if (page.getOrder() != null && page.getSort() != null) {
			if (sql.toUpperCase().indexOf("ORDER") > 0) {
				sql = sql + " , " + page.getSort() + " " + page.getOrder();
			} else {
				sql = sql + " ORDER BY " + page.getSort() + " " + page.getOrder();
			}
		}
		int offset = (page.getPageIndex() - 1) * page.getPageSize();
		int limit = page.getPageSize();
		// int offset=0,limit=11;
		metaStatementHandler.setValue("delegate.boundSql.sql", dialect.getLimitString(sql, offset, limit));
		metaStatementHandler.setValue("delegate.rowBounds.offset", RowBounds.NO_ROW_OFFSET);
		metaStatementHandler.setValue("delegate.rowBounds.limit", RowBounds.NO_ROW_LIMIT);

		return invocation.proceed();
	}

	/**
	 * 获取总记录数
	 * 
	 * @param sql
	 * @param connection
	 * @param mappedStatement
	 * @param boundSql
	 * @param page
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws NoSuchFieldException
	 * @throws Exception
	 */
	private void setPageTotoalData(String sql, Connection connection, MappedStatement mappedStatement, BoundSql boundSql, Page page) throws Exception {
		if (page.getTotal() > 0)
			return;
		// 记录总记录数
		String countSql = "select count(0) from (" + sql + ") t";
		PreparedStatement countStmt = null;
		ResultSet rs = null;
		try {
			countStmt = connection.prepareStatement(countSql);
			BoundSql countBS = new BoundSql(mappedStatement.getConfiguration(), countSql, boundSql.getParameterMappings(), boundSql.getParameterObject());
			
			// 为了分页插件对foreach支持，集合参数利用反射机制重新设置
			Field metaParamsField = ReflectUtil.getFieldByFieldName(boundSql, "metaParameters");
			if (metaParamsField != null) {
				MetaObject mo = (MetaObject) ReflectUtil.getValueByFieldName(boundSql, "metaParameters");
				ReflectUtil.setValueByFieldName(countBS, "metaParameters", mo);
			}

			setParameters(countStmt, mappedStatement, countBS, boundSql.getParameterObject());
			rs = countStmt.executeQuery();
			int totalCount = 0;
			if (rs.next()) {
				totalCount = rs.getInt(1);
			}
			page.setTotal(totalCount);
		} catch (SQLException e) {
			log.error("Ignore this exception", e);
		} finally {
			try {
				rs.close();
			} catch (SQLException e) {
				log.error("Ignore this exception", e);
			}
			try {
				countStmt.close();
			} catch (SQLException e) {
				log.error("Ignore this exception", e);
			}
		}
	}

	public void setPageContentData(String sql, Connection connection, MappedStatement mappedStatement, BoundSql boundSql, Page page) {
		PreparedStatement countStmt = null;
		ResultSet rs = null;
		try {
			countStmt = connection.prepareStatement(sql);
			BoundSql countBS = new BoundSql(mappedStatement.getConfiguration(), sql, boundSql.getParameterMappings(), boundSql.getParameterObject());
			setParameters(countStmt, mappedStatement, countBS, boundSql.getParameterObject());
			rs = countStmt.executeQuery();
			int totalCount = 0;
			if (rs.next()) {
				totalCount = rs.getInt(1);
			}
			page.setTotal(totalCount);
		} catch (SQLException e) {
			log.error("Ignore this exception", e);
		} finally {
			try {
				rs.close();
			} catch (SQLException e) {
				log.error("Ignore this exception", e);
			}
			try {
				countStmt.close();
			} catch (SQLException e) {
				log.error("Ignore this exception", e);
			}
		}
	}

	/**
	 * 代入参数值
	 * 
	 * @param ps
	 * @param mappedStatement
	 * @param boundSql
	 * @param parameterObject
	 * @throws SQLException
	 */
	private void setParameters(PreparedStatement ps, MappedStatement mappedStatement, BoundSql boundSql, Object parameterObject) throws SQLException {
		ParameterHandler parameterHandler = new DefaultParameterHandler(mappedStatement, parameterObject, boundSql);
		parameterHandler.setParameters(ps);
	}

	public Object plugin(Object target) {
		return Plugin.wrap(target, this);
	}

	public void setProperties(Properties properties) {
		this.dialect = properties.getProperty("dialect");
	}
}
