package com.project.service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.project.common.base.BaseController;
import com.project.common.base.BaseResult;
import com.project.service.service.HomeService;

/**
 * 首页相关
 */
@RestController
@RequestMapping("/server/home")
public class HomeController extends BaseController {

	@Autowired
	private HomeService homeService;

	/*
	 * 首页
	 */
	@RequestMapping(value = "", method = RequestMethod.POST)
	public BaseResult home(String params) {
		return homeService.home(getJSONObjectParams(params));
	}
	/*
	 * 首页-搜索
	 */
	@RequestMapping(value = "/search", method = RequestMethod.POST)
	public BaseResult search(String params) {
		return homeService.search(getJSONObjectParams(params));
	}
}
