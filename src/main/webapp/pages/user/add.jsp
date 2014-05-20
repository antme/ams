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
	<form id="add-user" method="post" novalidate>
		<div class="form-container">
			<input class="" type="hidden" name="id" />
			<div>
				<span class="r-edit-label">用户名:</span> <input class="easyui-validatebox textbox" type="text" name="userName" data-options="required:true"></input>
			</div>
			<div>
				<span class="r-edit-label">员工编号:</span> <input class="easyui-validatebox textbox" type="text" name="userCode"></input>
			</div>
			<div>
				<span class="r-edit-label">员工类型:</span> <input class="easyui-validatebox textbox" type="text" name="userTypeId"></input>
			</div>
			<div>
				<span class="r-edit-label">员工级别:</span> <input class="easyui-validatebox textbox" type="text" name="userLevelId"></input>
			</div>
			<div>
				<span class="r-edit-label">手机登录状态:</span> <select class="easyui-combobox" name="status" style="width: 200px;">
					<option value="0">启用</option>
					<option value="1">禁用</option>
				</select>
			</div>
			<div>
				<span class="r-edit-label">后台登录状态:</span> <select class="easyui-combobox" name="bstatus" style="width: 200px;">
					<option value="0">启用</option>
					<option value="1">禁用</option>
				</select>
			</div>

			<div>
				<span class="r-edit-label">手机:</span> <input class="easyui-validatebox textbox" type="text" validType="mobile" name="mobileNumber"></input>
			</div>
			<div>
				<span class="r-edit-label">住址:</span> <input class="easyui-validatebox textbox-long" type="text" name="address"></input>
			</div>

			<div>
				<span class="r-edit-label">密码：</span> <input name="password" id="userpassword" autocomplete="off" onfocus="this.type='password'" class="r-textbox at easyui-validatebox"
					type="password" validType="username" required="true" missingMessage="请输入密码" /> <span class="get_span"><label class="g-label">*</label></span>
			</div>
			<div>
				<span class="r-edit-label">确认密码：</span> <input name="userpasswordConfirm" autocomplete="off" onfocus="this.type='password'" class="r-textbox at easyui-validatebox"
					type="password" required="true" missingMessage="请再次输入密码" validType="pwdEquals['#userpassword']" /> <span class="get_span"><label class="g-label">*</label></span>
			</div>
			<div>
				<span class="r-edit-label">备注:</span>
				<textarea class="easyui-validatebox textarea" name="departmentDescription"></textarea>
			</div>
			<div style="margin-left: 100px;">
				<input type="submit" value="提交"></a>
			</div>
		</div>
	</form>