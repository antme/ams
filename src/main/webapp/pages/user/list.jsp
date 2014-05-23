<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<button onclick="loadRemotePage('user/add&a=4');">新增</button>
<table id=userList class="easyui-datagrid" data-options="checkOnSelect:false, remoteFilter:true, fitColumns: false" url="/ams/user/list.do" iconCls="icon-save"
	sortOrder="asc" pagination="true" singleSelect="true">
	<thead>
		<tr>
			<th align="center" field="userName" width="100" sortable="false" resizable="true">用户名</th>
			<th align="center" field="userCode" width="100" sortable="false" resizable="true">员工编号</th>
			<th align="center" field="typeName" width="80" sortable="false" resizable="true">员工类型</th>
			<th align="center" field="levelName" width="80" sortable="false" resizable="true">员工级别</th>
			<th align="center" field="status" width="80" sortable="false" resizable="true">手机登录</th>
			<th align="center" field="bstatus" width="80" sortable="false" resizable="true">后台登录</th>
			<th align="center" field="mobileNumber" width="100" sortable="false" resizable="true">手机号</th>
			<th align="center" field="address" width="120" sortable="false" resizable="true">住址</th>
			<th align="center" field="createdOn" width="140" sortable="false" resizable="true">创建时期</th>
			<th align="center" data-options="field:'id',formatter:formatterUserOperation"  width="120">操作</th>

		</tr>
	</thead>
</table>