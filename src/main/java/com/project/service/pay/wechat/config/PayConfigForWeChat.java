package com.project.service.pay.wechat.config;

import java.io.InputStream;

import com.project.service.pay.wechat.IWXPayDomain;
import com.project.service.pay.wechat.WXPayConfig;

public class PayConfigForWeChat extends WXPayConfig {

	public String getAppID() {
		return "wx8888888888888888";
	}

	public String getMchID() {
		return "12888888";
	}

	public String getKey() {
		return "88888888888888888888888888888888";
	}

	public int getHttpConnectTimeoutMs() {
		return 8000;
	}

	public int getHttpReadTimeoutMs() {
		return 10000;
	}

	@Override
	public IWXPayDomain getWXPayDomain() {
		PayDomain payDomain = new PayDomain();
		return payDomain;
	}

	@Override
	public InputStream getCertStream() {
		return null;
	}
}
