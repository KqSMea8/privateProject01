package com.project.service.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.common.base.BaseResult;
import com.project.common.base.BaseService;
import com.project.common.page.PageForApp;
import com.project.service.entity.User;
import com.project.service.proxy.InformationProxy;

import net.sf.json.JSONObject;

@Service("informationService")
public class InformationService extends BaseService {

	@Autowired
	private InformationProxy informationProxy;

	public BaseResult typeList() {
		try {
			return successResult("获取成功", informationProxy.typeList());
		} catch (Exception e) {
			e.printStackTrace();
			return errorExceptionResult();
		}
	}

	public BaseResult listOfType(JSONObject params) {
		Integer typeId;
		PageForApp page = null;
		try {
			page = getPageEntity(params);
			typeId = params.getInt("typeId");
		} catch (Exception e) {
			e.printStackTrace();
			return errorParamsResult();
		}
		try {
			return successResult("获取成功", informationProxy.listOfType(page, typeId));
		} catch (Exception e) {
			e.printStackTrace();
			return errorExceptionResult();
		}
	}

	public BaseResult info(JSONObject params) {
		Integer informationId;
		try {
			informationId = params.getInt("informationId");
		} catch (Exception e) {
			e.printStackTrace();
			return errorParamsResult();
		}
		try {
			Map<String, Object> result = informationProxy.info(informationId);

			try {
				informationProxy.addBrowe(informationId);
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("增加浏览量出错!");
			}

			return successResult("获取成功", result);
		} catch (Exception e) {
			e.printStackTrace();
			return errorExceptionResult();
		}
	}

	public BaseResult commentlist(JSONObject params) {
		Integer informationId;
		PageForApp page = null;
		try {
			page = getPageEntity(params);
			informationId = params.getInt("informationId");
		} catch (Exception e) {
			e.printStackTrace();
			return errorParamsResult();
		}
		try {
			Map<String, Object> result = new HashMap<String, Object>();
			informationProxy.commentlist(page, informationId);
			
			result.put("commentCount", page.getTotal());
			result.put("commentList", page.getPageContent());
			return successResult("获取成功", result);
		} catch (Exception e) {
			e.printStackTrace();
			return errorExceptionResult();
		}
	}

	public BaseResult comment(User user, JSONObject params) {
		Integer informationId;
		String commentContent;
		try {
			commentContent = params.getString("commentContent");
			informationId = params.getInt("informationId");
		} catch (Exception e) {
			e.printStackTrace();
			return errorParamsResult();
		}
		try {
			informationProxy.comment(commentContent, informationId,user);
			return successResult("评论成功");
		} catch (Exception e) {
			e.printStackTrace();
			return errorExceptionResult();
		}
	}
}
