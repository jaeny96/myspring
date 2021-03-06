<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
  <!--  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>-->
 
 <script>
 $(function(){
// 	 console.log($('body > div > section > div > div:nth-child(2) > ol > li:nth-child(5) > button#putcartBtn'));
    $('body > div > section > div > div:nth-child(2) > ol > li:nth-child(5) > button#putcartBtn').click(function(){
      var prod_no = $(this).parents('ol').find('li>span.prod_no').html();
       //현재 객체의 부모들 중에서 li.prod_no의 html body 내용을 가져오기
       // button의 조상들은 버튼만 찾는다.
       //productinfo>div>ol>li로 주면 여기에서 버튼찾음. 
       //html, val()은 다른의미다. 
      console.log(prod_no);
       
      var quantity = $(this).parents('ol').find('li>input[type=number]').val();
      console.log(quantity);
       $.ajax({
          url: './putcart',
          method:'get',
          data:{prod_no: prod_no, quantity: quantity},
       
          success: function(responseData){
             alert(responseData);
             //div 영역 보여주기 
             $('div.productinfo>div.modal').show();
          }
       }); 
    });    
    //계속하기 버튼 클릭 
    $('div.productinfo>div.modal>button.productlist').click(function(){
       //상품 목록 보기 메뉴에 click 이벤트를 강제 발생시킨다  
       console.log('클릭');
      $('body>nav>ol>li>a[href="./productlist"]').trigger('click'); 
    });
    
    //장바구니 보기 버튼 클릭 이벤트 처리 
    
   $('div.productinfo>div.modal>button.viewcart').click(function(){
      console.log('클릭');
      $('body>nav>ol>li>a[href="./viewcart"]').trigger('click');
   });
 });
 </script>
<!--  
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>Insert title here</title>
</head>
<body>

</body>
</html>
나중에 합칠 때 다 없앨 태그들 
-->

<c:set var="p" value="${requestScope.p}"/>
<div class="productinfo">
<div style="float:left">
<img src="./images/${p.prod_no}.jpg" alt="${p.prod_name}" style="width: 200px;">
</div>
<div style="float:right">
   <ol style="list-style-type:none; padding:0px">
      <li>상품번호:<span class="prod_no">${p.prod_no}</span></li>
      <li>상품명:<span>${p.prod_name}</span></li>
      <li>가격:<span>${p.prod_price}</span></li>
      <li>수량:<input type="number" value="1" max="99"></li>
      <li><button id="putcartBtn">장바구니 넣기</button></li>
   </ol>
</div>
<div class="modal" style="clear:both; display:none">
   <button class="viewcart">장바구니 보기</button>
   <button class="productlist">계속하기</button>
</div>
</div>