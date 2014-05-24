<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="Expires" content="0">
<meta http-equiv="kiben" content="no-cache">
<meta name="keywords" content="新鹰建筑工程考勤管理系统" />
<meta name="description" content="新鹰建筑工程考勤管理系统" />
<title>新鹰建筑工程考勤管理系统</title>
<link rel="stylesheet" type="text/css" href="/resources/css/alogin.css" />
<link rel="stylesheet" type="text/css" href="/resources/css/easyui.css">
<script src="/resources/js/jquery-1.11.1.min.js"></script>
<script src="/resources/js/user/login.js"></script>
<script type="text/javascript" src="/resources/js/jquery.easyui.min.js"></script>
<script type="text/javascript" src="/resources/js/eweblib.js"></script>
<script type="text/javascript" src="/resources/js/validation.js"></script>
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
#form-login .div_input_login {
	width: 150px;
}
</style>

<body >

	<div class="view-login login_back">
		<form id="login-form" novalidate>
		<div class="Main">
			<ul>
				<li class="top"></li>
				<li class="top2"></li>
				<li class="topA"></li>
				<li class="topB"><span><h1>新鹰考勤管理系统</h1></span></li>
				<li class="topC"></li>
				<li class="topD">
					<ul class="login">
						<li><span class="left">用户名：</span> <span style=""> <input name="userName" required id="userName" class="txt easyui-validatebox" type="text"   missingMessage="请输入用户名" />

						</span></li>
						<li><span class="left">密码：</span> <span style=""> <input name="password"  required id="password"   class="txt easyui-validatebox"  missingMessage="请输入密码"   type="password" />
						</span></li>
						<li><span class="left">验证码: </span> 
						 <span>
						 	<input  name="imgCode" id="" class="txt_shot easyui-validatebox" type="text" deltaX="50" size="15" style="_height:30px; " validType="unnormal" required missingMessage="请输入验证码"/>
						 </span>
                                <span><a class="verification"><img style="width:63px; height:23px; cursor: pointer; margin-top:5px;" id="randomcode" src="/ams/user/img.do" onclick="changeImage();" ></a> </span>              
                                <script type="text/javascript">
                                    function changeImage(){
                                         $("#randomcode").attr("src", "/ams/user/img.do?_id=" + +Math.random());
                                    }
                                </script>
						</li>


					</ul>
				</li>
				<li class="topE"></li>
				<li class="middle_A"></li>
				<li class="middle_B"></li>
				<li class="middle_C"><input type="submit" value="登录" style="margin-left:50px;"/></li>
				<li class="middle_D"></li>
				<li class="bottom_A"></li>

			</ul>
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