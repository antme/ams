
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<br>
<script type="text/javascript">
	$(document).ready(function() {
		initFormSubmit("task_import", "/ams/sys/salary/import.do", "任务导入", function(){
			alert("导入成功");
			loadRemotePage("salary/list&a=4");
		});
	});
	
	
	$(function(){
        $('#salaryList').datagrid({
            view: detailview,
            detailFormatter:function(index,row){
                return '<div style="padding:5px;background-color: beige;"><div><h2><strong>应付款：</strong></h2></div><table class="ddv"></table><div style="margin-top:5px;"><h2><strong>应扣款：</strong></h2></div><table class="ddv1"></table></div>';
            },
            onExpandRow: function(index,row){
                var ddv = $(this).datagrid('getRowDetail',index).find('table.ddv');
                var ddv1 = $(this).datagrid('getRowDetail',index).find('table.ddv1');
                postAjaxRequest('/ams/user/salary/app/detail.do', {id:row.id}, function(data){
                	
                ddv.datagrid({
                    fitColumns:true,
                    singleSelect:true,
                    rownumbers:true,
                    loadMsg:'',
                    height:'auto',
                    data: data.data.salaryItems,
                    columns:[[
                        {field:'projectName',title:'项目',width:200},
                        {field:'attendanceDays',title:'出勤数',width:50},
                        {field:'totolSalary',title:'工资',width:80},
                        {field:'performanceSalary',title:'绩效',width:80},
                        {field:'performanceSalaryUnit',title:'绩效价',width:80,align:'right'},
                        {field:'comment',title:'备注',width:150,align:'right'}
                    ]],
                    onResize:function(){
                        $('#salaryList').datagrid('fixDetailRowHeight',index);
                    },
                    onLoadSuccess:function(){
                        setTimeout(function(){
                            $('#salaryList').datagrid('fixDetailRowHeight',index);
                        },0);
                    }
                });
                
                
                ddv1.datagrid({
                    fitColumns:true,
                    singleSelect:true,
                    rownumbers:true,
                    loadMsg:'',
                    height:'auto',
                    data: data.data.deductedSalaryItems,
                    columns:[[
                        {field:'name',title:'项目',width:200},
                        {field:'totolSalary',title:'工资',width:80},
                        {field:'comment',title:'备注',width:150,align:'right'}
                    ]],
                    onResize:function(){
                        $('#salaryList').datagrid('fixDetailRowHeight',index);
                    },
                    onLoadSuccess:function(){
                        setTimeout(function(){
                            $('#salaryList').datagrid('fixDetailRowHeight',index);
                        },0);
                    }
                });
                
                $('#salaryList').datagrid('fixDetailRowHeight',index);
                
                });
                $('#salaryList').datagrid('fixDetailRowHeight',index);
            }
        });
    });
	
</script>
<form id="task_import" action="/ams/sys/salary/import.do" method="post" enctype="multipart/form-data">
	<input type="file" name="salaryFile"/>
	<input type="submit" value="上传"/>
</form>
<p></p>
<hr>
<script>

	function search() {
		$('#salaryList').datagrid('load', {
			userName : $("#userName").val(),
			years : $("#years").val(),
			month : $("#month").val()
			
		});
	}
	
	function deleteSalary(){
		deleteMultipleData("salaryList", "/ams/user/salary/delete.do");
	}
</script>

<div>
	<label>用户名:</label> <input type="text" name="userName" id="userName" /> 
	<label>年份:</label> <input type="number" name="years" id="years" /> 
	<label>月份:</label> <input type="number" name="month" id="month" />


	<button onclick="search();">搜索</button>
</div>
<p></p>

<button onclick="deleteSalary();">删除</button>
<p></p>
<div>点击<img src="/resources/images/add.png">可以查看工资细节</div>
<table id="salaryList" class="easyui-datagrid" data-options="height:500, selectOnCheck:true, checkOnSelect:true, remoteFilter:true, fitColumns: true" url="/ams/user/salary/list.do" iconCls="icon-save"
	sortOrder="asc" pagination="true" ">
	<thead>
		<tr>
			<th data-options="field:'ck',checkbox:true"></th>
			<th align="center" field="userName" width="100" sortable="false" resizable="true">用户名</th>
			<th align="center" field="year" width="100" sortable="false" resizable="true">年份</th>
			<th align="center" field="month" width="50" sortable="false" resizable="true">月份</th>
			<th align="center" field="totalSalary" width="100" sortable="false" resizable="true">应发工资</th>
			<th align="center" field="deductedSalary" width="100" sortable="false" resizable="true">应扣工资</th>
			<th align="center" field="remainingSalaray" width="100" sortable="false" resizable="true">剩余工资</th>
		</tr>
	</thead>
</table>