
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<br>
<script>

function search() {
	$('#reportList').datagrid('load', {
		queryUserId : $("#userId").combobox('getValue'),
		startDate : $("#startDate").datebox('getValue'),
		endDate : $("#endDate").datebox('getValue'),
		projectId : $("#projectId").combobox('getValue')		
	});
	return false;
}

function deleteTasks(){
	deleteMultipleData("reportList", "/ams/user/report/delete.do");
}

$(function(){
    $('#reportList').datagrid({
        view: detailview,
        detailFormatter:function(index,row){
            return '<div style="padding:5px;background-color: beige;"><div><h2><strong>评论详情：</strong></h2></div><table class="ddv"></table>';
        },
        onExpandRow: function(index,row){
            var ddv = $(this).datagrid('getRowDetail',index).find('table.ddv');
           
            	
            ddv.datagrid({
                fitColumns:true,
                singleSelect:true,
                rownumbers:true,
                loadMsg:'',
                height:'auto',
                data: row.comments,
                columns:[[
                    {field:'userName',title:'评论人',width:100},
                    {field:'comment',title:'内容',width:350},
                    {field:'commentDate',title:'评论日期',width:80}
                ]],
                onResize:function(){
                    $('#reportList').datagrid('fixDetailRowHeight',index);
                },
                onLoadSuccess:function(){
                    setTimeout(function(){
                        $('#reportList').datagrid('fixDetailRowHeight',index);
                    },0);
                }
            });
            

            $('#reportList').datagrid('fixDetailRowHeight',index);
         
        }
    });
});
</script>


<div>
   <form action="/ams/project/dailyreport/export.do" method="post">
	<span class="r-edit-label">用户:</span> <input class="easyui-combobox textbox" type="text" id="userId" name="userId" data-options="
                    valueField:'id',
                    url:'/ams/user/list.do?userId=',
                    textField:'userName',
                    panelHeight:'auto',
                    loadFilter:function(data){
						return data.rows;
					}"></input>

	<span class="r-edit-label">项目:</span> <input class="easyui-combobox textbox"  id ="projectId" type="text" name="projectId"
				data-options="
                    valueField:'id',
                    url:'/ams/project/list.do?userId=',
                    textField:'projectName',
                    panelHeight:'auto',
                    loadFilter:function(data){
						return data.rows;
					}"></input>
			
				
			
	<label>日报时间:</label>
	<input class="easyui-datebox textbox " type="text" name="startDate" id="startDate"/>
	<input class="easyui-datebox textbox " type="text" name="endDate" id="endDate"/>
	
	<button onclick="search(); return false;">搜索</button><button>导出</button>
	
	</form>
	
</div>

<div>点击<img src="/resources/images/add.png">可以查看日报评论</div>
<table id="reportList" class="easyui-datagrid" data-options="checkOnSelect:false, remoteFilter:true, fitColumns: true, height:400" url="/ams/project/dailyreport/list.do" iconCls="icon-save"
	sortOrder="asc" pagination="true" singleSelect="true">
	<thead>
		<tr>
			<th align="center" field="userName" width="80" sortable="false" resizable="true">用户名</th>
			<th align="center" field="projectName" width="100" sortable="false" resizable="true">项目</th>
			<th align="center" field="reportDay" width="80" sortable="false" resizable="true">日报日期</th>
			<th align="center" field="workingRecord" width="150" sortable="false" resizable="true">工作纪录</th>
			<th align="center" field="materialRecord" width="150" sortable="false" resizable="true">材料纪录</th>
			<th align="center" field="summary" width="150" sortable="false" resizable="true">总结</th>
			<th align="center" field="plan" width="150" sortable="false" resizable="true">明日计划</th>			
			<th align="center" field="weather" width="100" sortable="false" resizable="true">天气</th>
		</tr>
	</thead>
</table>