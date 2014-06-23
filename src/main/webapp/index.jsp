<%@page import="org.springframework.web.context.support.WebApplicationContextUtils"%>
<%@page import="com.ams.service.impl.UserServiceImpl"%>
<%@page import="com.ams.service.IUserService"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.eweblib.cfg.ConfigManager" %>
<%@ page import="com.ams.bean.Menu" %>
<%@ page import="com.ams.bean.MenuItem" %>
<%@ page import="com.ams.*" %>
<%@ page import="java.lang.*" %>
<%@ page import="java.util.*" %>

<%@ page import="org.springframework.web.context.WebApplicationContext" %>

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
		<script type="text/javascript" src="/resources/js/datagrid-detailview.js"></script>
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
	
	WebApplicationContext ctx = WebApplicationContextUtils.getRequiredWebApplicationContext(getServletConfig().getServletContext());
	IUserService us = (UserServiceImpl)ctx.getBean("userService");

%>

<body onResize="resizeTabAndGrid();">

	<%@ include file="pages/head.jsp"%>


	<div class="left" >
		<div data-options="region:'west',split:true" style="width: 250px; height:800px">
			<div class="easyui-accordion left-accordion" id="accordion" data-options="multiple:false,animate:false" style="width: 200px;">
			
			
			   <%
			   	List<Menu> menuList = (List<Menu>) us.getMenuList();
			   
			    for(Menu m: menuList){
			    	out.println("<div title=\"" + m.getTitle() + "\" style=\"" + m.getStyle() + "\" data-options=\"" +m.getDataOptions() + "\">");
			    	out.println("<ul>");
			    	 
			    	List<MenuItem> itemList = m.getList();
			    	
			    	for(MenuItem item: itemList){
			    		out.println("<li class='accordion_li'>");
			    		out.println("<a href=\"" + item.getHref() + "\">" + item.getTitle() + "</a>");
			    		out.println("</li>");
			    	}
		
			    	
			    	out.println("</ul>");
			    	out.println("</div>");
			    }
			   
			   %>
   

				
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
	
	 <div id="dlg"  title="详情" data-options="iconCls:'icon-save'" style="width:500px;height:250px;padding:10px;">
	 
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
            var acc = $('#accordion').accordion('getPanel', title); 
            
            if(acc == null && pagePath!="main"){
            	window.location.href="index.jsp";
            }else{
        		$('#accordion').accordion('select', title); 
            }

            $(".accordion_li").show();
           
            
        });

   
   

     </script>


</body>
</html>