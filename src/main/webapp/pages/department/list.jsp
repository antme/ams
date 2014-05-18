
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<button onclick="loadRemotePage('department/add');">新增</button>
<table id=noticeList class="easyui-datagrid" data-options="checkOnSelect:false, remoteFilter:true, fitColumns: true" url="/ams/user/department/list.do" iconCls="icon-save"
	sortOrder="asc" pagination="true" singleSelect="true">
	<thead>
		<tr>
			<th align="center" field="departmentName" width="200" sortable="false" resizable="true">部门名称</th>
			<th align="center" field="userName" width="200" sortable="false" resizable="true">部门主管</th>
			<th align="center" field="departmentDescription" width="200" sortable="false" resizable="true">部门描述</th>
			<th align="center" data-options="field:'id',formatter:formatterDepartmentOperation"  width="120">操作</th>

		</tr>
	</thead>
</table>