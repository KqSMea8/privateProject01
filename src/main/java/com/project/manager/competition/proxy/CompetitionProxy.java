package com.project.manager.competition.proxy;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.project.common.jqgrid.JQGridPageParams;
import com.project.manager.system.entity.SystemOperatorEntity;

@Repository("competitionProxy")
public interface CompetitionProxy {

	public List<Map<String, Object>> getCompetitionList(@Param("pageParams") JQGridPageParams pageParams, @Param("status") Integer status);

	public long getCompetitionListCount(@Param("status") Integer status);

	public Integer updateStatus(@Param("competitionIds") String competitionIds);

	/*
	 * 查询所有比分ID
	 */
	public String getCompetitionSubIds(@Param("competitionIds") String competitionIds);

	public Integer delete(@Param("competitionIds") String competitionIds);

	public Integer cancel(@Param("competitionIds") String competitionIds);

	public Map<String, Object> getCompetitionInfoById(@Param("competitionId") Integer competitionId);

	public Integer add(@Param("title") String title,@Param("title_th") String title_th, @Param("name1") String name1, @Param("name1_th") String name1_th, @Param("name2") String name2,@Param("name2_th") String name2_th, @Param("logo1Url") String logo1Url, @Param("logo2Url") String logo2Url,
			@Param("gameBeginTime") String gameBeginTime, @Param("status") Integer status, @Param("operator") SystemOperatorEntity operator);

	public Integer update(@Param("competitionId") Integer competitionId, @Param("title") String title, @Param("title_th") String title_th, @Param("name1") String name1, @Param("name1_th") String name1_th, @Param("name2") String name2,@Param("name2_th") String name2_th, @Param("logo1Url") String logo1Url,
			@Param("logo2Url") String logo2Url, @Param("gameBeginTime") String gameBeginTime, @Param("status") Integer status);

	public Integer setResult(@Param("competitionId") Integer competitionId, @Param("halfGameResult1") Integer halfGameResult1, @Param("halfGameResult2") Integer halfGameResult2,
			@Param("gameResult1") Integer gameResult1, @Param("gameResult2") Integer gameResult2, @Param("operator") SystemOperatorEntity operator);

	// ============================================================================================================================

	public List<Map<String, Object>> getCompetitionSubList(@Param("pageParams") JQGridPageParams pageParams, @Param("competitionId") Integer competitionId, @Param("isDecrease") Integer isDecrease,
			@Param("type") Integer type);

	public long getCompetitionSubListCount(@Param("competitionId") Integer competitionId, @Param("isDecrease") Integer isDecrease, @Param("type") Integer type);

	/*
	 * 退还赛事交易金额
	 */
	public Integer returnUserDeal(@Param("competitionSubIds") String competitionSubIds);

	public Integer deleteSub(@Param("competitionSubIds") String competitionSubIds);

	public Integer addSubInfo(@Param("competitionId") Integer competitionId, @Param("team1Result") Integer team1Result, @Param("team2Result") Integer team2Result,
			@Param("allResult") Integer allResult, @Param("type") Integer type, @Param("isDecrease") Integer isDecrease, @Param("lossOdds") BigDecimal lossOdds,
			@Param("canDealTotalCount") BigDecimal canDealTotalCount, @Param("decreaseNum") BigDecimal decreaseNum, @Param("maxDealOneCount") BigDecimal maxDealOneCount,
			@Param("isBackPrincipal") Integer isBackPrincipal, @Param("isOther") Integer isOther, @Param("operator") SystemOperatorEntity operator);

	public Integer updateSubInfo(@Param("competitionSubId") Integer competitionSubId, @Param("competitionId") Integer competitionId, @Param("team1Result") Integer team1Result,
			@Param("team2Result") Integer team2Result, @Param("allResult") Integer allResult, @Param("type") Integer type, @Param("isDecrease") Integer isDecrease,
			@Param("lossOdds") BigDecimal lossOdds, @Param("canDealTotalCount") BigDecimal canDealTotalCount, @Param("decreaseNum") BigDecimal decreaseNum,
			@Param("maxDealOneCount") BigDecimal maxDealOneCount, @Param("isBackPrincipal") Integer isBackPrincipal, @Param("isOther") Integer isOther);

}
