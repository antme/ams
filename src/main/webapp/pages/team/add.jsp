
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<script type="text/javascript">
	$(document).ready(function() {
		initFormSubmit("add-department", "/ams/user/team/add.do", "添加团队", function(){
			alert("添加成功");
			loadRemotePage("team/list");
		});
	});
</script>

<div style="padding: 10px 60px 20px 60px">
	<form id="add-department" method="post">
		<div class="form-container" style="width: 500px;">
			<div>
				<span>团队名称:</span> <input class="easyui-validatebox textbox" type="text" name="teamName" data-options="required:true"></input>
			</div>
			<div>
				<span>所属部门:</span> <input class="easyui-validatebox textbox" type="text" name="departmentId" data-options="required:true"></input>
			</div>
			<div>
				<span>描述:</span>
				<textarea class="easyui-validatebox textbox" name="teamDescription" ></textarea>
			</div>
			<div style="margin-left: 100px;">
				<input type="submit" value="提交"></a>
			</div>
		</div>
	</form>