package com.project.service.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.common.base.BaseResult;
import com.project.common.base.BaseService;
import com.project.common.util.StringUtil;
import com.project.service.proxy.PlatformProxy;

import net.sf.json.JSONObject;

@Service("platformService")
public class PlatformService extends BaseService {

	@Autowired
	private PlatformProxy platformProxy;

	public BaseResult login(JSONObject params) {
		String openId;
		try {
			openId = params.getString("openId");
			if (StringUtil.isEmpty(openId)) {
				return errorParamsResult();
			}
		} catch (Exception e) {
			e.printStackTrace();
			return errorParamsResult();
		}
		try {

			Map<String, Object> map = new HashMap<String, Object>();
			String token = generateToken(1);
			redisUtil.set(token, map);
			map.put("token", token);
			return successResult("登录成功!", map);
		} catch (Exception e) {
			e.printStackTrace();
			return errorExceptionResult();
		}
	}

	public BaseResult loginOut(String token) {
		try {
			redisUtil.decr(token);
			return successResult("登出成功!");
		} catch (Exception e) {
			e.printStackTrace();
			return errorExceptionResult();
		}
	}
}
