$(function ($) {
	 Common.initSelect2("isDecrease", {
         width: "200px"
     });
	 Common.initSelect2("type", {
         width: "200px"
     });
	 Common.initSelect2("isDecreaseForEdit", {
         width: "280px"
     });
	 Common.initSelect2("typeForEdit", {
         width: "280px"
     });
	 Common.initSelect2("isBackPrincipal", {
         width: "280px"
     });
	 Common.initSelect2("isOther", {
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
        title: "赛事比分列表",
        url: "getcompetitionsublist",
        postData: {
        	competitionId : $("#competitionId").val()
        },
        multiSelect: true,
        height: "620px",
        sortName: "create_time",
        sortOrder: "desc",
        colNamesArray: ['赛事比分ID', '比分', '全场总进球', '赔率', '可交易总量', '剩余可交易量', '单人最高限制金额', '类型', '是否归还本金', '是否递减', '递减比例', '创建时间', '创建人', '球队1比分', '球队2比分', '未转化类型', '未转化是否递减', '是否归还本金未转化', '是否其他比分', '未转化是否其他比分'],
        colModel: [{
            name: "competition_sub_id",
            index: "competition_sub_id",
            width: 60,
            sorttype: "string",
            editable: false,
            hidden: true
        }, {
            name: "gameResult",
            index: "gameResult",
            width: 100,
            sorttype: "string",
            editable: false
        },{
            name: "all_result",
            index: "all_result",
            width: 100,
            sorttype: "string",
            editable: false
        },{
            name: "loss_odds",
            index: "loss_odds",
            width: 100,
            sorttype: "string",
            editable: false
        }, {
            name: "can_deal_total_count",
            index: "can_deal_total_count",
            width: 100,
            sorttype: "string",
            editable: false
        }, {
            name: "can_deal_count",
            index: "can_deal_count",
            width: 100,
            sorttype: "string",
            editable: false
        }, {
            name: "max_deal_one_count",
            index: "max_deal_one_count",
            width: 80,
            sorttype: "string",
            editable: false
        },{
            name: "type",
            index: "type",
            width: 80,
            sorttype: "string",
            editable: false,
            formatter : competitionSubTypeFormatter
        },{
            name: "is_back_principal",
            index: "is_back_principal",
            width: 80,
            sorttype: "string",
            editable: false,
            formatter : yesnoFormatter
        }, {
            name: "is_decrease",
            index: "is_decrease",
            width: 80,
            sorttype: "string",
            editable: false,
            formatter : yesnoFormatter
        }, {
            name: "decrease_num",
            index: "decrease_num",
            width: 100,
            sorttype: "string",
            editable: false
        }, {
            name: "create_time",
            index: "create_time",
            width: 80,
            sorttype: "string",
            editable: false
        }, {
            name: "operator_name",
            index: "operator_name",
            width: 100,
            sorttype: "string",
            editable: false
        },{
            name: "team1_result",
            index: "team1_result",
            width: 60,
            sorttype: "string",
            editable: false,
            hidden: true
        },{
            name: "team2_result",
            index: "team2_result",
            width: 60,
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
        }, {
            name: "is_decrease",
            index: "is_decrease",
            width: 80,
            sorttype: "string",
            editable: false,
            hidden: true
        },{
            name: "is_back_principal",
            index: "is_back_principal",
            width: 80,
            sorttype: "string",
            editable: false,
            hidden: true
        },{
            name: "is_other",
            index: "is_other",
            width: 80,
            sorttype: "string",
            editable: false,
            formatter : yesnoFormatter
        },{
            name: "is_other",
            index: "is_other",
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

    $("#delete").click(function () {
    	
    	var competitionSubIds = "";
		dataRows = Common.jqGridGetSelectedRows(gridSelector);
		for (var i = 0; i < dataRows.length; i++) {
			if (dataRows[i] == "undefined") 
				continue;
			
			var competitionSubId = Common.jqGridGetRowData(gridSelector, dataRows[i]).competition_sub_id;
			if (Common.isEmpty(competitionSubId)) 
				continue;
			
			competitionSubIds += competitionSubId + ",";
		}
    	if (Common.isEmpty(competitionSubIds)) {
            Common.messageBox("提示", "请至少选择一个你想删除的赛事!", false);
            return false;
        }
    	Common.confirm("请确认是否要删除该数据", function() {
			Common.showMask();
	        $.ajax({
	            url: "deletesub",
	            type: "post",
	            data: {
	            	competitionSubIds: competitionSubIds
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
    
    $('#backMain').click(function () {
        location.href = "competitionindex";
    });
    

    $('#add').click(function () {

		$('#isDecreaseForEdit').select2("val", '0');
        $('#isDecreaseForEdit').trigger("change");
        $('#typeForEdit').select2("val", '');
        $('#typeForEdit').trigger("change");
        $('#isBackPrincipal').select2("val", '0');
        $('#isBackPrincipal').trigger("change");
        $('#isOther').select2("val", '0');
        $('#isOther').trigger("change");
        $('#decreaseNum').val('');
        $('#canDealTotalCount').val('');
        $('#maxDealOneCount').val('');
        $('#lossOdds').val('');
        $('#team1Result').val('');
        $('#team2Result').val('');
        $('#allResult').val('');
        
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
                                url: "savesub",
                                type: "post",
                                data: {
                                	team1Result : $('#team1Result').val(),
                                	team2Result : $('#team2Result').val(),
                                	allResult : $('#allResult').val(),
                                	lossOdds : $('#lossOdds').val()/100,
                                	canDealTotalCount : $('#canDealTotalCount').val(),
                                	maxDealOneCount : $('#maxDealOneCount').val(),
                                	type : $('#typeForEdit').val(),
                                	isDecrease : $('#isDecreaseForEdit').val(),
                                	decreaseNum : $('#decreaseNum').val()/100,
                                	competitionId : $('#competitionId').val(),
                                	isBackPrincipal : $('#isBackPrincipal').val(),
                                	isOther : $('#isOther').val()
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

		$('#isDecreaseForEdit').select2("val", rowData.is_decrease);
        $('#isDecreaseForEdit').trigger("change");
        $('#typeForEdit').select2("val", rowData.type);
        $('#typeForEdit').trigger("change");
        $('#isBackPrincipal').select2("val", rowData.is_back_principal);
        $('#isBackPrincipal').trigger("change");
        $('#isOther').select2("val", rowData.is_other);
        $('#isOther').trigger("change");
        $('#decreaseNum').val(rowData.decrease_num.replace('%',''));
        $('#canDealTotalCount').val(rowData.can_deal_total_count);
        $('#maxDealOneCount').val(rowData.max_deal_one_count == '不限'?0:rowData.max_deal_one_count);
        $('#lossOdds').val(rowData.loss_odds.replace('%',''));
        $('#team1Result').val(rowData.team1_result);
        $('#team2Result').val(rowData.team2_result);
        $('#allResult').val(rowData.all_result);
    	
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
                                url: "savesub",
                                type: "post",
                                data: {
                                	team1Result : $('#team1Result').val(),
                                	team2Result : $('#team2Result').val(),
                                	allResult : $('#allResult').val(),
                                	lossOdds : $('#lossOdds').val()/100,
                                	canDealTotalCount : $('#canDealTotalCount').val(),
                                	maxDealOneCount : $('#maxDealOneCount').val(),
                                	type : $('#typeForEdit').val(),
                                	isDecrease : $('#isDecreaseForEdit').val(),
                                	decreaseNum : $('#decreaseNum').val()/100,
                                	competitionSubId : rowData.competition_sub_id,
                                	competitionId : $('#competitionId').val(),
                                	isBackPrincipal : $('#isBackPrincipal').val(),
                                	isOther : $('#isOther').val()
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
	 var team1Result = $('#team1Result').val();
     var team2Result = $('#team1Result').val();
     var allResult = $('#allResult').val();
	 var lossOdds = $('#lossOdds').val();
     var canDealTotalCount = $('#canDealTotalCount').val();
     var maxDealOneCount = $('#maxDealOneCount').val();
	 var type = $('#typeForEdit').val();
	 var isDecreaseForEdit = $('#isDecreaseForEdit').val();
	 var decreaseNum = $('#decreaseNum').val();
	 var isOther = $('#isOther').val();
	 
     if(isOther == 0 && ((Common.isEmpty(allResult))&&(Common.isEmpty(team1Result)||Common.isEmpty(team2Result)))){
         Common.messageBox("提示", "请填写球队比分或者总进球!", false);
         return false;
     }
     
     if(!Common.verificationInteger(team1Result) || !Common.verificationInteger(team2Result) || !Common.verificationInteger(allResult)){
         Common.messageBox("提示", "比分/总进球只允许填写整数!", false);
         return false;
     }
     
     if(Common.isEmpty(lossOdds) || lossOdds == '0'){
         Common.messageBox("提示", "请填写赔率!", false);
         return false;
     }

     if(!Common.verificationDecimal( lossOdds )){
         Common.messageBox("提示", "请填写正确的赔率!", false);
         return false;
     }

     if(Common.isEmpty(canDealTotalCount) || canDealTotalCount == '0'){
         Common.messageBox("提示", "请填写可交易总量!", false);
         return false;
     }
     
     if(!Common.verificationDecimal(canDealTotalCount)){
         Common.messageBox("提示", "请填写正确的可交易总量!", false);
         return false;
     }

     if(!Common.verificationDecimal(maxDealOneCount)){
         Common.messageBox("提示", "请填写正确的最大限额!", false);
         return false;
     }
     if(Common.isEmpty(type)){
         Common.messageBox("提示", "请选择类型!", false);
         return false;
     }
     
     if(Common.isEmpty(isDecreaseForEdit)){
         Common.messageBox("提示", "请选择递减状态!", false);
         return false;
     }

     if(isDecreaseForEdit == 1 && (Common.isEmpty(decreaseNum) || !Common.verificationDecimal(decreaseNum))){
         Common.messageBox("提示", "递减赛事比分需要设置递减比例!", false);
         return false;
     }
     
     return true;
}

function search(){
	var params = {
		type : $('#type').val(),
		isDecrease : $('#isDecrease').val(),
    	competitionId : $("#competitionId").val()
	};
	Common.jqGridReload("grid-table", params);
}