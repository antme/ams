var idParam = undefined;
var gridId = undefined;
var ieGridPopupInit = false;
function loadRemotePage(page) {

	window.location.href = "index.jsp?p=" + page;

}

function loadRemoteWindowPage(page, config) {

	if (page) {
		var url = page;
		if (!page.endWith(".html") && !page.endWith(".jsp")) {
			var pages = page.split("/");
			if (pages.length == 3) {
				url = "/pages/" + pages[0] + "/" + pages[1] + "/" + pages[2]
						+ ".jsp";
			}
			if (pages.length == 4) {
				url = "/pages/" + pages[0] + "/" + pages[1] + "/" + pages[2]
						+ "/" + pages[3] + ".jsp";
			}
		}

		$.ajaxSetup({
			cache : false
		// 关闭AJAX相应的缓存
		});

		$.ajax({
			url : url,
			success : function(data) {
				config.top = 50;
				config.maximizable = false;
				config.minimizable = false;
				config.collapsible = false;
				$("#remotePageWindow").show();
				$("#remotePageWindow").html(data);
				$("#remotePageWindow").window(config);
			},
			error : function(res) {

			}
		});

	}

}

function postAjaxRequest(url, parameters, callback, loading, hiddenDetaiPage) {
	if (loading == undefined || loading) {
		ajaxLoading();
	}
	$.ajax({
		url : url,
		success : function(responsetxt) {
			var res = undefined;
			try {
				eval("res=" + responsetxt);
			} catch (error) {
				res = responsetxt;
			}

			if (res && res.code != undefined && res.code != 1) {
				ajaxLoadEnd();
				dealMessage(res, "错误信息");
			} else {
				if (res) {
					eval("callback(res)");
				}

				if (hiddenDetaiPage === false) {

				} else {
					if (loading == undefined || loading) {

						$(".remotePage").hide();
					}

				}
			}
			if (loading == undefined || loading) {
				ajaxLoadEnd();
			}
		},

		error : function(res) {

			if (loading == undefined || loading) {
				ajaxLoadEnd();
			}
		},

		data : parameters,
		method : "post",
		cache : false
	});

}

function ajaxLoading() {
	$("<div class=\"datagrid-mask\"></div>").css({
		display : "block",
		width : "100%",
		height : document.body.scrollHeight
	}).appendTo("body");
	$("<div class=\"datagrid-mask-msg\"></div>").html("正在处理，请稍候。。。").appendTo(
			"body").css({
		display : "block",
		left : ($(document.body).outerWidth(true) - 190) / 2,
		top : ($(window).height() - 45) / 2
	});
}
function ajaxLoading2(top) {
	$("<div class=\"datagrid-mask\"></div>").css({
		display : "block",
		width : "100%",
		height : document.body.scrollHeight
	}).appendTo("body");
	$("<div class=\"datagrid-mask-msg\"></div>").html("正在处理，请稍候。。。").appendTo(
			"body").css({
		'display' : "block",
		'left' : ($(document.body).outerWidth(true) - 190) / 2,
		'top' : top
	});
}
function ajaxLoadEnd() {
	$(".datagrid-mask").remove();
	$(".datagrid-mask-msg").remove();
	run = false;
}

function forceLogin() {
	window.location.href = "/login.jsp";
}

String.prototype.endWith = function(s) {
	if (s == null || s == "" || this.length == 0 || s.length > this.length)
		return false;
	if (this.substring(this.length - s.length) == s)
		return true;
	else
		return false;
	return true;
};

function dealMessage(data, title, redirectPage) {
	$.messager.defaults = {
		ok : "确定",
		cancel : "取消"
	};
	var response = undefined;
	if ((data && data.code != undefined) || (data.length != undefined)) {
		response = data;
		eval("response=" + data);
	} else {
		eval("response=" + data);
	}
	if (data) {
		if (response.code != 1) {

			if (response.msg) {
				if (title) {
					$.messager.alert(title, response.msg, 'info');
				} else {
					$.messager.alert("信息", response.msg, 'info');
				}
			}
		}
		if (response.code == 1 && redirectPage) {
			window.location.href = redirectPage;
		}

		if (response.code == 1 && response.msg) {
			$.messager.alert("信息", response.msg, 'info');
		}
	} else {
		$.messager.alert('服务器错误', '服务器错误', 'error');
	}
}

function displayInfoMsg(msg) {
	$("#content-right-info").text(msg);
	$("#content-right-info").show();
	$('#content-right-info').fadeOut(5000);
}

