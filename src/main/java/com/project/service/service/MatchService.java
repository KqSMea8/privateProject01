package com.project.service.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.common.base.BaseResult;
import com.project.common.base.BaseService;
import com.project.common.page.PageForApp;
import com.project.service.entity.User;
import com.project.service.proxy.MatchProxy;
import com.project.service.reqentity.match.AddEventrealityReqEntity;
import com.project.service.reqentity.match.MatchSelectReqEntity;

import net.sf.json.JSONObject;

@Service("matchService")
public class MatchService extends BaseService {

	@Autowired
	private MatchProxy matchProxy;

	public BaseResult matchlist(String paramsStr) {
		MatchSelectReqEntity paramEntity;
		PageForApp page;
		try {
			page = getPageEntity(getJSONObjectParams(paramsStr));
			paramEntity = json2Entity(paramsStr, MatchSelectReqEntity.class);
			if (!paramEntity.validate())
				return errorParamsResult(paramEntity.getValidateErrorMsg());

		} catch (Exception e) {
			e.printStackTrace();
			return errorParamsResult();
		}

		try {
			return successResult("获取成功", matchProxy.matchlist(page, paramEntity));
		} catch (Exception e) {
			e.printStackTrace();
			return errorExceptionResult();
		}
	}

	public BaseResult matchinfo(User user, JSONObject params) {
		Integer matchInfoId;
		try {
			matchInfoId = params.getInt("matchInfoId");

		} catch (Exception e) {
			e.printStackTrace();
			return errorParamsResult();
		}

		try {
			Map<String, Object> result = matchProxy.matchinfo(matchInfoId, user == null ? 0 : user.getUserId());
			Integer status = Integer.parseInt(result.get("status").toString());
			Map<String, Object> matchEndInfo = null, marchRuningInfo = null;
			if (4 == status.intValue()) {
				matchEndInfo = matchProxy.matchEndInfo(matchInfoId);
			} else if (3 == status.intValue()) {
				marchRuningInfo = matchProxy.marchRuningInfo(matchInfoId, user == null ? 0 : user.getUserId());
			}
			result.put("matchEndInfo", matchEndInfo);
			result.put("marchRuningInfo", marchRuningInfo);
			return successResult("获取成功", result);
		} catch (Exception e) {
			e.printStackTrace();
			return errorExceptionResult();
		}
	}

	public BaseResult matchinfoImagelist(JSONObject params) {
		Integer matchInfoId, teamId, teamMemberId;
		try {
			matchInfoId = params.getInt("matchInfoId");
			teamId = params.getInt("teamId");
			teamMemberId = params.getInt("teamMemberId");
		} catch (Exception e) {
			e.printStackTrace();
			return errorParamsResult();
		}

		try {
			return successResult("获取成功", matchProxy.matchinfoImagelist(matchInfoId, teamId, teamMemberId));
		} catch (Exception e) {
			e.printStackTrace();
			return errorExceptionResult();
		}
	}

	public BaseResult matchinfoVideolist(JSONObject params) {
		Integer matchInfoId, teamId, teamMemberId;
		try {
			matchInfoId = params.getInt("matchInfoId");
			teamId = params.getInt("teamId");
			teamMemberId = params.getInt("teamMemberId");
		} catch (Exception e) {
			e.printStackTrace();
			return errorParamsResult();
		}

		try {
			return successResult("获取成功", matchProxy.matchinfoVideolist(matchInfoId, teamId, teamMemberId));
		} catch (Exception e) {
			e.printStackTrace();
			return errorExceptionResult();
		}
	}

	public BaseResult matchinfoVideoInfo(JSONObject params) {
		Integer matchInfoId, videoId;
		try {
			matchInfoId = params.getInt("matchInfoId");
			videoId = params.getInt("videoId");
		} catch (Exception e) {
			e.printStackTrace();
			return errorParamsResult();
		}

		try {
			return successResult("获取成功", matchProxy.matchinfoVideoInfo(matchInfoId, videoId));
		} catch (Exception e) {
			e.printStackTrace();
			return errorExceptionResult();
		}
	}

