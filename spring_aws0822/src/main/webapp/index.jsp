<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
    <%@ taglib prefix = "c" uri ="http://java.sun.com/jsp/jstl/core"%>		<!-- jstl을 사용하겠다는 선언 어노테이션으로 -->
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>인덱스페이지</title>
</head>
<body>
<%-- 
<% if(session.getAttribute("midx") != null) {
	out.println(session.getAttribute("memberName")+"<a href='"+request.getContextPath()+"/member/memberLogout.aws'>로그아웃</a>"); 
} 


%>--%>
<c:if test="${!empty midx}">		<!--  midx 값이 비어있지 않으면 -->
	${memberName} 					<!--  회원 이름 칸 띄워 주고 로그아웃 기능 하도록 설정 -->
	&nbsp;<a href="${pageContext.request.contextPath}/member/memberLogout.aws">로그아웃
</c:if>

	<div>
		<a href = "${pageContext.request.contextPath}/member/memberJoin.aws">회원가입 페이지</a>
	</div>
	<div>
		<a href = "${pageContext.request.contextPath}/member/memberLogin.aws">회원로그인 페이지</a>
	</div>
	<div>
		<a href = "${pageContext.request.contextPath}/member/memberList.aws">회원목록 페이지</a>
	</div>
	<div>
		<a href = "${pageContext.request.contextPath}/board/boardList.aws">게시판목록 페이지</a>
	</div>

	

</body>
</html>