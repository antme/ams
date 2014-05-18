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
</head>

<%
	if (session.getValue("userName") == null) {
		String url = request.getServerName();
		response.sendRedirect("https://" + url + "/login.jsp");
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

				<div title="公告管理" style="">
					<div class="easyui-panel" style="padding: 5px; width: 150px; border-style: none;">
						<ul class="easyui-tree" data-options="url:'/menu/tree_notice.json',method:'get',animate:true,onClick:onNoticeClick"></ul>
					</div>
				</div>
				<div title="考勤管理" data-options="iconCls:'icon-ok'" style="overflow: auto; padding: 10px;">
					<div class="easyui-panel" style="padding: 5px; width: 150px; border-style: none;">
						<ul class="easyui-tree" data-options="url:'/menu/tree_attendance.json',method:'get',animate:true,onClick:onAttendanceClick"></ul>
					</div>
				
				</div>
				<div title="任务管理" style="padding: 10px;">
					<div class="easyui-panel" style="padding: 5px; width: 150px; border-style: none;">
						<ul class="easyui-tree" data-options="url:'/menu/tree_task.json',method:'get',animate:true,onClick:onNoticeClick"></ul>
					</div>
				</div>
				<div title="项目管理" style="padding: 10px;">
					<div class="easyui-panel" style="padding: 5px; width: 150px; border-style: none;">
						<ul class="easyui-tree" data-options="url:'/menu/tree_project.json',method:'get',animate:true,onClick:onProjectClick"></ul>
					</div>
				</div>
				<div title="用户管理" style="padding: 10px;">
					<div class="easyui-panel" style="padding: 5px; width: 150px; border-style: none;">
						<ul class="easyui-tree" data-options="url:'/menu/tree_user.json',method:'get',animate:true,onClick:onUserTreeClick"></ul>
					</div>
				</div>
				<div title="系统设置" style="padding: 10px;"></div>
				<div title="统计" style="padding: 10px;"></div>
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

	<script type="text/javascript">
        var roleName = "<%=userRoleName%>";
        var pagePath = "<%=pagePath%>";
        var index = <%=accindex%>;
      
        $(document).ready(function(){
            //初始化页面最小宽度  
            initDataGridEvent();
            
           	$('#accordion').accordion('select', index); 
            
        });

   
   

     </script>


</body>
</html>