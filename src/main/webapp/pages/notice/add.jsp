
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<script type="text/javascript">
	$(document).ready(function() {
		initFormSubmit("add-notice", "/ams/notice/add.do", "添加公告", function(){
			$.messager.defaults = {
	        	ok : "继续加添",
	        	cancel : "去管理"
	        };
			
			$.messager.confirm('添加成功', '继续上传或跳转到公告管理列表页?', function(r){
                if (!r){
                    loadRemotePage('notice/list');
                }else{
                	
                }
        	});
		});
	});
</script>

<div style="padding: 10px 60px 20px 60px">
	<form id="add-notice" method="post">
		<div class="form-container" style="width: 500px;">
			<div>
				<span>标题:</span> <input class="easyui-validatebox textbox input-title" type="text" name="title" data-options="required:true"></input>
			</div>
			<div>
				<span>发布人:</span>
				<td><input class="easyui-validatebox textbox" type="text" name="publisher" data-options="required:true"></input></td>
			</div>

			<div>
				<span>附件:</span> <input class="easyui-validatebox textbox" type="text" name="attachFileUrl" data-options="required:true"></input>
			</div>

			<div>
				<span>公告内容:</span>
				<textarea class="easyui-validatebox textbox" name="content" data-options="required:true"></textarea>
			</div>
			<div style="margin-left: 100px;">
				<input type="submit" value="提交"></a>
			</div>
		</div>
	</form>