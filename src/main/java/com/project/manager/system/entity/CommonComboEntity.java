package com.project.manager.system.entity;

import java.io.Serializable;

/**
 * 下拉列表实体
 */
public class CommonComboEntity implements Serializable {
	private static final long serialVersionUID = 8838048817106062694L;

	private String value;
	private String id;
	private String text;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
}
