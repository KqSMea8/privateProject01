package com.project.service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.project.common.annotation.interfaces.CheckParam;
import com.project.common.annotation.interfaces.CheckToken;
import com.project.common.base.BaseController;
import com.project.common.base.BaseResult;
import com.project.service.service.PlayerKillingService;

/**
 * 约战相关
 */
@RestController
@RequestMapping("/server/match/pk")
public class PlayerKillingController extends BaseController {

	@Autowired
	private PlayerKillingService plKillingService;

	/*
	 * 约战列表
	 */
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	public BaseResult list(String params) {
		return plKillingService.list(params);
	}

	/*
	 * 约战详情
	 */
	@RequestMapping(value = "/info", method = RequestMethod.POST)
	public BaseResult info(String token, String params) {
		return plKillingService.info(getUserByToken(token), getJSONObjectParams(params));
	}

	/*
	 * 发起/编辑约战
	 */
	@CheckToken
	@RequestMapping(value = "/release", method = RequestMethod.POST)
	public BaseResult release(@CheckParam String token, String params) {
		return plKillingService.release(getUserByToken(token), params);
	}

	/*
	 * 约战详情
	 */
	@CheckToken
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public BaseResult delete(@CheckParam String token, String params) {
		return plKillingService.delete(getUserByToken(token), getJSONObjectParams(params));
	}

	/*
	 * 确认应战
	 */
	@CheckToken
	@RequestMapping(value = "/confirm", method = RequestMethod.POST)
	public BaseResult confirm(@CheckParam String token, String params) {
		return plKillingService.confirm(getUserByToken(token), getJSONObjectParams(params));
	}

	/*
	 * 约战申请
	 */
	@CheckToken
	@RequestMapping(value = "/apply", method = RequestMethod.POST)
	public BaseResult apply(@CheckParam String token, String params) {
		return plKillingService.apply(getUserByToken(token), params);
	}
}
