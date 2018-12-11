package com.project.manager.vipparam.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.common.base.BaseResult;
import com.project.common.base.BaseService;
import com.project.common.jqgrid.JQGridPageParams;
import com.project.common.jqgrid.JQGridResultEntity;
import com.project.manager.vipparam.proxy.VipParamProxy;

@Service("vipParamService")
public class VipParamService extends BaseService {

	@Autowired
	VipParamProxy vipParamProxy;

	public JQGridResultEntity<Map<String, Object>> getVipParamList(JQGridPageParams pageParams) {
		JQGridResultEntity<Map<String, Object>> result = new JQGridResultEntity<Map<String, Object>>();
		// 查看列表
		List<Map<String, Object>> all = vipParamProxy.getVipParamList(pageParams);
		// 总条数
		long totalRecords = vipParamProxy.getVipParamListCount();
		return fillJQGrid(result, all, totalRecords, pageParams);
	}

	public BaseResult delete(String vipParamId, Integer level) {
		try {
			Integer maxLevelHad = vipParamProxy.getMaxLevelHad();
			if (maxLevelHad.intValue() > level) {
				return generateResult(false, "不能删除中间的VIP等级!");
			}
			vipParamProxy.delete(vipParamId);
			return generateResult(true, "删除成功!");
		} catch (Exception e) {
			e.printStackTrace();
			return generateResult(false, "系统繁忙,请稍后再试!");
		}
	}

	public BaseResult save(Integer vipParamId, String name, Integer level, Integer recommendTotal, Integer teamuserTotal, BigDecimal interestRate, String remark, Integer needLevel,
			Integer needLevelTotal) {
		try {
			if (vipParamId == null) {
				Integer maxLevelHad = vipParamProxy.getMaxLevelHad();
				maxLevelHad = maxLevelHad == null ? 0 : maxLevelHad;
				/*if ((maxLevelHad.intValue() + 1) < level || level.intValue() == 0) {
					return generateResult(false, "请按照VIP从1开始的顺序进行设置!");
				}*/
				vipParamProxy.addInfo(name, level, recommendTotal, teamuserTotal, interestRate, remark, needLevel, needLevelTotal);
			} else {
				vipParamProxy.updateInfo(vipParamId, name, level, recommendTotal, teamuserTotal, interestRate, remark, needLevel, needLevelTotal);
			}
			return generateResult(true, "保存成功!");
		} catch (Exception e) {
			e.printStackTrace();
			return generateResult(false, "系统繁忙,请稍后再试!");
		}
	}
}
