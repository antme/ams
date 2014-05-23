
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<br>
<button onclick="loadRemotePage('sys/addusertype&a=5');">新增</button>
<table id=noticeList class="easyui-datagrid" data-options="checkOnSelect:false, remoteFilter:true, fitColumns: true" url="/ams/sys/usertype/list.do" iconCls="icon-save"
	sortOrder="asc" pagination="true" singleSelect="true">
	<thead>
		<tr>
			<th align="center" field="typeName" width="100" sortable="false" resizable="true">类型</th>
			<th align="center" field="createdOn" width="100" sortable="false" resizable="true">创建日期</th>
			<th align="center" field="updatedOn" width="100" sortable="false" resizable="true">更新日期</th>
			<th align="center" field="creator" width="50" sortable="false" resizable="true">创建人</th>
			<th align="center" field="typeDescription" width="100" sortable="false" resizable="true">描述</th>
			<th align="center" data-options="field:'id',formatter:formatterUserTypeOperation" width="120">操作</th>
		</tr>
	</thead>
</table>