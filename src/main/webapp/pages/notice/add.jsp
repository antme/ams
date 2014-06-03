
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<% String id = request.getParameter("id"); %>
<script type="text/javascript">
	var id = "<%=id%>";
	$(document).ready(function() {
		
		initFormSubmit("add-notice", "/ams/notice/add.do", "添加公告", function(){
			   alert("提交成功");
               loadRemotePage('notice/list');
               
        });
		
		if(id!="null"){
			postAjaxRequest("/ams/notice/get.do", {id: id}, function(data){
				var notice = data.data;
				$("#add-notice").form('load',notice);
				if(notice.attachFileUrl){
					$("#att_url").attr("href", notice.attachFileUrl);
					$("#attachFileDiv").show();
					
				}
			});
		}
	});

</script>

<div style="padding: 10px 60px 20px 60px">
	<form novalidate id="add-notice" method="post" enctype="multipart/form-data">
		<div class="form-container" >
			<input class="" type="hidden" name="id" />
			<div>
				<span class="r-edit-label">标题:</span> <input class="easyui-validatebox textbox input-title" type="text" name="title" data-options="required:true"></input>
			</div>
			
			<div>
				<span class="r-edit-label">优先级:</span> <input class="easyui-validatebox textbox input-title" type="number" name="priority" value="0" ></input>
				<span>优先级越高的将显示在最顶上</span>
			</div>
			
			<div>
				<span class="r-edit-label">公告结束时间:</span> <input class="easyui-datebox textbox input-title" type="text" name="publishEndDate" ></input>
				<span>此时间过后公告将不显示在手机上</span>
			</div>
		
			<div id="attachFileDiv" style="display:none;">
				<span class="r-edit-label">原始附件地址:</span> <span><a id="att_url" style="margin-left:20px;">下载</a> 
				<input type="checkbox" name="deleteAttachFile" id="deleteAttachFile"/> <label for="deleteAttachFile">是否删除附件</label></span>
				<span style="margin-left:20px;">勾选此项将会删除已经上传的附件</span>
			</div>
			<div>
				<span class="r-edit-label">附件上传:</span> <input class="easyui-validatebox textbox" type="file" name="attachFileUpload" style="width:250px; border: 0px;"></input>
			</div>
			
			

			<div>
				<span class="r-edit-label">公告内容:</span>
				<textarea class="easyui-validatebox textarea" name="content"  data-options="required:true"></textarea>
			</div>
			<div style="margin-left: 100px;">
				<input type="submit" value="提交"></a>
			</div>
		</div>
	</form>
</div>