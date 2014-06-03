
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<table id="logList" class="easyui-datagrid" data-options="checkOnSelect:false, remoteFilter:true, fitColumns: true" url="/ams/sys/log/list.do" iconCls="icon-save" sortOrder="asc"
	pagination="true" singleSelect="true">
	<thead>
		<tr>
			<th align="center" field="userName" width="200" sortable="false" resizable="true">操作人</th>
			<th align="center" field="createdOn" width="200" sortable="false" resizable="true">时间</th>
			<th align="center" field="message" width="100" sortable="false" resizable="true">事件</th>
			<th align="center" data-options="field:'id',formatter:formatterNoticeOperation"  width="120">详情</th>
		</tr>
	</thead>
</table>