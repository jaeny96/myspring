<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>구조화용 태그</title>
<link rel="shortcut icon" href="#" />

<link rel="stylesheet" href="./css/basic.css" />
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script src="./js/semantic_css_jq.js"></script>
</head>


<!--유효성검사  
    switch(href){
    case './login.html':
    case './signup.html':
     
        $section.load(href, function(responseTxt, statusTxt, xhr){
if(statusTxt == "success")
alert("External content loaded successfully!");
if(statusTxt == "error")
alert("Error: " + xhr.status + ": " + xhr.statusText);
}); -->

<body>
	<header id="header">
		<a href="./"> <img
			src="./images/starbucks_logo.png" alt="스타벅스 로고" />
		</a><span>KOSTABUCKS</span>
	</header>
	<nav>
		<ol>
			<c:choose>
				<c:when test="${empty sessionScope.loginInfo}">
					<li><a href="./html/login.html"> 로그인 </a></li>
					<li><a href="./html/signup.html"> 가입 </a></li>
				</c:when>


				<c:otherwise>
					<li>${loginInfo.id}님반갑습니다.<a href="./logout">로그아웃</a></li>
				</c:otherwise>
			</c:choose>

			<li><a href="./productlist"> 상품목록 </a></li>
			<li><a href="./viewcart"> 장바구니보기 </a></li>

			<!-- 상품목록의 경우 사용자에 따라서 보여지는 결과가 달라야 함  -->

			<c:if test="${!empty sessionScope.loginInfo }">
				<li><a href="./orderlist">주문목록</a></li>
			</c:if>
		</ol>

	</nav>
	<div class="container">
		<section>
			<article class="one">
				<span class="content">서머 시즌 블렌드여름의 시작을 함께할 인도네시아 웨스트 자바. 허브의
					복합적인 풍미와 낮은 산미,<br /> 은은하게 달콤한 바닐라향이 매력적인 원두<br />스타벅스 로스트 스팩트럼은
					다년 간의 연구와 장인정신으로 탄생했습니다.” <br />모든 원두는 온도와 시간이 균형을 이루어야 그 향과 청량감,
					바디와 풍미가 최고 수준에 이릅니다. <br />우리의 커피는 세 가지 로스팅으로 분류됩니다.<br /> - 블론드
					로스트, 미디엄 로스트, 다크 로스트. 그래서 당신에게 맞는 풍미와 강도를 쉽게 찾을 수 있습니다.<br />스타벅스
					블론드 로스트 커피는 부드럽게 우리의 감각을 깨웁니다.<br /> 향긋한 청량감으로 은은하고 부드러우며, 살짝 달콤한
					풍미가 느껴지는 친근하고 맛 좋은 커피입니다.
				</span>
			</article>
			<article class="two">
				<span class="content"> 회원 가입 후, 스타벅스 e-Gift Card를 "나에게 선물하기"로
					구매하시고, 편리하게 등록하세요!<br /> 카드를 등록하여 스타벅스 리워드 회원이 되신 후, 첫 구매를 하시면 무료
					음료쿠폰을 드립니다!
				</span>
			</article>
		</section>
		<aside>
			<ul>
				<li><span class="hyundai">KOSTABUCKS 현대카드</span></li>
				<li><a
					href="https://www.starbucks.co.kr/plcc/promotionView.do?eventCode=STH02"><img
						src="./images/starbucks_card.png" alt="스타벅스 현대카드" /></a></li>
			</ul>
		</aside>
	</div>
	<footer> 사업자등록번호 : 201-81-21515 (주)스타벅스커피 코리아 대표이사 : 송 데이비드 호섭
		TEL : 1522-3232 개인정보 책임자 : 하익성 ⓒ 2021 Starbucks Coffee Company. All
		Rights Reserved. </footer>
</body>
</html>
