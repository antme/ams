<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<button onclick="loadRemotePage('user/add&a=4');">新增</button>
<table id=userList class="easyui-datagrid" data-options="checkOnSelect:false, remoteFilter:true, fitColumns: true" url="/ams/user/app/list.do" iconCls="icon-save"
	sortOrder="asc" pagination="true" singleSelect="true">
	<thead>
		<tr>
			<th align="center" field="userName" width="200" sortable="false" resizable="true">用户名</th>
			<th align="center" field="createdOn" width="200" sortable="false" resizable="true">创建时期</th>
			<th align="center" data-options="field:'id',formatter:formatterUserOperation"  width="120">操作</th>

		</tr>
	</thead>
</table>