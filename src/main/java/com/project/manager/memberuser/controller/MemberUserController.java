package com.project.manager.memberuser.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.project.common.base.BaseController;
import com.project.common.base.BaseResult;
import com.project.common.jqgrid.JQGridPageParams;
import com.project.common.jqgrid.JQGridResultEntity;
import com.project.manager.memberuser.service.MemberUserService;

/*
 * 注册用户相关业务
 */
@Controller
@RequestMapping("/memberuser")
public class MemberUserController extends BaseController {

	@Autowired
	MemberUserService memberUserService;

	// ===============================================================会员管理相关=================================================================
	/*
	 * 跳转注册用户管理页面
	 */
	@RequestMapping("memberuserindex")
	@ResponseBody
	public ModelAndView memberUserIndex(ModelAndView mv) {
		mv.setViewName("memberuser/memberuserindex");
		return mv;
	}

	/*
	 * 跳转注册用户列表页面
	 */
	@RequestMapping("memberuserlist")
	@ResponseBody
	public ModelAndView memberUserList(ModelAndView mv) {
		mv.setViewName("memberuser/memberuserlist");
		return mv;
	}

	/*
	 * 跳转僵尸注册用户列表页面
	 */
	@RequestMapping("memberuserdeathlist")
	@ResponseBody
	public ModelAndView memberUserDeathList(ModelAndView mv) {
		mv.setViewName("memberuser/memberuserdeathlist");
		return mv;
	}

	/*
	 * 获取活跃会员列表
	 */
	@RequestMapping("getmemberuserlist")
	@ResponseBody
	public JQGridResultEntity<Map<String, Object>> getMemberUserList(JQGridPageParams pageParams, Integer status, String userName, String userAccount, String userTel) {
		return memberUserService.getMemberUserList(pageParams, status, userName, userAccount, userTel);
	}

	/*
	 * 获取僵尸会员列表
	 */
	@RequestMapping("getmemberuserdeathlist")
	@ResponseBody
	public JQGridResultEntity<Map<String, Object>> getMemberUserDeathList(JQGridPageParams pageParams, String userName, String userAccount, String userTel) {
		return memberUserService.getMemberUserList(pageParams, 3, userName, userAccount, userTel);
	}

	/*
	 * 修改会员
	 */
	@RequestMapping("edit")
	@ResponseBody
	public BaseResult edit(Integer userId, String userName, String userTel, String userBankNumber, String userBankName) {
		return memberUserService.edit(userId, userName, userTel, userBankNumber, userBankName);
	}

	/*
	 * 冻结用户
	 */
	@RequestMapping("lock")
	@ResponseBody
	public BaseResult lock(Integer userId) {
		return memberUserService.updateStatus(userId, 2);
	}

	/*
	 * 指定VIP等级
	 */
	@RequestMapping("setviplevel")
	@ResponseBody
	public BaseResult setVipLevel(Integer userId, Integer level) {
		return memberUserService.setVipLevel(userId, level);
	}

	@RequestMapping("open")
	@ResponseBody
	public BaseResult open(Integer userId) {
		return memberUserService.updateStatus(userId, 1);
	}

	/*
	 * 解冻用户
	 */
	@RequestMapping("activation")
	@ResponseBody
	public BaseResult activation(Integer userId) {
		return memberUserService.updateStatus(userId, 1);
	}

	/*
	 * 计算会员等级
	 */
	@RequestMapping("calculationviplevel")
	@ResponseBody
	public BaseResult calculationVipLevel() {
		return memberUserService.calculationVipLevel();
	}
	// ===============================================================会员管理相关=================================================================
	// ===============================================================会员充值相关=================================================================

	/*
	 * 跳转会员充值管理页面
	 */
	@RequestMapping("userrechargeindex")
	@ResponseBody
	public ModelAndView userRechargeIndex(ModelAndView mv) {
		mv.setViewName("memberuser/recharge/userrechargeindex");
		return mv;
	}

	/*
	 * 会员充值申请列表
	 */
	@RequestMapping("getuserrechargelist")
	@ResponseBody
	public JQGridResultEntity<Map<String, Object>> getUserRechargeList(JQGridPageParams pageParams, Integer status, String userName, String userAccount, String auditOperatorName) {
		return memberUserService.getUserRechargeList(pageParams, status, userName, userAccount, auditOperatorName);
	}

	/*
	 * 充值审核
	 */
	@RequestMapping("auditrecharge")
	@ResponseBody
	public BaseResult auditRecharge(String dealLogIds, Integer status, String remark) {
		return memberUserService.auditRecharge(dealLogIds, status, remark);
	}
	// ===============================================================会员充值相关=================================================================
	// ===============================================================会员提现相关=================================================================

	/*
	 * 跳转会员提现管理页面
	 */
	@RequestMapping("userwithdrawalsindex")
	@ResponseBody
	public ModelAndView userWithdrawalsIndex(ModelAndView mv) {
		mv.setViewName("memberuser/withdrawals/userwithdrawalsindex");
		return mv;
	}

	/*
	 * 会员提现申请列表
	 */
	@RequestMapping("getuserwithdrawalslist")
	@ResponseBody
	public JQGridResultEntity<Map<String, Object>> getUserWithdrawalsList(JQGridPageParams pageParams, Integer status, String userName, String userAccount, String auditOperatorName) {
		return memberUserService.getUserWithdrawalsList(pageParams, status, userName, userAccount, auditOperatorName);
	}

	/*
	 * 提现审核
	 */
	@RequestMapping("auditwithdrawals")
	@ResponseBody
	public BaseResult auditWithdrawals(String dealLogIds, Integer status, String remark) {
		return memberUserService.auditWithdrawals(dealLogIds, status, remark);
	}
	// ===============================================================会员提现相关=================================================================

}
