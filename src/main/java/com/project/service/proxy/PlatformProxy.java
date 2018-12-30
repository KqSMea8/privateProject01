package com.project.service.proxy;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.project.service.entity.User;

@Repository("platformProxy")
public interface PlatformProxy {

	public User getUserByOpenId(@Param("openId") String openId);

	public Integer createUserIdByOpenId(@Param("user") User user);

	public Integer updateUserWxUserId(@Param("user") User user);

	public List<Map<String, Object>> getCityList(@Param("type") int type);

	public List<Map<String, Object>> screeTeamForApply(@Param("userId") Integer userId);

	public List<Map<String, Object>> screeTeam(@Param("matchInfoId") Integer matchInfoId);

	public List<Map<String, Object>> screeMember(@Param("teamId") Integer teamId, @Param("matchInfoId") Integer matchInfoId);

	public Integer diamondsRecharge(@Param("rechargeTotal") BigDecimal rechargeTotal, @Param("userId") Integer userId);

	public Integer userRecharge(@Param("rechargeTotal") BigDecimal rechargeTotal, @Param("userId") Integer userId, @Param("type") Integer type);

	public boolean totalContinuousSign(@Param("userId") Integer userId);

}
