<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
  <!--  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>-->
 
 <script>
 $(function(){
// 	 console.log($('body > div > section > div > div:nth-child(2) > ol > li:nth-child(5) > button#putcartBtn'));
    $('body > div > section > div > div:nth-child(2) > ol > li:nth-child(5) > button#putcartBtn').click(function(){
      var prod_no = $(this).parents('ol').find('li>span.prod_no').html();
       //���� ��ü�� �θ�� �߿��� li.prod_no�� html body ������ ��������
       // button�� ������� ��ư�� ã�´�.
       //productinfo>div>ol>li�� �ָ� ���⿡�� ��ưã��. 
       //html, val()�� �ٸ��ǹ̴�. 
      console.log(prod_no);
       
      var quantity = $(this).parents('ol').find('li>input[type=number]').val();
      console.log(quantity);
       $.ajax({
          url: './putcart',
          method:'get',
          data:{prod_no: prod_no, quantity: quantity},
       
          success: function(responseData){
             alert(responseData);
             //div ���� �����ֱ� 
             $('div.productinfo>div.modal').show();
          }
       }); 
    });    
    //����ϱ� ��ư Ŭ�� 
    $('div.productinfo>div.modal>button.productlist').click(function(){
       //��ǰ ��� ���� �޴��� click �̺�Ʈ�� ���� �߻���Ų��  
       console.log('Ŭ��');
      $('body>nav>ol>li>a[href="./productlist"]').trigger('click'); 
    });
    
    //��ٱ��� ���� ��ư Ŭ�� �̺�Ʈ ó�� 
    
   $('div.productinfo>div.modal>button.viewcart').click(function(){
      console.log('Ŭ��');
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
���߿� ��ĥ �� �� ���� �±׵� 
-->

<c:set var="p" value="${requestScope.p}"/>
<div class="productinfo">
<div style="float:left">
<img src="./images/${p.prod_no}.jpg" alt="${p.prod_name}" style="width: 200px;">
</div>
<div style="float:right">
   <ol style="list-style-type:none; padding:0px">
      <li>��ǰ��ȣ:<span class="prod_no">${p.prod_no}</span></li>
      <li>��ǰ��:<span>${p.prod_name}</span></li>
      <li>����:<span>${p.prod_price}</span></li>
      <li>����:<input type="number" value="1" max="99"></li>
      <li><button id="putcartBtn">��ٱ��� �ֱ�</button></li>
   </ol>
</div>
<div class="modal" style="clear:both; display:none">
   <button class="viewcart">��ٱ��� ����</button>
   <button class="productlist">����ϱ�</button>
</div>
</div>