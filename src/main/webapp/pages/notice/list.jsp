
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div class="page_tip">说明： 点击服务订单可以看到此产品订单分配给哪些服务商，和查看服务商安装进度</div>
<br>
<div class="p_height_div"></div>
<button onclick="loadRemotePage('notice/add');">新增</button>
<table id=noticeList class="easyui-datagrid" data-options="checkOnSelect:false, remoteFilter:true, fitColumns: true" url="/ams/notice/list.do" iconCls="icon-save"
	sortOrder="asc" pagination="true" singleSelect="true">
	<thead>
		<tr>
			<th align="center" field="title" width="100" sortable="false" resizable="true">标题</th>
			<th align="center" field="publisher" width="100" sortable="false" resizable="true">发布人</th>
			<th align="center" field="priority" width="50" sortable="false" resizable="true">优先级</th>
			<th align="center" field="publishDate" width="100" sortable="false" resizable="true">发布日期</th>
			<th align="center" field="publishEndDate" width="100" sortable="false" resizable="true">公告结束日期</th>
			<th align="center" field="attachFileUrl" width="100" sortable="false" formatter="formatteAttachFileLink" resizable="true">附件地址</th>
			<th align="center" field="content" width="200" sortable="false" resizable="true">内容</th>
		</tr>
	</thead>
</table>