
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<br>
<button onclick="loadRemotePage('notice/add');">新增</button>
<script type="text/javascript">
	$(document).ready(function() {
		initFormSubmit("task_import", "/ams/sys/task/import.do", "工资导入", function(){
			alert("导入成功");
			loadRemotePage("task/list&a=2");
		});
	});
</script>
<form id="task_import" action="/ams/sys/task/import.do" method="post" enctype="multipart/form-data">
	<input type="file" name="taskFile"/>
	<input type="submit" value="上传"/>
</form>
<table id=noticeList class="easyui-datagrid" data-options="checkOnSelect:false, remoteFilter:true, fitColumns: true" url="/ams/project/task/list.do" iconCls="icon-save"
	sortOrder="asc" pagination="true" singleSelect="true">
	<thead>
		<tr>
			<th align="center" field="taskName" width="100" sortable="false" resizable="true">任务名字</th>
		</tr>
	</thead>
</table>