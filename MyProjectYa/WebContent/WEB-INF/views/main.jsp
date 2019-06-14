<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link href="css/home.css" rel="stylesheet" type="text/css">
<link href="css/meddle.css" rel="stylesheet" type="text/css">
</head>
<body>
<div id="wrap">
<!-- 헤더파일들어가는 곳 -->
<jsp:include page="/WEB-INF/views/inc/top.jsp"></jsp:include>
<!-- 헤더파일들어가는 곳 -->
<!-- 메인이미지 들어가는곳 -->
<div class="clear"></div>
<div id="main_img"><img src="images/song3.jpg"
 width="974" height="282"></div>
<!-- 메인이미지 들어가는곳 -->
<!-- 메인 콘텐츠 들어가는 곳 -->
<article id="front">
<div id="solution">
<div id="hosting">
<img src="images/ballad.jpg" width="50" height="100" id="ballad">
<h3>발라드</h3>
<p>라틴어의 춤추다(Ballare)라는 뜻에서 생긴 프랑스어로, 영어로는 Ballad라고 쓴다. 
서양 고전음악의 한 장르.
</p>
</div>
<div id="security">
<img src="images/lapper.jpg" width="50" height="100" id="lapper">
<h3>랩</h3>
<p>랩(rap)은 힙합의 한 축을 이루는 음악 요소로서, 주로 각운을 이루는 말을 리듬에 맞추어 음악적으로 발성하는 것을 일컫는다. 
랩은 말과 노래의 경계다.
</p>
</div>
<div id="payment">
<img src="images/idol.png" width="50" height="100" id="idol">
<h3>아이돌</h3>
<p>아이돌(idol 아이들[*])은 본래 신화적인 꼭두각시을 뜻하는 영어이고, 어원은 그리스어로 ιδειν이며, 이후 ειδo에서 idola로 변천되어 최종적으로 idol로 바뀌었다. 
</p>
</div>
</div>
<div class="clear"></div>
<div id="sec_news">
<h3><span class="orange">Security</span> News</h3>
<dl>
<dt>관리자</dt>
<dd>게시판에 이상한 글을 올리시는 분들은 제재시키겠습니다.</dd>
</dl>
<dl>
<dt>이 홈페이지는 좋아하는 노래 공유 페이지입니다.</dt>
<dd>원하는 가수의 노래를 게시판에 올려주고 서로 공유합시다!!</dd>
</dl>
</div>
<div id="news_notice">
<h3 class="brown">지도</h3>
<div id="map" style="width:100%;height:350px;"></div>
<script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=0090e134a5053b7c43be998b5788e1ee"></script>
<script>
var container = document.getElementById('map'); //지도를 담을 영역의 DOM 레퍼런스
var options = { //지도를 생성할 때 필요한 기본 옵션
	center: new daum.maps.LatLng(35.127275, 129.093646), //지도의 중심좌표.
	level: 3 //지도의 레벨(확대, 축소 정도)
};

var map = new daum.maps.Map(container, options); //지도 생성 및 객체 리턴
</script>
</div>
</article>
<!-- 메인 콘텐츠 들어가는 곳 -->
<div class="clear"></div>
<!-- 푸터 들어가는 곳 -->
<jsp:include page="/WEB-INF/views/inc/bottom.jsp"></jsp:include>
<!-- 푸터 들어가는 곳 -->
</div>
</body>
</html>