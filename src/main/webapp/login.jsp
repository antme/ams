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
<script src="/resources/js/jquery-1.11.1.min.js"></script>
<script src="/resources/js/user/login.js"></script>
<script type="text/javascript" src="/resources/js/jquery.easyui.min.js"></script>
<script type="text/javascript" src="/resources/js/eweblib.js"></script>
<script type="text/javascript" src="resources/js/validation.js"></script>
<link rel="stylesheet" type="text/css" href="/resources/css/style.css" />

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


<body class="view-login login_back" > 

    <form action="/ecs/user/login.do" method="post" novalidate id="login-form">
        <div class="container">
           <div class="login_title"></div>
           <div id="form-login" class="k-edit-form-container">
                <input name="userName" id="userName" class="div_input_login easyui-validatebox" type="text" size="15" value=""    missingMessage="请输入用户名或手机号码" />
                <input name="password" id="password"  class="div_input_login2 easyui-validatebox" data-bind="value: password"   missingMessage="请输入密码"   type="password" size="15" />
                <input type="submit" class="btn-submitx text_indent60" value="登录" />
           </div>
        </div>
        
 
        
    </form>
    
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