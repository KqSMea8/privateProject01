package com.project.manager.drawmoney.controller;

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
import com.project.manager.drawmoney.service.DrawmoneyService;

/*
 * 提现类型管理
 */
@Controller
@RequestMapping("drawmoeny")
public class DrawmoneyController extends BaseController {

	@Autowired
	DrawmoneyService drawmoneyService;

	/*
	 * 跳转列表
	 */
	@RequestMapping("drawmoneyindex")
	public ModelAndView addOperator(ModelAndView mv) {
		mv.setViewName("drawmoney/drawmoneyindex");
		return mv;
	}

	@RequestMapping("getdrawmoneylist")
	@ResponseBody
	public JQGridResultEntity<Map<String, Object>> getDrawmoneyList(JQGridPageParams pageParams) {
		return drawmoneyService.getDrawmoneyList(pageParams);
	}

	@RequestMapping("updatdstatus")
	@ResponseBody
	public BaseResult updateStatus(String drawmoneyTypeId) {
		return drawmoneyService.updateStatus(drawmoneyTypeId);
	}

	@RequestMapping("delete")
	@ResponseBody
	public BaseResult delete(String drawmoneyTypeId) {
		return drawmoneyService.delete(drawmoneyTypeId);
	}

	@RequestMapping("save")
	@ResponseBody
	public BaseResult save(Integer drawmoneyTypeId, String name, String name_th, String logourl, String converstion) {
		return drawmoneyService.save(drawmoneyTypeId, name, name_th, logourl, converstion);
	}
}
