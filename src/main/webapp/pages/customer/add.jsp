
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<script type="text/javascript">
	$(document).ready(function() {
		initFormSubmit("add-customer", "/ams/user/customer/add.do", "添加客户", function(){
			alert("添加成功");
			loadRemotePage("customer/list");
		});
	});
</script>

<div style="padding: 10px 60px 20px 60px">
	<form id="add-customer" method="post">
		<div class="form-container" style="width: 500px;">
			<div>
				<span>客户名称:</span> <input class="easyui-validatebox textbox" type="text" name="name" data-options="required:true"></input>
			</div>
			<div>
				<span>联系人:</span> <input class="easyui-validatebox textbox" type="text" name="contactPerson" data-options="required:true"></input>
			</div>
			<div>
				<span>职位:</span> <input class="easyui-validatebox textbox" type="text" name="position" data-options="required:true"></input>
			</div>
			<div>
				<span>手机:</span> <input class="easyui-validatebox textbox" type="text" name="contactMobileNumber" data-options="required:true"></input>
			</div>
			<div>
				<span>联系地址:</span> <input class="easyui-validatebox textbox" type="text" name="address" data-options="required:true"></input>
			</div>
			<div>
				<span>备注:</span>
				<textarea class="easyui-validatebox textbox" name="remark" ></textarea>
			</div>
			<div style="margin-left: 100px;">
				<input type="submit" value="提交"></a>
			</div>
		</div>
	</form>