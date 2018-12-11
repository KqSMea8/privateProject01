$(function ($) {
	Common.initSelect2("status", {
		width: "200px"
	});
	
	if($("#actionType").val() == 'add'){
		$("#upload_logo1_img").hide();
		$("#upload_logo2_img").hide();
	} 
	
	$("#backBtn").click(back);
	$("#upload_logo1").uploadifive({
		buttonClass : "btn btn-xs btn-info",
		buttonText : "上传球队A-logo",
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
                $("#upload_logo1_url").val(jsonData.result);
                $("#upload_logo1_img").attr('src',jsonData.result); 
            	$("#upload_logo1_img").show();
            } else {
                Common.messageBox("提示", jsonData.msg, false);
            }
		}
	});

	$("#upload_logo2").uploadifive({
		buttonClass : "btn btn-xs btn-info",
		buttonText : "上传球队B-logo",
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
                $("#upload_logo2_url").val(jsonData.result);
                $("#upload_logo2_img").attr('src',jsonData.result); 
            	$("#upload_logo2_img").show();
            } else {
                Common.messageBox("提示", jsonData.msg, false);
            }
		}
	});
    $("#saveBtn").click(function () {
    	if(!verification()){
    		return false;
    	}
    	Common.showMask();
	    $.ajax({
	    	url: "save",
	        type: "post",
	        data: {
	        	competitionId: $("#competitionInfoId").val(),
	            title : $('#title').val(),
	            title_th:$('#title_th').val(),
	            name1 : $('#name1').val(),
	            name2 : $('#name2').val(),
	            name1_th : $('#name1_th').val(),
	            name2_th : $('#name2_th').val(),
	            logo1Url : $('#upload_logo1_url').val(),
	            logo2Url : $('#upload_logo2_url').val(),
	            gameBeginTime : $('#gameBeginTime').val(),
	            status : $('#status').val()
	        },
	        dataType: "json",
	        success: function (json) {
	        	Common.hideMask();
				if (json.isSuccess) {
	                Common.messageBox("提示", json.msg, true);
	                back();
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
function verification(){
	var title 	 = $('#title').val();
	var title_th = $('#title_th').val();
    var name1 = $('#name1').val();
	var name2 = $('#name2').val();
	
	var name1_th = $('#name1_th').val();
	var name2_th = $('#name2_th').val();
	
    var logo1Url = $('#upload_logo1_url').val();
    var logo2Url = $('#upload_logo2_url').val();
    var gameBeginTime = $('#gameBeginTime').val();
    var status = $('#status').val();
    var competitionInfoId = $("#competitionInfoId").val();
	 
    if($("#actionType").val() == 'edit' && Common.isEmpty(competitionInfoId)){
        Common.messageBox("提示", "页面数据异常!", false);
        return false;
    }
    
    if(Common.isEmpty(title)){
        Common.messageBox("提示", "请填写赛事标题!", false);
        return false;
    }
    
    if(Common.isEmpty(title_th)){
        Common.messageBox("提示", "请填写[泰文]赛事标题!", false);
        return false;
    }

    if(Common.isEmpty(name1) || Common.isEmpty(name2)){
        Common.messageBox("提示", "请填写球队名称!", false);
        return false;
    }
    
    if(Common.isEmpty(name1_th) || Common.isEmpty(name2_th)){
        Common.messageBox("提示", "请填写[泰文]球队名称!", false);
        return false;
    }

    if(Common.isEmpty(logo1Url) || Common.isEmpty(logo2Url)){
        Common.messageBox("提示", "请上传球队LOGO!", false);
        return false;
    }
    
    if(Common.isEmpty(gameBeginTime)){
        Common.messageBox("提示", "请设置开赛时间!", false);
        return false;
    }
    
    if(Common.isEmpty(status)){
        Common.messageBox("提示", "请选择状态!", false);
        return false;
    }
    return true;
}
function back (){
	location.href = "competitionindex";
}
