package com.project.manager.vipparam.controller;

import java.math.BigDecimal;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.project.common.base.BaseResult;
import com.project.common.jqgrid.JQGridPageParams;
import com.project.common.jqgrid.JQGridResultEntity;
import com.project.manager.vipparam.service.VipParamService;

/*
 * VIP等级参数
 */
@Controller
@RequestMapping("/vipparam")
public class VipParamController {

	@Autowired
	VipParamService vipParamService;

	/*
	 * 跳转VIP等级参数管理页面
	 */
	@RequestMapping("vipparamindex")
	@ResponseBody
	public ModelAndView VipParamIndex(ModelAndView mv, String type) {
		mv.setViewName("vipparam/vipparamindex");
		return mv;
	}

	/*
	 * 获取VIP等级参数列表
	 */
	@RequestMapping("getvipparamlist")
	@ResponseBody
	public JQGridResultEntity<Map<String, Object>> getVipParamList(JQGridPageParams pageParams) {
		return vipParamService.getVipParamList(pageParams);
	}

	/*
	 * 删除VIP等级参数
	 */
	@RequestMapping("delete")
	@ResponseBody
	public BaseResult delete(String vipParamId, Integer level) {
		return vipParamService.delete(vipParamId, level);
	}

	/*
	 * 保存VIP等级参数信息
	 */
	@RequestMapping("save")
	@ResponseBody
	public BaseResult save(Integer vipParamId, String name, Integer level, Integer recommendTotal, Integer teamuserTotal, BigDecimal interestRate, String remark, Integer needLevel,
			Integer needLevelTotal) {
		return vipParamService.save(vipParamId, name, level, recommendTotal, teamuserTotal, interestRate, remark,needLevel,needLevelTotal);
	}
}
