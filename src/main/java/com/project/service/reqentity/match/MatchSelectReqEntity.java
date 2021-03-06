package com.project.service.reqentity.match;

import com.project.common.annotation.aops.ValidateParam;
import com.project.common.util.validate.AbstractParameter;
import com.project.common.util.validate.RegexConstant;

/**
 * 约战列表请求参数实体
 */
public class MatchSelectReqEntity extends AbstractParameter {
	private static final long serialVersionUID = 2775924572169274595L;

	@ValidateParam(allowEmpty = true)
	Integer cityId;// 第一层筛选条件

	@ValidateParam(allowEmpty = true)
	Integer districtId;// 第一层筛选条件

	@ValidateParam(allowEmpty = true)
	Integer marchType;//

	@ValidateParam(allowEmpty = true)
	Integer matchStatus;

	String searchKey;

	@ValidateParam(minValue = 1)
	Integer pageIndex;

	@ValidateParam(minValue = 1)
	Integer pageRows;

	@ValidateParam(allowEmpty = true, tip = "日期格不对或日期不合法", regex = RegexConstant.REGEX_DATE_YYYY_MM_DD)
	String startDate;

	@ValidateParam(allowEmpty = true, tip = "日期格不对或日期不合法", regex = RegexConstant.REGEX_DATE_YYYY_MM_DD)
	String endDate;

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

	public Integer getMarchType() {
		return marchType;
	}

	public void setMarchType(Integer marchType) {
		this.marchType = marchType;
	}

	public Integer getMatchStatus() {
		return matchStatus;
	}

	public void setMatchStatus(Integer matchStatus) {
		this.matchStatus = matchStatus;
	}

	public String getSearchKey() {
		return searchKey;
	}

	public void setSearchKey(String searchKey) {
		this.searchKey = searchKey;
	}

	public Integer getPageIndex() {
		return pageIndex;
	}

	public void setPageIndex(Integer pageIndex) {
		this.pageIndex = pageIndex;
	}

	public Integer getPageRows() {
		return pageRows;
	}

	public void setPageRows(Integer pageRows) {
		this.pageRows = pageRows;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

}
