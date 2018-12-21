package com.project.service.proxy;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.project.common.page.PageForApp;
import com.project.service.entity.User;

@Repository("informationProxy")
public interface InformationProxy {

	public List<Map<String, Object>> typeList();

	public List<Map<String, Object>> listOfType(@Param("page") PageForApp page, @Param("typeId") Integer typeId);

	public Map<String, Object> info(@Param("informationId") Integer informationId);

	public Integer addBrowe(@Param("informationId") Integer informationId);

	public List<Map<String, Object>> commentlist(@Param("page") PageForApp page, @Param("informationId") Integer informationId);

	public Integer comment(@Param("commentContent") String commentContent, @Param("informationId") Integer informationId, @Param("user") User user);
}
