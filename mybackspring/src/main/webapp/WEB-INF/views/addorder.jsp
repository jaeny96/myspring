<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var ="status" value="${requestScope.status}"></c:set>
<c:choose>
<c:when test="${status==1}">
	�ֹ� �߰� ����
</c:when>
	<c:otherwise>
	�ֹ��߰� ���� 
		<c:if test ="${status==0}">
			�α����ϼ���
			<%--���� �� �α���ȭ������ �̵�  --%>
		</c:if>
		<c:if test="${status==-1}">
			��ٱ��ϰ� ������ϴ�. 
			<%--d���� �� ��ǰ��� �̵� �Ǵ� �ֹ���� �̵�  --%>
		</c:if>
		<c:if test="${status==-2}">
			�߰� ���� ${requestScope.msg} 
		</c:if>	

	</c:otherwise>

</c:choose>