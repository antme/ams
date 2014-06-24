
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
				var url = '/ams/user/project/user/select.do?id=' + project.id;
			
				$('#projectMemberIds').combogrid(
						{
							url: url, 
							onLoadSuccess: function(){
								$('#projectMemberIds').combogrid('setValues', project.projectMemberIds);
								onHidePanel();
							}
						}
					);
				
				
				
				
			});
		}
		
		
	});
	
	function onHidePanel(){		
		$("#count").html("已选择项目成员:" + $("#projectMemberIds").combogrid('getValues').length + "人");
	}
	
	
	
	function searchUser(){
		
		var teamId = $("#teamId").combobox('getValue');

		if(teamId==undefined){
			teamId = "";
		}

		if($("#username").val() == "" && (teamId == ""  ||  teamId==undefined)){			
			alert("请输入搜索条件");
			return false;
		}
		var url = '/ams/user/project/user/select.do?userName=' + $("#username").val() + '&teamId=' + teamId;
		
		$('#userdlg').dialog({
			modal : true
		});
		$('#userdlg').dialog('open');
		
		$("#projectMemberSearchIds").combogrid({url: url});
		$("#projectMemberSearchIds").combogrid("showPanel");
		
		hasChange = true;
		
	
		
	}
	
	
	function onClickRow(rowIndex, rowData){
		
		if(rowData.projectId && !rowData.isMultipleProject){
			var g = $('#projectMemberIds').combogrid('grid');
			g.datagrid('unselectRow', rowIndex);			
		}
	}
	
	function onLoadSuccess(){
		disableCheckBox("projectMemberIds");
	}
	function onLoadSuccess2(){
		
		var g = $("#projectMemberSearchIds").combogrid('grid');	
		var rows = g.datagrid('getRows');
		$(".datagrid-header-check").html("");
		
		for(var j=0;j<rows.length;j++){	
			  if(rows[j].projectId && !rows[j].isMultipleProject){
			  	$(".row_disable2[datagrid-row-index="+j+"] input[type='checkbox']")[0].disabled=true;
			  }

		}
	}
	
	
	function onChange(){
	
		var values = $("#projectMemberSearchIds").combogrid('getValues');
		var ovalues = $("#projectMemberIds").combogrid('getValues');
		
		var length = ovalues.length;
		for(var j=0; j< values.length; j++){			
			var find = false;
			for(var i = 0; i<ovalues.length; i++){			
					if(ovalues[i] == values[j]){
						find = true;
						break;
					}
				
			}			
			
			if(!find){			
				ovalues[length + j] = values[j];
			}
		}
		$("#projectMemberIds").combogrid('setValues', ovalues);
		
		$('#userdlg').dialog('close');
		onHidePanel();
	}
	
	function disableCheckBox(id){
		
		var g = $("#"+id).combogrid('grid');	
		var rows = g.datagrid('getRows');
		$(".datagrid-header-check").html("");
		
		for(var j=0;j<rows.length;j++){	
			  if(rows[j].projectId && !rows[j].isMultipleProject){
			  	$(".datagrid-row[datagrid-row-index="+j+"] input[type='checkbox']")[0].disabled=true;
			  }

		}
	}
	
	
	function rowStyler(index,rowData){
		if(rowData.projectId && !rowData.isMultipleProject){
			//return 'background-color:#777A7C;color:#fff;'; // return inline style
			return {	
				class:'row_disable'
			
			};
			//return {class:'row_disable', style:{'background-color:#777A7C;color:#fff;'}};
		}
	}
	
	
	
	function rowStyler2(index,rowData){
		if(rowData.projectId && !rowData.isMultipleProject){
			return {	
				class:'row_disable  row_disable2'
			
			};
		}
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
				<span class="r-edit-label"></span> 
				<span> 
				
					<div style="display:inline-block; margin-left:25px;">
			            用户名: <input class="easyui-validatebox textbox" type="text" name="username" id="username" style="width:80px">
			            施工队: <input class="easyui-combobox textbox"  id ="teamId" type="text" name="teamId"
								data-options="
			                    valueField:'id',
			                    url:'/ams/user/team/list.do?userId=',
			                    textField:'teamName',
			                    panelHeight:'auto',
			                    loadFilter:function(data){
									return data.rows;
								}"></input>
			            <a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="searchUser();">搜索</a>
			        </div>
			        
			       
				</span>
						
			</div>
			<div>
				<span class="r-edit-label">项目成员：</span> 
						<select id="projectMemberIds" class="easyui-combogrid" name="projectMemberIds[]" style="width:700px; height:40px;"
								        data-options="
								            panelWidth:700,
								            panelHeight:450,
								            idField:'id',
								            multiple: true,
								            textField:'userName',
								            fitColumns: true,
								            rowStyler: rowStyler,
								            onLoadSuccess: onLoadSuccess,
								            onClickRow: onClickRow,
								            onHidePanel:onHidePanel,
								            url:'/ams/user/project/user/select.do',
								            columns:[[
								            	{field:'ck',checkbox:true},
								                {field:'userName',title:'用户名',width:60},
								                {field:'userCode',title:'员工编号',width:60},
								                {field:'typeName',title:'员工类型',width:60},
								                {field:'levelName',title:'员工级别',width:60},
								                {field:'id',title:'Id',width:100, hidden:true}
								            ]]
								        "></select><span id="count"></span>
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
				<span class="r-edit-label">显示顺序:</span> <input class="easyui-validatebox textbox input-title" type="number" name="displayOrder" value="0" ></input>
				<span>数值高的将显示在后面</span>
			</div>
			<div>
				<span class="r-edit-label">是否手机显示:</span> <select class="easyui-combobox" name="displayForApp" style="width: 200px;" >
					<option value="1">是</option>
					<option value="0">否</option>									
				</select>
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


<div id="userdlg"  title="员工搜索" data-options=
				"iconCls:'icon-save',
				toolbar: [{
				                    text:'保存',
				                    iconCls:'icon-save',
				                    handler:function(){
				                        onChange();
				   			}}]" style="width:800px;height:550px;padding:10px;">

			
			<select id="projectMemberSearchIds" style="width:700px; height:40px;display:none;" 
								        data-options="
								            panelWidth:700,
								            panelHeight:450,
								            idField:'id',
								            multiple: true,
								            textField:'userName',
								            fitColumns: true,
								            rowStyler: rowStyler2,
								            onLoadSuccess: onLoadSuccess2,
								            onClickRow: onClickRow,
								            onHidePanel:onHidePanel,
								            columns:[[
								            	{field:'ck',checkbox:true},
								                {field:'userName',title:'用户名',width:60},
								                {field:'userCode',title:'员工编号',width:60},
								                {field:'typeName',title:'员工类型',width:60},
								                {field:'levelName',title:'员工级别',width:60},
								                {field:'id',title:'Id',width:100, hidden:true}
								            ]]
								        "></select><button onclick="onChange();" style="display:none;">添加</button>
			
</div>
