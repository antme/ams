
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<script>

	function search() {
		$('#teamList').datagrid('load', {
			teamName : $("#teamName").val(),
			teamLeaderId : $("#teamLeaderId").combobox('getValue'),
			projectId : $("#projectId").combobox('getValue')
			
		});
	}
</script>


<div>
	<label>施工队:</label>
	<input type="text" name="teamName" id="teamName"/> 
	
	
	
				<span class="r-edit-label">队长:</span> <input class="easyui-combobox textbox" type="text" id="teamLeaderId" name="teamLeaderId" data-options="
                    valueField:'id',
                    url:'/ams/user/list.do?userId=',
                    textField:'userName',
                    panelHeight:'auto',
                    loadFilter:function(data){
						return data.rows;
					}"></input>
			
				<span class="r-edit-label">项目:</span> <input class="easyui-combobox textbox"  id ="projectId" type="text" name="projectId"
				data-options="
                    valueField:'id',
                    url:'/ams/project/list.do?userId=',
                    textField:'projectName',
                    panelHeight:'auto',
                    loadFilter:function(data){
						return data.rows;
					}"></input>
			
			
	
	<button onclick="search();">搜索</button>
</div>



<button onclick="loadRemotePage('team/add');">新增</button>

<table id="teamList" class="easyui-datagrid" height="600" data-options="checkOnSelect:false, remoteFilter:true, fitColumns: true" url="/ams/user/team/list.do" iconCls="icon-save"
	sortOrder="asc" pagination="true" singleSelect="true">
	<thead>
		<tr>
			<th align="center" field="teamName" width="100" sortable="false" resizable="true">团队名称</th>
			<th align="center" field="departmentName" width="100" sortable="false" resizable="true">所属部门</th>
			<th align="center" field="projectName" width="150" sortable="false" resizable="true">所属项目</th>
			<th align="center" field="userName" width="100" sortable="false" resizable="true">队长</th>
			<th align="center" field="teamMembers" width="250" sortable="false" resizable="true">团队成员</th>
			<th align="center" data-options="field:'id',formatter:formatterTeamOperation"  width="120">操作</th>
		</tr>
	</thead>
</table>