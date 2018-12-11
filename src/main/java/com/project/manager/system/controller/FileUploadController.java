package com.project.manager.system.controller;

import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.project.common.base.BaseController;
import com.project.common.base.BaseResult;
import com.project.common.util.ImageUpload;

/**
 * 文件上传
 * 
 * @author Jiangbo
 *
 */
@Controller
@RequestMapping("/common/upload")
public class FileUploadController extends BaseController {

	@RequestMapping("uploadImg")
	@ResponseBody
	public BaseResult uploadImg(MultipartHttpServletRequest mreq) throws Exception {
		MultiValueMap<String, MultipartFile> fileMap = mreq.getMultiFileMap();
		try {
			Iterator<Entry<String, List<MultipartFile>>> it = fileMap.entrySet().iterator();
			if (it.hasNext()) {
				List<MultipartFile> fileList = it.next().getValue();
				return generateResult(true, "上传图片成功", ImageUpload.uploadByMultipartFile(fileList.get(0)));
			}
			return generateResult(false, "文件为空!");
		} catch (Exception e) {
			e.printStackTrace();
			return generateResult(false, "上传图片失败");
		}
	}
}
