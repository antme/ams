
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<button onclick="loadRemotePage('project/add&a=3');">新增项目</button>
<table id=projectList class="easyui-datagrid" data-options="checkOnSelect:false, remoteFilter:true, fitColumns: true" url="/ams/project/list.do" iconCls="icon-save"
	sortOrder="asc" pagination="true" singleSelect="true">
	<thead>
		<tr>
			<th align="center" field="projectName" width="100" sortable="false" resizable="true">项目名称</th>
			<th align="center" field="projectStartDate" width="100" sortable="false" resizable="true">项目开始日期</th>
			<th align="center" field="projectEndDate" width="100" sortable="false" resizable="true">项目结束日期</th>
			<th align="center" data-options="field:'id',formatter:formatterProjectOperation"  width="120">操作</th>
			
		</tr>
	</thead>
</table>