<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isELIgnored="false"%>

<div class="categoryMenu_position">
    <div class="categoryMenu">
        <c:forEach items="${cs}" var="c" varStatus="st">
            <div class="eachCategory" cid="${c.id}">
                <span class="glyphicon glyphicon-link"></span>
                <a href="forecategory?cid=${c.id}">
                    ${c.name}
                </a>
            </div>
        </c:forEach>
    </div>
</div>