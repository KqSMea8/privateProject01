package com.project.common.util.validate;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.impl.SLF4JLogFactory;

import com.google.gson.Gson;
import com.project.common.annotation.aops.ValidateParam;
import com.project.common.util.DateUtil;

/**
 * 实体属性验证抽象类
 */
public abstract class AbstractParameter implements Serializable {
	private static final long serialVersionUID = -2094873572292397508L;

	private static Log log = SLF4JLogFactory.getLog(AbstractParameter.class);

	private String validateErrorMsg;

	public String getValidateErrorMsg() {
		return validateErrorMsg;
	}

	public void setValidateErrorMsg(String validateErrorMsg) {
		this.validateErrorMsg = validateErrorMsg;
	}

	public boolean validate() throws IllegalArgumentException, IllegalAccessException, InstantiationException, UnsupportedEncodingException {
		List<Field> fieldList = new ArrayList<>();
		Class<?> currentClass = this.getClass();

		// 递归自己及所有父类的属性
		while (null != currentClass) {
			fieldList.addAll(Arrays.asList(currentClass.getDeclaredFields()));
			currentClass = currentClass.getSuperclass();
		}

		// 遍历所有属性
		for (Field f : fieldList) {
			// 获取属性上的注解
			Annotation Annotations[] = f.getAnnotations();
			for (Annotation a : Annotations) {
				if (a instanceof ValidateParam) {
					if (!validateParam(f, a, fieldList)) {
						return false;
					}
				}
			}
		}

		System.out.println("参数：" + new Gson().toJson(this));
		
		return true;
	}

