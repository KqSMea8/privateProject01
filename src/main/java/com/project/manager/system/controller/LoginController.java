package com.project.manager.system.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.project.common.base.BaseController;
import com.project.common.base.BaseResult;
import com.project.common.constants.Constants;
import com.project.manager.system.service.SystemService;

/**
 * 登录相关 ClassName: LoginController
 */
@RequestMapping("/")
@Controller
public class LoginController extends BaseController {
	@Resource(name = "systemService")
	private SystemService systemService;

	/**
	 * 前往登录页面
	 * @param mv
	 * @return   
	 * @return ModelAndView  
	 * @date 2017年4月17日
	 */
	@RequestMapping("")
	@ResponseBody
	public ModelAndView tologin(ModelAndView mv) {
		mv.setViewName("login");
		return mv;
	}
	/**
	 * 登录
	 * 
	 * @param request
	 * @param operator
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("login")
	@ResponseBody
	public BaseResult login(String loginName, String loginPwd, HttpSession session) {
		try {
			return systemService.login(loginName, loginPwd, session);
		} catch (Exception e) {
			e.printStackTrace();
			return generateResult(false, "登录失败!");
		}
	}

	/**
	 * 跳转首页
	 * 
	 * @param request
	 * @return
	 * @return ModelAndView
	 */
	@RequestMapping("toindex")
	@ResponseBody
	public ModelAndView toIndex() {
		try {
			return systemService.toIndex();
		} catch (Exception e) {
			e.printStackTrace();
			return new ModelAndView("error");// 返回登录页面
		}
	}

	/**
	 * 退出
	 * 
	 * @param request
	 * @return
	 * @return ModelAndView
	 */
	@RequestMapping("loginOut")
	@ResponseBody
	public BaseResult loginOut(HttpSession session) {
		session.removeAttribute(Constants.SYSTEM_USER);
		return generateResult(true, "");
	}
}
