package com.project.service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.project.common.base.BaseController;
import com.project.common.base.BaseResult;
import com.project.service.service.MineService;

@RestController
@RequestMapping("/server/mine")
public class MineController extends BaseController {

	@Autowired
	private MineService mineService;

	/*
	 * 个人中心
	 */
	@RequestMapping(value = "", method = RequestMethod.POST)
	public BaseResult center(String token) {
		return mineService.center(getUserByToken(token));
	}

	/*
	 * 个人资料修改
	 */
	@RequestMapping(value = "/user/info", method = RequestMethod.POST)
	public BaseResult userInfo(String token, String params) {
		return mineService.userInfo(getUserByToken(token), params);
	}

	/*
	 * 修改头像
	 */
	@RequestMapping(value = "/user/update/head", method = RequestMethod.POST)
	public BaseResult userUpdateHeadImage(String token, String params) {
		return mineService.userUpdateHeadImage(getUserByToken(token), getJSONObjectParams(params));
	}
}
