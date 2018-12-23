package com.project.service.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.common.base.BaseResult;
import com.project.common.base.BaseService;
import com.project.common.page.PageForApp;
import com.project.common.util.StringUtil;
import com.project.service.entity.User;
import com.project.service.proxy.MineProxy;
import com.project.service.reqentity.receive.ReceiveSaveReqEntity;
import com.project.service.reqentity.team.TeamSaveReqEntity;
import com.project.service.reqentity.user.UserSaveReqEntity;

import net.sf.json.JSONObject;

@Service("mineService")
public class MineService extends BaseService {
	@Autowired
	private MineProxy mineProxy;

	// ==============================================用户相关=============================================================
	public BaseResult center(User user) {
		try {
			return successResult("获取成功", mineProxy.getCenterInfo(user.getUserId()));
		} catch (Exception e) {
			e.printStackTrace();
			return errorExceptionResult();
		}
	}

	public BaseResult userInfo(User user) {
		try {
			return successResult("获取成功", mineProxy.userInfo(user.getUserId()));
		} catch (Exception e) {
			e.printStackTrace();
			return errorExceptionResult();
		}
	}

	public BaseResult updateUserInfo(User user, String paramsStr) {
		UserSaveReqEntity paramEntity;
		try {
			paramEntity = json2Entity(paramsStr, UserSaveReqEntity.class);
			if (!paramEntity.validate())
				return errorParamsResult(paramEntity.getValidateErrorMsg());
			paramEntity.setUserId(user.getUserId());
		} catch (Exception e) {
			e.printStackTrace();
			return errorParamsResult();
		}

		try {
			if (mineProxy.updateUserInfo(paramEntity).intValue() == 1) {
				return successResult("设置成功");
			} else {
				return successResult("设置失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return errorExceptionResult();
		}
	}

	public BaseResult userUpdateHeadImage(User user, JSONObject params) {
		String headImgUrl;
		try {
			headImgUrl = params.getString("headImgUrl");
		} catch (Exception e) {
			e.printStackTrace();
			return errorParamsResult();
		}

		try {
			if (mineProxy.userUpdateHeadImage(headImgUrl, user.getUserId()).intValue() == 1) {
				return successResult("设置成功");
			} else {
				return successResult("设置失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return errorExceptionResult();
		}
	}

	public BaseResult userUpdateMobile(User user, JSONObject params) {
		String mobile;
		String code;
		String validateType;
		try {
			mobile = params.getString("mobile");
			code = params.getString("code");
			validateType = params.getString("validateType");
			if (StringUtil.isEmpty(mobile) || StringUtil.isEmpty(code) || StringUtil.isEmpty(validateType)) {
				return successResult("参数短缺");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return errorParamsResult();
		}

		try {
			String vcode = redisUtil.get(validateType + mobile);
			if (StringUtil.isEmpty(vcode)) {
				return successResult("验证码过期");
			}
			if (!vcode.equals(code)) {
				return successResult("验证码不正确");
			}
			if (mineProxy.userUpdateMobile(mobile, user.getUserId()).intValue() == 1) {
				return successResult("绑定成功");
			} else {
				return successResult("绑定失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return errorExceptionResult();
		}
	}

	public BaseResult authentication(User user, JSONObject params) {
		String credentialsName;
		String imgPath;
		try {
			credentialsName = params.getString("mobile");
			imgPath = params.getString("code");
		} catch (Exception e) {
			e.printStackTrace();
			return errorParamsResult();
		}

		try {
			if (mineProxy.authentication(credentialsName, imgPath, user.getUserId()).intValue() == 1) {
				return successResult("绑定成功");
			} else {
				return successResult("绑定失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return errorExceptionResult();
		}
	}
	// ==============================================收货地址相关=============================================================

	public BaseResult receiveList(User user) {
		try {
			return successResult("获取成功", mineProxy.receiveList(user.getUserId()));
		} catch (Exception e) {
			e.printStackTrace();
			return errorExceptionResult();
		}
	}

	public BaseResult receiveEdit(User user, String paramsStr) {
		ReceiveSaveReqEntity paramEntity;
		try {
			paramEntity = json2Entity(paramsStr, ReceiveSaveReqEntity.class);
			if (!paramEntity.validate())
				return errorParamsResult(paramEntity.getValidateErrorMsg());

		} catch (Exception e) {
			e.printStackTrace();
			return errorParamsResult();
		}

		try {
			boolean result = false;
			if (paramEntity.getReceiveId() == null) {
				result = mineProxy.createReceive(paramEntity, user).intValue() == 1;
			} else {
				result = mineProxy.editReceive(paramEntity, user).intValue() == 1;
			}
			if (result) {
				return successResult("设置成功");
			} else {
				return successResult("设置失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return errorExceptionResult();
		}
	}

	// ==============================================球队相关=============================================================

	public BaseResult teamList(User user) {
		try {
			return successResult("获取成功", mineProxy.teamList(user.getUserId()));
		} catch (Exception e) {
			e.printStackTrace();
			return errorExceptionResult();
		}
	}

	public BaseResult teamEdit(User user, String paramsStr) {
		TeamSaveReqEntity paramEntity;
		try {
			paramEntity = json2Entity(paramsStr, TeamSaveReqEntity.class);
			if (!paramEntity.validate())
				return errorParamsResult(paramEntity.getValidateErrorMsg());

		} catch (Exception e) {
			e.printStackTrace();
			return errorParamsResult();
		}

		try {
			boolean result = false;
			if (paramEntity.getTeamId() == null) {
				result = mineProxy.createTeam(paramEntity, user).intValue() == 1;
			} else {
				result = mineProxy.updateTeam(paramEntity, user).intValue() == 1;
			}
			if (result) {
				return successResult("设置成功");
			} else {
				return successResult("设置失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return errorExceptionResult();
		}
	}

	public BaseResult teamInfo(User user, JSONObject params) {
		Integer teamId;
		try {
			teamId = params.getInt("teamId");
		} catch (Exception e) {
			e.printStackTrace();
			return errorParamsResult();
		}

		try {
			Map<String, Object> result = mineProxy.teamInfo(teamId);
			result.putAll(mineProxy.getTeamCount(teamId));// 获取统计数据
			result.put("matchScheduleInfo", mineProxy.matchScheduleInfo(teamId));
			// TODO 注意查询 signUpTotal和signUpStatus的值
			Integer roleType = user == null ? 0 : 1;// TODO 注意判断当前用户的权限
			result.put("roleType", roleType);

			return successResult("获取成功", result);
		} catch (Exception e) {
			e.printStackTrace();
			return errorExceptionResult();
		}
	}

	public BaseResult teamInfoMore(User user, JSONObject params) {
		Integer teamId;
		String year;
		try {
			teamId = params.getInt("teamId");
			year = params.getString("year");
		} catch (Exception e) {
			e.printStackTrace();
			return errorParamsResult();
		}

		try {
			Map<String, Object> result = mineProxy.teamInfoMore(teamId, year);// TODO peopleAverage, matchAverage 未统计
			result.put("matchScheduleList", mineProxy.matchScheduleList(teamId, year));
			return successResult("获取成功", result);
		} catch (Exception e) {
			e.printStackTrace();
			return errorExceptionResult();
		}
	}

	public BaseResult teamMemberList(User user, JSONObject params) {
		Integer teamId, order;
		String sort;
		try {
			teamId = params.getInt("teamId");
			order = params.getInt("order");
			sort = params.getString("sort");
		} catch (Exception e) {
			e.printStackTrace();
			return errorParamsResult();
		}

		try {
			Map<String, Object> result = new HashMap<String, Object>();
			result.put("isOperator", 1);// TODO 这里根据规则展示是不是有权限操作相关内容
			result.put("applyUserList", mineProxy.applyUserList(teamId));
			result.put("memberList", mineProxy.memberList(teamId, order, sort));
			return successResult("获取成功", result);
		} catch (Exception e) {
			e.printStackTrace();
			return errorExceptionResult();
		}
	}

	public BaseResult listEdplayers(User user, JSONObject params) {
		Integer teamId;
		try {
			teamId = params.getInt("teamId");
		} catch (Exception e) {
			e.printStackTrace();
			return errorParamsResult();
		}

		try {
			// 排除球队队员和自己
			return successResult("获取成功", mineProxy.listEdplayers(teamId, user.getUserId()));
		} catch (Exception e) {
			e.printStackTrace();
			return errorExceptionResult();
		}
	}

	public BaseResult teamMemberInfo(User user, JSONObject params) {
		Integer teamMemberId;
		try {
			teamMemberId = params.getInt("teamMemberId");
		} catch (Exception e) {
			e.printStackTrace();
			return errorParamsResult();
		}

		try {
			Map<String, Object> result = mineProxy.teamMemberInfo(teamMemberId);
			// TODO totalIn,totalAttendance，totalNoAttendance未统计
			return successResult("获取成功", result);
		} catch (Exception e) {
			e.printStackTrace();
			return errorExceptionResult();
		}
	}

	public BaseResult updateMemberStatus(User user, JSONObject params) {
		Integer teamMemberId, status;
		try {
			teamMemberId = params.getInt("teamMemberId");
			status = params.getInt("status");
		} catch (Exception e) {
			e.printStackTrace();
			return errorParamsResult();
		}

		try {
			if (mineProxy.updateMemberStatus(teamMemberId, status, user.getUserId()).intValue() == 1) {
				return successResult("操作成功");
			} else {
				return errorResult("操作失败");
			}

		} catch (Exception e) {
			e.printStackTrace();
			return errorExceptionResult();
		}
	}

	public BaseResult teamAlbumList(JSONObject params) {
		Integer teamId;
		try {
			teamId = params.getInt("teamId");
		} catch (Exception e) {
			e.printStackTrace();
			return errorParamsResult();
		}

		try {
			return successResult("获取成功", mineProxy.teamAlbumList(teamId));
		} catch (Exception e) {
			e.printStackTrace();
			return errorExceptionResult();
		}
	}

	public BaseResult albumList(User user, JSONObject params) {
		String startDate, endDate;
		try {
			startDate = params.getString("startDate");
			endDate = params.getString("endDate");
		} catch (Exception e) {
			e.printStackTrace();
			return errorParamsResult();
		}

		try {
			return successResult("获取成功", mineProxy.albumList(user.getUserId(), startDate, endDate));
		} catch (Exception e) {
			e.printStackTrace();
			return errorExceptionResult();
		}
	}
	// ==============================================商品订单相关=============================================================

	public BaseResult orderList(User user, JSONObject params) {
		Integer type;
		PageForApp page = null;
		try {
			page = getPageEntity(params);
			type = params.getInt("type");
		} catch (Exception e) {
			e.printStackTrace();
			return errorParamsResult();
		}
		try {
			return successResult("获取成功", mineProxy.orderList(page, type, user.getUserId()));
		} catch (Exception e) {
			e.printStackTrace();
			return errorExceptionResult();
		}

	}

	// ==============================================约战相关=============================================================
	public BaseResult pklist(User user, JSONObject params) {
		PageForApp page = null;
		try {
			page = getPageEntity(params);
		} catch (Exception e) {
			e.printStackTrace();
			return errorParamsResult();
		}
		try {
			return successResult("获取成功", mineProxy.pklist(page, user.getUserId()));
		} catch (Exception e) {
			e.printStackTrace();
			return errorExceptionResult();
		}
	}
	// ==============================================关注相关=============================================================

	public BaseResult follow(User user, JSONObject params) {
		Integer type, followInfoId;
		try {
			followInfoId = params.getInt("followInfoId");
			type = params.getInt("type");
		} catch (Exception e) {
			e.printStackTrace();
			return errorParamsResult();
		}
		try {
			return successResult("获取成功", mineProxy.follow(followInfoId, type, user.getUserId()));
		} catch (Exception e) {
			e.printStackTrace();
			return errorExceptionResult();
		}

	}

	public BaseResult followMatchlist(User user, JSONObject params) {
		PageForApp page = null;
		try {
			page = getPageEntity(params);
		} catch (Exception e) {
			e.printStackTrace();
			return errorParamsResult();
		}
		try {
			return successResult("获取成功", mineProxy.followMatchlist(page, user.getUserId()));
		} catch (Exception e) {
			e.printStackTrace();
			return errorExceptionResult();
		}
	}

	public BaseResult followTeamlist(User user, JSONObject params) {
		PageForApp page = null;
		try {
			page = getPageEntity(params);
		} catch (Exception e) {
			e.printStackTrace();
			return errorParamsResult();
		}
		try {
			return successResult("获取成功", mineProxy.followTeamlist(page, user.getUserId()));
		} catch (Exception e) {
			e.printStackTrace();
			return errorExceptionResult();
		}
	}

	public BaseResult followMemberlist(User user, JSONObject params) {
		PageForApp page = null;
		try {
			page = getPageEntity(params);
		} catch (Exception e) {
			e.printStackTrace();
			return errorParamsResult();
		}
		try {
			return successResult("获取成功", mineProxy.followMemberlist(page, user.getUserId()));
		} catch (Exception e) {
			e.printStackTrace();
			return errorExceptionResult();
		}
	}

}
