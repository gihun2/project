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
<h1>Notice Write</h1>

<form action="boardWriteProcess.do" method="post" name="frm">
<table id="notice">
<tr><th>작성자명</th><td><input type="text" name="name"></td></tr>
<tr><th>비밀번호</th><td><input type="password" name="pass"></td></tr>     
<tr><th>글제목</th><td><input type="text" name="subject"></td></tr>
<tr>
<th>글내용</th>
<td><textarea rows="13" cols="40" name="content"></textarea></td>
</tr>
</table>


<div id="table_search">
<input type="submit" value="글쓰기" class="btn">
<input type="reset" value="다시작성" class="btn">
<input type="button" value="글목록" class="btn" onclick="location.href='boardList.do';">
</div>
</form>
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