$(function () {
  //1.localStorage의 item(이름: loginInfo)찾기
  var loginInfoValue = localStorage.getItem("loginInfo");

  //2. 1의 결과가 null이거나 ''이 아니면 1의결과를 아이디입력란에 설정한다
  var idObj = document.querySelector("form.login input[name=id]");
  if (loginInfoValue != null && loginInfoValue != "") {
    idObj.value = loginInfoValue;
  }

  var formObj = document.querySelector("form.login");
  formObj.addEventListener("submit", function (event) {
    //아이디값 유효성검사
    //비밀번호값 유효성검사

    //유효성검사가 실패된 경우에는 전송 중지
    //event.preventDefault();

    //localStorage의 item(이름:loginInfo)삭제
    localStorage.removeItem("loginInfo");

    //아이디저장 체크된 경우 localStorage의 item(이름:loginInfo, 값:입력한아이디값)추가
    //var idObj = documnt.querySelector('form.login input[name=id]');
    //1)DOM트리에서 체크박스객체 찾기
    var chkboxObj = document.querySelector("form.login input[type=checkbox]");

    //2)체크여부확인
    if (chkboxObj.checked) {
      //체크된 경우
      localStorage.setItem("loginInfo", idObj.value); //localStorage에 추가
    }

//	var url="http://localhost:8888/myback/login";
	var url="./login";
	//var url="http://localhost:8888/mybackjson/login";
    //서버로 AJAX 요청,응답
    $.ajax({
      url: url,
      method: "post",
      data: { id: $("form.login input[name=id]").val(), pwd: $("form.login input[name=pwd]").val() },
      //data: { id: "id1", pwd: "33" },
      success: function (responseData) {
        alert(responseData);
		location.href="./";
		//alert(responseData.status);
		//if(responseData.status == 1){ //성공
			
		//}else{ //실패
		//	alert("실패 이유 : "+responseData.msg);
		//}
      },
      error: function (xhr) {
        alert(xhr.status);
      },
    });
    event.preventDefault();
  });
}); // 쿠키로 저장하면 안 되나요?
        //서버로 전송하지 않아도 된다면 쿠키로 만들어서 관리할 수 있다. 세션을 유지하면서, 서버로 전송해야 하는 경우 storage를 쓰는 게 낫다. 스토리지는 쿠키를 대신할 수 있다. 