function displayAlert(msg) {

	$.messager.alert('提示信息', msg, 'info');
}

function dealMessageWithCallBack(data, title, callback) {
	$.messager.defaults = {
		ok : "确定",
		cancel : "取消"
	};
	var response = undefined;
	if (data && data.code != undefined) {
		response = data;
		eval("response=" + data);
	} else {
		eval("response=" + data);
	}
	if (data) {
		if (response.code != 1) {
			if (title) {
				$.messager.alert(title, response.msg, 'info');
			} else {
				$.messager.alert("信息", response.msg, 'info');
			}
		}

		if (response.code == 1 && response.msg) {
			$.messager.alert("信息", response.msg, 'info');
		} else if (response.code == 1 && callback) {
			eval("callback(response)");
		}

	} else {
		$.messager.alert('服务器错误', '服务器错误', 'error');
	}
}

function logout() {
	postAjaxRequest("/ecs/user/logout.do", {}, function(data) {
		window.location.href = "https://" + document.location.host
				+ "/login.jsp";
	}, false);
}

// easy ui 获取grid多选数据的id， 可以根据key和filterValue过滤数据
function getGridCheckedIds(gridId, key, filterValue) {
	var rows = $('#' + gridId).datagrid('getChecked');
	var ids = new Array();
	for (i = 0; i < rows.length; i++) {
		if (key) {
			var rowValue = eval("rows[i]." + key);

			if (rowValue && filterValue && rowValue == filterValue) {
				ids.push(rows[i].id);
			}
		} else {
			ids.push(rows[i].id);
		}

	}
	return ids;
}

//easy ui 获取grid多选数据的id， 可以根据key和filterValue过滤数据
function getGridCheckedSigleId(gridId) {
	var rows = $('#' + gridId).datagrid('getChecked');

	if (!rows || rows.lenght == 0 || !rows[0]) {
		alert("点击列表选择数据");
		return;
	}

	if (rows.length > 1) {
		alert("只能选择一条数据");
		return;
	} else {
		var id = rows[0].id;
		return id;
	}

}

String.prototype.replaceAll = function(s1, s2) {
	return this.replace(new RegExp(s1, "gm"), s2);
}

String.prototype.trim = function() {
	//   用正则表达式将前后空格
	//   用空字符串替代。
	return this.replace(/(^\s*)|(\s*$)/g, "");
}

function dateFormatter(date) {
	var y = date.getFullYear();
	var m = date.getMonth() + 1;
	var d = date.getDate();
	return y + '-' + (m < 10 ? ('0' + m) : m) + '-' + (d < 10 ? ('0' + d) : d);
}

function dateTimeFormatter(date) {//date is the js date
	var y = date.getFullYear();
	var m = date.getMonth() + 1;
	var d = date.getDate();
	var h = date.getHours();
	var minu = date.getMinutes();
	var sec = date.getSeconds();

	if (y) {
		return y + '-' + (m < 10 ? ('0' + m) : m) + '-'
				+ (d < 10 ? ('0' + d) : d) + " " + (h < 10 ? ('0' + h) : h)
				+ ":" + (minu < 10 ? ('0' + minu) : minu) + ":"
				+ (sec < 10 ? ('0' + sec) : sec);
	}
	return "";
}

function showDateTimeFormatter(date) {//date is the java date
	date = new Date(date);//covert from java date to js date
	return dateTimeFormatter(date);
}

function showEstDateFormatter(date) {//date is the java date

	if (typeof date == "object") {
		date = new Date(date);//covert from java date to js date
		return dateFormatter(date);
	}
	if (date) {
		date = date.replace("00:00:00", "");
		return date.split(" ", 1);

	}
	return date;
}

function dateParser(s) {
	if (!s)
		return new Date();
	var ss = (s.split('-'));
	var y = parseInt(ss[0], 10);
	var m = parseInt(ss[1], 10);
	var d = parseInt(ss[2], 10);
	if (!isNaN(y) && !isNaN(m) && !isNaN(d)) {
		return new Date(y, m - 1, d);
	} else {
		return new Date();
	}
}

function dateTimeParser(s) {
}

String.prototype.trim = function() {
	return this.replace(/(^\s*)|(\s*$)/g, "");
}

