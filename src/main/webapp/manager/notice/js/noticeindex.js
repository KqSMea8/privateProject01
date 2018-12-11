$(function ($) {
	 Common.initSelect2("status", {
         width: "200px"
     });
	 Common.initSelect2("type", {
         width: "200px"
     });
	 Common.initSelect2("statusForEdit", {
         width: "280px"
     });
	 Common.initSelect2("typeForEdit", {
         width: "280px"
     });
    /** **************************************构造jqGrid start *************************************** */
    var gridSelector = "#grid-table";
    var pagerSelector = "#grid-pager";

    // 构造jqGrid配置信息
    var jqGridOption = new Common().createGridOption({
        pagerSelector: pagerSelector,
        gridSelector: gridSelector,
        parentDOMClass: "col-sm-12",
        title: "公告列表",
        url: "getnoticelist",
        postData: {},
        multiSelect: true,
        height: "620px",
        sortName: "create_time",
        sortOrder: "desc",
        colNamesArray: ['公告ID', '标题', '标题-泰文', '内容','内容-泰文', '创建时间', '状态', '类型', '创建人','未转化状态','未转化类型'],
        colModel: [{
            name: "notice_id",
            index: "notice_id",
            width: 60,
            sorttype: "string",
            editable: false,
            hidden: true
        }, {
            name: "title",
            index: "title",
            width: 100,
            sorttype: "string",
            editable: false
        },{
            name: "title_th",
            index: "title_th",
            width: 100,
            sorttype: "string",
            editable: false
        },{
            name: "content",
            index: "content",
            width: 150,
            sorttype: "string",
            editable: false
        },{
            name: "content_th",
            index: "content_th",
            width: 150,
            sorttype: "string",
            editable: false
        },  {
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
            name: "type",
            index: "type",
            width: 80,
            sorttype: "string",
            editable: false,
            formatter : noticeTypeFormatter
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
        }, {
            name: "type",
            index: "type",
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
                            $('#type').select2("val", "");
                            $('#type').trigger("change");
							search();
                            $(this).dialog("close");
                        }
                    } ]
                });
    });
    
    $("#updateStatus").click(function () {
    	var noticeIds = "";
		dataRows = Common.jqGridGetSelectedRows(gridSelector);
		for (var i = 0; i < dataRows.length; i++) {
			if (dataRows[i] == "undefined") 
				continue;
			
			var noticeId = Common.jqGridGetRowData(gridSelector, dataRows[i]).notice_id;
			if (Common.isEmpty(noticeId)) 
				continue;
			
			noticeIds += noticeId + ",";
		}
    	if (Common.isEmpty(noticeIds)) {
            Common.messageBox("提示", "请至少选择一个你想修改状态的公告!", false);
            return false;
        }

		Common.showMask();
        $.ajax({
            url: "updatestatus",
            type: "post",
            data: {
            	noticeIds: noticeIds
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
    	
    	var noticeIds = "";
		dataRows = Common.jqGridGetSelectedRows(gridSelector);
		for (var i = 0; i < dataRows.length; i++) {
			if (dataRows[i] == "undefined") 
				continue;
			
			var noticeId = Common.jqGridGetRowData(gridSelector, dataRows[i]).notice_id;
			if (Common.isEmpty(noticeId)) 
				continue;
			
			noticeIds += noticeId + ",";
		}
    	if (Common.isEmpty(noticeIds)) {
            Common.messageBox("提示", "请至少选择一个你想删除的公告!", false);
            return false;
        }

		Common.showMask();
        $.ajax({
            url: "delete",
            type: "post",
            data: {
                noticeIds: noticeIds
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
        $('#typeForEdit').select2("val", "2");
        $('#typeForEdit').trigger("change");
        $('#title').val("");
        $('#content').val("");
        $('#title_th').val("");
        $('#content_th').val("");
    	
        var dialog = $("#editInfoDiv").removeClass('hide')
            .dialog(
                {
                    modal : true,
                    title : "<div class='widget-header widget-header-small'><h4 class='smaller'><i class='ace-icon fa fa-search'></i> 创建</h4></div>",
                    title_html : true,
                    width : "500px",
                    buttons : [{
                        text : "保存",
                        "class" : "btn btn-primary btn-xs",
                        click : function() {

                            var title = $('#title').val();
                            var title_th=$('#title_th').val();
                            var content = $('#content').val();
                            var content_th = $('#content_th').val();
                            
                            if(Common.isEmpty(title)){
                                Common.messageBox("提示", "请填写标题!", false);
                                return false;
                            }
                            if(Common.isEmpty(title_th)){
                                Common.messageBox("提示", "请填写[泰文]标题!", false);
                                return false;
                            }
                            

                            if(Common.isEmpty(content)){
                                Common.messageBox("提示", "请填写公告内容!", false);
                                return false;
                            }
                            if(Common.isEmpty(content_th)){
                                Common.messageBox("提示", "请填写[泰文]公告内容!", false);
                                return false;
                            }
                            
                            
                    		Common.showMask();
                        	$.ajax({
                                url: "save",
                                type: "post",
                                data: {
                                	status : $('#statusForEdit').val(),
                                	type : $('#typeForEdit').val(),
                                	title : title,
                                	title_th: title_th,
                                	content : content,
                                	content_th : content_th
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
        $('#typeForEdit').select2("val", rowData.type);
        $('#typeForEdit').trigger("change");
        $('#title').val(rowData.title);
        $('#content').val(rowData.content);
        $('#title_th').val(rowData.title_th);
        $('#content_th').val(rowData.content_th);
        
        var dialog = $("#editInfoDiv").removeClass('hide')
            .dialog(
                {
                    modal : true,
                    title : "<div class='widget-header widget-header-small'><h4 class='smaller'><i class='ace-icon fa fa-search'></i> 修改</h4></div>",
                    title_html : true,
                    width : "500px",
                    buttons : [{
                        text : "保存",
                        "class" : "btn btn-primary btn-xs",
                        click : function() {

                            var title = $('#title').val();
                            var title_th = $('#title_th').val();
                            var content = $('#content').val();
                            var content_th = $('#content_th').val();
                            
                            if(Common.isEmpty(title)){
                                Common.messageBox("提示", "请填写标题!", false);
                                return false;
                            }
                            if(Common.isEmpty(title_th)){
                                Common.messageBox("提示", "请填写[泰文]标题!", false);
                                return false;
                            }

                            if(Common.isEmpty(content)){
                                Common.messageBox("提示", "请填写公告内容!", false);
                                return false;
                            }
                            
                            if(Common.isEmpty(content_th)){
                                Common.messageBox("提示", "请填写[泰文]公告内容!", false);
                                return false;
                            }

                    		Common.showMask();
                        	$.ajax({
                                url: "save",
                                type: "post",
                                data: {
                                	status : $('#statusForEdit').val(),
                                	type : $('#typeForEdit').val(),
                                	title : title,
                                	title_th: title_th,
                                	content : content,
                                	content_th : content_th,
                                	noticeId : rowData.notice_id
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
function search(){
	var params = {
		type : $('#type').val(),
		status : $('#status').val()
	};
	Common.jqGridReload("grid-table", params);
}