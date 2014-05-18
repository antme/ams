
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<button onclick="loadRemotePage('project/add');">新增</button>
<button onclick="loadRemotePage('salary/add');">新增工资</button>
<table id=projectList class="easyui-datagrid" data-options="checkOnSelect:false, remoteFilter:true, fitColumns: true" url="/ams/project/list.do" iconCls="icon-save"
	sortOrder="asc" pagination="true" singleSelect="true">
	<thead>
		<tr>
			<th align="center" field="projectName" width="100" sortable="false" resizable="true">项目名称</th>
			<th align="center" field="projectStartDate" width="100" sortable="false" resizable="true">项目开始日期</th>
			<th align="center" field="projectEndDate" width="100" sortable="false" resizable="true">项目结束日期</th>
		</tr>
	</thead>
</table>