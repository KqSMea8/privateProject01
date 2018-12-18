package com.project.common.annotation.interfaces;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 验证token注解
 * 
 * ClassName: CheckToken
 * 
 * @author chentianjin
 * @date 2017年4月28日
 */

@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD })
public @interface CheckToken {
	public String value() default "token";
}
