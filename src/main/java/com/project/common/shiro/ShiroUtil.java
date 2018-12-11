package com.project.common.shiro;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.subject.Subject;

import com.project.common.constants.GlobalConstant;
import com.project.common.redis.RedisUtil;
import com.project.common.util.SpringContextUtil;
import com.project.manager.system.entity.SystemOperatorEntity;
import com.project.manager.system.proxy.OperatorProxy;
import com.project.manager.system.service.PermissionService;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * Shiro快速工具类
 */
public class ShiroUtil {
	private static final String OPERATOR_INFO_CACHE = "OPERATOR_INFO_CACHE_";
	private static final String ALL_PERMISSSION_CACHE = "ALL_PERMISSSION_CACHE";
	private static final String OPERATOR_ROLE_MAP_CACHE = "OPERATOR_ROLE_MAP_CACHE_";
	private static final String OPERATOR_PERMISSION_MAP_CACHE = "OPERATOR_PERMISSION_MAP_CACHE_";
	private static final String OPERATOR_AUTH_INFO_MAP_CACHE = "OPERATOR_AUTH_INFO_MAP_CACHE_";

	/**
	 * 缓存用户登录信息
	 * 
	 * @param accountNo
	 * @param userEntity
	 */
	public static void cacheUserInfo(SystemOperatorEntity operator) {
		cacheUserInfo(currentAccount(), operator);
	}

