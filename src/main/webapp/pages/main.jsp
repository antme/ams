<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>


<div class="easyui-layout" style="width: 1050px; height: 300px;">
	<div data-options="region:'east',split:true,collapsible:false" title="最新日报" style="width: 550px;">
		<table id="reportList" class="easyui-datagrid" data-options="checkOnSelect:false, remoteFilter:true, fitColumns: true" url="/ams/project/dailyreport/list.do?test=" iconCls="icon-save"
			sortOrder="asc" pagination="false" singleSelect="true">
			<thead>
				<tr>
					<th align="center" field="userName" width="100" sortable="false" resizable="true">用户名</th>
					<th align="center" field="reportDay" width="100" sortable="false" resizable="true">日报日期</th>
					<th align="center" field="projectName" width="100" sortable="false" resizable="true">项目</th>
				</tr>
			</thead>
		</table>
	</div>
	<div data-options="region:'west',split:true,collapsible:false" title="最新公告" style="width: 500px;">
		<table id="noticeList" class="easyui-datagrid" data-options="checkOnSelect:true, pagination:false, remoteFilter:true, fitColumns: true" url="/ams/notice/list.do"
			iconCls="icon-save" sortOrder="asc" singleSelect="true" height="300">
			<thead>
				<tr>
					<th align="center" field="title" width="200" sortable="false" resizable="true">标题</th>
					<th align="center" field="createdOn" width="130" sortable="false" resizable="true">发布日期</th>
				</tr>
			</thead>
		</table>
	</div>

</div>
<div class="easyui-layout" style="width: 1050px; height: 500px;">
	<div data-options="region:'east',split:true, collapsible:false" title="最新图片" style="width: 550px;">
	<table id="picList" class="easyui-datagrid" data-options="checkOnSelect:false, remoteFilter:true, fitColumns: true" url="/ams/user/pic/list.do?test=" iconCls="icon-save"
	sortOrder="asc" pagination="false" singleSelect="true">
	<thead>
		<tr>
			<th align="center" field="userName" width="200" sortable="false" resizable="true">上传者</th>
			<th align="center" field="projectName" width="200" sortable="false" resizable="true">项目</th>
			<th align="center" field="picUrl" data-options="formatter:formatterPicOperation"  width="200" sortable="false" resizable="true">图片</th>			
		</tr>
	</thead>
</table>
	</div>
	<div data-options="region:'west',split:true, collapsible:false" title="今日事宜" style="width: 500px;"></div>

</div>