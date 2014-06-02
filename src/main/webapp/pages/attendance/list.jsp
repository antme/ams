
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script>

	function search() {
		$('#attList').datagrid('load', {
			userId : $("#userId").combobox('getValue'),
			operatorId : $("#operatorId").combobox('getValue'),
			year : $("#year").val(),
			month : $("#month").val(),
			projectId : $("#projectId").combobox('getValue'),
			teamId : $("#teamId").combobox('getValue')
			
		});
	}
	
	
	function checkExport(){
		if($("#year").val() == ""){
			alert("请输入年");
			return false;
		}
		
		if($("#month").val() == ""){
			alert("请输入月");
			return false;
		}
		
		return true;
	}
</script>


<div>
   <form action="/ams/attendance/export.do" method="post">
   <label>员工:</label>
   <input class="easyui-combobox textbox" type="text" id="userId" name="userId" data-options="
                    valueField:'id',
                    url:'/ams/user/list.do?userId=',
                    textField:'userName',
                    panelHeight:'auto',
                    loadFilter:function(data){
						return data.rows;
					}"></input>
	
	<label>考勤人:</label>
	 <input class="easyui-combobox textbox" type="text" id="operatorId" name="operatorId" data-options="
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
			
				<span class="r-edit-label">施工队:</span> <input class="easyui-combobox textbox"  id ="teamId" type="text" name="teamId"
				data-options="
                    valueField:'id',
                    url:'/ams/user/team/list.do?userId=',
                    textField:'teamName',
                    panelHeight:'auto',
                    loadFilter:function(data){
						return data.rows;
					}"></input>
			
			
	<label>年:</label>
	<input type="number" name="year" id="year"/>
	<label>月:</label>
	<input type="number" name="month" id="month"/>
	
	<button onclick="search(); return false;">搜索</button><button onclick="return checkExport();">导出</button>
	</form>
	
</div>

<table id="attList" class="easyui-datagrid" data-options="checkOnSelect:false, remoteFilter:true, fitColumns: true" url="/ams/attendance/list.do" iconCls="icon-save"
	sortOrder="asc" pagination="true" singleSelect="true">
	<thead>
		<tr>
			<th align="center" field="userName" width="100" sortable="false" resizable="true">员工</th>		
			<th align="center" field="attendanceDate" width="100" sortable="false" resizable="true">考勤日期</th>
			<th align="center" field="operator" width="100" sortable="false" resizable="true">考勤人</th>
			<th align="center" field="teamName" width="100" sortable="false" resizable="true">施工队</th>
			<th align="center" field="departmentName" width="100" sortable="false" resizable="true">部门</th>
			<th align="center" field="projectName" width="100" sortable="false" resizable="true">项目</th>
			<th align="center" field="attendanceDayType" width="100" sortable="false" formatter="formatterAttendanceDayType" resizable="true">上午，下午</th>
			<th align="center" field="attendanceType" width="180" sortable="false" formatter="formatterAttendanceType" resizable="true">考勤类型</th>
			<th align="center" field="createdOn" width="150" sortable="false" resizable="true">提交日期</th>
		</tr>
	</thead>
</table>