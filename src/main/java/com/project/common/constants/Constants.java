package com.project.common.constants;

public class Constants {

	public static String SYSTEM_USER = "operator";
	public static String MENU_BTN_POWER = "menuBtnPower";
	public static String SUPER_PWD = "Soccer@2018";
	public static String RESET_PWD = "123456";
	
	public static boolean isEmpty(Object obj) {
		if (obj == null)
			return true;
		if (obj instanceof CharSequence) {
			return ((String) obj).trim().isEmpty();
		}
		return false;
	}
}
