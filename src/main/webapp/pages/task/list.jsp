
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<br>
<script type="text/javascript">
	$(document).ready(function() {
		initFormSubmit("task_import", "/ams/sys/task/import.do", "任务导入", function(){
			alert("导入成功");
			loadRemotePage("task/list&a=2");
		});
	});
	

	function search() {
		$('#taskList').datagrid('load', {
			userName : $("#userName").val(),
			projectId : $("#projectId").combobox('getValue'),
			teamId : $("#teamId").combobox('getValue')
			
		});
	}
</script>
<form id="task_import" action="/ams/sys/task/import.do" method="post" enctype="multipart/form-data">
	<span>任务文件上传：</span><input type="file" name="taskFile"/>
	<br>
	<input type="submit" value="上传"/>
</form>
<p></p>
<hr>


<div>
	<label>施工员:</label>
	<input type="text" name="userName" id="userName"/> 
	

				<span class="r-edit-label">项目:</span> <input class="easyui-combobox textbox" type="text" id="projectId" name="projectId" data-options="url:'/ams/project/list.do?userId=',
                    method:'get',
                    valueField:'id',
                    required:true,
                    textField:'projectName',
                    panelHeight:'auto',
                    loadFilter:function(data){
						return data.rows;
					}"></input>
			
				<span class="r-edit-label">施工队:</span> <input class="easyui-combobox textbox"  id ="teamId" type="text" name="teamId"
				data-options="
                    valueField:'id',
                    url:'/ams/user/team/list.do?userId=',
                    required:true,
                    textField:'teamName',
                    panelHeight:'auto',
                    loadFilter:function(data){
						return data.rows;
					}"></input>
			
			
	
	<button onclick="search();">搜索</button>
</div>

<div style="margin-top:20px;"></div>
<table id="taskList" class="easyui-datagrid" data-options="checkOnSelect:false, remoteFilter:true, fitColumns: true" url="/ams/project/task/list.do" iconCls="icon-save"
	sortOrder="asc" pagination="true" singleSelect="true">
	<thead>
		<tr>
			<th  field="taskName" width="150" sortable="false" resizable="true">任务名字</th>
			<th align="center" field="projectName" width="150" sortable="false" resizable="true">项目</th>
			<th align="center" field="teamName" width="100" sortable="false" resizable="true">施工队</th>
			<th align="center" field="userName" width="100" sortable="false" resizable="true">施工员</th>
			<th align="center" field="taskContactPhone" width="100" sortable="false" resizable="true">联系电话</th>
			<th align="center" field="amount" width="100" sortable="false" resizable="true">施工面积</th>
			<th align="center" field="unit" width="80" sortable="false" resizable="true">单位</th>
			<th align="center" field="price" width="80" sortable="false" resizable="true">绩效单价/元</th>
			<th align="center" field="projectStartDate" width="100" sortable="false" resizable="true">开工日期</th>
			<th align="center" field="projectEndDate" width="100" sortable="false" resizable="true">竣工日期</th>
			<th align="center" field="taskPeriod" width="100" sortable="false" resizable="true">工期</th>
			<th field="description" width="200"  data-options="formatter:formatterDescription"  sortable="false" resizable="true">施工细节</th>
		</tr>
	</thead>
</table>