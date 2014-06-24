
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script>

	function search() {
		$('#noticeList').datagrid('load', {
			keyword : $("#keyword").val()
		});
	}
	

	function deleteNotice(id) {
		var data = {
			id : id
		};

		if (confirm("确认删除此数据")) {
			postAjaxRequest("/ams/notice/delete.do", data, function(data) {
				alert("删除成功");
				$("#noticeList").datagrid('reload');
			});
		}
	}
</script>
<div>
	<label>关键字搜索:</label><input type="text" name="keyword" id="keyword"/> <button onclick="search();">搜索</button>
</div>
<p></p>
<button onclick="loadRemotePage('notice/add');">新增</button>
 
<table id="noticeList" class="easyui-datagrid" data-options="checkOnSelect:true, remoteFilter:true, fitColumns: true"  url="/ams/notice/list.do" iconCls="icon-save"
	sortOrder="asc" pagination="true" singleSelect="true">
	<thead>
		<tr>
			<th align="center" field="title" width="200" sortable="false" resizable="true">标题</th>
			<th align="center" field="publisher" width="80" sortable="false" resizable="true">发布人</th>
			<th align="center" field="priority" width="50" sortable="false" resizable="true">优先级</th>
			<th align="center" field="createdOn" width="150" sortable="false" resizable="true">发布日期</th>
			<th align="center" field="publishEndDate" width="120" sortable="false" resizable="true">结束日期</th>
			<th align="center" field="attachFileUrl" width="60" sortable="false" formatter="formatteAttachFileLink" resizable="true">附件</th>
			<th align="center" field="content" width="350"  data-options="formatter:formatterDescription"  sortable="false" resizable="true">内容</th>
			<th align="center" data-options="field:'id',formatter:formatterNoticeOperation"  width="120">操作</th>
		</tr>
	</thead>
</table>