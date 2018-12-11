package com.project.manager.drawmoney.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.common.base.BaseResult;
import com.project.common.base.BaseService;
import com.project.common.jqgrid.JQGridPageParams;
import com.project.common.jqgrid.JQGridResultEntity;
import com.project.manager.drawmoney.proxy.DrawmoneyProxy;

@Service("drawmoneyService")
public class DrawmoneyService extends BaseService {

	@Autowired
	DrawmoneyProxy drawmoneyProxy;

	public JQGridResultEntity<Map<String, Object>> getDrawmoneyList(JQGridPageParams pageParams) {
		JQGridResultEntity<Map<String, Object>> result = new JQGridResultEntity<Map<String, Object>>();
		// 查看列表
		List<Map<String, Object>> all = drawmoneyProxy.getDrawmoneyList(pageParams);
		// 总条数
		long totalRecords = drawmoneyProxy.getDrawmoneyListCount();
		return fillJQGrid(result, all, totalRecords, pageParams);
	}

	public BaseResult updateStatus(String drawmoneyTypeId) {
		try {
			drawmoneyProxy.updateStatus(drawmoneyTypeId);
			return generateResult(true, "修改成功!");
		} catch (Exception e) {
			e.printStackTrace();
			return generateResult(false, "系统繁忙,请稍后再试!");
		}
	}

	public BaseResult delete(String drawmoneyTypeId) {
		try {
			drawmoneyProxy.delete(drawmoneyTypeId);
			return generateResult(true, "删除成功!");
		} catch (Exception e) {
			e.printStackTrace();
			return generateResult(false, "系统繁忙,请稍后再试!");
		}
	}

	public BaseResult save(Integer drawmoneyTypeId, String name, String name_th, String logourl, String converstion) {
		try {
			// drawmoneyProxy.addInfo(name, logourl, converstion);
			if (drawmoneyTypeId == null) {
				drawmoneyProxy.addInfo(name, name_th, logourl, converstion);
			} else {
				drawmoneyProxy.updateInfo(drawmoneyTypeId, name, name_th, logourl, converstion);
			}
			return generateResult(true, "保存成功!");
		} catch (Exception e) {
			e.printStackTrace();
			return generateResult(false, "系统繁忙,请稍后再试!");
		}
	}

}
