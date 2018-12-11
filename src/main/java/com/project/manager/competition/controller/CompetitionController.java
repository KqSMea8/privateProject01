package com.project.manager.competition.controller;

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
import com.project.manager.competition.service.CompetitionService;

/*
 * 赛事相关业务
 */
@Controller
@RequestMapping("/competition")
public class CompetitionController extends BaseController {

	@Autowired
	CompetitionService competitionService;

	/*
	 * 跳转赛事页面
	 */
	@RequestMapping("competitionindex")
	@ResponseBody
	public ModelAndView competitionIndex(ModelAndView mv) {
		mv.setViewName("competition/competitionindex");
		return mv;
	}

	/*
	 * 获取赛事列表
	 */
	@RequestMapping("getcompetitionlist")
	@ResponseBody
	public JQGridResultEntity<Map<String, Object>> getCompetitionList(JQGridPageParams pageParams, Integer status) {
		return competitionService.getCompetitionList(pageParams, status);
	}

	/*
	 * 修改状态
	 */
	@RequestMapping("updatestatus")
	@ResponseBody
	public BaseResult updateStatus(String competitionIds) {
		return competitionService.updateStatus(competitionIds);
	}

	/*
	 * 删除赛事
	 */
	@RequestMapping("delete")
	@ResponseBody
	public BaseResult delete(String competitionIds) {
		return competitionService.delete(competitionIds);
	}

	/*
	 * 取消赛事
	 */
	@RequestMapping("cancel")
	@ResponseBody
	public BaseResult cancel(String competitionIds) {
		return competitionService.cancel(competitionIds);
	}

	/*
	 * 跳转
	 */
	@RequestMapping("toedit")
	public ModelAndView toEdit(ModelAndView mv, Integer competitionId) {

		if (competitionId == null) {
			mv.addObject("actionType", "add");
		} else {
			mv.addObject("competitionInfo", competitionService.getCompetitionInfo(competitionId));
			mv.addObject("actionType", "edit");
		}
		mv.setViewName("competition/competitionedit");
		return mv;
	}

	/*
	 * 保存赛事
	 */
	@RequestMapping("save")
	@ResponseBody
	public BaseResult save(Integer competitionId, String title, String title_th, String name1, String name1_th, String name2, String name2_th, String logo1Url, String logo2Url, String gameBeginTime, Integer status) {
		return competitionService.save(competitionId, title, title_th, name1, name1_th, name2, name2_th, logo1Url, logo2Url, gameBeginTime, status);
	}

	/*
	 * 录入结果
	 */
	@RequestMapping("setresult")
	@ResponseBody
	public BaseResult setResult(Integer competitionId, Integer halfGameResult1, Integer halfGameResult2, Integer gameResult1, Integer gameResult2) {
		return competitionService.setResult(competitionId, halfGameResult1, halfGameResult2, gameResult1, gameResult2);
	}

	// ===============================================================================================
	/*
	 * 跳转赛事比分列表页面
	 */
	@RequestMapping("competitionsubindex")
	@ResponseBody
	public ModelAndView competitionSubIndex(ModelAndView mv, Integer competitionId, String vsName) {
		mv.addObject("competitionId", competitionId);
		mv.addObject("vsName", vsName);
		mv.setViewName("competition/competitionsubindex");
		return mv;
	}

	/*
	 * 获取赛事比分列表
	 */
	@RequestMapping("getcompetitionsublist")
	@ResponseBody
	public JQGridResultEntity<Map<String, Object>> getCompetitionSubList(JQGridPageParams pageParams, Integer competitionId, Integer isDecrease, Integer type) {
		return competitionService.getCompetitionSubList(pageParams, competitionId, isDecrease, type);
	}

	/*
	 * 删除赛事比分
	 */
	@RequestMapping("deletesub")
	@ResponseBody
	public BaseResult deleteSub(String competitionSubIds) {
		return competitionService.deleteSub(competitionSubIds);
	}

	/*
	 * 保存赛事比分
	 */
	@RequestMapping("savesub")
	@ResponseBody
	public BaseResult saveSub(Integer competitionSubId, Integer competitionId, Integer team1Result, Integer team2Result, Integer allResult, Integer type, Integer isDecrease, BigDecimal lossOdds,
			BigDecimal canDealTotalCount, BigDecimal decreaseNum, BigDecimal maxDealOneCount, Integer isBackPrincipal, Integer isOther) {
		return competitionService.saveSub(competitionSubId, competitionId, team1Result, team2Result, allResult, type, isDecrease, lossOdds, canDealTotalCount, decreaseNum, maxDealOneCount,
				isBackPrincipal,isOther);
	}
}
