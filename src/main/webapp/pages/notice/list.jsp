
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<br>
<button onclick="loadRemotePage('notice/add');">新增</button>
<button onclick="editNotice();">编辑</button>
<script>

  function editNotice(){
	  var id = getGridCheckedSigleId("noticeList");
	  if(id){
	  	loadRemotePage("notice/add&a=0&id=" + id);
	  }
  }
  
</script>

<table id="noticeList" class="easyui-datagrid" data-options="checkOnSelect:true, remoteFilter:true, fitColumns: false"  url="/ams/notice/list.do" iconCls="icon-save"
	sortOrder="asc" pagination="true" singleSelect="true">
	<thead>
		<tr>
			<th align="center" field="title" width="200" sortable="false" resizable="true">标题</th>
			<th align="center" field="publisher" width="100" sortable="false" resizable="true">发布人</th>
			<th align="center" field="priority" width="50" sortable="false" resizable="true">优先级</th>
			<th align="center" field="createdOn" width="120" sortable="false" resizable="true">发布日期</th>
			<th align="center" field="publishEndDate" width="120" sortable="false" resizable="true">公告结束日期</th>
			<th align="center" field="attachFileUrl" width="80" sortable="false" formatter="formatteAttachFileLink" resizable="true">附件地址</th>
			<th align="center" field="content" width="350" sortable="false" resizable="true">内容</th>
		</tr>
	</thead>
</table>