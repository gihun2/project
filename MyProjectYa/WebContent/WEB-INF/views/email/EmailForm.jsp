<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>관리자 파일 이메일 쓰기</title>
</head>
<body>
<h1>관리자 파일 이메일</h1>
<form action="mailProcess.do" method="post" enctype="multipart/form-data">
받는사람 이메일주소: <input type="text" name="receiver" required><br>
<br>
<br>
메일 제목: <input type="text" name="subject" required><br>
<br>
<br>
첨부 파일: <input type="file" name="filename"><br>
<br>
메일 내용: <textarea rows="7" cols="40" name="content" required></textarea><br>
<br>
<input type="submit" value="이메일 전송" class="submit">
</form>
<input type="button" value="HOME" onclick="location.href='main.do'">
</body>
</html>