
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<script type="text/javascript">
	$(document).ready(function() {
		initFormSubmit("add-customer", "/ams/user/customer/add.do", "添加客户", function(){
			alert("添加成功");
			loadRemotePage("customer/list&a=3");
		});
	});
</script>

<div style="padding: 10px 60px 20px 60px">
	<form novalidate novalidate id="add-customer" method="post">
		<div class="form-container" >
			<input class="" type="hidden" name="id" />
			<div>
				<span class="r-edit-label">客户名称:</span> <input class="easyui-validatebox textbox" type="text" name="name" data-options="required:true"></input>
			</div>
			<div>
				<span class="r-edit-label">联系人:</span> <input class="easyui-validatebox textbox" type="text" name="contactPerson" data-options="required:true"></input>
			</div>
			<div>
				<span class="r-edit-label"> 职位:</span> <input class="easyui-validatebox textbox" type="text" name="position" data-options="required:true"></input>
			</div>
			<div>
				<span class="r-edit-label">手机:</span> <input class="easyui-validatebox textbox" type="text" name="contactMobileNumber" data-options="required:true"></input>
			</div>
			<div>
				<span class="r-edit-label">联系地址:</span> <input class="easyui-validatebox textbox-long" type="text" name="address" data-options="required:true"></input>
			</div>
			<div>
				<span class="r-edit-label">备注:</span>
				<textarea class="easyui-validatebox textarea" name="remark" ></textarea>
			</div>
			<div style="margin-left: 100px;">
				<input type="submit" value="提交"></a>
			</div>
		</div>
	</form>