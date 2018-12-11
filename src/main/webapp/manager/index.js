$(function() {
	var resultHeight = $(window).height() - $("#navbar").height()
			- $("#myTab").height() - 65;
	$("#myTabContent").height(resultHeight);
	
	$("#updatePwdContent").click(
			function() {
				var dialog = $("#condition").removeClass('hide')
						.dialog(
								{
									modal : true,
									title : "<div class='widget-header widget-header-small'><h4 class='smaller'><i class='ace-icon fa fa-search'></i>修改个人密码</h4></div>",
									title_html : true,
									width : "500px",
									buttons : [ {
										text : "取消",
										"class" : "btn btn-xs",
										click : function() {
											$(this).dialog("close");
										}
									}, {
										text : "确定",
										"class" : "btn btn-primary btn-xs",
										click : function() {
											updatePwdOfMy($(this),function(obj){
												obj.dialog("close");
											});
										}
									} ]
								});
			});
	

	//定义验证提示
	Common.formValidate("updatePwdForm", {
		oldPwd: {
            required: true
        },
        newPwd: {
            required: true
        },
        newPwdConfirm: {
            required: true
        }
    }, {
    	oldPwd: {
            required: "原密码不能为空."
        },
        newPwd: {
            required: "新密码不能为空."
        },
        newPwdConfirm: {
            required: "新密码确认不能为空"
        }
    });
});

