
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<% String id = request.getParameter("id"); %>
<script type="text/javascript">
	var id = "<%=id%>";
	$(document).ready(function() {
		
		initFormSubmit("add-usertype", "/ams/sys/usertype/add.do", "添加员工类型", function(){
			   alert("提交成功");
               loadRemotePage('sys/usertypelist&a=5');
               
        });
		
		if(id!="null"){
			postAjaxRequest("/ams/sys/usertype/get.do", {id: id}, function(data){
				var ut = data.data;
				$("#add-usertype").form('load',ut);
			});
		}
	});

</script>

<div style="padding: 10px 60px 20px 60px">
	<form novalidate id="add-usertype" method="post" enctype="multipart/form-data">
		<div class="form-container" >
			<input class="" type="hidden" name="id" />
			<div>
				<span class="r-edit-label">员工类型名:</span> <input class="easyui-validatebox textbox input-title"   missingMessage="请输入员工类型"  type="text" name="typeName" data-options="required:true"></input>
			</div>
			

			<div>
				<span class="r-edit-label">描述:</span>
				<textarea class="easyui-validatebox textarea" name="typeDescription" ></textarea>
			</div>
			<div style="margin-left: 100px;">
				<input type="submit" value="提交"></a>
			</div>
		</div>
	</form>
</div>