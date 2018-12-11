$(function ($) {
    /** **************************************构造jqGrid start *************************************** */

    var gridSelector = "#grid-table";
    var pagerSelector = "#grid-pager";

    var jqGridOption = new Common().createGridOption({
        pagerSelector: pagerSelector,
        gridSelector: gridSelector,
        parentDOMClass: "col-sm-12",
        title: "提现类型列表",
        url: "getdrawmoneylist",
        postData: {},
        multiSelect: true,
        height: "620px",
        sortName: "status",
        sortOrder: "desc",
        colNamesArray: ['提现类型ID', '提现类型名称', '提现类型名称-泰文', '比率', '状态', 'logo图标地址'],
        colModel: [{
            name: "drawmoney_type_id",
            index: "drawmoney_type_id",
            width: 60,
            sorttype: "string",
            editable: false,
            hidden: true
        }, {
            name: "drawmoney_type_name",
            index: "drawmoney_type_name",
            width: 100,
            sorttype: "string",
            editable: false
        },{
            name: "drawmoney_type_name_th",
            index: "drawmoney_type_name_th",
            width: 100,
            sorttype: "string",
            editable: false
        },{
            name: "converstion",
            index: "converstion",
            width: 100,
            sorttype: "string",
            editable: false
        }, {
            name: "status",
            index: "status",
            width: 100,
            sorttype: "string",
            editable: false,
            formatter : statusFormatter
        }, {
            name: "drawmoney_type_logo",
            index: "drawmoney_type_logo",
            width: 80,
            sorttype: "string",
            editable: false,
            hidden: true
        }]
    });

    // 渲染表格
    $(gridSelector).jqGrid(jqGridOption);
    $(window).triggerHandler('resize.jqGrid');

    
    /** **************************************构造jqGrid end *************************************** */

    /** **************************************按钮事件 start *************************************** */
    
    $("#delete").click(function () {
    	
    	var selectedRowsId = Common.jqGridGetSelectedRow(gridSelector);
		if (Common.isEmpty(selectedRowsId) || selectedRowsId.length <= 0) {
			Common.messageBox("提示", "请选择一条你想删除的类型!", false);
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
            	drawmoneyTypeId: rowData.drawmoney_type_id
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
    
    $("#updatdstatus").click(function () {
    	
    	var selectedRowsId = Common.jqGridGetSelectedRow(gridSelector);
		if (Common.isEmpty(selectedRowsId) || selectedRowsId.length <= 0) {
			Common.messageBox("提示", "请选择一条你想修改状态的类型!", false);
			return false;
		}
		if(Common.jqGridGetSelectedRows(gridSelector).length>1){
			Common.messageBox("提示", "只能选择一条你想修改状态的数据!", false);
			return false;
		}
		var rowData = Common.jqGridGetRowData(gridSelector, selectedRowsId);

		Common.showMask();
        $.ajax({
            url: "updatdstatus",
            type: "post",
            data: {
            	drawmoneyTypeId: rowData.drawmoney_type_id
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
    
    $("#typeLogoButton").uploadifive({
		buttonClass : "btn btn-xs btn-info",
		buttonText : "上传Logo",
		uploadScript : "../common/upload/uploadImg",
		fileSizeLimit : 1024 * 20,
		fileType : "image/*",
		height : "30px",
		width : "110px",
		queueSizeLimit : 1,
		multi : true,
		removeCompleted : true,
           dataType: "json",
		onUploadComplete : function(file, data) {
			var jsonData = eval("(" + data + ")");
			if (jsonData.isSuccess) {
                Common.messageBox("提示", jsonData.msg, true);
                $("#typeLogo").val(jsonData.result);
                $("#typeLogoImg").attr('src',jsonData.result); 
            	$("#typeLogoImg").show();
            } else {
                Common.messageBox("提示", jsonData.msg, false);
            }
		}
	});
    
    $('#add').click(function () {

        $('#typeName').val("");
        $('#typeName_th').val("");
        $('#typeLogo').val("");
        $('#typeLogoImg').hide();
        $('#converstion').val("");
        
        var dialog = $("#addTypeDiv").removeClass('hide')
            .dialog(
                {
                    modal : true,
                    title : "<div class='widget-header widget-header-small'><h4 class='smaller'><i class='ace-icon fa fa-search'></i> 添加类型</h4></div>",
                    title_html : true,
                    width : "500px",
                    buttons : [{
                        text : "保存",
                        "class" : "btn btn-primary btn-xs",
                        click : function() {

                        	if(Common.isEmpty($('#typeName').val())){
                        		Common.messageBox("提示", "请填写类型名称!", false);	
                        		return false;
                        	}
                        	if(Common.isEmpty($('#typeName_th').val())){
                        		Common.messageBox("提示", "请填写[泰文]类型名称!", false);	
                        		return false;
                        	}

                        	if(Common.isEmpty($('#typeLogo').val())){
                        		Common.messageBox("提示", "请上传logo!", false);	
                        		return false;
                        	}

                        	if(!Common.verificationDecimal($('#converstion').val())){
                        		Common.messageBox("提示", "请填写正确的比率!", false);	
                        		return false;
                        	}
                    		Common.showMask();
                    		alert($('#typeName_th').val());
                        	$.ajax({
                                url: "save",
                                type: "post",
                                data: {
                                	name : $('#typeName').val(),
                                	name_th : $('#typeName_th').val(),
                                	logourl : $('#typeLogo').val(),
                                	converstion : $('#converstion').val()
                                },
                                dataType: "json",
                                success: function (json) {
                            		Common.hideMask();
                                    if (json.isSuccess) {
                                        Common.messageBox("提示", json.msg, true);
                                        search();
                                        $("#addTypeDiv").dialog("close");
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
			Common.messageBox("提示", "请选择一条你想修改状态的类型!", false);
			return false;
		}
		if(Common.jqGridGetSelectedRows(gridSelector).length>1){
			Common.messageBox("提示", "只能选择一条你想修改状态的数据!", false);
			return false;
		}
		var rowData = Common.jqGridGetRowData(gridSelector, selectedRowsId);

        $('#typeName').val(rowData.drawmoney_type_name);
        $('#typeName_th').val(rowData.drawmoney_type_name_th);
        $('#typeLogo').val(rowData.drawmoney_type_logo);
        $('#typeLogoImg').attr("src",rowData.drawmoney_type_logo);
        $('#converstion').val(rowData.converstion);
        
        var dialog = $("#addTypeDiv").removeClass('hide')
            .dialog(
                {
                    modal : true,
                    title : "<div class='widget-header widget-header-small'><h4 class='smaller'><i class='ace-icon fa fa-search'></i> 添加类型</h4></div>",
                    title_html : true,
                    width : "500px",
                    buttons : [{
                        text : "保存",
                        "class" : "btn btn-primary btn-xs",
                        click : function() {

                        	if(Common.isEmpty($('#typeName').val())){
                        		Common.messageBox("提示", "请填写类型名称!", false);	
                        		return false;
                        	}
                        	
                        	if(Common.isEmpty($('#typeName_th').val())){
                        		Common.messageBox("提示", "请填写[泰文]类型名称!", false);	
                        		return false;
                        	}

                        	if(Common.isEmpty($('#typeLogo').val())){
                        		Common.messageBox("提示", "请上传logo!", false);	
                        		return false;
                        	}

                        	if(!Common.verificationDecimal($('#converstion').val())){
                        		Common.messageBox("提示", "请填写正确的比率!", false);	
                        		return false;
                        	}
                    		Common.showMask();
                        	$.ajax({
                                url: "save",
                                type: "post",
                                data: {
                                	drawmoneyTypeId : rowData.drawmoney_type_id,
                                	name : $('#typeName').val(),
                                	name_th:  $('#typeName_th').val(),
                                	logourl : $('#typeLogo').val(),
                                	converstion : $('#converstion').val()
                                },
                                dataType: "json",
                                success: function (json) {
                            		Common.hideMask();
                                    if (json.isSuccess) {
                                        Common.messageBox("提示", json.msg, true);
                                        search();
                                        $("#addTypeDiv").dialog("close");
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

function search(){
	var params = {
		typeId : $('#typeId').val()
	};
	Common.jqGridReload("grid-table", params);
}