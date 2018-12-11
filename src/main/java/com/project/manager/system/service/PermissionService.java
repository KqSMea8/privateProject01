package com.project.manager.system.service;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.common.base.BaseService;
import com.project.common.constants.GlobalConstant;
import com.project.manager.system.entity.SystemMenuEntity;
import com.project.manager.system.proxy.PermissionProxy;

/**
 * 权限服务类
 */
@Service("permissionService")
public class PermissionService extends BaseService {
	@Autowired
	private PermissionProxy permissionProxy;

	/**
	 * 获取系统中所有需要验证的权限
	 * 
	 * @param map
	 */
	public void getAllPermission(Map<String, String> map) {
		// 得到所有需要验证的权限（包括状态为0的）
		List<SystemMenuEntity> list = permissionProxy.getAllPermission();

		for (SystemMenuEntity m : list) {
			map.put(m.getMenuUrl().replace("/", ":"), m.getMenuUrl());
		}
	}

	/**
	 * 获取指定用户的所有权限
	 * 
	 * @param userNo
	 * @param roleMap
	 * @param permissionMap
	 * @param userAuthInfoMap
	 * @return
	 */
	public boolean getAllRolePermissionByUser(String accountNo, Map<String, Set<String>> roleMap, Map<String, Set<String>> permissionMap,
			Map<String, SimpleAuthorizationInfo> userAuthInfoMap) {
		try {
			List<SystemMenuEntity> list = permissionProxy.getOperatorPermission(accountNo);

			if (roleMap.containsKey(accountNo)) {
				roleMap.remove(accountNo);
			}

			if (permissionMap.containsKey(accountNo)) {
				permissionMap.remove(accountNo);
			}

			if (userAuthInfoMap.containsKey(accountNo)) {
				userAuthInfoMap.remove(accountNo);
			}

			Set<String> permission = new HashSet<String>();
			Set<String> role = new HashSet<String>();
			for (SystemMenuEntity m : list) {
				String tmp[] = m.getMenuUrl().split("/");
				if (tmp.length > 1) {
					role.add(tmp[0]);
					permission.add(m.getMenuUrl().replaceAll("/", ":"));
				}
			}

			roleMap.put(accountNo, role);
			permissionMap.put(accountNo, permission);

			// shiro权限
			SimpleAuthorizationInfo info = new SimpleAuthorizationInfo(role);
			info.setStringPermissions(permission);

			userAuthInfoMap.put(accountNo, info);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean getSysAdminPermission(Map<String, Set<String>> roleMap, Map<String, Set<String>> permissionMap,
			Map<String, SimpleAuthorizationInfo> userAuthInfoMap) {
		try {
			List<SystemMenuEntity> list = permissionProxy.getSysAdminPermission();

			String accountNo = GlobalConstant.systemDefaultOperator.getOperatorAccount();

			if (roleMap.containsKey(accountNo)) {
				roleMap.remove(accountNo);
			}

			if (permissionMap.containsKey(accountNo)) {
				permissionMap.remove(accountNo);
			}

			if (userAuthInfoMap.containsKey(accountNo)) {
				userAuthInfoMap.remove(accountNo);
			}

			Set<String> permission = new HashSet<String>();
			Set<String> role = new HashSet<String>();
			for (SystemMenuEntity m : list) {
				String tmp[] = m.getMenuUrl().split("/");
				if (tmp.length > 1) {
					role.add(tmp[0]);
					permission.add(m.getMenuUrl().replaceAll("/", ":"));
				}
			}

			roleMap.put(accountNo, role);
			permissionMap.put(accountNo, permission);

			// shiro权限
			SimpleAuthorizationInfo info = new SimpleAuthorizationInfo(role);
			info.setStringPermissions(permission);

			userAuthInfoMap.put(accountNo, info);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
}
