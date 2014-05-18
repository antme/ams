
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<script type="text/javascript">
	$(document).ready(function() {
		initFormSubmit("add-project", "/ams/project/add.do", "添加项目", function(){
			alert("添加成功");
			loadRemotePage("project/list");
		});
	});
</script>

<div style="padding: 10px 60px 20px 60px">
	<form id="add-project" method="post">
		<div class="form-container" style="width: 500px;">
			<input class="" type="hidden" name="id" />
			<div>
				<span class="label">项目名称:</span> <input class="easyui-validatebox textbox input-title" type="text" name="projectName" ></input>
			</div>
			<div>
				<span class="label">所属部门:</span> <input class="easyui-combobox"  name="departmentId" 
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
				<span class="label">团队负责人:</span> <input class="easyui-combobox"  name="projectManagerId" 
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
				<span class="label">项目状态</span> <select class="easyui-combobox" name="projectStatus" style="width: 200px;">
					<option value="1">进行中</option>				
					<option value="0">未开始</option>
					<option value="2">延期</option>
					<option value="3">已结束</option>
				</select>
			</div>

			<div>
				<span class="label">项目开始时间:</span>
				<td><input class="easyui-datebox easyui-validatebox textbox" type="text" name="projectStartDate" ></input></td>
			</div>

			<div>
				<span class="label">项目结束时间:</span>
				<td><input class="easyui-datebox easyui-validatebox textbox" type="text" name="projectEndDate"></input></td>
			</div>


			<div>
				<span class="label">项目描述:</span>
				<textarea class="easyui-validatebox textbox" name="projectDescription"></textarea>
			</div>
			<div style="margin-left: 100px;">
				<input type="submit" value="提交"></a>
			</div>
		</div>
	</form>
</div>