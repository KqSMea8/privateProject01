package com.project.service.proxy;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.project.common.page.PageForApp;
import com.project.service.entity.User;
import com.project.service.reqentity.match.AddEventrealityReqEntity;
import com.project.service.reqentity.match.MatchSelectReqEntity;

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

	public Integer matchApply(@Param("teamId") Integer teamId, @Param("matchInfoId") Integer matchInfoId, @Param("contacts") String contacts, @Param("contactsTel") String contactsTel);

	public List<Map<String, Object>> getMemberList(@Param("teamId") Integer teamId, @Param("matchScheduleId") Integer matchScheduleId);

	public Integer addEventreality(AddEventrealityReqEntity paramEntity, User user);

	public List<Map<String, Object>> eventRealityList(Integer matchScheduleId);

}
