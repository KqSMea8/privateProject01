package com.project.common.util.validate;

/**
 * 固定日期枚举类
 */
public enum FixedDateEnum implements BaseEnumInterface {
	今日("今日"), 昨日("昨日"), 本周("本周"), 上周("上周"), 本月("本月"), 上月("上月");

	private String fixedDate;

	FixedDateEnum(String fixedDate) {
		this.fixedDate = fixedDate;
	}

	public String getFixedDate() {
		return fixedDate;
	}

	public void setFixedDate(String fixedDate) {
		this.fixedDate = fixedDate;
	}

	@Override
	public BaseEnumInterface getEnumByName(String enumName) {
		FixedDateEnum enums[] = FixedDateEnum.values();

		for (FixedDateEnum e : enums) {
			if (e.getFixedDate().equals(enumName)) {
				return e;
			}
		}

		return null;
	}
	
	public static FixedDateEnum getFixedDateEnum(String fixedDate) {
		FixedDateEnum enums[] = FixedDateEnum.values();

		for (FixedDateEnum e : enums) {
			if (e.getFixedDate().equals(fixedDate)) {
				return e;
			}
		}

		return null;
	}
}
