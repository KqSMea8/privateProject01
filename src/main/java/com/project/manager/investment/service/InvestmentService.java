package com.project.manager.investment.service;

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
import com.project.manager.investment.proxy.InvestmentProxy;
import com.project.manager.system.entity.SystemOperatorEntity;

@Service("investmentService")
public class InvestmentService extends BaseService {

	@Autowired
	InvestmentProxy investmentProxy;

	public JQGridResultEntity<Map<String, Object>> getInvestmentList(JQGridPageParams pageParams, Integer status, Integer type) {
		JQGridResultEntity<Map<String, Object>> result = new JQGridResultEntity<Map<String, Object>>();
		// 查看列表
		List<Map<String, Object>> all = investmentProxy.getInvestmentList(pageParams, status, type);
		// 总条数
		long totalRecords = investmentProxy.getInvestmentListCount(status, type);
		return fillJQGrid(result, all, totalRecords, pageParams);
	}

	public BaseResult updateStatus(String investmentIds) {
		try {
			if (investmentProxy.isHaveDeal(investmentIds).intValue() > 0) {
				return generateResult(false, "您选择的产品中有已经存在交易的产品,请重新选择!");
			}
			investmentProxy.updateStatus(investmentIds);
			return generateResult(true, "修改状态成功!");
		} catch (Exception e) {
			e.printStackTrace();
			return generateResult(false, "系统繁忙,请稍后再试!");
		}
	}

	public BaseResult delete(String investmentIds) {
		try {
			if (investmentProxy.isHaveDeal(investmentIds).intValue() > 0) {
				return generateResult(false, "您选择的产品中有已经存在交易的产品,请重新选择!");
			}
			investmentProxy.delete(investmentIds);
			return generateResult(true, "删除成功!");
		} catch (Exception e) {
			e.printStackTrace();
			return generateResult(false, "系统繁忙,请稍后再试!");
		}
	}

	public BaseResult save(Integer investmentId, String name, String content, Integer status, Integer minDay, BigDecimal startMoney, BigDecimal earnings) {
		try {
			if (investmentId == null) {
				SystemOperatorEntity operator = ShiroUtil.getOperatorInfo();
				investmentProxy.addInfo(name, content, status, minDay, startMoney, earnings, operator);
			} else {
				investmentProxy.updateInfo(investmentId, name, content, status, minDay, startMoney, earnings);
			}
			return generateResult(true, "保存成功!");
		} catch (Exception e) {
			e.printStackTrace();
			return generateResult(false, "系统繁忙,请稍后再试!");
		}
	}

	/*
	 * 定时任务计算每天理财产品收益
	 */
	public void calculationEarnings() {
		/**
		 * 理财通中转入后是否可以转出， 是否需要手动转出？（利滚利）[最低购买限制] <br>
		 * a.可转出， 没到设置时间没有收益 ， 超过设置时间， 算收益{有几天算几天}<br>
		 * b.周期：计算收益的开始时间，（多少天后开始计算收益）<br>
		 * c.手动转出
		 */
		// 必须先计算收益的收益，在计算本金的收益，这样不会多计算一次今天的收益的利润
		// 1、在收益表中插入结算日数据
		investmentProxy.calculationEarningsSettlement();
		// 2、计算之前所有收益的收益额插入收益表
		investmentProxy.calculationEarningsByEarnings();
		// 3、计算本金的收益额插入收益表
		investmentProxy.calculationEarningsByPrincipal();
	}
}
