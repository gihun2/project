<%@page import="java.util.Map"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="vo.Sboard"%>
<%@page import="java.util.List"%>
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

<!-- 왼쪽메뉴 -->
<nav id="sub_menu">
<ul>
<li><a href="boardList.do">게시판</a></li>
<li><a href="boardFileList.do">자료 게시판</a></li>
</ul>
</nav>
<!-- 왼쪽메뉴 -->

<!-- 게시판 -->
<article>
<h1>자료 게시판 [전체 글 개수: ${pageInfoMap.allRowCount}]</h1>

<table id="notice">
<tr><th class="tno">No.</th>
    <th class="ttitle">Title</th>
    <th class="twrite">Writer</th>
    <th class="tdate">Date</th>
    <th class="tread">Read</th></tr>

<c:choose>
<c:when test="${not empty list}">
	<c:forEach var="board" items="${list}">
		<tr onclick="location.href='boardFileDetail.do?num=${board.num}&pageNum=${pageInfoMap.pageNum}'">
			<td>${board.num}</td>
			<td class="left">
			<c:if test="${board.re_lev gt 0}">
				<c:set var="wid" value="${board.re_lev * 10}" />
					<img src="images/center/level.gif" style="width: ${wid}px; height: 13px;">
					<img src="images/center/reply.png">
			</c:if>	
			${board.subject}
			</td>
			<td>${board.name}</td>
			<td>
			<fmt:formatDate value="${board.reg_date}" pattern="yyyy.MM.dd"/>
			</td>
			<td>${board.readcount}</td>
		</tr>
	</c:forEach>
</c:when>
<c:otherwise>
<tr>
	<td colspan="5">게시판 글 없습니돠.</td>
</tr>
</c:otherwise>
</c:choose>
</table>
<!-- 세션값 있을때 글쓰기 버튼 보이게 설정 -->
<c:if test="${sessionScope.id ne null}">
	<div id="table_search">
	<input type="button" value="파일 글쓰기" class="btn" onclick="location.href='boardFileWriteForm.do';">
	</div>
</c:if>

<div id="table_search">
<form action="boardFileList.do" method="get">
<input type="text" name="search" class="input_box" value="${search}">
<input type="submit" value="검색" class="btn">
</form>
</div>
<div class="clear"></div>
<div id="page_control">

<c:if test="${pageInfoMap.allRowCount gt 0}">
<!-- 이전 블록이 존재하는지 확인 -->
<c:if test="${pageInfoMap.startPage gt pageInfoMap.pageBlockSize}">
	<a href="boardList.do?pageNum=${pageInfoMap.startPage - pageInfoMap.pageBlockSize}&search=${search}">[Prev] </a>
	</c:if>
	<c:forEach var="i" begin="${pageInfoMap.startPage}" end="${pageInfoMap.endPage}" step="1">
		<c:choose>
		<c:when test="${i eq pageInfoMap.pageNum}">
		<a href="boardList.do?pageNum=${i}&search=${search}"> <span style="color: blue; font-weight: bold;">[${i}]</span> </a>
		</c:when>
		<c:otherwise>
		<a href="boardList.do?pageNum=${i}&search=${search}"> [${i}] </a>
		</c:otherwise>
		</c:choose>
	</c:forEach>
	<!-- 다음 블록이 존재하는지 확인 -->
	<c:if test="${pageInfoMap.endPage lt pageInfoMap.maxPage}">
	<a href="boardList.do?pageNum=${pageInfoMap.startPage + pageInfoMap.pageBlockSize}&search=${search}">[Next]</a>
	</c:if>
</c:if>

</div>
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