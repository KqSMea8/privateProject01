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
        title: "赛事列表",
        url: "getcompetitionlist",
        postData: {},
        multiSelect: true,
        height: "620px",
        sortName: "create_time",
        sortOrder: "desc",
        colNamesArray: ['赛事ID', '标题', '标题-泰文', '赛事', '赛事-泰文', '开赛时间', '创建时间', '状态', '创建人', '上半场比分','总比分','比分录入时间','比分录入人','总收益', '未转化状态'],
        colModel: [{
            name: "competition_id",
            index: "competition_id",
            width: 60,
            sorttype: "string",
            editable: false,
            hidden: true
        }, {
            name: "title",
            index: "title",
            width: 50,
            sorttype: "string",
            editable: false
        }, {
            name: "title_th",
            index: "title_th",
            width: 50,
            sorttype: "string",
            editable: false
        },{
            name: "vsName",
            index: "vsName",
            width: 100,
            sorttype: "string",
            editable: false
        },{
            name: "vsName_th",
            index: "vsName_th",
            width: 100,
            sorttype: "string",
            editable: false
        },  {
            name: "game_begin_time",
            index: "game_begin_time",
            width: 100,
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
            formatter : statusForCompetitionFormatter
        }, {
            name: "operator_name",
            index: "operator_name",
            width: 80,
            sorttype: "string",
            editable: false
        }, {
            name: "halfGameResult",
            index: "halfGameResult",
            width: 100,
            sorttype: "string",
            editable: false
        }, {
            name: "gameResult",
            index: "gameResult",
            width: 80,
            sorttype: "string",
            editable: false
        }, {
            name: "result_input_time",
            index: "result_input_time",
            width: 100,
            sorttype: "string",
            editable: false
        }, {
            name: "result_input_operator",
            index: "result_input_operator",
            width: 80,
            sorttype: "string",
            editable: false
        }, {
            name: "allEarnings",
            index: "allEarnings",
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
    	var competitionIds = "";
		dataRows = Common.jqGridGetSelectedRows(gridSelector);
		for (var i = 0; i < dataRows.length; i++) {
			if (dataRows[i] == "undefined") 
				continue;

			var rowData = Common.jqGridGetRowData(gridSelector, dataRows[i]);

			if(rowData.result_input_operator != ''){
				Common.messageBox("提示", "该赛事已经录入结果,不允许做任何改动!", false);
				return false;
			} 
			
			if(rowData.status == 2){
				Common.messageBox("提示", "该赛事已经取消,不允许做任何改动!", false);
				return false;
			}
			
			var competitionId = rowData.competition_id;
			if (Common.isEmpty(competitionId)) 
				continue;
			
			competitionIds += competitionId + ",";
		}
    	if (Common.isEmpty(competitionIds)) {
            Common.messageBox("提示", "请至少选择一个你想修改状态的赛事!", false);
            return false;
        }

		Common.showMask();
        $.ajax({
            url: "updatestatus",
            type: "post",
            data: {
            	competitionIds: competitionIds
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
    	
    	var competitionIds = "";
		dataRows = Common.jqGridGetSelectedRows(gridSelector);
		for (var i = 0; i < dataRows.length; i++) {
			if (dataRows[i] == "undefined") 
				continue;
			
			var rowData = Common.jqGridGetRowData(gridSelector, dataRows[i]);

			if(rowData.result_input_operator != ''){
				Common.messageBox("提示", "该赛事已经录入结果,不允许做任何改动!", false);
				return false;
			}
			
			var competitionId = rowData.competition_id;
			if (Common.isEmpty(competitionId)) 
				continue;

			competitionIds += competitionId + ",";
		}
    	if (Common.isEmpty(competitionIds)) {
            Common.messageBox("提示", "请至少选择一个你想删除的赛事!", false);
            return false;
        }
    	Common.confirm("请确认是否要删除该数据", function() {
			Common.showMask();
	        $.ajax({
	            url: "delete",
	            type: "post",
	            data: {
	                competitionIds: competitionIds
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
    
    $("#cancel").click(function () {
    	
    	var competitionIds = "";
		dataRows = Common.jqGridGetSelectedRows(gridSelector);
		for (var i = 0; i < dataRows.length; i++) {
			if (dataRows[i] == "undefined") 
				continue;
			
			var rowData = Common.jqGridGetRowData(gridSelector, dataRows[i]);

			if(rowData.result_input_operator != ''){
				Common.messageBox("提示", "该赛事已经录入结果,不允许做任何改动!", false);
				return false;
			}
			
			var competitionId = rowData.competition_id;
			if (Common.isEmpty(competitionId)) 
				continue;

			competitionIds += competitionId + ",";
		}
    	if (Common.isEmpty(competitionIds)) {
            Common.messageBox("提示", "请至少选择一个你想取消的赛事!", false);
            return false;
        }
    	Common.confirm("请确认是否要删除该数据", function() {
			Common.showMask();
	        $.ajax({
	            url: "cancel",
	            type: "post",
	            data: {
	                competitionIds: competitionIds
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
    
    $('#add').click(function () {
        location.href = "toedit";
    });
    
    $('#edit').click(function () {

		if(Common.jqGridGetSelectedRows(gridSelector).length>1){
			Common.messageBox("提示", "只能选择一条你想修改的赛事!", false);
			return false;
		}
		
    	var selectedRowsId = Common.jqGridGetSelectedRow(gridSelector);
        if (Common.isEmpty(selectedRowsId) || selectedRowsId.length <= 0) {
            Common.messageBox("提示", "请选择一条你想修改的数据！", false);
            return false;
        }

        var rowData = Common.jqGridGetRowData(gridSelector, selectedRowsId);

		if(rowData.result_input_operator != ''){
			Common.messageBox("提示", "该赛事已经录入结果,不允许做任何改动!", false);
			return false;
		}

		if(rowData.status == 2){
			Common.messageBox("提示", "该赛事已经取消,不允许做任何改动!", false);
			return false;
		}
        location.href = "toedit?competitionId=" + rowData.competition_id;
    });

    $('#toConfigSub').click(function () {

		if(Common.jqGridGetSelectedRows(gridSelector).length>1){
			Common.messageBox("提示", "只能选择一条你想配置比分的赛事!", false);
			return false;
		}
		
    	var selectedRowsId = Common.jqGridGetSelectedRow(gridSelector);
        if (Common.isEmpty(selectedRowsId) || selectedRowsId.length <= 0) {
            Common.messageBox("提示", "请选择一条你想配置的数据！", false);
            return false;
        }

        var rowData = Common.jqGridGetRowData(gridSelector, selectedRowsId);

		if(rowData.result_input_operator != ''){
			Common.messageBox("提示", "该赛事已经录入结果,不允许做任何改动!", false);
			return false;
		}

		if(rowData.status == 2){
			Common.messageBox("提示", "该赛事已经取消,不允许做任何改动!", false);
			return false;
		}
        location.href = "competitionsubindex?competitionId=" + rowData.competition_id + "&vsName="+rowData.vsName;
    });
    $('#setResult').click(function () {

    	var selectedRowsId = Common.jqGridGetSelectedRow(gridSelector);
		if (Common.isEmpty(selectedRowsId) || selectedRowsId.length <= 0) {
			Common.messageBox("提示", "请选择一条你想录入结果的赛事!", false);
			return false;
		}
		if(Common.jqGridGetSelectedRows(gridSelector).length>1){
			Common.messageBox("提示", "只能选择一条你想录入结果的赛事!", false);
			return false;
		}
		var rowData = Common.jqGridGetRowData(gridSelector, selectedRowsId);
        
		if(rowData.result_input_operator != ''){
			Common.messageBox("提示", "该赛事已经录入结果,不允许做任何改动!", false);
			return false;
		}

		if(rowData.status == 2){
			Common.messageBox("提示", "该赛事已经取消,不允许做任何改动!", false);
			return false;
		}
        $('#halfGameResult1').val("");
        $('#halfGameResult2').val("");
        $('#gameResult1').val("");
        $('#gameResult2').val("");
		
        var dialog = $("#setResultDiv").removeClass('hide')
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
                        	Common.confirm("比分录入后则不允许对赛事相关做任何修改,确定录入比分吗?", function() {
	                    		Common.showMask();
	                        	$.ajax({
	                                url: "setresult",
	                                type: "post",
	                                data: {
	                                	halfGameResult1 : $('#halfGameResult1').val(),
	                                	halfGameResult2 : $('#halfGameResult2').val(),
	                                	gameResult1 : $('#gameResult1').val(),
	                                	gameResult2 : $('#gameResult2').val(),
	                                	competitionId : rowData.competition_id
	                                },
	                                dataType: "json",
	                                success: function (json) {
	                            		Common.hideMask();
	                                    if (json.isSuccess) {
	                                        Common.messageBox("提示", json.msg, true);
	                                        search();
	                                        $("#setResultDiv").dialog("close");
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
     var halfGameResult1 = $('#halfGameResult1').val();
	 var halfGameResult2 = $('#halfGameResult2').val();
     var gameResult1 = $('#gameResult1').val();
	 var gameResult2 = $('#gameResult2').val();
	 
     if(Common.isEmpty(halfGameResult1) || Common.isEmpty(halfGameResult2)){
         Common.messageBox("提示", "请填写半场比分!", false);
         return false;
     }

     if(Common.isEmpty(gameResult1) || Common.isEmpty(gameResult2)){
         Common.messageBox("提示", "请填写整场比分!", false);
         return false;
     }
     
	 if (!Common.verificationInteger(halfGameResult1)
			|| !Common.verificationInteger(halfGameResult2)
			|| !Common.verificationInteger(gameResult1)
			|| !Common.verificationInteger(gameResult2)) {
         Common.messageBox("提示", "请填写正确比分,只能填写整数!", false);
         return false;
     }
     
     if(gameResult1 < halfGameResult1 || gameResult2 < halfGameResult2){
         Common.messageBox("提示", "整场比分应大于半场比分!", false);
         return false;
     }
     return true;
}
function search(){
	var params = {
		type : $('#type').val(),
		status : $('#status').val()
	};
	Common.jqGridReload("grid-table", params);
}