	public BaseResult videoInfoCommentlist(JSONObject params) {
		Integer videoId;
		PageForApp page;
		try {
			page = getPageEntity(params);
			videoId = params.getInt("videoId");
		} catch (Exception e) {
			e.printStackTrace();
			return errorParamsResult();
		}

		try {
			matchProxy.videoInfoCommentlist(page, videoId);

			Map<String, Object> result = new HashMap<String, Object>();
			result.put("commentCount", page.getTotal());
			result.put("commentList", page.getPageContent());
			return successResult("获取成功", result);
		} catch (Exception e) {
			e.printStackTrace();
			return errorExceptionResult();
		}
	}

	public BaseResult videoInfoComment(User user, JSONObject params) {
		Integer videoId;
		String commentContent;
		try {
			videoId = params.getInt("videoId");
			commentContent = params.getString("commentContent");
		} catch (Exception e) {
			e.printStackTrace();
			return errorParamsResult();
		}

		try {
			if (matchProxy.videoInfoComment(commentContent, videoId, user.getUserId()).intValue() == 1) {
				return successResult("评论成功");
			} else {
				return errorResult("评论失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return errorExceptionResult();
		}
	}

	public BaseResult matchApply(User user, JSONObject params) {
		Integer matchInfoId, teamId;
		String contacts, contactsTel;
		try {
			matchInfoId = params.getInt("matchInfoId");
			teamId = params.getInt("teamId");
			contacts = params.getString("contacts");
			contactsTel = params.getString("contactsTel");
		} catch (Exception e) {
			e.printStackTrace();
			return errorParamsResult();
		}

		try {
			if (matchProxy.matchApply(teamId, matchInfoId, contacts, contactsTel).intValue() == 1) {
				return successResult("评论成功");
			} else {
				return errorResult("评论失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return errorExceptionResult();
		}
	}

	public BaseResult eventrealityInfo(User user, JSONObject params) {
		Integer matchInfoId;
		try {
			matchInfoId = params.getInt("matchInfoId");
		} catch (Exception e) {
			e.printStackTrace();
			return errorParamsResult();
		}

		try {
			Map<String, Object> result = matchProxy.marchRuningInfo(matchInfoId, user.getUserId());
			Integer matchScheduleId = Integer.parseInt(result.get("matchScheduleId").toString());

			Integer teamIdA = Integer.parseInt(result.get("teamIdA").toString());
			result.put("memberListA", matchProxy.getMemberList(teamIdA, matchScheduleId));
			Integer teamIdB = Integer.parseInt(result.get("teamIdB").toString());
			result.put("memberListA", matchProxy.getMemberList(teamIdB, matchScheduleId));

			return successResult("获取成功", result);
		} catch (Exception e) {
			e.printStackTrace();
			return errorExceptionResult();
		}
	}

	public BaseResult addEventreality(User user, String paramsStr) {
		AddEventrealityReqEntity paramEntity;
		try {
			paramEntity = json2Entity(paramsStr, AddEventrealityReqEntity.class);
			if (!paramEntity.validate())
				return errorParamsResult(paramEntity.getValidateErrorMsg());

		} catch (Exception e) {
			e.printStackTrace();
			return errorParamsResult();
		}

		try {
			if (matchProxy.addEventreality(paramEntity, user).intValue() == 1) {
				return successResult("播报成功");
			} else {
				return errorResult("播报失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return errorExceptionResult();
		}
	}

	public BaseResult eventrealityList(JSONObject params) {
		Integer matchInfoId;
		try {
			matchInfoId = params.getInt("matchInfoId");
		} catch (Exception e) {
			e.printStackTrace();
			return errorParamsResult();
		}

		try {
			Map<String, Object> result = matchProxy.marchRuningInfo(matchInfoId, 0);
			Integer matchScheduleId = Integer.parseInt(result.get("matchScheduleId").toString());

			result.put("eventRealityList", matchProxy.eventRealityList(matchScheduleId));
			return successResult("获取成功", result);
		} catch (Exception e) {
			e.printStackTrace();
			return errorExceptionResult();
		}
	}
}
