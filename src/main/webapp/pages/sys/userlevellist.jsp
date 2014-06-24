
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<br>
<script>
  function deleteUserLevel(id){	  
	  deleteData("userlevelliST", id, "/ams/sys/userlevel/delete.do");	  
  }
</script>

<button onclick="loadRemotePage('sys/adduserlevel&a=5');">新增</button>
<table id="userlevelliST" class="easyui-datagrid" data-options="checkOnSelect:false, remoteFilter:true, fitColumns: true" url="/ams/sys/userlevel/list.do" iconCls="icon-save"
	sortOrder="asc" pagination="true" singleSelect="true">
	<thead>
		<tr>
			<th align="center" field="typeName" width="80" sortable="false" resizable="true">员工类型</th>
			<th align="center" field="levelName" width="80" sortable="false" resizable="true">级别</th>
			<th align="center" field="createdOn" width="80" sortable="false" resizable="true">创建日期</th>
			<th align="center" field="userName" width="50" sortable="false" resizable="true">创建人</th>
			<th align="center" field="levelDescription" width="300" sortable="false" resizable="true">描述</th>
			<th align="center" data-options="field:'id',formatter:formatteruserlevelOperation" width="60">操作</th>
		</tr>
	</thead>
</table>