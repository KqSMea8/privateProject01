package com.project.common.util;

import java.io.File;
import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

import com.project.common.constants.UploadConstant;

public class ImageUpload {
	/*
	 * springmvc 上传图片,返回平台可访问的http图片url
	 */
	public static String uploadByMultipartFile(MultipartFile source) throws IllegalStateException, IOException {
		String today = DateUtil.getCurrentTime("yyyyMMdd");
		String diskFilePath = UploadConstant.BASE_DISK + "/" + UploadConstant.BASE_FOLDER + "/" + today + "/";
		String httpFilePath = UploadConstant.BASE_HTTP + "/" + UploadConstant.BASE_FOLDER + "/" + today + "/";
		int random = (int) (Math.random() * 10000);
		String fileName = source.getOriginalFilename();
		fileName = System.currentTimeMillis() + "_" + random + fileName.substring(fileName.lastIndexOf("."));
		File file = new File(diskFilePath);
		if (!file.exists())
			file.mkdirs();

		source.transferTo(new File(diskFilePath + fileName));

		return httpFilePath + fileName;
	}
}
