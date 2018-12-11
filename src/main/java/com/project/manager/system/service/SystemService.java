package com.project.manager.system.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import com.project.common.base.BaseResult;
import com.project.common.base.BaseService;
import com.project.common.constants.Constants;
import com.project.common.constants.GlobalConstant;
import com.project.common.shiro.ShiroUtil;
import com.project.common.util.Md5Util;
import com.project.manager.system.entity.SystemOperatorEntity;
import com.project.manager.system.proxy.OperatorProxy;
import com.project.manager.system.proxy.SystemProxy;

/**
 * 一些用户相关的管理业务层
 * 
 * @author chentianjin
 * @time 2016-08-23
 */
@Service("systemService")
public class SystemService extends BaseService {

	@Autowired
	SystemProxy systemProxy;

	@Autowired
	OperatorProxy operatorProxy;

	@Autowired
	OperatorService operatorService;

	@Autowired
	RoleService roleService;

	/**
	 * 登录验证
	 * 
	 * @param dsRequest
	 */
	public BaseResult login(String loginName, String loginPwd, HttpSession session) throws Exception {

		try {
			Subject currentUser = SecurityUtils.getSubject();
			UsernamePasswordToken token = new UsernamePasswordToken(loginName, Md5Util.getMD5(loginPwd));
			currentUser.login(token);

			SystemOperatorEntity operator = null;
			if ("admin".equals(loginName))
				operator = getAdminOperator(loginName);
			else
				operator = operatorProxy.getOperatorInfoByAccount(loginName);

			session.setAttribute(Constants.SYSTEM_USER, operator);
			ShiroUtil.cacheUserInfo(operator);

			return generateResult(true, "登录成功");
		} catch (UnknownAccountException uae) {
			return generateResult(false, "用户名或密码错误");
		} catch (IncorrectCredentialsException ice) {
			return generateResult(false, "用户名或密码错误");
		} catch (LockedAccountException lae) {
			return generateResult(false, "帐号已被锁定");
		} catch (AuthenticationException ae) {
			return generateResult(false, "认证未通过，请输入正确的用户名和密码");
		}
	}

	/**
	 * 获得所有的系统参数并且封装
	 * 
	 * @return
	 * @return Object
	 */
	public Map<String, Object> getAllParams() throws Exception {
		List<Map<String, Object>> allParams = systemProxy.getAllParam();
		Map<String, Object> data = null;
		Map<String, Object> result = new HashMap<String, Object>();
		for (int i = 0, j = allParams.size(); i < j; i++) {
			data = allParams.get(i);
			result.put(data.get("param_category").toString(), data.get("param_value"));
		}
		return result;
	}

	/**
	 * 登出
	 * 
	 * @param session
	 * @return BaseResult
	 * @author chentianjin
	 * @date 2017年4月21日
	 */
	public BaseResult loginOut(HttpSession session) {
		// SystemOperatorEntity operator = ShiroUtil.getOperatorInfo();
		session.removeAttribute(Constants.SYSTEM_USER);
		Subject currentUser = SecurityUtils.getSubject();
		currentUser.logout();

		return generateResult(true, "");
	}

	/**
	 * 准备首页信息
	 * 
	 * @param dsRequest
	 * @return ModelAndView
	 */
	public ModelAndView toIndex() throws Exception {
		SystemOperatorEntity operator = ShiroUtil.getOperatorInfo();
		ModelAndView mv = new ModelAndView();
		mv.setViewName("index");
		mv.addObject(Constants.SYSTEM_USER, operator);
		mv.addObject("menus", getMenus(operator));
		mv.addObject("systemParams", getAllParams());
		return mv;
	}

