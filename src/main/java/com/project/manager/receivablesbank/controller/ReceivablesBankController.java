package com.project.manager.receivablesbank.controller;

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
import com.project.manager.receivablesbank.service.ReceivablesBankService;

/*
 * 收款卡管理相关业务
 */
@Controller
@RequestMapping("/receivablesbank")
public class ReceivablesBankController {

	@Autowired
	ReceivablesBankService receivablesBankService;

	/*
	 * 跳转收款卡管理页面
	 */
	@RequestMapping("receivablesbankindex")
	@ResponseBody
	public ModelAndView receivablesBankIndex(ModelAndView mv, String type) {
		mv.setViewName("receivablesbank/receivablesbankindex");
		return mv;
	}

	/*
	 * 获取收款卡列表
	 */
	@RequestMapping("getreceivablestypelist")
	@ResponseBody
	public JQGridResultEntity<Map<String, Object>> getReceivablesTypeList(JQGridPageParams pageParams) {
		return receivablesBankService.getReceivablesTypeList(pageParams);
	}

	/*
	 * 获取收款卡列表
	 */
	@RequestMapping("getreceivablesbanklist")
	@ResponseBody
	public JQGridResultEntity<Map<String, Object>> getReceivablesBankList(JQGridPageParams pageParams, Integer typeId) {
		return receivablesBankService.getReceivablesBankList(pageParams, typeId);
	}

	/*
	 * 修改类型状态
	 */
	@RequestMapping("updatdstatus")
	@ResponseBody
	public BaseResult updateStatus(String receivablesTypeIds) {
		return receivablesBankService.updateStatus(receivablesTypeIds);
	}

	/*
	 * 修改状态
	 */
	@RequestMapping("setreceive")
	@ResponseBody
	public BaseResult setReceive(String receivablesBankId, Integer typeId) {
		return receivablesBankService.setReceive(receivablesBankId, typeId);
	}

	/*
	 * 删除收款卡
	 */
	@RequestMapping("deletetype")
	@ResponseBody
	public BaseResult deleteType(String receivablesTypeIds) {
		return receivablesBankService.deleteType(receivablesTypeIds);
	}

	/*
	 * 删除收款卡
	 */
	@RequestMapping("delete")
	@ResponseBody
	public BaseResult delete(String receivablesBankId) {
		return receivablesBankService.delete(receivablesBankId);
	}

	/*
	 * 保存收款类型
	 */
	@RequestMapping("savetype")
	@ResponseBody
	public BaseResult saveType(String name, String logoUrl) {
		return receivablesBankService.saveType(name, logoUrl);
	}

	/*
	 * 保存收款卡信息
	 */
	@RequestMapping("save")
	@ResponseBody
	public BaseResult save(Integer receivablesBankId, String name, String number, String customName, String type, Integer typeId, BigDecimal conversion) {
		return receivablesBankService.save(receivablesBankId, name, number, customName, type, typeId, conversion);
	}
}
