<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div>
	<label>关键字:</label>
	<input type="text" name="keyword" id="keyword"/> 
	

				<span class="r-edit-label">工种:</span> <input class="easyui-combobox textbox" type="text" name="userTypeId" data-options="url:'/ams/sys/usertype/list.do?userId=',
                    method:'get',
                    valueField:'id',
                    required:true,
                    textField:'typeName',
                    panelHeight:'auto',
                    onSelect: function(rec){
			            var url = '/ams/sys/userlevel/list.do?userTypeId='+rec.id;
			            $('#userTypeId').combobox('reload', url);
			        },
                    loadFilter:function(data){
						return data.rows;
					}"></input>
			
				<span class="r-edit-label">级别:</span> <input class="easyui-combobox textbox"  id ="userTypeId" type="text" name="userLevelId"
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
<table id=userList class="easyui-datagrid" data-options="checkOnSelect:false, remoteFilter:true, fitColumns: true" url="/ams/user/list.do" iconCls="icon-save"
	sortOrder="asc" pagination="true" singleSelect="true">
	<thead>
		<tr>
			<th align="center" field="userName" width="100" sortable="false" resizable="true">用户名</th>
			<th align="center" field="userCode" width="80" sortable="false" resizable="true">员工编号</th>
			<th align="center" field="userCategory" width="80" sortable="false" resizable="true">员工类型</th>
			<th align="center" field="typeName" width="80" sortable="false" resizable="true">工种</th>
			<th align="center" field="levelName" width="80" sortable="false" resizable="true">级别</th>
			<th align="center" field="status" width="80" sortable="false" resizable="true" formatter="formatterUserMobileLoginOperation">手机登录</th>
			<th align="center" field="bstatus" width="80" sortable="false" resizable="true" formatter="formatterUserWebLoginOperation">后台登录</th>
			<th align="center" field="mobileNumber" width="100" sortable="false" resizable="true">手机号</th>
			<th align="center" field="address" width="250" sortable="false" resizable="true">住址</th>
			<th align="center" field="createdOn" width="140" sortable="false" resizable="true">创建时期</th>
			<th align="center" data-options="field:'id',formatter:formatterUserOperation"  width="120">操作</th>

		</tr>
	</thead>
</table>