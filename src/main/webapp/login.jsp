<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="Expires" content="0">
<meta http-equiv="kiben" content="no-cache">
<meta name="keywords" content="新鹰建筑工程考勤管理系统"/>
<meta name="description" content="新鹰建筑工程考勤管理系统"/>
<title>新鹰建筑工程考勤管理系统</title>
<link rel="stylesheet" type="text/css" href="/resources/css/easyui.css">
<link rel="stylesheet" type="text/css" href="/resources/css/icon.css">
<link rel="stylesheet" type="text/css" href="/resources/css/style.css" />
<link rel="stylesheet" type="text/css" href="/resources/css/common.css" />

<script src="/resources/js/jquery-1.11.1.min.js"></script>
<script src="/resources/js/user/login.js"></script>
<script type="text/javascript" src="/resources/js/jquery.easyui.min.js"></script>
<script type="text/javascript" src="/resources/js/eweblib.js"></script>
<script type="text/javascript" src="/resources/js/validation.js"></script>
<script type="text/javascript" src="/resources/js/ams.js"></script>
</head>
<%
    if(session.getValue("userName")!=null){
        response.sendRedirect("index.jsp");
    }
%>
<script type="text/javascript">
window.onload = function(){
	document.getElementById("userName").focus();
}

</script>


<style>
	#form-login .div_input_login{
		width : 150px;
	}
</style>

<body class="view-login login_back" > 
<div class="container">

    <form action="/ams/user/login.do" method="post" novalidate id="login-form">
           <div class="login_title"></div>
           <div id="form-login" class="k-edit-form-container">
                <label style="width:100px;">用户名：</label><input name="userName" id="userName" class="div_input_login easyui-validatebox" type="text" size="15" value=""    missingMessage="请输入用户名或手机号码" />
                <p></p>
                <label style="width:100px;">密码：</label><input name="password" id="password"  style="margin-left: 16px;" class="div_input_login easyui-validatebox" data-bind="value: password"   missingMessage="请输入密码"   type="password" size="15" />
                <p></p>
                <input type="submit" class="btn-submitx text_indent60" value="登录" style="margin-left:60px;" />
           </div>
 
    </form>
</div>
    
    <noscript>警告！为正确操作管理后台，JavaScript 必须启用。</noscript>    
        <script type="text/javascript">
	    $(document).ready(function(){
	    	
	    });
	    $("#password").click(function(){
	        if($(this).val()=="请输入密码"){
	            $(this).val("");
	            $(this).css("color","#000");
	            $(this).hide();
	            $("#passwordw").show();
	            document.getElementById("passwordw").focus();
	        }
	    });
	    $("#password").focus(function(){
            if($(this).val()=="请输入密码"){
                $(this).val("");
                $(this).css("color","#000");
                $(this).hide();
                $("#passwordw").show();
                document.getElementById("passwordw").focus();
            }
        });
	    $("#userName").click(function(){
	        if($(this).val()=="请输入用户名或手机号"){
	            $(this).val("");
	            $(this).css("color","#000");
	        }
	    });
	    $("#userName").focus(function(){
            if($(this).val()=="请输入用户名或手机号"){
                $(this).val("");
                $(this).css("color","#000");
            }
        });
    </script>
    
</body>
</html>