	/**
	 * 参数验证
	 * 
	 * @param f
	 * @param a
	 * @param fieldList
	 * @return
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 * @throws UnsupportedEncodingException 
	 */
	private boolean validateParam(Field f, Annotation a, List<Field> fieldList)
			throws IllegalArgumentException, IllegalAccessException, InstantiationException, UnsupportedEncodingException {
		// 提升权限可读private字段
		f.setAccessible(true);

		// 取得属性的名称，值，数据类型
		String attrName = ("".equals(((ValidateParam) a).ParamName()) ? f.getName() : ((ValidateParam) a).ParamName());
		Object attrValue = f.get(this);
//		String fieldGenericType = f.getGenericType().getTypeName(); JDK8可以这样写
		// JDK7只能这样写
		String fieldGenericType = f.getGenericType().toString().replaceAll("class ", "");
		
		// 取出属性通用数据类型的枚举进行switch
		JavaGenericTypeEnum javaType = JavaGenericTypeEnum.getJavaGenericTypeEnum(fieldGenericType);

		// 获取注解中一些常规值
		String tip = ((ValidateParam) a).tip();
		boolean hasTip = StringUtils.isNotEmpty(tip);
		String defaultValue = ((ValidateParam) a).defaultValue();
		boolean hasDefaultValue = StringUtils.isNotEmpty(defaultValue);
		boolean allowEmpty = ((ValidateParam) a).allowEmpty();
		boolean allowEmptyStr = ((ValidateParam) a).allowEmptyStr();
		String compareToAttr = ((ValidateParam) a).compareTo();
		boolean isCompare = StringUtils.isNotEmpty(compareToAttr);
		// 由于注解入参中限制了<? extends BaseEnumInterface>且findIn有默认值则此处可直接取第一个枚举对象来验证
		BaseEnumInterface baseEnum = ((ValidateParam) a).findIn().getEnumConstants()[0];
		boolean isFindIn = !(baseEnum instanceof BaseNullEnum);

		// 基础判空
		if (null == attrValue || StringUtils.isEmpty(attrValue.toString())) {
			// 属性为空
			if (allowEmpty) {
				// 允许为空时直接返回不再验证
				if(!allowEmptyStr){
					f.set(this, null);
				}
				return true;
			} else {
				if (!hasDefaultValue) {
					validateErrorMsg = hasTip ? tip : attrName + "属性不能为空";
					return false;
				} else {
					// 设置默认值后继续后结验证，防止默认值不符合作者要求
					attrValue = setDefaultValue(javaType, defaultValue, f);
				}
			}
		}

		switch (javaType) {
		case 基础类型_btye:
		case 基础类型_short:
		case 基础类型_int:
		case 基础类型_long:
		case 包装类_BYTE:
		case 包装类_SHORT:
		case 包装类_INTEGER:
		case 包装类_LONG:
		case 包装类_BIGINTEGER:
			// 处理整型类型
			BigInteger integerValue = null;
			try {
				integerValue = new BigInteger(attrValue.toString());
			} catch (Exception e) {
				validateErrorMsg = hasTip ? tip : attrName + "属性为空或类型不正确";
				return false;
			}

			if (integerValue.compareTo(new BigInteger(((ValidateParam) a).maxValue() + "")) == 1) {
				// 参数值超过最大值
				validateErrorMsg = hasTip ? tip : attrName + "属性值不能大于" + ((ValidateParam) a).maxValue();
				return false;
			}

			if (integerValue.compareTo(new BigInteger(((ValidateParam) a).minValue() + "")) == -1) {
				// 参数值小与最小值
				validateErrorMsg = hasTip ? tip : attrName + "属性值不能小于" + ((ValidateParam) a).minValue();
				return false;
			}

			break;
		case 基础类型_float:
		case 基础类型_double:
		case 包装类_FLOAT:
		case 包装类_DOUBLE:
		case 包装类_BIGDECIMAL:
			// 处理浮点型类型
			BigDecimal decimalValue = null;
			try {
				decimalValue = new BigDecimal(attrValue.toString());
			} catch (Exception e) {
				validateErrorMsg = hasTip ? tip : attrName + "属性为空或类型不正确";
				return false;
			}

			if (decimalValue.compareTo(new BigDecimal(((ValidateParam) a).maxValue() + "")) == 1) {
				// 参数值超过最大值
				validateErrorMsg = hasTip ? tip : attrName + "属性值不能大于" + ((ValidateParam) a).maxValue();
				return false;
			}

			if (decimalValue.compareTo(new BigDecimal(((ValidateParam) a).minValue() + "")) == -1) {
				// 参数值小与最小值
				validateErrorMsg = hasTip ? tip : attrName + "属性值不能小于" + ((ValidateParam) a).minValue();
				return false;
			}
			break;
		case 包装类_STRING:
			// 先判断是否可以为空
			String stringValue = attrValue.toString();

			// 判断正则
			String regex = ((ValidateParam) a).regex();
			if (StringUtils.isNotEmpty(regex)) {
				Pattern p = Pattern.compile(regex);
				Matcher m = p.matcher(stringValue);

				if (!m.matches()) {
					// 正则验证未通过
					validateErrorMsg = hasTip ? tip : attrName + "属性正则验证未通过";
					return false;
				}
			}
			// 判断最大长度
			int maxLength = ((ValidateParam) a).maxLength();
			if (stringValue.getBytes("utf-8").length > maxLength) {
				// 参数超长
				validateErrorMsg = hasTip ? tip : attrName + "属性最大长度不能大于" + maxLength + "个字符";
				return false;
			}

			int minLength = ((ValidateParam) a).minLength();
			if (stringValue.length() < minLength) {
				// 参数过短
				validateErrorMsg = hasTip ? tip : attrName + "属性最小长度不能小于" + minLength + "个字符";
				return false;
			}

			break;
		default:
			return true;
		}

		// 判断有没有枚举类中查询属生的验证
		if (isFindIn) {
			// baseEnum不是默认值，说明作者注解了别的枚举类对参数进行验证
			if (null == baseEnum.getEnumByName(attrValue.toString())) {
				validateErrorMsg = hasTip ? tip : attrName + "属性必须满足枚举类" + baseEnum.getClass().getCanonicalName();
				return false;
			}
		}

		// 判断该属性是否要与其它属性做比较
		if (isCompare) {
			// 要与其它属性比较
			if (!compareTo(attrValue, a, compareToAttr, fieldList)) {
				validateErrorMsg = StringUtils.isNotEmpty(((ValidateParam) a).compareTip()) ? ((ValidateParam) a).compareTip()
						: attrName + "属性对比" + ((ValidateParam) a).compareTo() + "属性失败";
				return false;
			}
		}

		return true;
	}

