
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<% String id = request.getParameter("id"); %>

<script type="text/javascript">
	var id = "<%=id%>";

	$(document).ready(function() {
		initFormSubmit("add-team", "/ams/user/team/add.do", "添加团队", function(){
			alert("添加成功");
			loadRemotePage("team/list&a=4");
		});
		
		if(id!="null"){
			postAjaxRequest("/ams/user/team/get.do", {id: id}, function(data){
				var team = data.data;
				$("#add-team").form('load',team);
				$('#teamMemberIds').combogrid('setValues', team.teamMemberIds);
			});
		}
	});
</script>

<div style="padding: 10px 60px 20px 60px">
	<form novalidate id="add-team" method="post">
		<div class="form-container" >
			<input class="" type="hidden" name="id" />
			<div>
				<span class="r-edit-label">团队名称:</span> <input class="easyui-validatebox textbox" type="text" name="teamName" data-options="required:true"></input>
			</div>
		
	
			<div>
				<span class="r-edit-label">施工队长:</span> <input class="easyui-combobox"  name="teamLeaderId" 
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
			<span class="r-edit-label">所属项目:</span> <input class="easyui-combobox"  name="projectId" 
					data-options="url:'/ams/project/list.do?userId=',
                    method:'get',
                    valueField:'id',
                    required:true,
                    textField:'projectName',
                    panelHeight:'auto',
                    onSelect: function(rec){
			            $('#departmentId').val(rec.departmentId);
			            $('#departmentName').val(rec.departmentName);
			        },
                    loadFilter:function(data){
						return data.rows;
					}"></input>
			</div>
			
			<div>
				<span class="r-edit-label">所属部门:</span> <input type="hidden" class=""  id="departmentId" name="departmentId"></input>
				<input class=""  disabled id="departmentName" name="departmentName"></input>
			</div>
			<div>
				<span class="r-edit-label">团队成员：</span> <select id="teamMemberIds" class="easyui-combogrid" name="teamMemberIds[]" style="width:250px;"
								        data-options="
								            panelWidth:450,
								            idField:'id',
								            multiple: true,
								            textField:'userName',
								            fitColumns: true,
								            url:'/ams/user/list.do?userId=',
								            columns:[[
								            	{field:'ck',checkbox:true},
								                {field:'userName',title:'用户名',width:60},
								                {field:'id',title:'Id',width:100, hidden:true}
								            ]]
								        "></select>
			</div>
			
			<div style="margin-left: 100px;">
				<input type="submit" value="提交"></a>
			</div>
		</div>
	</form>