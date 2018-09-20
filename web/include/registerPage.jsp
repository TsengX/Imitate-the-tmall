<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isELIgnored="false"%>
<!-- 注册表单 -->
<script>
$(function(){
    <c:if test="${!empty msg}">
        $("span.errorMessage").html("${msg}");
        $("div.registerErrorMessageDiv").css("visibility","visible");
    </c:if>
});
</script>
<link rel="stylesheet" type="text/css" href="css/fore/registerPage.css">
<form method="post" action="foreregister" class="registerForm">
	<div class="registerErrorMessageDiv">
		<div class="alert alert-danger" role="alert">
			<button type="button" class="close" data-dismiss="alert" aria-label="Close"></button>
			<span class="errorMessage"></span>
		</div>
	</div>
    <div class="registerDiv">
        <table align="center" class="registerTable">
            <tbody>
                <tr>
                    <td class="registerTip registerTableLeftTD">设置会员名</td>
                    <td></td>
                </tr>
                <tr>
                    <td class="registerTableLeftTD">会员名/登录名</td>
                    <td class="registerTableRightTD">
                        <input placeholder="会员名一旦设置成功，无法修改" name="name" id="name">
                    </td>
                </tr>
                <tr>
                    <td class="registerTip registerTableLeftTD">设置登陆密码</td>
                    <td class="registerTableRightTD">登陆时验证，保护账号信息</td>
                </tr>    
                <tr>
                    <td class="registerTableLeftTD">登陆密码</td>
                    <td class="registerTableRightTD">
                        <input type="password" placeholder="设置你的登陆密码" name="password" id="password">
                    </td>
                </tr>
                <tr>
                    <td class="registerTableLeftTD">密码确认</td>
                    <td class="registerTableRightTD">
                        <input type="password" placeholder="请再次输入你的密码" id="repeatpassword">
                    </td>
                </tr>
                <tr>
                    <td class="registerButtonTD" colspan="2">
                        <button type="submit">提   交</button>
                    </td>
                </tr>            
            </tbody>
        </table>
    </div>
 </form>
