
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<% String id = request.getParameter("id"); %>

<script type="text/javascript">
	var id = "<%=id%>";

	$(document).ready(function() {
		initFormSubmit("add-team", "/ams/user/team/add.do", "添加团队", function(){
			alert("提交成功");
			loadRemotePage("team/list&a=4");
		});
		
		if(id!="null"){
			postAjaxRequest("/ams/user/team/get.do", {id: id}, function(data){
				var team = data.data;
				$("#add-team").form('load',team);
				var url = '/ams/user/team/user/select.do?id=' + team.id;
				
				$('#teamMemberIds').combogrid(
						{
							url: url, 
							onLoadSuccess: function(){
								$('#teamMemberIds').combogrid('setValues', team.teamMemberIds);
								onHidePanel();
							}
						}
					);
				
				
				
			});
		}
	});
	
	function onHidePanel(){		
		$("#count").html("已选择团队成员:" + $("#teamMemberIds").combogrid('getValues').length + "人");
	}
	
	

	function searchUser(){
		
		var projectId = $("#projectId").combobox('getValue');


		if($("#username").val() == "" && projectId == ""){			
			alert("请输入搜索条件");
			return false;
		}
		var url = '/ams/user/project/user/select.do?userName=' + $("#username").val() + '&projectId=' + projectId;
		
		$('#userdlg').dialog({
			modal : true
		});
		$('#userdlg').dialog('open');
		
		$("#projectMemberSearchIds").combogrid({url: url});
		$("#projectMemberSearchIds").combogrid("showPanel");
		
		hasChange = true;
		
	
		
	}
	
	
	function onClickRow(rowIndex, rowData){
		
		if(rowData.teamId && !rowData.isMultipleTeam){
			var g = $('#teamMemberIds').combogrid('grid');
			g.datagrid('unselectRow', rowIndex);			
		}
	}
	
	function onLoadSuccess(){
		disableCheckBox("teamMemberIds");
	}
	function onLoadSuccess2(){
		
		var g = $("#projectMemberSearchIds").combogrid('grid');	
		var rows = g.datagrid('getRows');
		$(".datagrid-header-check").html("");
		
		for(var j=0;j<rows.length;j++){	
			  if(rows[j].teamId && !rows[j].isMultipleTeam){
			  	$(".row_disable2[datagrid-row-index="+j+"] input[type='checkbox']")[0].disabled=true;
			  }

		}
	}
	
	
	function onChange(){
	
		var values = $("#projectMemberSearchIds").combogrid('getValues');
		var ovalues = $("#teamMemberIds").combogrid('getValues');
		
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
		$("#teamMemberIds").combogrid('setValues', ovalues);
		
		$('#userdlg').dialog('close');
	}
	
	function disableCheckBox(id){
		
		var g = $("#"+id).combogrid('grid');	
		var rows = g.datagrid('getRows');
		$(".datagrid-header-check").html("");
		
		for(var j=0;j<rows.length;j++){	
			  if(rows[j].teamId && !rows[j].isMultipleTeam){
			  	$(".datagrid-row[datagrid-row-index="+j+"] input[type='checkbox']")[0].disabled=true;
			  }

		}
	}
	
	
	function rowStyler(index,rowData){
		if(rowData.teamId && !rowData.isMultipleTeam){
			//return 'background-color:#777A7C;color:#fff;'; // return inline style
			return {	
				class:'row_disable'
			
			};
			//return {class:'row_disable', style:{'background-color:#777A7C;color:#fff;'}};
		}
	}
	
	
	
	function rowStyler2(index,rowData){
		if(rowData.teamId && !rowData.isMultipleTeam){
			return {	
				class:'row_disable  row_disable2'
			
			};
		}
	}
	
	
	
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
				<span class="r-edit-label"></span> 
				<span> 
				
					<div style="display:inline-block; margin-left:25px;">
			            用户名: <input class="easyui-validatebox textbox" type="text" name="username" id="username" style="width:80px">
			            项目:<input class="easyui-combobox textbox" type="text" id="projectId" name="projectId" data-options="url:'/ams/project/list.do?userId=',
			                    method:'get',
			                    valueField:'id',
			                    textField:'projectName',
			                    panelHeight:'auto',
			                    loadFilter:function(data){
									return data.rows;
								}"></input>
			            <a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="searchUser();">搜索</a>
			        </div>
			        
			       
				</span>
						
			</div>
			
			
			<div>
				<span class="r-edit-label">团队成员：</span> <select id="teamMemberIds" class="easyui-combogrid" name="teamMemberIds[]" style="width:700px; height:40px;"
								        data-options="
								            panelWidth:700,
								            panelHeight:450,
								            idField:'id',
								            multiple: true,
								            textField:'userName',
								            fitColumns: true,
								            onHidePanel:onHidePanel,
								            rowStyler: rowStyler,
								            onLoadSuccess: onLoadSuccess,
								            onClickRow: onClickRow,
								            url:'/ams/user/team/user/select.do',
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
			
			<div style="margin-left: 100px;">
				<input type="submit" value="提交"></a>
			</div>
		</div>
	</form>
	
	
	
	
	
<div id="userdlg"  title="员工搜索" data-options="iconCls:'icon-save'" style="width:800px;height:550px;padding:10px;">

			
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
								        "></select><button onclick="onChange();">添加</button>
			
</div>