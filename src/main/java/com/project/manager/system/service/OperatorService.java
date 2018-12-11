package com.project.manager.system.service;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.common.base.BaseResult;
import com.project.common.base.BaseService;
import com.project.common.constants.Constants;
import com.project.common.constants.GlobalConstant;
import com.project.common.jqgrid.JQGridPageParams;
import com.project.common.jqgrid.JQGridResultEntity;
import com.project.common.shiro.ShiroUtil;
import com.project.common.util.Md5Util;
import com.project.manager.system.entity.SystemOperatorEntity;
import com.project.manager.system.proxy.OperatorProxy;

/**
 * 操作员业务类
 */
@Service("operatorService")
public class OperatorService extends BaseService {
	@Autowired
	private OperatorProxy operatorProxy;

	/**
	 * 获取人员列表
	 * 
	 * @param pageParams
	 * @param parentId
	 * @return
	 */
	public JQGridResultEntity<SystemOperatorEntity> getGridData(JQGridPageParams pageParams, String type, Integer parentId) {
		JQGridResultEntity<SystemOperatorEntity> result = new JQGridResultEntity<SystemOperatorEntity>();

		SystemOperatorEntity operator = ShiroUtil.getOperatorInfo();

		// 分页列表
		List<SystemOperatorEntity> list = operatorProxy.getGridData(pageParams, type, parentId, operator);

		// 总行数
		long totalRecords = operatorProxy.getGridDataCount(parentId, type, operator);

		return fillJQGrid(result, list, totalRecords, pageParams);
	}

	/**
	 * 获取用户信息
	 * 
	 * @param accountNo
	 * @return
	 */
	public SystemOperatorEntity getOperatorInfoByAccount(String accountNo) {
		return operatorProxy.getOperatorInfoByAccount(accountNo);
	}

	/**
	 * 创建用户
	 * 
	 * @param operator
	 * @return
	 * @throws Exception
	 */
	public BaseResult create(SystemOperatorEntity operator) {
		try {
			// 判断是不是同系统内置账户是一致
			if (operator.getOperatorAccount().equals(GlobalConstant.systemDefaultOperator.getOperatorAccount())) {
				return generateResult(false, "创建用户失败，用户账号【" + operator.getOperatorAccount() + "】已被占用!");
			}

			// 名称排重
			int ret = operatorProxy.isExistsByAccount(operator.getOperatorAccount());
			if (ret >= 1) {
				return generateResult(false, "创建用户失败，用户账号【" + operator.getOperatorAccount() + "】已被占用!");
			}

			operator.setOperatorPwd(Md5Util.getMD5(operator.getOperatorPwd()));

			ret = operatorProxy.insertOperator(operator);
			if (ret == 1) {
				return generateResult(true, "创建用户成功!");
			} else {
				return generateResult(false, "创建用户失败，数据库操作失败!");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return generateResult(false, "创建用户失败，请稍候再试!");
		}
	}

	/**
	 * 修改操作员
	 * 
	 * @param operator
	 * @return
	 * @throws Exception
	 */
	public BaseResult update(Integer operatorId, String operatorAccount, String operatorCode,
			String operatorName, String operatorTel) {
		try {
			// 判断是不是同系统内置账户是一致
			if (operatorAccount.equals(GlobalConstant.systemDefaultOperator.getOperatorAccount())) {
				return generateResult(false, "修改用户失败，用户账号【" + operatorAccount + "】已被占用!");
			}

			// 操作员登录账号排重
			int ret = operatorProxy.isExistsByAccountId(operatorId, operatorAccount);
			if (ret >= 1) {
				return generateResult(false, "修改用户失败，用户账号【" + operatorAccount + "】已被占用!");
			}

			ret = operatorProxy.updateOperator(operatorId, operatorAccount, operatorCode, operatorName, operatorTel);
			if (ret == 1) {
				return generateResult(true, "修改用户成功!");
			} else {
				return generateResult(false, "修改用户失败，数据库操作失败!");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return generateResult(false, "创建用户失败，请稍候再试!!");
		}
	}

	/**
	 * 获取用户信息（用户ID）
	 * 
	 * @param operatorId
	 * @return
	 */
	public SystemOperatorEntity getOperatorInfoById(Integer operatorId) {
		return operatorProxy.getOperatorInfoById(operatorId);
	}

	/**
	 * 禁用/启用操作员
	 * 
	 * @param operatorId
	 * @param operatorAccount
	 * @return
	 * @throws RuiYunException
	 */
	public BaseResult lock(Integer operatorId, String operatorAccount) {
		try {
			int ret = operatorProxy.lock(operatorId);
			if (ret == 1) {
				// 刷新操作员权限
				ShiroUtil.refreshOperatorPermissionCache(null, operatorAccount);
				return generateResult(true, "禁用/启用用户成功!");
			} else {
				return generateResult(true, "禁用/启用用户失败!");
			}

		} catch (Exception e) {
			e.printStackTrace();
			return generateResult(false, "创建用户失败，请稍候再试!!");
		}
	}

	/**
	 * 用户关联角色
	 * 
	 * @param operatorId
	 * @param roleIds
	 * @return
	 * @throws Exception
	 */
	@Transactional(rollbackFor = Exception.class)
	public BaseResult saveOperatorPermission(Integer operatorId, final String operatorAccount, String roleIds) {
		try {
			// step1:删除该用户关联的所有角色
			operatorProxy.delOperatorPermission(operatorId);

			// step2:保存新关联关系
			if (StringUtils.isNotEmpty(roleIds)) {
				String[] roleIdArray = roleIds.split(",");

				for (String roleId : roleIdArray) {
					if (operatorProxy.saveOperatorPermission(operatorId, new Integer(roleId)) != 1) {
						throw new Exception("用户关联角色失败!");
					}
				}
			}

			// step3:刷新该操作员权限
			ShiroUtil.refreshOperatorPermissionCache(null, operatorAccount);

			return generateResult(true, "用户关联角色成功!");
		} catch (Exception e) {
			e.printStackTrace();
			return generateResult(false, "用户关联角色失败，请稍候再试!");
		}
	}

	/**
	 * 获取操作员绑定的角色
	 * 
	 * @param operatorId
	 * @return
	 */
	public BaseResult getOperatorRole(Integer operatorId) {
		String result = operatorProxy.getOperatorRole(operatorId);
		return generateResult(true, "", result);
	}

	public BaseResult resetPwd(Integer operatorId) {
		try {
			// step1:删除该用户关联的所有角色
			operatorProxy.resetPwd(operatorId,Md5Util.getMD5(Constants.RESET_PWD));

			return generateResult(true, "重置密码色成功,新密码为("+Constants.RESET_PWD+")!");
		} catch (Exception e) {
			e.printStackTrace();
			return generateResult(false, "重置密码失败，请稍候再试!");
		}
	}
}
