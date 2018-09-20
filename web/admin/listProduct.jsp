<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.*"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@include file="../include/admin/adminHeader.jsp"%>
<%@include file="../include/admin/adminNavigator.jsp"%>

<title>产品管理</title>

<div class="workingArea">
    <ol class="breadcrumb">
        <li><a href="admin_category_list">所有分类</a></li>
        <li><a href="admin_product_list?cid=${c.id}">${c.name}</a></li>
        <li class="active">产品管理</li>
    </ol>

    <div class="listDataTableDiv">
        <table class="table table-striped table-bordered table-hover  table-condensed">
            <thead>
                <tr class="success">
                <th>ID</th>
                <th>图片</th>
                <th>产品名称</th>
                <th>产品小标题</th>
                <th class="product_Th_Width1">原价格</th>
                <th class="product_Th_Width2">优惠价格</th>
                <th class="product_Th_Width2">库存数量</th>
                <th class="product_Th_Width2">图片管理</th>
                <th class="product_Th_Width2">设置属性</th>
                <th class="product_Th_Width3">编辑</th>
                <th class="product_Th_Width3">删除</th>
            </tr>
            </thead>
            <tbody>
                <c:forEach items="${ps}" var="p">
                <tr>
                    <td>${p.id}</td>
                    <td>
                        <c:if test="${!empty p.firstProductImage}">
                            <img src="img/productSingle/${p.firstProductImage.id}.jpg">
                        </c:if>
                    </td>
                    <td>${p.name}</td>
                    <td>${p.subTitle}</td>
                    <td>${p.originalPrice}</td>
                    <td>${p.promotePrice}</td>
                    <td>${p.stock}</td>
                    <td>
                        <a href="admin_productImage_list?pid=${p.id}">
                            <span class="glyphicon glyphicon-picture"></span>
                        </a>
                    </td>
                    <td>
                        <a href="admin_product_editPropertyValue?id=${p.id}">
                            <span class="glyphicon glyphicon-th-list"></span>
                        </a>
                    </td>
                    <td>
                        <a href="admin_product_edit?id=${p.id}">
                            <span class="glyphicon glyphicon-edit"></span>
                        </a>
                    </td>
                    <td>
                        <a href="admin_product_delete?id=${p.id}" deleteLink="true">
                            <span class="glyphicon glyphicon-trash"></span>
                        </a>
                    </td>
                </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>

    <div class="pageDiv">
        <%@include file="../include/admin/adminPage.jsp"%>
    </div>


    <div class="panel panel-warning addDiv">
        <div class="panel-heading">产品管理</div>
        <div class="panel-body">
            <form method="post" id="listProduct_addForm" action="admin_product_add">
                <table class="addTable">
                    <tr>
                        <td>产品名称</td>
                        <td>
                            <input type="text" name="name" id="name" class="form-control" required>
                        </td>
                    </tr>
                    <tr>
                        <td>产品小标题</td>
                        <td>
                            <input type="text" name="subTitle" id="subTitle" class="form-control">
                        </td>
                    </tr>
                    <tr>
                       <td>原价格</td> 
                       <td>
                           <input type="text" name="originalPrice" id="originalPrice" class="form-control" required>
                       </td>
                    </tr>
                    <tr>
                        <td>优惠价格</td>
                        <td>
                            <input type="text" name="promotePrice" id="promotePrice" class="form-control" required>
                        </td>
                    </tr>
                    <tr>
                        <td>库存数量</td>
                        <td>
                            <input type="text" name="stock" id="stock" class="form-control" required>
                        </td>
                    </tr>
                    <tr class="submitTR">
                        <td colspan="2" align="center">
                            <input type="hidden" name="cid" value="${c.id}">
                            <button type="submit" class="btn btn-success">提 交</button>
                        </td>
                    </tr>
                </table>
            </form>
        </div>
    </div>
</div>

<%@include file="../include/admin/adminFooter.jsp"%>