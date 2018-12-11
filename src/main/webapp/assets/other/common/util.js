var alertUtil = {
	/**
	 * 正确的信息提示
	 * 
	 * @param msg
	 * @param callback
	 */
	alert : function(msg, callback) {
		bootbox
				.alert({
					buttons : {
						ok : {
							label : "确定",
							className : 'btn-primary btn-sm'
						}
					},
					message : "<apsn style='color:#078E4B;font-weight:bold;font-size:18px;'>"
							+ msg + "</span>",
					callback : function() {
						if (callback)
							callback();
					},
					title : "<i class='icon-ok-sign' style='color:#078E4B;'></i><apsn style='color:#078E4B;font-weight:bold;margin-left:10px;'>系统提示</span>"
				})
	},
	/**
	 * 系统警告提示
	 * 
	 * @param msg
	 * @param callback
	 */
	waring : function(msg, callback) {
		bootbox
				.alert({
					buttons : {
						ok : {
							label : "确定",
							className : 'btn-sm'
						}
					},
					message : "<apsn style='color:#dd5a43;font-weight:bold;font-size:18px;'>"
							+ msg + "</span>",
					callback : function() {
						if (callback)
							callback();
					},
					title : "<i class='icon-warning-sign red'></i><apsn style='color:#dd5a43;font-weight:bold;margin-left:10px;'>系统警告</span>"
				})
	},
	/**
	 * 确认框
	 * 
	 * @param msg
	 * @param sureCallBack
	 *            确认回调函数
	 * @param noCallBack
	 *            取消回调函数
	 */
	confirm : function(msg, sureCallBack, noCallBack) {
		bootbox
				.confirm({
					buttons : {
						cancel : {
							label : '取消',
							className : 'btn-default btn-sm'
						},
						confirm : {
							label : '确认',
							className : 'btn-primary btn-sm'
						}
					},
					message : "<apsn style='font-weight:bold;font-size:18px;'>"
							+ msg + "</span>",
					callback : function(result) {
						if (result) {
							if (sureCallBack)
								sureCallBack();
						} else {
							if (noCallBack)
								noCallBack();
						}
					},
					title : "<i class='icon-cogs red'></i><apsn style='color:#dd5a43;font-weight:bold;margin-left:10px;'>系统确认框</span>"
				});
	}
}
var treeUtil = {
	createTree : function(id, data, multiSelect) {
		$(id)
				.ace_tree(
						{
							dataSource : new DataSourceTree({
								data : data
							}),// 树结构选数据
							multiSelect : multiSelect,// 是否可以多选
							loadingHTML : '<div class="tree-loading"><i class="icon-refresh icon-spin blue"></i></div>',// 加载时候的样式
							'open-icon' : 'icon-minus',// 打开样式
							'close-icon' : 'icon-plus',// 关闭样式
							'selectable' : true,// 是否可以选择
							'selected-icon' : 'icon-ok',// 选择过后的图片样式
							'unselected-icon' : 'icon-remove',// 为选择的图片样式
							// cacheItems : true,
							/*'folder-open-icon' : 'ace-icon tree-plus',
							'folder-close-icon' : 'ace-icon tree-minus',*/
							'folderSelect' : true
						});
		// 加载时候的方法内容
		$(id).on('loaded', function(evt, data) {
		});
		// 关闭菜单时候的方法
		$(id).on('closed', function(evt, data) {
		});
		// 打开菜单时候的方法
		$(id).on("opened", function(evt, data) {
			// alert(data.additionalParameters.id);
		});
		// data返回的选中的所有树节点数据，data.info是返回的要使用的数据数组。单选的时候默认取data.info[0]即可，additionalParameters是除了name和type的其他参数，包括children和其他自己需要的参数
		$(id).on("selected", function(evt, data) {
			// alert(data.info[0].additionalParameters.id);
		});
	}
}