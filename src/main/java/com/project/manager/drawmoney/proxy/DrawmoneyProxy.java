package com.project.manager.drawmoney.proxy;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.project.common.jqgrid.JQGridPageParams;

@Repository("drawmoneyProxy")
public interface DrawmoneyProxy {

	public List<Map<String, Object>> getDrawmoneyList(@Param("pageParams") JQGridPageParams pageParams);

	public long getDrawmoneyListCount();

	public Integer updateStatus(@Param("drawmoneyTypeId") String drawmoneyTypeId);

	public Integer delete(@Param("drawmoneyTypeId") String drawmoneyTypeId);

	public Integer addInfo(@Param("name") String name, @Param("name_th") String name_th, @Param("logourl") String logourl, @Param("converstion") String converstion);

	public Integer updateInfo(@Param("drawmoneyTypeId") Integer drawmoneyTypeId, @Param("name") String name, @Param("name_th") String name_th, @Param("logourl") String logourl, @Param("converstion") String converstion);

}
