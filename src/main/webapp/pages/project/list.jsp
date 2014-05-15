
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div class="page_tip">说明： 点击服务订单可以看到此产品订单分配给哪些服务商，和查看服务商安装进度</div>
<br>
<div class="p_height_div"></div>
<button onclick="loadRemotePage('project/add');">新增</button>
<button onclick="loadRemotePage('salary/add');">新增工资</button>
<table id=projectList class="easyui-datagrid" data-options="checkOnSelect:false, remoteFilter:true, fitColumns: true" url="/ams/project/list.do" iconCls="icon-save"
	sortOrder="asc" pagination="true" singleSelect="true">
	<thead>
		<tr>
			<th align="center" field="projectName" width="200" sortable="false" resizable="true">项目名称</th>
			<th align="center" field="projectStartDate" width="200" sortable="false" resizable="true">项目开始日期</th>
			<th align="center" field="projectStartDate" width="200" sortable="false" resizable="true">项目结束日期</th>
		</tr>
	</thead>
</table>