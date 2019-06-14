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
<h1>파일 게시글 삭제</h1>

<form action="boardFileDeleteProcess.do" method="post" name="frm">
<input type="hidden" name="num" value="<%=request.getParameter("num")%>">
<input type="hidden" name="pageNum" value="<%=request.getParameter("pageNum")%>">
<table id="notice">
<tr>
<th>비밀번호</th>
<td><input type="password" name="pass"></td>
</tr>     
</table>


<div id="table_search">
<input type="submit" value="파일글삭제" class="btn">
<input type="reset" value="다시작성" class="btn">
<input type="button" value="글목록" class="btn" onclick="location.href='boardFileList.do?pageNum=<%=request.getParameter("pageNum")%>';">
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