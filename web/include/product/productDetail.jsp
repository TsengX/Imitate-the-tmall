<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isELIgnored="false"%>

<div class="productDetailDiv">

    <div class="productDetailTopPart">
        <a class="productDetailTopPartSelectedLink selected" href="#nowhere">商品详情</a>
        <a class="productDetailTopReviewLink" href="#nowhere">
            累计评价<span class="productDetailTopReviewLinkNumber">${p.reviewCount}</span>
        </a>
    </div>

    <div class="productParamterPart">
        <div class="productParamter"> 产品参数： </div>
        <div class="productParamterList">
            <c:forEach items="${pvs}" var="pv">
                <span>${pv.property.name}:  ${fn:substring(pv.value,0,10)}</span>
            </c:forEach>
        </div>
        <div style="clear:both"></div>
    </div>

    <div class="productDetailImagesPart">
        <c:forEach items="${p.productDetailImages}" var="pi">
            <img src="img/productDetail/${pi.id}.jpg">
        </c:forEach>
    </div>

</div>