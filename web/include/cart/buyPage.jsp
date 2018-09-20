<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isELIgnored="false"%>
<link rel="stylesheet" type="text/css" href="css/fore/buyPage.css">
<script>
//给买家留言,  有客户体验更好的方法
$(function(){
    $("img.leaveMessageImg").click(function(){
        $(this).hide();
        $(".leaveMessageTextareaSpan").show();
        $(".orderItemSumDiv").css("height","100px");
    });
});
</script>

<div class="buyPageDiv">
    <form action="forecreateOrder" method="post">
        <div class="buyFlow">
            <img class="simpleLogo" src="./img/site/simpleLogo.png" class="pull-left">
            <img src="./img/site/buyflow.png" class="pull-right">
            <div style="clear:both"></div>
        </div>
        <div class="address">
          <div class="addressTip">输入收货地址</div>
            <div>
                <table class="addressTable">
                    <tbody>
                        <tr>
                        <td class="firstColumn">详细地址<span class="redStar">*</span></td>
                        <td>
                            <textarea placeholder="建议您如实填写详细收货地址，例如接到名称，门牌好吗，楼层和房间号等信息" name="address"></textarea>
                        </td>
                    </tr>
                    <tr>
                        <td>邮政编码</td>
                        <td><input type="text" placeholder="如果您不清楚邮递区号，请填写000000" name="post"></td>
                    </tr>
                    <tr>
                        <td>收货人姓名<span class="redStar">*</span></td>
                        <td><input type="text" placeholder="长度不超过25个字符" name="receiver"></td>
                    </tr>
                    <tr>
                        <td>手机号码 <span class="redStar">*</span></td>
                        <td><input type="text" placeholder="请输入11位手机号码" name="mobile"></td>
                    </tr>
                    </tbody>
                </table>
            </div>
        </div>

        <div class="productList">
            <div class="productListTip">确认订单信息</div>
            <table class="productListTable">
                <thead>
                    <tr>
                        <th class="productListTableFirstColumn" colspan="2">
                            <img class="tmallbuy" src="./img/site/tmallbuy.png">
                            <a class="marketLink" href="#nowhere">店铺：天猫店铺</a>
                            <a class="wangwangLink" href="#nowhere"><span class="wangwangGif"></span></a>
                        </th>
                        <th>单价</th>
                        <th>数量</th>
                        <th>小计</th>
                        <th>配送方式</th>
                    </tr>
                    <tr class="rowborder">  <!-- 标题栏下的下划线 -->
                        <td colspan="2"></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                    </tr>
                </thead>
                <tbody class="productListTableTbody">
                    <c:forEach items="${ois}" var="oi" varStatus="st">
                        <tr class="orderItemTR">
                            <td class="orderItemFirstTD">
                                <img src="img/productSingle_middle/${oi.product.firstProductImage.id}.jpg" class="orderItemImg">
                            </td>
                            <td class="orderItemProductInfo">
                                <a class="orderItemProductLink" href="foreproduct?pid=${oi.product.id}">
                                    ${oi.product.name}
                                </a>
                                <img title="支持信用卡支付" src="./img/site/creditcard.png">
                                <img title="消费者保障服务,承诺7天退货" src="./img/site/7day.png">
                                <img title="消费者保障服务,承诺如实描述" src="./img/site/promise.png">
                            </td>
                            <td>
                                <span class="orderItemProductPrice">￥<fmt:formatNumber type="number" value="${oi.product.promotePrice}" minFractionDigits="2" /></span>
                            </td>
                            <td>
                                <span class="orderItemProductNumber">${oi.number}</span>
                            </td>
                            <td>
                                <span class="orderItemUnitSum">
                                    ￥<fmt:formatNumber type="number" value="${oi.number*oi.product.promotePrice}"/>
                                </span>
                            </td>
                            <c:if test="${st.count==1}">
                            <td class="orderItemLastTD" rowspan="5">
                                <label class="orderItemDeliveryLabel">
                                    <input type="radio" checked="checked" value="">
                                    普通配送
                                </label>
                                <select class="orderItemDeliverySelect">
                                    <option>快递 免邮费</option>
                                </select>
                            </td>
                            </c:if>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>

        <div class="orderItemSumDiv">
            <div class="orderItemSumDiv-left">
                <div class="leaveMessageText">给卖家留言:</div>
                <div>
                    <img src="http://how2j.cn/tmall/img/site/leaveMessage.png" class="leaveMessageImg">
                </div>
                <div class="leaveMessageTextareaSpan" style="display: none;">
                    <textarea class="leaveMessageTextarea" name="userMessage"></textarea>
                    <div>
                        <span>还可以输入200个字符</span>
                    </div>
                </div>
            </div>
            <span class="orderItemSumDiv-right">店铺合计(含运费): ￥<span><fmt:formatNumber type="number" value="${total}" minFractionDigits="2" /></span></span>
        </div>

        <div class="orderItemTotalSumDiv">
            <div class="pull-right">
                <span>实付款： </span>
                <span class="orderItemTotalSumSpan">￥<fmt:formatNumber type="number" value="${total}" minFractionDigits="2"/></span>
            </div>
        </div>

        <div class="submitOrderDiv">  
            <button class="submitOrderButton" type="submit">提交订单</button>
        </div>
    </form>
</div>