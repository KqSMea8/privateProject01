package com.project.service.service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.project.common.base.BaseResult;
import com.project.common.base.BaseService;
import com.project.common.page.PageForApp;
import com.project.service.entity.User;
import com.project.service.proxy.CommodityProxy;

import net.sf.json.JSONObject;

@Service("commodityService")
public class CommodityService extends BaseService {

	@Autowired
	private DataSourceTransactionManager transactionManager;

	@Autowired
	private CommodityProxy commodityProxy;

	public BaseResult typeList() {
		try {
			return successResult("获取成功", commodityProxy.typeList());
		} catch (Exception e) {
			e.printStackTrace();
			return errorExceptionResult();
		}
	}

	public BaseResult listOfType(JSONObject params) {
		Integer typeId;
		String searchKey;
		PageForApp page = null;
		try {
			page = getPageEntity(params);
			typeId = params.getInt("typeId");
			searchKey = params.getString("searchKey");
		} catch (Exception e) {
			e.printStackTrace();
			return errorParamsResult();
		}
		try {

			return successResult("获取成功", commodityProxy.listOfType(page, typeId, searchKey));
		} catch (Exception e) {
			e.printStackTrace();
			return errorExceptionResult();
		}

	}

	public BaseResult info(JSONObject params) {
		Integer commodityId;
		try {
			commodityId = params.getInt("commodityId");
		} catch (Exception e) {
			e.printStackTrace();
			return errorParamsResult();
		}
		try {

			return successResult("获取成功", commodityProxy.info(commodityId));
		} catch (Exception e) {
			e.printStackTrace();
			return errorExceptionResult();
		}
	}

	public BaseResult buy(User user, JSONObject params) {
		Integer commodityId;
		Integer receiveId;
		Integer total;
		Integer payType;
		try {
			commodityId = params.getInt("commodityId");
			receiveId = params.getInt("receiveId");
			total = params.getInt("total");
			payType = params.getInt("payType");
		} catch (Exception e) {
			e.printStackTrace();
			return errorParamsResult();
		}
		try {
			Map<String, Object> result = new HashMap<String, Object>();
			TransactionStatus status = transactionManager.getTransaction(new DefaultTransactionDefinition());
			if (commodityProxy.buy(commodityId, receiveId, total, payType, user).intValue() == 1) {
				transactionManager.commit(status);

				result.put("orderId", user.getTempId());
				return successResult("创建订单成功", result);
			} else {
				transactionManager.rollback(status);
				return errorResult("创建订单失败!");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return errorExceptionResult();
		}
	}

	public BaseResult update(User user, JSONObject params) {
		Integer orderId;
		Integer status;
		try {
			orderId = params.getInt("orderId");
			status = params.getInt("status");
		} catch (Exception e) {
			e.printStackTrace();
			return errorParamsResult();
		}
		try {
			Map<String, Object> result = new HashMap<String, Object>();
			if (commodityProxy.update(orderId, status).intValue() == 1) {
				result.put("orderId", user.getTempId());
				return successResult("修改成功", result);
			} else {
				return errorResult("修改失败!");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return errorExceptionResult();
		}
	}

	// =============================================钻石相关=============================================================

	public BaseResult diamondsRecharge(User user, JSONObject params) {
		BigDecimal rechargeTotal;
		BigDecimal cashTotal;
		try {
			rechargeTotal = new BigDecimal(params.getString("rechargeTotal"));
			cashTotal = new BigDecimal(params.getDouble("cashTotal"));
		} catch (Exception e) {
			e.printStackTrace();
			return errorParamsResult();
		}
		TransactionStatus status = transactionManager.getTransaction(new DefaultTransactionDefinition());
		try {
			boolean result = commodityProxy.userRecharge(rechargeTotal, cashTotal, user.getUserId()).intValue() == 1;
			result = result && commodityProxy.diamondsRecharge(rechargeTotal, user.getUserId()).intValue() == 1;
			if (result) {
				transactionManager.commit(status);
				return successResult("充值成功", result);
			} else {
				transactionManager.rollback(status);
				return errorResult("充值失败!");
			}
		} catch (Exception e) {
			e.printStackTrace();
			transactionManager.rollback(status);
			return errorExceptionResult();
		}
	}

	public BaseResult diamondsCommodityList(User user, JSONObject params) {
		String searchKey;
		PageForApp page = null;
		try {
			searchKey = params.getString("searchKey");
			page = getPageEntity(params);
		} catch (Exception e) {
			e.printStackTrace();
			return errorParamsResult();
		}
		try {
			Map<String, Object> result = commodityProxy.getDiamonds(user.getUserId());
			result.put("commodityList", commodityProxy.diamondsCommodityList(page, searchKey));
			return successResult("获取成功", result);
		} catch (Exception e) {
			e.printStackTrace();
			return errorExceptionResult();
		}
	}

	public BaseResult diamondsCommodityInfo(User user, JSONObject params) {
		Integer commodityId;
		try {
			commodityId = params.getInt("commodityId");
		} catch (Exception e) {
			e.printStackTrace();
			return errorParamsResult();
		}
		try {
			Map<String, Object> result = commodityProxy.info(commodityId);
			return successResult("获取成功", result);
		} catch (Exception e) {
			e.printStackTrace();
			return errorExceptionResult();
		}
	}

	public BaseResult diamondsCommodityBuy(User user, JSONObject params) {
		Integer commodityId, receiveId;
		BigDecimal commodityPrice;
		try {
			commodityId = params.getInt("commodityId");
			receiveId = params.getInt("receiveId");
			commodityPrice = new BigDecimal("-" + params.getString("commodityPrice"));
		} catch (Exception e) {
			e.printStackTrace();
			return errorParamsResult();
		}
		TransactionStatus status = transactionManager.getTransaction(new DefaultTransactionDefinition());
		try {
			boolean result = false;
			result = commodityProxy.buy(commodityId, receiveId, 1, 0, user).intValue() == 1;
			result = result && commodityProxy.diamondsRecharge(commodityPrice, user.getUserId()).intValue() == 1;

			if (result) {
				transactionManager.commit(status);
				return successResult("购买成功");
			} else {
				transactionManager.rollback(status);
				return errorResult("购买失败");
			}

		} catch (Exception e) {
			e.printStackTrace();
			transactionManager.rollback(status);
			return errorExceptionResult();
		}
	}

	public BaseResult diamondsCommodityOrder(User user, JSONObject params) {
		PageForApp page = null;
		try {
			page = getPageEntity(params);
		} catch (Exception e) {
			e.printStackTrace();
			return errorParamsResult();
		}
		try {
			return successResult("获取成功", commodityProxy.diamondsCommodityList(page, user.getUserId()));
		} catch (Exception e) {
			e.printStackTrace();
			return errorExceptionResult();
		}
	}

	public BaseResult diamondsRechargelog(User user, JSONObject params) {
		PageForApp page = null;
		try {
			page = getPageEntity(params);
		} catch (Exception e) {
			e.printStackTrace();
			return errorParamsResult();
		}
		try {
			return successResult("获取成功", commodityProxy.diamondsRechargelog(page, user.getUserId()));
		} catch (Exception e) {
			e.printStackTrace();
			return errorExceptionResult();
		}
	}

}
