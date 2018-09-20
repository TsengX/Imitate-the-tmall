//公共函数
// 千进制格式化金额函数
function formatMoney(num) {
    num = num.toString().replace(/\$|\,/g, '');
    if (isNaN(num))
        num = "0";
    sign = (num == (num = Math.abs(num)));
    num = Math.floor(num * 100 + 0.50000000001);
    cents = num % 100;
    num = Math.floor(num / 100).toString();
    if (cents < 10)
        cents = "0" + cents;
    for (var i = 0; i < Math.floor((num.length - (1 + i)) / 3); i++)
        num = num.substring(0, num.length - (4 * i + 3)) + ',' +
            num.substring(num.length - (4 * i + 3));
    return (((sign) ? '' : '-') + num + '.' + cents);
}

//如果有商品被选中,结算按钮的颜色变为红, 可点击
function syncCreateOrderButton() {
    var selectAny = false;
    $(".cartProductItemIfSelected").each(function () {
        if ("selectit" == $(this).attr("selectit")) {
            selectAny = true;
        }
    });
    if (selectAny) {
        $("button.createOrderButton").css("background-color", "#FF0036");
        $("button.createOrderButton").css("border-color", "#FF0036");
        $("button.createOrderButton").removeAttr("disabled");
    }
    else {
        $("button.createOrderButton").css("background-color", "#AAAAAA");
        $("button.createOrderButton").css("border-color", "#AAA");
        $("button.createOrderButton").attr("disabled", "disabled");
    }
}
//同步"全选"状态
function syncSelect() {
    var selectAll = true;
    $(".cartProductItemIfSelected").each(function () {
        if ("false" == $(this).attr("selectit")) {
            selectAll = false;
        }
    });
    if (selectAll)
        $("img.selectAllItem").attr("src", "./img/site/cartSelected.png");
    else
        $("img.selectAllItem").attr("src", "./img/site/cartNotSelected.png");
}
//计算选中的商品总数，以及总价格
function calcCartSumPriceAndNumber() {
    var sum = 0;
    var totalNumber = 0;
    $("img.cartProductItemIfSelected[selectit='selectit']").each(function () {
        var oiid = $(this).attr("oiid");
        var price = $(".cartProductItemSmallSumPrice[oiid=" + oiid + "]").text();
        price = price.replace(/,/g, "");
        price = price.replace(/￥/g, "");
        sum += new Number(price);
        var num = $(".orderItemNumberSetting[oiid=" + oiid + "]").val();
        totalNumber += new Number(num);
    });
    $("span.cartSumPrice").html("￥" + formatMoney(sum));
    $("span.cartTitlePrice").html("￥" + formatMoney(sum));
    $("span.cartSumNumber").html(totalNumber);
}

function syncPrice(pid, num, price) {
    $(".orderItemNumberSetting[pid=" + pid + "]").val(num);
    var cartProductItemSmallSumPrice = formatMoney(num * price);
    $(".cartProductItemSmallSumPrice[pid=" + pid + "]").html("￥" + cartProductItemSmallSumPrice);
    calcCartSumPriceAndNumber();

    var page = "forechangeOrderItem";
    $.post(
        page,
        {"pid":pid,"number":num},
        function(result){
            if("success"!=result) {
                location.href="login.jsp";
            }
        }
    );
}

