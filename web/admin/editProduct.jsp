<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.*"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@include file="../include/admin/adminHeader.jsp"%>
<%@include file="../include/admin/adminNavigator.jsp"%>

<title>编辑产品</title>

<div class="workingArea">
    <ol class="breadcrumb">
        <li><a href="admin_category_list">所有分类</a></li>
        <li><a href="admin_product_list?cid=${p.category.id}">${p.category.name}</a></li>
        <li class="active">${p.name}</li>
        <li class="active">编辑产品</li>
    </ol>

    <div class="panel panel-warning editDiv">
        <div class="panel-heading">编辑产品</div>
        <div class="panel-body">
            <form method="post" id="listProduct_editForm" action="admin_product_update">
                <table class="editTable">
                    <tr>
                        <td>产品名称</td>
                        <td>
                            <input type="text" name="name" id="name" value="${p.name}" class="form-control" required>
                        </td>
                    </tr>
                    <tr>
                        <td>产品小标题</td>
                        <td>
                            <input type="text" name="subTitle" id="subTitle" value="${p.subTitle}" class="form-control">
                        </td>
                    </tr>
                    <tr>
                       <td>原价格</td> 
                       <td>
                           <input type="text" name="originalPrice" id="originalPrice" value="${p.originalPrice}" class="form-control" required>
                       </td>
                    </tr>
                    <tr>
                        <td>优惠价格</td>
                        <td>
                            <input type="text" name="promotePrice" id="promotePrice" value="${p.promotePrice}" class="form-control" required>
                        </td>
                    </tr>
                    <tr>
                        <td>库存数量</td>
                        <td>
                            <input type="text" name="stock" id="stock" value="${p.stock}" class="form-control" required>
                        </td>
                    </tr>
                    <tr class="submitTR">
                        <td colspan="2" align="center">
                            <input type="hidden" name="id" value="${p.id}">
                            <input type="hidden" name="cid" value="${p.category.id}">                            
                            <button class="btn btn-success">提 交</button>
                        </td>
                    </tr>
                </table>
            </form>
        </div>
    </div>
</div>