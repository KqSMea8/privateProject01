package com.project.common.sms.enums;

/**
 * 短信模板
 */
public enum SMSTempletEnum {

	公共验证码("欢迎关注互助网，您的服务验证码为%s，悦家期待更好地为您服务。");

	private String tencentTemplet;

	/**
	 * 模板实体
	 * 
	 * @param tencentTemplet 腾讯短信模板
	 * @param aliTemplet     阿里短信模板
	 */
	SMSTempletEnum(String tencentTemplet) {
		this.tencentTemplet = tencentTemplet;
	}

	public String getTencentTemplet() {
		return tencentTemplet;
	}

}