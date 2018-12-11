package com.project.common.interceptor;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.project.common.constants.Constants;
import com.project.manager.system.entity.SystemOperatorEntity;

public class LoginInterceptor extends HandlerInterceptorAdapter {
	private static Log log = LogFactory.getLog(LoginInterceptor.class);

	private static final String LOGIN_URL = "/";//登录页面路径

	/**
	 * 最后执行
	 */
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
		super.afterCompletion(request, response, handler, ex);
	}

	/**
	 * 显示视图前执行
	 */
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
		super.postHandle(request, response, handler, modelAndView);
	}

	/**
	 * Controller之前执行
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		HttpSession session = request.getSession(true);
		String url = request.getRequestURI();
		String contextPath = request.getContextPath();
		String key = url.replace(contextPath, "").substring(1);
		log.debug("action url:" + key);
		log.debug("contextPath url=============================:" + contextPath);
		if ((SystemOperatorEntity) session.getAttribute(Constants.SYSTEM_USER) == null) {
			reLogin(response, contextPath);
			return false;
		}
		return super.preHandle(request, response, handler);
	}

	private void reLogin(HttpServletResponse response, String contextPath) throws IOException {
		log.debug("用户未登录，返回登111111录界面！");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		out.println("<p hidden>!need login!</p>");
		out.println("<script language='javascript' type='text/javascript'>");
		out.println("alert('由于长时间没有操作，导致Session失效！' + '\\n' + '请你重新登录！');window.open('" + contextPath + LOGIN_URL + "','_top')");
		out.println("</script>");
	}
}
