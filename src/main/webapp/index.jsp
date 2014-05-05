<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<meta name="keywords" content="新鹰建筑工程考勤管理系统"/>
<meta name="description" content="新鹰建筑工程考勤管理系统"/>
<title>新鹰建筑工程考勤管理系统</title>
<link rel="stylesheet" type="text/css" href="/resources/css/easyui.css" />

<script type="text/javascript" src="/resources/js/jquery-1.11.1.min.js"></script>
<script type="text/javascript" src="/resources/js/eweblib.js"></script>
<script type="text/javascript" src="/resources/js/json2.js"></script>
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

<body onResize="resizeder();">
     <div class="head">
        <%@ include file="pages/head.jsp" %>
     </div>
     <div class="center">
         <div class="left"><%@ include file="pages/left.jsp" %></div>
         <div class="right" >
            <div id="content-right-info" style="color:red; font-size:18px;font-weight:bold;"></div>
            <div id="content-right" style="margin-top:5px; height:auto; overflow:hidden">
	            <% 
	               String pagePath = request.getParameter("p"); 
	            
	               if(pagePath == null){	                
	                       pagePath = "main";	                   
	               }
	               if(pagePath != null && pagePath!="null" && pagePath!=""){
	                   pageContext.setAttribute("pagePath","pages/"+pagePath+".jsp");                           
	            
	            %>
	                <jsp:include page="${pagePath}" />
	            
	            <% } %>
            </div>
            <div id="remotePage"  class="remotePage" style="display:none;"></div>
             <div id="remotePageWindow"  style="display:none; overflow-y: scroll;"></div>
         </div>
         <%@ include file="pages/bottom.jsp" %>
		<div class="handle_events" >
		   <div class="handle_events_title">待处理事项</div>
		   <div class="handle_events_text">
		       <div id="tips"></div>
               <div class="next_info"><a onclick="getNextmsg();">下一条</a></div>
		   </div>
		</div>
	</div>
	
	<div style="display:none;">
		<div id="detailWindow"  >
			<span id="detailspan" height="200" style="margin-top:20px;"></span>
		</div>
	</div>
	
      <script type="text/javascript">
        var roleName = "<%=userRoleName%>";
        var pagePath = "<%=pagePath%>";
        
      
        $(document).ready(function(){
            //初始化页面最小宽度
            $("body").css("min-width","1250px");
  
            initDataGridEvent();
        });

        function  resizeder(){
            resizeTabAndGrid();
            $("body").css("min-width","1250px");
        }
        
    

     </script>

	 
</body>
</html>