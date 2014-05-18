
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<script type="text/javascript">
	$(document).ready(function() {
		initFormSubmit("add-report", "/ams/project/dailyreport/app/add.do", "添加图片", function(){
			alert("添加成功");
			loadRemotePage("pic/list");
		});
	});
</script>

<div style="padding: 10px 60px 20px 60px">
	<form id="add-report" method="post" enctype="multipart/form-data">
		<div class="form-container" style="width: 500px;">
		
			<input class="easyui-validatebox textbox" type="hidden" name="imagesCount" value="2" data-options="required:true"></input>
			<input class="easyui-validatebox textbox" type="hidden" name="userId" value="05c07bcc-833e-4b22-a8be-3c3a63609ac8" data-options="required:true"></input>
			<input class="easyui-validatebox textbox" type="hidden" name="projectId" value="b8a353e0-b178-4e2f-8abf-7fb2ad60f9ab" data-options="required:true"></input>
			<div>
				<span class="label">材料纪录:</span> <input class="easyui-validatebox textbox" type="text" name="materialRecord" data-options="required:true"></input>
			</div>
			
			<div>
				<span class="label">工作纪录:</span> <input class="easyui-validatebox textbox" type="text" name="workingRecord" data-options="required:true"></input>
			</div>
			<div>
				<span class="label">天气:</span> <input class="easyui-validatebox textbox" type="text" name="weather" data-options="required:true"></input>
			</div> 
			<div>
				<span class="label">总结:</span> <input class="easyui-validatebox textbox" type="text" name="summary" data-options="required:true"></input>
			</div>
			<div>
				<span class="label">明日计划:</span> <input class="easyui-validatebox textbox" type="text" name="plan" data-options="required:true"></input>
			</div>
			<div>
				<span class="label">图片0:</span> <input type="file" class="easyui-validatebox textbox"  name="picData0" data-options="required:true"></input>
			</div>
			<div>
				<span class="label">图片1:</span> <input type="file" class="easyui-validatebox textbox"  name="picData1" data-options="required:true"></input>
			</div>
			
			<div style="margin-left: 100px;">
				<input type="submit" value="提交"></a>
			</div>
		</div>
	</form>