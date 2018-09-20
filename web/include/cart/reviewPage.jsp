<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isELIgnored="false"%>

<title>评价</title>
<link rel="stylesheet" type="text/css" href="css/fore/review.css">

<div class="reviewDiv">
    <!-- 商品信息div -->
    <div class="reviewProductInfoDiv">
        <!-- 商品图片 -->
        <div class="reviewProductInfoImg">
            <img class="reviewProductImg" src="./img/productSingle/${p.firstProductImage.id}.jpg">
        </div>
        <div class="reviewProductInfoRightDiv">
            <div class="reviewProductInfoRightText">
                <a href="foreproduct?pid=${p.id}" target="_blank">${p.name}</a>
            </div>
            <!-- 商品信息 -->
            <table class="reviewProductInfoTable">
                <tbody>
                    <tr>
                        <td width="75px">价格:</td>
                        <td>
                            <span class="reviewProductInfoTablePrice">
                                ￥<fmt:formatNumber type="number" value="${p.originalPrice}"/>
                            </span> 元 </td>
                    </tr>
                    <tr>
                        <td>配送</td>
                        <td>快递: 0.00</td>
                    </tr>
                    <tr>
                        <td>月销量:</td>
                        <td>
                            <span class="reviewProductInfoTableSellNumber">${p.saleCount}</span> 件</td>
                    </tr>
                </tbody>
            </table>
            <div class="reviewProductInfoRightBelowDiv">
                <!-- 灯泡小图片 -->
                <span class="reviewProductInfoRightBelowImg">

                </span>
                <div class="reviewProductInfoRightBelowText">
                    现在查看的是您所购买商品的信息于<fmt:formatDate value="${o.createDate}" pattern="yyyy年MM月dd日"/>下单购买了此商品
                </div>
            </div>
        </div>
        <div style="clear: both"></div>
    </div>
    <!-- 评价部分 -->

    <div class="reviewStasticsDiv">
        <div class="reviewStatics-left">
            <div class="reviewStatics-left-top">

            </div>
            <div class="reviewStasticsLeft-content">
                累计评价
                <span class="reviewStastic-number">
                    ${p.reviewCount}
                </span>
            </div>
            <div class="reviewStastics-left-foot">

            </div>
        </div>
        <div class="reviewStastics-right">
            <div class="reviewStastics-right-empty"></div>
            <div class="reviewStastics-right-Foot"></div>
        </div>
    </div>

    <c:if test="${param.showonly==true}">
    <div class="reviewDivlistReviews">
        <c:forEach items="${reviews}" var="r">
            <div class="reviewDivlistReviewsEach">
                <div class="reviewDate"><fmt:formatDate value="${r.createDate}" pattern="yyyy-MM-dd "/></div>
                <div class="reviewContent">${r.content}</div>
                <div class="reviewUserInfo pull-right">${r.user.anonymousName}<span class="reviewUserInfoAnonymous">(匿名)</span></div>
            </div>
        </c:forEach>
    </div>
    </c:if>

    <!-- 进行评价div -->
    <c:if test="${param.showonly!=true}">
    <div class="makeReviewDiv">
        <form method="post" action="foredoreview">
            <div class="makeReviewText">其他买家，需要你的建议哦！</div>
            <table class="makeReviewTable">
                <tbody>
                    <tr>
                        <td class="makeReviewTableFirstTD">评价商品</td>
                        <td>
                            <textarea name="content"></textarea>
                        </td>
                    </tr>
                </tbody>
            </table>
            <div class="makeReviewButtonDiv">
                <input type="hidden" value="${o.id}" name="oid">
                <input type="hidden" value="${p.id}" name="pid">
                <button type="submit">提交评价</button>
            </div>
        </form>
    </div>
    </c:if>

</div>