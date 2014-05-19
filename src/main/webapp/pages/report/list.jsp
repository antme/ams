
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<br>


<table id="reportList" class="easyui-datagrid" data-options="checkOnSelect:false, remoteFilter:true, fitColumns: true" url="/ams/project/dailyreport/app/list.do" iconCls="icon-save"
	sortOrder="asc" pagination="true" singleSelect="true">
	<thead>
		<tr>
			<th align="center" field="userName" width="100" sortable="false" resizable="true">用户名</th>
			<th align="center" field="plan" width="100" sortable="false" resizable="true">明日计划</th>
			<th align="center" field="summary" width="50" sortable="false" resizable="true">总结</th>
			<th align="center" field="reportDay" width="100" sortable="false" resizable="true">日报日期</th>
			<th align="center" field="workingRecord" width="100" sortable="false" resizable="true">工作纪录</th>
			<th align="center" field="materialRecord" width="100" sortable="false" resizable="true">材料纪录</th>
			<th align="center" field="weather" width="100" sortable="false" resizable="true">天气</th>
			<th align="center" field="projectName" width="100" sortable="false" resizable="true">项目</th>
		</tr>
	</thead>
</table>