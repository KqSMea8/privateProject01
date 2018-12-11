package com.project.manager.system.proxy;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.project.manager.system.entity.SystemMenuEntity;

/**
 * 权限操作代理接口
 */
@Repository("permissionProxy")
public interface PermissionProxy {
	/**
	 * 获取系统中所有需要验证的url权限
	 * 
	 * @return
	 */
	public List<SystemMenuEntity> getAllPermission();

	/**
	 * 获取操作员的所有需要验证的url权限
	 * 
	 * @param accountNo
	 * @return
	 */
	public List<SystemMenuEntity> getOperatorPermission(@Param("accountNo") String accountNo);

	/**
	 * 获取内置账户权限
	 * 
	 * @return
	 */
	public List<SystemMenuEntity> getSysAdminPermission();
}
