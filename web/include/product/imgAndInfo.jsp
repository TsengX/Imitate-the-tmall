<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8" isELIgnored="false"%>

<script>
$(function(){
    // 加入购物车按钮监听
    $(".addCartLink").click(function(){
        var page = "forecheckLogin";
        $.get(
            page,
            function(result){
                if("success"==result){
                    var pid = ${p.id};
                    var num = $(".productNumberSetting").val();
                    var addCartpage = "foreaddCart";
                    $.get(
                        addCartpage,
                        {"pid":pid,"num":num},
                        function(result){
                            if("success"==result){
                                $(".addCartButton").html("已加入购物车");
                                $(".addCartButton").attr("disabled","disabled");
                                $(".addCartButton").css("background-color","lightgray")
				                $(".addCartButton").css("border-color","lightgray")
                                $(".addCartButton").css("color","black")
                            }
                            else{

                            }
                        }
                    );
                }
                else{
                    $("#loginModal").modal('show');
                }
            }
        );
        return false;
    });
    //立即购买事件监听
    $(".buyLink").click(function(){
        var page = "forecheckLogin";
        $.get(
            page,
            function(result){
                if("success"==result){
                    var num = $(".productNumberSetting").val();
                    location.href = $(".buyLink").attr("href") + "&num=" + num;
                }
                else {
                    $("#loginModal").modal('show');
                }
            }
        );
        return false;
    });

    $(".loginSubmitButton").click(function(){
        var name = $("#name").val();
        var password = $("#password").val();
        if(0==name.length||0==password.length){
            $("span.errorMessage").html("请输入账号密码");
            $(".loginErrorMessageDiv").show();
            return false;
        }
        var page = "foreloginAjax";
        $.get(
            page,
            {"name":name,"password":password},
            function(result){
                if("success"==result){
                    location.reload();
                }
                else{
                    $("span.errorMessage").html("账号密码错误");
                    $(".loginErrorMessageDiv").show();
                }
            }
        );
        return true;
    });
});
</script>

<div class="imgAndInfo">

    <div class="imgInimgAndInfo">
        <img class="bigImg" src="img/productSingle/${p.firstProductImage.id}.jpg">
        <div class="smallImageDiv">
            <c:forEach items="${p.productSingleImages}" var="pi">
                <img class="smallImage" src="img/productSingle_small/${pi.id}.jpg" bigImageURL="./img/productSingle/${pi.id}.jpg">
            </c:forEach>
        </div>
        <div class="img4load hidden" ></div>
    </div>

    <div class="infoInimgAndInfo">
        <div class="productTitle">
            ${p.name}
        </div>
        <div class="productSubTitle">
            ${p.subTitle}
        </div>
        <div class="productPrice">
            <div class="juhuasuan">
                <span class="juhuasuanBig">聚划算</span>
                <span>此商品即将参加聚划算，<span class="juhuasuanTime">1天19小时</span>后开始，</span>
            </div>
            <div class="productPriceDiv">
                <div class="vouchersDiv">
                    <img src="./img/site/gouwujuan.png">
                    <span>全天猫实物商品通用</span>
                </div>
                <div class="originalDiv">
                    <span class="originalPriceDesc">价格</span>
                    <span class="originalPriceYuan">¥</span>
                    <span class="originalPrice">
                        <fmt:formatNumber type="number" value="${p.originalPrice}" minFractionDigits="2"/>
                    </span>
                </div>
                <div class="promotionDiv">
                    <span class="promotionPriceDesc">促销价 </span>
                    <span class="promotionPriceYuan">¥</span>
                    <span class="promotionPrice">
                        <fmt:formatNumber type="number" value="${p.promotePrice}" minFractionDigits="2"/>
                    </span>
                </div>
            </div>
        </div>
        <div class="productSaleAndReviewNumber">
            <div>销量<span class="redColor boldWord">${p.saleCount}</span></div>
            <div>累计评价<span class="redColor boldWord">${p.reviewCount}</span></div>
        </div>
        <div class="productNumber">
            <span>数量</span>
            <span>
                <span class="productNumberSettingSpan">
                    <input class="productNumberSetting" type="text" value="1">
                </span>
                <span class="arrow">
                    <a class="increaseNumber" href="#nowhere">
                        <span class="updown">
                            <img src="./img/site/increase.png">
                        </span>
                    </a>
                    <span class="updownMiddle"></span>
                    <a class="decreaseNumber" href="#nowhere">
                        <span class="updown">
                            <img src="./img/site/decrease.png">
                        </span>
                    </a>
                </span>件
            </span>
            <span>库存${p.stock}件</span>
        </div>
        <div class="serviceCommitment">
            <span class="serviceCommitmentDesc">服务承诺</span>
            <span class="serviceCommitmentLink">
                <a href="#nowhere">正品保证</a>
                <a href="#nowhere">极速退款</a>
                <a href="#nowhere">赠运费险</a>
                <a href="#nowhere">七天无理由退换</a>
            </span>
        </div>
        <div class="buyDiv">
            <a class="buyLink" href="forebuyone?pid=${p.id}"><button class="buyButton">立即购买</button></a>
            <a class="addCartLink" href="#nowhere">
                <button class="addCartButton">
                    <span class="glyphicon glyphicon-shopping-cart"></span>加入购物车
                </button>
            </a>
        </div>
    </div>
    <div style="clear:both"></div>
</div>
