
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script>

	function search() {
		$('#projectList').datagrid('load', {
			projectName : $("#projectName").val(),
			projectAttendanceManagerId : $("#projectAttendanceManagerId").combobox('getValue'),
			projectManagerId : $("#projectManagerId").combobox('getValue')
			
		});
	}
</script>


<div>
	<label>项目名:</label>
	<input type="text" name="projectName" id="projectName"/> 
	
	
	
				<span class="r-edit-label">考勤负责人:</span> <input class="easyui-combobox textbox" type="text" id="projectAttendanceManagerId" name="projectAttendanceManagerId" data-options="
                    valueField:'id',
                    url:'/ams/user/list.do?userId=',
                    textField:'userName',
                    panelHeight:'auto',
                    loadFilter:function(data){
						return data.rows;
					}"></input>
			
				<span class="r-edit-label">项目负责人:</span> <input class="easyui-combobox textbox"  id ="projectManagerId" type="text" name="projectManagerId"
				data-options="
                    valueField:'id',
                    url:'/ams/user/list.do?userId=',
                    textField:'userName',
                    panelHeight:'auto',
                    loadFilter:function(data){
						return data.rows;
					}"></input>
			
			
	
	<button onclick="search();">搜索</button>
</div>

<button onclick="loadRemotePage('project/add&a=3');">新增项目</button>
<table id=projectList class="easyui-datagrid" data-options="checkOnSelect:false, remoteFilter:true, fitColumns: true" url="/ams/project/list.do" iconCls="icon-save"
	sortOrder="asc" pagination="true" singleSelect="true">
	<thead>
		<tr>
			<th align="center" field="projectName" width="100" sortable="false" resizable="true">项目名称</th>
			<th align="center" field="departmentName" width="100" sortable="false" resizable="true">部门</th>
			<th align="center" field="projectAttendanceManagerName" width="100" sortable="false" resizable="true">考勤负责人</th>
			<th align="center" field="projectManagerName" width="100" sortable="false" resizable="true">项目负责人</th>
			<th align="center" field="projectStatus" width="100" sortable="false" resizable="true">项目状态</th>
			<th align="center" field="customerName" width="100" sortable="false" resizable="true">客户</th>
			<th align="center" field="projectStartDate" width="100" sortable="false" resizable="true">项目开始日期</th>
			<th align="center" field="projectEndDate" width="100" sortable="false" resizable="true">项目结束日期</th>
			<th align="center" field="workTimePeriod" width="100" sortable="false" resizable="true">项目作息时间</th>
			<th align="center" field="projectDescription" width="100"  data-options="formatter:formatterDescription"  sortable="false" resizable="true">项目描述</th>
			<th align="center" data-options="field:'id',formatter:formatterProjectOperation"  width="120">操作</th>
			
		</tr>
	</thead>
</table>