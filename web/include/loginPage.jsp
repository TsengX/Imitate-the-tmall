<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isELIgnored="false"%>
<link rel="stylesheet" type="text/css" href="css/fore/loginPage.css">
<script>
$(function(){
    //用来显示登录密码错误
    <c:if test="${!empty msg}">
        $("span.errorMessage").html("${msg}");
        $(".loginErrorMessageDiv").show();
    </c:if>
})
</script>

<div class="loginDiv">
    <div class="simpleLogo">
        <img src="./img/site/simpleLogo.png">
    </div>

    <div class="content">

        <img class="loginBackgroundImg" id="loginBackgroundImg" src="./img/site/loginBackground.jpg">

        <form class="loginForm" method="post" action="forelogin">
            <div class="loginSmallDiv" id="loginSmallDiv">
                <div class="loginErrorMessageDiv">
                    <div class="alert alert-danger">
                        <button type="button" class="close" data-dismiss="alert" aria-label="Close"></button>
                        <span class="errorMessage"></span>
                    </div>
                </div>
                <div class="login_acount_text">账户登录</div>
                <div class="loginInput">
                    <span class="loginInputIcon">
                        <label class="glyphicon glyphicon-user"></label>
                    </span>
                    <input type="text" name="name" id="name" placeholder="手机/会员名/邮箱">
                </div>
                <div class="loginInput">
                    <span class="loginInputIcon">
                        <label class="glyphicon glyphicon-lock"></label>
                    </span>
                    <input type="password" name="password" id="password" placeholder="密码">
                </div>
                <div class="loginButtonDiv">
                    <button class="btn btn-block redButton" type="submit">登录</button>
                </div>
                <div class="login-links">
                    <a class="notImplementLink" href="#nowhere">忘记登录密码</a>  
                    <a class="pull-right" href="register.jsp">免费注册</a>
                </div>        
            </div>
        </form>
    </div> 
</div>