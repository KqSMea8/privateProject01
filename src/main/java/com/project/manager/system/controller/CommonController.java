package com.project.manager.system.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.project.common.base.BaseController;
import com.project.manager.system.entity.CommonComboEntity;
import com.project.manager.system.service.CommonService;

/**
 * 公共请求控制器
 * 
 */
@Controller
@RequestMapping("/common")
public class CommonController extends BaseController {
	@Autowired
	private CommonService commonService;

	/*
	 * 获取禁用、启用状态下拉
	 */
	@RequestMapping("getstatuscombo")
	@ResponseBody
	public List<CommonComboEntity> getStatusCombo() {
		return commonService.getStatusCombo();
	}

	/*
	 * 会员状态下拉
	 */
	@RequestMapping("getmemberuserstatus")
	@ResponseBody
	public List<CommonComboEntity> getMemberUserStatusCombo() {
		return commonService.getMemberUserStatusCombo();
	}

	/*
	 * 审核状态下拉
	 */
	@RequestMapping("getauditstatus")
	@ResponseBody
	public List<CommonComboEntity> getauditstatusCombo() {
		return commonService.getauditstatusCombo();
	}

	/*
	 * 公告类型下拉
	 */
	@RequestMapping("getnoticetype")
	@ResponseBody
	public List<CommonComboEntity> getNoticeType() {
		return commonService.getNoticeTypeCombo();
	}

	/*
	 * 赛事比分类型下拉
	 */
	@RequestMapping("getcompetitionsubtype")
	@ResponseBody
	public List<CommonComboEntity> getCompetitionSubType() {
		return commonService.getCompetitionSubTypeCombo();
	}

	/*
	 * 是否下拉
	 */
	@RequestMapping("getyesno")
	@ResponseBody
	public List<CommonComboEntity> getYesNoType() {
		return commonService.getYesNoTypeCombo();
	}

	/*
	 * 获取会员等级
	 */
	@RequestMapping("getmemberuserlevel")
	@ResponseBody
	public List<CommonComboEntity> getMemberUserLevel() {
		return commonService.getMemberUserLevel();
	}
}
