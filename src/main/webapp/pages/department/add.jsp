
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<% String departmentId = request.getParameter("id"); %>

<script type="text/javascript">
	var departmentId = "<%=departmentId%>";

	$(document).ready(function() {
		initFormSubmit("add-department", "/ams/user/department/add.do", "添加部门", function(){
			alert("提交成功");
			loadRemotePage("department/list&a=5");
		});
		
		if(departmentId!="null"){
			postAjaxRequest("/ams/user/department/load.do", {id:departmentId}, function(data){
				$("#add-department").form('load',data.data);
				
			});
		}
		
	});
</script>
<div style="padding: 10px 60px 20px 60px">
	<form novalidate id="add-department" method="post">
		<div class="form-container" >
			<input class="" type="hidden" name="id" />
			<div>
				<span class="r-edit-label">部门名称:</span> <input class="easyui-validatebox textbox" type="text" name="departmentName" data-options="required:true"></input>
			</div>
			<div>
			<span class="r-edit-label">部门主管:</span> <input class="easyui-combobox"  name="departmentManagerId" 
					data-options="url:'/ams/user/list.do?userId=',
                    method:'get',
                    valueField:'id',
                    required:true,
                    textField:'userName',
                    panelHeight:'auto',
                    loadFilter:function(data){
						return data.rows;
					}"></input>
			</div>
			
			<div>
				<span class="r-edit-label">显示顺序:</span> <input class="easyui-validatebox textbox input-title" type="number" name="displayOrder" value="0" ></input>
				<span>数值高的将显示在后面</span>
			</div>
			
			<div>
				<span class="r-edit-label">描述:</span>
				<textarea class="easyui-validatebox textarea" name="departmentDescription" ></textarea>
			</div>
			<div style="margin-left: 100px;">
				<input type="submit" value="提交"></a>
			</div>
		</div>
	</form>