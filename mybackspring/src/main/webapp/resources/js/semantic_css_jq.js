$(function() {
	var $menuObj = $("nav>ol>li>a");
	var $section = $("section");
	$menuObj.click(function() {
		//클릭된 현재 객체의 href 속성값 얻기
		var href = $(this).attr("href");
		switch (href) {
			case "./html/login.html":
			case "./html/signup.html":
			case "./productlist":
			case './viewcart':
			case './orderlist':
			
				$section.load(href, function(responseTxt, statusTxt, xhr) {
					if (statusTxt == "error")
						alert("Error: " + xhr.status + ": " + xhr.statusText);
				});
				break;
			case './logout':
				$section.load(href, function(responseTxt, statusTxt, xhr) {
					if (statusTxt == "error") //실패 
						alert("Error: " + xhr.status + ": " + xhr.statusText);
				});
				location.href = "./";

				break;
		}
		return false;
	});
});
