
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<script type="text/javascript">
	$(document).ready(function() {
		initFormSubmit("add-salary", "/ams/user/salary/add.do", "添加工资", function(){
			alert("添加成功");
			loadRemotePage("project/list");
		});
	});
</script>

<div style="padding: 10px 60px 20px 60px">
	<form novalidate id="add-salary" method="post">
		<div class="form-container" >
			<input class="easyui-validatebox textbox" type="hidden" name="userId" value="05c07bcc-833e-4b22-a8be-3c3a63609ac8" data-options="required:true"></input>
			<div>
				<span class="r-edit-label">月份:</span> <input class="easyui-validatebox textbox input-title" type="text" name="month" ></input>
			</div>
			<div>
				<span class="r-edit-label">年份:</span> <input class="easyui-validatebox textbox input-title" type="text" name="year" ></input>
			</div>
			<div>
				<span class="r-edit-label">总工资:</span>
				<td><input class="easyui-validatebox textbox" type="text" name="totalSalary" ></input></td>
			</div>

			<div>
				<span class="r-edit-label">扣除工资:</span>
				<td><input class="easyui-validatebox textbox" type="text" name="deductedSalary" ></input></td>
			</div>

			<div>
				<span class="r-edit-label">剩余工资:</span>
				<td><input class="easyui-validatebox textbox" type="text" name="remainingSalaray" ></input></td>
			</div>
			<div style="margin-left: 100px;">
				<input type="submit" value="提交"></a>
			</div>
		</div>
	</form>
</div>