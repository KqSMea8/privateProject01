package com.project.service.proxy;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.project.common.page.PageForApp;
import com.project.service.entity.User;
import com.project.service.reqentity.pk.PlayerKillingApplyReqEntity;
import com.project.service.reqentity.pk.PlayerKillingSaveReqEntity;
import com.project.service.reqentity.pk.PlayerKillingSelectReqEntity;

@Repository("playerKillingProxy")
public interface PlayerKillingProxy {

	public List<Map<String, Object>> list(@Param("page") PageForApp page, @Param("paramEntity") PlayerKillingSelectReqEntity paramEntity);

	public Map<String, Object> info(@Param("pkInfoId") Integer pkInfoId);

	public List<Map<String, Object>> getApplyTeams(@Param("pkInfoId") Integer pkInfoId);

	public Integer create(@Param("paramEntity") PlayerKillingSaveReqEntity paramEntity, @Param("user") User user);

	public Integer edit(@Param("paramEntity") PlayerKillingSaveReqEntity paramEntity, @Param("user") User user);

	public Integer delete(@Param("pkInfoId") Integer pkInfoId, @Param("userId") Integer userId);

	public Integer confirm(@Param("pkInfoId") Integer pkInfoId, @Param("applyTeamId") Integer applyTeamId, @Param("userId") Integer userId);

	public Integer apply(@Param("paramEntity") PlayerKillingApplyReqEntity paramEntity, @Param("user") User user);

}
