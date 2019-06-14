<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link href="css/home.css" rel="stylesheet" type="text/css">
<link href="css/subpage1.css" rel="stylesheet" type="text/css">
</head>
<body>
<div id="wrap">
<!-- 헤더가 들어가는 곳 -->
<jsp:include page="/WEB-INF/views/inc/top.jsp"></jsp:include>
<!-- 헤더가 들어가는 곳 -->

<!-- 본문 들어가는 곳 -->
<!-- 서브페이지 메인이미지 -->
<div id="sub_img"></div>
<!-- 서브페이지 메인이미지 -->
<!-- 왼쪽메뉴 -->
<nav id="sub_menu">
<ul>
<li><a href="welcome.do">가수들</a></li>
<li><a href="#">History</a></li>
</ul>
</nav>
<!-- 왼쪽메뉴 -->
<!-- 내용 -->
<article>
<h1>Singer</h1>
<figure class="singer"><img src="images/company/hyosin.jpg" width="150" height="190" alt="SINGER">
<figcaption>박효신</figcaption>
</figure>
<figure class="singer"><img src="images/company/chonha.jpg" width="150" height="190" alt="SINGER">
<figcaption>청하</figcaption>
</figure>
<figure class="singer"><img src="images/company/minsu.jpg" width="150" height="190" alt="SINGER">
<figcaption>윤민수</figcaption>
</figure>
<figure class="singer"><img src="images/company/theone.JPG" width="150" height="190" alt="SINGER">
<figcaption>더원</figcaption>
</figure>
<figure class="singer"><img src="images/company/ssai.jpg" width="150" height="190" alt="SINGER">
<figcaption>싸이</figcaption>
</figure>
<figure class="singer"><img src="images/company/Guckkasten.jpg" width="150" height="190" alt="SINGER">
<figcaption>국카스텐</figcaption>
</figure>
<figure class="singer"><img src="images/company/mamamu.jpg" width="150" height="190" alt="SINGER">
<figcaption>마마무</figcaption>
</figure>
<figure class="singer"><img src="images/company/sunmi.png" width="150" height="190" alt="SINGER">
<figcaption>선미</figcaption>
</figure>
<figure class="singer"><img src="images/company/iu.jpg" width="150" height="190" alt="SINGER">
<figcaption>아이유</figcaption>
</figure>
<figure class="singer"><img src="images/company/bumsu.jpg" width="150" height="190" alt="SINGER">
<figcaption>김범수</figcaption>
</figure>
<figure class="singer"><img src="images/company/naal.jpg" width="150" height="190" alt="SINGER">
<figcaption>나얼</figcaption>
</figure>
<figure class="singer"><img src="images/company/isu.jpg" width="150" height="190" alt="SINGER">
<figcaption>이수</figcaption>
</figure>
</article>
<!-- 내용 -->
<!-- 본문 들어가는 곳 -->
<div class="clear"></div>
<!-- 푸터 들어가는 곳 -->
<jsp:include page="/WEB-INF/views/inc/bottom.jsp"></jsp:include>
<!-- 푸터 들어가는 곳 -->
</div>
</body>
</html>



    