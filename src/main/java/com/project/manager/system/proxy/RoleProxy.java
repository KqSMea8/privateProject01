package com.project.manager.system.proxy;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.project.common.jqgrid.JQGridPageParams;
import com.project.common.jqgrid.TreeNodeEntity;
import com.project.manager.system.entity.SystemOperatorEntity;
import com.project.manager.system.entity.SystemRoleEntity;

/**
 * 角色管理数据访问接口代理类
 */
@Repository("roleProxy")
public interface RoleProxy {
	/**
	 * 获取角色列表
	 * 
	 * @param startRow
	 * @param endRow
	 * @return
	 */
	public List<SystemRoleEntity> getGridData(@Param("pageParams") JQGridPageParams pageParams, @Param("operator") SystemOperatorEntity operator,
			@Param("status") Integer status);

	/**
	 * 获取角色总条数
	 * 
	 * @return
	 */
	public Long getGridDataCount(@Param("operator") SystemOperatorEntity operator, @Param("status") Integer status);

	/**
	 * 获取菜单树
	 * 
	 * @param parentId
	 * @param merchantId
	 * @return
	 */
	public List<TreeNodeEntity> getMenuTree(@Param("parentId") String parentId);

	/**
	 * 获取角色权限列表
	 * 
	 * @param roleId
	 * @return
	 */
	public List<TreeNodeEntity> getRolePermission(@Param("roleId") Integer roleId);

	public Integer isExistsByName(@Param("role") SystemRoleEntity role);

	public Integer insertRole(SystemRoleEntity role);

	public Integer isExistsByIdName(@Param("roleName") String roleName, @Param("roleId") Integer roleId);

	public Integer updateRoleName(@Param("roleName") String roleName, @Param("roleId") Integer roleId);

	public Integer updateRoleStatus(@Param("roleId") Integer roleId);

	public Integer delRolePermission(@Param("roleId") Integer roleId);

	public Integer saveRolePermission(@Param("roleId") Integer roleId, @Param("menuId") String menuId);

}
