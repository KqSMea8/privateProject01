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
	 * 个人资料
	 */
	@CheckToken
	@RequestMapping(value = "/user/info", method = RequestMethod.POST)
	public BaseResult userInfo(@CheckParam() String token, String params) {
		return mineService.userInfo(getUserByToken(token));
	}

	/*
	 * 修改个人资料
	 */
	@CheckToken
	@RequestMapping(value = "/user/update/info", method = RequestMethod.POST)
	public BaseResult updateUserInfo(@CheckParam() String token, String params) {
		return mineService.updateUserInfo(getUserByToken(token), params);
	}

	/*
	 * 修改头像
	 */
	@CheckToken
	@RequestMapping(value = "/user/update/head", method = RequestMethod.POST)
	public BaseResult userUpdateHeadImage(@CheckParam() String token, String params) {
		return mineService.userUpdateHeadImage(getUserByToken(token), getJSONObjectParams(params));
	}

	/*
	 * 绑定手机号
	 */
	@CheckToken
	@RequestMapping(value = "/user/update/mobile", method = RequestMethod.POST)
	public BaseResult userUpdateMobile(@CheckParam() String token, String params) {
		return mineService.userUpdateMobile(getUserByToken(token), getJSONObjectParams(params));
	}

	/*
	 * 认证裁判
	 */
	@CheckToken
	@RequestMapping(value = "/user/authentication", method = RequestMethod.POST)
	public BaseResult authentication(@CheckParam() String token, String params) {
		return mineService.authentication(getUserByToken(token), getJSONObjectParams(params));
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
	 * 创建球队
	 */
	@CheckToken
	@RequestMapping(value = "/team/edit", method = RequestMethod.POST)
	public BaseResult teamEdit(String token, String params) {
		return mineService.teamEdit(getUserByToken(token), params);
	}

	/*
	 * 我的球队详情
	 */
	@RequestMapping(value = "/team/info", method = RequestMethod.POST)
	public BaseResult teamInfo(String token, String params) {
		return mineService.teamInfo(getUserByToken(token), getJSONObjectParams(params));
	}

	/*
	 * 申请加入球队
	 */
	@CheckToken
	@RequestMapping(value = "/team/member/apply", method = RequestMethod.POST)
	public BaseResult teamMemberApply(String token, String params) {
		return mineService.teamMemberApply(getUserByToken(token), getJSONObjectParams(params));
	}

	/*
	 * 我的球队球员列表
	 */
	@RequestMapping(value = "/team/member/list", method = RequestMethod.POST)
	public BaseResult teamMemberList(String token, String params) {
		return mineService.teamMemberList(getUserByToken(token), getJSONObjectParams(params));
	}

	/*
	 * 设置队员职务
	 */
	@RequestMapping(value = "/team/member/settingrole", method = RequestMethod.POST)
	public BaseResult settingRole(String token, String params) {
		return mineService.settingRole(getUserByToken(token), getJSONObjectParams(params));
	}

	/*
	 * 我的球队-挂牌球员搜索
	 */
	@CheckToken
	@RequestMapping(value = "/team/member/listedplayers", method = RequestMethod.POST)
	public BaseResult listEdplayers(String token, String params) {
		return mineService.listEdplayers(getUserByToken(token), getJSONObjectParams(params));
	}

	/**
	 * 队员详情
	 */
	@CheckToken
	@RequestMapping(value = "/team/member/info", method = RequestMethod.POST)
	public BaseResult teamMemberInfo(String token, String params) {
		return mineService.teamMemberInfo(getUserByToken(token), getJSONObjectParams(params));
	}

	/**
	 * 球队成员（移除、审核）
	 */
	@CheckToken
	@RequestMapping(value = "/team/member/updatestatus", method = RequestMethod.POST)
	public BaseResult updateMemberStatus(String token, String params) {
		return mineService.updateMemberStatus(getUserByToken(token), getJSONObjectParams(params));
	}

	/**
	 * 球队相册
	 */
	@RequestMapping(value = "/team/album", method = RequestMethod.POST)
	public BaseResult teamAlbumList(String params) {
		return mineService.teamAlbumList(getJSONObjectParams(params));
	}

	// ==============================================我的相册相关=============================================================
	/**
	 * 我的相册
	 */
	@CheckToken
	@RequestMapping(value = "/album", method = RequestMethod.POST)
	public BaseResult albumList(String token, String params) {
		return mineService.albumList(getUserByToken(token), getJSONObjectParams(params));
	}
	// ==============================================商品订单相关=============================================================

	/**
	 * 我的订单
	 */
	@CheckToken
	@RequestMapping(value = "/order", method = RequestMethod.POST)
	public BaseResult orderList(String token, String params) {
		return mineService.orderList(getUserByToken(token), getJSONObjectParams(params));
	}
	// ==============================================约战相关=============================================================

	/**
	 * 我的约战
	 */
	@CheckToken
	@RequestMapping(value = "/pklist", method = RequestMethod.POST)
	public BaseResult pklist(String token, String params) {
		return mineService.pklist(getUserByToken(token), getJSONObjectParams(params));
	}
	// ==============================================关注相关=============================================================

	/**
	 * 关注操作
	 */
	@CheckToken
	@RequestMapping(value = "/follow", method = RequestMethod.POST)
	public BaseResult follow(String token, String params) {
		return mineService.follow(getUserByToken(token), getJSONObjectParams(params));
	}

	/**
	 * 我关注的赛事
	 */
	@CheckToken
	@RequestMapping(value = "/follow/matchlist", method = RequestMethod.POST)
	public BaseResult followMatchlist(String token, String params) {
		return mineService.followMatchlist(getUserByToken(token), getJSONObjectParams(params));
	}

	/**
	 * 我关注的球队
	 */
	@CheckToken
	@RequestMapping(value = "/follow/teamlist", method = RequestMethod.POST)
	public BaseResult followTeamlist(String token, String params) {
		return mineService.followTeamlist(getUserByToken(token), getJSONObjectParams(params));
	}

	/**
	 * 我关注的球员
	 */
	@CheckToken
	@RequestMapping(value = "/follow/memberlist", method = RequestMethod.POST)
	public BaseResult followMemberlist(String token, String params) {
		return mineService.followMemberlist(getUserByToken(token), getJSONObjectParams(params));
	}
	// ==============================================我的游戏=============================================================

	/**
	 * 我的游戏
	 */
	@CheckToken
	@RequestMapping(value = "/gamelist", method = RequestMethod.POST)
	public BaseResult gamelist(String token, String params) {
		return mineService.gamelist(getUserByToken(token), getJSONObjectParams(params));
	}
}
