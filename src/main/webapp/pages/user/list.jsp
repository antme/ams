<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script>

	function search() {
		$('#userList').datagrid('load', {
			userName : $("#userName").val(),
			userTypeId : $("#userTypeId").combobox('getValue'),
			userLevelId : $("#userLevelId").combobox('getValue'),
			groupId : $("#groupId").combobox('getValue'),
			userCode : $("#userCode").val(),
			teamGroup : $("#teamGroup").val(),
			idCard : $("#idCard").val()
			
		});
	}
	
	function loadFilter(data){
		$("#count").html(data.total);
		return data;
	}
</script>
<div >
	<span class="r-edit-label">用户名:</span>
	<input type="text" name="userName" id="userName"/> 
	<span class="r-edit-label">工号:</span>
	<input type="text" name="userCode" id="userCode"/> 

	<span class="r-edit-label">所属队伍:</span>
	<input type="text" name="teamGroup" id="teamGroup"/> 
	<span class="r-edit-label">身份证号:</span>
	<input type="text" name="idCard" id="idCard"/> 
	<p></p>
	<span class="r-edit-label"> 角色:</span>
	 <input class="easyui-combobox textbox" type="text" id="groupId" name="groupId" data-options="url:'/ams/sys/group/list.do?userId=',
                    method:'get',
                    valueField:'id',
                    required:true,
                    textField:'groupName',
                    panelHeight:'auto',
                    loadFilter:function(data){
						return data.rows;
					}"></input>
					
					

				<span class="r-edit-label">工种:</span> <input class="easyui-combobox textbox" type="text" name="userTypeId" id="userTypeId" data-options="url:'/ams/sys/usertype/list.do?userId=',
                    method:'get',
                    valueField:'id',
                    required:true,
                    textField:'typeName',
                    panelHeight:'auto',
                    onSelect: function(rec){
			            var url = '/ams/sys/userlevel/list.do?userTypeId='+rec.id;
			            $('#userLevelId').combobox('reload', url);
			        },
                    loadFilter:function(data){
						return data.rows;
					}"></input>
			
				<span class="r-edit-label">级别:</span> <input class="easyui-combobox textbox"  id ="userLevelId" type="text" name="userLevelId"
				data-options="
                    valueField:'id',
                    required:true,
                    textField:'levelName',
                    panelHeight:'auto',
                    loadFilter:function(data){
						return data.rows;
					}"></input>
			
			
	
	<button onclick="search();">搜索</button>
</div>
<p></p>

<button onclick="loadRemotePage('user/add&a=4');">新增</button>
<p></p>
<span>员工总数:</span><span id="count" style="color:red; margin-left:5px;"></span>
<div>点击<img src="/resources/images/add.png">可以查看员工详情</div>
<table id=userList class="easyui-datagrid" data-options="checkOnSelect:false, remoteFilter:true, fitColumns: true, loadFilter: loadFilter" url="/ams/user/list.do" iconCls="icon-save"
	sortOrder="asc" pagination="true" singleSelect="true">
	<thead>
		<tr>
			<th align="center" field="userName" width="45" sortable="false" resizable="true">用户名</th>
			<th align="center" field="userCode" width="45" sortable="false" resizable="true">员工编号</th>
			<th align="center" field="typeName" width="55" sortable="false" resizable="true">工种</th>
			<th align="center" field="levelName" width="40" sortable="false" resizable="true">级别</th>
			<th align="center" field="mobileNumber" width="70" sortable="false" resizable="true">手机号</th>
			
			<th align="center" field="status" width="40" sortable="false" resizable="true" formatter="formatterUserMobileLoginOperation">手机登录</th>
			<th align="center" field="bstatus" width="40" sortable="false" resizable="true" formatter="formatterUserWebLoginOperation">后台登录</th>
			<th align="center" field="projects" width="100" sortable="false" resizable="true" >项目</th>
			<th align="center" field="teams" width="100" sortable="false" resizable="true" >施工队</th>
			<th align="center" field="createdOn" width="90" sortable="false" resizable="true">创建时期</th>
			<th align="center" data-options="field:'id',formatter:formatterUserOperation"  width="30">操作</th>

		</tr>
	</thead>
	
	 <script type="text/javascript">
        $(function(){
            $('#userList').datagrid({
                view: detailview,
                detailFormatter:function(index,row){
                    return '<div class="ddv" style="padding:5px 0; border: solid 1px; "></div>';
                },
                onExpandRow: function(index,row){
             	
                	if(row.isMultipleProject){
                		row.isMultipleProject_str = "是";
                	}else{
                		row.isMultipleProject_str = "否";
                	}
                	
                	if(row.isMultipleTeam){
                		row.isMultipleTeam_str = "是";
                	}else{
                		row.isMultipleTeam_str = "否";
                	}
                	
                	
                	if(row.displayForApp){
                		row.displayForApp_str = "是";
                	}else{
                		row.displayForApp_str = "否";
                	}
                	
                	
                    var ddv = $(this).datagrid('getRowDetail',index).find('div.ddv');
                    ddv.panel({
                        height:120,
                        border:false,
                        cache:false,
                        href:'/pages/user/template.html',
                        onLoad:function(){
                            $('#userList').datagrid('fixDetailRowHeight',index);
                            
                            ddv.form('load',row);
                        }
                    });
                    $('#userList').datagrid('fixDetailRowHeight',index);
                }
            });
        });
    </script>
    
    
</table>