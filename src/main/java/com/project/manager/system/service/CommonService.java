package com.project.manager.system.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.manager.system.entity.CommonComboEntity;
import com.project.manager.system.enums.ConstantsEnums.*;
import com.project.manager.system.proxy.SystemProxy;

/**
 * 公共服务类
 */
@Service("commonService")
public class CommonService {

	@Autowired
	SystemProxy systemProxy;
	
	/**
	 * 获取禁用、启用状态下拉
	 */
	public List<CommonComboEntity> getStatusCombo() {
		List<CommonComboEntity> comboList = new ArrayList<CommonComboEntity>();
		COMMON_STATUS[] se = COMMON_STATUS.values();
		for (COMMON_STATUS staen : se) {
			CommonComboEntity combo = new CommonComboEntity();
			combo.setId(staen.getStatusEnumId().toString());
			combo.setValue(staen.getStatusEnumId().toString());
			combo.setText(staen.getStatusEnum().toString());
			comboList.add(combo);
		}
		return comboList;
	}

	public List<CommonComboEntity> getMemberUserStatusCombo() {

		MEMBER_USER_STATUS_ENUM pe[] = MEMBER_USER_STATUS_ENUM.values();
		List<CommonComboEntity> comboList = new ArrayList<CommonComboEntity>();
		for (MEMBER_USER_STATUS_ENUM e : pe) {
			CommonComboEntity combo = new CommonComboEntity();
			combo.setId(e.getCode());
			combo.setValue(e.getCode());
			combo.setText(e.getName());
			comboList.add(combo);
		}
		return comboList;
	}

	public List<CommonComboEntity> getauditstatusCombo() {
		AUDIT_STATUS_ENUM pe[] = AUDIT_STATUS_ENUM.values();
		List<CommonComboEntity> comboList = new ArrayList<CommonComboEntity>();
		for (AUDIT_STATUS_ENUM e : pe) {
			CommonComboEntity combo = new CommonComboEntity();
			combo.setId(e.getCode());
			combo.setValue(e.getCode());
			combo.setText(e.getName());
			comboList.add(combo);
		}
		return comboList;
	}

	public List<CommonComboEntity> getNoticeTypeCombo() {
		NOTICE_TYPE_ENUM pe[] = NOTICE_TYPE_ENUM.values();
		List<CommonComboEntity> comboList = new ArrayList<CommonComboEntity>();
		for (NOTICE_TYPE_ENUM e : pe) {
			CommonComboEntity combo = new CommonComboEntity();
			combo.setId(e.getCode());
			combo.setValue(e.getCode());
			combo.setText(e.getName());
			comboList.add(combo);
		}
		return comboList;
	}

	public List<CommonComboEntity> getCompetitionSubTypeCombo() {
		COMPETITION_SUB_TYPE_ENUM pe[] = COMPETITION_SUB_TYPE_ENUM.values();
		List<CommonComboEntity> comboList = new ArrayList<CommonComboEntity>();
		for (COMPETITION_SUB_TYPE_ENUM e : pe) {
			CommonComboEntity combo = new CommonComboEntity();
			combo.setId(e.getCode());
			combo.setValue(e.getCode());
			combo.setText(e.getName());
			comboList.add(combo);
		}
		return comboList;
	}

	public List<CommonComboEntity> getYesNoTypeCombo() {
		YSE_NO_ENUM pe[] = YSE_NO_ENUM.values();
		List<CommonComboEntity> comboList = new ArrayList<CommonComboEntity>();
		for (YSE_NO_ENUM e : pe) {
			CommonComboEntity combo = new CommonComboEntity();
			combo.setId(e.getCode());
			combo.setValue(e.getCode());
			combo.setText(e.getName());
			comboList.add(combo);
		}
		return comboList;
	}

	public List<CommonComboEntity> getMemberUserLevel() {
		return systemProxy.getMemberUserLevel();
	}
}
