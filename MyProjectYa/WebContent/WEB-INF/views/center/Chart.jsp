<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>  
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link href="css/home.css" rel="stylesheet" type="text/css">
<link href="css/subpage1.css" rel="stylesheet" type="text/css">

<script src="js/jquery-3.3.1.min.js"></script>
<script src="https://www.gstatic.com/charts/loader.js"></script>
<script>
//구글 시각화 API를 로딩하는 메소드
google.charts.load('current', {packages: ['corechart']});

//구글 시각화 API가 로딩이 완료되면,
// 인자로 전달된 콜백함수를 내부적으로 호출하여 차트를 그리는 메소드
 //google.charts.setOnLoadCallback(columnChart1);


$(document).ready(function(){
	 google.charts.setOnLoadCallback(function () {
		 $.ajax({
				url: 'columnChart1.jsp',
				success: function(result){
					console.log(result);
					columnChart1(result);
				}
			});	 
	 });
});

//묶은 세로 막대형 차트 1
function columnChart1(arraylist) {
	
	// 실 데이터를 가진 데이터테이블 객체를 반환하는 메소드
	var dataTable = google.visualization.arrayToDataTable(arraylist);
	// 옵션객체 준비
	var options = {
			title: '거주지 별 인원수',
			hAxis: {
				title: '거주지',
				titleTextStyle: {
					color: 'red'
				}
			}
	};
	// 차트를 그릴 영역인 div 객체를 가져옴 
	var objDiv = document.getElementById('column_chart_div1');
	// 인자로 전달한 div 객체의 영역에 컬럼차트를 그릴수 있는 차트객체를 반환
	var chart = new google.visualization.ColumnChart(objDiv);
	// 차트객체에 데이터테이블과 옵션 객체를 인자로 전달하여 차트 그리는 메소드
	chart.draw(dataTable, options);
} // drawColumnChart1()의 끝
</script>
</head>
<body>
<div id="wrap">
<!-- 헤더들어가는 곳 -->
<jsp:include page="/WEB-INF/views/inc/top.jsp"></jsp:include>
<!-- 헤더들어가는 곳 -->

<!-- 본문들어가는 곳 -->
<!-- 메인이미지 -->
<div id="sub_img_center"></div>
<!-- 메인이미지 -->

<!-- 게시판 -->
<article>
<h1>통계</h1>

<div id="column_chart_div1"></div>

</article>
<!-- 게시판 -->
<!-- 본문들어가는 곳 -->
<div class="clear"></div>
<!-- 푸터들어가는 곳 -->
<jsp:include page="/WEB-INF/views/inc/bottom.jsp"></jsp:include>
<!-- 푸터들어가는 곳 -->
</div>
</body>
</html>