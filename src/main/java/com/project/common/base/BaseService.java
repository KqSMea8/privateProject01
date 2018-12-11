package com.project.common.base;

import java.util.List;

import com.project.common.jqgrid.JQGridPageParams;
import com.project.common.jqgrid.JQGridResultEntity;
import com.project.common.util.DateUtil;

/**
 * Service基类
 */
public class BaseService extends Base {

	/**
	 * 计算总页数
	 * 
	 * @param totalRows
	 *            总行数
	 * @param pageRows
	 *            单页行数
	 * @return
	 */
	protected long calcTotalPage(Long totalRows, Long pageRows) {
		return totalRows / pageRows + (totalRows % pageRows > 0 ? 1 : 0);
	}

	/**
	 * 填充数据
	 * 
	 * @param list
	 * @param totalRecords
	 * @param pageParams
	 * @return
	 */
	protected <T> JQGridResultEntity<T> fillJQGrid(List<T> list, Long totalRecords, JQGridPageParams pageParams) {
		return fillJQGrid(null, list, totalRecords, pageParams);
	}

	/**
	 * 填充数据
	 * 
	 * @param result
	 * @param list
	 * @param totalRecords
	 * @param page
	 * @param rows
	 * @return
	 */
	protected <T> JQGridResultEntity<T> fillJQGrid(JQGridResultEntity<T> result, List<T> list, Long totalRecords, JQGridPageParams pageParams) {
		if (null == result) {
			result = new JQGridResultEntity<T>();
		}

		if (pageParams != null) {
			result.setCurrentPage(pageParams.getPage());
			result.setTotalPages(calcTotalPage(totalRecords, pageParams.getRows()));
		} else {
			result.setCurrentPage(1);
			result.setTotalPages(0);
		}

		result.setCurrentPage(pageParams.getPage());
		result.setRows(list);
		result.setTotalRecords(totalRecords);

		return result;
	}

	/**
	 * 生产Id
	 * 
	 * @param tableName
	 *            需要查询的表名
	 * @param fieldName
	 *            需要查询的字段名
	 * @return Integer
	 * @date 2017年3月15日
	 */
	protected Integer generateId() {
		String yyMMdd = DateUtil.getCurrentTime("mmssSSS");
		return Integer.parseInt(yyMMdd);
	}

	/**
	 * 左补位，右对齐
	 * 
	 * @param oriStr
	 *            原字符串
	 * @param len
	 *            目标字符串长度
	 * @param alexin
	 *            补位字符
	 * @return 目标字符串
	 */
	@SuppressWarnings("unused")
	private String padLeft(String oriStr, int len, char alexin) {
		int strlen = oriStr.length();
		String str = "";
		if (strlen < len) {
			for (int i = 0; i < len - strlen; i++) {
				str = str + alexin;
			}
		}
		str += oriStr;
		return str;
	}
}
