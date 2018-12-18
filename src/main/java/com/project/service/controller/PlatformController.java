package com.project.service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.project.common.annotation.interfaces.CheckParam;
import com.project.common.annotation.interfaces.CheckToken;
import com.project.common.base.BaseController;
import com.project.common.base.BaseResult;
import com.project.service.service.PlatformService;

/**
 * 前台系统相关
 */
@RestController
@RequestMapping("/server/")
public class PlatformController extends BaseController {

	@Autowired
	private PlatformService platformService;

	/*
	 * 登录
	 */
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public BaseResult login(String params) {
		return platformService.login(getJSONObjectParams(params));
	}

	/*
	 * 退出
	 */
	@CheckToken
	@RequestMapping(value = "/loginout", method = RequestMethod.POST)
	public BaseResult loginOut(@CheckParam String token) {
		return platformService.loginOut(token);
	}
}
