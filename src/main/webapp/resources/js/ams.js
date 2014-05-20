
function forceLogin(){
	window.location.href="/login.jsp";
}



function logout(){
	postAjaxRequest("/ecs/user/logout.do", {}, function(data) {
		window.location.href="https://" + document.location.host + "/login.jsp";
	}, false);
}

function formatterProjectOperation(val, row){
	return '<a href="#" onclick=loadRemotePage("project/add&a=3&id=' + row.id+ '")> 编辑 </a>';
}


function formatterNoticeOperation(val, row){
	return '<a href="#" onclick=loadRemotePage("notice/add&a=0&id=' + row.id+ '")> 编辑 </a>';
}

function formatterDepartmentOperation(val, row){
	
	return '<a href="#" onclick=loadRemotePage("department/add&a=4&id=' + row.id+ '")> 编辑 </a>';
}


function formatteAttachFileLink(val, row){
	return '<a target="_blank" href="' + val + '")> 下载 </a>';
}

function formatterPicOperation(val, row){
	return '<a target="_blank" href="' + val + '")> <img height="50" width="100" src="' + val +'"/> </a>';
}

function formatterUserOperation(val, row){
	return '<a href="#" onclick=loadRemotePage("user/add&a=4&id=' + row.id+ '")> 编辑 </a>';
}

function formatterCustomerOperation(val, row){
	return '<a href="#" onclick=loadRemotePage("customer/add&a=3&id=' + row.id+ '")> 编辑 </a>';
}



function formatterTeamOperation(val, row){
	
	return '<a href="#" onclick=loadRemotePage("team/add&a=3&id=' + row.id+ '")> 编辑 </a>';
}

function formatterNewsDetail(val, row){//新闻列表：查看新闻详情
	return '<a href="#" onclick=openNewsDetailPage("' + row.id+ '")>' + val + '</a>';
}

function openSMDetailPage(id){
	idParam =id;	
	var config = {
		    width:800,
		    height:500,
		    modal:true,
		    minimizable:false,
		    maximizable:false,
		    collapsible:false,
		    title : "站内信息详情"
		};
	loadRemoteWindowPage("message/receiver/siteMessageDetail", config);
}



function createLogPage(data, tableName){
	$("#logDetail").html("");
	$(".remotePage").hide();
	var labels = {};
	
	var mfclabels = {
			mfcCompanyName: "厂商公司", 
			mfcCompanyAdress: "厂商公司地址", 
			mfcContactPerson: "厂商联系人", 
			mfcContactPhone: "厂商电话", 
			mfcContactMobilePhone: "厂商联系手机", 
			mfcServiceTypeStr: "主营类型", 
			mfcLocation: "所在地", 
			mfcQQ: "厂商qq"
	}
	
	var splabels = {
			spUserName: "用户名", 
			spServiceTypeStr: "主营服务类型", 
			spCompanySize: "工人数", 
			spCompanyName: "公司名称", 
			spCompanyAddress: "公司地址", 
			spContactPerson: "联系人", 
			spLocation: "所在地", 
			spContactPhone: "联系电话", 
			spContactMobilePhone :"联系手机", 
			spQQ: "qq"
	}
	
	var workderlabels = {
			workerName: "工人名", 
			idCard: "请输入身份证", 
			address: "地址", 
			mobilePhone: "手机号码", 
			workerType: "工人类型"
	}
	
	if(tableName == "Manufacturer"){
		labels = mfclabels;
	}
	if(tableName == "ServiceProvider"){
		labels = splabels;
	}
	if(tableName == "Worker"){
		labels = workderlabels;
	}
	if(data){
		var div = $("#logDetail");
		$(div).show();
		var jsonData = JSON.parse(data);
		for(var key in jsonData){		
			if(labels[key]){
				$('<div class="fitem width_455px"><span class="span_style_s"><label>' + labels[key] + ': </label></span><span class="span_style_s"><label class="width_360">' + jsonData[key] +'</label></span></div>').appendTo(div);
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

function onAttendanceClick(node){
	var accor = "&a=1";
	var text = node.text;
	if (text == '考勤查询') {
		loadRemotePage("attendance/list" + accor);
	} else if (text == '图片管理') {
		loadRemotePage("pic/list" + accor);
	}
	
}


function onUserTreeClick(node){
	var accor = "&a=4";
	var text = node.text;
	if (text == '新增用户') {
		loadRemotePage("user/add" + accor);
	} else if (text == '用户管理') {
		loadRemotePage("user/list" + accor);
	}else if (text == '部门管理') {
		loadRemotePage("department/list" + accor);
	}else if (text == '权限组管理') {
		loadRemotePage("user/grouplist" + accor);
	}else if (text == '图片管理') {
		loadRemotePage("pic/list" + accor);
	}else if (text == '团队管理') {
		loadRemotePage("team/list" + accor);
	}else if (text == '工资管理') {
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
	}else if (text == '团队管理') {
		loadRemotePage("team/list" + accor);
	}
}








