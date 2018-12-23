package com.project.service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.project.common.annotation.interfaces.CheckParam;
import com.project.common.annotation.interfaces.CheckToken;
import com.project.common.base.BaseController;
import com.project.common.base.BaseResult;
import com.project.service.service.MatchService;

/**
 * 赛事相关
 */
@RestController
@RequestMapping("/server/match/")
public class MatchController extends BaseController {
	@Autowired
	private MatchService matchService;

	/*
	 * 赛事首页列表
	 */
	@RequestMapping(value = "/matchlist", method = RequestMethod.POST)
	public BaseResult matchlist(String params) {
		return matchService.matchlist(params);
	}

	/*
	 * 赛事详情
	 */
	@RequestMapping(value = "/matchinfo", method = RequestMethod.POST)
	public BaseResult matchinfo(String token, String params) {
		return matchService.matchinfo(getUserByToken(token), getJSONObjectParams(params));
	}

	/*
	 * 赛事图片列表
	 */
	@RequestMapping(value = "/matchinfo/imagelist", method = RequestMethod.POST)
	public BaseResult matchinfoImagelist(String params) {
		return matchService.matchinfoImagelist(getJSONObjectParams(params));
	}

	/*
	 * 赛事视频列表
	 */
	@RequestMapping(value = "/matchinfo/videolist", method = RequestMethod.POST)
	public BaseResult matchinfoVideolist(String params) {
		return matchService.matchinfoVideolist(getJSONObjectParams(params));
	}

	/*
	 * 赛事视频详情
	 */
	@RequestMapping(value = "/matchinfo/videoinfo", method = RequestMethod.POST)
	public BaseResult matchinfoVideoInfo(String params) {
		return matchService.matchinfoVideoInfo(getJSONObjectParams(params));
	}

	/*
	 * 赛事视频详情-评论列表
	 */
	@RequestMapping(value = "/matchinfo/videoinfo/commentlist", method = RequestMethod.POST)
	public BaseResult videoInfoCommentlist(String params) {
		return matchService.videoInfoCommentlist(getJSONObjectParams(params));
	}

	/*
	 * 赛事视频详情-发起评论
	 */
	@CheckToken
	@RequestMapping(value = "/matchinfo/videoinfo/comment", method = RequestMethod.POST)
	public BaseResult videoInfoComment(@CheckParam() String token, String params) {
		return matchService.videoInfoComment(getUserByToken(token), getJSONObjectParams(params));
	}

	/*
	 * 申请参赛
	 */
	@CheckToken
	@RequestMapping(value = "/matchinfo/matchapply", method = RequestMethod.POST)
	public BaseResult matchApply(@CheckParam() String token, String params) {
		return matchService.matchApply(getUserByToken(token), getJSONObjectParams(params));
	}

	/*
	 * 播报实况页面可选信息
	 */
	@CheckToken
	@RequestMapping(value = "/matchinfo/eventrealityinfo", method = RequestMethod.POST)
	public BaseResult eventrealityInfo(@CheckParam() String token, String params) {
		return matchService.eventrealityInfo(getUserByToken(token), getJSONObjectParams(params));
	}

	/*
	 * 赛事详情-播报实况
	 */
	@CheckToken
	@RequestMapping(value = "/matchinfo/addeventreality", method = RequestMethod.POST)
	public BaseResult addEventreality(@CheckParam() String token, String params) {
		return matchService.addEventreality(getUserByToken(token), params);
	}

	/*
	 * 赛事详情-播报列表
	 */
	@RequestMapping(value = "/matchinfo/eventrealitylist", method = RequestMethod.POST)
	public BaseResult eventrealityList(String params) {
		return matchService.eventrealityList(getJSONObjectParams(params));
	}
}
