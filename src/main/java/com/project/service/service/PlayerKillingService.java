package com.project.service.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.common.base.BaseResult;
import com.project.common.base.BaseService;
import com.project.common.page.PageForApp;
import com.project.service.entity.User;
import com.project.service.proxy.PlayerKillingProxy;
import com.project.service.reqentity.pk.PlayerKillingApplyReqEntity;
import com.project.service.reqentity.pk.PlayerKillingSaveReqEntity;
import com.project.service.reqentity.pk.PlayerKillingSelectReqEntity;

import net.sf.json.JSONObject;

@Service("playerKillingService")
public class PlayerKillingService extends BaseService {

	@Autowired
	private PlayerKillingProxy prPlayerKillingProxy;

	public BaseResult list(String paramsStr) {
		PlayerKillingSelectReqEntity paramEntity;
		PageForApp page;
		try {
			page = getPageEntity(getJSONObjectParams(paramsStr));
			paramEntity = json2Entity(paramsStr, PlayerKillingSelectReqEntity.class);
			if (!paramEntity.validate())
				return errorParamsResult(paramEntity.getValidateErrorMsg());

		} catch (Exception e) {
			e.printStackTrace();
			return errorParamsResult();
		}

		try {
			return successResult("获取成功", prPlayerKillingProxy.list(page, paramEntity));
		} catch (Exception e) {
			e.printStackTrace();
			return errorExceptionResult();
		}
	}

	public BaseResult info(User user, JSONObject params) {
		Integer pkInfoId;
		try {
			pkInfoId = params.getInt("pkInfoId");
		} catch (Exception e) {
			e.printStackTrace();
			return errorParamsResult();
		}

		try {
			Map<String, Object> result = prPlayerKillingProxy.info(pkInfoId);
			Integer roleType = user == null ? 0 : user.getUserId().compareTo(Integer.parseInt(result.get("createUserId").toString())) == 0 ? 1 : 0;
			result.put("roleType", roleType);
			result.put("applyTeams", prPlayerKillingProxy.getApplyTeams(pkInfoId));
			return successResult("获取成功", result);
		} catch (Exception e) {
			e.printStackTrace();
			return errorExceptionResult();
		}
	}

	public BaseResult release(User user, String paramsStr) {
		PlayerKillingSaveReqEntity paramEntity;
		try {
			paramEntity = json2Entity(paramsStr, PlayerKillingSaveReqEntity.class);
			if (!paramEntity.validate())
				return errorParamsResult(paramEntity.getValidateErrorMsg());

		} catch (Exception e) {
			e.printStackTrace();
			return errorParamsResult();
		}

		try {
			boolean result = false;
			if (paramEntity.getPkInfoId() == null) {
				result = prPlayerKillingProxy.create(paramEntity, user).intValue() == 1;
			} else {
				result = prPlayerKillingProxy.edit(paramEntity, user).intValue() == 1;
			}

			if (result) {
				return successResult("申请成功");
			} else {
				return errorResult("申请失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return errorExceptionResult();
		}
	}

	public BaseResult delete(User user, JSONObject params) {
		Integer pkInfoId;
		try {
			pkInfoId = params.getInt("pkInfoId");
		} catch (Exception e) {
			e.printStackTrace();
			return errorParamsResult();
		}

		try {
			Integer roleType = prPlayerKillingProxy.delete(pkInfoId, user.getUserId());
			if (roleType.intValue() == 1) {
				return successResult("删除成功");
			} else {
				return errorResult("删除失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return errorExceptionResult();
		}
	}

	public BaseResult confirm(User user, JSONObject params) {
		Integer pkInfoId, applyTeamId;
		try {
			pkInfoId = params.getInt("pkInfoId");
			applyTeamId = params.getInt("applyTeamId");
		} catch (Exception e) {
			e.printStackTrace();
			return errorParamsResult();
		}

		try {
			Integer roleType = prPlayerKillingProxy.confirm(pkInfoId, applyTeamId, user.getUserId());
			if (roleType.intValue() == 1) {
				return successResult("删除成功");
			} else {
				return errorResult("删除失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return errorExceptionResult();
		}
	}

	public BaseResult apply(User user, String paramsStr) {
		PlayerKillingApplyReqEntity paramEntity;
		try {
			paramEntity = json2Entity(paramsStr, PlayerKillingApplyReqEntity.class);
			if (!paramEntity.validate())
				return errorParamsResult(paramEntity.getValidateErrorMsg());

		} catch (Exception e) {
			e.printStackTrace();
			return errorParamsResult();
		}

		try {
			if (prPlayerKillingProxy.apply(paramEntity, user).intValue() == 1) {
				return successResult("申请成功");
			} else {
				return errorResult("申请失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return errorExceptionResult();
		}
	}

}
