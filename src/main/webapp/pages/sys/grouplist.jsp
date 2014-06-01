<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<button onclick="loadRemotePage('sys/addgroup&a=5');">新增角色</button>

<script>
	function deleteGroup(id){
		var data = {id: id};
		
		postAjaxRequest("/ams/sys/group/delete.do", data, function(data){
			alert("删除成功");
			$("#groupList").datagrid('reload');
		});
	}
</script>
<table id="groupList" class="easyui-datagrid" data-options="checkOnSelect:false, remoteFilter:true, fitColumns: true" url="/ams/sys/group/list.do" iconCls="icon-save" sortOrder="asc"
	pagination="true" singleSelect="true">
	<thead>
		<tr>
			<th align="center" field="groupName" width="150" sortable="false" resizable="true">角色名称</th>
			<th align="center" field="description" width="450" sortable="false" resizable="true">描述</th>

			<th align="center" data-options="field:'id',formatter:formatterGroupOperation" width="120">操作</th>

		</tr>
	</thead>
</table>