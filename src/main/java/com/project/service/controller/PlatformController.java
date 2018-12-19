package com.project.service.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

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

	/*
	 * 获取验证码
	 */
	@RequestMapping(value = "/getsmscode", method = RequestMethod.POST)
	public BaseResult getSmsCode(String params) {
		return platformService.getSmsCode(getJSONObjectParams(params));
	}

	/*
	 * 上传图片
	 */
	@RequestMapping(value = "/uploadimage", method = RequestMethod.POST)
	public BaseResult uploadImage(MultipartFile file, HttpServletRequest request) {
		return platformService.uploadImage(file, request);
	}

	/*
	 * 获取切换城市页面数据
	 */
	@RequestMapping(value = "/citylist", method = RequestMethod.POST)
	public BaseResult cityList(String params) {
		return platformService.cityList(getJSONObjectParams(params));
	}

	/*
	 * 赛事-获取可选球队
	 */
	@RequestMapping(value = "/screen/team", method = RequestMethod.POST)
	public BaseResult screeTeam(String params) {
		return platformService.screeTeam(getJSONObjectParams(params));
	}

	/*
	 * 赛事-获取可选球员
	 */
	@RequestMapping(value = "/screen/member", method = RequestMethod.POST)
	public BaseResult screeMember(String params) {
		return platformService.screeMember(getJSONObjectParams(params));
	}
}
