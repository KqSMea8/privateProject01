package com.project.service.proxy;

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

	public List<Map<String, Object>> screeTeam(@Param("matchInfoId") String matchInfoId);

	public List<Map<String, Object>> screeMember(@Param("teamId") String teamId);

}
