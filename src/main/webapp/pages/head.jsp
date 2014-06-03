<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>


	<div class="head">
		<div class="head-left"><img style="padding-left:5px;" src="/resources/images/logo_new.png" height="91px;" width="91px;" align="top"/>
		
			<span style="margin-top: 15px; display: inline-block"><a class="head_logo" href="index.jsp">新鹰考勤管理系统</a></span>
		
		</div>
		
		<div class="nav">
            <div class="user" style="float:left;margin-left:10px">	
            	<a class="info_done" href="#" onclick="logout(); return false;"></a>
            	<label>欢迎: <% out.print(session.getAttribute("userName")); %></label>
            </div>
            <div style="float:right;margin-right:55px;margin-top:5px;color:gray;">
                <a href="/index.jsp" title="返回首页"><img style="border:0px;" src="/resources/images/title_icon_home.png">&nbsp;<b>返回首页</b></a>&nbsp;&nbsp;&nbsp;
                <a href="?p=/user/changepwd" title="修改密码"><img style="border:0px;" src="/resources/images/title_icon_help.png">&nbsp;<b>修改密码</b></a>&nbsp;&nbsp;&nbsp;
                <a href="/ams/user/logout.do" title="退出系统"><img style="border:0px;" src="/resources/images/title_icon_quit.png">&nbsp;<b>退出系统</b></a>
            </div>
        </div>

	</div>
