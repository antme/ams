
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<script type="text/javascript">
	$(document).ready(function() {
		initFormSubmit("task_import", "/ams/sys/task/import.do", "任务导入", function(){
			alert("导入成功");
			loadRemotePage("task/list&a=2");
		});
	});
	
</script>
<form id="task_import" action="/ams/sys/task/import.do" method="post" enctype="multipart/form-data" novalidate>
	<span>任务上传文件只支持excel文件，<a href="/template/施工任务单.xls" target="_blank">模板下载</a></span>
	<p></p>
	<hr>
	<span>任务文件上传：</span><input type="file" class="easyui-validatebox" missingmessage="请选择上传文件"  required  name="taskFile"/>
	<p></p>
	<span style="margin-left:75px;"></span> <input type="checkbox" name="overrideexists" id="overrideexists" value="true"/> <label for="overrideexists">是否覆盖现有任务</label> 
	<br>
	<input type="submit" value="上传"/>
</form>
