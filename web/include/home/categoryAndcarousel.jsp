<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isELIgnored="false"%>

<script>
$(function(){
    // 左导航左边的留白
    var left = $("div#carousel-of-product").offset().left;
    $("div.categoryMenu").css("left",left-20);
    $("div.categoryWithCarousel div.head").css("margin-left",left);
    $("div.productsAsideCategorys").css("left",left-20);
});
</script>

<div class="categoryWithCarousel">
    <!-- 上导航,天猫国际,天猫超市... -->
    <div class="headbar show1">
        <div class="head">
            <span class="glyphicon glyphicon-th-list"></span>
            <span>商品分类</span>
        </div>
        <div class="rightMenu">
            <span><a href="#nowhere"><img src="./img/site/tmall_shop.png"></a></span>
            <span><a href="#nowhere"><img src="./img/site/tmall_international.png"></a></span>
            <!-- 天猫超市右边的分类 -->
            <c:forEach items="${cs}" var="c" varStatus="st">
                <c:if test="${st.count<=4}">
                    <span>
                        <a href="forecategory?cid=${c.id}">
                            ${c.name}
                        </a>
                    </span>
                </c:if>
            </c:forEach>
        </div>

    </div>
<!-- 左导航,17类 -->
<%@include file="categoryMenu.jsp" %>
<!-- 左边导航:hover -->
<%@include file="productsAsideCategorys.jsp" %>
<!-- 轮播 -->
<%@include file="carousel.jsp" %>
    
<div class="carouselBackgroundDiv"></div>

</div>