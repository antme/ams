
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<% String id = request.getParameter("id"); %>
<script type="text/javascript">

	var id = "<%=id%>";	
	$(document).ready(function() {
		initFormSubmit("add-project", "/ams/project/add.do", "添加项目", function(){
			alert("提交成功");
			loadRemotePage("project/list&a=3");
		});
		
		if(id!="null"){
			postAjaxRequest("/ams/project/get.do", {id: id}, function(data){
				var project = data.data;
				$("#add-project").form('load',project);
				
				$('#projectMemberIds').combogrid('setValues', project.projectMemberIds);
				
				onHidePanel();
				
			});
		}
		
		
	});
	
	function onHidePanel(){		
		$("#count").html("已选择项目成员:" + $("#projectMemberIds").combogrid('getValues').length + "人");
	}
	
	
	
	 
</script>

<div style="padding: 10px 60px 20px 60px">
	<form novalidate id="add-project" method="post">
		<div class="form-container" >
			<input class="" type="hidden" name="id" />
			<div>
				<span class="r-edit-label">项目名称:</span> <input class="easyui-validatebox textbox input-title" required type="text" name="projectName" ></input>
			</div>
			<div>
				<span class="r-edit-label">所属部门:</span> <input class="easyui-combobox "  name="departmentId" 
					data-options="url:'/ams/user/department/list.do',
                    method:'get',
                    valueField:'id',
                    required:true,
                    textField:'departmentName',
                    panelHeight:'auto',
                    loadFilter:function(data){
						return data.rows;
					}"></input> <span>部门改变的时候，项目下的团队的部门也将一起改变</span>
			</div>
			<div>
				<span class="r-edit-label">考勤负责人:</span> <input class="easyui-combobox"  name="projectAttendanceManagerId" 
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
				<span class="r-edit-label">项目负责人:</span> <input class="easyui-combobox"  name="projectManagerId" 
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
				<span class="r-edit-label">项目状态</span> <input class="easyui-validatebox textbox input-title" type="text" name="projectStatus" style="width: 200px;"/>			
			</div>

			<div>
				<span class="r-edit-label">项目开始时间:</span>
				<td><input class="easyui-datebox easyui-validatebox textbox" type="text" name="projectStartDate" ></input></td>
			</div>

			<div>
				<span class="r-edit-label">项目结束时间:</span>
				<td><input class="easyui-datebox easyui-validatebox textbox" type="text" name="projectEndDate"></input></td>
			</div>
			<div>
				<span class="r-edit-label">客户:</span> <input class="easyui-combobox"  name="customerId"  style="width:250px;"
					data-options="url:'/ams/project/customer/list.do?userId=',
                    method:'get',
                    valueField:'id',
                    required:true,
                    textField:'name',
                    panelHeight:'auto',
                    loadFilter:function(data){
						return data.rows;
					}"></input>
			</div>
			
			<div>
				<span class="r-edit-label">项目成员：</span> <select id="projectMemberIds" class="easyui-combogrid" name="projectMemberIds[]" style="width:500px; height:40px;"
								        data-options="
								            panelWidth:450,
								            idField:'id',
								            multiple: true,
								            textField:'userName',
								            fitColumns: true,
								            onHidePanel:onHidePanel,
								            url:'/ams/user/list.do?userId=',
								            columns:[[
								            	{field:'ck',checkbox:true},
								                {field:'userName',title:'用户名',width:60},
								                {field:'levelCode',title:'员工编号',width:60},
								                {field:'typeName',title:'员工类型',width:60},
								                {field:'levelName',title:'员工级别',width:60},
								                {field:'id',title:'Id',width:100, hidden:true}
								            ]]
								        "></select><span id="count"></span>
			</div>
			<div>
				<span class="r-edit-label">项目作息时间:</span>
				<textarea class="easyui-validatebox textarea" name="workTimePeriod"></textarea>
			</div>
			<div>
				<span class="r-edit-label">项目描述:</span>
				<textarea class="easyui-validatebox textarea" name="projectDescription"></textarea>
			</div>
			<div style="margin-left: 100px;">
				<input type="submit" value="提交"></a>
			</div>
		</div>
	</form>
</div>