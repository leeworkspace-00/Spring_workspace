<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<%
	String msg = "";
	if(request.getAttribute("msg")!=null) {
		msg = (String)request.getAttribute("msg"); 
	}
	%>
	
	



<!DOCTYPE HTML>
<HTML>
<HEAD>
<TITLE>로그인 페이지</TITLE>
<style>
header {
	width: 100%;
	height: 100px;
	text-align: center;
}

nav {
	width: 15%;
	height: 400px;
	float: left;
}

section {
	width: 70%;
	height: 400px;
	float: left;
}

aside {
	width: 15%;
	height: 400px;
	float: left;
}

footer {
	width: 100%;
	height: 150px;
	clear: both;
	text-align: center;
}

tr {
	display: inline-block;
}

table {
	border: 5px solid gray;
}
</style>

<script>





<%if(msg !=""){

out.println("alert('"+msg+"')"); // 
}
%>

//아이디 비밀번호 유효성 검사
	function check() {
	let memberid = document.getElementsByName("memberid");
	let memberpwd = document.getElementsByName("memberpwd");
	//alert(memberid);
	
	if(memberid[0].value=="") { // 아이디 입력란이 비어있으면
		alert("아이디를 입력해주세요"); // 알림창에 입력해주세요 출력
		memberid[0].focus(); // 다시 아이디 칸에 포커스 주기
		return;
	}else if(memberpwd[0].value=="") { // 비밀번호 입력란이 비어있으면 
		alert("비밀번호를 입력해주세요"); // 알림창에 입력해주세요 출력
		memberpwd[0].focus(); // 비밀번호 칸에 포커스 주기
		return;
		
	}
	
	var fm = document.frm;
	fm.action = "<%=request.getContextPath()%>/member/memberLoginAction.aws"; 
	fm.method = "post";
	fm.submit();
	return;
}

</script>

</HEAD>
<BODY>
	<header>회원로그인페이지</header>
	<nav></nav>
	<section>
		<article>

			<form name="frm" action=".test0920_result.html" method="post">
				<table style = "border= 10 width: 800px;">
					<tr>
						<td>아이디</td>
						<td>
						<input type="text" name="memberid" maxlength="30" style="width: 100px;" value="">
						</td>
						<td>비밀번호</td>
						<td>
						<input type="password" name="memberpwd" maxlength="30" style="width: 100px;" value="">
						</td>
					<tr>
						<td colspan=2 style="text-align: center;">
						<input type="button" name="btn" value="로그인" onclick= "check();">
						</td>
					</tr>
				</table>
			</form>

		</article>
	</section>
	<aside></aside>
	<footer> made by lee. </footer>
</BODY>
</HTML>
