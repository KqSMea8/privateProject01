package com.project.service.reqentity.user;

import com.project.common.annotation.aops.ValidateParam;
import com.project.common.util.validate.AbstractParameter;

public class UserSaveReqEntity extends AbstractParameter {
	private static final long serialVersionUID = -1536532472489323356L;
	
	Integer userId;
	@ValidateParam(allowEmpty = true)
	String userName;
	@ValidateParam(allowEmpty = true)
	String height;
	@ValidateParam(allowEmpty = true)
	String weight;
	@ValidateParam(allowEmpty = true)
	Integer sex;
	@ValidateParam(allowEmpty = true)
	String birthday;
	@ValidateParam(allowEmpty = true)
	String position;
	@ValidateParam(allowEmpty = true)
	String occupation;
	@ValidateParam(allowEmpty = true)
	String trueName;
	@ValidateParam(allowEmpty = true)
	String idCode;
	@ValidateParam(allowEmpty = true)
	Integer isGP;
	@ValidateParam(allowEmpty = true)
	Integer intr;

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getHeight() {
		return height;
	}

	public void setHeight(String height) {
		this.height = height;
	}

	public String getWeight() {
		return weight;
	}

	public void setWeight(String weight) {
		this.weight = weight;
	}

	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getOccupation() {
		return occupation;
	}

	public void setOccupation(String occupation) {
		this.occupation = occupation;
	}

	public String getTrueName() {
		return trueName;
	}

	public void setTrueName(String trueName) {
		this.trueName = trueName;
	}

	public String getIdCode() {
		return idCode;
	}

	public void setIdCode(String idCode) {
		this.idCode = idCode;
	}

	public Integer getIsGP() {
		return isGP;
	}

	public void setIsGP(Integer isGP) {
		this.isGP = isGP;
	}

	public Integer getIntr() {
		return intr;
	}

	public void setIntr(Integer intr) {
		this.intr = intr;
	}

}
