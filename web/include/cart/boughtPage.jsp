<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isELIgnored="false"%>

<link rel="stylesheet" type="text/css" href="css/fore/bought.css">
<script src="js/fore/boughtPage.js"></script>
        

<div class="orderDiv">
    <div class="orderType">
        <div class="selectedOrderType">
            <a href="#nowhere" orderStatus="all">所有订单</a>
        </div>
        <div>
            <a href="#nowhere" orderStatus="waitPay">待付款</a>
        </div>
        <div>
            <a href="#nowhere" orderStatus="waitDelivery">待发货</a>
        </div>
        <div>
            <a href="#nowhere" orderStatus="waitConfirm">待收货</a>
        </div>
        <div>
            <a class="noRightborder" href="#nowhere" orderStatus="waitReview">待评价</a>
        </div>
        <div class="orderTypeLastOne">

        </div>
    </div>
    <div style="clear:both"></div>
    <div class="orderListTitle">
        <table class="orderListTitleTable">
            <thead>
                <tr>
                    <td>宝贝</td>
                    <td width="100px">单价</td>
                    <td width="100px">数量</td>
                    <td width="120px">实付款</td>
                    <td width="100px">交易操作</td>
                </tr>
            </thead>
        </table>
    </div>

    <div class="orderListItem">
        <c:forEach items="${os}" var="o">
            <table class="orderListItemTable" oid="${o.id}" orderStatus="${o.status}">
                <tbody>
                    <tr class="orderListItemFirstTR">
                        <td colspan="2">
                            <b>
                                <fmt:formatDate value="${o.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
                            </b>
                            <span>订单号: ${o.orderCode}</span>
                        </td>
                        <td colspan="2">
                            <img src="./img/site/orderItemTmall.png">天猫商场</td>
                        <td colspan="1">
                            <a class="wangwangLink" href="#nowhere">
                                <div class="orderItemWangWangGif"></div>
                            </a>
                        </td>
                        <td class="orderItemDeleteTD">
                            <a class="deleteOrderLink" href="#nowhere" oid="${o.id}">
                                <span class="orderListItemDelete glyphicon glyphicon-trash"></span>
                            </a>
                        </td>
                    </tr>
                    <c:forEach items="${o.orderItems}" var="oi" varStatus="st">
                        <tr class="orderItemProductInfoPartTR">
                            <td class="orderItemProductInfoPartTD">
                                <img class="productImg" src="img/productSingle_middle/${oi.product.firstProductImage.id}.jpg">
                            </td>
                            <td class="orderItemProductInfoPartTD">
                                <div class="orderListItemProductLinkOutDiv">
                                    <a href="foreproduct?pid=${oi.product.id}">${oi.product.name}</a>
                                    <div class="orderListItemProductLinkInnerDiv">
                                        <img title="支持信用卡支付" src="./img/site/creditcard.png">
                                        <img title="消费者保障服务,承诺7天退货" src="./img/site/7day.png">
                                        <img title="消费者保障服务,承诺如实描述" src="./img/site/promise.png">
                                    </div>
                                </div>
                            </td>
                            <td class="orderItemProductInfoPartTD" width="100px">
                                <div class="orderListItemProductOriginalPrice">
                                    ￥<fmt:formatNumber type="number" value="${oi.product.originalPrice}" minFractionDigits="2"/>
                                </div>
                                <div class="orderListItemProductPrice">
                                    ￥<fmt:formatNumber type="number" value="${oi.product.promotePrice}" minFractionDigits="2"/>
                                </div>
                            </td>
                            <c:if test="${st.count==1}">
                                <td class="orderListItemNumberTD orderItemOrderInfoPartTD" width="100px" valign="top" rowspan="${fn:length(o.orderItems)}">
                                    <span class="orderListItemNumber">${o.totalNumber}</span>
                                </td>
                                <td width="120px" valign="top" class="orderListItemProductRealPriceTD orderItemOrderInfoPartTD" rowspan="${fn:length(o.orderItems)}">
                                    <div class="orderListItemProductRealPrice">
                                        ￥<fmt:formatNumber  minFractionDigits="2"  maxFractionDigits="2" type="number" value="${o.total}"/>
                                    </div>
                                    <div class="orderListItemPriceWithTransport">(含运费：￥0.00)</div>
                                </td>
                                <td width="100px" valign="top" class="orderListItemButtonTD orderItemOrderInfoPartTD" rowspan="${fn:length(o.orderItems)}">
                                    <c:if test="${o.status=='waitConfirm'}">
                                        <a href="foreconfirmPay?oid=${o.id}">
                                            <button class="orderListItemConfirm">确认收货</button>
                                        </a>
                                    </c:if>
                                    <c:if test="${o.status=='waitPay'}">
                                        <a href="alipay.jsp?oid=${o.id}&total=${o.total}">
                                            <button class="orderListItemConfirm">付款</button>
                                        </a>
                                    </c:if>
                                    <c:if test="${o.status=='waitDelivery'}">
                                                <span>待发货</span>
            <%--                                     <button class="btn btn-info btn-sm ask2delivery" link="admin_order_delivery?id=${o.id}">催卖家发货</button> --%>
                                                
                                    </c:if>
                                    <c:if test="${o.status=='waitReview'}">
                                        <a href="forereview?oid=${o.id}">
                                            <button class="orderListItemReview">评价</button>
                                        </a>
                                    </c:if>
                                </td>
                            </c:if>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </c:forEach>

    </div>
</div>
