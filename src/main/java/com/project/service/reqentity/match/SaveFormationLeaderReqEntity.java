package com.project.service.reqentity.match;

import com.project.common.util.validate.AbstractParameter;

public class SaveFormationLeaderReqEntity extends AbstractParameter {

	private static final long serialVersionUID = 1668222306231167862L;
	private Integer scheduleId;
	private Integer type;// 1表示A队，2表示B队
	private String colorOfJersey;
	private String colorOfNants;
	private String colorOfSocks;
	private String formation;
	private String teamFirstMemberStr;

	public Integer getScheduleId() {
		return scheduleId;
	}

	public void setScheduleId(Integer scheduleId) {
		this.scheduleId = scheduleId;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getColorOfJersey() {
		return colorOfJersey;
	}

	public void setColorOfJersey(String colorOfJersey) {
		this.colorOfJersey = colorOfJersey;
	}

	public String getColorOfNants() {
		return colorOfNants;
	}

	public void setColorOfNants(String colorOfNants) {
		this.colorOfNants = colorOfNants;
	}

	public String getColorOfSocks() {
		return colorOfSocks;
	}

	public void setColorOfSocks(String colorOfSocks) {
		this.colorOfSocks = colorOfSocks;
	}

	public String getFormation() {
		return formation;
	}

	public void setFormation(String formation) {
		this.formation = formation;
	}

	public String getTeamFirstMemberStr() {
		return teamFirstMemberStr;
	}

	public void setTeamFirstMemberStr(String teamFirstMemberStr) {
		this.teamFirstMemberStr = teamFirstMemberStr;
	}
}
