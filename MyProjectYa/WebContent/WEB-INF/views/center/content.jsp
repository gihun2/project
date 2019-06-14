<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
<h1>Notice Content</h1>
<table id="notice">
<tr>
<th width="50px">글제목</th>
<td colspan="3">${board.subject}</td>
</tr>
<tr>
<th>글번호</th>
<td>${board.num}</td>
<th>조회수</th>
<td>${board.readcount}</td>
</tr>
<tr>
<th>작성자</th>
<td>${board.name}</td>
<th>작성일</th>
<td>${board.reg_date}</td>
</tr>     

<tr>
<td colspan="4">${board.content}</td>
</tr>
</table>

<div id="table_search">
<c:if test="${id ne null}">
	<input type="button" value="글수정" class="btn" onclick="location.href='boardModifyForm.do?num=${board.num}&pageNum=${param.pageNum}';">
	<input type="button" value="글삭제" class="btn" onclick="location.href='boardDeleteForm.do?num=${board.num}&pageNum=${param.pageNum}';">
	<input type="button" value="답글쓰기" class="btn" onclick="location.href='boardReplyForm.do?re_ref=${board.re_ref}&re_lev=${board.re_lev}&re_seq=${board.re_seq}&pageNum=${param.pageNum}';">
</c:if>
<input type="button" value="글목록" class="btn" onclick="location.href='boardList.do?pageNum=${param.pageNum}';">
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