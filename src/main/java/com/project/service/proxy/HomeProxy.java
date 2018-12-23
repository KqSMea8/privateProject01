package com.project.service.proxy;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.project.common.page.PageForApp;

@Repository("homeProxy")
public interface HomeProxy {

	public List<Map<String, Object>> rotationImgList(@Param("limit") Integer limit);

	public List<Map<String, Object>> rotationNoticeList(@Param("limit") Integer limit);

	public List<Map<String, Object>> hotMatchList(@Param("cityId") Integer cityId, @Param("limit") Integer limit);

	public List<Map<String, Object>> searchMatchList(@Param("page")PageForApp page, @Param("cityId")Integer cityId, @Param("searchKey")String searchKey);

	public List<Map<String, Object>> searchTeamList(@Param("page")PageForApp page, @Param("cityId")Integer cityId, @Param("searchKey")String searchKey);

}
