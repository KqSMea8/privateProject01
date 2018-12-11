package com.project.common.util;

import org.springframework.security.authentication.encoding.Md5PasswordEncoder;

/**
 * 加密工具
 * 
 * @author zhoulu
 * 
 */
public class EncryptUtil {
	private static final String SALT = "ruiyun";
	private static final Md5PasswordEncoder md5 = new Md5PasswordEncoder();

	/**
	 * MD5加密
	 * 
	 * @param rawPass 原始文本
	 * @return 加密文本
	 */
	public static String MD5EncodePassword(String rawPass) {
		return md5.encodePassword(rawPass, SALT);
	}

	/**
	 * MD5密码验证
	 * 
	 * @param encPass 加密文本
	 * @param rawPass 原始文本
	 * @return boolean
	 */
	public static boolean MD5IsPasswordValid(String encPass, String rawPass) {
		return md5.isPasswordValid(encPass, rawPass, SALT);
	}
}
