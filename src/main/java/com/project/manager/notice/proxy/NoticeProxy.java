package com.project.manager.notice.proxy;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.project.common.jqgrid.JQGridPageParams;
import com.project.manager.system.entity.SystemOperatorEntity;

@Repository("noticeProxy")
public interface NoticeProxy {

	public List<Map<String, Object>> getNoticeList(@Param("pageParams") JQGridPageParams pageParams, @Param("status") Integer status, @Param("type") Integer type);

	public long getNoticeListCount(@Param("status") Integer status, @Param("type") Integer type);

	public Integer updateStatus(@Param("noticeIds") String noticeIds);

	public Integer delete(@Param("noticeIds") String noticeIds);

	public Integer addInfo(@Param("title") String title, @Param("title_th") String title_th,@Param("content") String content, @Param("content_th") String content_th,@Param("status") Integer status, @Param("type") Integer type, @Param("operator") SystemOperatorEntity operator);

	public Integer updateInfo(@Param("noticeId") Integer noticeId, @Param("title") String title, @Param("title_th") String title_th,@Param("content") String content, @Param("content_th") String content_th, @Param("status") Integer status, @Param("type") Integer type);

}
