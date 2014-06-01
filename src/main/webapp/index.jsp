<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<meta name="keywords" content="新鹰建筑工程考勤管理系统" />
<meta name="description" content="新鹰建筑工程考勤管理系统" />
<title>新鹰建筑工程考勤管理系统</title>
<link rel="stylesheet" type="text/css" href="/resources/css/easyui.css" />
<link rel="stylesheet" type="text/css" href="/resources/css/icon.css">
	<link rel="stylesheet" type="text/css" href="/resources/css/common.css" />
	<link rel="stylesheet" type="text/css" href="/resources/css/style.css">
		<script type="text/javascript" src="/resources/js/jquery-1.11.1.min.js"></script>
		<script type="text/javascript" src="/resources/js/jquery.easyui.min.js"></script>
		<script type="text/javascript" src="/resources/js/eweblib.js"></script>
		<script type="text/javascript" src="/resources/js/json2.js"></script>
		<script type="text/javascript" src="/resources/js/ams.js"></script>
		<script type="text/javascript" src="/resources/js/validation.js"></script>
		<script type="text/javascript" src="/resources/js/easyui-lang-zh_CN.js"></script>
		
</head>

<%
	if (session.getValue("userName") == null) {
		String url = request.getServerName();
		response.sendRedirect("/login.jsp");
	}
%>

<%
	String userRoleName = null;
	if (session.getAttribute("roleName") != null) {
		userRoleName = session.getAttribute("roleName").toString();
	}
	

%>

<body onResize="resizeTabAndGrid();">

	<%@ include file="pages/head.jsp"%>


	<div class="left" >
		<div data-options="region:'west',split:true" style="width: 250px; height:800px">
			<div class="easyui-accordion left-accordion" id="accordion" data-options="multiple:false,animate:false" style="width: 200px;">

				<div title="公告管理" style="height:150px;" data-options="iconCls:'icon-notice'">
	
					<ul>
						<li>
							<a href="?p=notice/add">新增</a>
						</li>
						<li>
							<a href="?p=notice/list">公告管理</a>
						</li>
					</ul>
				</div>
				<div title="考勤管理" data-options="iconCls:'icon-attendance'" style="overflow: auto;height:150px;">
				
					<ul>
						<li>
							<a href="?p=attendance/list&a=1">考勤管理</a>
						</li>						
					</ul>
				</div>
				
				<div title="图片管理" data-options="iconCls:'icon-img'" style="overflow: auto;height:150px;">			
					<ul>					
						<li>
							<a href="?p=pic/list&a=2">图片管理</a>
						</li>
					</ul>
				</div>
				<div title="任务管理"  data-options="iconCls:'icon-task'" style="height:150px;">
				
					<ul>
						<li>
							<a href="?p=task/import&a=3">任务导入</a>
						</li>
						
						<li>
							<a href="?p=task/list&a=3">任务管理</a>
						</li>
						
					</ul>
				</div>
				<div title="日报管理"  data-options="iconCls:'icon-dailyreport'" style="height:150px;">
				
					<ul>
					
						<li>
							<a href="?p=report/list&a=4"> 日报管理</a>
						</li>
					</ul>
				</div>
				<div title="备忘录管理"  data-options="iconCls:'icon-reminder'" style="height:150px;">
				
					
				</div>
				<div title="项目管理" style="height:150px;" data-options="iconCls:'icon-project'">
				
					<ul>
						<li>
							<a href="?p=project/list&a=6">项目管理</a>
						</li>
						<li>
							<a href="?p=team/list&a=6">施工队管理</a>
						</li>
						
					</ul>
					
				</div>
				
					<div title="客户管理" style="height:150px;" data-options="iconCls:'icon-customer'">
				
					<ul>
						<li>
							<a href="?p=customer/list&a=7">客户管理</a>
						</li>
					</ul>
					
				</div>
				<div title="用户管理" style="height:150px;" data-options="iconCls:'icon-user'">
				
					<ul>
						<li>
							<a href="?p=user/add&a=8">新增用户</a>
						</li>
						<li>
							<a href="?p=user/list&a=8">用户管理</a>
						</li>
					
					</ul>
				</div>
				
				<div title="工资管理" style="height:150px;" data-options="iconCls:'icon-salary'">
				
					<ul>

						<li>
							<a href="?p=salary/list&a=9">工资管理</a>
						</li>
					</ul>
				</div>
				<div title="系统设置" style="height:300px;" data-options="iconCls:'icon-sys'">
				
					<ul>
						<li>
							<a href="?p=sys/usertypelist&a=10">员工类型管理</a>
						</li>
						<li>
							<a href="?p=sys/userlevellist&a=10">员工级别管理</a>
						</li>
						<li>
							<a href="?p=sys/grouplist&a=10">角色管理</a>
						</li>
						<li>
							<a href="?p=department/list&a=10">部门管理</a>
						</li>
						<li>
							<a href="?p=log/list&a=10">日志查询</a>
						</li>
						<li>
							<a href="?p=sys/menu&a=10">菜单顺序管理</a>
						</li>
					</ul>
				</div>
			</div>
		</div>
	</div>

	<div class="right">
		<div id="content-right-info" style="color: red; font-size: 18px; font-weight: bold;"></div>
		<div id="content-right" style="margin-top: 5px; height: auto; overflow: hidden">
			<% 
		               String pagePath = request.getParameter("p"); 
		               String accindex = request.getParameter("a"); 
		               if(pagePath == null){	                
		                       pagePath = "main";	                   
		               }
		               if(pagePath != null && pagePath!="null" && pagePath!=""){
		                   pageContext.setAttribute("pagePath","pages/"+pagePath+".jsp");                           
		            
		            %>
			<jsp:include page="${pagePath}" />

			<% } %>
		</div>
	</div>



	<%@ include file="pages/bottom.jsp"%>

	<div style="display: none;">
		<div id="detailWindow">
			<span id="detailspan" height="200" style="margin-top: 20px;"></span>
		</div>
	</div>
	
	 <div id="dlg"  title="详情" data-options="iconCls:'icon-save'" style="width:500px;padding:10px;">
	 
     </div>

	<script type="text/javascript">
        var roleName = "<%=userRoleName%>";
        var pagePath = "<%=pagePath%>";
      
        $(document).ready(function(){
            //初始化页面最小宽度  
           // initDataGridEvent();
             var title ="";
            if(pagePath.startWith("notice")){
            	title = "公告管理";
            }else if(pagePath.startWith("user")){
            	title = "用户管理";
            }else if(pagePath.startWith("log") || pagePath.startWith("sys") || pagePath.startWith("department")){
            	title = "系统设置";
            }else if(pagePath.startWith("attendance")){
            	title = "考勤管理";
            }else if(pagePath.startWith("task")){
            	title = "任务管理";
            }else if(pagePath.startWith("reminder")){
            	title = "备忘录管理";
            }else if(pagePath.startWith("project") || pagePath.startWith("team")){
            	title = "项目管理";
            }else if(pagePath.startWith("customer")){
            	title = "客户管理";
            }else if(pagePath.startWith("report")){
            	title = "日报管理";
            }else if(pagePath.startWith("pic")){
            	title = "图片管理";
            }else if(pagePath.startWith("salary")){
            	title = "工资管理";
            }
        	$('#accordion').accordion('select', title); 

           
            
        });

   
   

     </script>


</body>
</html>