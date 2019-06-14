<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	//세션값 가져오기
	String id = (String) session.getAttribute("id");
%>
<header>
	<div id="login">
		<%
			boolean login = false;
			Cookie[] cookies = request.getCookies();
			if (cookies != null) {
				for (Cookie tempCookie : cookies) {
					if (tempCookie.getName().equals("Project_ID")) {
						String userID = tempCookie.getValue();
						String id2 = null;
						id2 = (String) session.getAttribute(userID);
						if (id != null) {
							login = true;
		%><%=id%>님 반가워요~ <a href="logout.do">로그아웃</a>
		<%
										}
						}
					}
				}
		%>
		<%
			if (login == false) {
		%>
		<a href="loginForm.do">로그인</a>
		<%
			}
		%>
		| <a href="joinForm.do">회원가입</a>
	</div>
	<div class="clear"></div>
	<!-- 로고들어가는 곳 -->
	<div id="logo">
		<img src="images/song.jpg" width="200" height="55" alt="Song">
	</div>
	<!-- 로고들어가는 곳 -->
	<nav id="top_menu">
		<%
			if (session.getAttribute("id") != null && session.getAttribute("id").equals("admin")) {
		%>
		<ul>
			<li><a href="main.do">홈</a></li>
			<li><a href="welcome.do">노래</a></li>
			<li><a href="boardList.do">게시판</a></li>
			<li><a href="memberList.do">회원보기</a></li>
			<li><a href="boardAllShowForm.do">게시글 관리</a></li>
			<li><a href="columnChart.do">통계</a></li>
			<li><a href="mail.do">메일</a></li>
		</ul>
		<%
			} else {
		%>
		<ul>
			<li><a href="main.do">홈</a></li>
			<li><a href="welcome.do">노래</a></li>
			<li><a href="boardList.do">게시판</a></li>
		</ul>
		<%
			}
		%>
	</nav>
</header>