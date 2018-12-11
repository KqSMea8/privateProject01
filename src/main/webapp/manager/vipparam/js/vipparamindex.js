$(function ($) {

	 Common.initSelect2("needLevel", {
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
        title: "VIP等级列表",
        url: "getvipparamlist",
        postData: {},
        multiSelect: true,
        height: "620px",
        sortName: "create_time",
        sortOrder: "desc",
        colNamesArray: ['VIP等级ID', 'VIP等级名称', 'VIP等级', 'VIP直推需要达到人数', 'VIP团队需要达到人数', 'VIP直推需要直推人员星级', 'VIP团队需要直推人员星级人数', 'VIP团队收益点数', 'VIP描述', '创建时间', '最后更新时间'],
        colModel: [{
            name: "vip_param_id",
            index: "vip_param_id",
            width: 60,
            sorttype: "string",
            editable: false,
            hidden: true
        }, {
            name: "vip_name",
            index: "vip_name",
            width: 80,
            sorttype: "string",
            editable: false
        },{
            name: "vip_level",
            index: "vip_level",
            width: 50,
            sorttype: "string",
            editable: false
        }, {
            name: "vip_recommend_total",
            index: "vip_recommend_total",
            width: 80,
            sorttype: "string",
            editable: false
        }, {
            name: "vip_teamuser_total",
            index: "vip_teamuser_total",
            width: 80,
            sorttype: "string",
            editable: false
        },{
            name: "need_level",
            index: "need_level",
            width: 80,
            sorttype: "string",
            editable: false
        },{
            name: "need_level_total",
            index: "need_level_total",
            width: 80,
            sorttype: "string",
            editable: false
        }, {
            name: "vip_interest_rate",
            index: "vip_interest_rate",
            width: 80,
            sorttype: "string",
            editable: false
        }, {
            name: "vip_remark",
            index: "vip_remark",
            width: 200,
            sorttype: "string",
            editable: false
        }, {
            name: "create_time",
            index: "create_time",
            width: 80,
            sorttype: "string",
            editable: false
        }, {
            name: "last_update_time",
            index: "last_update_time",
            width: 80,
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

    $("#delete").click(function () {
    	
    	var selectedRowsId = Common.jqGridGetSelectedRow(gridSelector);
		if (Common.isEmpty(selectedRowsId) || selectedRowsId.length <= 0) {
			Common.messageBox("提示", "请选择一条你想删除的数据!", false);
			return false;
		}
		if(Common.jqGridGetSelectedRows(gridSelector).length>1){
			Common.messageBox("提示", "只能选择一条你想删除的数据!", false);
			return false;
		}
		var rowData = Common.jqGridGetRowData(gridSelector, selectedRowsId);

		Common.showMask();
        $.ajax({
            url: "delete",
            type: "post",
            data: {
            	vipParamId : rowData.vip_param_id,
            	level : rowData.vip_level
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

        $('#vipname').val("");
        $('#level').val("");
        $('#level').removeAttr('readonly');
        $('#recommendTotal').val("");
        $('#teamuserTotal').val("");
        $('#interestRate').val("");
        $('#needLevelTotal').val("");
        $('#needLevel').select2("val", '');
        $('#needLevel').trigger("change");
        $('#remark').val("");
    	
        var dialog = $("#editInfoDiv").removeClass('hide')
            .dialog(
                {
                    modal : true,
                    title : "<div class='widget-header widget-header-small'><h4 class='smaller'><i class='ace-icon fa fa-search'></i> 创建</h4></div>",
                    title_html : true,
                    width : "650px",
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
                                	name : $('#vipname').val(),
                                	level : $('#level').val(),
                                	recommendTotal : $('#recommendTotal').val(),
                                	teamuserTotal : $('#teamuserTotal').val(),
                                	interestRate : $('#interestRate').val()/100,
                                	remark : $('#remark').val(),
                                	needLevel : $('#needLevel').val(),
                                	needLevelTotal : $('#needLevelTotal').val()
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

        $('#vipname').val(rowData.vip_name);
        $('#level').val(rowData.vip_level);
        $('#level').attr('readonly','readonly');
        $('#recommendTotal').val(rowData.vip_recommend_total);
        $('#teamuserTotal').val(rowData.vip_teamuser_total);
        $('#interestRate').val(rowData.vip_interest_rate.replace('%',''));
        $('#remark').val(rowData.vip_remark);
        $('#needLevelTotal').val(rowData.need_level_total);
        $('#needLevel').select2("val", rowData.need_level);
        $('#needLevel').trigger("change");
        
        var dialog = $("#editInfoDiv").removeClass('hide')
            .dialog(
                {
                    modal : true,
                    title : "<div class='widget-header widget-header-small'><h4 class='smaller'><i class='ace-icon fa fa-search'></i> 修改</h4></div>",
                    title_html : true,
                    width : "650px",
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
                                	name : $('#vipname').val(),
                                	level : $('#level').val(),
                                	recommendTotal : $('#recommendTotal').val(),
                                	teamuserTotal : $('#teamuserTotal').val(),
                                	interestRate : $('#interestRate').val()/100,
                                	remark : $('#remark').val(),
                                	vipParamId: rowData.vip_param_id,
                                	needLevel : $('#needLevel').val(),
                                	needLevelTotal : $('#needLevelTotal').val()
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
	 var name = $('#vipname').val();
	 var level = $('#level').val();
	 var recommendTotal = $('#recommendTotal').val();
	 var teamuserTotal = $('#teamuserTotal').val();
	 var interestRate = $('#interestRate').val();

	 var needLevel = $('#needLevel').val();
	 var needLevelTotal = $('#needLevelTotal').val();
	 
	 if(Common.isEmpty(name)){
         Common.messageBox("提示", "请填VIP等级名称!", false);
         return false;
     }

     if(Common.isEmpty(level)){
         Common.messageBox("提示", "请填VIP等级!", false);
         return false;
     }
     
     if(!Common.verificationInteger( level )){
         Common.messageBox("提示", "请填写正确的VIP等级!", false);
         return false;
     }

     if(Common.isEmpty(recommendTotal)){
         Common.messageBox("提示", "请填写VIP直推需要达到人数!", false);
         return false;
     }

     if(!Common.verificationInteger( recommendTotal )){
         Common.messageBox("提示", "请填写正确的VIP直推需要达到人数!", false);
         return false;
     }

     if(needLevelTotal!='' && !Common.verificationInteger( needLevelTotal )){
         Common.messageBox("提示", "请填写正确的VIP需要直推人员星级人数!", false);
         return false;
     }
     
     if(Common.isEmpty(teamuserTotal)){
         Common.messageBox("提示", "请填写VIP团队需要达到人数!", false);
         return false;
     }

     if(!Common.verificationInteger( teamuserTotal )){
         Common.messageBox("提示", "请填写正确的VIP团队需要达到人数!", false);
         return false;
     }

     if(Common.isEmpty(interestRate)){
         Common.messageBox("提示", "请填写VIP团队收益点数!", false);
         return false;
     }
     
     if(!Common.verificationDecimal( interestRate )){
         Common.messageBox("提示", "请填写正确的VIP团队收益点数!", false);
         return false;
     }
     return true;
}
function search(){
	var params = {
		name : $('#vipname').val()
	};
	Common.jqGridReload("grid-table", params);
}