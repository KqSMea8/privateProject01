package com.project.service.proxy;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.project.common.page.PageForApp;
import com.project.service.entity.User;
import com.project.service.reqentity.receive.ReceiveSaveReqEntity;
import com.project.service.reqentity.team.TeamSaveReqEntity;
import com.project.service.reqentity.user.UserSaveReqEntity;

@Repository("mineProxy")
public interface MineProxy {
	// ==============================================用户相关=============================================================

	public Map<String, Object> getCenterInfo(@Param("userId") Integer userId);

	public Map<String, Object> userInfo(@Param("userId") Integer userId);

	public Integer updateUserInfo(@Param("paramEntity") UserSaveReqEntity paramEntity);

	public Integer userUpdateHeadImage(@Param("headImgUrl") String headImgUrl, @Param("userId") Integer userId);

	public Integer userUpdateMobile(@Param("mobile") String mobile, @Param("userId") Integer userId);

	public Integer authentication(@Param("credentialsName") String credentialsName, @Param("imgPath") String imgPath, @Param("userId") Integer userId);

	public boolean isHaveAllSave(@Param("userId") Integer userId);

	// ==============================================收货地址相关=============================================================
	public List<Map<String, Object>> receiveList(@Param("userId") Integer userId);

	public Integer createReceive(@Param("paramEntity") ReceiveSaveReqEntity paramEntity, @Param("user") User user);

	public Integer editReceive(@Param("paramEntity") ReceiveSaveReqEntity paramEntity, @Param("user") User user);

	// ==============================================球队相关=============================================================

	public List<Map<String, Object>> teamList(@Param("userId") Integer userId);

	public Integer createTeam(@Param("paramEntity") TeamSaveReqEntity paramEntity, @Param("user") User user);

	public Integer updateTeam(@Param("paramEntity") TeamSaveReqEntity paramEntity, @Param("user") User user);

	public Map<String, Object> teamInfo(@Param("teamId") Integer teamId, @Param("userId") Integer userId);

	public Map<String, Object> getTeamCount(@Param("teamId") Integer teamId);

	public Map<String, Object> matchScheduleInfo(@Param("teamId") Integer teamId, @Param("userId") Integer userId);

	public Integer getApplyAuditStatus(@Param("teamId") Integer teamId, @Param("userId") Integer userId);

	public Integer teamMemberApply(@Param("teamId") Integer teamId, @Param("userId") Integer userId, @Param("roleType") Integer roleType);

	public Map<String, Object> teamInfoMore(@Param("teamId") Integer teamId, @Param("year") String year);

	public Integer totalJoinUser(@Param("teamId") Integer teamId, @Param("year") String year);

	public List<Map<String, Object>> matchScheduleList(@Param("teamId") Integer teamId, @Param("year") String year);

	public Integer getRoleType(@Param("teamId") Integer teamId, @Param("userId") Integer userId);

	public List<Map<String, Object>> applyUserList(@Param("teamId") Integer teamId);

	public List<Map<String, Object>> memberList(@Param("teamId") Integer teamId, @Param("order") Integer order, @Param("sort") String sort);

	public Integer settingRole(@Param("teamMemberId") Integer teamMemberId, @Param("roleType") Integer roleType);

	public List<Map<String, Object>> listEdplayers(@Param("teamId") Integer teamId, @Param("userId") Integer userId);

	public Map<String, Object> teamMemberInfo(@Param("teamMemberId") Integer teamMemberId);

	public Integer updateMemberStatus(@Param("teamMemberId") Integer teamMemberId, @Param("status") Integer status, @Param("userId") Integer userId);

	public List<Map<String, Object>> teamAlbumList(@Param("teamId") Integer teamId);

	public List<Map<String, Object>> albumList(@Param("userId") Integer userId, @Param("startDate") String startDate, @Param("endDate") String endDate);

	// ==============================================商品订单相关=============================================================

	public List<Map<String, Object>> orderList(@Param("page") PageForApp page, @Param("type") Integer type, @Param("userId") Integer userId);

	// ==============================================约战相关=============================================================

	public List<Map<String, Object>> pklist(@Param("page") PageForApp page, @Param("userId") Integer userId);
	// ==============================================关注相关=============================================================

	public Integer follow(@Param("followInfoId") Integer followInfoId, @Param("type") Integer type, @Param("userId") Integer userId);

	public List<Map<String, Object>> followMatchlist(@Param("page") PageForApp page, @Param("userId") Integer userId);

	public List<Map<String, Object>> followTeamlist(@Param("page") PageForApp page, @Param("userId") Integer userId);

	public List<Map<String, Object>> followMemberlist(@Param("page") PageForApp page, @Param("userId") Integer userId);
	// ==============================================我的游戏=============================================================

	public List<Map<String, Object>> gamelist(@Param("page") PageForApp page, @Param("status") Integer status, @Param("userId") Integer userId);

}
