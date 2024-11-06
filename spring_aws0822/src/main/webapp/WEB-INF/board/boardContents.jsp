<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ page import="com.myaws.myapp.domain.*" %>  
    

 <% 
 BoardVo bv = (BoardVo)request.getAttribute("bv"); 

 %>  
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>글내용</title>
<link href="../board/boardStyle.css" rel="stylesheet">
<script src="https://code.jquery.com/jquery-latest.min.js"></script> <!-- mvc jquery-CDN주소 추가 추천이랑 조회수 때문에 j쿼리가 필요함  -->
<script>

$(document).ready(function(){
	
	$("#btn").click(function() {		// 추천버튼 클릭 함수
		$.ajax({
			type :"get",	//	전송방식 : get방식으로 전송하겠다 선언
			url : "<%=request.getContextPath()%>/board/boardRecom.aws?bidx=<%=bv.getBidx()%>",
			dataType : "json",		//json : 문서에서 {"키값":"value값","키값2:"value값2"}
			success : function(result) {		// 결과가 넘어와서 성공한 경우 받는 영역
				//alert("전송성공 테스트");
			
				var str ="추천("+result.recom+")";
				$("#btn").val(str);
			},
			error : function(){  //결과가 실패했을때 받는 영역
				alert("전송실패 테스트");	
			}
	
		});
		

	});
	
});
</script>
</head>
<body>
<header>
	<h2 class = "mainTitle">글내용</h2>
</header>

<article class = "detailContents">
	<h2 class = "contentTitle"><%=bv.getSubject() %> (조회수:<%=bv.getViewcnt() %>)
	<input type = "button" id = "btn" value = "추천(<%=bv.getRecom() %>)">
	</h2>
	
	<p class="write"><%=bv.getWriter() %> (<%=bv.getWriteday() %>)</p>
	<hr>
	<div class="content">
		<%=bv.getContents() %>	
	</div>
	
	<% if (bv.getFilename() == null || bv.getFilename().equals("") ) {}else{ %>	
	<img src="<%=request.getContextPath() %>/images/<%=bv.getFilename() %>">	
	<p>
	<a href="<%=request.getContextPath() %>/board/boardDownload.aws?filename=<%=bv.getFilename() %>" class="fileDown">	
	첨부파일 다운로드</a>
	</p>	
	<%} %>

</article>



<div class="btnBox">
	<a class="btn aBtn" href="<%=request.getContextPath() %>/board/boardModify.aws?bidx=<%=bv.getBidx()%>">수정</a>
	<a class="btn aBtn" href="<%=request.getContextPath() %>/board/boardDelete.aws?bidx=<%=bv.getBidx()%>">삭제</a>
	<a class="btn aBtn" href="<%=request.getContextPath() %>/board/boardReply.aws?bidx=<%=bv.getBidx()%>">답변</a>
	<a class="btn aBtn" href="<%=request.getContextPath() %>/board/boardList.aws">목록</a>
</div>



</body>
</html>