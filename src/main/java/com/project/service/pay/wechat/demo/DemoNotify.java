package com.project.service.pay.wechat.demo;

import java.util.Map;

import com.project.service.pay.wechat.WXPay;
import com.project.service.pay.wechat.WXPayUtil;
import com.project.service.pay.wechat.config.PayConfigForWeChat;

public class DemoNotify {
	public static void main(String[] args) throws Exception {

		String notifyData = "...."; // 支付结果通知的xml格式数据

		PayConfigForWeChat config = new PayConfigForWeChat();
		WXPay wxpay = new WXPay(config);

		Map<String, String> notifyMap = WXPayUtil.xmlToMap(notifyData); // 转换成map

		if (wxpay.isPayResultNotifySignatureValid(notifyMap)) {
			// 签名正确
			// 进行处理。
			// 注意特殊情况：订单已经退款，但收到了支付结果成功的通知，不应把商户侧订单状态从退款改成支付成功
		} else {
			// 签名错误，如果数据里没有sign字段，也认为是签名错误
		}
	}
}
