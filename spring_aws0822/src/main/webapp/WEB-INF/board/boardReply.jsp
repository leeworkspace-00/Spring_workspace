<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ page import="com.myaws.myapp.domain.*" %>  
    
<%
 int bidx = (int)request.getAttribute("bidx");
 int originbidx = (int)request.getAttribute("originbidx");
 int depth = (int)request.getAttribute("depth");
 int level_ = (int)request.getAttribute("level_");
 %>
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>글답변</title>
<link href="/resources/boardStyle.css" rel="stylesheet">

<script>
	function saveBtn()  {
		
		let fm = document.frm;
		
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
		let ans = confirm("댓글을 남기시겠습니까?");
		  
		  if (ans == true) {
			  fm.action="<%=request.getContextPath()%>/board/boardReplyAction.aws";
			  fm.method="post";
			  fm.enctype="multipart/form-data";
			  fm.submit();
		  }	  
		  
		  return;
	}
	
		
</script>




</head>
<body style="width:800px;">
<h3>글답변</h3>
<hr>
<form name = "frm">
	<input type = "hidden" name = "bidx" value = "<%=bidx%>">
	<input type = "hidden" name = "originbidx" value = "<%=originbidx%>">
	<input type = "hidden" name = "depth" value = "<%=depth%>">
	<input type = "hidden" name = "level_" value = "<%=level_%>">

	제목<input type="text" name="subject">
	<hr>
	내용<input type="text" name="contents"  style="width:700px;height:250px; display:inline-block;">
	<hr>
	작성자<input type="text" name="writer" style="width:90px;height:40px; display:inline-block;">
	<hr>
	비밀번호<input type="password" name = "password">
	<hr>
	첨부파일<input type="file" name="filename">
	<input type="button" name="filename" value="파일선택">
	<hr>
	<input type="button" value="저장" onclick="saveBtn();" style="float: right;">
	<input type="button" value="취소" onclick="history.back();" style="float: right;">

</form>
</body>
</html>