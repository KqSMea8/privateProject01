package com.project.service.proxy;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.project.common.page.PageForApp;
import com.project.service.entity.User;

@Repository("commodityProxy")
public interface CommodityProxy {

	public List<Map<String, Object>> typeList();

	public List<Map<String, Object>> listOfType(@Param("page") PageForApp page, @Param("typeId") Integer typeId, @Param("searchKey") String searchKey);

	public Map<String, Object> info(@Param("commodityId") Integer commodityId);

	public Integer buy(@Param("commodityId") Integer commodityId, @Param("receiveId") Integer receiveId, @Param("total") Integer total, @Param("user") User user);

}
