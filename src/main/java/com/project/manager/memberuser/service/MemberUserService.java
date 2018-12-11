package com.project.manager.memberuser.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.common.base.BaseResult;
import com.project.common.base.BaseService;
import com.project.common.jqgrid.JQGridPageParams;
import com.project.common.jqgrid.JQGridResultEntity;
import com.project.common.shiro.ShiroUtil;
import com.project.manager.memberuser.proxy.MemberUserProxy;
import com.project.manager.system.entity.SystemOperatorEntity;
import com.project.manager.system.service.SystemService;
import com.project.manager.vipparam.entity.VipParams;

@Service("memberUserService")
public class MemberUserService extends BaseService {

	@Autowired
	MemberUserProxy memberUserProxy;

	@Autowired
	SystemService systemService;

	// ===============================================================会员管理相关=================================================================
	public JQGridResultEntity<Map<String, Object>> getMemberUserList(JQGridPageParams pageParams, Integer status, String userName, String userAccount, String userTel) {
		JQGridResultEntity<Map<String, Object>> result = new JQGridResultEntity<Map<String, Object>>();
		// 查看列表
		List<Map<String, Object>> all = memberUserProxy.getMemberUserList(pageParams, status, userName, userAccount, userTel);
		// 总条数
		long totalRecords = memberUserProxy.getMemberUserListCount(status, userName, userAccount, userTel);
		return fillJQGrid(result, all, totalRecords, pageParams);
	}

	public BaseResult edit(Integer userId, String userName, String userTel, String userBankNumber, String userBankName) {
		try {
			memberUserProxy.edit(userId, userName, userTel, userBankNumber, userBankName);
			return generateResult(true, "修改成功!");
		} catch (Exception e) {
			e.printStackTrace();
			return generateResult(false, "系统繁忙,请稍后再试!");
		}
	}

	public BaseResult updateStatus(Integer userId, Integer status) {
		try {
			memberUserProxy.updateStatus(userId, status);
			return generateResult(true, "修改状态成功!");
		} catch (Exception e) {
			e.printStackTrace();
			return generateResult(false, "系统繁忙,请稍后再试!");
		}
	}

	public BaseResult setVipLevel(Integer userId, Integer level) {
		try {
			memberUserProxy.setVipLevel(userId, level, null, 1);
			return generateResult(true, "修改状态成功!");
		} catch (Exception e) {
			e.printStackTrace();
			return generateResult(false, "系统繁忙,请稍后再试!");
		}
	}

	public BaseResult calculationVipLevel() {
		try {
			this.userCorpseJudgeTask();

			this.calculationUserLevelTask();
		} catch (Exception e) {
			e.printStackTrace();
			return generateResult(false, "程序出错");
		}
		return generateResult(true, "执行成功");
	}
	// ===============================================================会员管理相关=================================================================
	// ===============================================================会员充值相关=================================================================

	public JQGridResultEntity<Map<String, Object>> getUserRechargeList(JQGridPageParams pageParams, Integer status, String userName, String userAccount, String auditOperatorName) {
		JQGridResultEntity<Map<String, Object>> result = new JQGridResultEntity<Map<String, Object>>();
		// 查看列表
		List<Map<String, Object>> all = memberUserProxy.getUserDealLogList(pageParams, 1, status, userName, userAccount, auditOperatorName);
		// 总条数
		long totalRecords = memberUserProxy.getUserDealLogListCount(1, status, userName, userAccount, auditOperatorName);
		return fillJQGrid(result, all, totalRecords, pageParams);
	}

	public BaseResult auditRecharge(String dealLogIds, Integer status, String remark) {
		SystemOperatorEntity operetor = ShiroUtil.getOperatorInfo();
		try {
			memberUserProxy.auditDealLog(dealLogIds, status, remark, operetor);
			if (status.intValue() == 1) {
				memberUserProxy.auditUserBalance(dealLogIds, 1);
			}
			return generateResult(true, "修改状态成功!");
		} catch (Exception e) {
			e.printStackTrace();
			return generateResult(false, "系统繁忙,请稍后再试!");
		}
	}

	// ===============================================================会员充值相关=================================================================
	// ===============================================================会员提现相关=================================================================

