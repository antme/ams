<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<% String id = request.getParameter("id"); %>

<script type="text/javascript">
	var id = "<%=id%>";

	$(document).ready(function() {
		initFormSubmit("add-user", "/ams/user/add.do", "添加用户", function(){
			alert("添加成功");
			loadRemotePage("user/list&a=4");
		});
		
		if(id!="null"){
			postAjaxRequest("/ams/user/load.do", {id:id}, function(data){
				$("#add-user").form('load',data.data);
				
			});
		}
		
	});
</script>
<div style="padding: 10px 60px 20px 60px">
	<form id="add-user" method="post">
		<div class="form-container" style="width: 500px;">
			<input class="" type="hidden" name="id" />
			<div>
				<span>用户名:</span> <input class="easyui-validatebox textbox" type="text" name="userName" data-options="required:true"></input>
			</div>
			
			<div>
				<span>描述:</span>
				<textarea class="easyui-validatebox textbox" name="departmentDescription"></textarea>
			</div>
			<div style="margin-left: 100px;">
				<input type="submit" value="提交"></a>
			</div>
		</div>
	</form>