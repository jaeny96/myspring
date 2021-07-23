$(function () {
  var $searchObj = $("form>label>input[name=search]");
  var $searchformObj = $("form");
  var $liObj = $("div.divSearchzip>ul>li");
  $liObj.click(function () {
    var $zipcode = $(this).children("span.zipcode").text();
    var $baseaddr = $(this).find("span.baseaddr").text();
    // var $zipcode = $(this).find("span.zipcode").html();
    // var $baseaddr = $(this).find("span.baseaddr").html();

    // var parent = $(opener.document);
    var parent = self.opener;
    // var $zipObj = parent.find("input[name=post]");
    // var $baseaddrObj = parent.find("input.baseaddr");
    var $zipObj = $("input[name=post]", parent.document);
    var $baseaddrObj = $("input.baseaddr", parent.document);

    $zipObj.val($zipcode);
    $baseaddrObj.val($baseaddr);

    window.close();
  });

  console.log($searchObj.val());

  $searchformObj.submit(function () {
    $.ajax({
      url: "./searchzip",
      method: "post",
      data: { search: "doro=" + $searchObj.val() },
      success: function (responseData) {
        alert("도로명 주소 검색 성공 " + responseData);
      },
      error: function (xhr) {
        alert($searchObj.val() + " 도로명 주소 검색 실패 " + xhr.status);
      },
    });
  });
});
