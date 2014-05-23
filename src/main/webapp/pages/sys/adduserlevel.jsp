
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<% String id = request.getParameter("id"); %>
<script type="text/javascript">
	var id = "<%=id%>";
	$(document).ready(function() {
		
		initFormSubmit("add-userlevel", "/ams/sys/userlevel/add.do", "添加员工级别", function(){
			   alert("添加成功");
               loadRemotePage('sys/userlevellist&a=5');
               
        });
		
		if(id!="null"){
			postAjaxRequest("/ams/sys/userlevel/get.do", {id: id}, function(data){
				var ut = data.data;
				$("#add-userlevel").form('load',ut);
			});
		}
	});

</script>

<div style="padding: 10px 60px 20px 60px">
	<form novalidate id="add-userlevel" method="post" enctype="multipart/form-data">
		<div class="form-container" >
			<input class="" type="hidden" name="id" />
			<div>
				<span class="r-edit-label">所属员工类型:</span> <input class="easyui-combobox textbox input-title" type="text" name="userTypeId"  required
				 data-options="url:'/ams/sys/usertype/list.do?userId=',
                    method:'get',
                    valueField:'id',
                    required:true,
                    textField:'typeName',
                    panelHeight:'auto',
                    loadFilter:function(data){
						return data.rows;
					}"></input>
			</div>
			
			<div>
				<span class="r-edit-label">员工级别:</span> <input class="easyui-validatebox textbox input-title" type="text" name="levelName" data-options="required:true"></input>
			</div>
			<div>
				<span class="r-edit-label">级别描述:</span>
				<textarea class="easyui-validatebox textarea" name="levelDescription" ></textarea>
			</div>
			<div style="margin-left: 100px;">
				<input type="submit" value="提交"></a>
			</div>
		</div>
	</form>
</div>