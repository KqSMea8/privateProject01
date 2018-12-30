package com.project.service.enums;

public enum EventrealityEnum {
	进球(42, "进球"), 黄牌(43, "黄牌"), 换人(45, "换人"), 红牌(44, "红牌"), 点球(60, "点球"), 射门(54, "射门"), 助攻(55, "助攻"), 规则牌(101, "规则牌");

	private Integer keyCode;
	private String showName;

	private EventrealityEnum(Integer keyCode, String showName) {
		this.keyCode = keyCode;
		this.showName = showName;
	}

	public Integer getKeyCode() {
		return keyCode;
	}

	public void setKeyCode(Integer keyCode) {
		this.keyCode = keyCode;
	}

	public String getShowName() {
		return showName;
	}

	public void setShowName(String showName) {
		this.showName = showName;
	}

}
