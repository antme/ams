
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<button >导出</button>
<table id=noticeList class="easyui-datagrid" data-options="checkOnSelect:false, remoteFilter:true, fitColumns: true" url="/ams/user/pic/list.do" iconCls="icon-save"
	sortOrder="asc" pagination="true" singleSelect="true">
	<thead>
		<tr>
			<th align="center" field="userName" width="200" sortable="false" resizable="true">上传者</th>
			<th align="center" field="projectName" width="200" sortable="false" resizable="true">项目</th>
			<th align="center" field="description" width="200" sortable="false" resizable="true">描述</th>
			<th align="center" field="createdOn" width="200" sortable="false" resizable="true">上传时间</th>
			<th align="center" field="picUrl" data-options="formatter:formatterPicOperation"  width="200" sortable="false" resizable="true">图片</th>			
		</tr>
	</thead>
</table>