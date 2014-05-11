
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<script type="text/javascript">
	$(document).ready(function() {
		initFormSubmit("add-department", "/ams/user/department/add.do", "添加部门", function(){
			alert("添加成功");
			loadRemotePage("department/list");
		});
	});
</script>

<div style="padding: 10px 60px 20px 60px">
	<form id="add-department" method="post">
		<div class="form-container" style="width: 500px;">
			<div>
				<span>部门名称:</span> <input class="easyui-validatebox textbox" type="text" name="departmentName" data-options="required:true"></input>
			</div>

			<div>
				<span>描述:</span>
				<textarea class="easyui-validatebox textbox" name="departmentDescription" ></textarea>
			</div>
			<div style="margin-left: 100px;">
				<input type="submit" value="提交"></a>
			</div>
		</div>
	</form>