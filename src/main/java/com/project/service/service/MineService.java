package com.project.service.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.common.base.BaseResult;
import com.project.common.base.BaseService;
import com.project.service.entity.User;
import com.project.service.proxy.MineProxy;

import net.sf.json.JSONObject;

@Service("mineService")
public class MineService extends BaseService {
	@Autowired
	private MineProxy mineProxy;

	public BaseResult center(User user) {
		try {
			return successResult("获取成功", mineProxy.getCenterInfo(user.getUserId()));
		} catch (Exception e) {
			e.printStackTrace();
			return errorExceptionResult();
		}
	}

	public BaseResult userInfo(User user, String paramsStr) {
		// TODO Auto-generated method stub
		return null;
	}

	public BaseResult userUpdateHeadImage(User user, JSONObject params) {
		// TODO Auto-generated method stub
		return null;
	}
}
