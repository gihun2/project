<%@page import="vo.Sboard"%>
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
<h1>파일 게시판</h1>
<%
	//페이지번호 파라미터 가져오기
String pageNum=request.getParameter("pageNum");

Sboard board=(Sboard)request.getAttribute("board");
%>
<table id="notice">
<tr>
<th width="50px">글제목</th>
<td colspan="3" class="left"><%=board.getSubject() %></td>
</tr>
<tr>
<th>파일</th>
<td colspan="3" class="left">
<a href="upload/<%=board.getFilename() %>"><%=board.getFilename()%></a>
<br>
<%
String ext=(String)request.getAttribute("ext");
if(ext.equalsIgnoreCase("jpg")
	|| ext.equalsIgnoreCase("gif")
	|| ext.equalsIgnoreCase("png")){
	%>
	<img src="upload/<%=board.getFilename() %>" style="width: 50px; height: 50px;">
	<%	
}
%>
</td>
</tr>
<tr>
<th>글번호</th>
<td><%=board.getNum() %></td>
<th>조회수</th>
<td><%=board.getReadcount() %></td>
</tr>
<tr>
<th>작성자</th>
<td><%=board.getName() %></td>
<th>작성일</th>
<td><%=board.getReg_date() %></td>
</tr>     

<tr>
<td colspan="4" class="left"><%=board.getContent() %></td>
</tr>
</table>

<div id="table_search">
<%
String id=(String)session.getAttribute("id");
// 세션값 있으면(로그인 했으면) 수정,삭제,답글쓰기 보이게 설정
if(id != null){
	%>
	<input type="button" value="글수정" class="btn" onclick="location.href='boardFileModifyForm.do?num=<%=board.getNum()%>&pageNum=<%=pageNum%>';">
	<input type="button" value="글삭제" class="btn" onclick="location.href='boardFileDeleteForm.do?num=<%=board.getNum()%>&pageNum=<%=pageNum%>';">
	<input type="button" value="답글쓰기" class="btn" onclick="location.href='boardReplyForm.do?re_ref=<%=board.getRe_ref()%>&re_lev=<%=board.getRe_lev()%>&re_seq=<%=board.getRe_seq()%>&pageNum=<%=pageNum%>';">
	<%
}
%>
<input type="button" value="글목록" class="btn" onclick="location.href='boardFileList.do?pageNum=<%=pageNum%>';">
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