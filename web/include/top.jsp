<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8" isELIgnored="false"%>


<div class="top">
    <div class="top_middle">
        <a href="${contextPath}">
            <span class="glyphicon glyphicon-home redColor"></span>
            天猫首页
        </a>
        <span>喵, 欢迎来到天猫</span>

        <c:if test="${!empty user}">
            <a href="login.jsp">${user.name}</a>
            <a href="forelogout">退出</a>
        </c:if>

        <c:if test="${empty user}">
            <a href="login.jsp">请登陆</a>
            <a href="register.jsp">免费注册</a>
        </c:if>

        <span class="pull-right">
            <a href="forebought">我的订单</a>
            <a href="forecart">
                <span class="glyphicon glyphicon-shopping-cart redColor"></span>
                购物车<strong>${cartTotalItemNumber}</strong>件</a>
        </span>
        
    </div>
    
</div>
