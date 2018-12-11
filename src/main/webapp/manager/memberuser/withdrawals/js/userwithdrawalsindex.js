$(function ($) {
	 Common.initSelect2("status", {
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
        title: "会员提现申请列表",
        url: "getuserwithdrawalslist",
        postData: {},
        multiSelect: false,
        height: "630px",
        sortName: "status",
        sortOrder: "ASC",
        colNamesArray: ['交易记录ID', '申请会员名称', '会员登录帐号', '申请金额', '提现类型', '收款卡号', '手续费', '申请时间', '当前状态', '审核人', '审核时间', '审核备注','未转化审核状态','换算比率'],
        colModel: [{
            name: "deal_id",
            index: "deal_id",
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
        },{
            name: "account",
            index: "account",
            width: 80,
            sorttype: "string",
            editable: false
        }, {
            name: "money",
            index: "money",
            width: 80,
            sorttype: "string",
            editable: false
        },{
            name: "pay_type",
            index: "pay_type",
            width: 80,
            sorttype: "string",
            editable: false
        }, {
            name: "recharge_money_account",
            index: "recharge_money_account",
            width: 150,
            sorttype: "string",
            editable: false
        }, {
            name: "serviceCharge",
            index: "serviceCharge",
            width: 80,
            sorttype: "string",
            editable: false
        }, {
            name: "create_time",
            index: "create_time",
            width: 100,
            sorttype: "string",
            editable: false
        }, {
            name: "status",
            index: "status",
            width: 100,
            sorttype: "string",
            editable: false,
            formatter : audtiStatusFormatter
        }, {
            name: "operator_name",
            index: "operator_name",
            width: 100,
            sorttype: "string",
            editable: false
        }, {
            name: "audit_time",
            index: "audit_time",
            width: 100,
            sorttype: "string",
            editable: true
        }, {
            name: "remark",
            index: "remark",
            width: 100,
            sorttype: "string",
            editable: false
        },{
            name: "status",
            index: "status",
            width: 60,
            sorttype: "string",
            editable: false,
            hidden: true
        },{
            name: "converstion",
            index: "converstion",
            width: 60,
            sorttype: "string",
            editable: false
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
							$('#auditOperatorName').val("");
							search();
                            $(this).dialog("close");
                        }
                    } ]
                });
    });
    
    $('#audit').click(function () {
    	/*var dealLogIds = "";
		dataRows = Common.jqGridGetSelectedRows(gridSelector);
		for (var i = 0; i < dataRows.length; i++) {
			if (dataRows[i] == "undefined") continue;
			var dealId = Common.jqGridGetRowData(gridSelector, dataRows[i]).deal_id;
			if (Common.isEmpty(dealId)) continue;
			
			if(Common.jqGridGetRowData(gridSelector, dataRows[i]).status.indexOf("待处理")>0){
				dealLogIds += dealId + ",";
				continue;
			}else{
				dealLogIds = "";
				Common.messageBox("提示", "您选择的待审核记录中存在已经审核的数据,请核查!", false);
				return false;
			}
		}
    	if (Common.isEmpty(dealLogIds)) {
            Common.messageBox("提示", "请至少选择一个你想审核的申请!", false);
            return false;
        }*/
    	var selectedRowsId = Common.jqGridGetSelectedRow(gridSelector);
        if (Common.isEmpty(selectedRowsId) || selectedRowsId.length <= 0) {
            Common.messageBox("提示", "请选择一个你想审核的申请！", false);
            return false;
        }

        var rowData = Common.jqGridGetRowData(gridSelector, selectedRowsId);
        
        if(rowData.status > 0){
            Common.messageBox("提示", "该申请已经审核！", false);
            return false;
        }
    	var dealLogIds = rowData.deal_id;

    	$("#userNameForAudit").text(rowData.user_name);
    	$("#account").text(rowData.account);
    	$("#money").text(rowData.money);
//    	$("#bankName").text(rowData.bank_name);
    	$("#bankNumber").text(rowData.recharge_money_account);
    	$("#createTime").text(rowData.create_time);
    	

    	$("#payType").text(rowData.pay_type);
    	$("#chargeMoney").text((rowData.money-rowData.serviceCharge)*rowData.converstion);
    	$("#serviceCharge").text(rowData.serviceCharge);
    	
        var dialog = $("#auditDiv").removeClass('hide')
            .dialog(
                {
                    modal : true,
                    title : "<div class='widget-header widget-header-small'><h4 class='smaller'>请填写审核备注，并且确定审核方式。</h4></div>",
                    title_html : true,
                    width : "500px",
                    buttons : [ {
                        text : "取消",
                        "class" : "btn btn-xs",
                        click : function() {
                            $(this).dialog("close");
                        }
                    }, {
                        text : "同意",
                        "class" : "btn btn-primary btn-xs",
                        click : function() {
                        	if(Common.isEmpty($('#remark').val())){
                        		Common.messageBox("提示", "请填写备注!", false);
                            	return false;
                        	}
                        	audit(dealLogIds,1);
							search();
                            $(this).dialog("close");
                        }
                    },{
                        text : "拒绝",
                        "class" : "btn btn-warning btn-xs",
                        click : function() {
                        	if(Common.isEmpty($('#remark').val())){
                        		Common.messageBox("提示", "请填写备注!", false);
                            	return false;
                        	}
                        	audit(dealLogIds,2);
							search();
                            $(this).dialog("close");
                        }
                    } ]
                });
    });
    
    /** **************************************按钮事件 end *************************************** */
});
function search(){
	var params = {
		userName : $('#userName').val(),
		userAccount : $('#userAccount').val(),
		auditOperatorName : $('#auditOperatorName').val(),
		status : $('#status').val()
	};
	Common.jqGridReload("grid-table", params);
}

function audit(dealLogIds,status){
	Common.showMask();
	$.ajax({
        url: "auditwithdrawals",
        type: "post",
        data: {
        	dealLogIds : dealLogIds,
        	status : status,
        	remark : $('#remark').val()
        },
        dataType: "json",
        success: function (json) {
    		Common.hideMask();
            if (json.isSuccess) {
                Common.messageBox("提示", json.msg, true);
                $('#remark').val("");
                search();
            } else {
                Common.messageBox("提示", json.msg, false);
                $('#remark').val("");
            }
        },
        error: function () {
    		Common.hideMask();
            Common.messageBox("提示", Common.SERVER_EXCEPTION, false);
        }
    });
}