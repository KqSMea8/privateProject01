package com.project.service.service;

import java.math.BigDecimal;
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
import com.project.service.proxy.GameProxy;
import com.project.service.proxy.PlatformProxy;

import net.sf.json.JSONObject;

@Service("gameService")
public class GameService extends BaseService {

	@Autowired
	private GameProxy gameProxy;

	@Autowired
	private PlatformProxy platformProxy;

	@Autowired
	private DataSourceTransactionManager transactionManager;

	public BaseResult list(JSONObject params) {
		Integer gameStatus;
		String searchKey;
		PageForApp page;
		try {
			page = getPageEntity(params);
			gameStatus = params.getInt("gameStatus");
			searchKey = params.getString("searchKey");
		} catch (Exception e) {
			e.printStackTrace();
			return errorParamsResult();
		}

		try {
			return successResult("获取成功", gameProxy.list(page, gameStatus, searchKey));
		} catch (Exception e) {
			e.printStackTrace();
			return errorExceptionResult();
		}
	}

	public BaseResult info(User user, JSONObject params) {
		Integer gameInfoId;
		try {
			gameInfoId = params.getInt("gameInfoId");
		} catch (Exception e) {
			e.printStackTrace();
			return errorParamsResult();
		}

		try {
			Map<String, Object> result = gameProxy.info(gameInfoId, user == null ? 0 : user.getUserId());
			result.put("scoreOddsList", gameProxy.scoreOddsList(gameInfoId, 1));
			result.put("totalGoalOddsList", gameProxy.scoreOddsList(gameInfoId, 2));
			return successResult("获取成功", result);
		} catch (Exception e) {
			e.printStackTrace();
			return errorExceptionResult();
		}
	}

	public BaseResult gambling(User user, JSONObject params) {
		Integer gameInfoId, oddsId = null, type;
		BigDecimal amount, odds, estimate;
		try {
			gameInfoId = params.getInt("gameInfoId");
			type = params.getInt("type");
			if (type.intValue() == 7 || type.intValue() == 8) {
				oddsId = params.getInt("oddsId");
			}

			amount = new BigDecimal(params.getString("amount"));
			odds = new BigDecimal(params.getString("odds"));
			estimate = new BigDecimal(params.getString("estimate"));
		} catch (Exception e) {
			e.printStackTrace();
			return errorParamsResult();
		}

		TransactionStatus status = transactionManager.getTransaction(new DefaultTransactionDefinition());
		try {
			// 判断余额是否充足
			if (gameProxy.isHaveEnoughAmount(amount, user.getUserId())) {
				boolean flag = false;
				// 扣减余额
				if (amount.compareTo(BigDecimal.ZERO) == 0) {
					flag = true;
				} else {
					flag = platformProxy.diamondsRecharge(new BigDecimal("-" + amount.toString()), user.getUserId()).intValue() == 1;
				}

				flag = flag && gameProxy.gambling(gameInfoId, oddsId, type, amount, odds, estimate, user.getUserId()).intValue() == 1;

				if (flag) {
					transactionManager.commit(status);
					return successResult("购买成功");
				} else {
					transactionManager.rollback(status);
					return errorResult("购买失败!");
				}
			} else {
				return errorResult("余额不足!");
			}

		} catch (Exception e) {
			e.printStackTrace();
			transactionManager.rollback(status);
			return errorExceptionResult();
		}
	}
}
