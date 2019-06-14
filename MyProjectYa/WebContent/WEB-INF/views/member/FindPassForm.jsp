<%@page import="vo.Smember"%>
<%@page import="org.apache.commons.mail.SimpleEmail"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>비밀번호 찾기 폼</title>
<link href="css/home.css" rel="stylesheet" type="text/css">
<link href="css/subpage1.css" rel="stylesheet" type="text/css">
</head>
<body>
	<br>
	<br>
	<b><font size="6" color="white">비밀번호 찾기</font></b>
	<br>
	<br>
	<br>

	<!-- 입력한 값을 전송하기 위해 form 태그를 사용한다 -->
	<!-- 값(파라미터) 전송은 POST 방식 -->
	<form method="post" action="findPassProcess.do" id="findPass">
		<fieldset>
		<legend>Basic Info</legend>
		<label>User ID</label>
		<input type="text" name="id" class="id" required> <br>
		<label>Name</label>
		<input type="text" name="name" required><br>
		<label>Birthday</label>
		<input type="date" name="birthday" required><br>
		<label>Gender</label>
		<p>
		<input type="radio" name="gender" value="여" required>여자
		<input type="radio" name="gender" value="남" required>남자<br>
		</p>
		<label>E-Mail</label>
		<input type="email" name="email" required><br>
		</fieldset>
		
		<div class="clear"></div>
		<div id="buttons">
		<input type="submit" value="비밀번호 찾기" class="submit">
		<input type="reset" value="초기화" class="cancel">
		<input type="button" value="HOME" onClick="location.href='main.do'" >
		</div>
	</form>
</body>
</html>