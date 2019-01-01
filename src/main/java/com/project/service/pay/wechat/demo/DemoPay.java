package com.project.service.pay.wechat.demo;

import java.util.HashMap;
import java.util.Map;

import com.project.service.pay.wechat.WXPay;
import com.project.service.pay.wechat.config.PayConfigForWeChat;

public class DemoPay {
	public static void main(String[] args) throws Exception {
		PayConfigForWeChat config = new PayConfigForWeChat();
		WXPay wxpay = new WXPay(config);

		Map<String, String> data = new HashMap<String, String>();
		data.put("body", "腾讯充值中心-QQ会员充值");// 商品描述
		data.put("out_trade_no", "2016090910595900000012");// 商户订单号
		data.put("fee_type", "CNY");// 货币类型,默认是CNY人民币
		data.put("total_fee", "1");// 总金额
		data.put("spbill_create_ip", "123.12.12.123");// 终端IP
		data.put("notify_url", "http://www.example.com/wxpay/notify");// 回调地址
		data.put("trade_type", "MWEB"); // 交易类型  APP为APP 网页H5用MWEB

		try {
			Map<String, String> resp = wxpay.unifiedOrder(data);
			System.out.println(resp);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
