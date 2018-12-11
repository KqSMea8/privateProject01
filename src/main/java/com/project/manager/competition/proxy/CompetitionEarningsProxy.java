package com.project.manager.competition.proxy;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository("competitionEarningsProxy")
public interface CompetitionEarningsProxy {

	public Integer calculationCompetitionDealEarnings(@Param("competitionId") Integer competitionId);

	public Integer calculationCompetitionDealLossEarnings(@Param("competitionId") Integer competitionId);

	public Integer updateUserBalanceByCompetitionDeal(@Param("competitionId") Integer competitionId);

	public List<Integer> getAllUser(@Param("competitionId") Integer competitionId);

	public Integer calculationDirectEarnings(@Param("competitionId") Integer competitionId);

	public Integer updateUserBalanceByDirectEarnings(@Param("competitionId") Integer competitionId);

	/**
	 * 获取某一级别的用户的收益数据
	 * 
	 * @param userId
	 *            当前计算用户ID
	 * @param competitionId
	 *            球赛ID
	 * @param userIds
	 *            某一级别的用户ID字符串
	 * @param isReduce
	 *            是否减幅（0否、1是）
	 */
	public Map<String, Object> calculationGroupEarningsByCondition(@Param("userId") Integer userId, @Param("competitionId") Integer competitionId, @Param("userIds") String userIds,
			@Param("isReduce") int isReduce);

	public Integer getGroupList(@Param("userId") Integer userId);

	public String getNextLevelUserIds(@Param("userIds") String userIds);

	public Integer insertGroupEarningsData(@Param("userId") Integer userId, @Param("groupUserId") Integer groupUserId, @Param("groupNormalMoneyTotal") BigDecimal groupNormalMoneyTotal,
			@Param("groupNormalMoneyProportions") BigDecimal groupNormalMoneyProportions, @Param("groupNormalMoneyEearnings") BigDecimal groupNormalMoneyEearnings,
			@Param("groupHalfMoneyTotal") BigDecimal groupHalfMoneyTotal, @Param("groupHalfMoneyProportions") BigDecimal groupHalfMoneyProportions,
			@Param("groupHalfMoneyEearnings") BigDecimal groupHalfMoneyEearnings, @Param("halfGroupEaringsProportion") BigDecimal halfGroupEaringsProportion,
			@Param("allEarnings") BigDecimal allEarnings);

	public Integer backPrincipal(@Param("competitionId") Integer competitionId);

	public Integer settlementGroupEarnings();

	public Integer updateGroupEarningsStatus();

}
