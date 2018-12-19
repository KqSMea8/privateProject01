package com.project.service.entity;

public class User {

	private String openid;
	private Integer userId;
	private String name;
	private String nickname;
	private Byte sex;
	private String birthday;
	private String qq;
	private String idCardNumber;
	private Integer score;
	private Integer diamonds;
	private Integer loginTotal;
	private String num;
	private String weight;
	private String height;
	private String position;
	private String mobile;
	private Integer image;
	private Integer red;
	private Integer yellow;
	private Integer goal;
	private Integer type;
	private Byte isSafe;
	private Byte job;
	private Integer times;
	private Byte isGp;
	private String intr;
	private Byte status;

	public String getOpenId() {
		return openid;
	}

	public void setOpenId(String openid) {
		this.openid = openid;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name == null ? null : name.trim();
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname == null ? null : nickname.trim();
	}

	public Byte getSex() {
		return sex;
	}

	public void setSex(Byte sex) {
		this.sex = sex;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday == null ? null : birthday.trim();
	}

	public String getQq() {
		return qq;
	}

	public void setQq(String qq) {
		this.qq = qq == null ? null : qq.trim();
	}

	public String getIdCardNumber() {
		return idCardNumber;
	}

	public void setIdCardNumber(String idCardNumber) {
		this.idCardNumber = idCardNumber == null ? null : idCardNumber.trim();
	}

	public Integer getScore() {
		return score;
	}

	public void setScore(Integer score) {
		this.score = score;
	}

	public Integer getDiamonds() {
		return diamonds;
	}

	public void setDiamonds(Integer diamonds) {
		this.diamonds = diamonds;
	}

	public Integer getLoginTotal() {
		return loginTotal;
	}

	public void setLoginTotal(Integer loginTotal) {
		this.loginTotal = loginTotal;
	}

	public String getNum() {
		return num;
	}

	public void setNum(String num) {
		this.num = num == null ? null : num.trim();
	}

	public String getWeight() {
		return weight;
	}

	public void setWeight(String weight) {
		this.weight = weight == null ? null : weight.trim();
	}

	public String getHeight() {
		return height;
	}

	public void setHeight(String height) {
		this.height = height == null ? null : height.trim();
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position == null ? null : position.trim();
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile == null ? null : mobile.trim();
	}

	public Integer getImage() {
		return image;
	}

	public void setImage(Integer image) {
		this.image = image;
	}

	public Integer getRed() {
		return red;
	}

	public void setRed(Integer red) {
		this.red = red;
	}

	public Integer getYellow() {
		return yellow;
	}

	public void setYellow(Integer yellow) {
		this.yellow = yellow;
	}

	public Integer getGoal() {
		return goal;
	}

	public void setGoal(Integer goal) {
		this.goal = goal;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Byte getIsSafe() {
		return isSafe;
	}

	public void setIsSafe(Byte isSafe) {
		this.isSafe = isSafe;
	}

	public Byte getJob() {
		return job;
	}

	public void setJob(Byte job) {
		this.job = job;
	}

	public Integer getTimes() {
		return times;
	}

	public void setTimes(Integer times) {
		this.times = times;
	}

	public Byte getIsGp() {
		return isGp;
	}

	public void setIsGp(Byte isGp) {
		this.isGp = isGp;
	}

	public String getIntr() {
		return intr;
	}

	public void setIntr(String intr) {
		this.intr = intr == null ? null : intr.trim();
	}

	public Byte getStatus() {
		return status;
	}

	public void setStatus(Byte status) {
		this.status = status;
	}
}