	public ModelAndView toReport() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("report/index");
		mv.addObject("data", getReportData());
		return mv;
	}
	private Map<String, Object> getReportData() {
		Map<String, Object> data = systemProxy.getReportDataOfMoney();
		data.putAll(systemProxy.getReportDataOfUser());
		//总充值， 总提现， 总手续费收益， 总会员， 总激活会员， 今日充值 ， 今日提现， 今日注册会员， 今日手续费收益
		return data;
	}

	/**
	 * 获取菜单权限
	 * 
	 * @param operator
	 * @throws Exception
	 * @return List<Map<String,Object>>
	 * @author chentianjin
	 * @date 2017年4月4日
	 */
	private List<Map<String, Object>> getMenus(SystemOperatorEntity operator) throws Exception {
		// 超级管理员菜单权限
		if (GlobalConstant.systemDefaultOperator.getOperatorAccount().equals(operator.getOperatorAccount())) {
			return getAdminMenuPwoer(systemProxy.getAdminMainMenuPower());
		}
		return getMyMenuPwoer(systemProxy.getMyMainMenuPower(operator), operator);
	}

	private List<Map<String, Object>> getAdminMenuPwoer(List<Map<String, Object>> result) {
		Map<String, Object> map = null;
		List<Map<String, Object>> subResult = null;
		boolean isHaveSubMenu = false;
		for (int i = 0, j = result.size(); i < j; i++) {
			map = result.get(i);
			subResult = systemProxy.getAdminMenuPwoer(String.valueOf(map.get("menu_id")));
			isHaveSubMenu = subResult.size() > 0;
			map.put("isHaveSubMenu", isHaveSubMenu);
			if (isHaveSubMenu) {
				subResult = getAdminMenuPwoer(subResult);// 递归子菜单
				map.put("subMenu", subResult);
			}
			result.remove(i);
			result.add(i, map);
		}
		return result;
	}

	/**
	 * 获得权限菜单
	 * 
	 * @param List
	 *            <Map<String, Object>>
	 * @throws Exception
	 * @return List<Map<String,Object>>
	 */
	private List<Map<String, Object>> getMyMenuPwoer(List<Map<String, Object>> result, SystemOperatorEntity operator) throws Exception {
		Map<String, Object> map = null;
		List<Map<String, Object>> subResult = null;
		boolean isHaveSubMenu = false;
		for (int i = 0, j = result.size(); i < j; i++) {
			map = result.get(i);
			subResult = systemProxy.getMySubMenuPower(String.valueOf(map.get("menu_id")), operator);
			isHaveSubMenu = subResult.size() > 0;
			map.put("isHaveSubMenu", isHaveSubMenu);
			if (isHaveSubMenu) {
				subResult = getMyMenuPwoer(subResult, operator);// 递归子菜单
				map.put("subMenu", subResult);
			}
			result.remove(i);
			result.add(i, map);
		}
		return result;
	}

	private SystemOperatorEntity getAdminOperator(String loginName) {
		SystemOperatorEntity operator = new SystemOperatorEntity();
		operator.setOperatorAccount(loginName);
		operator.setStatus(1);
		operator.setOperatorName("平台管理员");
		return operator;
	}

	/**
	 * 修改密码
	 * 
	 * @param oldPwd
	 * @param newPwd
	 * @return BaseResult
	 * @author chentianjin
	 * @date 2017年4月5日
	 */
	public BaseResult updatePwdOfMy(String oldPwd, String newPwd) {

		SystemOperatorEntity operator = ShiroUtil.getOperatorInfo();

		if (!operator.getOperatorPwd().equals(Md5Util.getMD5(oldPwd)))
			return generateResult(false, "原密码不正确，请重新输入!");

		operator.setOperatorPwd(Md5Util.getMD5(newPwd));

		operatorProxy.updatePwd(operator);

		ShiroUtil.cacheUserInfo(operator);

		return generateResult(true, "修改密码成功，下次登录请使用新密码登录!");
	}

	public BaseResult setSystemParams(String category, String value) {
		try {
			System.out.println(systemProxy.setSystemParams(category, value));
			return generateResult(true, "设置参数成功!");
		} catch (Exception e) {
			e.printStackTrace();
			return generateResult(false, "设置参数失败，请稍候再试!");
		}
	}

}
