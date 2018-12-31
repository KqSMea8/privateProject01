package com.project.service.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.project.common.base.BaseResult;
import com.project.common.base.BaseService;
import com.project.common.page.PageForApp;
import com.project.common.util.StringUtil;
import com.project.service.entity.User;
import com.project.service.enums.EventrealityEnum;
import com.project.service.proxy.MatchProxy;
import com.project.service.reqentity.match.AddEventrealityReqEntity;
import com.project.service.reqentity.match.MatchSelectReqEntity;
import com.project.service.reqentity.match.SaveFormationLeaderReqEntity;
import com.project.service.reqentity.match.SaveFormationReqEntity;

import net.sf.json.JSONObject;

@Service("matchService")
public class MatchService extends BaseService {

	@Autowired
	private MatchProxy matchProxy;

	@Autowired
	private DataSourceTransactionManager transactionManager;

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
			if (matchProxy.isHaveApply(teamId, matchInfoId)) {
				return errorResult("已经申请过,请耐心等待审核!");
			}

			if (matchProxy.matchApply(teamId, matchInfoId, contacts, contactsTel).intValue() == 1) {
				// 后台审核通过后将人员球队人员加入match_join_user表 TODO 后台功能
				return successResult("申请成功");
			} else {
				return errorResult("申请失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return errorExceptionResult();
		}
	}

	// ==============================================播报相关==================================================

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

