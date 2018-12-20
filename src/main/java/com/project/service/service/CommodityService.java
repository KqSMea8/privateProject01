package com.project.service.service;

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
		try {
			commodityId = params.getInt("commodityId");
			receiveId = params.getInt("receiveId");
			total = params.getInt("total");
		} catch (Exception e) {
			e.printStackTrace();
			return errorParamsResult();
		}
		try {
			TransactionStatus status = transactionManager.getTransaction(new DefaultTransactionDefinition());
			if (commodityProxy.buy(commodityId, receiveId, total, user).intValue() == 1) {
				transactionManager.commit(status);
				return successResult("创建订单成功");
			} else {
				transactionManager.rollback(status);
				return errorResult("创建订单失败!");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return errorExceptionResult();
		}
	}

}
