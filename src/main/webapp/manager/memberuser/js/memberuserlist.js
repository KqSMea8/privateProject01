$(function ($) {
	 Common.initSelect2("status", {
         width: "200px"
     });
	 Common.initSelect2("level", {
         width: "200px"
     });
    /** **************************************构造jqGrid start *************************************** */
    var gridSelector = "#grid-table";
    var pagerSelector = "#grid-pager";

    // 构造jqGrid配置信息
    var jqGridOption = new Common().createGridOption({
        pagerSelector: pagerSelector,
        gridSelector: gridSelector,
        parentDOMClass: "col-sm-12",
        title: "活跃会员列表",
        url: "getmemberuserlist",
        postData: {},
        multiSelect: true,
        height: "500px",
        sortName: "create_time",
        sortOrder: "desc",
        colNamesArray: ['会员ID', '会员名称', '登录帐号', '会员电话', '会员等级', '银行名称', '银行卡号', '充值总额', '下注总金额', '账户余额', '注册时间',/* '最后登录时间',*/ '状态', '是否指定等级'],
        colModel: [{
            name: "user_id",
            index: "user_id",
            width: 60,
            sorttype: "string",
            editable: false,
            hidden: true
        }, {
            name: "user_name",
            index: "user_name",
            width: 80,
            sorttype: "string",
            editable: false
        }, {
            name: "account",
            index: "account",
            width: 80,
            sorttype: "string",
            editable: false
        }, {
            name: "user_tel",
            index: "user_tel",
            width: 80,
            sorttype: "string",
            editable: false
        }, {
            name: "now_level",
            index: "now_level",
            width: 80,
            sorttype: "string",
            editable: false
        }, {
            name: "bank_name",
            index: "bank_name",
            width: 100,
            sorttype: "string",
            editable: false
        }, {
            name: "bank_number",
            index: "bank_number",
            width: 150,
            sorttype: "string",
            editable: false
        }, {
            name: "total_recharge",
            index: "total_recharge",
            width: 100,
            sorttype: "string",
            editable: false
        }, {
            name: "total_deal",
            index: "total_deal",
            width: 100,
            sorttype: "string",
            editable: false
        }, {
            name: "balance",
            index: "balance",
            width: 100,
            sorttype: "string",
            editable: true
        }, {
            name: "create_time",
            index: "create_time",
            width: 100,
            sorttype: "string",
            editable: false
        }, /*{
            name: "last_login_time",
            index: "last_login_time",
            width: 100,
            sorttype: "string",
            editable: false
        }, */{
            name: "status",
            index: "status",
            width: 100,
            sorttype: "string",
            editable: false,
            formatter : memberUserFormatter
        }, {
            name: "is_change_level",
            index: "is_change_level",
            width: 100,
            sorttype: "string",
            editable: false,
            formatter : yesnoFormatter
        }]
    });

    // 选中行事件
    jqGridOption.onSelectRow = function (rowIndex, status) {
        var rowData = $(gridSelector).getRowData(rowIndex);
    };

    // 渲染表格
    $(gridSelector).jqGrid(jqGridOption);

    $(window).triggerHandler('resize.jqGrid');// 浏览器大小发生改变时，GRID跟着一起变


    /** **************************************构造jqGrid end *************************************** */

    /** **************************************按钮事件 start *************************************** */

    $('#search').click(function () {
        var dialog = $("#condition").removeClass('hide')
            .dialog(
                {
                    modal : true,
                    title : "<div class='widget-header widget-header-small'><h4 class='smaller'><i class='ace-icon fa fa-search'></i> 请选择查询条件</h4></div>",
                    title_html : true,
                    width : "500px",
                    buttons : [ {
                        text : "取消",
                        "class" : "btn btn-xs",
                        click : function() {
                            $(this).dialog("close");
                        }
                    }, {
                        text : "查询",
                        "class" : "btn btn-primary btn-xs",
                        click : function() {
							search();
                            $(this).dialog("close");
                        }
                    },{
                        text : "重置",
                        "class" : "btn btn-primary btn-xs",
                        click : function() {
                            $('#status').select2("val", "");
                            $('#status').trigger("change");
							$('#userName').val("");
							$('#userAccount').val("");
							$('#userTel').val("");
							search();
                            $(this).dialog("close");
                        }
                    } ]
                });
    });
    
    /**
	 * 冻结用户
	 */
    $("#lock").click(function () {
        var selectedRowsId = Common.jqGridGetSelectedRow(gridSelector);
        if (Common.isEmpty(selectedRowsId) || selectedRowsId.length <= 0) {
            Common.messageBox("提示", "请选择一个你想冻结的用户！", false);
            return false;
        }

        var rowData = Common.jqGridGetRowData(gridSelector, selectedRowsId);

        Common.confirm("请确认是否要冻结该用户？", function () {
    		Common.showMask();
            $.ajax({
                url: "lock",
                type: "post",
                data: {
                    userId: rowData.user_id
                },
                dataType: "json",
                success: function (json) {
            		Common.hideMask();
                    if (json.isSuccess) {
                        Common.messageBox("提示", json.msg, true);
                        search();
                    } else {
                        Common.messageBox("提示", json.msg, false);
                    }
                },
                error: function () {
            		Common.hideMask();
                    Common.messageBox("提示", Common.SERVER_EXCEPTION, false);
                }
            });
        });
    });
    /**
	 * 解冻用户
	 */
    $("#open").click(function () {
        var selectedRowsId = Common.jqGridGetSelectedRow(gridSelector);
        if (Common.isEmpty(selectedRowsId) || selectedRowsId.length <= 0) {
            Common.messageBox("提示", "请选择一个你想解冻的用户！", false);
            return false;
        }

        var rowData = Common.jqGridGetRowData(gridSelector, selectedRowsId);

		Common.showMask();
        $.ajax({
            url: "open",
            type: "post",
            data: {
                userId: rowData.user_id
            },
            dataType: "json",
            success: function (json) {
        		Common.hideMask();
                if (json.isSuccess) {
                    Common.messageBox("提示", json.msg, true);
                    search();
                } else {
                    Common.messageBox("提示", json.msg, false);
                }
            },
            error: function () {
        		Common.hideMask();
                Common.messageBox("提示", Common.SERVER_EXCEPTION, false);
            }
        });
    });
    
    /**
     * 指定等级
     */
    $('#setVipLevel').click(function () {
   	 	var selectedRowsId = Common.jqGridGetSelectedRow(gridSelector);
    	if (Common.isEmpty(selectedRowsId) || selectedRowsId.length <= 0) {
    		Common.messageBox("提示", "请选择一个你指定等级的用户！", false);
    		return false;
    	}

    	var rowData = Common.jqGridGetRowData(gridSelector, selectedRowsId);
    	
		$('#level').select2("val", '0');
        $('#level').trigger("change");
    	
        var dialog = $("#setVipLevelForm").removeClass('hide')
            .dialog(
                {
                    modal : true,
                    title : "<div class='widget-header widget-header-small'><h4 class='smaller'><i class='ace-icon fa fa-search'></i> 请选择会员等级</h4></div>",
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
                        	
                        	if($('#level').val() == ''){
                        		Common.messageBox("提示", "请选择指定等级！", false);
                        		return false;
                        	}
                        	
                     		Common.showMask();
                             $.ajax({
                                 url: "setviplevel",
                                 type: "post",
                                 data: {
                                     userId : rowData.user_id,
                                     level : $('#level').val()
                                 },
                                 dataType: "json",
                                 success: function (json) {
                             		Common.hideMask();
                                     if (json.isSuccess) {
                                         Common.messageBox("提示", json.msg, true);
                                         search();
                                         $("#setVipLevelForm").dialog("close");
                                     } else {
                                         Common.messageBox("提示", json.msg, false);
                                     }
                                 },
                                 error: function () {
                             		Common.hideMask();
                                     Common.messageBox("提示", Common.SERVER_EXCEPTION, false);
                                 }
                             });
                        }
                    }]
                });
    });
    
    /*
     * 修改会员信息
     */
    $('#edit').click(function () {
   	 	var selectedRowsId = Common.jqGridGetSelectedRow(gridSelector);
    	if (Common.isEmpty(selectedRowsId) || selectedRowsId.length <= 0) {
    		Common.messageBox("提示", "请选择一个你指定等级的用户！", false);
    		return false;
    	}

    	var rowData = Common.jqGridGetRowData(gridSelector, selectedRowsId);
    	

        $('#userNameForEdit').val(rowData.user_name);
        $('#userTelForEdit').val(rowData.user_tel);
        $('#userBankNumberForEdit').val(rowData.bank_number);
        $('#userBankNameForEdit').val(rowData.bank_name);
    	
        var dialog = $("#editUserForm").removeClass('hide')
            .dialog(
                {
                    modal : true,
                    title : "<div class='widget-header widget-header-small'><h4 class='smaller'><i class='ace-icon fa fa-search'></i> 请填写会员信息</h4></div>",
                    title_html : true,
                    width : "600px",
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

                        	if(Common.isEmpty($('#userNameForEdit').val())){
                                Common.messageBox("提示", "请填写会员名称!", false);
                                return false;
                            }

                            if(Common.isEmpty($('#userTelForEdit').val())){
                                Common.messageBox("提示", "请填写会员电话!", false);
                                return false;
                            }
                        	
                        	if(Common.isEmpty($('#userBankNumberForEdit').val())){
                                Common.messageBox("提示", "请填写会员绑定卡号!", false);
                                return false;
                            }

                            if(Common.isEmpty($('#userBankNameForEdit').val())){
                                Common.messageBox("提示", "请填写会员绑定卡银行!", false);
                                return false;
                            }

                        	
                     		Common.showMask();
                             $.ajax({
                                 url: "edit",
                                 type: "post",
                                 data: {
                                     userId : rowData.user_id,
                                     userName : $('#userNameForEdit').val(),
                                     userTel : $('#userTelForEdit').val(),
                                     userBankNumber : $('#userBankNumberForEdit').val(),
                                     userBankName : $('#userBankNameForEdit').val()
                                 },
                                 dataType: "json",
                                 success: function (json) {
                             		Common.hideMask();
                                     if (json.isSuccess) {
                                         Common.messageBox("提示", json.msg, true);
                                         search();
                                         $("#editUserForm").dialog("close");
                                     } else {
                                         Common.messageBox("提示", json.msg, false);
                                     }
                                 },
                                 error: function () {
                             		Common.hideMask();
                                     Common.messageBox("提示", Common.SERVER_EXCEPTION, false);
                                 }
                             });
                        }
                    }]
                });
    });
    
    /**
     * 计算会员等级  
     */
    $("#calculationVipLevel").click(function () {

		Common.showMask();
        $.ajax({
            url: "calculationviplevel",
            type: "post",
            dataType: "json",
            success: function (json) {
        		Common.hideMask();
                if (json.isSuccess) {
                    Common.messageBox("提示", json.msg, true);
                    search();
                } else {
                    Common.messageBox("提示", json.msg, false);
                }
            },
            error: function () {
        		Common.hideMask();
                Common.messageBox("提示", Common.SERVER_EXCEPTION, false);
            }
        });
    });
    /** **************************************按钮事件 end *************************************** */
});
function search(){
	var params = {
		userName : $('#userName').val(),
		userAccount : $('#userAccount').val(),
		userTel : $('#userTel').val(),
		status : $('#status').val()
	};
	Common.jqGridReload("grid-table", params);
}