			result.put("teamAFirstMemberList", matchProxy.firstMemberList(matchScheduleId, 1));
			result.put("teamASubstitutesMemberList", matchProxy.substitutesMemberList(matchScheduleId, 1));
			result.put("teamBFirstMemberList", matchProxy.firstMemberList(matchScheduleId, 2));
			result.put("teamBSubstitutesMemberList", matchProxy.substitutesMemberList(matchScheduleId, 2));

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
		TransactionStatus ststus = transactionManager.getTransaction(new DefaultTransactionDefinition());
		try {
			boolean flag = false;

			paramEntity = matchProxy.setParamEntity(paramEntity);

			boolean isNoContent = StringUtil.isEmpty(paramEntity.getContent());

			if (paramEntity.getType().equals(EventrealityEnum.进球.getKeyCode())) {

				matchProxy.updatePlayerSituation("total_goal", paramEntity);
				if (isNoContent)
					paramEntity.setContent("【" + paramEntity.getTeamInfoName() + "】队的" + paramEntity.getTeamMemberName() + " 射门进球");
			} else if (paramEntity.getType().equals(EventrealityEnum.黄牌.getKeyCode())) {

				matchProxy.updatePlayerSituation("total_yellow_card", paramEntity);
				if (isNoContent)
					paramEntity.setContent("【" + paramEntity.getTeamInfoName() + "】队的" + paramEntity.getTeamMemberName() + " 被罚黄牌");
			} else if (paramEntity.getType().equals(EventrealityEnum.红牌.getKeyCode())) {

				matchProxy.updatePlayerSituation("total_red_card", paramEntity);
				if (isNoContent)
					paramEntity.setContent("【" + paramEntity.getTeamInfoName() + "】队的" + paramEntity.getTeamMemberName() + " 被罚红牌");
			} else if (paramEntity.getType().equals(EventrealityEnum.助攻.getKeyCode())) {

				matchProxy.updatePlayerSituation("total_assistant_ngineer", paramEntity);
				if (isNoContent)
					paramEntity.setContent("【" + paramEntity.getTeamInfoName() + "】队的" + paramEntity.getTeamMemberName() + " 助攻成功");
			} else if (paramEntity.getType().equals(EventrealityEnum.换人.getKeyCode())) {

				matchProxy.replaceDown(paramEntity);
				if(paramEntity.getIsHaveInfo()) {
					matchProxy.replaceUpUpdate(paramEntity, user.getUserId());
				}else {
					matchProxy.replaceUpInsert(paramEntity, user.getUserId());
				}
				

				if (isNoContent)
					paramEntity.setContent("【" + paramEntity.getTeamInfoName() + "】队由替补队员 " + paramEntity.getReplaceTeamMemberName() + "换下场上的" + paramEntity.getTeamMemberName());
			} else if (paramEntity.getType().equals(EventrealityEnum.射门.getKeyCode())) {
				if (isNoContent)
					paramEntity.setContent("【" + paramEntity.getTeamInfoName() + "】队的" + paramEntity.getTeamMemberName() + " 进行射门");
			} else if (paramEntity.getType().equals(EventrealityEnum.点球.getKeyCode())) {
				if (isNoContent)
					paramEntity.setContent("【" + paramEntity.getTeamInfoName() + "】队的" + paramEntity.getTeamMemberName() + " 进行点球");
			} else if (paramEntity.getType().equals(EventrealityEnum.规则牌.getKeyCode())) {
				if (isNoContent)
					paramEntity.setContent("【" + paramEntity.getTeamInfoName() + "】队的" + paramEntity.getTeamMemberName() + " 被罚规则牌");
			}

			flag = matchProxy.addEventreality(paramEntity, user).intValue() == 1;

			if (StringUtil.isNotEmpty(paramEntity.getImgUrl())) {
				List<AddEventrealityReqEntity> valuse = new ArrayList<AddEventrealityReqEntity>();
				String[] imgs = paramEntity.getImgUrl().split(",");
				AddEventrealityReqEntity temp = null;
				for (String img : imgs) {
					temp = new AddEventrealityReqEntity();
					BeanUtils.copyProperties(paramEntity, temp);
					temp.setImgUrl(img);
					valuse.add(temp);
				}
				flag = flag && matchProxy.addFile(valuse, user.getUserId()).intValue() > 0;
			}

			if (flag) {
				transactionManager.commit(ststus);
				return successResult("播报成功");
			} else {
				transactionManager.rollback(ststus);
				return errorResult("播报失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
			transactionManager.rollback(ststus);
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

	// ==============================================赛事详情下半部分==================================================

	public BaseResult teamList(JSONObject params) {
		Integer matchInfoId;
		try {
			matchInfoId = params.getInt("matchInfoId");
		} catch (Exception e) {
			e.printStackTrace();
			return errorParamsResult();
		}

		try {
			return successResult("获取成功", matchProxy.teamList(matchInfoId));
		} catch (Exception e) {
			e.printStackTrace();
			return errorExceptionResult();
		}
	}

	public BaseResult scorelist(JSONObject params) {
		Integer matchInfoId;
		try {
			matchInfoId = params.getInt("matchInfoId");
		} catch (Exception e) {
			e.printStackTrace();
			return errorParamsResult();
		}

		try {
			return successResult("获取成功", matchProxy.scorelist(matchInfoId));
		} catch (Exception e) {
			e.printStackTrace();
			return errorExceptionResult();
		}
	}

	public BaseResult scheduleList(JSONObject params) {
		Integer matchInfoId, roundNumber;
		String date;
		try {
			matchInfoId = params.getInt("matchInfoId");
			roundNumber = params.get("roundNumber") == null ? null : params.getInt("roundNumber");
			date = params.get("date") == null ? null : params.getString("date");
		} catch (Exception e) {
			e.printStackTrace();
			return errorParamsResult();
		}

		try {
			return successResult("获取成功", matchProxy.scheduleList(matchInfoId, roundNumber, date));
		} catch (Exception e) {
			e.printStackTrace();
			return errorExceptionResult();
		}
	}

	public BaseResult scheduleReview(JSONObject params) {
		Integer matchInfoId, roundNumber;
		String date;
		try {
			matchInfoId = params.getInt("matchInfoId");
			roundNumber = params.get("roundNumber") == null ? null : params.getInt("roundNumber");
			date = params.get("date") == null ? null : params.getString("date");
		} catch (Exception e) {
			e.printStackTrace();
			return errorParamsResult();
		}

		try {
			return successResult("获取成功", matchProxy.scheduleList(matchInfoId, roundNumber, date));
		} catch (Exception e) {
			e.printStackTrace();
			return errorExceptionResult();
		}
	}

	public BaseResult memberList(JSONObject params) {
		Integer matchInfoId, type;
		try {
			matchInfoId = params.getInt("matchInfoId");
			type = params.getInt("type");
		} catch (Exception e) {
			e.printStackTrace();
			return errorParamsResult();
		}

		try {
			return successResult("获取成功", matchProxy.memberList(matchInfoId, type));
		} catch (Exception e) {
			e.printStackTrace();
			return errorExceptionResult();
		}
	}

	public BaseResult memberInfo(User user, JSONObject params) {
		Integer teamMemberId;
		try {
			teamMemberId = params.getInt("teamMemberId");
		} catch (Exception e) {
			e.printStackTrace();
			return errorParamsResult();
		}

		try {
			Map<String, Object> result = matchProxy.memberInfo(teamMemberId, user == null ? 0 : user.getUserId());
			result.putAll(matchProxy.memberInfoCount(teamMemberId));
			result.put("signTeamList", matchProxy.memberTeamList(teamMemberId));
			return successResult("获取成功", result);
		} catch (Exception e) {
			e.printStackTrace();
			return errorExceptionResult();
		}
	}
	// ==============================================裁判设置阵型相关==================================================

	public BaseResult formationInfo(User user, JSONObject params) {
		Integer scheduleId;
		try {
			scheduleId = params.getInt("scheduleId");
		} catch (Exception e) {
			e.printStackTrace();
			return errorParamsResult();
		}

		try {
			Map<String, Object> result = matchProxy.formationInfo(scheduleId, null);

			result.put("teamAFirstMemberList", matchProxy.firstMemberList(scheduleId, 1));
			result.put("teamASubstitutesMemberList", matchProxy.substitutesMemberList(scheduleId, 1));
			result.put("teamBFirstMemberList", matchProxy.firstMemberList(scheduleId, 2));
			result.put("teamBSubstitutesMemberList", matchProxy.substitutesMemberList(scheduleId, 2));
			return successResult("获取成功", result);
		} catch (Exception e) {
			e.printStackTrace();
			return errorExceptionResult();
		}
	}

	public BaseResult saveFormation(User user, String paramsStr) {
		SaveFormationReqEntity paramEntity;
		try {
			paramEntity = json2Entity(paramsStr, SaveFormationReqEntity.class);
		} catch (Exception e) {
			e.printStackTrace();
			return errorParamsResult();
		}
		TransactionStatus status = transactionManager.getTransaction(new DefaultTransactionDefinition());
		try {
			boolean flag = false;
			flag = matchProxy.saveFormation(paramEntity).intValue() == 1;

			if (StringUtil.isNotEmpty(paramEntity.getTeamAFirstMemberStr())) {
				flag = flag && matchProxy.deleteFirstMemberList(paramEntity.getScheduleId(), 1).intValue() > 0;
				flag = flag && matchProxy.saveFirstMemberList(paramEntity.getScheduleId(), paramEntity.getFormationA(), paramEntity.getTeamAFirstMemberStr(), 1, user.getUserId()).intValue() > 0;
			}
			if (StringUtil.isNotEmpty(paramEntity.getTeamBFirstMemberStr())) {
				flag = flag && matchProxy.deleteFirstMemberList(paramEntity.getScheduleId(), 2).intValue() > 0;
				flag = flag && matchProxy.saveFirstMemberList(paramEntity.getScheduleId(), paramEntity.getFormationB(), paramEntity.getTeamBFirstMemberStr(), 2, user.getUserId()).intValue() > 0;
			}
			if (flag) {
				transactionManager.commit(status);
				return successResult("设置成功");
			} else {
				transactionManager.rollback(status);
				return errorResult("设置失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
			transactionManager.rollback(status);
			return errorExceptionResult();
		}
	}

	public BaseResult formationInfoLeader(User user, JSONObject params) {
		Integer scheduleId;
		try {
			scheduleId = params.getInt("scheduleId");
		} catch (Exception e) {
			e.printStackTrace();
			return errorParamsResult();
		}

		try {
			Integer type = matchProxy.getType(scheduleId, user.getUserId());
			Map<String, Object> result = matchProxy.formationInfo(scheduleId, type);

			result.put("teamFirstMemberList", matchProxy.firstMemberList(scheduleId, type));
			result.put("teamSubstitutesMemberList", matchProxy.substitutesMemberList(scheduleId, type));
			return successResult("获取成功", result);
		} catch (Exception e) {
			e.printStackTrace();
			return errorExceptionResult();
		}
	}

	public BaseResult saveFormationLeader(User user, String paramsStr) {
		SaveFormationLeaderReqEntity paramEntity;
		try {
			paramEntity = json2Entity(paramsStr, SaveFormationLeaderReqEntity.class);
		} catch (Exception e) {
			e.printStackTrace();
			return errorParamsResult();
		}
		TransactionStatus status = transactionManager.getTransaction(new DefaultTransactionDefinition());
		try {
			boolean flag = false;
			Integer type = matchProxy.getType(paramEntity.getScheduleId(), user.getUserId());

			flag = matchProxy.saveFormationLeader(paramEntity, type).intValue() == 1;

			if (StringUtil.isNotEmpty(paramEntity.getTeamFirstMemberStr())) {
				flag = flag && matchProxy.deleteFirstMemberList(paramEntity.getScheduleId(), 1).intValue() > 0;
				flag = flag && matchProxy.saveFirstMemberList(paramEntity.getScheduleId(), paramEntity.getFormation(), paramEntity.getTeamFirstMemberStr(), type, user.getUserId()).intValue() > 0;
			}
			if (flag) {
				transactionManager.commit(status);
				return successResult("设置成功");
			} else {
				transactionManager.rollback(status);
				return errorResult("设置失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
			transactionManager.rollback(status);
			return errorExceptionResult();
		}
	}
}
