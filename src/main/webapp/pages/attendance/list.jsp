
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<button>导出</button>
<table id=noticeList class="easyui-datagrid" data-options="checkOnSelect:false, remoteFilter:true, fitColumns: true" url="/ams/attendance/list.do" iconCls="icon-save"
	sortOrder="asc" pagination="true" singleSelect="true">
	<thead>
		<tr>
			<th align="center" field="attendanceDate" width="200" sortable="false" resizable="true">考勤日期</th>
			<th align="center" field="attendanceDayType" width="200" sortable="false" resizable="true">上午，下午</th>
			<th align="center" field="attendanceType" width="200" sortable="false" resizable="true">考勤类型</th>
			<th align="center" field="createdOn" width="200" sortable="false" resizable="true">提交日期</th>
		</tr>
	</thead>
</table>