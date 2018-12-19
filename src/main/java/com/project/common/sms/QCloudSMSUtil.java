package com.project.common.sms;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.apache.commons.lang3.StringUtils;

import com.google.gson.Gson;
import com.project.common.sms.entity.SMSParameter;
import com.project.common.sms.entity.SMSRecvEntity;
import com.project.common.sms.entity.SMSSendEntity;
import com.project.common.sms.enums.SMSSignEnum;
import com.project.common.sms.enums.SMSTempletEnum;
import com.project.common.util.HttpRequestClient;

/**
 * 腾讯云短信发送服务类
 */
public class QCloudSMSUtil {
	private final static String SMS_API_KEY = "ef5c4072909ab6233a649fa1d01216ea";
	private final static String SMS_API_ID = "1400014254";

	private final static String RECV_SUCCESS = "0";

	public final static String API_URL_SMS = "https://yun.tim.qq.com/v3/tlssmssvr/sendsms?sdkappid=%s&random=%s";
	private String lasdErrorText;
	private String msg;

	/**
	 * 获取最后一次错误信息
	 */
	public String getLasdErrorText() {
		return lasdErrorText;
	}

	public String getMsg() {
		return msg;
	}

	/**
	 * 发送短信
	 * 
	 * @param toUserTel 接收短信的用户手机号码
	 * @param sign      签名
	 * @param signFront 签名前置
	 * @param extend    扩展信息
	 * @param templet   短信模板
	 * @param smsParams 模板参数
	 * @return
	 */
	public boolean sendSMS(String toUserTel, SMSSignEnum sign, boolean signFront, String extend, SMSTempletEnum templet, SMSParameter smsParams) {
		try {
			// 得到腾讯云发送短信的文本内容
			String content = "";
			if (null != smsParams && smsParams.size() > 0) {
				List<String> list = new ArrayList<String>();
				for (int i = 0; i < smsParams.size(); i++) {
					list.add(smsParams.get(i).getValue());
				}

				content = String.format(templet.getTencentTemplet(), list.toArray());
			} else {
				content = templet.getTencentTemplet();
			}

			// 去掉正文中的【】
			content = content.replaceAll("【", "").replaceAll("】", "");

			if (signFront) {
				msg = sign.getSignName() + content;
			} else {
				msg = content + sign.getSignName();
			}

			Gson gson = new Gson();

			SMSSendEntity sendEntity = SMSSendEntity.getInstance(toUserTel, msg, extend, SMS_API_KEY);
			String json = gson.toJson(sendEntity);

			String url = String.format(API_URL_SMS, SMS_API_ID, new Random().nextInt());

			System.out.println("发送的URL：" + url);
			System.out.println("发送的文本：" + json);

			HttpRequestClient httpClient = new HttpRequestClient().setHttpPostUrl(url).setStringEntity(json, "utf-8");
			httpClient.execute();
			String resultJson = httpClient.getResponseText();

			System.out.println("返回的文本：" + resultJson);

			if (StringUtils.isEmpty(resultJson)) {
				lasdErrorText = "未接收到远端返回结果";
				return false;
			} else {
				SMSRecvEntity recvEntity = gson.fromJson(resultJson, SMSRecvEntity.class);
				if (recvEntity.getResult().equals(RECV_SUCCESS)) {
					return true;
				} else {
					lasdErrorText = recvEntity.getErrmsg();
					return false;
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
}