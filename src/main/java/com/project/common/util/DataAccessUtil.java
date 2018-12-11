package com.project.common.util;

import java.math.BigDecimal;
import java.text.NumberFormat;


public class DataAccessUtil {
	// 默认除法运算精度
	private static final int DEF_DIV_SCALE = 2;
	
	/**
	 * 数字字符串转人民币金额 精度为"分"
	 * @param numStr
	 * @return
	 */
	public static String toRMBString(String numStr){
		if(numStr==""||numStr==null||numStr=="null"){
			return "0.00";
		}
		NumberFormat numberformat = NumberFormat.getInstance();
        numberformat.setMinimumFractionDigits(2);
        numberformat.setMaximumFractionDigits(2);
        return numberformat.format(Double.parseDouble(numStr));
	}
	
	/**
	 * 比较两个数据大小
	 * @param v1
	 * @param v2
	 * @return
	 */
	public static int compareTo(String v1, String v2) {
		BigDecimal b1 = new BigDecimal(v1);
		BigDecimal b2 = new BigDecimal(v2);
		return b1.compareTo(b2);
	}
	/**
	 * 提供精确的加法运算
	 * @param v1 被加数
	 * @param v2 加数
	 * @return 两个参数的和
	 */
	public static String add(String v1, String v2) {
		if("".equals(v1) || v1 == null)
			v1 = "0";
		if("".equals(v2) || v2 == null)
			v2 = "0";
		BigDecimal b1 = new BigDecimal(v1);
		BigDecimal b2 = new BigDecimal(v2);
		return b1.add(b2).toString();
	}

	/**
	 * 减法运算
	 * @param v1 被减数
	 * @param v2 减数
	 * @return 两个参数的差
	 */
	public static String sub(String v1, String v2) {
		BigDecimal b1 = new BigDecimal(v1);
		BigDecimal b2 = new BigDecimal(v2);
		return b1.subtract(b2).toString();
	}

	/**
	 * 乘法运算
	 * @param v1 被乘数
	 * @param v2 乘数
	 * @return 两个参数的积
	 */
	public static String mul(String v1, String v2) {
		BigDecimal b1 = new BigDecimal(v1);
		BigDecimal b2 = new BigDecimal(v2);
		return b1.multiply(b2).toString();
	}

	/**
	 * 除法运算，精确到 小数点以后2位，以后的数字四舍五入
	 * @param v1 被除数
	 * @param v2 除数
	 * @return 两个参数的商
	 */
	public static String div(String v1, String v2) {
		return div(v1, v2, DEF_DIV_SCALE);
	}

	/**
	 * 除法运算，scale参数指 定精度，以后的数字四舍五入
	 * @param v1 被除数
	 * @param v2 除数
	 * @param scale 需要精确到小数点以后几位
	 * @return 两个参数的商
	 */
	public static String div(String v1, String v2, int scale) {
		if (scale < 0) {
			throw new IllegalArgumentException("精确的小数点位数必须大于等于0,当前值为："+scale);
		}
		BigDecimal b1 = new BigDecimal(v1);
		BigDecimal b2 = new BigDecimal(v2);
		return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).toString();
	}

}
