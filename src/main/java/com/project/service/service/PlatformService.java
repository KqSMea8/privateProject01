package com.project.service.service;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.project.common.base.BaseResult;
import com.project.common.base.BaseService;
import com.project.common.util.ImageUpload;
import com.project.common.util.StringUtil;
import com.project.service.entity.User;
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
			User user = platformProxy.getUserByOpenId(openId);

			if (user.getUserId() == null) {
				platformProxy.createUserIdByOpenId(user);
				platformProxy.updateUserWxUserId(user);
				user = platformProxy.getUserByOpenId(openId);
			}

			Map<String, Object> result = new HashMap<String, Object>();
			String token = generateToken(user.getOpenId());

			redisUtil.set(token, user);

			result.put("token", token);
			return successResult("登录成功!", result);
		} catch (Exception e) {
			e.printStackTrace();
			return errorExceptionResult();
		}
	}

	public BaseResult loginOut(String token) {
		try {
			redisUtil.delete(token);
			return successResult("登出成功!");
		} catch (Exception e) {
			e.printStackTrace();
			return errorExceptionResult();
		}
	}

	public BaseResult getSmsCode(JSONObject params) {
		String mobile;
		String validateType;
		try {
			mobile = params.getString("mobile");
			validateType = params.getString("validateType");
			if (StringUtil.isEmpty(mobile) || StringUtil.isEmpty(validateType))
				return errorParamsResult();
		} catch (Exception e) {
			e.printStackTrace();
			return errorParamsResult();
		}

		try {

			Pattern pattern = Pattern.compile("[0-9]*");
			Matcher isNum = pattern.matcher(mobile);
			if (!isNum.matches())
				return errorResult("传入手机号格式不正确");

			String vcode = redisUtil.get(validateType + mobile);
			if (StringUtil.isEmpty(vcode)) {
				vcode = makeVcode();
			} else {
				return errorResult("已发送验证码未过期，无需重新获取");
			}

			if (!sendVcode(mobile, vcode)) {
				return errorResult("发送验证码失败");
			}

			redisUtil.setex(validateType + mobile, 60, vcode);
			return successResult("发送成功");
		} catch (Exception e) {
			e.printStackTrace();
			return errorExceptionResult();
		}
	}

	public BaseResult uploadImage(MultipartFile file, HttpServletRequest request) {
		try {
			String path = ImageUpload.uploadByMultipartFile(file);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("fileUrl", path);
			map.put("httpUrl", getPartPath(request) + path);
			return successResult("上传成功", map);
		} catch (Exception e) {
			e.printStackTrace();
			return errorExceptionResult();
		}
	}

	public BaseResult cityList(JSONObject params) {
		try {
			Map<String, Object> result = new HashMap<String, Object>();
			result.put("hotCityList", platformProxy.getCityList(1));
			result.put("cityList", platformProxy.getCityList(0));
			return successResult("登录成功!", result);
		} catch (Exception e) {
			e.printStackTrace();
			return errorExceptionResult();
		}
	}

	public BaseResult screeTeam(JSONObject params) {
		String matchInfoId;
		try {
			matchInfoId = params.getString("matchInfoId");
			if (matchInfoId == null)
				return errorParamsResult();
		} catch (Exception e) {
			e.printStackTrace();
			return errorParamsResult();
		}

		try {
			return successResult("发送成功", platformProxy.screeTeam(matchInfoId));
		} catch (Exception e) {
			e.printStackTrace();
			return errorExceptionResult();
		}
	}

	public BaseResult screeMember(JSONObject params) {
		String teamId;
		try {
			teamId = params.getString("matchInfoId");
			if (teamId == null)
				return errorParamsResult();
		} catch (Exception e) {
			e.printStackTrace();
			return errorParamsResult();
		}

		try {
			return successResult("发送成功", platformProxy.screeMember(teamId));
		} catch (Exception e) {
			e.printStackTrace();
			return errorExceptionResult();
		}
	}
}
