$(function() {
	//imgAndInfo.jsp
	// 产品页面----显示缩略图效果
	$("img.smallImage").mouseenter(function() {
		var bigImageURL = $(this).attr("bigImageURL");
		$("img.bigImg").attr("src", bigImageURL);
	});
	$("img.bigImg").load(
	//预加载
	function() {
		$("img.smallImage").each(function() {
			var bigImageURL = $(this).attr("bigImageURL");
			img = new Image();
			img.src = bigImageURL;
			img.onload = function() {
				console.log(bigImageURL);
				$("div.img4load").append($(img));
				//放到被隐藏的div.img4load中，从而达到预加载的效果
			};
		});
	});

	//改变商品的数量
	var stock = 66;
	$(".productNumberSetting").change(function() {
		var num = $(".productNumberSetting").val();
		num = parseInt(num);
		if (isNaN(num))
			num = 1;
		if (num <= 0)
			num = 1;
		if (num > stock)
			num = stock;
		$(".productNumberSetting").val(num);
	});

	$(".increaseNumber").click(function() {
		var num = $(".productNumberSetting").val();
		num++;
		if (num > stock)
			num = stock;
		$(".productNumberSetting").val(num);
	});

	$(".decreaseNumber").click(function() {
		var num = $(".productNumberSetting").val();
		num--;
		if (num <= 0)
			num = 1;
		$(".productNumberSetting").val(num);
	});

	//productReview.jsp 累计评价和商品详情的转换
	$("div.productReviewDiv").hide();
    $("a.productDetailTopReviewLink").click(function(){
        $("div.productReviewDiv").show();
        $("div.productDetailDiv").hide();
    });
    $("a.productReviewTopPartSelectedLink").click(function(){
        $("div.productReviewDiv").hide();
        $("div.productDetailDiv").show();      
    });

    
});