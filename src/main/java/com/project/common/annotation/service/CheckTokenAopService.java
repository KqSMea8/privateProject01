package com.project.common.annotation.service;

import java.lang.annotation.Annotation;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Service;

import com.project.common.annotation.interfaces.CheckParam;
import com.project.common.annotation.interfaces.CheckToken;
import com.project.common.base.BaseService;
import com.project.common.util.StringUtil;

@Service("checkTokenAopService")
public class CheckTokenAopService extends BaseService {

	/**
	 * 验证token是否有值
	 */
	public String getOperatorIdByToken(JoinPoint joinPoint) {
		// 获取方法签名
		MethodSignature ms = (MethodSignature) joinPoint.getSignature();

		// 获取方法注解
		CheckToken checkToken = ms.getMethod().getAnnotation(CheckToken.class);

		if (checkToken == null)
			return null;

		String token = null;
		Annotation[][] anno = ms.getMethod().getParameterAnnotations();
		for (int i = 0; i < anno.length; i++) { // 循环参数列表
			for (int j = 0; j < anno[i].length; j++) { // 循环某上参数上的注解
				if (anno[i][j] instanceof CheckParam && ((CheckParam) anno[i][j]).value().equals(checkToken.value())) {
					token = joinPoint.getArgs()[i] == null ? null : (String) joinPoint.getArgs()[i];
				}
			}
		}
		if (token == null || StringUtil.isEmpty(redisUtil.get(token)))
			token = null;
		return token;
	}
}