$(function () {

    //事件监听
    //选中一种商品
    $("img.cartProductItemIfSelected").click(function () {
        var selectit = $(this).attr("selectit");
        //如果已经选中, 再次点击就变为未选择
        if ("selectit" == selectit) {
            $(this).attr("src", "./img/site/cartNotSelected.png");
            $(this).attr("selectit", "false")
            $(this).parents("tr.cartProductItemTR").css("background-color", "#fff");
        }//如果没选中, 再次点击变为选中
        else {
            $(this).attr("src", "./img/site/cartSelected.png");
            $(this).attr("selectit", "selectit")
            $(this).parents("tr.cartProductItemTR").css("background-color", "#FFF8E1");
        }
        syncSelect();//同步"全选"状态
        syncCreateOrderButton();  //结算按钮的样式
        calcCartSumPriceAndNumber();  //计算商品总数,总价格
    });
    //商品全选
    $("img.selectAllItem").click(function () {
        var selectit = $(this).attr("selectit")
        if ("selectit" == selectit) {
            $("img.selectAllItem").attr("src", "./img/site/cartNotSelected.png");
            $("img.selectAllItem").attr("selectit", "false")
            $(".cartProductItemIfSelected").each(function () {
                $(this).attr("src", "./img/site/cartNotSelected.png");
                $(this).attr("selectit", "false");
                $(this).parents("tr.cartProductItemTR").css("background-color", "#fff");
            });
        }
        else {
            $("img.selectAllItem").attr("src", "./img/site/cartSelected.png");
            $("img.selectAllItem").attr("selectit", "selectit")
            $(".cartProductItemIfSelected").each(function () {
                $(this).attr("src", "./img/site/cartSelected.png");
                $(this).attr("selectit", "selectit");
                $(this).parents("tr.cartProductItemTR").css("background-color", "#FFF8E1");
            });
        }
        syncCreateOrderButton();
        calcCartSumPriceAndNumber();
    });
    //加
    $(".numberPlus").click(function () {
        var pid = $(this).attr("pid");
        var stock = $("span.orderItemStock[pid=" + pid + "]").text();
        var price = $("span.orderItemPromotePrice[pid=" + pid + "]").text();
        var num = $(".orderItemNumberSetting[pid=" + pid + "]").val();
        num++;
        if (num > stock)
            num = stock;
        syncPrice(pid, num, price);
    });
    //减
    $(".numberMinus").click(function () {
        var pid = $(this).attr("pid");
        var stock = $("span.orderItemStock[pid=" + pid + "]").text();
        var price = $("span.orderItemPromotePrice[pid=" + pid + "]").text();
        var num = $(".orderItemNumberSetting[pid=" + pid + "]").val();
        --num;
        if (num <= 0)
            num = 1;
        syncPrice(pid, num, price);
    });

    //手动更改商品数量
    $(".orderItemNumberSetting").keyup(function () {
        var pid = $(this).attr("pid");
        var stock = $("span.orderItemStock[pid=" + pid + "]").text();
        var price = $("span.orderItemPromotePrice[pid=" + pid + "]").text();
        var num = $(".orderItemNumberSetting[pid=" + pid + "]").val();
        num = parseInt(num);
        if (isNaN(num))
            num = 1;
        if (num <= 0)
            num = 1;
        if (num > stock)
            num = stock;
        syncPrice(pid, num, price);
    });
    // 删除订单监听
    $("a.deleteOrderItem").click(function(){
        deleteOrderItem = false;
        var oiid = $(this).attr("oiid");
        deleteOrderItemid = oiid;
        $("#deleteConfirmModal").modal('show');
    });

    $("button.deleteConfirmButton").click(function(){
        deleteOrderItem = true;
        $("#deleteConfirmModal").modal('hide');
    });

    $("#deleteConfirmModal").on('hidden.bs.modal',function(e) {
        if(deleteOrderItem) {
            var page = "foredeleteOrderItem";
            $.post(
                page,
                {"oiid":deleteOrderItemid},
                function(result){
                    if("success"==result){
                        $("tr.cartProductItemTR[oiid=" + deleteOrderItemid + "]").hide();
                    }
                    else {
                        location.href = "login.jsp";
                    }
                }
            );
        }
    })

    // 结算按钮监听
    $("button.createOrderButton").click(function(){
        var params = "";
        $(".cartProductItemIfSelected").each(function(){
            if("selectit"==$(this).attr("selectit")){
                var oiid = $(this).attr("oiid");
                params += "&oiid=" + oiid;
            }
        });
        params = params.substring(1);
        location.href="forebuy?"+params;

    });

})