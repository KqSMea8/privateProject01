package com.project.manager.competition.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.common.base.BaseResult;
import com.project.common.base.BaseService;
import com.project.common.jqgrid.JQGridPageParams;
import com.project.common.jqgrid.JQGridResultEntity;
import com.project.common.shiro.ShiroUtil;
import com.project.manager.competition.proxy.CompetitionProxy;
import com.project.manager.system.entity.SystemOperatorEntity;

@Service("competitionService")
public class CompetitionService extends BaseService {

	@Autowired
	CompetitionProxy competitionProxy;

	@Autowired
	CompetitionEarningsService competitionEarningsService;

	public JQGridResultEntity<Map<String, Object>> getCompetitionList(JQGridPageParams pageParams, Integer status) {
		JQGridResultEntity<Map<String, Object>> result = new JQGridResultEntity<Map<String, Object>>();
		// 查看列表
		List<Map<String, Object>> all = competitionProxy.getCompetitionList(pageParams, status);
		// 总条数
		long totalRecords = competitionProxy.getCompetitionListCount(status);
		return fillJQGrid(result, all, totalRecords, pageParams);
	}

	public BaseResult updateStatus(String competitionIds) {
		try {
			competitionProxy.updateStatus(competitionIds);
			return generateResult(true, "修改状态成功!");
		} catch (Exception e) {
			e.printStackTrace();
			return generateResult(false, "系统繁忙,请稍后再试!");
		}
	}

	public BaseResult delete(String competitionIds) {
		try {
			String competitionSubIds = competitionProxy.getCompetitionSubIds(competitionIds);
			competitionProxy.returnUserDeal(competitionSubIds);
			competitionProxy.delete(competitionIds);
			return generateResult(true, "删除成功!");
		} catch (Exception e) {
			e.printStackTrace();
			return generateResult(false, "系统繁忙,请稍后再试!");
		}
	}

	public BaseResult cancel(String competitionIds) {
		try {
			String competitionSubIds = competitionProxy.getCompetitionSubIds(competitionIds);
			competitionProxy.returnUserDeal(competitionSubIds);
			competitionProxy.cancel(competitionIds);
			return generateResult(true, "删除成功!");
		} catch (Exception e) {
			e.printStackTrace();
			return generateResult(false, "系统繁忙,请稍后再试!");
		}
	}

	public Map<String, Object> getCompetitionInfo(Integer competitionId) {
		return competitionProxy.getCompetitionInfoById(competitionId);
	}

	public BaseResult save(Integer competitionId, String title, String title_th, String name1, String name1_th, String name2, String name2_th, String logo1Url, String logo2Url, String gameBeginTime, Integer status) {
		try {
			if (competitionId == null) {
				SystemOperatorEntity operator = ShiroUtil.getOperatorInfo();
				competitionProxy.add(title, title_th, name1,name1_th, name2,name2_th, logo1Url, logo2Url, gameBeginTime, status, operator);
			} else {
				competitionProxy.update(competitionId, title, title_th, name1,name1_th, name2,name2_th, logo1Url, logo2Url, gameBeginTime, status);
			}
			return generateResult(true, "保存成功!");
		} catch (Exception e) {
			e.printStackTrace();
			return generateResult(false, "系统繁忙,请稍后再试!");
		}
	}

	public BaseResult setResult(final Integer competitionId, Integer halfGameResult1, Integer halfGameResult2, Integer gameResult1, Integer gameResult2) {
		try {
			SystemOperatorEntity operator = ShiroUtil.getOperatorInfo();
			competitionProxy.setResult(competitionId, halfGameResult1, halfGameResult2, gameResult1, gameResult2, operator);

			// 计算收益
			new Thread(new Runnable() {
				public void run() {
					try {
						competitionEarningsService.calculationIncome(competitionId);
					} catch (Exception e) {
						// 还原为未设置比分
						competitionProxy.setResult(competitionId, null, null, null, null, null);
						e.printStackTrace();
					}
				}
			}).start();
			return generateResult(true, "保存成功!");
		} catch (Exception e) {
			e.printStackTrace();
			return generateResult(false, "系统繁忙,请稍后再试!");
		}
	}

	// ===============================================================================================

	public JQGridResultEntity<Map<String, Object>> getCompetitionSubList(JQGridPageParams pageParams, Integer competitionId, Integer isDecrease, Integer type) {
		JQGridResultEntity<Map<String, Object>> result = new JQGridResultEntity<Map<String, Object>>();
		// 查看列表
		List<Map<String, Object>> all = competitionProxy.getCompetitionSubList(pageParams, competitionId, isDecrease, type);
		// 总条数
		long totalRecords = competitionProxy.getCompetitionSubListCount(competitionId, isDecrease, type);
		return fillJQGrid(result, all, totalRecords, pageParams);
	}

	public BaseResult deleteSub(String competitionSubIds) {

		try {
			competitionProxy.returnUserDeal(competitionSubIds);
			competitionProxy.deleteSub(competitionSubIds);
			return generateResult(true, "删除成功!");
		} catch (Exception e) {
			e.printStackTrace();
			return generateResult(false, "系统繁忙,请稍后再试!");
		}
	}

	public BaseResult saveSub(Integer competitionSubId, Integer competitionId, Integer team1Result, Integer team2Result, Integer allResult, Integer type, Integer isDecrease, BigDecimal lossOdds,
			BigDecimal canDealTotalCount, BigDecimal decreaseNum, BigDecimal maxDealOneCount, Integer isBackPrincipal, Integer isOther) {
		try {
			if (competitionSubId == null) {
				SystemOperatorEntity operator = ShiroUtil.getOperatorInfo();
				competitionProxy.addSubInfo(competitionId, team1Result, team2Result, allResult, type, isDecrease, lossOdds, canDealTotalCount, decreaseNum, maxDealOneCount, isBackPrincipal, isOther,
						operator);
			} else {
				competitionProxy.updateSubInfo(competitionSubId, competitionId, team1Result, team2Result, allResult, type, isDecrease, lossOdds, canDealTotalCount, decreaseNum, maxDealOneCount,
						isBackPrincipal, isOther);
			}
			return generateResult(true, "保存成功!");
		} catch (Exception e) {
			e.printStackTrace();
			return generateResult(false, "系统繁忙,请稍后再试!");
		}
	}
}
