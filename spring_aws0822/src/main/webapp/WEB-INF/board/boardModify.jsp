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
<body style="width:800px;">
<h3>글수정</h3>
<hr>
<form name="frm">
<input type = "hidden" name="bidx" value="<%=bv.getBidx()%>">

	제목<input type="text" name="subject" value="<%=bv.getSubject()%>">
	<hr>
	내용<input type="text" name="contents" value="<%=bv.getContents() %>"  style="width:700px;height:250px; display:inline-block;">
	<hr>
	작성자<input type="text" name="writer" value="<%=bv.getWriter() %>" style="width:90px;height:40px; display:inline-block;">
	<hr>
	비밀번호<input type="password" name = "password">
	<hr>
	첨부파일
	<input type="button" name="filename" value = "<%=bv.getUploadedFilename()%>"> 선택된 파일 없음
	<hr>
	<input type="button"  value="저장" onclick="saveBtn();" style="float: right;">
	<input type="button"  value="취소" onclick="history.back();" style="float: right;">
		
	

</form>
</body>
</html>