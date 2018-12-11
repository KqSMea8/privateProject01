package com.project.manager.notice.controller;

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
import com.project.manager.notice.service.NoticeService;

/*
 * 公告相关业务
 */
@Controller
@RequestMapping("/notice")
public class NoticeController extends BaseController {

	@Autowired
	NoticeService noticeService;

	/*
	 * 跳转公告管理页面
	 */
	@RequestMapping("noticeindex")
	@ResponseBody
	public ModelAndView noticeIndex(ModelAndView mv, String type) {
		mv.setViewName("notice/noticeindex");
		return mv;
	}

	/*
	 * 获取公告列表
	 */
	@RequestMapping("getnoticelist")
	@ResponseBody
	public JQGridResultEntity<Map<String, Object>> getNoticeList(JQGridPageParams pageParams, Integer status, Integer type) {
		return noticeService.getNoticeList(pageParams, status, type);
	}

	/*
	 * 修改状态
	 */
	@RequestMapping("updatestatus")
	@ResponseBody
	public BaseResult updateStatus(String noticeIds) {
		return noticeService.updateStatus(noticeIds);
	}

	/*
	 * 删除公告
	 */
	@RequestMapping("delete")
	@ResponseBody
	public BaseResult delete(String noticeIds) {
		return noticeService.delete(noticeIds);
	}

	/*
	 * 保存公告信息
	 */
	@RequestMapping("save")
	@ResponseBody
	public BaseResult save(Integer noticeId, String title, String title_th, String content, String content_th, Integer status, Integer type) {
		return noticeService.save(noticeId,title, title_th, content, content_th, status,type);
	}

}
