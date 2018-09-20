<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isELIgnored="false"%>

<div class="searchOutDiv">
    <a href="${contentPath}">
        <img class="logo" src="./img/site/logo.gif">
    </a>
    <form action="foresearch" method="post" >
        <div class="searchDiv">
            <input type="text" name="keyword" value="${param.keyword}" placeholder="时尚男鞋  太阳镜">
            <button class="searchButton" type="submit">搜索</button>
            <div class="searchBelow">
                <c:forEach items="${cs}" var="c" varStatus="st">
                    <c:if test="${st.count>=5 and st.count<=8}">
                        <span>
                            <a href="forecategory?cid=${c.id}">
                                ${c.name}
                            </a>
                            <c:if test="${st.count!=8}">
                                <span>|</span>
                            </c:if>
                        </span>
                    </c:if>
                </c:forEach>
            </div>

        </div>
    </form>
</div>