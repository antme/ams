
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<br>
<script>

function search() {
	$('#reportList').datagrid('load', {
		queryUserId : $("#userId").combobox('getValue'),
		startDate : $("#startDate").datebox('getValue'),
		endDate : $("#endDate").datebox('getValue'),
		projectId : $("#projectId").combobox('getValue')		
	});
	return false;
}

function deleteTasks(){
	deleteMultipleData("reportList", "/ams/user/report/delete.do");
}
</script>


<div>
   <form action="/ams/project/dailyreport/export.do" method="post">
	<span class="r-edit-label">用户:</span> <input class="easyui-combobox textbox" type="text" id="userId" name="userId" data-options="
                    valueField:'id',
                    url:'/ams/user/list.do?userId=',
                    textField:'userName',
                    panelHeight:'auto',
                    loadFilter:function(data){
						return data.rows;
					}"></input>

	<span class="r-edit-label">项目:</span> <input class="easyui-combobox textbox"  id ="projectId" type="text" name="projectId"
				data-options="
                    valueField:'id',
                    url:'/ams/project/list.do?userId=',
                    textField:'projectName',
                    panelHeight:'auto',
                    loadFilter:function(data){
						return data.rows;
					}"></input>
			
				
			
	<label>日报时间:</label>
	<input class="easyui-datebox textbox " type="text" name="startDate" id="startDate"/>
	<input class="easyui-datebox textbox " type="text" name="endDate" id="endDate"/>
	
	<button onclick="search(); return false;">搜索</button><button>导出</button>
	
	</form>
	
</div>

<button onclick="deleteTasks();">删除</button>
<table id="reportList" class="easyui-datagrid" data-options="checkOnSelect:false, remoteFilter:true, fitColumns: true" url="/ams/project/dailyreport/list.do" iconCls="icon-save"
	sortOrder="asc" pagination="true" singleSelect="true">
	<thead>
		<tr>
			<th data-options="field:'ck',checkbox:true"></th>
			<th align="center" field="userName" width="100" sortable="false" resizable="true">用户名</th>
			<th align="center" field="projectName" width="100" sortable="false" resizable="true">项目</th>
			<th align="center" field="reportDay" width="80" sortable="false" resizable="true">日报日期</th>
			<th align="center" field="workingRecord" width="100" sortable="false" resizable="true">工作纪录</th>
			<th align="center" field="materialRecord" width="100" sortable="false" resizable="true">材料纪录</th>
			<th align="center" field="summary" width="50" sortable="false" resizable="true">总结</th>
			<th align="center" field="plan" width="100" sortable="false" resizable="true">明日计划</th>			
			<th align="center" field="weather" width="100" sortable="false" resizable="true">天气</th>
		</tr>
	</thead>
</table>