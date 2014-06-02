
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<script type="text/javascript">
 
        var editIndex = undefined;
        function endEditing(){           
        	return true;
        }
        function onClickRow(index){
        	var ddv = $("#customerList").datagrid('getRowDetail',rowIndex).find('table.ddv');
        	ddv.datagrid('endEdit', editIndex);
        	ddv.datagrid('selectRow', index).datagrid('beginEdit', index);

            editIndex = index;  
        }

        function removeit(){
            if (editIndex == undefined){return}
            $('#dg').datagrid('cancelEdit', editIndex)
                    .datagrid('deleteRow', editIndex);
            editIndex = undefined;
        }

		var rowId = undefined;

        var rowIndex = 0;
        $(function(){
            $('#customerList').datagrid({
                view: detailview,
                detailFormatter:function(index,row){
                    return '<div style="padding:5px;background-color: beige;"><div><h2><strong>联系人：</strong></h2></div><table class="ddv" ></table>';
                },
                onExpandRow: function(index,row){
                    var ddv = $(this).datagrid('getRowDetail',index).find('table.ddv');
                    rowIndex = index;
                    rowId = row.id;
                    postAjaxRequest('/ams/project/customer/contact/list.do', {customerId:row.id}, function(data){
                    	
                   ddv.datagrid({
                        fitColumns:true,
                        singleSelect:true,
                        rownumbers:true,
                        iconCls: 'icon-edit',
                        loadMsg:'',
                        height:'auto',
                        toolbar: [{
                            text:'新增',
                            iconCls:'icon-add',
                            id : rowIndex + "",
                            handler:function(){
                            	
                            	 var ddv = $("#customerList").datagrid('getRowDetail',this.id).find('table.ddv');
                            	
                            	 if(editIndex !=undefined){
                            		 ddv.datagrid('endEdit', editIndex);
                            	 }
                            	 ddv.datagrid('appendRow',{id:""});
                            	 
                            	

                            	 editIndex = ddv.datagrid('getRows').length-1;
                            	 ddv.datagrid('selectRow', editIndex).datagrid('beginEdit', editIndex);
                                 
                            }
                        },{
                            text:'删除',
                            id : rowIndex + "",
                            iconCls:'icon-cut',
                            handler:function(){
                            	var ddv = $("#customerList").datagrid('getRowDetail',this.id).find('table.ddv');
                            	if (editIndex == undefined){return}
                            	ddv.datagrid('cancelEdit', editIndex).datagrid('deleteRow', editIndex);
                                editIndex = undefined;
                                 
                            }
                        },'-',{
                            text:'保存',
                            id : rowIndex + "",
                            iconCls:'icon-save',
                            handler:function(){
                            	var ddv = $("#customerList").datagrid('getRowDetail',this.id).find('table.ddv');
                            	ddv.datagrid('endEdit', editIndex);
                            	var data = ddv.datagrid('getData');
                            	
                            	var rows =  $("#customerList").datagrid('getData');
                            	
                            	var postData = {
                            			customerId: rows.rows[this.id].id,
                            			rows: JSON.stringify(data.rows)
                            	}
                            	
                            	postAjaxRequest("/ams/project/customer/contact/add.do", postData, function(data){
                            		
                            		
                            	});
                            	
                            	if (editIndex == undefined){return}
                            	
                            	
                            }
                        }],
                        onClickRow: onClickRow,
                        data: data.rows,
                        columns:[[
                            {field:'contactPerson',title:'联系人',width:200, editor:'text'},
                            {field:'position',title:'职位',width:250, editor:'text'},
                            {field:'contactMobileNumber',title:'联系电话',width:200, editor:'text'}
                        ]],
                        onResize:function(){
                            $('#customerList').datagrid('fixDetailRowHeight',index);
                        },
                        onLoadSuccess:function(){
                            setTimeout(function(){
                                $('#customerList').datagrid('fixDetailRowHeight',index);
                            },0);
                        }
                    });
                    

                    $('#customerList').datagrid('fixDetailRowHeight',index);
                    
                    });
                    $('#customerList').datagrid('fixDetailRowHeight',index);
                }
            });
        });
        
        
</script>
    
    
<button onclick="loadRemotePage('customer/add&a=3');">新增</button>
<div>点击<img src="/resources/images/add.png">可以新增更多客户联系人</div>
<table id="customerList" class="easyui-datagrid" data-options="checkOnSelect:false, remoteFilter:true, fitColumns: true, height:450" url="/ams/project/customer/list.do" iconCls="icon-save"
	sortOrder="asc" pagination="true" singleSelect="true">
	<thead>
		<tr>
			<th align="center" field="name" width="200" sortable="false" resizable="true">客户名称</th>
			<th align="center" field="address" width="200" sortable="false" resizable="true">地址</th>
			<th align="center" field="contactPerson" width="100" sortable="false" resizable="true">客户联系人</th>
			<th align="center" field="position" width="100" sortable="false" resizable="true">联系人职位</th>
			<th align="center" field="contactMobileNumber" width="100" sortable="false" resizable="true">手机</th>
			<th align="center" field="remark" width="100" data-options="formatter:formatterDescription"  sortable="false" resizable="true">备注</th>
			<th align="center" data-options="field:'id',formatter:formatterCustomerOperation"  width="120">操作</th>
		</tr>
	</thead>
</table>