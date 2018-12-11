package com.project.manager.notice.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.common.base.BaseResult;
import com.project.common.base.BaseService;
import com.project.common.jqgrid.JQGridPageParams;
import com.project.common.jqgrid.JQGridResultEntity;
import com.project.common.shiro.ShiroUtil;
import com.project.manager.notice.proxy.NoticeProxy;
import com.project.manager.system.entity.SystemOperatorEntity;

@Service("noticeService")
public class NoticeService extends BaseService {

	@Autowired
	NoticeProxy noticeProxy;

	public JQGridResultEntity<Map<String, Object>> getNoticeList(JQGridPageParams pageParams, Integer status, Integer type) {
		JQGridResultEntity<Map<String, Object>> result = new JQGridResultEntity<Map<String, Object>>();
		// 查看列表
		List<Map<String, Object>> all = noticeProxy.getNoticeList(pageParams, status, type);
		// 总条数
		long totalRecords = noticeProxy.getNoticeListCount(status, type);
		return fillJQGrid(result, all, totalRecords, pageParams);
	}

	public BaseResult updateStatus(String noticeIds) {
		try {
			noticeProxy.updateStatus(noticeIds);
			return generateResult(true, "修改状态成功!");
		} catch (Exception e) {
			e.printStackTrace();
			return generateResult(false, "系统繁忙,请稍后再试!");
		}
	}

	public BaseResult delete(String noticeIds) {
		try {
			noticeProxy.delete(noticeIds);
			return generateResult(true, "删除成功!");
		} catch (Exception e) {
			e.printStackTrace();
			return generateResult(false, "系统繁忙,请稍后再试!");
		}
	}

	public BaseResult save(Integer noticeId, String title, String title_th, String content,  String content_th, Integer status, Integer type) {
		try {
			if (noticeId == null) {
				SystemOperatorEntity operator = ShiroUtil.getOperatorInfo();
				noticeProxy.addInfo(title, title_th, content, content_th, status, type, operator);
			} else {
				noticeProxy.updateInfo(noticeId, title, title_th, content, content_th, status, type);
			}
			return generateResult(true, "保存成功!");
		} catch (Exception e) {
			e.printStackTrace();
			return generateResult(false, "系统繁忙,请稍后再试!");
		}
	}
}
