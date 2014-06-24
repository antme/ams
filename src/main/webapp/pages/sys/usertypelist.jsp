
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<br>
<script>
  function deleteUserType(id){	  
	  deleteData("userTypeList", id, "/ams/sys/usertype/delete.do");	  
  }
</script>
<button onclick="loadRemotePage('sys/addusertype&a=5');">新增</button>
<table id="userTypeList" class="easyui-datagrid" data-options="checkOnSelect:false, remoteFilter:true, fitColumns: true" url="/ams/sys/usertype/list.do" iconCls="icon-save"
	sortOrder="asc" pagination="true" singleSelect="true">
	<thead>
		<tr>
			<th align="center" field="typeName" width="60" sortable="false" resizable="true">类型</th>
			<th align="center" field="createdOn" width="80" sortable="false" resizable="true">创建日期</th>
			<th align="center" field="updatedOn" width="80" sortable="false" resizable="true">更新日期</th>
			<th align="center" field="userName" width="50" sortable="false" resizable="true">创建人</th>
			<th align="center" field="typeDescription" width="250" sortable="false" resizable="true">描述</th>
			<th align="center" data-options="field:'id',formatter:formatterUserTypeOperation" width="80">操作</th>
		</tr>
	</thead>
</table>