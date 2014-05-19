
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<button onclick="loadRemotePage('customer/add&a=3');">新增</button>
<table id=noticeList class="easyui-datagrid" data-options="checkOnSelect:false, remoteFilter:true, fitColumns: true" url="/ams/project/customer/list.do" iconCls="icon-save"
	sortOrder="asc" pagination="true" singleSelect="true">
	<thead>
		<tr>
			<th align="center" field="name" width="200" sortable="false" resizable="true">客户名称</th>
			<th align="center" field="address" width="200" sortable="false" resizable="true">地址</th>
			<th align="center" field="contactPerson" width="200" sortable="false" resizable="true">客户联系人</th>
			<th align="center" field="position" width="200" sortable="false" resizable="true">联系人职位</th>
			<th align="center" field="contactMobileNumber" width="200" sortable="false" resizable="true">手机</th>
			<th align="center" field="remark" width="200" sortable="false" resizable="true">备注</th>
		</tr>
	</thead>
</table>