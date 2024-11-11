<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>인덱스페이지</title>
</head>
<body>

<% if(session.getAttribute("midx") != null) {
	out.println(session.getAttribute("memberName")+"<a href='"+request.getContextPath()+"/member/memberLogout.aws'>로그아웃</a>"); 
}


%>


	<div>
		<a href = "<%=request.getContextPath() %>/member/memberJoin.aws">회원가입 페이지</a>
	</div>
	<div>
		<a href = "<%=request.getContextPath() %>/member/memberLogin.aws">회원로그인 페이지</a>
	</div>
	<div>
		<a href = "<%=request.getContextPath() %>/member/memberList.aws">회원목록 페이지</a>
	</div>
	<div>
		<a href = "<%=request.getContextPath() %>/board/boardList.aws">게시판목록 페이지</a>
	</div>

	

</body>
</html>