package com.project.service.proxy;

import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository("mineProxy")
public interface MineProxy {

	public Map<String, Object> getCenterInfo(@Param("userId") Integer userId);

}
