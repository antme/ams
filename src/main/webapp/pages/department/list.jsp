
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script>
  function deleteDepartment(id){	  
	  deleteData("departmentList", id, "/ams/sys/department/delete.do");	  
  }
</script>

<button onclick="loadRemotePage('department/add&a=5');">新增</button>
<table id="departmentList" class="easyui-datagrid" data-options="checkOnSelect:false, remoteFilter:true, fitColumns: true" url="/ams/user/department/list.do" iconCls="icon-save"
	sortOrder="asc" pagination="true" singleSelect="true">
	<thead>
		<tr>
			<th align="center" field="departmentName" width="100" sortable="false" resizable="true">部门名称</th>
			<th align="center" field="userName" width="100" sortable="false" resizable="true">部门主管</th>
			<th align="center" field="departmentDescription" width="300" sortable="false" resizable="true">部门描述</th>
			<th align="center" data-options="field:'id',formatter:formatterDepartmentOperation"  width="120">操作</th>

		</tr>
	</thead>
</table>