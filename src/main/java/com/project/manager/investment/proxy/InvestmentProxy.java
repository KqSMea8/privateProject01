package com.project.manager.investment.proxy;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.project.common.jqgrid.JQGridPageParams;
import com.project.manager.system.entity.SystemOperatorEntity;

@Repository("investmentProxy")
public interface InvestmentProxy {

	public List<Map<String, Object>> getInvestmentList(@Param("pageParams") JQGridPageParams pageParams, @Param("status") Integer status, @Param("type") Integer type);

	public long getInvestmentListCount(@Param("status") Integer status, @Param("type") Integer type);

	public Integer updateStatus(@Param("investmentIds") String investmentIds);

	public Integer delete(@Param("investmentIds") String investmentIds);

	public Integer addInfo(@Param("name") String name, @Param("content") String content, @Param("status") Integer status, @Param("minDay") Integer minDay, @Param("startMoney") BigDecimal startMoney,
			@Param("earnings") BigDecimal earnings, @Param("operator") SystemOperatorEntity operator);

	public Integer updateInfo(@Param("investmentId") Integer investmentId, @Param("name") String name, @Param("content") String content, @Param("status") Integer status,
			@Param("minDay") Integer minDay, @Param("startMoney") BigDecimal startMoney, @Param("earnings") BigDecimal earnings);

	/*
	 * 查询是否已经有交易记录
	 */
	public Integer isHaveDeal(@Param("investmentIds") String investmentIds);

	// ===============================================================定时任务=================================================================
	/*
	 * 插入结算日
	 */
	public void calculationEarningsSettlement();

	/*
	 * 计算之前所有收益的收益额插入收益表
	 */
	public Integer calculationEarningsByEarnings();

	/*
	 * 计算本金的收益额插入收益表
	 */
	public Integer calculationEarningsByPrincipal();
	// ===============================================================定时任务=================================================================

}
