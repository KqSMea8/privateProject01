package com.project.common.page;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.sql.Statement;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.executor.resultset.FastResultSetHandler;
import org.apache.ibatis.executor.resultset.ResultSetHandler;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;

@Intercepts(@Signature(method = "handleResultSets", type = ResultSetHandler.class, args = { Statement.class }))
public class PaginationForPageContentInterceptor implements Interceptor {

	@SuppressWarnings("unchecked")
	@Override
	public Object intercept(Invocation invocation) throws Throwable {
		Object target = invocation.getTarget();
		if (!(target instanceof FastResultSetHandler)) {
			return invocation.proceed();
		}
		FastResultSetHandler resultSetHandler = (FastResultSetHandler) target;
		// 利用反射获取到FastResultSetHandler的ParameterHandler属性，从而获取到ParameterObject；
		ParameterHandler parameterHandler = (ParameterHandler) getFieldValue(resultSetHandler, "parameterHandler", null);
		Object parameterObj = parameterHandler.getParameterObject();
		// 判断是否包含分页参数
		Page page = null;
		if (parameterObj instanceof Map) {
			Map<String, Object> m = (Map<String, Object>) parameterObj;
			for (Entry<String, Object> en : m.entrySet()) {
				if (en.getValue() instanceof Page) {
					page = (Page) en.getValue();
					break;
				}
			}
		} else if (parameterObj instanceof Page)
			page = (Page) parameterObj;
		if (page == null)
			return invocation.proceed();
		// 进行结果集处理、封装
		Object result = invocation.proceed();
		if (result == null)
			return result;
		page.setPageContent((List<Object>) result);
		return result;
	}

	public static Object getFieldValue(Object target, String fname, Class<?> ftype) {
		if (target == null || fname == null || "".equals(fname))
			return null;
		Class<?> clazz = target.getClass();
		try {
			Method method = clazz.getDeclaredMethod("get" + Character.toUpperCase(fname.charAt(0)) + fname.substring(1), ftype);
			if (!Modifier.isPublic(method.getModifiers())) {
				method.setAccessible(true);
			}
			return method.invoke(target);
		} catch (Exception me) {
			try {
				Field field = clazz.getDeclaredField(fname);
				if (!Modifier.isPublic(field.getModifiers())) {
					field.setAccessible(true);
				}
				return field.get(target);
			} catch (Exception fe) {
				fe.getStackTrace();
			}
		}
		return null;
	}

	@Override
	public Object plugin(Object obj) {
		return Plugin.wrap(obj, this);
	}

	@Override
	public void setProperties(Properties props) {
	}

}
