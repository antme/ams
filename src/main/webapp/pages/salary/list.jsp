
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<br>
<button onclick="loadRemotePage('notice/add');">新增</button>
<script type="text/javascript">
	$(document).ready(function() {
		initFormSubmit("task_import", "/ams/sys/task/import.do", "任务导入", function(){
			alert("导入成功");
			loadRemotePage("salary/list&a=4");
		});
	});
</script>
<form id="task_import" action="/ams/sys/task/import.do" method="post" enctype="multipart/form-data">
	<input type="file" name="taskFile"/>
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
</script>
<div>
	<label>用户名:</label> <input type="text" name="userName" id="userName" /> 
	<label>年份:</label> <input type="number" name="years" id="years" /> 
	<label>月份:</label> <input type="number" name="month" id="month" />




	<button onclick="search();">搜索</button>
</div>
<p></p>

<table id="salaryList" class="easyui-datagrid" data-options="checkOnSelect:false, remoteFilter:true, fitColumns: true" url="/ams/user/salary/list.do" iconCls="icon-save"
	sortOrder="asc" pagination="true" singleSelect="true">
	<thead>
		<tr>
			<th align="center" field="userName" width="100" sortable="false" resizable="true">用户名</th>
			<th align="center" field="year" width="100" sortable="false" resizable="true">年份</th>
			<th align="center" field="month" width="50" sortable="false" resizable="true">月份</th>
			<th align="center" field="totalSalary" width="100" sortable="false" resizable="true">应发工资</th>
			<th align="center" field="deductedSalary" width="100" sortable="false" resizable="true">应扣工资</th>
			<th align="center" field="remainingSalaray" width="100" sortable="false" resizable="true">剩余工资</th>
		</tr>
	</thead>
</table>