
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script>

function search() {
	$('#picList').datagrid('load', {
		userId : $("#userId").combobox('getValue'),
		startDate : $("#startDate").datebox('getValue'),
		endDate : $("#startDate").datebox('getValue'),
		projectId : $("#projectId").combobox('getValue')		
	});
	console.log("xxxxxxxx");
	return false;
}

function exportData(){

	
	var data =  {
		userName : $("#userName").val(),
		operator : $("#operator").val(),
		year : $("#year").val(),
		month : $("#month").val(),
		projectId : $("#projectId").combobox('getValue'),
		teamId : $("#teamId").combobox('getValue')
		
	};
	
	postAjaxRequest("/ams/attendance/export.do", data, function(data){
		console.log(data);
	});
	
	
}
</script>

<div>
   <form action="/ams/attendance/pic/export.do" method="post">
	<span class="r-edit-label">上传者:</span> <input class="easyui-combobox textbox" type="text" id="userId" name="userId" data-options="
                    valueField:'id',
                    url:'/ams/user/list.do?userId=',
                    textField:'userName',
                    panelHeight:'auto',
                    loadFilter:function(data){
						return data.rows;
					}"></input>

				<span class="r-edit-label">项目:</span> <input class="easyui-combobox textbox" type="text" id="projectId" name="projectId" data-options="url:'/ams/project/list.do?userId=',
                    method:'get',
                    valueField:'id',
                    textField:'projectName',
                    panelHeight:'auto',
                    loadFilter:function(data){
						return data.rows;
					}"></input>
			
				
			
	<label>上传时间:</label>
	<input class="easyui-datebox textbox " type="text" name="startDate" id="startDate"/>
	<input class="easyui-datebox textbox " type="text" name="endDate" id="endDate"/>
	
	<button onclick="search(); return false;">搜索</button><button>导出</button>
	
	</form>
	
</div>
<table id="picList" class="easyui-datagrid" data-options="checkOnSelect:false, remoteFilter:true, fitColumns: true" url="/ams/user/pic/list.do" iconCls="icon-save"
	sortOrder="asc" pagination="true" singleSelect="true">
	<thead>
		<tr>
			<th align="center" field="userName" width="200" sortable="false" resizable="true">上传者</th>
			<th align="center" field="projectName" width="200" sortable="false" resizable="true">项目</th>
			<th align="center" field="description" width="200" sortable="false" resizable="true">描述</th>
			<th align="center" field="createdOn" width="200" sortable="false" resizable="true">上传时间</th>
			<th align="center" field="picUrl" data-options="formatter:formatterPicOperation"  width="200" sortable="false" resizable="true">图片</th>			
		</tr>
	</thead>
</table>