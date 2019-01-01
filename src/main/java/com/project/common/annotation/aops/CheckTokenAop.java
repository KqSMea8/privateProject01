package com.project.common.annotation.aops;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.project.common.annotation.service.CheckTokenAopService;
import com.project.common.base.Base;
import com.project.common.base.BaseResult;

/**
 * 验证登录切面
 * 
 * ClassName: CheckTokenAop
 * 
 * @author chentianjin
 * @date 2017年6月12日
 */
@Component
@Aspect
public class CheckTokenAop extends Base {

	@Autowired
	CheckTokenAopService checkTokenAopService;

	/**
	 * @param joinPoint
	 * @throws Throwable
	 * @return BaseResult
	 * @author chentianjin
	 * @date 2017年5月2日
	 */
	@Around("@annotation(com.project.common.annotation.interfaces.CheckToken)")
	public BaseResult around(ProceedingJoinPoint joinPoint) throws Throwable {
		// 当token为null的时候说明未登录
		if (checkTokenAopService.getOperatorIdByToken(joinPoint) == null)
			return generateResult(101, "该用户未登录");
		// 如果登录，则返回方法的返回值
		return (BaseResult) joinPoint.proceed();
	}
}
