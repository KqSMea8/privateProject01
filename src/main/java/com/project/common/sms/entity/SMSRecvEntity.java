package com.project.common.sms.entity;

import java.io.Serializable;

/**
 * 腾讯云短信返回实体
 */
public class SMSRecvEntity implements Serializable {
	private static final long serialVersionUID = -8110381874151657370L;

	private String result; // 0表示成功(计费依据)，非0表示失败
	private String errmsg; // result非0时的具体错误信息
	private String ext; // 用户的session内容，腾讯server回包中会原样返回
	private String sid; // 标识本次发送id
	private String fee; // 短信计费的条数

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getErrmsg() {
		return errmsg;
	}

	public void setErrmsg(String errmsg) {
		this.errmsg = errmsg;
	}

	public String getExt() {
		return ext;
	}

	public void setExt(String ext) {
		this.ext = ext;
	}

	public String getSid() {
		return sid;
	}

	public void setSid(String sid) {
		this.sid = sid;
	}

	public String getFee() {
		return fee;
	}

	public void setFee(String fee) {
		this.fee = fee;
	}
}
