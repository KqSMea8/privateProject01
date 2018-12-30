package com.project.service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.project.common.annotation.interfaces.CheckParam;
import com.project.common.annotation.interfaces.CheckToken;
import com.project.common.base.BaseController;
import com.project.common.base.BaseResult;
import com.project.service.service.GameService;

/**
 * 竞猜相关
 */
@RestController
@RequestMapping("/server/match/game")
public class GameController extends BaseController {

	@Autowired
	private GameService gameService;

	/*
	 * 竞猜页面详情
	 */
	@RequestMapping(value = "list", method = RequestMethod.POST)
	public BaseResult list(String params) {
		return gameService.list(getJSONObjectParams(params));
	}

	/*
	 * 竞猜页面详情
	 */
	@RequestMapping(value = "info", method = RequestMethod.POST)
	public BaseResult info(String token, String params) {
		return gameService.info(getUserByToken(token), getJSONObjectParams(params));
	}

	/*
	 * 发起竞猜
	 */
	@CheckToken
	@RequestMapping(value = "gambling", method = RequestMethod.POST)
	public BaseResult gambling(@CheckParam() String token, String params) {
		return gameService.gambling(getUserByToken(token), getJSONObjectParams(params));
	}
}
