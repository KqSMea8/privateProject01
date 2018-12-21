package com.project.service.reqentity.pk;

import com.project.common.annotation.aops.ValidateParam;
import com.project.common.util.validate.AbstractParameter;

/**
 * 约战申请请求参数实体
 */
public class PlayerKillingApplyReqEntity extends AbstractParameter {
	private static final long serialVersionUID = -6766968319729552767L;

	@ValidateParam(tip = "约战ID不得为空!")
	Integer pkInfoId;

	@ValidateParam(tip = "球队ID不得为空!")
	Integer teamId;

	@ValidateParam(tip = "人数上限不得为空!")
	Integer teamMemberTotal;

	@ValidateParam(tip = "队服颜色不得为空!")
	Integer teamJerseyColor;//

	@ValidateParam(tip = "联系人不得为空!")
	String contacts;

	@ValidateParam(tip = "联系人电话不得为空!")
	String contactsTel;

	@ValidateParam(allowEmpty = true)
	Integer briefing;

	public Integer getPkInfoId() {
		return pkInfoId;
	}

	public void setPkInfoId(Integer pkInfoId) {
		this.pkInfoId = pkInfoId;
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

	public Integer getBriefing() {
		return briefing;
	}

	public void setBriefing(Integer briefing) {
		this.briefing = briefing;
	}
}
