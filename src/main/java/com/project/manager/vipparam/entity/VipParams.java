package com.project.manager.vipparam.entity;

import java.io.Serializable;

public class VipParams implements Serializable {

	private static final long serialVersionUID = 621724010495536513L;

	private Integer vipLevel;
	private String vipName;
	private Integer directTotal;
	private Integer groupTotal;
	private Integer needLevel;
	private Integer needLevelTotal;

	public Integer getVipLevel() {
		return vipLevel;
	}

	public void setVipLevel(Integer vipLevel) {
		this.vipLevel = vipLevel;
	}

	public String getVipName() {
		return vipName;
	}

	public void setVipName(String vipName) {
		this.vipName = vipName;
	}

	public Integer getDirectTotal() {
		return directTotal;
	}

	public void setDirectTotal(Integer directTotal) {
		this.directTotal = directTotal;
	}

	public Integer getGroupTotal() {
		return groupTotal;
	}

	public void setGroupTotal(Integer groupTotal) {
		this.groupTotal = groupTotal;
	}

	public Integer getNeedLevel() {
		return needLevel;
	}

	public void setNeedLevel(Integer needLevel) {
		this.needLevel = needLevel;
	}

	public Integer getNeedLevelTotal() {
		return needLevelTotal;
	}

	public void setNeedLevelTotal(Integer needLevelTotal) {
		this.needLevelTotal = needLevelTotal;
	}

}