	/**
	 * 给属性设置默认值
	 * 
	 * @param javaType
	 * @param defaultValue
	 * @param field
	 * @return
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	private Object setDefaultValue(JavaGenericTypeEnum javaType, String defaultValue, Field field)
			throws IllegalArgumentException, IllegalAccessException {
		switch (javaType) {
		case 基础类型_btye:
			field.set(this, new Byte(defaultValue).byteValue());
			return new Byte(defaultValue);
		case 基础类型_short:
			field.set(this, new Short(defaultValue).shortValue());
			return new Short(defaultValue);
		case 基础类型_int:
			field.set(this, new Integer(defaultValue).intValue());
			return new Integer(defaultValue);
		case 基础类型_long:
			field.set(this, new Long(defaultValue).longValue());
			return new Long(defaultValue);
		case 包装类_BYTE:
			field.set(this, new Byte(defaultValue));
			return new Byte(defaultValue);
		case 包装类_SHORT:
			field.set(this, new Short(defaultValue));
			return new Short(defaultValue);
		case 包装类_INTEGER:
			field.set(this, new Integer(defaultValue));
			return new Integer(defaultValue);
		case 包装类_LONG:
			field.set(this, new Long(defaultValue));
			return new Long(defaultValue);
		case 包装类_BIGINTEGER:
			field.set(this, new BigInteger(defaultValue));
			return new BigInteger(defaultValue);
		case 基础类型_float:
			field.set(this, new Float(defaultValue).floatValue());
			return new Float(defaultValue);
		case 基础类型_double:
			field.set(this, new Double(defaultValue).doubleValue());
			return new Double(defaultValue);
		case 包装类_FLOAT:
			field.set(this, new Float(defaultValue));
			return new Float(defaultValue);
		case 包装类_DOUBLE:
			field.set(this, new Double(defaultValue));
			return new Double(defaultValue);
		case 包装类_BIGDECIMAL:
			field.set(this, new BigDecimal(defaultValue));
			return new BigDecimal(defaultValue);
		case 包装类_STRING:
			field.set(this, defaultValue);
			return defaultValue;
		case 包装类_BOOLEAN:
			field.set(this, new Boolean(defaultValue));
			return new Boolean(defaultValue);
		case 基础类型_boolean:
			field.set(this, new Boolean(defaultValue).booleanValue());
			return new Boolean(defaultValue);
		default:
			field.set(this, defaultValue);
			return defaultValue;
		}
	}

	/**
	 * 属性间比较
	 * 
	 * @param attrValue
	 *            当前属性的值
	 * @param a
	 *            当前属性上的注解
	 * @param compareToAttr
	 *            被比较的属性名称
	 * @param fieldList
	 *            当前实体所有属性
	 * @return
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 */
	private boolean compareTo(Object attrValue, Annotation a, String compareToAttr, List<Field> fieldList)
			throws IllegalArgumentException, IllegalAccessException {
		// 查找属性是否存在
		Field compareToField = null;
		for (Field f : fieldList) {
			if (f.getName().equals(compareToAttr)) {
				f.setAccessible(true);
				compareToField = f;
				break;
			}
		}

		if (null == compareToField) {
			// 未找到对应的属性，直接返回true
			log.info("未找到目标属性：" + compareToAttr + "，不再进行比较");
			return true;
		}

		// 判断目标对象是否为空
		if (null == compareToField.get(this) || StringUtils.isEmpty(compareToField.get(this).toString())) {
			// 比标对象为空，不用判断
			log.info("找到目标属性：" + compareToAttr + "，但属性值为空，不再进行比较");
			return true;
		}

		CompareTypeEnum compareType = ((ValidateParam) a).compareType();
		CompareModeEnum compareMode = ((ValidateParam) a).compareMode();

		switch (compareType) {
		case STRING:
			try {
				switch (compareMode) {
				case 等于:
					return attrValue.equals(compareToField.get(this));
				case 不等于:
					return !attrValue.equals(compareToField.get(this));
				default:
					log.info("String类型属性比较时，只有“等于”或“不等于”，请作者排查注解内容");
					return false;
				}
			} catch (Exception e) {
				log.error("属性间比较出现异常，请排查询属性值或注解内容", e);
				return false;
			}
		case DATE:
			try {
				long dateA = DateUtil.dateFormat.parse(attrValue.toString()).getTime();
				long dateB = DateUtil.dateFormat.parse(compareToField.get(this).toString()).getTime();

				switch (compareMode) {
				case 等于:
					return dateA == dateB;
				case 不等于:
					return dateA != dateB;
				case 大于:
					return dateA > dateB;
				case 大于等于:
					return dateA >= dateB;
				case 小于:
					return dateA < dateB;
				case 小于等于:
					return dateA <= dateB;
				}
			} catch (Exception e) {
				log.error("属性间比较出现异常，请排查询属性值或注解内容", e);
				return false;
			}
		case NUMBER:
			try {
				BigDecimal decimalA = new BigDecimal(attrValue.toString());
				BigDecimal decimalB = new BigDecimal(compareToField.get(this).toString());

				switch (compareMode) {
				case 等于:
					return decimalA.compareTo(decimalB) == 0;
				case 不等于:
					return decimalA.compareTo(decimalB) != 0;
				case 大于:
					return decimalA.compareTo(decimalB) == 1;
				case 大于等于:
					return decimalA.compareTo(decimalB) == 1 || decimalA.compareTo(decimalB) == 0;
				case 小于:
					return decimalA.compareTo(decimalB) == -1;
				case 小于等于:
					return decimalA.compareTo(decimalB) == -1 || decimalA.compareTo(decimalB) == 0;
				}
			} catch (Exception e) {
				log.error("属性间比较出现异常，请排查询属性值或注解内容", e);
				return false;
			}
		}

		return true;
	}
}
