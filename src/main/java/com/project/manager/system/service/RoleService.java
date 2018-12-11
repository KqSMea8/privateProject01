package com.project.manager.system.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.common.base.BaseResult;
import com.project.common.base.BaseService;
import com.project.common.jqgrid.JQGridPageParams;
import com.project.common.jqgrid.JQGridResultEntity;
import com.project.common.jqgrid.TreeNodeEntity;
import com.project.common.shiro.ShiroUtil;
import com.project.manager.system.entity.SystemOperatorEntity;
import com.project.manager.system.entity.SystemRoleEntity;
import com.project.manager.system.proxy.OperatorProxy;
import com.project.manager.system.proxy.RoleProxy;

/**
 * 角色管理业务类
 */
@Service("roleService")
public class RoleService extends BaseService {
	@Autowired
	private RoleProxy roleProxy;
	@Autowired
	private OperatorProxy operatorProxy;

	/**
	 * 获取角色列表
	 * 
	 * @param pageParams
	 * @return
	 */
	public JQGridResultEntity<SystemRoleEntity> getGridData(JQGridPageParams pageParams, Integer status) {
		JQGridResultEntity<SystemRoleEntity> result = new JQGridResultEntity<SystemRoleEntity>();

		SystemOperatorEntity operator = ShiroUtil.getOperatorInfo();
		// 分页列表
		List<SystemRoleEntity> list = roleProxy.getGridData(pageParams, operator, status);

		// 总行数
		long totalRecords = roleProxy.getGridDataCount(operator, status);

		return fillJQGrid(result, list, totalRecords, pageParams);
	}

	/**
	 * 生成权限树
	 * 
	 * @param currentNode
	 * @param nodeId
	 * @return
	 */
	public TreeNodeEntity getMenuTree(TreeNodeEntity currentNode, String parentId) {
		List<TreeNodeEntity> tree = roleProxy.getMenuTree(parentId);

		if (null != tree && tree.size() > 0) {
			for (TreeNodeEntity node : tree) {
				getMenuTree(node, node.getId());

				Map<String, Object> attr = new HashMap<String, Object>();
				attr.put("url", node.getMenuUrl());
				attr.put("isButton", node.getIsButton());
				attr.put("parentId", node.getParentId());
				attr.put("sort", node.getSort());
				attr.put("menuName", node.getMenuName());
				attr.put("status", node.getStatus());
				node.setAttributes(attr);
			}

			currentNode.setChildren(tree);
		}

		return currentNode;
	}

	/**
	 * 获取角色权限列表
	 * 
	 * @param roleId
	 * @return
	 */
	public List<TreeNodeEntity> getRolePermission(Integer roleId) {
		return roleProxy.getRolePermission(roleId);
	}

	/**
	 * 创建角色
	 * 
	 * @param role
	 * @return
	 * @throws Exception
	 */
	public BaseResult create(SystemRoleEntity role) {
		try {
			if (roleProxy.isExistsByName(role) > 0) {
				return generateResult(false, "创建角色失败，角色名称【" + role.getRoleName() + "】已被占用!");
			}

			if (roleProxy.insertRole(role) == 1) {
				return generateResult(true, "创建角色成功!");
			} else {
				return generateResult(false, "创建角色失败，数据库操作失败!");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return generateResult(false, "创建角色失败，请稍候再试!!");
		}
	}

	/**
	 * 修改角色名称
	 * 
	 * @param roleName
	 * @param roleId
	 * @return
	 */
	public BaseResult update(String roleName, Integer roleId) {
		// 公司名称排重
		int ret = roleProxy.isExistsByIdName(roleName, roleId);
		if (ret >= 1) {
			return generateResult(false, "修改角色失败，角色名称【" + roleName + "】已被占用!");
		}

		ret = roleProxy.updateRoleName(roleName, roleId);
		if (ret == 1) {
			return generateResult(true, "修改角色成功!");
		} else {
			return generateResult(false, "修改角色失败，数据库操作失败!");
		}
	}

	/**
	 * 禁用启用角色
	 * 
	 * @param roleId
	 * @return
	 */
	public BaseResult lock(final Integer roleId) {
		int ret = roleProxy.updateRoleStatus(roleId);
		if (ret == 1) {
			// 刷新使用该角色的操作员权限
			new Thread() {
				public void run() {
					List<SystemOperatorEntity> list = operatorProxy.getOperatorByRoleId(roleId);
					if (null != list && list.size() > 0) {
						for (SystemOperatorEntity operator : list) {
							ShiroUtil.refreshOperatorPermissionCache(null, operator.getOperatorAccount());
						}
					}
				}
			}.start();

			return generateResult(true, "禁用/启用角色成功!");
		} else {
			return generateResult(false, "禁用/启用角色失败，数据库操作失败!");
		}
	}

	/**
	 * 保存角色权限
	 * 
	 * @param roleId
	 * @param merchantId
	 * @param menuIds
	 * @return
	 * @throws Exception
	 */
	@Transactional(rollbackFor = Exception.class)
	public BaseResult saveRolePermission(final Integer roleId, String menuIds) {
		try {
			// 1、删除该角色的所有权限
			roleProxy.delRolePermission(roleId);

			// 2、保存新的权限
			if (StringUtils.isNotEmpty(menuIds)) {
				String[] menus = menuIds.split(",");

				for (String menuId : menus) {
					if (roleProxy.saveRolePermission(roleId, menuId) != 1) {
						throw new Exception("保存角色权限失败!");
					}
				}
			}

			// step3:刷新使用该角色的操作员权限
			new Thread() {
				public void run() {
					List<SystemOperatorEntity> list = operatorProxy.getOperatorByRoleId(roleId);
					if (null != list && list.size() > 0) {
						for (SystemOperatorEntity operator : list) {
							ShiroUtil.refreshOperatorPermissionCache(null, operator.getOperatorAccount());
						}
					}
				}
			}.start();
			return generateResult(true, "保存角色权限成功!");
		} catch (Exception e) {
			e.printStackTrace();
			return generateResult(false, "保存角色权限失败，请稍候再试!");
		}
	}
}
