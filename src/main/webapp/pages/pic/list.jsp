
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<button onclick="loadRemotePage('pic/add');">新增</button>

<table id=noticeList class="easyui-datagrid" data-options="checkOnSelect:false, remoteFilter:true, fitColumns: true" url="/ams/user/pic/list.do" iconCls="icon-save"
	sortOrder="asc" pagination="true" singleSelect="true">
	<thead>
		<tr>
			<th align="center" field="picUrl" width="200" sortable="false" resizable="true">团队描述</th>
		</tr>
	</thead>
</table>