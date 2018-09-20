<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isELIgnored="false"%>
<!-- <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%> -->

<div class="categorySortBar">
    <table class="categorySortBarTable categorySortTable">
        <tbody>
            <tr>
                <td <c:if test="${'all'==param.sort||empty param.sort}">class="grayColumn"</c:if>>
                    <a href="?cid=${c.id}&sort=all"">综合<span class="glyphicon glyphicon-arrow-down"></span></a>
                </td>
                <td <c:if test="${'review'==param.sort}">class="grayColumn"</c:if>>
                    <a href="?cid=${c.id}&sort=review">人气<span class="glyphicon glyphicon-arrow-down"></span></a>
                </td>
                <td <c:if test="${'date'==param.sort}">class="grayColumn"</c:if>>
                    <a href="?cid=${c.id}&sort=date">新品<span class="glyphicon glyphicon-arrow-down"></span></a>
                </td>
                <td <c:if test="${'saleCount'==param.sort}">class="grayColumn"</c:if>>
                    <a href="?cid=${c.id}&sort=saleCount">销量<span class="glyphicon glyphicon-arrow-down"></span></a>
                </td>
                <td <c:if test="${'price'==parma.sort}">class="grayColumn"</c:if>>
                    <a href="?cid=${c.id}&sort=price">价格<span class="glyphicon glyphicon-resize-vertical"></span></a>
                </td>
            </tr>
        </tbody>
    </table>
    <table class="categorySortBarTable">
        <tbody>
            <tr>
                <td><input class="sortBarPrice beginPrice" type="text" placeholder="请输入"></td>
                <td class="grayColumn priceMidedleColumn">-</td>
                <td><input class="sortBarPrice endPrice" type="text" placeholder="请输入"></td>
            </tr>
        </tbody>
    </table>
</div>