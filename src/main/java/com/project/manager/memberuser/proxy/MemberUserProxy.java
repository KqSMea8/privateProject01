package com.project.manager.memberuser.proxy;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.project.common.jqgrid.JQGridPageParams;
import com.project.manager.system.entity.SystemOperatorEntity;
import com.project.manager.vipparam.entity.VipParams;

@Repository("memberUserProxy")
public interface MemberUserProxy {

	// ===============================================================会员管理相关=================================================================
	public List<Map<String, Object>> getMemberUserList(@Param("pageParams") JQGridPageParams pageParams, @Param("status") Integer status, @Param("userName") String userName,
			@Param("userAccount") String userAccount, @Param("userTel") String userTel);

	public long getMemberUserListCount(@Param("status") Integer status, @Param("userName") String userName, @Param("userAccount") String userAccount, @Param("userTel") String userTel);

	public Integer edit(@Param("userId") Integer userId, @Param("userName") String userName, @Param("userTel") String userTel, @Param("userBankNumber") String userBankNumber,
			@Param("userBankName") String userBankName);

	public Integer updateStatus(@Param("userId") Integer userId, @Param("status") int status);

	public Integer setVipLevel(@Param("userId") Integer userId, @Param("level") Integer level, @Param("totalGroupUser") Integer totalGroupUser, @Param("status") Integer status);

	// ===============================================================会员管理相关=================================================================
	// ===============================================================会员充值/提现相关=================================================================
	public List<Map<String, Object>> getUserDealLogList(@Param("pageParams") JQGridPageParams pageParams, @Param("type") Integer type, @Param("status") Integer status,
			@Param("userName") String userName, @Param("userAccount") String userAccount, @Param("auditOperatorName") String auditOperatorName);

	public long getUserDealLogListCount(@Param("type") Integer type, @Param("status") Integer status, @Param("userName") String userName, @Param("userAccount") String userAccount,
			@Param("auditOperatorName") String auditOperatorName);

	public Integer auditDealLog(@Param("dealLogIds") String dealLogIds, @Param("status") Integer status, @Param("remark") String remark, @Param("operetor") SystemOperatorEntity operetor);

	public Integer getTotalNoCanWithdrawals(@Param("dealLogIds") String dealLogIds);

	public Integer auditUserBalance(@Param("dealLogIds") String dealLogIds, @Param("type") Integer type);

	// ===============================================================会员充值/提现相关=================================================================

	// ===============================================================定时任务=================================================================
	public Integer getOpenJudgeCorpseFlag();

	public Integer frozenUserByCondition();

	public Integer deleteUserByCondition();

	public List<Integer> getUserIdList();

	public List<VipParams> getVipParams();

	public Map<String, Object> getDirectUserInfo(@Param("userIds") String userIds);

	public Integer needLevelTotal(@Param("userIds") String userIds, @Param("vipLevel") Integer vipLevel, @Param("needLevelTotal") Integer needLevelTotal);
	// ===============================================================定时任务=================================================================

	public Integer backOpenDeduction();

}
