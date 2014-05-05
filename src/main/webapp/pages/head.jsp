<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>小猫安装平台</title>

</head>
<body>
	<div class="head">
		<a class="head_login" href="index.jsp"></a>
		<div class="login_info">
			<!-- <div class="info_photo"></div>-->
			<!-- <input type="button" class="info_done" /> -->
			<div class="info_user">
                <% if("MFC".equals(session.getAttribute("roleName"))){%><a style="color:#fff;" href="/context/小猫服务平台用户手册-厂商.doc" >用户手册</a><%}%>
                <% if("SP".equals(session.getAttribute("roleName"))){%><a style="color:#fff;" href="/context/小猫服务平台用户手册-服务商.doc">用户手册</a><%}%>
            </div>
			<div class="info_user">
                <% if("MFC".equals(session.getAttribute("roleName"))){%><a style="color:#fff;" target="_blank" href="context/mfc.html" >帮助文档</a><%}%>
                <% if("SP".equals(session.getAttribute("roleName"))){%><a style="color:#fff;" target="_blank" href="context/sp.html">帮助文档</a><%}%>
            </div>
			<a class="info_done" href="#" onclick="logout(); return false;"></a>
			<% if("MFC".equals(session.getAttribute("roleName")) || "SP".equals(session.getAttribute("roleName"))){%>
			<div class="info_has_info">
				<div>
					<a style="text-decoration:none" href="?p=message/receiver/list"><span class="info_has_info_title">您的消息</span> <label class="info_has_info_num" ></label></a>
				</div>
			</div>
			<% }%>
			<div class="info_user">
				<% if("MFC".equals(session.getAttribute("roleName"))){%><label>尊敬的厂商用户</label><a href="?p=web/mfc/mfcinfo" style="color:#ff6600"><% out.print(session.getAttribute("userName")); %></a><%}%>
				<% if("SP".equals(session.getAttribute("roleName"))){%><label>尊敬的服务商用户</label><a href="?p=web/sp/spinfo" style="color:#ff6600"><% out.print(session.getAttribute("userName")); %></a><%}%>
				<% if("ADMIN".equals(session.getAttribute("roleName"))){%><label>尊敬的管理员</label><% out.print(session.getAttribute("userName")); %><%}%>
				<% if("CUSTOMER_SERVICE".equals(session.getAttribute("roleName"))){%><label>尊敬的客服</label><% out.print(session.getAttribute("userName")); %><%}%>
				<% if("SUPPER_ADMIN".equals(session.getAttribute("roleName"))){%><label>尊敬的超级管理员</label><% out.print(session.getAttribute("userName")); %><%}%>
				<label>欢迎进入小猫平台</label>
			</div>
			
		</div>
	</div>

</body>
</html>