function showGridEmptyDataMessage(id) {
	var target = $("#" + id);
	var opts = $(target).datagrid('options');
	var time = 1;
	if ($(target).datagrid('getPanel')) {
		var vc = $(target).datagrid('getPanel').children('div.datagrid-view');

		vc.children('div.datagrid-empty').remove();
		if (!$(target).datagrid('getRows').length) {

			var d = $('<div class="datagrid-empty"></div>').html('无数据')
					.appendTo(vc);
			d.css({
				position : 'absolute',
				left : 0,
				top : 40,
				width : '100%',
				textAlign : 'center'
			});
			var idevent = window.setInterval(function() {

				if ($(target).datagrid('getRows').length
						&& $(target).datagrid('getRows').length > 0) {
					vc.children('div.datagrid-empty').remove();
					clearInterval(idevent);
				} else {
					if (time > 5) {
						clearInterval(idevent);
					}
					if (vc.css('height') == '58px') {
						//					clearInterval(idevent);
					} else {
						vc.css('height', 58);
					}
				}
				time++;
			}, 500);
		} else {
			vc.children('div.datagrid-empty').remove();
		}

	}
	$(".datagrid-htable").css("width", "100%");
	$(".datagrid-btable").css("width", "100%");

	if (id == "soOrderList" && $("#soOrderList").length > 0) {
		$("#soOrderList").datagrid('resize', {
			width : "1064px"
		});
		if (jQuery.browser && jQuery.browser.msie
				&& jQuery.browser.version <= 7 && !ieGridPopupInit) {

			$(".window .datagrid-btable .datagrid-cell").each(function(index) {
				var width = $(this).parent().width();
				$(this).css("width", width - 10);
				$(this).parent().css("width", width - 2);
				var field = $(this).parent().attr("field");
				updateParent(field, width);

			});

		}

	}

}
function updateParent(field, width) {
	$(".window .datagrid-header-row td").each(function(index) {
		if ($(this).attr("field") == field) {

			$(this).css("width", width - 2);
			$(this).children().css("width", width - 10);
		}
	});
}

function fixWidth(width) {
	return document.body.clientWidth - 270; //这里你可以自己做调整  
}

function initDataGridEvent() {

	//表格和tab自动适应大小
	if ($(".easyui-datagrid_tf").length > 0) {
		$(".easyui-datagrid_tf").each(function(index) {
			var id = this.id;
			initDataGrid(id);
		});
	}

}

function initDataGrid(id, param) {
	if (param) {
		if (!param.width) {
			param.width = fixWidth(1);
		}
	} else {
		param = {
			width : fixWidth(1)
		};
	}
	if (id) {

		$("#" + id).datagrid(param);
		var opt = $("#" + id).datagrid('options');
		if (opt) {
			opt.showRefresh = false;
			opt.onLoadSuccess = function() {
				ieGridPopupInit = false;
				showGridEmptyDataMessage(id);

			};
			opt.onClickCell = loadPageDetail;

			opt.onBeforeLoad = function() {
				$(".remotePage").hide();
			}, opt.onSelect = function(rowIndex, rowData) {
				$("#" + id + " .datagrid-btable").find("tr").removeClass(
						"datagrid-row-selected");
				$("#" + id + " .datagrid-btable").find(
						"tr[datagrid-row-index=" + rowIndex + "]").addClass(
						"datagrid-row-selected");
			}
		}
	}
}

function resizeTabAndGrid() {

	//表格和tab自动适应大小
	if ($(".easyui-datagrid_tf").length > 0) {
		$(".easyui-datagrid_tf").each(function(index) {
			var id = this.id;
			if (id) {
				$("#" + id).datagrid('resize', {
					width : fixWidth(1)
				});
				showGridEmptyDataMessage(id);
			}
		})

	}

	if ($(".easyui-tabs").length > 0) {
		$(".easyui-tabs").each(function(index) {
			var id = this.id;
			if (id) {
				$("#" + id).tabs('resize');
			}
		})

	}
}

