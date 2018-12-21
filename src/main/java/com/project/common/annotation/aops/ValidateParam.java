package com.project.common.annotation.aops;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import com.project.common.util.validate.BaseEnumInterface;
import com.project.common.util.validate.BaseNullEnum;
import com.project.common.util.validate.CompareModeEnum;
import com.project.common.util.validate.CompareTypeEnum;

/**
 * 自定义注解，用于参数验证
 */
@Documented
@Retention(RUNTIME)
@Target({ FIELD, PARAMETER, METHOD })
public @interface ValidateParam {
	/**
	 * 提示
	 * 
	 * @return
	 */
	String tip() default "";

	/**
	 * 正则表达式
	 * 
	 * @return
	 */
	String regex() default "";

	/**
	 * 是否非空
	 * 
	 * @return
	 */
	boolean allowEmpty() default false;

	/**
	 * 是否可以为 "" 或者 "null" false 如果是""或者"null"这转换成null true 不做任何转化
	 * 
	 * @author chentianjin
	 * @date 2018年12月12日
	 * @return
	 */
	boolean allowEmptyStr() default false;

	/**
	 * 当属性不允许为空时，但验证时为空，则设置默认值
	 * 
	 * <p>
	 * 默认值也为进行验证
	 * </p>
	 * 
	 * @return
	 */
	String defaultValue() default "";

	/**
	 * 最大长度
	 * 
	 * @return
	 */
	int maxLength() default Integer.MAX_VALUE;

	/**
	 * 最小长度
	 * 
	 * @return
	 */
	int minLength() default 0;

	/**
	 * 最大值
	 * 
	 * @return
	 */
	int maxValue() default Integer.MAX_VALUE;

	/**
	 * 最小值
	 * 
	 * @return
	 */
	int minValue() default Integer.MIN_VALUE;

	/**
	 * 在某个枚举类中查询这个属性值是否能找到对应的枚举内
	 * 
	 * @return
	 */
	Class<? extends BaseEnumInterface> findIn() default BaseNullEnum.class;

	/**
	 * 关联验证（与哪个属性关联验证）
	 * 
	 * @return
	 */
	String compareTo() default "";

	/**
	 * 比较方式
	 * 
	 * @return
	 */
	CompareModeEnum compareMode() default CompareModeEnum.等于;

	/**
	 * 比较类型
	 * 
	 * @return
	 */
	CompareTypeEnum compareType() default CompareTypeEnum.STRING;

	/**
	 * 用于属性比较返回false时提示用
	 * 
	 * @return
	 */
	String compareTip() default "";

	/**
	 * 参数名称
	 * 
	 * @return
	 */
	String ParamName() default "";
}
