package com.project.common.sms.entity;

import java.io.Serializable;

import com.project.common.util.MD5Utils;


/**
 * 腾讯云短信发送实体
 */
public class SMSSendEntity implements Serializable {
	private static final long serialVersionUID = -5619261971685075199L;

	private TelInfo tel;
	private String type = "0"; // 0:普通短信;1:营销短信（强调：要按需填值，不然会影响到业务的正常使用）
	private String msg;
	private String sig;
	private String extend = ""; // 可选字段，默认没有开通(需要填空)。通道扩展码， 在短信回复场景中，腾讯server会原样返回，开发者可依此区分是哪种类型的回复
	private String ext = ""; // 可选字段，不需要就填空。用户的session内容，腾讯server回包中会原样返回

	public TelInfo getTel() {
		return tel;
	}

	public void setTel(TelInfo tel) {
		this.tel = tel;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getSig() {
		return sig;
	}

	public void setSig(String sig) {
		this.sig = sig;
	}

	public String getExtend() {
		return extend;
	}

	public void setExtend(String extend) {
		this.extend = extend;
	}

	public String getExt() {
		return ext;
	}

	public void setExt(String ext) {
		this.ext = ext;
	}

	public static SMSSendEntity getInstance(String tel, String msg, String extend, String appKey) {
		SMSSendEntity se = new SMSSendEntity();
		
		TelInfo telInfo = new TelInfo();
		telInfo.setPhone(tel);
		
		se.setTel(telInfo);
		se.setMsg(msg);
		se.setSig(MD5Utils.Md532(appKey + tel));
		se.setExtend(extend);
		
		return se;
	}
}

class TelInfo implements Serializable {
	private static final long serialVersionUID = -6672644058098294357L;

	private String nationcode = "86"; // 国家码，默认中国
	private String phone; // 手机号码

	public String getNationcode() {
		return nationcode;
	}

	public void setNationcode(String nationcode) {
		this.nationcode = nationcode;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
}
