
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<div>
	<label>关键字:</label>
	<input type="text" name="keyword" id="keyword"/> 
	

				<span class="r-edit-label">项目:</span> <input class="easyui-combobox textbox" type="text" name="projectId" data-options="url:'/ams/project/list.do?userId=',
                    method:'get',
                    valueField:'id',
                    required:true,
                    textField:'projectName',
                    panelHeight:'auto',
                    loadFilter:function(data){
						return data.rows;
					}"></input>
			
				<span class="r-edit-label">施工队:</span> <input class="easyui-combobox textbox"  id ="teamId" type="text" name="teamId"
				data-options="
                    valueField:'id',
                    url:'/ams/user/team/list.do?userId=',
                    required:true,
                    textField:'teamName',
                    panelHeight:'auto',
                    loadFilter:function(data){
						return data.rows;
					}"></input>
			
			
	
	<button onclick="search();">搜索</button><button>导出</button>
</div>

<table id=noticeList class="easyui-datagrid" data-options="checkOnSelect:false, remoteFilter:true, fitColumns: true" url="/ams/attendance/list.do" iconCls="icon-save"
	sortOrder="asc" pagination="true" singleSelect="true">
	<thead>
		<tr>
			<th align="center" field="attendanceDate" width="200" sortable="false" resizable="true">考勤日期</th>
			<th align="center" field="operator" width="200" sortable="false" resizable="true">考勤人</th>
			<th align="center" field="userName" width="200" sortable="false" resizable="true">员工</th>
			<th align="center" field="teamName" width="200" sortable="false" resizable="true">施工队</th>
			<th align="center" field="departmentName" width="200" sortable="false" resizable="true">部门</th>
			<th align="center" field="projectName" width="200" sortable="false" resizable="true">项目</th>
			<th align="center" field="attendanceDayType" width="200" sortable="false" formatter="formatterAttendanceDayType" resizable="true">上午，下午</th>
			<th align="center" field="attendanceType" width="200" sortable="false" formatter="formatterAttendanceType" resizable="true">考勤类型</th>
			<th align="center" field="createdOn" width="200" sortable="false" resizable="true">提交日期</th>
		</tr>
	</thead>
</table>