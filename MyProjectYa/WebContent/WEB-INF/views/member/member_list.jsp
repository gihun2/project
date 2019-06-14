<%@page import="java.text.SimpleDateFormat"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<%@ page import="java.util.*"%>
<%@ page import="vo.Smember"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원 리스트 - 관리자 화면</title>
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
	function change1(aaa) {
		aaa.style.background = 'red';
		aaa.style.color = 'white';
	}
	function change2(aaa) {
		aaa.style.background = 'white';
		aaa.style.color = 'black';
	}
</script>
</head>
<body>
	<article>
		<br> <br>
		<div>
			<form action="memberChoiceDelete.do" method="post">
				<b><font size="6" color="gray">전체 회원정보</font></b> 
				<input type="button" value="HOME" onclick="location.href='main.do'"
					class="btn"> 
					<input type="submit" value="삭제" class="Alldelete" >
				<br> <br>
				<table cellpadding="2">
					<tr align="center">
						<td>아이디</td>
						<td>비밀번호</td>
						<td>이름</td>
						<td>성별</td>
						<td>생년월일</td>
						<td>이메일</td>
						<td>주소</td>
						<td>전화번호</td>
						<td>폰번호</td>
						<td>가입일</td>
						<td>삭제</td>
					</tr>
					<c:choose>
						<c:when test="${not empty list}">
							<c:forEach var="member" items="${list}">
								<tr onmouseout="change2(this)" onmouseover="change1(this)"
									style="background-color: white;">
									<td onclick="location.href='memberInfo.do?id=${member.id}';">${member.id}</td>
									<td>${member.password}</td>
									<td>${member.name}</td>
									<td>${member.gender}</td>
									<td><fmt:formatDate value="${member.birthday}"
											pattern="yyyy.MM.dd" /></td>
									<td>${member.email}</td>
									<td>${member.address}</td>
									<td>${member.tel}</td>
									<td>${member.mtel}</td>
									<td>${member.reg_date}</td>
									<td><input type="checkbox" name="choiceDelete" value="${member.id}"></td>
								</tr>
							</c:forEach>
						</c:when>
					</c:choose>
				</table>
			</form>
		</div>
		
		<div id="member_search">
			<form action="memberList.do" method="get">
				<input type="text" name="search" class="input_box" value="${search}">
				<input type="submit" value="검색" class="btn">
			</form>
		</div>
		
		<div id="page_control">
			<c:if test="${pageInfoMap.allRowCount gt 0}">
				<!-- 이전 블록이 존재하는지 확인 -->
				<c:if test="${pageInfoMap.startPage gt pageInfoMap.pageBlockSize}">
					<a
						href="memberList.do?pageNum=${pageInfoMap.startPage - pageInfoMap.pageBlockSize}&search=${search}">[Prev]
					</a>
				</c:if>
				<c:forEach var="i" begin="${pageInfoMap.startPage}"
					end="${pageInfoMap.endPage}" step="1">
					<c:choose>
						<c:when test="${i eq pageInfoMap.pageNum}">
							<a href="memberList.do?pageNum=${i}&search=${search}"> <span
								style="color: blue; font-weight: bold;">[${i}]</span>
							</a>
						</c:when>
						<c:otherwise>
							<a href="memberList.do?pageNum=${i}&search=${search}"> [${i}]
							</a>
						</c:otherwise>
					</c:choose>
				</c:forEach>
				<!-- 다음 블록이 존재하는지 확인 -->
				<c:if test="${pageInfoMap.endPage lt pageInfoMap.maxPage}">
					<a
						href="memberList.do?pageNum=${pageInfoMap.startPage + pageInfoMap.pageBlockSize}&search=${search}">[Next]</a>
				</c:if>
			</c:if>
		</div>
	</article>
</body>
</html>