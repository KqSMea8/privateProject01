package com.project.common.util.validate;
/**
 * java通用类型枚举类
 * 
 * <li>8种基础类型及其包装类</li>
 */
public enum JavaGenericTypeEnum {
	基础类型_btye("byte"), 
	基础类型_short("short"), 
	基础类型_int("int"), 
	基础类型_long("long"), 
	基础类型_float("float"), 
	基础类型_double("double"), 
	基础类型_char("char"), 
	基础类型_boolean("boolean"),
	包装类_BYTE("java.lang.Byte"),
	包装类_SHORT("java.lang.Short"),
	包装类_INTEGER("java.lang.Integer"),
	包装类_LONG("java.lang.Long"),
	包装类_FLOAT("java.lang.Float"),
	包装类_DOUBLE("java.lang.Double"),
	包装类_CHARACTER("java.lang.Character"),
	包装类_STRING("java.lang.String"),
	包装类_BOOLEAN("java.lang.Boolean"),
	包装类_BIGDECIMAL("java.math.BigDecimal"),
	包装类_BIGINTEGER("java.math.BigInteger"),
	数组_btye("byte[]"), 
	数组_short("short[]"), 
	数组_int("int[]"), 
	数组_long("long[]"), 
	数组_float("float[]"), 
	数组_double("double[]"), 
	数组_char("char[]"), 
	数组_boolean("boolean[]"),
	数组_BYTE("java.lang.Byte[]"),
	数组_SHORT("java.lang.Short[]"),
	数组_INTEGER("java.lang.Integer[]"),
	数组_LONG("java.lang.Long[]"),
	数组_FLOAT("java.lang.Float[]"),
	数组_DOUBLE("java.lang.Double[]"),
	数组_CHARACTER("java.lang.Character[]"),
	数组_STRING("java.lang.String[]"),
	数组_BOOLEAN("java.lang.Boolean[]"),
	数组_BIGDECIMAL("java.math.BigDecimal[]"),
	数组_BIGINTEGER("java.math.BigInteger[]");
	
	private String typeName;
	
	JavaGenericTypeEnum(String typeName) {
		this.typeName = typeName;
	}
	
	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public static JavaGenericTypeEnum getJavaGenericTypeEnum(String typeName) {
		JavaGenericTypeEnum enums[] = JavaGenericTypeEnum.values();
		for (JavaGenericTypeEnum e : enums) {
			if (e.getTypeName().equals(typeName)) {
				return e;
			}
		}
		
		return null;
	}
}
