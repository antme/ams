<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<script type="text/javascript">
	$(document).ready(function() {
		initFormSubmit("add-user", "/ams/user/changepwd.do", "添加用户", function(){
			alert("修改成功");
		});
	
	});
</script>
<div style="padding: 10px 60px 20px 60px">
	<form id="add-user" method="post" novalidate>
		<div class="form-container">

			<div>
				<span class="r-edit-label">密码：</span> <input name="userPassword" id="userpassword" required missmessage="请输入新密码" autocomplete="off" onfocus="this.type='password'" class="r-textbox at easyui-validatebox"
					type="password" validType="username" /> <span class="get_span"></span>
			</div>
			<div>
				<span class="r-edit-label">确认密码：</span> <input name="userpasswordConfirm" autocomplete="off" required  missmessage="请输入新密码确认"  onfocus="this.type='password'" class="r-textbox at easyui-validatebox"
					type="password" validType="pwdEquals['#userpassword']" /> <span class="get_span"></span>
			</div>
			
			<div style="margin-left: 100px;">
				<input type="submit" value="提交"></a>
			</div>
		</div>
	</form>