package com.project.service.pay.wechat.config;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.project.service.pay.wechat.WXPayUtil;

public class NotifyUtilForWeChat {

	public static Map<String, String> getWeChatPayReturn(HttpServletRequest request) {
		try {
			InputStream inStream = request.getInputStream();
			int _buffer_size = 1024;
			if (inStream != null) {
				ByteArrayOutputStream outStream = new ByteArrayOutputStream();
				byte[] tempBytes = new byte[_buffer_size];
				int count = -1;
				while ((count = inStream.read(tempBytes, 0, _buffer_size)) != -1) {
					outStream.write(tempBytes, 0, count);
				}
				tempBytes = null;
				outStream.flush();
				// 将流转换成字符串
				String result = new String(outStream.toByteArray(), "UTF-8");
				// 将XML格式转化成MAP格式数据
				Map<String, String> resultMap = WXPayUtil.xmlToMap(result);
				return resultMap;
			}
			// 通知微信支付系统接收到信息
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
