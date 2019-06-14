<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="dao.MemberDao"%>
<%@ page import="vo.Smember"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원정보 수정화면</title>
</head>
<body>
<%
Smember member=(Smember)request.getAttribute("member");
String id=request.getParameter("id"); //id 파라미터값 가져오기
%>
	<br>
	<br>
	<b><font size="6" color="gray">회원정보 수정</font></b>
	<br>
	<br>
	<br>

	<!-- 입력한 값을 전송하기 위해 form 태그를 사용한다 -->
	<!-- 값(파라미터) 전송은 POST 방식 -->
<form method="post" action="memberModifyProcess.do" name="userInfo"
	onsubmit="return checkValue()">
<input type="hidden" name="id" value="<%=id%>">
	<table>
		<tr>
			<td id="title">아이디</td>
			<td id="title"><input type="text" name="id" maxlength="50"
				value="<%=member.getId()%>"></td>
		</tr>
		<tr>
			<td id="title">비밀번호</td>
			<td><input type="password" name="password" maxlength="50"
				value="<%=member.getPassword()%>"></td>
		</tr>
	</table>
	<br>
	<br>
	<table>

		<tr>
			<td id="title">이름</td>
			<td><input type="text" name="name" maxlength="50"
				value="<%=member.getName()%>"></td>
		</tr>

		<tr>
			<td id="title">성별</td>
			<td><input type="text" name="gender" maxlength="50"
				value="<%=member.getGender()%>"></td>
		</tr>

		<tr>
			<td id="title">생일</td>
			<td><input type="date" name="birthday"
				value="<%=member.getBirthday()%>"></td>
		</tr>

		<tr>
			<td id="title">이메일</td>
			<td>
			<input type="text" name="Email" maxlength="50" value="<%=member.getEmail()%>"> 
			</td>
		</tr>

		<tr>
			<td id="title">집전화</td>
			<td>
			<input type="text" name="tel" maxlength="50" value="<%=member.getTel()%>"> 
			</td>
		</tr>

		<tr>
			<td id="title">휴대전화</td>
			<td>
			<input type="text" name="Mtel" value="<%=member.getMtel() %>"/>
			</td>
		</tr>
		<tr>
			<td id="title">주소</td>
			<td>
			<input type="text" size="50" name="address" value="<%=member.getAddress()%>" />
			</td>
		</tr>
	</table>
	<br>
	<br> 
	<input type="submit" value="수정" />
</form>
<input type="button" value="HOME" onClick="location.href='main.do'">
</body>
</html>