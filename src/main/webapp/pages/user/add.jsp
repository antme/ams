<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<% String id = request.getParameter("id"); %>

<script type="text/javascript">
	var id = "<%=id%>";

	$(document).ready(function() {
		initFormSubmit("add-user", "/ams/user/add.do", "添加用户", function(){
			alert("提交成功");
			loadRemotePage("user/list&a=4");
		});
		
		if(id!="null"){
			postAjaxRequest("/ams/user/load.do", {id:id}, function(data){
				
			if(data.data.userTypeId){
				var url = '/ams/sys/userlevel/list.do?userTypeId='+data.data.userTypeId;
		    	$('#userLevelId').combobox('reload', url);
			}
   
				$("#add-user").form('load',data.data);
				
			});
			
		}
		
	});
</script>
<div style="padding: 10px 60px 20px 60px">
	<form id="add-user" method="post" novalidate>
		<div class="form-container">
			<input class="" type="hidden" name="id" />
			<div>
				<span class="r-edit-label">用户名:</span> <input class="easyui-validatebox textbox" type="text" id="userName" name="userName" data-options="required:true"></input>
			</div>
			<div>
				<span class="r-edit-label">员工编号:</span> <input class="easyui-validatebox textbox" type="text" name="userCode"  data-options="required:true"></input>
			</div>
			
			<div>
				<span class="r-edit-label">上级领导:</span> <input class="easyui-combobox"  name="reportManagerId" 
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
				<span class="r-edit-label">角色:</span> <input class="easyui-combobox textbox" type="text" name="groupId" data-options="url:'/ams/sys/group/list.do?userId=',
                    method:'get',
                    valueField:'id',
                    required:true,
                    textField:'groupName',
                    panelHeight:'auto',
                    loadFilter:function(data){
						return data.rows;
					}"></input>
			</div>
			
			<div>
				<span class="r-edit-label">工种:</span> <input class="easyui-combobox textbox" type="text" name="userTypeId" data-options="url:'/ams/sys/usertype/list.do?userId=',
                    method:'get',
                    valueField:'id',
                    textField:'typeName',
                    panelHeight:'auto',
                    onSelect: function(rec){
			            var url = '/ams/sys/userlevel/list.do?userTypeId='+rec.id;
			            $('#userLevelId').combobox('reload', url);
			            $('#userLevelId').combobox('setValue', '');
			        },
                    loadFilter:function(data){
						return data.rows;
					}"></input>
			</div>
			<div>
				<span class="r-edit-label">级别:</span> <input class="easyui-combobox textbox"  id ="userLevelId" type="text" name="userLevelId"
				data-options="
                    valueField:'id',
                    textField:'levelName',
                    panelHeight:'auto',
                    loadFilter:function(data){
						return data.rows;
					}"></input>
			</div>
			<div>
				<span class="r-edit-label">手机登录状态:</span> <select class="easyui-combobox" name="status" style="width: 200px;">
					<option value="1">启用</option>
					<option value="0">禁用</option>
				</select>
			</div>
			<div>
				<span class="r-edit-label">后台登录状态:</span> <select class="easyui-combobox" name="bstatus" style="width: 200px;" >
					<option value="1">启用</option>
					<option value="0">禁用</option>
				</select>
			</div>
			<div>
				<span class="r-edit-label">参与多项目:</span> <select class="easyui-combobox" name="isMultipleProject" style="width: 200px;" >
					<option value="0">否</option>				
					<option value="1">是</option>
				</select>
			</div>
			<div>
				<span class="r-edit-label">参与多施工队:</span> <select class="easyui-combobox" name="isMultipleTeam" style="width: 200px;" >
					<option value="0">否</option>				
					<option value="1">是</option>
				</select>
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
				<span class="r-edit-label">手机:</span> <input class="easyui-validatebox textbox" type="text" validType="mobile" name="mobileNumber"></input>
			</div>
			
			<div>
				<span class="r-edit-label">所属队伍:</span> <input class="easyui-validatebox textbox" type="text" name="teamGroup"></input>
			</div>
			<div>
				<span class="r-edit-label">身份证:</span> <input class="easyui-validatebox textbox" type="text" validType="idcard" name="idCard"></input>
			</div>
			<div>
				<span class="r-edit-label">住址:</span> <input class="easyui-validatebox textbox-long" type="text" name="address"></input>
			</div>

			<div>
				<span class="r-edit-label">密码：</span> <input name="userPassword" id="userpassword" autocomplete="off" onfocus="this.type='password'" class="r-textbox at easyui-validatebox"
					type="password" validType="username" /> <span class="get_span"></span>
			</div>
			<div>
				<span class="r-edit-label">确认密码：</span> <input name="userpasswordConfirm" autocomplete="off" onfocus="this.type='password'" class="r-textbox at easyui-validatebox"
					type="password" validType="pwdEquals['#userpassword']" /> <span class="get_span"></span>
			</div>
			
			
			<div>
				<span class="r-edit-label">备注:</span>
				<textarea class="easyui-validatebox textarea" name="remark" ></textarea>
			</div>
			
			<div style="margin-left: 100px;">
				<input type="submit" value="提交"></a>
			</div>
		</div>
	</form>