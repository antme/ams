
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<br>
<script>

function search() {
	$('#reminderList').datagrid('load', {
		userId : $("#userId").combobox('getValue'),
		startDate : $("#startDate").datebox('getValue'),
		endDate : $("#endDate").datebox('getValue')	
	});
	return false;
}


</script>


<div>

	<span class="r-edit-label">用户:</span> <input class="easyui-combobox textbox" type="text" id="userId" name="userId" data-options="
                    valueField:'id',
                    url:'/ams/user/list.do?userId=',
                    textField:'userName',
                    panelHeight:'auto',
                    loadFilter:function(data){
						return data.rows;
					}"></input>

	
			
	<label>日报时间:</label>
	<input class="easyui-datebox textbox " type="text" name="startDate" id="startDate"/>
	<input class="easyui-datebox textbox " type="text" name="endDate" id="endDate"/>
	
	<button onclick="search(); return false;">搜索</button>
	
	
</div>


<table id="reminderList" class="easyui-datagrid" data-options="checkOnSelect:false, remoteFilter:true, fitColumns: true" url="/ams/notice/remind/all/list.do" iconCls="icon-save"
	sortOrder="asc" pagination="true" singleSelect="true">
	<thead>
		<tr>
			<th align="center" field="userName" width="80" sortable="false" resizable="true">用户名</th>
			<th align="center" field="remindDate" width="80" sortable="false" resizable="true">备忘录日期</th>
			<th align="center" field="title" width="150" sortable="false" resizable="true">标题</th>
			<th align="center" field="content" width="250" sortable="false" resizable="true">内容</th>
		</tr>
	</thead>
</table>