function loadPageDetail(rowIndex, field, value) {
	var rowData = undefined;
	var rows = $(this).datagrid('getRows');
	if ($(this)[0].id) {
		gridId = $(this)[0].id;
	}
	for (index in rows) {

		if (index == rowIndex) {
			rowData = rows[index];
			break;
		}
	}
	$("#logDetail").html("");
	if (field != "id") {
		if (!rowData.poId && rowData.poStatus) {
			openProductOrderDetailPage(rowData.id);
		} else if (rowData.soStatus) {
			openServiceOrderDetailPage(rowData.id);
		} else if (rowData.tableName && rowData.tableName == "ServiceOrder") {
			openServiceOrderDetailPage(rowData.dataId);
		} else if (rowData.tableName && rowData.tableName == "ProductOrder") {
			openProductOrderDetailPage(rowData.dataId);
		} else if (rowData.tableName
				&& (rowData.tableName == "Manufacturer"
						|| rowData.tableName == "User"
						|| rowData.tableName == "ServiceProvider" || rowData.tableName == "Worker")) {

			if (rowData.data) {
				createLogPage(rowData.data, rowData.tableName);
			}

		} else {

			$(".remotePage").hide();
		}

	} else {

	}
	return false;
}

function openDialog(id, title) {
	$("#" + id).dialog('open').dialog('setTitle', title);

	var o = $("#" + id);
	var itop = (document.documentElement.clientHeight - o.height()) / 2
			+ $(document).scrollTop();
	var ileft = (document.documentElement.clientWidth - o.width()) / 2
			+ document.documentElement.scrollLeft + document.body.scrollLeft;

	$('#' + id).dialog("move", {
		left : ileft,
		top : itop
	});

}

function s4() {
	return Math.floor((1 + Math.random()) * 0x10000).toString(16).substring(1);
};

function guid() {
	return s4() + s4() + '-' + s4() + '-' + s4() + '-' + s4() + '-' + s4()
			+ s4() + s4();
}

var noticeArray = Array();
var msgIndex = 0;
function updateNoticDiv(msg) {
	var find = false;
	for (index in noticeArray) {
		var item = noticeArray[index];
		if (item.type == msg.type) {
			item.msg = msg.msg;
			find = true;

			msgIndex = index;
			break;
		}

	}
	if (!find) {
		noticeArray.push(msg);
	}
	var tipDiv = $("#tips");
	tipDiv.html("");
	$(msg.msg).appendTo(tipDiv);

}

function getNextmsg() {

	if (msgIndex >= noticeArray.length) {
		msgIndex = 0;
	}
	for (index in noticeArray) {
		if (index == msgIndex) {
			var item = noticeArray[index];
			msgIndex = msgIndex + 1;
			var tipDiv = $("#tips");
			tipDiv.html("");
			$(item.msg).appendTo(tipDiv);
			break;
		}

	}
}

function pagerFilter(response) {

	data = {
		total : response.rows.length,
		rows : response.rows
	}

	var dg = $(this);
	var opts = dg.datagrid('options');
	var pager = dg.datagrid('getPager');
	pager.pagination({
		onSelectPage : function(pageNum, pageSize) {
			opts.pageNumber = pageNum;
			opts.pageSize = pageSize;
			pager.pagination('refresh', {
				pageNumber : pageNum,
				pageSize : pageSize
			});
			dg.datagrid('loadData', data);
		}
	});
	if (!data.originalRows) {
		data.originalRows = (data.rows);
	}
	var start = (opts.pageNumber - 1) * parseInt(opts.pageSize);
	var end = start + parseInt(opts.pageSize);
	data.rows = (data.originalRows.slice(start, end));
	return data;
}

function initFormSubmit(formId, url, title, callBack) {
	$("#" + formId).form({
		url : url,
		onSubmit : function() {
			return $(this).form('validate');
		},
		success : function(data) {
			dealMessageWithCallBack(data, title, callBack);

		},
		error : function() {
			var data = {};
			data.code = -1;
			data.msg = "连接服务器失败";
			dealMessageWithCallBack(data, "网络异常");
		}
	});
}

function deleteData(gridId, nodeId, url, callBack) {
	var data = {
		id : nodeId
	};

	if (confirm("确认删除此数据")) {
		postAjaxRequest(url, data, function(data) {
			alert("删除成功");
			$("#" + gridId).datagrid('reload');
		});
	}
}

function deleteMultipleData(gridId, url, callBack) {

	var ids = getGridCheckedIds(gridId);

	if (ids.length == 0) {
		alert("请先选择需要删除的数据");
		return false;
	}
	var data = {
		ids : ids
	};
	if (confirm("确认删除这些数据")) {
		postAjaxRequest(url, data, function(data) {
			alert("删除成功");
			$("#" + gridId).datagrid('reload');
		});
	}
}

String.prototype.startWith = function(str) {
	var reg = new RegExp("^" + str);
	return reg.test(this);
};

String.prototype.endWith = function(str) {
	var reg = new RegExp(str + "$");
	return reg.test(this);
};
