function forceLogin() {
	window.location.href = "/login.jsp";
}

function searchData(divId, inputId) {
	$('#' + divId).datagrid('load', {
		keyword : $("#" + inputId).val()
	});
}

function logout() {
	postAjaxRequest("/ecs/user/logout.do", {}, function(data) {
		window.location.href = "https://" + document.location.host
				+ "/login.jsp";
	}, false);
}

function formatterAttendanceDayType(val, row) {
	if (val == 1) {
		return "下午";
	}

	return "上午";

}

function formatterDescription(val, row) {

	if (!val) {
		return "";
	}
	if (val.length > 10) {
		var display = val.substr(0, 10) + "...";

		val = val.replace('\n', '<br>');

		return '<div  title="Basic Dialog" data-options="iconCls:\'icon-save\'" >'
				+ display
				+ '<a href="javascript:void(0)" title="'
				+ val
				+ '" style="margin-left:20px"; class="easyui-linkbutton" onclick="openDetail($(this));">更多</a></div>';

	} else {
		return val;
	}
}

function openDetail(target) {
	$('#dlg').html(target.attr("title"));
	$('#dlg').dialog({
		modal : true
	});
	$('#dlg').dialog('open');

}
function formatterAttendanceType(val, row) {

	var time = "";
	if (row.hours && row.hours > 0) {
		time = " (" + time + row.hours + "小时";
	}

	if (row.minutes && row.minutes > 0) {
		time = time + row.minutes + "分钟";
	}

	if (time.length > 0) {
		time = time + ")";
	}

	if (val == 0) {
		return "应勤出勤" + time;
	} else if (val == 1) {
		return "应休休息" + time;
	} else if (val == 2) {
		return "应勤请假" + time;
	} else if (val == 3) {
		return "旷工" + time;
	} else if (val == 4) {
		return "加班" + time;
	} else if (val == 5) {
		return "迟到" + time;
	} else if (val == 6) {
		return "早退" + time;
	} else if (val == 7) {
		return "中途脱岗" + time;
	} else if (val == 8) {
		return "未到岗位" + time;
	} else if (val == 9) {
		return "应出勤要求休息" + time;
	}

	return "其他出勤";

}

function formatterUserMobileLoginOperation(val, row) {

	if (val == 1) {
		return '<img src="/resources/images/check.png" style="cursor: pointer;"  onclick="disableUserLogin(\''
				+ row.id + '\',true, true);"/>';
	} else {
		return '<img src="/resources/images/cross.png" style="cursor: pointer;"  onclick="disableUserLogin(\''
				+ row.id + '\',true, false);"/>';
	}
}

function formatterUserWebLoginOperation(val, row) {
	if (val == 1) {
		return '<img src="/resources/images/check.png" style="cursor: pointer;" onclick="disableUserLogin(\''
				+ row.id + '\', false, true);"/>';
	} else {
		return '<img src="/resources/images/cross.png" style="cursor: pointer;"  onclick="disableUserLogin(\''
				+ row.id + '\', false, false);"/>';
	}
}

function disableUserLogin(userId, isMobile, isDisabled) {

	var submit = false;
	if (isMobile && isDisabled) {
		if (confirm("确定禁止此用户登录手机吗？"))

		{

			submit = true;

		}

	} else if (isMobile && !isDisabled) {
		if (confirm("确定启用此用户登录手机吗？"))

		{

			submit = true;

		}
	} else if (!isMobile && isDisabled) {
		if (confirm("确定禁止此用户登录后台吗？"))

		{

			submit = true;

		}

	} else if (!isMobile && !isDisabled) {
		if (confirm("确定启用此用户登录后台吗？"))

		{

			submit = true;

		}
	}

	var data = {};
	if (isMobile) {
		if (isDisabled) {
			data = {
				id : userId,
				status : 0
			};
		} else {
			data = {
				id : userId,
				status : 1
			};
		}
	} else {
		if (isDisabled) {
			data = {
				id : userId,
				bstatus : 0
			};
		} else {
			data = {
				id : userId,
				bstatus : 1
			};
		}
	}

	if (submit) {
		postAjaxRequest("/ams/user/add.do", data, function(data) {
			loadRemotePage("user/list&a=4");
		});
	}

}

