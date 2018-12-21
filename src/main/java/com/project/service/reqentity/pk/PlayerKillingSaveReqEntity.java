package com.project.service.reqentity.pk;

import com.project.common.annotation.aops.ValidateParam;
import com.project.common.util.validate.AbstractParameter;
import com.project.common.util.validate.RegexConstant;

/**
 * 约战信息保存请求参数实体
 */
public class PlayerKillingSaveReqEntity extends AbstractParameter {
	private static final long serialVersionUID = -6766968319729552767L;

	@ValidateParam(allowEmpty = true)
	Integer pkInfoId;

	@ValidateParam(tip = "标题不得为空!")
	String title;

	@ValidateParam(tip = "城市ID不得为空!")
	Integer cityId;

	@ValidateParam(tip = "区域ID不得为空!")
	Integer districtId;

	@ValidateParam(tip = "详细地址不得为空!")
	String address;

	@ValidateParam(tip = "发起约战球队ID不得为空!")
	Integer teamId;

	@ValidateParam(tip = "人数上限不得为空!")
	Integer teamMemberTotal;

	@ValidateParam(tip = "队服颜色类型不得为空!")
	Integer teamJerseyColor;

	@ValidateParam(tip = "赛制类型不得为空!", minValue = 1)
	Integer pkMarchType;

	@ValidateParam(tip = "比赛开始时间不得为空!!", regex = RegexConstant.REGEX_DATE_YYYY_MM_DD)
	String pkStartTime;

	@ValidateParam(tip = "应战申请截止时间不得为空!", regex = RegexConstant.REGEX_DATE_YYYY_MM_DD)
	String pkSignUpEndTime;

	@ValidateParam(tip = "比赛时长不得为空!")
	Integer pkDuration;

	@ValidateParam(tip = "费用说明不得为空!")
	String costContent;

	@ValidateParam(allowEmpty = true)
	String briefing;

	public Integer getPkInfoId() {
		return pkInfoId;
	}

	public void setPkInfoId(Integer pkInfoId) {
		this.pkInfoId = pkInfoId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
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

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Integer getTeamId() {
		return teamId;
	}

	public void setTeamId(Integer teamId) {
		this.teamId = teamId;
	}

	public Integer getTeamMemberTotal() {
		return teamMemberTotal;
	}

	public void setTeamMemberTotal(Integer teamMemberTotal) {
		this.teamMemberTotal = teamMemberTotal;
	}

	public Integer getTeamJerseyColor() {
		return teamJerseyColor;
	}

	public void setTeamJerseyColor(Integer teamJerseyColor) {
		this.teamJerseyColor = teamJerseyColor;
	}

	public Integer getPkMarchType() {
		return pkMarchType;
	}

	public void setPkMarchType(Integer pkMarchType) {
		this.pkMarchType = pkMarchType;
	}

	public String getPkStartTime() {
		return pkStartTime;
	}

	public void setPkStartTime(String pkStartTime) {
		this.pkStartTime = pkStartTime;
	}

	public String getPkSignUpEndTime() {
		return pkSignUpEndTime;
	}

	public void setPkSignUpEndTime(String pkSignUpEndTime) {
		this.pkSignUpEndTime = pkSignUpEndTime;
	}

	public Integer getPkDuration() {
		return pkDuration;
	}

	public void setPkDuration(Integer pkDuration) {
		this.pkDuration = pkDuration;
	}

	public String getCostContent() {
		return costContent;
	}

	public void setCostContent(String costContent) {
		this.costContent = costContent;
	}

	public String getBriefing() {
		return briefing;
	}

	public void setBriefing(String briefing) {
		this.briefing = briefing;
	}
}
