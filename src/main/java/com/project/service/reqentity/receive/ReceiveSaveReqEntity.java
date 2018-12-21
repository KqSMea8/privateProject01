package com.project.service.reqentity.receive;

import com.project.common.annotation.aops.ValidateParam;
import com.project.common.util.validate.AbstractParameter;

public class ReceiveSaveReqEntity extends AbstractParameter {

	private static final long serialVersionUID = 2834666349462528914L;

	@ValidateParam(allowEmpty = true)
	Integer receiveId;

	@ValidateParam(tip = "省份ID不得为空")
	Integer provinceId;

	@ValidateParam(tip = "城市ID不得为空")
	Integer cityId;

	@ValidateParam(tip = "区域ID不得为空")
	Integer districtId;

	@ValidateParam(tip = "是否默认状态不得为空")
	Integer isDeff;

	@ValidateParam(tip = "接收人姓名不得为空")
	String receiveName;

	@ValidateParam(tip = "接收人电话不得为空")
	String receiveMobile;

	@ValidateParam(tip = "接收地址不得为空")
	String address;

	public Integer getReceiveId() {
		return receiveId;
	}

	public void setReceiveId(Integer receiveId) {
		this.receiveId = receiveId;
	}

	public Integer getProvinceId() {
		return provinceId;
	}

	public void setProvinceId(Integer provinceId) {
		this.provinceId = provinceId;
	}

	public Integer getCityId() {
		return cityId;
	}

	public void setCityId(Integer cityId) {
		this.cityId = cityId;
	}

	public Integer getDistrictId() {
		return districtId;
	}

	public void setDistrictId(Integer districtId) {
		this.districtId = districtId;
	}

	public Integer getIsDeff() {
		return isDeff;
	}

	public void setIsDeff(Integer isDeff) {
		this.isDeff = isDeff;
	}

	public String getReceiveName() {
		return receiveName;
	}

	public void setReceiveName(String receiveName) {
		this.receiveName = receiveName;
	}

	public String getReceiveMobile() {
		return receiveMobile;
	}

	public void setReceiveMobile(String receiveMobile) {
		this.receiveMobile = receiveMobile;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
}
