package com.project.service.proxy;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.project.common.page.PageForApp;
import com.project.service.entity.User;
import com.project.service.reqentity.match.AddEventrealityReqEntity;
import com.project.service.reqentity.match.MatchSelectReqEntity;
import com.project.service.reqentity.match.SaveFormationLeaderReqEntity;
import com.project.service.reqentity.match.SaveFormationReqEntity;

@Repository("matchProxy")
public interface MatchProxy {

	public List<Map<String, Object>> matchlist(@Param("page") PageForApp page, @Param("paramEntity") MatchSelectReqEntity paramEntity);

	public Map<String, Object> matchinfo(@Param("matchInfoId") Integer matchInfoId, @Param("userId") Integer userId);

	public Map<String, Object> matchEndInfo(@Param("matchInfoId") Integer matchInfoId);

	public Map<String, Object> marchRuningInfo(@Param("matchInfoId") Integer matchInfoId, @Param("userId") Integer userId);

	public List<String> matchinfoImagelist(@Param("matchInfoId") Integer matchInfoId, @Param("teamId") Integer teamId, @Param("teamMemberId") Integer teamMemberId);

	public List<Map<String, Object>> matchinfoVideolist(@Param("matchInfoId") Integer matchInfoId, @Param("teamId") Integer teamId, @Param("teamMemberId") Integer teamMemberId);

	public Map<String, Object> matchinfoVideoInfo(@Param("matchInfoId") Integer matchInfoId, @Param("videoId") Integer videoId);

	public List<Map<String, Object>> videoInfoCommentlist(@Param("page") PageForApp page, @Param("videoId") Integer videoId);

	public Integer videoInfoComment(@Param("commentContent") String commentContent, @Param("videoId") Integer videoId, @Param("userId") Integer userId);

	public boolean isHaveApply(@Param("teamId") Integer teamId, @Param("matchInfoId") Integer matchInfoId);

	public Integer matchApply(@Param("teamId") Integer teamId, @Param("matchInfoId") Integer matchInfoId, @Param("contacts") String contacts, @Param("contactsTel") String contactsTel);

	// ==============================================播报相关==================================================
	public List<Map<String, Object>> getMemberList(@Param("teamId") Integer teamId, @Param("matchScheduleId") Integer matchScheduleId);

	public Integer addEventreality(@Param("paramEntity") AddEventrealityReqEntity paramEntity, @Param("user") User user);

	public List<Map<String, Object>> eventRealityList(@Param("matchScheduleId") Integer matchScheduleId);

	// ==============================================赛事详情下半部分==================================================
	public List<Map<String, Object>> teamList(@Param("matchInfoId") Integer matchInfoId);

	public List<Map<String, Object>> scorelist(@Param("matchInfoId") Integer matchInfoId);

	public List<Map<String, Object>> scheduleList(@Param("matchInfoId") Integer matchInfoId, @Param("roundNumber") Integer roundNumber, @Param("date") String date);

	public List<Map<String, Object>> memberList(@Param("matchInfoId") Integer matchInfoId, @Param("type") Integer type);

	public Map<String, Object> memberInfo(@Param("teamMemberId") Integer teamMemberId, @Param("userId") Integer userId);

	public Map<String, Object> memberInfoCount(@Param("teamMemberId") Integer teamMemberId);

	public List<Map<String, Object>> memberTeamList(@Param("teamMemberId") Integer teamMemberId);

	// ==============================================裁判设置阵型相关==================================================
	public Map<String, Object> formationInfo(@Param("scheduleId") Integer scheduleId, @Param("type") Integer type);

	public List<Map<String, Object>> firstMemberList(@Param("scheduleId") Integer scheduleId, @Param("type") Integer type);

	public List<Map<String, Object>> substitutesMemberList(@Param("scheduleId") Integer scheduleId, @Param("type") Integer type);

	public Integer saveFormation(@Param("paramEntity") SaveFormationReqEntity paramEntity);

	public Integer deleteFirstMemberList(@Param("scheduleId") Integer scheduleId, @Param("type") Integer type);

	public Integer saveFirstMemberList(@Param("scheduleId") Integer scheduleId, @Param("formation") String formation, @Param("firstMemberStr") String firstMemberStr, @Param("type") Integer type,
			@Param("userId") Integer userId);

	public Integer getType(@Param("scheduleId")Integer scheduleId, @Param("userId")Integer userId);

	public Integer saveFormationLeader(@Param("paramEntity")SaveFormationLeaderReqEntity paramEntity, @Param("type")Integer type);

}
