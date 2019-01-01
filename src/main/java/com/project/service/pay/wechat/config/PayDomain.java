package com.project.service.pay.wechat.config;

import com.project.service.pay.wechat.IWXPayDomain;
import com.project.service.pay.wechat.WXPayConfig;
import com.project.service.pay.wechat.WXPayConstants;

public class PayDomain implements IWXPayDomain {

	/**
	 * 域名访问后报告 <br>
	 * elapsedTimeMillis 请求所耗时间 <br>
	 * domain 域名
	 */
	@Override
	public void report(String domain, long elapsedTimeMillis, Exception ex) {
		// TODO Auto-generated method stub

	}

	/**
	 * 配置域名
	 */
	@Override
	public DomainInfo getDomain(WXPayConfig config) {
		System.out.println(config);
		return new DomainInfo(WXPayConstants.DOMAIN_API, true);
	}

}
