<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>결과 페이지</title>
</head>
<body>
<br><br><br>
	<%
		String msg = (String)session.getAttribute("msg");
	
		if(msg != null)
		{
			if(msg.equals("0"))
				out.println("<font size='6'>회원정보가 수정되었습니다.</font>");
			else if(msg.equals("1"))
				out.println("<font size='6'>회원가입을 축하드립니다.</font>");
			
			session.removeAttribute("msg");
		}
		else
		{
			out.println("<font size='6'>회원정보가 삭제되었습니다.</font>");
		}
	%>
	<br><br>
	<input type="button" value="메인으로" onclick="main.do"/>
</body>
</html>