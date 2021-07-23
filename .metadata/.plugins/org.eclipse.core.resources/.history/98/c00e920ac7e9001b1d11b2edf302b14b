<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="p" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<script>
	$(function() {
// 		console.log($("body > div > section > table > tbody > tr > td"));
		$('table.productlist tr>td').click(function() {
			console.log(this);
			var prod_no = $(this).attr('class');
			//상품 상세정보 보기 페이지 : ./productinfo
			//요청 방식 : get
			//요청 전달 데이터 : prod_no = 상품번호값
			//응답
			$.ajax({
				url : './productinfo',
				data : {
					prod_no : prod_no
				},
				success : function(responseData) {
					$('section').empty();
					$('section').html(responseData);
					//json 형태의 응답결과라면
					//var htmlStr='<img src='+responseData.prod_no+".jpg >";
					//$('section').html(htmlStr);
				}
			});
		});
	});
</script>

<p:set var="products" value="${requestScope.productlist}" />
<table class="productlist">
	<p:forEach var="p" items="${products}" varStatus="statusObj">
		<p:if test="${statusObj.index%4==0}">
			<tr>
				<p:if test="${statusObj.index>0}">
			</tr>
		</p:if>
		</p:if>
		<td class="${p.prod_no}">
			<ul>
				<li><img src="images/${p.prod_no}.jpg" alt="${p.prod_name}" /></li>
				<li>${p.prod_name}</li>
			</ul>
		</td>
	</p:forEach>
	</tr>
</table>