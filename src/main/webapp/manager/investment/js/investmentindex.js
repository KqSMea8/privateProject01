$(function ($) {
	 Common.initSelect2("status", {
         width: "200px"
     });
	 Common.initSelect2("statusForEdit", {
         width: "250px"
     });
    /** **************************************构造jqGrid start *************************************** */
    var gridSelector = "#grid-table";
    var pagerSelector = "#grid-pager";

    // 构造jqGrid配置信息
    var jqGridOption = new Common().createGridOption({
        pagerSelector: pagerSelector,
        gridSelector: gridSelector,
        parentDOMClass: "col-sm-12",
        title: "理财产品列表",
        url: "getinvestmentlist",
        postData: {},
        multiSelect: true,
        height: "620px",
        sortName: "create_time",
        sortOrder: "desc",
        colNamesArray: ['理财产品ID', '名称', '内容', '起始天数', '起始金额', '收益比例', '创建时间', '状态', '创建人', '未转换状态'],
        colModel: [{
            name: "investment_product_id",
            index: "investment_product_id",
            width: 60,
            sorttype: "string",
            editable: false,
            hidden: true
        }, {
            name: "name",
            index: "name",
            width: 100,
            sorttype: "string",
            editable: false
        }, {
            name: "content",
            index: "content",
            width: 100,
            sorttype: "string",
            editable: false
        }, {
            name: "min_day",
            index: "min_day",
            width: 100,
            sorttype: "string",
            editable: false
        }, {
            name: "start_money",
            index: "start_money",
            width: 100,
            sorttype: "string",
            editable: false
        }, {
            name: "earnings",
            index: "earnings",
            width: 100,
            sorttype: "string",
            editable: false
        },{
            name: "create_time",
            index: "create_time",
            width: 100,
            sorttype: "string",
            editable: false
        }, {
            name: "status",
            index: "status",
            width: 80,
            sorttype: "string",
            editable: false,
            formatter : statusFormatter
        }, {
            name: "operator_name",
            index: "operator_name",
            width: 100,
            sorttype: "string",
            editable: false
        }, {
            name: "status",
            index: "status",
            width: 80,
            sorttype: "string",
            editable: false,
            hidden: true
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
							search();
                            $(this).dialog("close");
                        }
                    } ]
                });
    });
    
    $("#updateStatus").click(function () {
    	var investmentIds = "";
		dataRows = Common.jqGridGetSelectedRows(gridSelector);
		for (var i = 0; i < dataRows.length; i++) {
			if (dataRows[i] == "undefined") 
				continue;
			
			var investmentId = Common.jqGridGetRowData(gridSelector, dataRows[i]).investment_product_id;
			if (Common.isEmpty(investmentId)) 
				continue;
			
			investmentIds += investmentId + ",";
		}
    	if (Common.isEmpty(investmentIds)) {
            Common.messageBox("提示", "请至少选择一个你想修改状态的理财产品!", false);
            return false;
        }

		Common.showMask();
        $.ajax({
            url: "updatestatus",
            type: "post",
            data: {
            	investmentIds: investmentIds
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

    $("#delete").click(function () {
    	var investmentIds = "";
		dataRows = Common.jqGridGetSelectedRows(gridSelector);
		for (var i = 0; i < dataRows.length; i++) {
			if (dataRows[i] == "undefined") 
				continue;
			
			var investmentId = Common.jqGridGetRowData(gridSelector, dataRows[i]).investment_product_id;
			if (Common.isEmpty(investmentId)) 
				continue;
			
			investmentIds += investmentId + ",";
		}
    	if (Common.isEmpty(investmentIds)) {
            Common.messageBox("提示", "请至少选择一个你想删除的理财产品!", false);
            return false;
        }

		Common.showMask();
        $.ajax({
            url: "delete",
            type: "post",
            data: {
                investmentIds: investmentIds
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
    
    $('#add').click(function () {

        $('#statusForEdit').select2("val", "0");
        $('#statusForEdit').trigger("change");
        $('#name').val("");
        $('#content').val("");
        $('#minDay').val("");
        $('#startMoney').val("");
        $('#earnings').val("");
    	 
        var dialog = $("#editInfoDiv").removeClass('hide')
            .dialog(
                {
                    modal : true,
                    title : "<div class='widget-header widget-header-small'><h4 class='smaller'><i class='ace-icon fa fa-search'></i> 创建</h4></div>",
                    title_html : true,
                    width : "600px",
                    buttons : [{
                        text : "保存",
                        "class" : "btn btn-primary btn-xs",
                        click : function() {

                        	if(!verification()){
                        		return false;
                        	}
                    		Common.showMask();
                        	$.ajax({
                                url: "save",
                                type: "post",
                                data: {
                                	status : $('#statusForEdit').val(),
                                	name : $('#name').val(),
                                	content : $('#content').val(),
                                	minDay : $('#minDay').val(),
                                	startMoney : $('#startMoney').val(),
                                	earnings : $('#earnings').val()/100
                                },
                                dataType: "json",
                                success: function (json) {
                            		Common.hideMask();
                                    if (json.isSuccess) {
                                        Common.messageBox("提示", json.msg, true);
                                        search();
                                        $("#editInfoDiv").dialog("close");
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
                    }, {
                        text : "取消",
                        "class" : "btn btn-xs",
                        click : function() {
                            $(this).dialog("close");
                        }
                    }]
                });
    });
    $('#edit').click(function () {

    	var selectedRowsId = Common.jqGridGetSelectedRow(gridSelector);
		if (Common.isEmpty(selectedRowsId) || selectedRowsId.length <= 0) {
			Common.messageBox("提示", "请选择一条你想修改的数据!", false);
			return false;
		}
		if(Common.jqGridGetSelectedRows(gridSelector).length>1){
			Common.messageBox("提示", "只能选择一条你想修改的数据进行修改!", false);
			return false;
		}
		var rowData = Common.jqGridGetRowData(gridSelector, selectedRowsId);

		$('#statusForEdit').select2("val", rowData.status);
        $('#statusForEdit').trigger("change");
        $('#name').val(rowData.name);
        $('#minDay').val(rowData.min_day);
        $('#startMoney').val(rowData.start_money);
        $('#content').val(rowData.content);
        $('#earnings').val(rowData.earnings.replace('%',''));
    	
        var dialog = $("#editInfoDiv").removeClass('hide')
            .dialog(
                {
                    modal : true,
                    title : "<div class='widget-header widget-header-small'><h4 class='smaller'><i class='ace-icon fa fa-search'></i> 修改</h4></div>",
                    title_html : true,
                    width : "600px",
                    buttons : [{
                        text : "保存",
                        "class" : "btn btn-primary btn-xs",
                        click : function() {

                        	if(!verification()){
                        		return false;
                        	}

                    		Common.showMask();
                        	$.ajax({
                                url: "save",
                                type: "post",
                                data: {
                                	status : $('#statusForEdit').val(),
                                	name : $('#name').val(),
                                	content : $('#content').val(),
                                	minDay : $('#minDay').val(),
                                	startMoney : $('#startMoney').val(),
                                	earnings : $('#earnings').val()/100,
                                	investmentId : rowData.investment_product_id
                                },
                                dataType: "json",
                                success: function (json) {
                            		Common.hideMask();
                                    if (json.isSuccess) {
                                        Common.messageBox("提示", json.msg, true);
                                        search();
                                        $("#editInfoDiv").dialog("close");
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
                    }, {
                        text : "取消",
                        "class" : "btn btn-xs",
                        click : function() {
                            $(this).dialog("close");
                        }
                    }]
                });
    });
    
    /** **************************************按钮事件 end *************************************** */
});

function verification(){
	 var name = $('#name').val();
     var content = $('#content').val();
	 var minDay = $('#minDay').val();
     var startMoney = $('#startMoney').val();
	 var earnings = $('#earnings').val();
	 
     if(Common.isEmpty(name)){
         Common.messageBox("提示", "请填写产品名称!", false);
         return false;
     }

     if(Common.isEmpty(content)){
         Common.messageBox("提示", "请填写内容介绍!", false);
         return false;
     }
     
     if(Common.isEmpty(minDay)){
         Common.messageBox("提示", "请填写周期天数!", false);
         return false;
     }
     
     if(!Common.verificationInteger( minDay)){
         Common.messageBox("提示", "请填写正确的周期天数!", false);
         return false;
     }

     if(Common.isEmpty(startMoney)){
         Common.messageBox("提示", "请填写起始金额!", false);
         return false;
     }
     
     if(!Common.verificationDecimal( startMoney)){
         Common.messageBox("提示", "请填写正确的起始金额!", false);
         return false;
     }

     if(Common.isEmpty(earnings)){
         Common.messageBox("提示", "请填写收益比例!", false);
         return false;
     }
     
     if(!Common.verificationDecimal( earnings )){
         Common.messageBox("提示", "请填写正确的收益比例!", false);
         return false;
     }
     return true;
}
function search(){
	var params = {
		status : $('#status').val()
	};
	Common.jqGridReload("grid-table", params);
}