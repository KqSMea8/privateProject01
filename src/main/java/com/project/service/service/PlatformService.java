package com.project.service.service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
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

	@Autowired
	private DataSourceTransactionManager transactionManager;

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

	public BaseResult screeTeamForApply(User user) {
		try {
			return successResult("获取成功", platformProxy.screeTeamForApply(user.getUserId()));
		} catch (Exception e) {
			e.printStackTrace();
			return errorExceptionResult();
		}
	}

	public BaseResult screeTeam(JSONObject params) {
		Integer matchInfoId;
		try {
			matchInfoId = params.getInt("matchInfoId");
		} catch (Exception e) {
			e.printStackTrace();
			return errorParamsResult();
		}

		try {
			return successResult("获取成功", platformProxy.screeTeam(matchInfoId));
		} catch (Exception e) {
			e.printStackTrace();
			return errorExceptionResult();
		}
	}

	public BaseResult screeMember(JSONObject params) {
		Integer teamId, matchInfoId;
		try {
			teamId = params.getInt("teamId");
			matchInfoId = params.getInt("matchInfoId");
		} catch (Exception e) {
			e.printStackTrace();
			return errorParamsResult();
		}

		try {
			return successResult("获取成功", platformProxy.screeMember(teamId, matchInfoId));
		} catch (Exception e) {
			e.printStackTrace();
			return errorExceptionResult();
		}
	}

	public BaseResult sgin(User user, JSONObject params) {
		TransactionStatus status = transactionManager.getTransaction(new DefaultTransactionDefinition());
		try {
			BigDecimal rechargeTotal = new BigDecimal("0.5");
			boolean result = platformProxy.userRecharge(rechargeTotal, user.getUserId(), 2).intValue() == 1;
			result = result && platformProxy.diamondsRecharge(rechargeTotal, user.getUserId()).intValue() == 1;

			// 连续签到满30天多给10钻石
			if (platformProxy.totalContinuousSign(user.getUserId())) {
				BigDecimal rechargeTotalTotal = new BigDecimal("10");
				result = result && platformProxy.userRecharge(rechargeTotalTotal, user.getUserId(), 6).intValue() == 1;
				result = result && platformProxy.diamondsRecharge(rechargeTotalTotal, user.getUserId()).intValue() == 1;
			}

			if (result) {
				transactionManager.commit(status);
				return successResult("签到成功", result);
			} else {
				transactionManager.rollback(status);
				return errorResult("签到失败!");
			}
		} catch (Exception e) {
			e.printStackTrace();
			transactionManager.rollback(status);
			return errorExceptionResult();
		}
	}

	public BaseResult shareOk(User user, JSONObject params) {
		TransactionStatus status = transactionManager.getTransaction(new DefaultTransactionDefinition());
		try {
			BigDecimal rechargeTotal = new BigDecimal("2");
			boolean result = platformProxy.userRecharge(rechargeTotal, user.getUserId(), 4).intValue() == 1;
			result = result && platformProxy.diamondsRecharge(rechargeTotal, user.getUserId()).intValue() == 1;

			if (result) {
				transactionManager.commit(status);
				return successResult("分享加钻成功", result);
			} else {
				transactionManager.rollback(status);
				return errorResult("分享加钻失败!");
			}
		} catch (Exception e) {
			e.printStackTrace();
			transactionManager.rollback(status);
			return errorExceptionResult();
		}
	}
}
