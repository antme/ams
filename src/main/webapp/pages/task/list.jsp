
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<br>
<script type="text/javascript">
	$(document).ready(function() {
		initFormSubmit("task_import", "/ams/sys/task/import.do", "任务导入", function(){
			alert("导入成功");
			loadRemotePage("task/list&a=2");
		});
	});
</script>
<form id="task_import" action="/ams/sys/task/import.do" method="post" enctype="multipart/form-data">
	<span>任务文件上传：</span><input type="file" name="taskFile"/>
	<br>
	<input type="submit" value="上传"/>
</form>
<p></p>
<table id=noticeList class="easyui-datagrid" data-options="checkOnSelect:false, remoteFilter:true, fitColumns: true" url="/ams/project/task/list.do" iconCls="icon-save"
	sortOrder="asc" pagination="true" singleSelect="true">
	<thead>
		<tr>
			<th align="center" field="taskName" width="100" sortable="false" resizable="true">任务名字</th>
			<th align="center" field="projectName" width="100" sortable="false" resizable="true">项目</th>
			<th align="center" field="teamName" width="100" sortable="false" resizable="true">施工队</th>
			<th align="center" field="userName" width="100" sortable="false" resizable="true">施工员</th>
			<th align="center" field="taskContactPhone" width="100" sortable="false" resizable="true">联系电话</th>
			<th align="center" field="amount" width="100" sortable="false" resizable="true">施工面积</th>
			<th align="center" field="unit" width="100" sortable="false" resizable="true">单位</th>
			<th align="center" field="price" width="100" sortable="false" resizable="true">绩效单价/元</th>
			<th align="center" field="projectStartDate" width="100" sortable="false" resizable="true">开工日期</th>
			<th align="center" field="projectEndDate" width="100" sortable="false" resizable="true">竣工日期</th>
			<th align="center" field="taskPeriod" width="100" sortable="false" resizable="true">工期</th>
		</tr>
	</thead>
</table>