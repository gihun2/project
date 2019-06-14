<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시판 리스트 - 관리자 화면</title>
<style>
table {
	width: 100%;
}

table, th, td {
	border: 1px solid #bcbcbc;
}

td {
	width: 30px;
}
</style>
<script>
	function change1(aaa){
		aaa.style.background='red';
		aaa.style.color='white';
	}
	function change2(aaa){
		aaa.style.background='white';
		aaa.style.color='black';
	}
</script>
</head>
<body>
<article>
	<form action="boardAdminDeleteProcess.do" method="post" name="frm">
		<input type="hidden" name="num"
			value="<%=request.getParameter("num")%>"> <input
	type="hidden" name="pageNum"
	value="<%=request.getParameter("pageNum")%>"> <input
	type="hidden" name="pass" value="<%=request.getParameter("pass")%>">
<h1>게시판 [전체글개수: ${pageInfoMap.allRowCount}]</h1>

<table id="notice">
	<tr>
		<th class="tno">No.</th>
		<th class="ttitle">Title</th>
		<th class="twrite">Writer</th>
		<th class="tdate">Date</th>
		<th class="tread">Read</th>
	</tr>

<c:choose>
	<c:when test="${not empty list}">
		<c:forEach var="board" items="${list}">
			<tr onclick="location.href='boardAdminDeleteForm.do?num=${board.num}';"
			onmouseout="change2(this)" onmouseover="change1(this)" style="background-color:white;">
				<td>${board.num}</td>
				<td class="left"><c:if test="${board.re_lev gt 0}">
						<c:set var="wid" value="${board.re_lev * 10}" />
						<img src="images/center/level.gif"
							style="width: ${wid}px; height: 13px;">
						<img src="images/center/reply.png">
					</c:if> ${board.subject}</td>
				<td>${board.name}</td>
				<td><fmt:formatDate value="${board.reg_date}"
						pattern="yyyy.MM.dd" /></td>
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
<div id="table_search">
	<input type="text" name="search" class="input_box" value="${search}">
	<input type="submit" value="검색" class="btn"> 
	<input type="button" value="HOME" onClick="location.href='main.do'" class="btn">
</div>
<div class="clear"></div>
<div id="page_control">

<c:if test="${pageInfoMap.allRowCount gt 0}">
<!-- 이전 블록이 존재하는지 확인 -->
	<c:if test="${pageInfoMap.startPage gt pageInfoMap.pageBlockSize}">
	<a href="boardAllShowForm.do?pageNum=${pageInfoMap.startPage - pageInfoMap.pageBlockSize}&search=${search}">[Prev] </a>
	</c:if>
	<c:forEach var="i" begin="${pageInfoMap.startPage}" end="${pageInfoMap.endPage}" step="1">
		<c:choose>
		<c:when test="${i eq pageInfoMap.pageNum}">
		<a href="boardAllShowForm.do?pageNum=${i}&search=${search}"> <span style="color: blue; font-weight: bold;">[${i}]</span> </a>
		</c:when>
		<c:otherwise>
		<a href="boardAllShowForm.do?pageNum=${i}&search=${search}"> [${i}] </a>
		</c:otherwise>
		</c:choose>
	</c:forEach>
	<!-- 다음 블록이 존재하는지 확인 -->
	<c:if test="${pageInfoMap.endPage lt pageInfoMap.maxPage}">
	<a href="boardAllShowForm.do?pageNum=${pageInfoMap.startPage + pageInfoMap.pageBlockSize}&search=${search}">[Next]</a>
	</c:if>
</c:if>
</div>
</form>
</article>
</body>
</html>