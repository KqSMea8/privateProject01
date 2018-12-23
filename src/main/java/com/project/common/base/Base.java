package com.project.common.base;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Random;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.gson.Gson;
import com.project.common.redis.RedisUtil;
import com.project.common.util.ClassPathUtil;
import com.project.common.util.JsonUtil;
import com.project.common.util.MD5Utils;
import com.project.common.util.ProFileReader;
import com.project.service.entity.User;

import net.sf.json.JSONObject;

/**
 * 基类
 */
public class Base {

	private static final String YAN = "GOGO$789787mnbqwe@//*-+'[]MMM;384785*^*&%^%$%";// 加盐
	private static Integer SUCCESS = 200;
	private static Integer ERROR_PARAM = 400;
	private static Integer ERROR = 500;
	public static String PARAM_NAME_PAGE_INDEX = "pageIndex";
	public static String PARAM_NAME_PAGE_ROWS = "pageRows";

	@Autowired
	public RedisUtil redisUtil;

	protected BaseResult generateResult(boolean isSuccess, String msg) {
		return generateResult(isSuccess, msg, null);
	}

	protected BaseResult generateResult(boolean isSuccess, String msg, Object result) {
		BaseResult obj = new BaseResult();
		obj.setMsg(msg);
		obj.setIsSuccess(isSuccess);
		obj.setResult(result);
		return obj;
	}

	protected BaseResult generateResult(Integer code, String msg) {
		return generateResult(code, msg, null);
	}

	protected BaseResult successResult() {
		return successResult(null, null);
	}

	protected BaseResult successResult(String msg) {
		return successResult(msg, null);
	}

	protected BaseResult successResult(String msg, Object result) {
		return generateResult(SUCCESS, msg, result);
	}

	protected BaseResult errorResult() {
		return errorResult(null, null);
	}

	protected BaseResult errorResult(String msg) {
		return errorResult(msg, null);
	}

	protected BaseResult errorResult(String msg, Object result) {
		return generateResult(ERROR, msg, result);
	}

	protected BaseResult errorExceptionResult() {
		return errorResult("抱歉，我的程序出错了，快去吐槽管理员!", null);
	}

	protected BaseResult errorParamsResult() {
		return generateResult(ERROR_PARAM, "传递参数必填参数有空值，或者类型不正确!", null);
	}

	protected BaseResult errorParamsResult(String resultMsg) {
		return generateResult(ERROR_PARAM, resultMsg, null);
	}

	/**
	 * 生成token
	 * 
	 * @param userName
	 * @param userId
	 * @return String
	 * @author chentianjin
	 * @date 2017年4月25日
	 */
	protected String generateToken(String openId) {
		return MD5Utils.Md532(System.currentTimeMillis() + YAN + openId);
	}

	protected BaseResult generateResult(Integer code, String msg, Object result) {
		boolean isEncryption = false;

		try {
			BaseResult br = new BaseResult();
			br.setCode(code);
			br.setMsg(msg);

			if (isEncryption && null != result) {
				br.setResult(JsonUtil.objectToJsonStr(result));
			} else {
				br.setResult(result);
			}

			return br;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 获得4位数的验证码
	 * 
	 * @return String
	 * @author chentianjin
	 * @date 2017年5月12日
	 */
	public String makeVcode() {
		Random random = new Random();
		String encode = "0123456789";
		String sRand = "";
		for (int i = 0; i < 4; i++) {
			String rand = encode.charAt(random.nextInt(10)) + "";
			sRand += rand;
		}
		return sRand;
	}

	public String getPartPath(HttpServletRequest request) {
		String path = request.getContextPath();
		String partPath = request.getServerPort() == 80 ? "" : ":" + request.getServerPort();
		return request.getScheme() + "://" + request.getServerName() + partPath + path + "/";
	}

	/**
	 * 生成指纹信息
	 * 
	 * @return
	 */
	protected String generateFingerprint() {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}

	/**
	 * 将传入的参数解析成JSONObject
	 */
	protected JSONObject getJSONObjectParams(String params) {
		if (StringUtils.isEmpty(params))
			return new JSONObject();
		JSONObject obj = JSONObject.fromObject(params);
		if (obj == null || obj.isNullObject()) {
			obj = new JSONObject();
		}
		return obj;
	}

	/**
	 * 根据token获取当前用户
	 */
	protected User getUserByToken(String token) {
		String operatorStr = redisUtil.get(token);
		if (StringUtils.isEmpty(operatorStr)) {
			return null;
		}
		return new Gson().fromJson(operatorStr, User.class);
	}

	public String getSystemParamsByName(String Name) {
		File file = new File(ClassPathUtil.getPath() + "resource/system.properties");
		ProFileReader uploadPropFile;
		try {
			uploadPropFile = new ProFileReader(new FileInputStream(file));
			return uploadPropFile.getParamValue("IS_ENCRYPTION");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return "";
		}
	}
}
