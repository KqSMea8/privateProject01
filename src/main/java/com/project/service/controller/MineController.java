package com.project.service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.project.common.annotation.interfaces.CheckParam;
import com.project.common.annotation.interfaces.CheckToken;
import com.project.common.base.BaseController;
import com.project.common.base.BaseResult;
import com.project.service.service.MineService;

@RestController
@RequestMapping("/server/mine")
public class MineController extends BaseController {

	@Autowired
	private MineService mineService;

	// ==============================================用户相关=============================================================
	/*
	 * 个人中心
	 */
	@CheckToken
	@RequestMapping(value = "", method = RequestMethod.POST)
	public BaseResult center(@CheckParam() String token) {
		return mineService.center(getUserByToken(token));
	}

	/*
	 * 个人资料修改
	 */
	@CheckToken
	@RequestMapping(value = "/user/info", method = RequestMethod.POST)
	public BaseResult userInfo(@CheckParam() String token, String params) {
		return mineService.userInfo(getUserByToken(token), params);
	}

	/*
	 * 修改头像
	 */
	@CheckToken
	@RequestMapping(value = "/user/update/head", method = RequestMethod.POST)
	public BaseResult userUpdateHeadImage(@CheckParam() String token, String params) {
		return mineService.userUpdateHeadImage(getUserByToken(token), getJSONObjectParams(params));
	}

	// ==============================================收货地址相关=============================================================
	/*
	 * 收货地址列表
	 */
	@CheckToken
	@RequestMapping(value = "/user/receive/list", method = RequestMethod.POST)
	public BaseResult receiveList(@CheckParam() String token) {
		return mineService.receiveList(getUserByToken(token));
	}

	/*
	 * 新增/修改收货地址
	 */
	@CheckToken
	@RequestMapping(value = "/user/receive/edit", method = RequestMethod.POST)
	public BaseResult receiveEdit(@CheckParam() String token, String params) {
		return mineService.receiveEdit(getUserByToken(token), params);
	}

	// ==============================================球队相关=============================================================
	/*
	 * 我的球队列表
	 */
	@CheckToken
	@RequestMapping(value = "/team/list", method = RequestMethod.POST)
	public BaseResult teamList(@CheckParam() String token) {
		return mineService.teamList(getUserByToken(token));
	}

	/*
	 * 我的球队详情
	 */
	@RequestMapping(value = "/team/info", method = RequestMethod.POST)
	public BaseResult teamInfo(String token, String params) {
		return mineService.teamInfo(getUserByToken(token), getJSONObjectParams(params));
	}
}
