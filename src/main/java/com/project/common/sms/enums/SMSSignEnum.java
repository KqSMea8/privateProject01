package com.project.common.sms.enums;

/**
 * 腾讯云短信服务，短信签名
 */
public enum SMSSignEnum {
	互助网("【互助网】");

	private String signName;

	SMSSignEnum(String signName) {
		this.signName = signName;
	}

	public String getSignName() {
		return signName;
	}

	public void setSignName(String signName) {
		this.signName = signName;
	}
}
