package com.project.common.base;

import java.util.UUID;

/**
 * 基类
 */
public class Base {

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

	/**
	 * 生成指纹信息
	 * 
	 * @return
	 */
	protected String generateFingerprint() {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}

}
