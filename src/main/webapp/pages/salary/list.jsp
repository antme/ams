
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<br>
<script type="text/javascript">
	$(document).ready(function() {
		initFormSubmit("task_import", "/ams/sys/salary/import.do", "任务导入", function(){
			alert("导入成功");
			loadRemotePage("salary/list&a=4");
		});
	});
</script>
<form id="task_import" action="/ams/sys/salary/import.do" method="post" enctype="multipart/form-data">
	<input type="file" name="salaryFile"/>
	<input type="submit" value="上传"/>
</form>
<p></p>
<hr>
<script>

	function search() {
		$('#salaryList').datagrid('load', {
			userName : $("#userName").val(),
			years : $("#years").val(),
			month : $("#month").val()
			
		});
	}
	
	function deleteSalary(){
		deleteMultipleData("salaryList", "/ams/user/salary/delete.do");
	}
</script>
<div>
	<label>用户名:</label> <input type="text" name="userName" id="userName" /> 
	<label>年份:</label> <input type="number" name="years" id="years" /> 
	<label>月份:</label> <input type="number" name="month" id="month" />


	<button onclick="search();">搜索</button>
</div>
<p></p>

<button onclick="deleteSalary();">删除</button>
<table id="salaryList" class="easyui-datagrid" data-options="selectOnCheck:true, checkOnSelect:true, remoteFilter:true, fitColumns: true" url="/ams/user/salary/list.do" iconCls="icon-save"
	sortOrder="asc" pagination="true">
	<thead>
		<tr>
			<th data-options="field:'ck',checkbox:true"></th>
			<th align="center" field="userName" width="100" sortable="false" resizable="true">用户名</th>
			<th align="center" field="year" width="100" sortable="false" resizable="true">年份</th>
			<th align="center" field="month" width="50" sortable="false" resizable="true">月份</th>
			<th align="center" field="totalSalary" width="100" sortable="false" resizable="true">应发工资</th>
			<th align="center" field="deductedSalary" width="100" sortable="false" resizable="true">应扣工资</th>
			<th align="center" field="remainingSalaray" width="100" sortable="false" resizable="true">剩余工资</th>
		</tr>
	</thead>
</table>