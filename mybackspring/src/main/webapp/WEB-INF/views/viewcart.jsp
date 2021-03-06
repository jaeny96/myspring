<%@page import="java.text.DecimalFormat"%>
<%@page import="com.day.dto.Product"%>
<%@page import="java.util.Set"%>
<%@page import="java.util.Map"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:set var="result" value="${requestScope.result}" />
<!--      <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script> -->
<!--  테스트가 끝나면 삭제 -->
<script>
	$(function() {
		$('div.viewcart>button.addorder').click(function() {
			$.ajax({
				url : './addorder',
				method : 'get',
				success : function(respsonseData) {
					alert(respsonseData.trim());
					$('body>nav>ol>li>a[href="./viewcart"]').trigger('click');
				}
			});
		});
	});
</script>
<c:choose>
	<c:when test="${empty result}">
 장바구니가 비었습니다
</c:when>
	<c:otherwise>
		<div class="viewcart">
			<h3>장바구니</h3>
			<table>
				<tr>
					<th>상품번호</th>
					<th>상품명</th>
					<th>가격</th>
					<th>수량</th>
					<th>금액</th>
				</tr>
				<c:forEach items="${result}" var="kv">
					<c:set var="p" value="${kv.key}" />
					<c:set var="quantity" value="${kv.value}" />
					<tr>
						<td>${p.prod_no}</td>
						<td>${p.prod_name}</td>
						<td>${p.prod_price}</td>
						<td>${quantity}</td>
						<td><fmt:formatNumber pattern="#,##0"
								value="${p.prod_price * quantity}" /></td>
					</tr>
				</c:forEach>
			</table>
			<button class="addorder">주문하기</button>
		</div>
	</c:otherwise>
</c:choose>
