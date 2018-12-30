package com.project.service.reqentity.match;

import com.project.common.util.validate.AbstractParameter;

public class SaveFormationReqEntity extends AbstractParameter {

	private static final long serialVersionUID = 6227629677568852940L;
	private Integer scheduleId;
	private String colorOfJerseyA;
	private String colorOfNantsA;
	private String colorOfSocksA;
	private String formationA;
	private String colorOfJerseyB;
	private String colorOfNantsB;
	private String colorOfSocksB;
	private String formationB;
	private String teamAFirstMemberStr;
	private String teamBFirstMemberStr;

	public Integer getScheduleId() {
		return scheduleId;
	}

	public void setScheduleId(Integer scheduleId) {
		this.scheduleId = scheduleId;
	}

	public String getColorOfJerseyA() {
		return colorOfJerseyA;
	}

	public void setColorOfJerseyA(String colorOfJerseyA) {
		this.colorOfJerseyA = colorOfJerseyA;
	}

	public String getColorOfNantsA() {
		return colorOfNantsA;
	}

	public void setColorOfNantsA(String colorOfNantsA) {
		this.colorOfNantsA = colorOfNantsA;
	}

	public String getColorOfSocksA() {
		return colorOfSocksA;
	}

	public void setColorOfSocksA(String colorOfSocksA) {
		this.colorOfSocksA = colorOfSocksA;
	}

	public String getFormationA() {
		return formationA;
	}

	public void setFormationA(String formationA) {
		this.formationA = formationA;
	}

	public String getColorOfJerseyB() {
		return colorOfJerseyB;
	}

	public void setColorOfJerseyB(String colorOfJerseyB) {
		this.colorOfJerseyB = colorOfJerseyB;
	}

	public String getColorOfNantsB() {
		return colorOfNantsB;
	}

	public void setColorOfNantsB(String colorOfNantsB) {
		this.colorOfNantsB = colorOfNantsB;
	}

	public String getColorOfSocksB() {
		return colorOfSocksB;
	}

	public void setColorOfSocksB(String colorOfSocksB) {
		this.colorOfSocksB = colorOfSocksB;
	}

	public String getFormationB() {
		return formationB;
	}

	public void setFormationB(String formationB) {
		this.formationB = formationB;
	}

	public String getTeamAFirstMemberStr() {
		return teamAFirstMemberStr;
	}

	public void setTeamAFirstMemberStr(String teamAFirstMemberStr) {
		this.teamAFirstMemberStr = teamAFirstMemberStr;
	}

	public String getTeamBFirstMemberStr() {
		return teamBFirstMemberStr;
	}

	public void setTeamBFirstMemberStr(String teamBFirstMemberStr) {
		this.teamBFirstMemberStr = teamBFirstMemberStr;
	}

}