	/**
	 * 缓存用户信息
	 * 
	 * @param currentAccount
	 * @param operator
	 */
	public static void cacheUserInfo(String currentAccount, SystemOperatorEntity operator) {
		try {
			RedisUtil redis = SpringContextUtil.getBean(RedisUtil.class);
			Gson gson = new Gson();

			SystemOperatorEntity opt = new SystemOperatorEntity();
			BeanUtils.copyProperties(opt, operator);

			// 缓存操作员信息
			redis.set(OPERATOR_INFO_CACHE + currentAccount, gson.toJson(opt));

			// 缓存权限信息
			refreshOperatorPermissionCache(redis, currentAccount);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获取操作员信息
	 * 
	 * @return
	 */
	public static SystemOperatorEntity getOperatorInfo() {
		try {
			RedisUtil redis = SpringContextUtil.getBean(RedisUtil.class);
			String operatorInfoJson = redis.get(OPERATOR_INFO_CACHE + currentAccount());
			if (StringUtils.isEmpty(operatorInfoJson)) {
				throw new Exception("从缓存中获取操作员信息失败，改从数据库中获取");
			}
			Gson gson = new Gson();
			return gson.fromJson(operatorInfoJson, SystemOperatorEntity.class);
		} catch (Exception e) {
			e.printStackTrace();
			return SpringContextUtil.getBean(OperatorProxy.class).getOperatorInfoByAccount(currentAccount());
		}
	}

	/**
	 * 是否内置账户
	 * 
	 * @return
	 */
	public static boolean isSysAdmin() {
		return getOperatorInfo().getOperatorId().equals(GlobalConstant.systemDefaultOperator.getOperatorId());
	}

	/**
	 * 获取当前登录帐号
	 * 
	 * @return
	 */
	public static String currentAccount() {
		Subject currentUser = SecurityUtils.getSubject();
		if (currentUser != null)
			return currentUser.getPrincipal() == null ? null : currentUser.getPrincipal().toString();
		else
			return null;
	}

	/**
	 * 验证用户是否认证通过
	 * 
	 * @return
	 */
	public static boolean isAuthenticated() {
		Subject currentUser = SecurityUtils.getSubject();
		return currentUser.isAuthenticated();
	}

	/**
	 * 判断该请求是否是系统要求验证的请求
	 * 
	 * @param p
	 * @return
	 */
	public static boolean isSystemCheck(String p) {
		try {
			RedisUtil redis = SpringContextUtil.getBean(RedisUtil.class);
			String allPersmisson = redis.get(ALL_PERMISSSION_CACHE);
			if (StringUtils.isEmpty(allPersmisson)) {
				throw new Exception("从缓存中获取系统权限信息失败，改从数据库中获取");
			}

			Gson gson = new Gson();
			Map<String, String> allPermissionMap = gson.fromJson(allPersmisson, new TypeToken<HashMap<String, String>>() {
			}.getType());

			return allPermissionMap.containsKey(p);
		} catch (Exception e) {
			e.printStackTrace();

			Map<String, String> allPermissionMap = new HashMap<String, String>();
			PermissionService permissionService = SpringContextUtil.getBean(PermissionService.class);
			permissionService.getAllPermission(allPermissionMap);

			return allPermissionMap.containsKey(p);
		}
	}

	/**
	 * 刷新单个用户的角色和权限
	 * 
	 * @param userId
	 * @return
	 */
	private static boolean refreshOperatorPermission(String operatorAccount, Map<String, Set<String>> userRoleMap,
			Map<String, Set<String>> userPermissionMap, Map<String, SimpleAuthorizationInfo> userAuthInfoMap) {
		PermissionService permissionService = SpringContextUtil.getBean(PermissionService.class);
		
		if (operatorAccount.equals(GlobalConstant.systemDefaultOperator.getOperatorAccount())) {
			return permissionService.getSysAdminPermission(userRoleMap, userPermissionMap, userAuthInfoMap);
		} else {
			return permissionService.getAllRolePermissionByUser(operatorAccount, userRoleMap, userPermissionMap, userAuthInfoMap);
		}
	}

	/**
	 * 获取操作shiro验证信息
	 * 
	 * @param userNo
	 * @return
	 */
	public static SimpleAuthorizationInfo getAuthInfo(String operatorAccount) {
		try {
			RedisUtil redis = SpringContextUtil.getBean(RedisUtil.class);
			String userAuthInfoMapJson = redis.get(OPERATOR_AUTH_INFO_MAP_CACHE + operatorAccount);
			if (StringUtils.isEmpty(userAuthInfoMapJson)) {
				throw new Exception();
			}

			Gson gson = new Gson();
			Map<String, SimpleAuthorizationInfo> userAuthInfoMap = gson.fromJson(userAuthInfoMapJson,
					new TypeToken<Map<String, SimpleAuthorizationInfo>>() {
					}.getType());
			return userAuthInfoMap.get(operatorAccount);
		} catch (Exception e) {
			e.printStackTrace();

			Map<String, Set<String>> userRoleMap = new HashMap<String, Set<String>>();
			Map<String, Set<String>> userPermissionMap = new HashMap<String, Set<String>>();
			Map<String, SimpleAuthorizationInfo> userAuthInfoMap = new HashMap<String, SimpleAuthorizationInfo>();

			refreshOperatorPermission(operatorAccount, userRoleMap, userPermissionMap, userAuthInfoMap);

			return userAuthInfoMap.get(operatorAccount);
		}
	}

	/**
	 * 刷新用户权限
	 * 
	 * @param redis
	 * @param operatorAccount
	 */
	public static void refreshOperatorPermissionCache(RedisUtil redis, String operatorAccount) {
		if (null == redis) {
			redis = SpringContextUtil.getBean(RedisUtil.class);
		}

		Map<String, Set<String>> userRoleMap = new HashMap<String, Set<String>>();
		Map<String, Set<String>> userPermissionMap = new HashMap<String, Set<String>>();
		Map<String, SimpleAuthorizationInfo> userAuthInfoMap = new HashMap<String, SimpleAuthorizationInfo>();

		refreshOperatorPermission(operatorAccount, userRoleMap, userPermissionMap, userAuthInfoMap);

		Gson gson = new Gson();

		redis.set(OPERATOR_ROLE_MAP_CACHE + operatorAccount, gson.toJson(userRoleMap));
		redis.set(OPERATOR_PERMISSION_MAP_CACHE + operatorAccount, gson.toJson(userPermissionMap));
		redis.set(OPERATOR_AUTH_INFO_MAP_CACHE + operatorAccount, gson.toJson(userAuthInfoMap));
	}

	/**
	 * 加载系统权限
	 */
	public static void loadSystemAllPermission() {
		RedisUtil redis = SpringContextUtil.getBean(RedisUtil.class);

		Map<String, String> allPermissionMap = new HashMap<String, String>();
		PermissionService permissionService = SpringContextUtil.getBean(PermissionService.class);
		permissionService.getAllPermission(allPermissionMap);

		redis.set(ALL_PERMISSSION_CACHE, new Gson().toJson(allPermissionMap));
	}
}
