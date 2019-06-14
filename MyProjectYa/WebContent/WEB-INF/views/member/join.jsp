<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link href="css/home.css" rel="stylesheet" type="text/css">
<link href="css/subpage1.css" rel="stylesheet" type="text/css">
<script src="script/jquery-3.3.1.min.js"></script>

<script>
function formCheck(){
	//ID는 3글자 이상 입력해야함
	if(frm.id.value.length<3){ //frm.id.value == ''
		alert('ID는 3글자 이상 입력해야 합니다.');
		frm.id.focus();
		return false;
	}
	
	
	if(frm.pass.value != frm.pass2.value){
		alert('패스워드 입력이 서로 다릅니다.\n다시 입력하세요.');
		frm.pass.focus();
		return false;
	}
	if(frm.email.value != frm.email2.value){
		alert('이메일주소 입력이 서로 다릅니다.\n다시 입력하세요.');
		frm.email.focus();
		return false;
	}
	return true;
}

function idDupCheck(){
	//id값이 공백이면 "아이디 입력하세요." 포커스 주기.
	var id=frm.id.value; //string 타입
	if(id.length==0){//id==''
		alert('아이디 입력하세요.');
		frm.id.focus();
		return;
	}
	//현재 창 기준으로 새 창 열기
	var childWindow=window.open('joinIdCheck.do?userid='+id,'','width=400,height=200');
}

$(document).ready(function(){
	$('input#idCheck').on('click', function(event){
		var id=$('input[name=id]').val();
		$.ajax({
			url:'joinIdCheckJson.do',
			data: {userid: id}, //userid=id입력값
			success: function(isDup){
				if(isDup){ //id중복
					$('span#idCheckValue').html('이미 존재하는 아이디입니다.').css('color','red');
				} else{
					$('span#idCheckValue').html('사용 가능한 아이디입니다.').css('color','green');
				}
			}
		});
	});
});
</script>
</head>
<body>
<div id="wrap">
<!-- 헤더들어가는 곳 -->
<jsp:include page="/WEB-INF/views/inc/top.jsp"></jsp:include>
<!-- 헤더들어가는 곳 -->

<!-- 본문들어가는 곳 -->
<!-- 본문메인이미지 -->
<div id="sub_img_member"></div>
<!-- 본문메인이미지 -->

<!-- 본문내용 -->
<article>
<h1>회원가입</h1>

<form action="joinProcess.do" method="post" id="join" name="frm" onsubmit="return formCheck()">

<fieldset>
<legend>Basic Info</legend>
<label>User ID</label>
<input type="text" name="id" class="id" required>
<input type="button" value="ID 중복확인" id="idCheck" class="dup">
<span id="idCheckValue"></span><br>
<label>Password</label>
<input type="password" name="pass" required><br>
<label>Retype Password</label>
<input type="password" name="pass2"><br>
<label>Name</label>
<input type="text" name="name" required><br>
<label>Birthday</label>
<input type="date" name="birthday"><br>
<label>Gender</label>
<p>
<input type="radio" name="gender" value="여">여자
<input type="radio" name="gender" value="남">남자<br>
</p>
<label>E-Mail</label>
<input type="email" name="email" required><br>
<label>Retype E-Mail</label>
<input type="email" name="email2"><br>
</fieldset>

<fieldset>
<legend>Optional</legend>
<label>Address</label>
<input type="text" name="address"><br>
<label>Phone Number</label>
<input type="text" name="phone"><br>
<label>Mobile Phone Number</label>
<input type="text" name="mobile"><br>
</fieldset>
<div class="clear"></div>
<div id="buttons">
<input type="submit" value="회원가입" class="submit">
<input type="reset" value="초기화" class="cancel">
</div>
</form>

</article>
<!-- 본문내용 -->
<!-- 본문들어가는 곳 -->

<div class="clear"></div>
<!-- 푸터들어가는 곳 -->
<jsp:include page="/WEB-INF/views/inc/bottom.jsp"></jsp:include>
<!-- 푸터들어가는 곳 -->
</div>
</body>
</html>