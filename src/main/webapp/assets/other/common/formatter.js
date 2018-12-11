function statusFormatter(value) {
	switch (value) {
	case 0:
		return "<span style=\"color:red\">禁用</span>";
	case 1:
		return "<span style=\"color:blue\">启用</span>";
	default:
		return "未知状态";
	}
}

function statusForCompetitionFormatter(value) {
	switch (value) {
	case 0:
		return "<span style=\"color:red\">禁用</span>";
	case 1:
		return "<span style=\"color:blue\">启用</span>";
	case 2:
		return "<span style=\"color:red\">赛事已取消</span>";
	default:
		return "未知状态";
	}
}

/*
 * 会员状态转换
 */
function memberUserFormatter(value) {
	switch (value) {
	case 0:
		return "<span style=\"color:blue\">未激活用户</span>";
	case 1:
		return "<span style=\"color:green\">激活用户</span>";
	case 2:
		return "<span style=\"color:red\">已冻结用户</span>";
	case 3:
		return "<span style=\"color:orange\">僵尸用户</span>";
	default:
		return "<font style=\"color:#6b8e23\">未知状态</font>";
	}
}

/*
 * 审核状态转换
 */
function audtiStatusFormatter(value) {
	switch (value) {
	case 0:
		return "<span style=\"color:blue\">待处理</span>";
	case 1:
		return "<span style=\"color:green\">成功</span>";
	case 2:
		return "<span style=\"color:red\">失败</span>";
	default:
		return "<font style=\"color:#6b8e23\">未知状态</font>";
	}
}

/*
 * 公告类型转换
 */
function noticeTypeFormatter(value) {
	switch (value) {
	case 1:
		return "<span style=\"color:blue\">滚动</span>";
	case 2:
		return "<span style=\"color:green\">列表</span>";
	default:
		return "<font style=\"color:#6b8e23\">未知类型</font>";
	}
}

function yesnoFormatter(value){
	switch (value) {
	case 0:
		return "<span style=\"color:red\">否</span>";
	case 1:
		return "<span style=\"color:blue\">是</span>";
	default:
		return "未知状态";
	}
}

function competitionSubTypeFormatter(value){
	switch (value) {
	case 1:
		return "<span style=\"color:green\">半场</span>";
	case 2:
		return "<span style=\"color:blue\">全场</span>";
	case 3:
		return "<span style=\"color:blue\">全场总进球以上</span>";
	default:
		return "未知状态";
	}
}

/** 本地图片格式化 */
function imgFormatter(value) {
	return "<img src=\"../../"
			+ value
			+ "\" width=\"40px\" height=\"40px\" style=\"border:solid 1px #989898;margin-top:5px\" onerror=\"this.src='../../image/noImg.png'\"/>";
}

/** 远程图片格式化 直接访问* */
function userImgFormatter(value) {
	return "<img src=\""
			+ value
			+ "\" width=\"40px\" height=\"40px\" style=\"border:solid 1px #989898;margin-top:5px\"  onerror=\"this.src='../../image/noImg.png'\"/>";
}

/** 价格格式 单位：元->元 */
function priceFormatter(value) {
	return "￥" + value + "元";
}

/** 时间格式 单位：分钟 */
function timeFormatter(value) {
	return value + "分钟";
}
function dateFormatter(value) {
	return value.substring(0, 10);
}

function dateLongFormatter(value) {
	var now = new Date(value);
	var year = now.getYear();
	var month = now.getMonth() + 1;
	var date = now.getDate();
	var hour = now.getHours();
	var minute = now.getMinutes();
	var second = now.getSeconds();
	return "20" + String(year).substring(1) + "-" + month + "-" + date + " "
			+ hour + ":" + minute + ":" + second;
}

/**
 * 前三后四格式化手机号
 */
function phoneFormatter(value) {
	if (!Common.isEmpty(value)) {
		if (value.length == 11) {
			return value.substring(0, 3) + "****" + value.substring(7);
		} else {
			return value;
		}
	} else {
		return "";
	}
}
