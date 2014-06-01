<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<% String id = request.getParameter("id"); %>

<script type="text/javascript">
	var id = "<%=id%>";

	$(document).ready(function() {
		initFormSubmit("addRoleGroupForm", "/ams/sys/group/add.do", "添加权限组", function(){
			alert("提交成功");
			loadRemotePage("user/grouplist&a=5");
		});
		
		
		if(id!="null"){
			postAjaxRequest("/ams/sys/group/get.do", {id:id}, function(data){
				
				var arr = data.data.permissions.split(",");
				$("#addRoleGroupForm").form('load',data.data);
				$('#permissionsel').combotree('setValues', arr);
				
			});
			
		}
		
	});
</script>

<form action="" id="addRoleGroupForm" method="post" novalidate>
	<div class="form-container">
		<input id="id" name="id" type="hidden" /> 
		<input  id="permissions" name="permissions" type="hidden" />
		<div>
			<span class="r-edit-label"><label for="groupName"> 权限组名字 </label>:</span> <input class="ui-widget-content ui-corner-all ui-input tabs_input_css easyui-validatebox"
				id="groupName" name="groupName" size="55" required missingMessage="请输入名称" />
		</div>
		<div>
			<span class="r-edit-label"><label for="permissionsel">权限 </label>:</span> 
			<select id="permissionsel" class="easyui-combotree" name="permissionitems[]"
				data-options="url:'/menu/roles.json',method:'post',valueField:'id', textField:'text',width:194,height:32" multiple style="width: 128px;"></select>

		</div>

		<div>
			<span class="r-edit-label"><label for="description"> 描述 </label>:</span>
			<textarea rows="15" cols="60" id="description" name="description"></textarea>
		</div>

		<div>
			<span><input type="submit" value="提交"></a></span>
		</div>
	</div>
</form>
