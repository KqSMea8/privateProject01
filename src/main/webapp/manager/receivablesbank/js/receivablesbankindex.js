$(function ($) {
    /** **************************************构造jqGrid start *************************************** */
    
    var gridTypeSelector = "#grid-type-table";
    var pagerTypeSelector = "#grid-type-pager";

    // 构造jqGrid配置信息
    var jqTypeGridOption = new Common().createGridOption({
        pagerSelector: pagerTypeSelector,
        gridSelector: gridTypeSelector,
        parentDOMClass: "col-sm-3",
        title: "收款类型列表",
        url: "getreceivablestypelist",
        postData: {},
        multiSelect: false,
        height: "620px",
        sortName: "create_time",
        sortOrder: "desc",
        colNamesArray: ['收款类型ID', '类型', '状态'],
        colModel: [{
            name: "receivables_type_id",
            index: "receivables_type_id",
            width: 60,
            sorttype: "string",
            editable: false,
            hidden: true
        }, {
            name: "receivables_type_name",
            index: "receivables_type_name",
            width: 150,
            sorttype: "string",
            editable: false
        },{
            name: "status",
            index: "status",
            width: 150,
            sorttype: "string",
            editable: false,
            formatter : statusFormatter
        }]
    });

    // 选中行事件
    jqTypeGridOption.onSelectRow = function (rowIndex, status) {
        var rowData = $(gridTypeSelector).getRowData(rowIndex);
        $('#typeId').val(rowData.receivables_type_id);
        search();
    };
    $(gridTypeSelector).jqGrid(jqTypeGridOption);
    $(window).triggerHandler('resize.jqGrid');// 浏览器大小发生改变时，GRID跟着一起变
    

    var gridSelector = "#grid-table";
    var pagerSelector = "#grid-pager";
    var jqGridOption = new Common().createGridOption({
        pagerSelector: pagerSelector,
        gridSelector: gridSelector,
        parentDOMClass: "col-sm-9",
        title: "收款卡号列表",
        url: "getreceivablesbanklist",
        postData: {},
        multiSelect: true,
        height: "620px",
        sortName: "create_time",
        sortOrder: "desc",
        colNamesArray: ['收款卡ID', '帐号归属', '币种收款帐号', '开卡人姓名', '卡种类型', '点数比例', '创建时间','是否为收款卡'],
        colModel: [{
            name: "receivables_bank_id",
            index: "receivables_bank_id",
            width: 60,
            sorttype: "string",
            editable: false,
            hidden: true
        }, {
            name: "bank_name",
            index: "bank_name",
            width: 100,
            sorttype: "string",
            editable: false
        },{
            name: "bank_number",
            index: "bank_number",
            width: 250,
            sorttype: "string",
            editable: false
        }, {
            name: "bank_custom_name",
            index: "bank_custom_name",
            width: 100,
            sorttype: "string",
            editable: false
        }, {
            name: "bank_type",
            index: "bank_type",
            width: 80,
            sorttype: "string",
            editable: false
        }, {
            name: "conversion",
            index: "conversion",
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
            width: 80,
            sorttype: "string",
            editable: false,
            formatter : yesnoFormatter
        }]
    });

    // 渲染表格
    $(gridSelector).jqGrid(jqGridOption);
    $(window).triggerHandler('resize.jqGrid');

    
    /** **************************************构造jqGrid end *************************************** */

    /** **************************************按钮事件 start *************************************** */

    $("#setreceive").click(function () {
    	
    	if(Common.isEmpty($('#typeId').val())){
			Common.messageBox("提示", "请选择收款类型!", false);
			return false;
    	}
    	
    	var selectedRowsId = Common.jqGridGetSelectedRow(gridSelector);
		if (Common.isEmpty(selectedRowsId) || selectedRowsId.length <= 0) {
			Common.messageBox("提示", "请选择一条你想设置的数据!", false);
			return false;
		}
		if(Common.jqGridGetSelectedRows(gridSelector).length>1){
			Common.messageBox("提示", "只能选择一条你想设置的数据!", false);
			return false;
		}
		var rowData = Common.jqGridGetRowData(gridSelector, selectedRowsId);
		
		Common.showMask();
        $.ajax({
            url: "setreceive",
            type: "post",
            data: {
            	receivablesBankId: rowData.receivables_bank_id,
            	typeId : $('#typeId').val() 
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
            	receivablesBankId: rowData.receivables_bank_id
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

    	if(Common.isEmpty($('#typeId').val())){
			Common.messageBox("提示", "请选择收款类型!", false);
			return false;
    	}
        $('#bankName').val("");
        $('#number').val("");
        $('#customName').val("");
        $('#type').val("");
        $('#conversion').val("");
    	
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
                                	name : $('#bankName').val(),
                                	number : $('#number').val(),
                                	customName : $('#customName').val(),
                                	type : $('#type').val(),
                                	typeId : $('#typeId').val(),
                                	conversion : $('#conversion').val()
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

        $('#bankName').val(rowData.bank_name);
        $('#number').val(rowData.bank_number);
        $('#customName').val(rowData.bank_custom_name);
        $('#type').val(rowData.bank_type);
        $('#conversion').val(rowData.conversion);
        
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
                                	name : $('#bankName').val(),
                                	number : $('#number').val(),
                                	customName : $('#customName').val(),
                                	type : $('#type').val(),
                                	receivablesBankId : rowData.receivables_bank_id,
                                	conversion : $('#conversion').val()
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
    
    
    $("#deleteType").click(function () {
    	
    	var selectedRowsId = Common.jqGridGetSelectedRow(gridTypeSelector);
		if (Common.isEmpty(selectedRowsId) || selectedRowsId.length <= 0) {
			Common.messageBox("提示", "请选择一条你想删除的类型!", false);
			return false;
		}
		if(Common.jqGridGetSelectedRows(gridSelector).length>1){
			Common.messageBox("提示", "只能选择一条你想删除的数据!", false);
			return false;
		}
		var rowData = Common.jqGridGetRowData(gridTypeSelector, selectedRowsId);

		Common.showMask();
        $.ajax({
            url: "deletetype",
            type: "post",
            data: {
            	receivablesTypeIds: rowData.receivables_type_id
            },
            dataType: "json",
            success: function (json) {
        		Common.hideMask();
                if (json.isSuccess) {
                    Common.messageBox("提示", json.msg, true);
                    searchType();
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
    
    $("#updateTypeStatus").click(function () {
    	
    	var selectedRowsId = Common.jqGridGetSelectedRow(gridTypeSelector);
		if (Common.isEmpty(selectedRowsId) || selectedRowsId.length <= 0) {
			Common.messageBox("提示", "请选择一条你想修改状态的类型!", false);
			return false;
		}
		if(Common.jqGridGetSelectedRows(gridSelector).length>1){
			Common.messageBox("提示", "只能选择一条你想修改状态的数据!", false);
			return false;
		}
		var rowData = Common.jqGridGetRowData(gridTypeSelector, selectedRowsId);

		Common.showMask();
        $.ajax({
            url: "updatdstatus",
            type: "post",
            data: {
            	receivablesTypeIds: rowData.receivables_type_id
            },
            dataType: "json",
            success: function (json) {
        		Common.hideMask();
                if (json.isSuccess) {
                    Common.messageBox("提示", json.msg, true);
                    searchType();
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
    $('#addType').click(function () {

        $('#typeName').val("");
        $('#typeLogo').val("");
        $('#typeLogoImg').hide();
    	
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

                        	if(Common.isEmpty($('#typeLogo').val())){
                        		Common.messageBox("提示", "请上传logo!", false);	
                        		return false;
                        	}
                        	
                    		Common.showMask();
                        	$.ajax({
                                url: "savetype",
                                type: "post",
                                data: {
                                	name : $('#typeName').val(),
                                	logoUrl : $('#typeLogo').val()
                                },
                                dataType: "json",
                                success: function (json) {
                            		Common.hideMask();
                                    if (json.isSuccess) {
                                        Common.messageBox("提示", json.msg, true);
                                        searchType();
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

function verification(){
	 var name = $('#bankName').val();
	 var number = $('#number').val();
	 var customName = $('#customName').val();
	 var type = $('#type').val();
	 var conversion = $('#conversion').val();

	 if(Common.isEmpty(name)){
         Common.messageBox("提示", "请填写帐号归属!", false);
         return false;
     }

     if(Common.isEmpty(number)){
         Common.messageBox("提示", "请填写币种收款帐号!", false);
         return false;
     }
     
     /*if(!( number%1 === 0 ) || number.length <16 || number.length >19){
         Common.messageBox("提示", "请填写正确的银行卡号!", false);
         return false;
     }*/

     if(Common.isEmpty(customName)){
         Common.messageBox("提示", "请填写开卡人姓名!", false);
         return false;
     }
     
     if(Common.isEmpty(type)){
         Common.messageBox("提示", "请填写卡种类型!", false);
         return false;
     }
     
     if(Common.isEmpty(conversion)){
         Common.messageBox("提示", "请正确填写点数比例!", false);
         return false;
     }
     
     if(!Common.isEmpty(conversion) && !Common.verificationDecimal(conversion)){
         Common.messageBox("提示", "请正确填写点数比例!", false);
         return false;
     }
     return true;
}
function search(){
	var params = {
		typeId : $('#typeId').val()
	};
	Common.jqGridReload("grid-table", params);
}
function searchType(){
	var params = {};
	Common.jqGridReload("grid-type-table", params);
	search();
}