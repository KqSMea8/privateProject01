package com.project.manager.competition.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.common.base.BaseService;
import com.project.common.util.StringUtil;
import com.project.manager.competition.proxy.CompetitionEarningsProxy;
import com.project.manager.competition.proxy.CompetitionProxy;
import com.project.manager.system.service.SystemService;

/*
 * 计算球赛相关收益数据
 */
@Service("competitionEarningsService")
public class CompetitionEarningsService extends BaseService {

	@Autowired
	CompetitionProxy competitionProxy;

	@Autowired
	CompetitionEarningsProxy competitionEarningsProxy;

	@Autowired
	SystemService systemService;

	Integer totalLevelGroupEarnings;// 团队计算总级数
	Integer halfGroupEaringsStartLevel;// 开始减幅级数
	BigDecimal halfGroupEaringsProportion;// 减幅比例

	/*
	 * 计算收益
	 */
	@Transactional(rollbackFor = Exception.class)
	public void calculationIncome(Integer competitionId) throws Exception {
		try {
			// 一、计算个人佣金
			this.calculationCompetitionDealEarnings(competitionId);

			// 二、计算直推佣金
			this.calculationDirectEarnings(competitionId);

			this.initParams();
			// 查询出所有用户
			List<Integer> userList = competitionEarningsProxy.getAllUser(competitionId);

			// 三、 计算团队佣金
			for (Integer userId : userList) {
				this.calculationGroupEarnings(userId, competitionId);
			}

			// 四、退还本金
			competitionEarningsProxy.backPrincipal(competitionId);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception();
		}
	}

	/*
	 * 计算个人佣金
	 */
	private void calculationCompetitionDealEarnings(Integer competitionId) {
		// 1、记录波胆盈利收益
		competitionEarningsProxy.calculationCompetitionDealEarnings(competitionId);

		// 2、记录波胆亏损收益
		competitionEarningsProxy.calculationCompetitionDealLossEarnings(competitionId);

		// 3、波胆收益入账
		competitionEarningsProxy.updateUserBalanceByCompetitionDeal(competitionId);
	}

	/*
	 * 计算直推佣金
	 */
	private void calculationDirectEarnings(Integer competitionId) {
		// 1、记录直推佣金记录
		competitionEarningsProxy.calculationDirectEarnings(competitionId);
		// 2、直推佣金入账
		competitionEarningsProxy.updateUserBalanceByDirectEarnings(competitionId);
	}

	/*
	 * 计算团队收益
	 */
	private void calculationGroupEarnings(Integer groupUserId, Integer competitionId) {
		Integer userId = competitionEarningsProxy.getGroupList(groupUserId);

		if (userId == null) {
			return;
		}
		// for (Integer groupUserId : groupList) {
		String userIds = groupUserId.toString();// 某一级别的用户集合字符串
		Map<String, Object> erverLevelEarningsData;
		BigDecimal groupNormalMoneyTotal = BigDecimal.ZERO;
		BigDecimal groupNormalMoneyProportions = BigDecimal.ZERO;
		BigDecimal groupNormalMoneyEearnings = BigDecimal.ZERO;
		BigDecimal groupHalfMoneyTotal = BigDecimal.ZERO;
		BigDecimal groupHalfMoneyProportions = BigDecimal.ZERO;
		BigDecimal groupHalfMoneyEearnings = BigDecimal.ZERO;
		BigDecimal allEarnings = BigDecimal.ZERO;

		for (int i = 0; i < totalLevelGroupEarnings - 1; i++) {
			if (StringUtil.isEmpty(userIds)) {
				break;
			}
			// isReduce的值是在 i大于等于(halfGroupEaringsStartLevel - 1 )级别的时候开始减幅
			erverLevelEarningsData = competitionEarningsProxy.calculationGroupEarningsByCondition(userId, competitionId, userIds, i >= halfGroupEaringsStartLevel - 1 ? 1 : 0);
			if (erverLevelEarningsData != null) {
				if (i >= halfGroupEaringsStartLevel - 1) {
					groupHalfMoneyTotal = groupHalfMoneyTotal.add(new BigDecimal(erverLevelEarningsData.get("total").toString()));
					groupHalfMoneyProportions = groupHalfMoneyProportions.equals(BigDecimal.ZERO)
							? new BigDecimal(erverLevelEarningsData.get("proportion") == null ? "0" : erverLevelEarningsData.get("proportion").toString())
							: groupHalfMoneyProportions;
					groupHalfMoneyEearnings = groupHalfMoneyEearnings
							.add(new BigDecimal(erverLevelEarningsData.get("totalEarngings") == null ? "0" : erverLevelEarningsData.get("totalEarngings").toString()));
					allEarnings = allEarnings.add(new BigDecimal(erverLevelEarningsData.get("totalEarngings") == null ? "0" : erverLevelEarningsData.get("totalEarngings").toString()));
				} else {
					groupNormalMoneyTotal = groupNormalMoneyTotal.add(new BigDecimal(erverLevelEarningsData.get("total").toString()));
					groupNormalMoneyProportions = groupNormalMoneyProportions.equals(BigDecimal.ZERO)
							? new BigDecimal(erverLevelEarningsData.get("proportion") == null ? "0" : erverLevelEarningsData.get("proportion").toString())
							: groupNormalMoneyProportions;
					groupNormalMoneyEearnings = groupNormalMoneyEearnings
							.add(new BigDecimal(erverLevelEarningsData.get("totalEarngings") == null ? "0" : erverLevelEarningsData.get("totalEarngings").toString()));
					allEarnings = allEarnings.add(new BigDecimal(erverLevelEarningsData.get("totalEarngings") == null ? "0" : erverLevelEarningsData.get("totalEarngings").toString()));
				}
			}

			userIds = competitionEarningsProxy.getNextLevelUserIds(userIds);
		}

		// 如果收益比例为0的话，表示该团队没有收益
		if (allEarnings.compareTo(BigDecimal.ZERO) > 0) {

			groupHalfMoneyProportions = groupHalfMoneyProportions.equals(BigDecimal.ZERO) ? groupHalfMoneyProportions.multiply(halfGroupEaringsProportion).setScale(4, BigDecimal.ROUND_HALF_UP)
					: groupHalfMoneyProportions;

			// 插入数据
			competitionEarningsProxy.insertGroupEarningsData(userId, groupUserId, groupNormalMoneyTotal, groupNormalMoneyProportions, groupNormalMoneyEearnings, groupHalfMoneyTotal,
					groupHalfMoneyProportions, groupHalfMoneyEearnings, halfGroupEaringsProportion, allEarnings);
		}
		// }
	}

	/*
	 * 初始化参数
	 */
	private void initParams() throws Exception {
		Map<String, Object> systemParams = systemService.getAllParams();
		totalLevelGroupEarnings = Integer.parseInt(systemParams.get("totalLevelGroupEarnings").toString());
		halfGroupEaringsStartLevel = Integer.parseInt(systemParams.get("halfGroupEaringsStartLevel").toString());
		halfGroupEaringsProportion = new BigDecimal(systemParams.get("halfGroupEaringsProportion").toString());
	}

	/*
	 * 团队解冻收益
	 */
	@Transactional(rollbackFor = Exception.class)
	public void settlementGroupEarnings() throws Exception {
		try {

			// 1、结算团队佣金
			competitionEarningsProxy.settlementGroupEarnings();

			// 2、修改状态
			competitionEarningsProxy.updateGroupEarningsStatus();
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception();
		}
	}
}
