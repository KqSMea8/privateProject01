package com.project.manager.system.proxy;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.project.manager.system.entity.CommonComboEntity;
import com.project.manager.system.entity.SystemOperatorEntity;

@Repository("systemProxy")
public interface SystemProxy {

	/**
	 * 获得当前用户所有权限的一级菜单权限
	 */
	public List<Map<String, Object>> getMyMainMenuPower(@Param("operator") SystemOperatorEntity operator);

	/**
	 * 获得当前用户所有权限的子级菜单权限
	 */
	public List<Map<String, Object>> getMySubMenuPower(@Param("parentId") String parentId, @Param("operator") SystemOperatorEntity operator);

	/**
	 * 获得所有参数表数据
	 */
	public List<Map<String, Object>> getAllParam();

	public List<Map<String, Object>> getAdminMainMenuPower();

	public List<Map<String, Object>> getAdminMenuPwoer(String parentId);

	public Integer setSystemParams(@Param("category") String category, @Param("value") String value);

	public List<CommonComboEntity> getMemberUserLevel();

	public Map<String, Object> getReportDataOfMoney();

	public Map<String, Object> getReportDataOfUser();

}
