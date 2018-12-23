package com.project.service.reqentity.match;

import com.project.common.annotation.aops.ValidateParam;
import com.project.common.util.validate.AbstractParameter;

/**
 * 约战列表请求参数实体
 */
public class AddEventrealityReqEntity extends AbstractParameter {

	private static final long serialVersionUID = -9179454675492903613L;

	@ValidateParam(tip = "赛事ID不得为空")
	Integer matchInfoId;// 第一层筛选条件

	@ValidateParam(tip = "球队球员ID不得为空")
	Integer teamMemberId;// 第一层筛选条件

	@ValidateParam(tip = "播报类型不得为空")
	Integer type;

	String content;

	String imgUrl;

	Integer replaceTeamMemberId;

	public Integer getMatchInfoId() {
		return matchInfoId;
	}

	public void setMatchInfoId(Integer matchInfoId) {
		this.matchInfoId = matchInfoId;
	}

	public Integer getTeamMemberId() {
		return teamMemberId;
	}

	public void setTeamMemberId(Integer teamMemberId) {
		this.teamMemberId = teamMemberId;
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
}
