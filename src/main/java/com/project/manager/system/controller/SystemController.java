package com.project.manager.system.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.project.common.base.BaseController;
import com.project.common.base.BaseResult;
import com.project.common.jqgrid.JQGridPageParams;
import com.project.common.jqgrid.JQGridResultEntity;
import com.project.common.jqgrid.TreeNodeEntity;
import com.project.manager.system.entity.SystemOperatorEntity;
import com.project.manager.system.entity.SystemRoleEntity;
import com.project.manager.system.service.OperatorService;
import com.project.manager.system.service.RoleService;
import com.project.manager.system.service.SystemService;

@RequestMapping("/system")
@Controller
public class SystemController extends BaseController {
	@Resource(name = "systemService")
	private SystemService systemService;

	@Resource(name = "operatorService")
	private OperatorService operatorService;

	@Resource(name = "roleService")
	private RoleService roleService;

	/*
	 * 修改个人密码
	 */
	@RequestMapping("updatepwdofmy")
	@ResponseBody
	public BaseResult updatePwdOfMy(String oldPwd, String newPwd) {
		return systemService.updatePwdOfMy(oldPwd, newPwd);
	}
	
	/*
	 * 数据统计页面
	 */
	@RequestMapping("report")
	@ResponseBody
	public ModelAndView getReport(ModelAndView mv) {
		return systemService.toReport();
	}
	// =================================================================角色相关 start ===============================================================
	/*
	 * 跳转到角色列表页面
	 */
	@RequestMapping("rolelist")
	@ResponseBody
	public ModelAndView roleList(ModelAndView mv) {
		mv.setViewName("system/role/rolelist");
		return mv;
	}

	/*
	 * 获取角色列表
	 * 
	 * @param pageParams
	 * 
	 * @author chentianjin
	 */
	@RequestMapping("getrolegriddata")
	@ResponseBody
	public JQGridResultEntity<SystemRoleEntity> getDataGrid(JQGridPageParams pageParams, Integer status) {
		return roleService.getGridData(pageParams, status);
	}

	/*
	 * 创建角色
	 * 
	 * @param role
	 * 
	 * @author chentianjin
	 */
	@RequestMapping("createrole")
	@ResponseBody
	public BaseResult createRole(SystemRoleEntity role) {
		return roleService.create(role);
	}

	/*
	 * 修改角色
	 */
	@RequestMapping("updaterole")
	@ResponseBody
	public BaseResult updateRole(String roleName, Integer roleId) {
		return roleService.update(roleName, roleId);
	}

	/*
	 * 启用/禁用角色
	 */
	@RequestMapping("lockrole")
	@ResponseBody
	public BaseResult lock(Integer roleId) {
		return roleService.lock(roleId);
	}

	/*
	 * 获得角色列表页面的权限树数据
	 */
	@RequestMapping("getrolemenutree")
	@ResponseBody
	public List<TreeNodeEntity> getRoleMenuTree(String parentId) {
		// 初使化一个根节点
		TreeNodeEntity rootNode = new TreeNodeEntity();
		rootNode.setChecked(false);
		rootNode.setId("0");
		rootNode.setText("所有权限");
		rootNode.setState("open");
		rootNode.setMenuId("0");
		rootNode.setParentId("top");
		TreeNodeEntity nodeEntity = roleService.getMenuTree(rootNode, parentId);

		List<TreeNodeEntity> treeNode = new ArrayList<TreeNodeEntity>();
		treeNode.add(nodeEntity);

		return treeNode;
	}

	/*
	 * 获得角色权限
	 */
	@RequestMapping("getrolepermission")
	@ResponseBody
	public List<TreeNodeEntity> getRolePermission(Integer roleId) {
		return roleService.getRolePermission(roleId);
	}

	/*
	 * 保存角色权限
	 */
	@RequestMapping("saverolepermission")
	@ResponseBody
	public BaseResult saveRolePermission(Integer roleId, String rolePermissions) {
		return roleService.saveRolePermission(roleId, rolePermissions);
	}

	// =================================================================角色相关 end ===============================================================
	// =================================================================用户相关 end ===============================================================
	/*
	 * 跳转到球会列表页面
	 */
	@RequestMapping("operatorlist")
	@ResponseBody
	public ModelAndView operatorList(ModelAndView mv) {
		mv.setViewName("system/operator/operatorlist");
		return mv;
	}

	/*
	 * 获得用户列表
	 */
	@RequestMapping("getoperatorgriddata")
	@ResponseBody
	public JQGridResultEntity<SystemOperatorEntity> getOperatorGridData(JQGridPageParams pageParams, String type, Integer parentId) {
		return operatorService.getGridData(pageParams, type, parentId);
	}

	/*
	 * 创建操作员页面
	 */
	@RequestMapping("addoperator")
	public ModelAndView addOperator(ModelAndView mv, Integer companyId) {
		mv.addObject("actionType", "add");
		mv.setViewName("system/operator/operatorinfo");
		return mv;
	}

	/*
	 * 创建操作员
	 */
	@RequestMapping("createoperator")
	@ResponseBody
	public BaseResult create(SystemOperatorEntity operator) {
		return operatorService.create(operator);
	}

	/*
	 * 修改操作员页面
	 */
	@RequestMapping("modifyoperator")
	public ModelAndView modify(ModelAndView mv, Integer operatorId) {
		SystemOperatorEntity operator = operatorService.getOperatorInfoById(operatorId);
		mv.addObject("actionType", "modify");
		mv.addObject("operatorInfo", operator);

		mv.setViewName("system/operator/operatorinfo");

		return mv;
	}

	/*
	 * 修改操作员
	 */
	@RequestMapping("updateoperator")
	@ResponseBody
	public BaseResult update(Integer operatorId, String operatorAccount, String operatorCode,
			String operatorName, String operatorTel) {
		return operatorService.update(operatorId, operatorAccount, operatorCode, operatorName, operatorTel);
	}

	/*
	 * 启用禁用操作员
	 */
	@RequestMapping("lockoperator")
	@ResponseBody
	public BaseResult lockOperator(Integer operatorId, String operatorAccount) {
		return operatorService.lock(operatorId, operatorAccount);
	}

	/*
	 * 保存操作角色关联关系
	 */
	@RequestMapping("saveoperatorrole")
	@ResponseBody
	public BaseResult saveOperatorRole(Integer operatorId, String operatorAccount, String roleIds) {
		return operatorService.saveOperatorPermission(operatorId, operatorAccount, roleIds);
	}

	/*
	 * 获取操作员绑定的角色
	 */
	@RequestMapping("getoperatorrole")
	@ResponseBody
	public BaseResult getOperatorRole(Integer operatorId) {
		return operatorService.getOperatorRole(operatorId);
	}
	
	/*
	 * 重置密码
	 */
	@RequestMapping("resetpwd")
	@ResponseBody
	public BaseResult resetPwd(Integer operatorId) {
		return operatorService.resetPwd(operatorId);
	}
	// =================================================================用户相关 end ===============================================================

	/*
	 * 设置系统参数
	 */
	@RequestMapping("setsystemparams")
	@ResponseBody
	public BaseResult setSystemParams(String category, String value) {
		return systemService.setSystemParams(category,value);
	}
}
