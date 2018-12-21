package com.project.service.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.common.base.BaseResult;
import com.project.common.base.BaseService;
import com.project.service.entity.User;
import com.project.service.proxy.MineProxy;
import com.project.service.reqentity.receive.ReceiveSaveReqEntity;

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

	public BaseResult userInfo(User user, String paramsStr) {
		// TODO Auto-generated method stub
		return null;
	}

	public BaseResult userUpdateHeadImage(User user, JSONObject params) {
		// TODO Auto-generated method stub
		return null;
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
			result.putAll(mineProxy.getTeamCount(teamId));//获取统计数据
			result.put("matchScheduleInfo", mineProxy.matchScheduleInfo(teamId));
			// 注意查询 signUpTotal和signUpStatus的值
			Integer roleType = user == null ? 0 : 1;// TODO 注意判断当前用户的权限
			result.put("roleType", roleType);

			return successResult("获取成功", result);
		} catch (Exception e) {
			e.printStackTrace();
			return errorExceptionResult();
		}
	}
}
