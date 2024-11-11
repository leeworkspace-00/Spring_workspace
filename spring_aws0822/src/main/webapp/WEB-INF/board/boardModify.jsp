<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ page import="com.myaws.myapp.domain.*" %>  

   <%
   BoardVo bv = (BoardVo)request.getAttribute("bv");			// 강제 형변환으로 양쪽의 타입을 맞춰주자
   %> 
  
    
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>글수정</title>
<link href="/resources/boardStyle.css" rel="stylesheet">

<script>
// 유효성검사 
	function saveBtn()  {
		
		var fm = document.frm;
		
		if(fm.subject.value=="") {
			alert("제목을 입력해주세요")
			fm.subject.focus();
			return;
		}else if(fm.contents.value==""){
			alert("내용을 입력해주세요")
			fm.contents.focus();
			return;
		}else if(fm.writer.value=="") {
			alert("작성자를을 입력해주세요")
			fm.writer.focus();
			return;
		}else if(fm.password.value=="") {
			alert("비밀번호를 입력해주세요")
			fm.password.focus();
			return;
		}
		let ans=confirm("저장하시겠습니까?");
		if(ans==true) {
			fm.action="<%=request.getContextPath()%>/board/boardModifyAction.aws";	
			fm.method="post";
			fm.enctype="multipart/form-data";
			fm.submit();
		}
		return;
		}
</script>

</head>
<body>
<header>
   <h2 class = "mainTitle">글수정</h2>
</header>
<form name="frm">
<input type="hidden" name="bidx" value="<%=bv.getBidx()%>">
	<table class="writeTable">

		<tr>
			<th>제목</th>
			<td><input type="text" name="subject" value="<%=bv.getSubject()%>"></td>
		</tr>
		<tr>
			<th>내용</th>
			<td><textarea name="contents" rows="6"><%=bv.getContents() %></textarea></td>
		</tr>
		<tr>
			<th>작성자</th>
			<td><input type="text" name="writer" value="<%=bv.getWriter()%>"></td>
		</tr>
		<tr>
			<th>비밀번호</th>
			<td><input type="password" name="password"></td>
		</tr>
		<tr>
			<th>첨부파일</th>
			<td><input type="file" name="attachfile"></td>
		</tr>
	</table>
	
   <div class = "btnBox">
      <button type="button" class="btn" onclick="saveBtn();">저장</button>
      <a class="btn aBtn" onclick="history.back();">취소</a>

   </div>
</form>
</body>
</html>