function formatterLogOperation(val, row) {

	if (row.logType != "msg") {
		return '<a href="#" onclick=openLogDetails("' + row.id
				+ '")> 数据库修改详情 </a>';
	}
}

function formatterLogMsg(val, row) {

	if (row.message) {
		return row.message;
	}

	var logType = "更新";
	if (row.logType == "add") {
		logType = "新增";
	}

	if (row.urlPath == "/ams/sys/usertype/add.do") {

		return logType + "工种";
	} else if (row.urlPath == "/ams/sys/userlevel/add.do") {

		return logType + "工种级别";
	} else if (row.urlPath == "/ams/sys/group/add.do") {

		return logType + "角色";
	} else if (row.urlPath == "/ams/user/add.do") {

		return logType + "用户";
	} else if (row.urlPath == "/ams/user/department/add.do") {

		return logType + "部门";
	} else if (row.urlPath == "/ams/user/team/add.do") {

		return logType + "团队";
	} else if (row.urlPath == "/ams/user/attendance/app/add.do") {

		return logType + "考勤";
	} else if (row.urlPath == "/ams/user/customer/add.do") {

		return logType + "客户";
	} else if (row.urlPath == "/ams/user/pic/app/add.do") {

		return "上传图片";
	} else if (row.urlPath == "/ams/sys/usertype/add.do") {

		return logType + "工种";
	} else if (row.urlPath == "/ams/sys/usertype/add.do") {

		return logType + "工种";
	} else if (row.urlPath == "/ams/sys/usertype/add.do") {

		return logType + "工种";
	} else if (row.urlPath == "/ams/sys/usertype/add.do") {

		return logType + "工种";
	} else if (row.urlPath == "/ams/sys/usertype/add.do") {

		return logType + "工种";
	} else if (row.urlPath == "/ams/sys/usertype/add.do") {

		return logType + "工种";
	} else if (row.urlPath == "/ams/sys/usertype/add.do") {

		return logType + "工种";
	}

}

function formatterProjectOperation(val, row) {
	return '<a href="#" onclick=loadRemotePage("project/add&a=3&id=' + row.id
			+ '")> 编辑 </a>';
}

function formatterNoticeOperation(val, row) {
	return '<a href="#" onclick=loadRemotePage("notice/add&a=0&id='
			+ row.id
			+ '")> 编辑 </a><a style="margin-left:5px" href="#" onclick=deleteNotice("'
			+ row.id + '")> 删除 </a>';
}

function formatterDepartmentOperation(val, row) {

	return '<a href="#" onclick=loadRemotePage("department/add&a=4&id='
			+ row.id + '")> 编辑 </a>';
}

function formatteAttachFileLink(val, row) {
	if (val) {
		return '<a target="_blank" href="' + val + '")> 下载 </a>';
	}
}

function formatterUserTypeOperation(val, row) {
	return '<a href="#" onclick=loadRemotePage("sys/addusertype&a=5&id='
			+ row.id
			+ '")> 编辑 </a><a style="margin-left:5px" href="#" onclick=deleteUserType("'
			+ row.id + '")> 删除 </a>';
}

function formatteruserlevelOperation(val, row) {
	return '<a href="#" onclick=loadRemotePage("sys/adduserlevel&a=5&id='
			+ row.id
			+ '")> 编辑 </a><a style="margin-left:5px" href="#" onclick=deleteUserLevel("'
			+ row.id + '")> 删除 </a>';

}
function formatterPicOperation(val, row) {
	return '<a target="_blank" href="' + val
			+ '")> <img height="50" width="100" src="' + val + '"/> </a>';
}

function formatterUserOperation(val, row) {
	return '<a href="#" onclick=loadRemotePage("user/add&a=4&id=' + row.id
			+ '")> 编辑 </a>';
}

function formatterGroupOperation(val, row) {
	return '<a href="#" onclick=loadRemotePage("sys/addgroup&a=5&id='
			+ row.id
			+ '")> 编辑 </a><a style="margin-left:5px" href="#" onclick=deleteGroup("'
			+ row.id + '")> 删除 </a>';
}

function formatterCustomerOperation(val, row) {
	return '<a href="#" onclick=loadRemotePage("customer/add&a=3&id=' + row.id
			+ '")> 编辑 </a>';
	
	//<a style="margin-left:5px" href="#" onclick=deleteCustomer("'+ row.id + '")> 删除 </a>
}

