package com.project.service.proxy;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.project.common.page.PageForApp;

@Repository("gameProxy")
public interface GameProxy {

	public List<Map<String, Object>> list(@Param("page") PageForApp page, @Param("gameStatus") Integer gameStatus, @Param("searchKey") String searchKey);

	public Map<String, Object> info(@Param("gameInfoId") Integer gameInfoId, @Param("userId") Integer userId);

	public List<Map<String, Object>> scoreOddsList(@Param("gameInfoId") Integer gameInfoId, @Param("oddsType") Integer oddsType);

	public boolean isHaveEnoughAmount(@Param("amount") BigDecimal amount, @Param("userId") Integer userId);

	public Integer gambling(@Param("gameInfoId") Integer gameInfoId, @Param("oddsId") Integer oddsId, @Param("type") Integer type, @Param("amount") BigDecimal amount, @Param("odds") BigDecimal odds,
			@Param("estimate") BigDecimal estimate, @Param("userId") Integer userId);

}
