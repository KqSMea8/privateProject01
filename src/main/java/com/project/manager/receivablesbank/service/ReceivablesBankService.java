package com.project.manager.receivablesbank.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.common.base.BaseResult;
import com.project.common.base.BaseService;
import com.project.common.jqgrid.JQGridPageParams;
import com.project.common.jqgrid.JQGridResultEntity;
import com.project.manager.receivablesbank.proxy.ReceivablesBankProxy;

@Service("receivablesBankService")
public class ReceivablesBankService extends BaseService {

	@Autowired
	ReceivablesBankProxy receivablesBankProxy;

	public JQGridResultEntity<Map<String, Object>> getReceivablesTypeList(JQGridPageParams pageParams) {
		JQGridResultEntity<Map<String, Object>> result = new JQGridResultEntity<Map<String, Object>>();
		// 查看列表
		List<Map<String, Object>> all = receivablesBankProxy.getReceivablesTypeList(pageParams);
		// 总条数
		long totalRecords = receivablesBankProxy.getReceivablesTypeListCount();
		return fillJQGrid(result, all, totalRecords, pageParams);
	}

	public JQGridResultEntity<Map<String, Object>> getReceivablesBankList(JQGridPageParams pageParams, Integer typeId) {
		JQGridResultEntity<Map<String, Object>> result = new JQGridResultEntity<Map<String, Object>>();
		// 查看列表
		List<Map<String, Object>> all = receivablesBankProxy.getReceivablesBankList(pageParams, typeId);
		// 总条数
		long totalRecords = receivablesBankProxy.getReceivablesBankListCount(typeId);
		return fillJQGrid(result, all, totalRecords, pageParams);
	}

	public BaseResult setReceive(String receivablesBankId, Integer typeId) {
		try {
			// receivablesBankProxy.setReceive(null, typeId);
			receivablesBankProxy.updateReceiveStatus(receivablesBankId, typeId);
			return generateResult(true, "修改状态成功!");
		} catch (Exception e) {
			e.printStackTrace();
			return generateResult(false, "系统繁忙,请稍后再试!");
		}
	}

	public BaseResult delete(String receivablesBankId) {
		try {
			receivablesBankProxy.delete(receivablesBankId);
			return generateResult(true, "删除成功!");
		} catch (Exception e) {
			e.printStackTrace();
			return generateResult(false, "系统繁忙,请稍后再试!");
		}
	}

	public BaseResult save(Integer receivablesBankId, String name, String number, String customName, String type, Integer typeId, BigDecimal conversion) {
		try {
			if (receivablesBankId == null) {
				receivablesBankProxy.addInfo(name, number, customName, type, typeId, conversion);
			} else {
				receivablesBankProxy.updateInfo(receivablesBankId, name, number, customName, type, conversion);
			}
			return generateResult(true, "保存成功!");
		} catch (Exception e) {
			e.printStackTrace();
			return generateResult(false, "系统繁忙,请稍后再试!");
		}
	}

	public BaseResult updateStatus(String receivablesTypeIds) {
		try {
			receivablesBankProxy.updateStatus(receivablesTypeIds);
			return generateResult(true, "修改状态成功!");
		} catch (Exception e) {
			e.printStackTrace();
			return generateResult(false, "系统繁忙,请稍后再试!");
		}
	}

	public BaseResult deleteType(String receivablesTypeIds) {
		try {
			receivablesBankProxy.deleteType(receivablesTypeIds);
			return generateResult(true, "删除成功!");
		} catch (Exception e) {
			e.printStackTrace();
			return generateResult(false, "系统繁忙,请稍后再试!");
		}
	}

	public BaseResult saveType(String name, String logoUrl) {
		try {
			receivablesBankProxy.addType(name, logoUrl);
			return generateResult(true, "保存成功!");
		} catch (Exception e) {
			e.printStackTrace();
			return generateResult(false, "系统繁忙,请稍后再试!");
		}
	}
}