var maxTabs = 11;// 主页标签最大展示个数值
// 打开菜单路径
function openMenu(url, tabId, title, mainMneuId) {
	// 清除菜单选中样式
	$("#allMenu li").each(function() {
		$(this).removeClass("active");
		$(this).removeClass("open");
		$(this).find("ul").css("display", "none");
	});
	// 添加菜单选中样式
	$("#menu_li_" + mainMneuId).addClass("active open");
	$("#menu_li_" + tabId).addClass("active");
	$("#menu_li_" + mainMneuId).find("ul").css("display", "block");
	openTab(url, tabId, title);
}
// 打开标签
function openTab(url, tabId, title) {
	clearCss();
	// 判断当前要打开的是否已经存在？
	if ($("#" + tabId).html()) {// 是的话刷新下
		$("#" + tabId).addClass("active");
		if ($("#" + tabId).parent().attr("id") == "myTab_more_ul") {
			$("#myTab_more_ul").parent().addClass("active");
		}
		$("#tab_" + tabId).addClass(" in active");
		$("#iframe_work_" + tabId).attr('src',
				$("#iframe_work_" + tabId).attr('src'));
	} else {// 不是的话就打开
		var liHtml = "<li class='active' id='"
				+ tabId
				+ "'><a data-toggle='tab' href='#tab_"
				+ tabId
				+ "'>"
				+ title
				+ "&nbsp;&nbsp;&nbsp;<i class='icon-remove bigger-110 width-auto' style='padding:5px;' onclick=\"clostTab('"
				+ tabId + "');\"></i></a></li>";
		var tabContentHtml = "<div id='tab_"
				+ tabId
				+ "' class='tab-pane in active' style='height:100%;'>"
				+ "<iframe id='iframe_work_"
				+ tabId
				+ "' src='"
				+ url
				+ "' scrolling='auto' frameborder=0 width='100%' height='100%''></iframe></div>";
		// 判断标签是否已经大于10个了？如果超过十个了用另外一种方式添加标签 TODO
		if ($("#myTab >li").length >= maxTabs) {
			$("#myTab_more_ul").parent().css("display", "block");
			$("#myTab_more_ul").parent().addClass("active");
			$("#myTab_more_ul").append(liHtml);
		} else {
			$("#myTab_more_ul").parent().before(liHtml);
		}
		$("#myTabContent").append(tabContentHtml);
	}
}
// 关闭标签
function clostTab(tabId) {
	var isMoreTabSub = $("#" + tabId).parent().attr("id") == "myTab_more_ul";
	// 判断是否选中当前要关闭的标签，是的话删除当前节点，然后显示前一个，不是的话就直接删除掉就可以了
	if ($("#" + tabId).attr("class").indexOf("active") >= 0) {
		clearCss();
		// 判断当前标签前边是否还有标签，是的话显示前边一个，不是的话就展示非更多签下边的最后一个并且隐藏更多按钮。
		if ($("#" + tabId).prev().html()) {
			$("#" + tabId).prev().attr("class", "active");
			if (isMoreTabSub) {
				$("#myTab_more_ul").parent().addClass("active");
			}
			$("#tab_" + tabId).prev().addClass(" in active");
		} else {
			$("#" + tabId).parent().parent().prev().attr("class", "active");
			$("#tab_" + $("#" + tabId).parent().parent().prev().attr("id"))
					.addClass(" in active");
		}
	}
	$("#" + tabId).remove();
	$("#tab_" + tabId).remove();
	if (!isMoreTabSub) {
		$("#myTab_more_ul").parent().before($("#myTab_more_ul li:first"));
	}
	closeMoreTab();
}
// 合适条件下隐藏更多标签
function closeMoreTab() {
	if ($("#myTab_more_ul >li").length == 0) {
		$("#myTab_more_ul").parent().css("display", "none");
	}
}
// 清楚选中样式
function clearCss() {
	// 清除tab选中样式
	$("#myTab >li").each(function() {
		$(this).removeClass("active");
	});
	// 清除tab中更多中的选中样式
	$("#myTab_more_ul >li").each(function() {
		$(this).removeClass("active");
	});
	// 清除tab内容选中样式
	$("#myTabContent >div").each(function() {
		$(this).removeClass(" in active");
	});
}
// 退出
function lognout() {
	$.ajax({
		type : 'POST',
		url : "loginOut",
		success : (function(data) {
			if (data.isSuccess == true) {
				location.href = Common.getPath();
			} else {
				alertUtil.waring(data.msg);
			}
		})
	});
}
//修改个人密码
function updatePwdOfMy(obj,callBack){
	//表单验证
	if (!$('#updatePwdForm').valid()) {
		Common.messageBox("提示", "表单信息填写不完整!", false);
		return false;
	}
	
	var oldPwd = $("#oldPwd").val();
	var newPwd = $("#newPwd").val();
	var newPwdConfirm = $("#newPwdConfirm").val();
	
	if(newPwdConfirm!=newPwd){
		Common.messageBox("提示", "两次新密码输入不一致，请确认!", false);
		return false;
	}
	Common.showMask();
	$.ajax({
		type : 'POST',
		url : "system/updatepwdofmy",
		data : {
			oldPwd : oldPwd,
			newPwd : newPwd
		},
		success : (function(data) {
			Common.hideMask();
			if (data.isSuccess == true) {
				alertUtil.alert(data.msg,callBack(obj));
			} else {
				alertUtil.waring(data.msg);
			}
		})
	});
}
function setJudgeCorpseStatus (thisObj){
	var flag = $(thisObj).prop('checked') == true ? 1 : 0;
	var title = flag == 1 ? "该功能开启后每日凌晨1点执行操作，确认开启吗?" : "关闭后将不再执行自动判断功能，确认关闭吗?";
	Common.confirm(title, function() {
		Common.showMask();
		$.ajax({
			url : "system/setsystemparams",
			type : "post",
			data : {
				category : "isOpenJudgeCorpse",
				value : flag
			},
			dataType : "json",
			success : function(json) {
				Common.hideMask();
				Common.messageBox("提示", json.msg, json.isSuccess);
			},
			error : function() {
				Common.hideMask();
				Common.messageBox("提示", Common.SERVER_EXCEPTION, false);
			}
		});
	});
}
function updateParams(category){
	Common.showMask();
	$.ajax({
		url : "system/setsystemparams",
		type : "post",
		data : {
			category : category,
			value : $("#"+category+"").val()
		},
		dataType : "json",
		success : function(json) {
			Common.hideMask();
			Common.messageBox("提示", json.msg, json.isSuccess);
		},
		error : function() {
			Common.hideMask();
			Common.messageBox("提示", Common.SERVER_EXCEPTION, false);
		}
	});
}