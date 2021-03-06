$(function () {
  var $formObj = $("form");
  var $idObj = $("form>label>input[name=id]");
  var $overlapObj = $("form>input.overlapbtn");
  var $searchObj = $("form>input.zipbtn");
  var $signUpBtnObj = $("form>button.signup");
  $idObj.focus(function () {
    $signUpBtnObj.hide();
  });
  $overlapObj.click(function () {
    var idValue = $idObj.val();
    /*if ($idObj.val() == "id9") {
      $signUpBtnObj.show();
    }*/
    var url = "./iddupchk";
    $.ajax({
      url: url,
      method: "get",
      data: { id: idValue },
      success: function (responseData) {
      	console.log(responseData.trim());
        if (responseData.trim() == "1") {
          //사용가능한 아이디인 경우
          alert("사용가능한 아이디입니다");
          $signUpBtnObj.show();
        } else {
          alert("이미 사용중인 아이디입니다");
          $idObj.focus();
          $idObj.select();
        }
      },
    });
    return false;
  });
  $searchObj.click(function () {
    var url = "./html/searchzip.html";
    var opt = "width=600 height=300";
    var name = "searchzip";
    //name 값을 주는 이유는 팝업창을 1개만 띄우기 위해서
    window.open(url, name, opt);
  });

  $formObj.submit(function () {
    var $pwd = $("form.signup input[name=pwd]");
    var $pwd1 = $("form.signup input.pwd1");
    if ($pwd.val() != $pwd1.val()) {
      console.log($pwd.val() + "//" + $pwd1.val());
      alert("비밀번호가 서로 다릅니다");
      $pwd.focus();
    } else {
      var url = "./signup";
      var method = "post";
      var data = $("form.signup").serialize();
      console.log(data);
      $.ajax({
        url: url,
        method: method,
        data: data,
        success: function (responseData) {
          if (responseData.trim() == "1") {
            //가입 성공
            alert("가입 성공");
            location.href = "./";
          } else {
            //가입 실패
            alert("가입 실패");
          }
        },
        error: function (xhr) {
          alert("ERROR : " + xhr.status);
        },
      });
      return false;
    }
  });
});
