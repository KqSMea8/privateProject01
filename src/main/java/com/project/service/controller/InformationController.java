package com.project.service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.project.common.base.BaseController;
import com.project.common.base.BaseResult;
import com.project.service.service.InformationService;

/**
 * 资讯相关
 */
@RestController
@RequestMapping("/server/information")
public class InformationController extends BaseController {

	@Autowired
	private InformationService informationService;

	/*
	 * 资讯类型
	 */
	@RequestMapping(value = "/typelist", method = RequestMethod.POST)
	public BaseResult typeList() {
		return informationService.typeList();
	}

	/*
	 * 资讯列表
	 */
	@RequestMapping(value = "/listoftype", method = RequestMethod.POST)
	public BaseResult listOfType(String params) {
		return informationService.listOfType(getJSONObjectParams(params));
	}

	/*
	 * 资讯详情
	 */
	@RequestMapping(value = "/info", method = RequestMethod.POST)
	public BaseResult info(String params) {
		return informationService.info(getJSONObjectParams(params));
	}

	/*
	 * 资讯评论列表
	 */
	@RequestMapping(value = "/info/commentlist", method = RequestMethod.POST)
	public BaseResult commentlist(String params) {
		return informationService.commentlist(getJSONObjectParams(params));
	}

	/*
	 * 资讯评论
	 */
	@RequestMapping(value = "/info/comment", method = RequestMethod.POST)
	public BaseResult comment(String token, String params) {
		return informationService.comment(getUserByToken(token), getJSONObjectParams(params));
	}
}
