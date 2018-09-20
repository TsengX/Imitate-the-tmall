<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isELIgnored="false"%>

<title>确认支付</title>
<link rel="stylesheet" type="text/css" href="css/fore/pay.css">

<div class="aliPayPageDiv">
    <div class="aliPayPageLogo">
        <img src="./img/site/simpleLogo.png" class="pull-left">
        <div style="clear: both"></div>
    </div>
    <div>
        <span class="confirmMoneyText">扫一扫付款（元）</span>
        <div class="confirmMoney">
            ￥<fmt:formatNumber type="number" value="${param.total}" minFractionDigits="2"/>
        </div>
    </div>
    <div>
        <img class="aliPayImg" src="./img/site/alipay2wei.png">
    </div>
    <div>
        <a href="forepayed?oid=${param.oid}&total=${param.total}">
            <button class="btn_confirmPay">确认支付</button>
        </a>
    </div>
</div>