	public JQGridResultEntity<Map<String, Object>> getUserWithdrawalsList(JQGridPageParams pageParams, Integer status, String userName, String userAccount, String auditOperatorName) {
		JQGridResultEntity<Map<String, Object>> result = new JQGridResultEntity<Map<String, Object>>();
		// 查看列表
		List<Map<String, Object>> all = memberUserProxy.getUserDealLogList(pageParams, 2, status, userName, userAccount, auditOperatorName);
		// 总条数
		long totalRecords = memberUserProxy.getUserDealLogListCount(2, status, userName, userAccount, auditOperatorName);
		try {
			this.calculationUserLevelTask();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return fillJQGrid(result, all, totalRecords, pageParams);
	}

	public BaseResult auditWithdrawals(String dealLogIds, Integer status, String remark) {
		SystemOperatorEntity operetor = ShiroUtil.getOperatorInfo();
		try {
			// Integer totalNoCanWithdrawals =
			// memberUserProxy.getTotalNoCanWithdrawals(dealLogIds);
			// if (status.intValue() == 1 && totalNoCanWithdrawals.intValue() > 0) {
			// return generateResult(false, "您选择的申请中有存在申请金额大于用户余额情况,请仔细核查后在操作!");
			// }

			memberUserProxy.auditDealLog(dealLogIds, status, remark, operetor);
			if (status.intValue() == 2) {
				memberUserProxy.auditUserBalance(dealLogIds, 2);
			}
			return generateResult(true, "修改状态成功!");
		} catch (Exception e) {
			e.printStackTrace();
			return generateResult(false, "系统繁忙,请稍后再试!");
		}
	}

	// ===============================================================会员提现相关=================================================================

	/*
	 * 执行判断是否注册用户为僵尸用户
	 */
	@Transactional(rollbackFor = Exception.class)
	public void userCorpseJudgeTask() throws Exception {
		/**
		 * 僵尸用户判定规则？(前期先不冻结, 人数少) <br>
		 * a.一周没有下注记录&账户没有余额 【冻结， 15天直接删除】<br>
		 * b.一周没有下注记录冻结，有理财产品除外【充值自动激活】<br>
		 * c.一周没有余额冻结【充值自动激活】<br>
		 * 
		 * 总结：<br>
		 * 1、7天没有下注记录、没有余额、没有买理财产品【冻结】，充值自动【激活】，或者手动【激活】<br>
		 * 2、15天没有下注记录、没有余额、没有买理财产品【直接删除】
		 */
		try {
			// 1、判断是否开启
			Integer isOpenJudgeCorpse = memberUserProxy.getOpenJudgeCorpseFlag();
			if (isOpenJudgeCorpse.intValue() == 1) {
				// 2、根据条件冻结帐号
				memberUserProxy.frozenUserByCondition();
				// 3、根据条件删除帐号
				memberUserProxy.deleteUserByCondition();
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception();
		}
	}

	/*
	 * 计算符合条件的用户Vip等级
	 */
	@Transactional(rollbackFor = Exception.class)
	public void calculationUserLevelTask() throws Exception {

		try {
			// 获取所以已经激活，并且需要计算等级的用户
			List<Integer> userIdList = memberUserProxy.getUserIdList();
			// 获取所有vip等级参数
			List<VipParams> vipParams = memberUserProxy.getVipParams();
			Map<String, Object> systemParams = systemService.getAllParams();
			Integer totalLevelGroupEarnings = Integer.parseInt(systemParams.get("totalLevelGroupEarnings").toString());
			for (Integer userId : userIdList) {
				this.calculationOneUserLevel(userId, totalLevelGroupEarnings, vipParams);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception();
		}
	}

	private void calculationOneUserLevel(Integer userId, Integer totalLevelGroupEarnings, List<VipParams> vipParams) {
		// 1、获取直推用户信息
		Map<String, Object> userInfo = memberUserProxy.getDirectUserInfo(userId.toString());
		String userIds = userInfo.get("userIds").toString();
		String directUserIds = userIds;
		Integer totalDirectUser = Integer.parseInt(userInfo.get("totalUser").toString());
		Integer totalGroupUser = totalDirectUser;
		Integer level = 0;

		// 获取团队人数
		for (int i = 0; i < totalLevelGroupEarnings - 1; i++) {
			userInfo = memberUserProxy.getDirectUserInfo(userIds);
			if (Integer.parseInt(userInfo.get("totalUser").toString()) < 1) {
				break;
			}
			totalGroupUser += Integer.parseInt(userInfo.get("totalUser").toString());
			userIds = userInfo.get("userIds").toString();
		}
		
		for (VipParams vip : vipParams) {
			if (vip.getDirectTotal() <= totalDirectUser && vip.getGroupTotal() <= totalGroupUser) {
				if (vip.getNeedLevel() != null && memberUserProxy.needLevelTotal(directUserIds, vip.getNeedLevel(), vip.getNeedLevelTotal()) <= 0) {
					break;
				} else {
					level = vip.getVipLevel();
				}
			} else {
				break;
			}
		}

		memberUserProxy.setVipLevel(userId, level, totalGroupUser.intValue() == 0 ? null : totalGroupUser, null);
	}

	/*
	 * 返还扣点
	 */
	public void backOpenDeduction() {
		memberUserProxy.backOpenDeduction();
	}
}
