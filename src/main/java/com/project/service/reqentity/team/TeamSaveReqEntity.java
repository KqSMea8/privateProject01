package com.project.service.reqentity.team;

import com.project.common.annotation.aops.ValidateParam;
import com.project.common.util.validate.AbstractParameter;

public class TeamSaveReqEntity extends AbstractParameter {

	private static final long serialVersionUID = 4780343280535513258L;

	@ValidateParam(allowEmpty = true)
	Integer teamId;

	@ValidateParam(tip = "城市ID不得为空")
	Integer cityId;

	@ValidateParam(tip = "区域ID不得为空")
	Integer districtId;

	@ValidateParam(tip = "球队LOGO不得为空")
	String teamLogo;

	@ValidateParam(tip = "球队名称不得为空")
	String teamName;

	@ValidateParam(tip = "队服颜色不得为空")
	Integer colorOfJersey;

	@ValidateParam(tip = "对裤颜色不得为空")
	Integer colorOfNants;

	@ValidateParam(tip = "队袜颜色不得为空")
	Integer colorOfSocks;

	@ValidateParam(tip = "成立时间不得为空")
	String establish;
	
	Integer job;
	
	Integer position;

	@ValidateParam(tip = "联系人不得为空")
	String contacts;

	@ValidateParam(tip = "联系人电话不得为空")
	String contactsTel;
	
	String teamSynopsis;

	public Integer getTeamId() {
		return teamId;
	}

	public void setTeamId(Integer teamId) {
		this.teamId = teamId;
	}

	public Integer getCityId() {
		return cityId;
	}

	public void setCityId(Integer cityId) {
		this.cityId = cityId;
	}

	public Integer getDistrictId() {
		return districtId;
	}

	public void setDistrictId(Integer districtId) {
		this.districtId = districtId;
	}

	public String getTeamLogo() {
		return teamLogo;
	}

	public void setTeamLogo(String teamLogo) {
		this.teamLogo = teamLogo;
	}

	public String getTeamName() {
		return teamName;
	}

	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}

	public Integer getColorOfJersey() {
		return colorOfJersey;
	}

	public void setColorOfJersey(Integer colorOfJersey) {
		this.colorOfJersey = colorOfJersey;
	}

	public Integer getColorOfNants() {
		return colorOfNants;
	}

	public void setColorOfNants(Integer colorOfNants) {
		this.colorOfNants = colorOfNants;
	}

	public Integer getColorOfSocks() {
		return colorOfSocks;
	}

	public void setColorOfSocks(Integer colorOfSocks) {
		this.colorOfSocks = colorOfSocks;
	}

	public String getEstablish() {
		return establish;
	}

	public void setEstablish(String establish) {
		this.establish = establish;
	}

	public Integer getJob() {
		return job;
	}

	public void setJob(Integer job) {
		this.job = job;
	}

	public Integer getPosition() {
		return position;
	}

	public void setPosition(Integer position) {
		this.position = position;
	}

	public String getContacts() {
		return contacts;
	}

	public void setContacts(String contacts) {
		this.contacts = contacts;
	}

	public String getContactsTel() {
		return contactsTel;
	}

	public void setContactsTel(String contactsTel) {
		this.contactsTel = contactsTel;
	}

	public String getTeamSynopsis() {
		return teamSynopsis;
	}

	public void setTeamSynopsis(String teamSynopsis) {
		this.teamSynopsis = teamSynopsis;
	}
	
}
