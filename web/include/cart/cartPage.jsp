<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isELIgnored="false"%>

<title>购物车</title>
<link rel="stylesheet" type="text/css" href="css/fore/cartPage.css">
<script src="js/fore/cartPage.js"></script>


<div class="cartDiv">
    <!-- 小结算部分 -->
    <div class="cartTitle pull-right">
        <span>已选商品 (不含运费)</span>
        <span class="cartTitlePrice">￥0.00</span>
        <button class="createOrderButton" disabled="disabled">结算</button>
    </div>
    <!-- 购物车内商品列表 -->
    <div class="cartProductList">
        <table class="cartProductTable">
            <thead>
                <tr>
                    <th class="selectAndImage">
                        <img src="./img/site/cartNotSelected.png" class="selectAllItem" selectit="false">全选
                    </th>
                    <th>商品信息</th>
                    <th>单价</th>
                    <th>数量</th>
                    <th>金额</th>
                    <th class="operation">操作</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${ois}" var="oi">
                <!-- 第一件商品 -->
                <tr class="cartProductItemTR" oiid="${oi.id}">
                    <td>
                        <img class="cartProductItemIfSelected" src="./img/site/cartNotSelected.png" oiid="${oi.id}" selectit="false">
                        <a href="#nowhere" style="display:none">
                            <img src="./img/site/cartSelected.png">
                        </a>
                        <img class="cartProductImg" src="./img/productSingle_middle/${oi.product.firstProductImage.id}.jpg">
                    </td>
                    <td>
                        <div class="cartProductLinkOutDiv">
                            <a class="cartProductLink" href="foreproduct?pid=${oi.product.id}">${oi.product.name}</a>
                            <div class="cartProductLinkInnerDiv">
                                <img title="支持信用卡支付" src="./img/site/creditcard.png">
                                <img title="消费者保障服务,承诺7天退货" src="./img/site/7day.png">
                                <img title="消费者保障服务,承诺如实描述" src="./img/site/promise.png">
                            </div>
                        </div>
                    </td>
                    <td>
                        <span class="cartProductItemOringalPrice">￥${oi.product.originalPrice}</span>
                        <span class="cartProductItemPromotionPrice">￥${oi.product.promotePrice}</span>
                    </td>
                    <td>
                        <div class="cartProductChangeNumberDiv">
                            <span pid="${oi.product.id}" class="hidden orderItemStock ">${oi.product.stock}</span>
                            <span pid="${oi.product.id}" class="hidden orderItemPromotePrice">${oi.product.promotePrice}</span>
                            <a href="#nowhere" class="numberMinus" pid="${oi.product.id}">-</a>
                            <input value="${oi.number}" autocomplete="off" class="orderItemNumberSetting" oiid="${oi.id}" pid="${oi.product.id}">
                            <a href="#nowhere" class="numberPlus" pid="${oi.product.id}" stock="${oi.product.stock}">+</a>
                        </div>
                    </td>
                    <td>
                        <span class="cartProductItemSmallSumPrice" pid="${oi.product.id}" oiid="${oi.id}">
                            ￥<fmt:formatNumber type="number" value="${oi.product.promotePrice*oi.number}" minFractionDigits="2"/>
                        </span>
                    </td>
                    <td>
                        <a href="#nowhere" oiid="${oi.id}" class="deleteOrderItem">删除</a>
                    </td>
                </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>
    <!-- 尾部大结算按钮部分 -->
    <div class="cartFoot">
        <img class="selectAllItem" selectit="false" src="./img/site/cartNotSelected.png">
        <span>全选</span>
        <div class="pull-right">
            <span>已选商品
                <span class="cartSumNumber">0</span>件</span>
            <span>合计 (不含运费): </span>
            <span class="cartSumPrice">￥0.00</span>
            <button type="submit" class="createOrderButton" disabled="disabled">结算</button>
        </div>
    </div>
</div>