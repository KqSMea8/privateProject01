package com.project.manager.receivablesbank.proxy;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.project.common.jqgrid.JQGridPageParams;

@Repository("receivablesBankProxy")
public interface ReceivablesBankProxy {
	public List<Map<String, Object>> getReceivablesTypeList(@Param("pageParams") JQGridPageParams pageParams);

	public long getReceivablesTypeListCount();

	public List<Map<String, Object>> getReceivablesBankList(@Param("pageParams") JQGridPageParams pageParams, @Param("typeId") Integer typeId);

	public long getReceivablesBankListCount(@Param("typeId") Integer typeId);

	public Integer setReceive(@Param("receivablesBankId") String receivablesBankId, @Param("typeId") Integer typeId);

	public Integer updateReceiveStatus(@Param("receivablesBankId") String receivablesBankId, @Param("typeId") Integer typeId);

	public Integer delete(@Param("receivablesBankId") String receivablesBankId);

	public Integer addInfo(@Param("name") String name, @Param("number") String number, @Param("customName") String customName, @Param("type") String type, @Param("typeId") Integer typeId,
			@Param("conversion") BigDecimal conversion);

	public Integer updateInfo(@Param("receivablesBankId") Integer receivablesBankId, @Param("name") String name, @Param("number") String number, @Param("customName") String customName,
			@Param("type") String type, @Param("conversion") BigDecimal conversion);

	public Integer updateStatus(@Param("receivablesTypeIds") String receivablesTypeIds);

	public Integer deleteType(@Param("receivablesTypeIds") String receivablesTypeIds);

	public Integer addType(@Param("name") String name, @Param("logoUrl") String logoUrl);

}
