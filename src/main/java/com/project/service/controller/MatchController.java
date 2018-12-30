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

	// ==============================================播报相关==================================================

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

	// ==============================================赛事详情下半部分==================================================

	/*
	 * 赛事详情-下半部-参赛球队列表
	 */
	@RequestMapping(value = "/matchinfo/teamlist", method = RequestMethod.POST)
	public BaseResult teamList(String params) {
		return matchService.teamList(getJSONObjectParams(params));
	}

	/*
	 * 赛事详情-下半部-积分榜列表
	 */
	@RequestMapping(value = "/matchinfo/scorelist", method = RequestMethod.POST)
	public BaseResult scorelist(String params) {
		return matchService.scorelist(getJSONObjectParams(params));
	}

	/*
	 * 赛事详情-下半部-赛程列表
	 */
	@RequestMapping(value = "/matchinfo/schedule/list", method = RequestMethod.POST)
	public BaseResult scheduleList(String params) {
		return matchService.scheduleList(getJSONObjectParams(params));
	}

	/*
	 * 下半部-赛程列表-球员榜列表
	 */
	@RequestMapping(value = "/matchinfo/member/list", method = RequestMethod.POST)
	public BaseResult memberList(String params) {
		return matchService.memberList(getJSONObjectParams(params));
	}

	/*
	 * 下半部-赛程列表-对方球员资料详情
	 */
	@RequestMapping(value = "/matchinfo/member/info", method = RequestMethod.POST)
	public BaseResult memberInfo(String token, String params) {
		return matchService.memberInfo(getUserByToken(token), getJSONObjectParams(params));
	}

	// ==============================================裁判设置阵型相关==================================================

	/*
	 * 下半部-阵型设置-页面信息获取
	 */
	@CheckToken
	@RequestMapping(value = "/matchinfo/formationinfo", method = RequestMethod.POST)
	public BaseResult formationInfo(@CheckParam() String token, String params) {
		return matchService.formationInfo(getUserByToken(token), getJSONObjectParams(params));
	}

	/*
	 * 下半部-阵型设置-页面信息获取
	 */
	@CheckToken
	@RequestMapping(value = "/matchinfo/saveformation", method = RequestMethod.POST)
	public BaseResult saveFormation(@CheckParam() String token, String params) {
		return matchService.saveFormation(getUserByToken(token), params);
	}

	/*
	 * 下半部-阵型设置-页面信息获取
	 */
	@CheckToken
	@RequestMapping(value = "/matchinfo/formationinfoleader", method = RequestMethod.POST)
	public BaseResult formationInfoLeader(@CheckParam() String token, String params) {
		return matchService.formationInfoLeader(getUserByToken(token), getJSONObjectParams(params));
	}

	/*
	 * 下半部-阵型设置-页面信息获取
	 */
	@CheckToken
	@RequestMapping(value = "/matchinfo/saveformationleader", method = RequestMethod.POST)
	public BaseResult saveFormationLeader(@CheckParam() String token, String params) {
		return matchService.saveFormationLeader(getUserByToken(token), params);
	}
}
