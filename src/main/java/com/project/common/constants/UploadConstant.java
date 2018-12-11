package com.project.common.constants;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URLDecoder;

import com.project.common.util.ProFileReader;

/**
 * 文件上传配置文件
 * 
 * @author sgsp
 *
 */
public class UploadConstant {
	// http路径
	public static String BASE_HTTP = "";
	// 本地图片路径
	public static String BASE_DISK = "";
	// upload
	public static String BASE_FOLDER = "";
	// web http url
	public static String WEB_HTTP_URL = "";
	/**
	 * credit-mamager系统访问根路径
	 */
	public static String Manager_HTTP_URL = "";
	static {
		File file = new File(UploadConstant.getPath() + "resource/upload.properties");
		ProFileReader proFile = null;
		try {
			proFile = new ProFileReader(new FileInputStream(file));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		BASE_HTTP = proFile.getParamValue("BASE_HTTP");
		BASE_DISK = proFile.getParamValue("BASE_DISK");
		BASE_FOLDER = proFile.getParamValue("BASE_FOLDER");
		WEB_HTTP_URL = proFile.getParamValue("BASE_WEB_HTTP");
		Manager_HTTP_URL = proFile.getParamValue("Manager_HTTP_URL");
	}

	public static String getPath() {
		String path = null;
		try {
			path = UploadConstant.class.getClassLoader().getResource("").getPath().toString();
			String systemName = System.getProperty("os.name");
			if (systemName.startsWith("Windows")) {
				path = URLDecoder.decode(path.substring(1), "utf-8");
			} else {
				path = URLDecoder.decode(path, "utf-8");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return path;
	}
}
