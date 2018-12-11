package com.project.manager.vipparam.proxy;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.project.common.jqgrid.JQGridPageParams;

@Repository("vipParamProxy")
public interface VipParamProxy {

	public List<Map<String, Object>> getVipParamList(@Param("pageParams") JQGridPageParams pageParams);

	public long getVipParamListCount();

	public Integer delete(@Param("vipParamId") String vipParamId);

	public Integer addInfo(@Param("name") String name, @Param("level") Integer level, @Param("recommendTotal") Integer recommendTotal, @Param("teamuserTotal") Integer teamuserTotal,
			@Param("interestRate") BigDecimal interestRate, @Param("remark") String remark, @Param("needLevel") Integer needLevel, @Param("needLevelTotal") Integer needLevelTotal);

	public Integer updateInfo(@Param("vipParamId") Integer vipParamId, @Param("name") String name, @Param("level") Integer level, @Param("recommendTotal") Integer recommendTotal,
			@Param("teamuserTotal") Integer teamuserTotal, @Param("interestRate") BigDecimal interestRate, @Param("remark") String remark, @Param("needLevel") Integer needLevel,
			@Param("needLevelTotal") Integer needLevelTotal);

	public Integer getMaxLevelHad();

}
