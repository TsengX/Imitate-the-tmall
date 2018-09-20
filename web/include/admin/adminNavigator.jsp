<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
	
<div class="navitagorDiv">
	<div class="navbar navbar-default navbar-fixed-top navbar-inverse">
		<img class="pull-left" src = "img/site/tmallbuy.png"> 
		<a class="navbar-brand" href="#nowhere">天猫后台</a>
		<a class="navbar-brand" href="admin_category_list">分类管理</a>
        <a class="navbar-brand" href="admin_user_list">用户管理</a>
        <a class="navbar-brand" href="admin_order_list">订单管理</a>
        
        <c:if test="${!empty admin}">
        <a class="navbar-brand" href="#nowhere">${admin.name}</a>
        <a class="navbar-brand" href="back_logout">退出</a>
        </c:if>
        
	</div>
</div>