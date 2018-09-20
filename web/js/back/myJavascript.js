//判空函数
function checkEmpty(id, name) {
    var value = $("#" + id).val();
    if(value.length == 0) {
        alert(name + "不能为空");
        $("#" + id)[0].focus();
        return false;
    }
    return true;
}
//删掉空白符函数
function checkBlank(id) {
    $("input#" + id).keyup(function(){
        var pattern = /\s/;
        var str = $("#"+id).val();
        if(true == pattern.test(str)) {
            alert("不能输入任何空白字符");
            $("#"+id).val(str.replace(/\s+/g,""));
            $("#" + id)[0].focus();
        }
    });
}

function checkNumber(id, name) {
    var value = $("#" + id).val();
    if(value.length == 0) {
        alert(name + "不能为空");
        $("#" + id)[0].focus();
        return false;
    }
    if(isNaN(value)) {
        alert(name + "必须是数字");
        $("#" + id)[0].focus();
        return false;
    }
    return true;
}

function checkInt(id, name) {
    var value = $("#" + id).val();
    if(value.length == 0) {
        alert(name + "不能为空");
        $("#" + id)[0].focus();
        return false;
    }
    if(parseInt(value) != value) {
        alert(name + "必须是整数");
        $("#" + id)[0].focus();
        return false;
    }
    return true;
}

$(function(){
    $("a").click(function(){
        var deleteLink = $(this).attr("deleteLink");
        console.log(deleteLink);
        if("true" == deleteLink) {
            confirmDelete = confirm("确认要删除");
            if(confirmDelete)
                return true;
            return false;
        }
    });

// adminPage.jsp

    $("ul.pagination li.disabled a").click(function(){
        return false;
    });


// listCategory.jsp
     
    $("#listCategory_addForm").submit(function(){
        if(!checkEmpty("name","分类名称"))
            return false;
        if(!checkEmpty("categoryPic","分类图片"))
            return false;
        return true;
    });

// *.jsp  删掉空白符

    checkBlank("name");
    checkBlank("subTitle");
    checkBlank("originalPrice");
    checkBlank("promotePrice");
    checkBlank("stock");

//editCategory.jsp

    $("#listCategory_editForm").click(function(){
        if(!checkEmpty("name","分类名称"))
            return false;
        return true;
    });

//listProperty.jsp

    $("#listProperty_addForm").submit(function(){
        if(!checkEmpty("name","属性名称"))
            return false;
        return true;
    });

//editProperty.jsp

    $("#listProperty_editForm").submit(function(){
        if(!checkEmpty("name","属性名称"))
            return false;
        return true;
    });

//listProduct.jsp

    $("#listProduct_addForm").submit(function(){
        if(!checkEmpty("name","产品名称"))
            return false;
        if(!checkNumber("originalPrice","原价格"))
            return false;
        if(!checkNumber("promotePrice","优惠价格"))
            return false;
        if(!checkInt("stock","库存"))
            return false;
        return true;
    });
//editProduct.jsp
    $("#listProduct_editForm").submit(function(){
        if(!checkEmpty("name","产品名称"))
            return false;
        if(!checkNumber("originalPrice","原价格"))
            return false;
        if(!checkNumber("promotePrice","优惠价格"))
            return false;
        if(!checkInt("stock","库存"))
            return false;
        return true;
    });

//listProductImage.jsp
    $(".addFormSingle").submit(function(){
        if(checkEmpty("filepathSingle","图片文件")) {
            $("#filepathSingle").value("");
            return true;
        }
        return false;
    });

    $(".addFormDetail").submit(function(){
        if(checkEmpty("filepathDetail","图片文件"))
            return true;
        return false;
    });

//editPropertyValue.jsp
    checkBlank("pvValue");

})

