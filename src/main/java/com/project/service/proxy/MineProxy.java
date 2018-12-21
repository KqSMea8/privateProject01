package com.project.service.proxy;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.project.service.entity.User;
import com.project.service.reqentity.receive.ReceiveSaveReqEntity;

@Repository("mineProxy")
public interface MineProxy {
	// ==============================================用户相关=============================================================

	public Map<String, Object> getCenterInfo(@Param("userId") Integer userId);

	// ==============================================收货地址相关=============================================================
	public List<Map<String, Object>> receiveList(@Param("userId") Integer userId);

	public Integer createReceive(@Param("paramEntity") ReceiveSaveReqEntity paramEntity, @Param("user") User user);

	public Integer editReceive(@Param("paramEntity") ReceiveSaveReqEntity paramEntity, @Param("user") User user);

	// ==============================================球队相关=============================================================

	public List<Map<String, Object>> teamList(@Param("userId") Integer userId);

	public Map<String, Object> teamInfo(@Param("teamId") Integer teamId);

	public Map<String, Object> getTeamCount(@Param("teamId") Integer teamId);

	public Map<String, Object> matchScheduleInfo(@Param("teamId") Integer teamId);
}
