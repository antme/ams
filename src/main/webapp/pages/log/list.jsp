
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<script>
	function openLogDetails(id){
		postAjaxRequest("/ams/sys/log/items/list.do", {id:id}, function(data){
			
			$('#logitem').datagrid({
			    data:data,
			    columns:[[
			        {field:'tableName',title:'表名',width:100},
			        {field:'field',title:'字段',width:100},
			        {field:'oldValue',title:'旧值',width:100,align:'right'},
			        {field:'newValue',title:'新值',width:100,align:'right'}
			    ]]
			});
			
			$('#logdetails').window('open')
		});		
	}
</script>

<table id="logList" class="easyui-datagrid" data-options="checkOnSelect:false, remoteFilter:true, fitColumns: true" url="/ams/sys/log/list.do" iconCls="icon-save" sortOrder="asc"
	pagination="true" singleSelect="true">
	<thead>
		<tr>
			<th align="center" field="userName" width="200" sortable="false" resizable="true">操作人</th>
			<th align="center" field="createdOn" width="200" sortable="false" resizable="true">时间</th>
			<th align="center" field="message" width="100" data-options="formatter:formatterLogMsg" sortable="false" resizable="true">事件</th>
			<th align="center" data-options="field:'id',formatter:formatterLogOperation" width="120">详情</th>
		</tr>
	</thead>
</table>


<div id="logdetails" class="easyui-window" title="数据库修改详情" data-options="modal:true,closed:true,iconCls:'icon-save'" style="width: 600px; height: 500px; padding: 10px;">
	<div id="logitem"></div>	
</div>