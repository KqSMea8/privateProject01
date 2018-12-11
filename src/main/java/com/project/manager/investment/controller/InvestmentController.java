package com.project.manager.investment.controller;

import java.math.BigDecimal;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.project.common.base.BaseController;
import com.project.common.base.BaseResult;
import com.project.common.jqgrid.JQGridPageParams;
import com.project.common.jqgrid.JQGridResultEntity;
import com.project.manager.investment.service.InvestmentService;

/*
 * 理财通相关业务
 */
@Controller
@RequestMapping("/investment")
public class InvestmentController extends BaseController {

	@Autowired
	InvestmentService investmentService;

	/*
	 * 跳转理财通管理页面
	 */
	@RequestMapping("investmentindex")
	@ResponseBody
	public ModelAndView investmentIndex(ModelAndView mv, String type) {
		mv.setViewName("investment/investmentindex");
		return mv;
	}

	/*
	 * 获取理财通列表
	 */
	@RequestMapping("getinvestmentlist")
	@ResponseBody
	public JQGridResultEntity<Map<String, Object>> getInvestmentList(JQGridPageParams pageParams, Integer status, Integer type) {
		return investmentService.getInvestmentList(pageParams, status, type);
	}

	/*
	 * 修改状态
	 */
	@RequestMapping("updatestatus")
	@ResponseBody
	public BaseResult updateStatus(String investmentIds) {
		return investmentService.updateStatus(investmentIds);
	}

	/*
	 * 删除理财通
	 */
	@RequestMapping("delete")
	@ResponseBody
	public BaseResult delete(String investmentIds) {
		return investmentService.delete(investmentIds);
	}

	/*
	 * 保存理财产品信息
	 */
	@RequestMapping("save")
	@ResponseBody
	public BaseResult save(Integer investmentId, String name, String content, Integer status, Integer minDay, BigDecimal startMoney, BigDecimal earnings) {
		return investmentService.save(investmentId, name, content, status, minDay, startMoney, earnings);
	}
}
