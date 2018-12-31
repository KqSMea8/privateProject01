package com.project.service.reqentity.match;

import com.project.common.annotation.aops.ValidateParam;
import com.project.common.util.validate.AbstractParameter;

/**
 * 约战列表请求参数实体
 */
public class AddEventrealityReqEntity extends AbstractParameter {

	private static final long serialVersionUID = -9179454675492903613L;

	Integer matchInfoId;// 需查询

	@ValidateParam(tip = "赛程ID不得为空")
	Integer scheduleId;

	Integer teamInfoId;// 需查询

	String teamInfoName;// 需查询

	@ValidateParam(tip = "球队球员ID不得为空")
	Integer teamMemberId;

	String teamMemberName;// 需查询

	@ValidateParam(tip = "播报类型不得为空")
	Integer type;

	String content;

	String imgUrl;

	Integer replaceTeamMemberId;

	String replaceTeamMemberName;// 需查询

	String formation;// 需查询

	Boolean isHaveInfo;// 需查询

	public Integer getMatchInfoId() {
		return matchInfoId;
	}

	public void setMatchInfoId(Integer matchInfoId) {
		this.matchInfoId = matchInfoId;
	}

	public Integer getScheduleId() {
		return scheduleId;
	}

	public void setScheduleId(Integer scheduleId) {
		this.scheduleId = scheduleId;
	}

	public Integer getTeamInfoId() {
		return teamInfoId;
	}

	public void setTeamInfoId(Integer teamInfoId) {
		this.teamInfoId = teamInfoId;
	}

	public String getTeamInfoName() {
		return teamInfoName;
	}

	public void setTeamInfoName(String teamInfoName) {
		this.teamInfoName = teamInfoName;
	}

	public Integer getTeamMemberId() {
		return teamMemberId;
	}

	public void setTeamMemberId(Integer teamMemberId) {
		this.teamMemberId = teamMemberId;
	}

	public String getTeamMemberName() {
		return teamMemberName;
	}

	public void setTeamMemberName(String teamMemberName) {
		this.teamMemberName = teamMemberName;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	public Integer getReplaceTeamMemberId() {
		return replaceTeamMemberId;
	}

	public void setReplaceTeamMemberId(Integer replaceTeamMemberId) {
		this.replaceTeamMemberId = replaceTeamMemberId;
	}

	public String getReplaceTeamMemberName() {
		return replaceTeamMemberName;
	}

	public void setReplaceTeamMemberName(String replaceTeamMemberName) {
		this.replaceTeamMemberName = replaceTeamMemberName;
	}

	public String getFormation() {
		return formation;
	}

	public void setFormation(String formation) {
		this.formation = formation;
	}

	public Boolean getIsHaveInfo() {
		return isHaveInfo;
	}

	public void setIsHaveInfo(Boolean isHaveInfo) {
		this.isHaveInfo = isHaveInfo;
	}

}
