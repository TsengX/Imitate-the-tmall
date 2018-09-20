// categoryMenu.jsp
function showProductsAsideCategorys(cid){
    $("div.eachCategory[cid="+cid+"]").css("background-color","white");
    $("div.eachCategory[cid="+cid+"] a").css("color","#87CEFA");
    $("div.productsAsideCategorys[cid="+cid+"]").show();
}

function hideProductsAsideCategorys(cid){
    $("div.eachCategory[cid="+cid+"]").css("background-color","#e2e2e3");
    $("div.eachCategory[cid="+cid+"] a").css("color","#000");
    $("div.productsAsideCategorys[cid="+cid+"]").hide();
}

$(function(){
    $("div.eachCategory").mouseenter(function(){
        var cid = $(this).attr("cid");
        showProductsAsideCategorys(cid);
    });
    $("div.eachCategory").mouseleave(function(){
        var cid = $(this).attr("cid");
        hideProductsAsideCategorys(cid);
    });
    $("div.productsAsideCategorys").mouseenter(function(){
        var cid = $(this).attr("cid");
        showProductsAsideCategorys(cid);
    });
    $("div.productsAsideCategorys").mouseleave(function(){
        var cid = $(this).attr("cid");
        hideProductsAsideCategorys(cid);
    });
    //注册rigesterPage.jsp
    $(".registerForm").submit(function(){
        if(0==$("#name").val().length){
            $("span.errorMessage").html("请输入会员名");
            $(".registerErrorMessageDiv").css("visibility","visible");
            return false;
        }
        if(0==$("#password").val().length){
            $("span.errorMessage").html("请输入密码");
            $(".registerErrorMessageDiv").css("visibility","visible");
            return false;
        }
        if(0==$("#repeatpassword").val().length){
            $("span.errorMessage").html("请再次输入密码");
            $(".registerErrorMessageDiv").css("visibility","visible");
            return false;
        }
        if($("password").val() != $("repeatpassword").val()){
            $("span.errorMessage").html("两次输入的密码不一致");
            $("div.registerErrorMessageDiv").css("visibility","visible");
            return false;
        }
        return true;
    });
    //登录loginPage.jsp
    $(".loginForm").submit(function(){
        if(0==$("#name").val().length){
            $("span.errorMessage").html("请输入账号");
            $("div.loginErrorMessageDiv").show();          
            return false;
        }
        if(0==$("#password").val().length){
            $("span.errorMessage").html("请输入密码");
            $("div.loginErrorMessageDiv").show();          
            return false;
        }
        return true;
    });
    $(".loginForm input").keyup(function(){
        $("div.loginErrorMessageDiv").hide();  
    });
});

