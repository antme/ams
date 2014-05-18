
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<button onclick="loadRemotePage('team/add');">新增</button>

<table id=noticeList class="easyui-datagrid" height="600" data-options="checkOnSelect:false, remoteFilter:true, fitColumns: true" url="/ams/user/team/list.do" iconCls="icon-save"
	sortOrder="asc" pagination="true" singleSelect="true">
	<thead>
		<tr>
			<th align="center" field="teamName" width="200" sortable="false" resizable="true">团队名称</th>
			<th align="center" field="departmentName" width="200" sortable="false" resizable="true">所属部门</th>
			<th align="center" field="projectName" width="200" sortable="false" resizable="true">所属项目</th>
			<th align="center" field="userName" width="200" sortable="false" resizable="true">队长</th>
			<th align="center" field="teamDescription" width="200" sortable="false" resizable="true">团队描述</th>
			<th align="center" data-options="field:'id',formatter:formatterTeamOperation"  width="120">操作</th>
		</tr>
	</thead>
</table>