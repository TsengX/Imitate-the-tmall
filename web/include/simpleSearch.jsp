<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isELIgnored="false"%>

<!-- 简单搜索框 -->
<link rel="stylesheet" type="text/css" href="css/fore/simpleSearch.css">
<div class="simpleSearchOutDiv"">
	<a href="${contextPath}">
		<img class="simpleLogo" id="simpleLogo" src="./img/site/simpleLogo.png">
	</a>
	<form action="foresearch" method="post">
		<div class="simpleSearchDiv">
			<input type="text" name="keyword" placeholder="平衡车 原汁机">
			<button type="button" class="searchButton">搜天猫</button>
			<div class="searchBelow">
				<c:forEach items="${cs}" var="c" varStatus="st">
					<c:if test="${st.count>=8 and st.count<=11}">
						<span>
							<a href="forecategory?cid=${c.id}">${c.name}</a>
							<c:if test="st.count!=11">
								<span>|</span>
							</c:if>
						</span>
					</c:if>
				</c:forEach>
			</div>
		</div>
	</form>
	<div style="clear: both"></div>
</div>