function formatterTeamOperation(val, row) {

	return '<a href="#" onclick=loadRemotePage("team/add&a=3&id=' + row.id
			+ '")> 编辑 </a>';
}

function createLogPage(data, tableName) {
	$("#logDetail").html("");
	$(".remotePage").hide();
	var labels = {};

	var mfclabels = {
		mfcCompanyName : "厂商公司",
		mfcCompanyAdress : "厂商公司地址",
		mfcContactPerson : "厂商联系人",
		mfcContactPhone : "厂商电话",
		mfcContactMobilePhone : "厂商联系手机",
		mfcServiceTypeStr : "主营类型",
		mfcLocation : "所在地",
		mfcQQ : "厂商qq"
	}

	var splabels = {
		spUserName : "用户名",
		spServiceTypeStr : "主营服务类型",
		spCompanySize : "工人数",
		spCompanyName : "公司名称",
		spCompanyAddress : "公司地址",
		spContactPerson : "联系人",
		spLocation : "所在地",
		spContactPhone : "联系电话",
		spContactMobilePhone : "联系手机",
		spQQ : "qq"
	}

	var workderlabels = {
		workerName : "工人名",
		idCard : "请输入身份证",
		address : "地址",
		mobilePhone : "手机号码",
		workerType : "工人类型"
	}

	if (tableName == "Manufacturer") {
		labels = mfclabels;
	}
	if (tableName == "ServiceProvider") {
		labels = splabels;
	}
	if (tableName == "Worker") {
		labels = workderlabels;
	}
	if (data) {
		var div = $("#logDetail");
		$(div).show();
		var jsonData = JSON.parse(data);
		for ( var key in jsonData) {
			if (labels[key]) {
				$(
						'<div class="fitem width_455px"><span class="span_style_s"><label>'
								+ labels[key]
								+ ': </label></span><span class="span_style_s"><label class="width_360">'
								+ jsonData[key] + '</label></span></div>')
						.appendTo(div);
			}
		}

	}
}

/*
 * 
 * 公告相关JS代码
 * 
 */

function onNoticeClick(node) {
	var text = node.text;
	if (text == '新增') {
		loadRemotePage("notice/add");
	} else {
		loadRemotePage("notice/list");
	}
}

function onTaskClick(node) {
	var accor = "&a=2";
	var text = node.text;
	if (text == '任务管理') {
		loadRemotePage("task/list" + accor);
	} else if (text == '日报查询') {
		loadRemotePage("report/list" + accor);
	}
}

function onSysTreeClick(node) {
	var accor = "&a=5";
	var text = node.text;
	if (text == '员工类型管理') {
		loadRemotePage("sys/usertypelist" + accor);
	} else if (text == '员工级别管理') {
		loadRemotePage("sys/userlevellist" + accor);
	} else if (text == '部门管理') {
		loadRemotePage("department/list" + accor);
	} else if (text == '权限组管理') {
		loadRemotePage("user/grouplist" + accor);
	}
}

function onAttendanceClick(node) {
	var accor = "&a=1";
	var text = node.text;
	if (text == '考勤查询') {
		loadRemotePage("attendance/list" + accor);
	} else if (text == '图片管理') {
		loadRemotePage("pic/list" + accor);
	}

}

function onUserTreeClick(node) {
	var accor = "&a=4";
	var text = node.text;
	if (text == '新增用户') {
		loadRemotePage("user/add" + accor);
	} else if (text == '用户管理') {
		loadRemotePage("user/list" + accor);
	} else if (text == '部门管理') {
		loadRemotePage("department/list" + accor);
	} else if (text == '权限组管理') {
		loadRemotePage("user/grouplist" + accor);
	} else if (text == '图片管理') {
		loadRemotePage("pic/list" + accor);
	} else if (text == '施工队管理') {
		loadRemotePage("team/list" + accor);
	} else if (text == '工资管理') {
		loadRemotePage("salary/list" + accor);
	}

}

function onProjectClick(node) {
	var accor = "&a=3";
	var text = node.text;
	if (text == '项目管理') {
		loadRemotePage("project/list" + accor);
	} else if (text == '客户管理') {
		loadRemotePage("customer/list" + accor);
	} else if (text == '施工队管理') {
		loadRemotePage("team/list" + accor);
	}
}
