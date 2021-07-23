<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var ="status" value="${requestScope.status}"></c:set>
<c:choose>
<c:when test="${status==1}">
	주문 추가 성공
</c:when>
	<c:otherwise>
	주문추가 실패 
		<c:if test ="${status==0}">
			로그인하세요
			<%--응답 후 로그인화면으로 이동  --%>
		</c:if>
		<c:if test="${status==-1}">
			장바구니가 비었습니다. 
			<%--d응답 후 상품목록 이동 또는 주문목록 이동  --%>
		</c:if>
		<c:if test="${status==-2}">
			추가 실패 ${requestScope.msg} 
		</c:if>	

	</c:otherwise>

</c:choose>