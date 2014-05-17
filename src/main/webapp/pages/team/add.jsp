
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<script type="text/javascript">
	$(document).ready(function() {
		initFormSubmit("add-department", "/ams/user/team/add.do", "添加团队", function(){
			alert("添加成功");
			loadRemotePage("team/list");
		});
	});
</script>

<div style="padding: 10px 60px 20px 60px">
	<form id="add-department" method="post">
		<div class="form-container" style="width: 500px;">
			<div>
				<span>团队名称:</span> <input class="easyui-validatebox textbox" type="text" name="teamName" data-options="required:true"></input>
			</div>
			<div>
				<p></p>
				<input/><span>小时:</span><input/>分钟：
				
				
				
				<p></p>
			</div>
			<div>
				<span>所属部门:</span> <input class="easyui-combobox"  name="departmentId" 
					data-options="url:'/ams/user/department/list.do',
                    method:'get',
                    valueField:'id',
                    required:true,
                    textField:'departmentName',
                    panelHeight:'auto',
                    loadFilter:function(data){
						return data.rows;
					}"></input>
			</div>
			<div>
				<span>团队负责人:</span> <input class="easyui-combobox"  name="teamLeaderId" 
					data-options="url:'/ams/user/app/list.do?userId=',
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
			<span>所属项目:</span> <input class="easyui-combobox"  name="projectId" 
					data-options="url:'/ams/project/list.do?userId=',
                    method:'get',
                    valueField:'id',
                    required:true,
                    textField:'projectName',
                    panelHeight:'auto',
                    loadFilter:function(data){
						return data.rows;
					}"></input>
			</div>
			<div>
				<span>团队成员：</span> <select id="teamMemberIds" class="easyui-combogrid" name="teamMemberIds[]" style="width:250px;"
								        data-options="
								            panelWidth:450,
								            idField:'id',
								            multiple: true,
								            textField:'userName',
								            fitColumns: true,
								            url:'/ams/user/app/list.do?userId=',
								            columns:[[
								            	{field:'ck',checkbox:true},
								                {field:'userName',title:'用户名',width:60},
								                {field:'id',title:'Id',width:100, hidden:true}
								            ]]
								        "></select>
			</div>
			<div>
				<span>描述:</span>
				<textarea class="easyui-validatebox textbox" name="teamDescription" ></textarea>
			</div>
			<div style="margin-left: 100px;">
				<input type="submit" value="提交"></a>
			</div>
		</div>
	</form>