package com.project.service.proxy;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.project.common.page.PageForApp;
import com.project.service.entity.User;

@Repository("commodityProxy")
public interface CommodityProxy {

	public List<Map<String, Object>> typeList();

	public List<Map<String, Object>> listOfType(@Param("page") PageForApp page, @Param("typeId") Integer typeId, @Param("searchKey") String searchKey);

	public Map<String, Object> info(@Param("commodityId") Integer commodityId);

	public Integer buy(@Param("commodityId") Integer commodityId, @Param("receiveId") Integer receiveId, @Param("total") Integer total, @Param("payType") Integer payType, @Param("user") User user);

	public Integer update(@Param("orderId") Integer orderId, @Param("status") Integer status);

	// =============================================钻石相关=============================================================

	public Integer diamondsRecharge(@Param("rechargeTotal") BigDecimal rechargeTotal, @Param("userId") Integer userId);

	public BigDecimal userRecharge(@Param("rechargeTotal") BigDecimal rechargeTotal, @Param("cashTotal") BigDecimal cashTotal, @Param("userId") Integer userId);

	public Map<String, Object> getDiamonds(@Param("userId") Integer userId);

	public List<Map<String, Object>> diamondsCommodityList(@Param("page") PageForApp page, @Param("searchKey") String searchKey);

	public Map<String, Object> diamondsCommodityInfo(@Param("commodityId") Integer commodityId);

	public List<Map<String, Object>> diamondsCommodityList(@Param("page") PageForApp page, @Param("userId") Integer userId);

	public List<Map<String, Object>> diamondsRechargelog(@Param("page") PageForApp page, @Param("userId") Integer userId);

}
