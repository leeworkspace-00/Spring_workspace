<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%String bidx = request.getAttribute("bidx").toString(); %>
    <%@ page import="com.myaws.myapp.domain.*" %>  
    <%@ taglib prefix = "c" uri ="http://java.sun.com/jsp/jstl/core"%>	    
    
<%-- <%
String msg= "";

if (request.getAttribute("msg") != null){
 msg = (String)request.getAttribute("msg");
}
if (msg !=""){
	 out.println("<script>alert('"+msg+"');</script>");	
	 }
%>
    
     --%>
    
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>글삭제</title>
<link href="/resources/boardStyle.css" rel="stylesheet">

<script>
function DeleteBtn() {
	let fm = document.frm;

	
	if(fm.password.value=="") {
		alert("비밀번호란이 공란입니다");
		fm.password.focus();
		return;
	}
	
	let ans=confirm("삭제하시겠습니까?");	// 함수의 값을 참과 거짓 true false로 나눈다 
	if(ans==true) {
		fm.action="${pageContext.request.contextPath}/board/boardDeleteAction.aws";
		fm.method="post";
		fm.submit();
	}
	return;
	}

</script>


</head>
<body>

<header>
	<h2 class="mainTitle">글삭제</h2>
</header>

<form name="frm">
<input type = "hidden" name = "bidx" value = "${bv.bidx}">

	<table class = "writeTable">
		<tr>
			<th>비밀번호</th>
			<td><input type="password" name="password"></td>
		</tr>
		
	</table>
	
	<div class="btnBox">
		<button type="button" class="btn" onclick="DeleteBtn();">삭제</button>
		<a class="btn aBtn" href="#"  onclick="history.back();">취소</a>
	</div>

</form>
	
</body>
</html>