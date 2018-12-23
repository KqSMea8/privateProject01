package com.project.service.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.common.base.BaseResult;
import com.project.common.base.BaseService;
import com.project.common.page.PageForApp;
import com.project.service.proxy.HomeProxy;

import net.sf.json.JSONObject;

@Service("homeService")
public class HomeService extends BaseService {
	@Autowired
	private HomeProxy homeProxy;

	public BaseResult home(JSONObject params) {
		Integer cityId;
		try {
			cityId = params.getInt("cityId");
		} catch (Exception e) {
			e.printStackTrace();
			return errorParamsResult();
		}
		try {
			Map<String, Object> result = new HashMap<String, Object>();
			result.put("rotationImgInfo", getRotationImgInfo());// 轮播图信息
			result.put("rotationNoticeInfo", getRotationNoticeInfo());// 轮播通知信息
			result.put("hotMatchList", homeProxy.hotMatchList(cityId, Integer.parseInt(getSystemParamsByName("matchLimit"))));// 热门球赛集合
			return successResult("获取成功", result);
		} catch (Exception e) {
			e.printStackTrace();
			return errorExceptionResult();
		}

	}

	private Map<String, Object> getRotationImgInfo() {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("rotationCycle", getSystemParamsByName("rotationCycleImg"));
		result.put("rotationImgList", homeProxy.rotationImgList(Integer.parseInt(getSystemParamsByName("imgLimit"))));
		return result;
	}

	private Map<String, Object> getRotationNoticeInfo() {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("rotationCycle", getSystemParamsByName("rotationCycleNotice"));
		result.put("rotationNoticeList", homeProxy.rotationNoticeList(Integer.parseInt(getSystemParamsByName("noticeLimit"))));
		return result;
	}

	public BaseResult search(JSONObject params) {
		Integer cityId;
		String searchKey;
		Integer searchType;
		PageForApp pageMatch = null, pageTeam = null;
		try {
			pageMatch = getPageEntity(params);
			pageTeam = getPageEntity(params);
			cityId = params.getInt("cityId");
			searchKey = params.getString("searchKey");
			searchType = params.getInt("searchType");
		} catch (Exception e) {
			e.printStackTrace();
			return errorParamsResult();
		}
		try {
			if (searchType.intValue() == 1) {
				Integer limit = Integer.parseInt(getSystemParamsByName("searchLimit"));
				pageMatch.setPage(1);
				pageMatch.setRows(limit);
				pageTeam.setPage(1);
				pageTeam.setRows(limit);
			}

			Map<String, Object> result = new HashMap<String, Object>();
			homeProxy.searchMatchList(pageMatch, cityId, searchKey);
			homeProxy.searchTeamList(pageTeam, cityId, searchKey);

			result.put("matchTotal", pageMatch.getTotal());
			result.put("matchList", pageMatch.getPageContent());
			result.put("teamTotal", pageTeam.getTotal());
			result.put("teamList", pageTeam.getPageContent());

			return successResult("获取成功", result);
		} catch (Exception e) {
			e.printStackTrace();
			return errorExceptionResult();
		}
	}
}
