<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>


	<div class="head">
		<div class="head-left"> <h1><a class="head_logo" href="index.jsp">新鹰考勤管理系统</a></h1></div>
		
		<div class="nav">
            <div class="user" style="float:left;margin-left:10px">	
            	<a class="info_done" href="#" onclick="logout(); return false;"></a>
            	<label>欢迎: <% out.print(session.getAttribute("userName")); %></label>
            </div>
            <div style="float:right;margin-right:15px;margin-top:5px;color:gray;">
                <a href="javascript:void(0)" onclick="loadHomePage();" title="返回首页"><img src="/resources/images/title_icon_home.png">&nbsp;<b>返回首页</b></a>&nbsp;&nbsp;&nbsp;
                <a href="javascript:void(0)" onclick="changePass();" title="修改密码"><img src="/resources/images/title_icon_help.png">&nbsp;<b>修改密码</b></a>&nbsp;&nbsp;&nbsp;
                <a href="javascript:void(0)" onclick="loginOut();" title="退出系统"><img src="/resources/images/title_icon_quit.png">&nbsp;<b>退出系统</b></a>
            </div>
        </div>

	</div>
