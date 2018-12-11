package com.project.manager.system.proxy;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.project.common.jqgrid.JQGridPageParams;
import com.project.manager.system.entity.SystemOperatorEntity;

/**
 * 操作员数据访问接口代理类
 */
@Repository("operatorProxy")
public interface OperatorProxy {
	public List<SystemOperatorEntity> getGridData(@Param("pageParams") JQGridPageParams pageParams, @Param("type") String type,
			@Param("parentId") Integer parentId, @Param("operator") SystemOperatorEntity operator);

	public Long getGridDataCount(@Param("parentId") Integer parentId, @Param("type") String type, @Param("operator") SystemOperatorEntity operator);

	public SystemOperatorEntity getOperatorInfoByAccount(@Param("accountNo") String accountNo);

	public Integer isExistsByAccount(@Param("operatorAccount") String operatorAccount);

	public Integer insertOperator(SystemOperatorEntity operator);

	public Integer delOperatorPermission(@Param("operatorId") Integer operatorId);

	public Integer lock(@Param("operatorId") Integer operatorId);

	public Integer saveOperatorPermission(@Param("operatorId") Integer operatorId, @Param("roleId") Integer roleId);

	public Integer isExistsByAccountId(@Param("operatorId") Integer operatorId, @Param("operatorAccount") String operatorAccount);

	public Integer updateOperator(@Param("operatorId") Integer operatorId, @Param("operatorAccount") String operatorAccount,
			@Param("operatorCode") String operatorCode, @Param("operatorName") String operatorName, @Param("operatorTel") String operatorTel);

	public String getOperatorRole(@Param("operatorId") Integer operatorId);

	public List<SystemOperatorEntity> getOperatorByRoleId(@Param("roleId") Integer roleId);

	public Integer updatePwd(@Param("operator") SystemOperatorEntity operator);

	public SystemOperatorEntity getOperatorInfoById(@Param("operatorId") Integer operatorId);

	public Integer resetPwd(@Param("operatorId")Integer operatorId, @Param("newPwd")String newPwd);
}
