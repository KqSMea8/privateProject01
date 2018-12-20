package com.project.service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.project.common.annotation.interfaces.CheckParam;
import com.project.common.annotation.interfaces.CheckToken;
import com.project.common.base.BaseController;
import com.project.common.base.BaseResult;
import com.project.service.service.CommodityService;

@RequestMapping("/server/malls")
public class CommodityController extends BaseController {

	@Autowired
	private CommodityService commodityService;

	/*
	 * 商品类型
	 */
	@RequestMapping(value = "/typelist", method = RequestMethod.POST)
	public BaseResult typeList() {
		return commodityService.typeList();
	}

	/*
	 * 商品列表
	 */
	@RequestMapping(value = "/listoftype", method = RequestMethod.POST)
	public BaseResult listOfType(String params) {
		return commodityService.listOfType(getJSONObjectParams(params));
	}

	/*
	 * 商品详情
	 */
	@RequestMapping(value = "/info", method = RequestMethod.POST)
	public BaseResult info(String params) {
		return commodityService.info(getJSONObjectParams(params));
	}

	/*
	 * 创建订单
	 */
	@CheckToken
	@RequestMapping(value = "/buy", method = RequestMethod.POST)
	public BaseResult buy(@CheckParam() String token, String params) {
		return commodityService.buy(getOperatorByToken(token), getJSONObjectParams(params));
	}
}
