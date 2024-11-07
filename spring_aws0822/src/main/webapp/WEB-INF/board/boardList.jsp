<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@page import="java.util.*" %>   
     <%@ page import="com.myaws.myapp.domain.*" %>  

 <%
 ArrayList<BoardVo> blist = (ArrayList<BoardVo>)request.getAttribute("blist"); // 형변환 때문에 경고라인 뜬거 걱정 말기
	//System.out.println("뭐가 나오나?"+alist); > 객체생성된거 확인해보기  인덱스창에서 목록 간다음 콘솔창 확인 	
	PageMaker pm = (PageMaker)request.getAttribute("pm");	// 여기서 pm 객체 꺼내옴
	int totalCount = pm.getTotalCount();
	String keyword= pm.getScri().getKeyword();		// 검색어
	String searchType = pm.getScri().getSearchType();	// 검색타입 제목 작성자 등
	String param = "keyword="+keyword+"&searchType="+searchType+"";		// 파라미터 변수에 검색이랑 검색종류 가지고 다닐거임		
 %>
 
 
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>글목록</title>
<link href="/resources/boardStyle.css" rel="stylesheet">
<script>
	function postwrite() {
		var link = '<%=request.getContextPath() %>/board/boardWrite.aws';		
		location.href =link;	
	}
</script>
</head>

<body style="width: 800px; margin: 0 auto;">
<header>
	<h2 class = "mainTitle">글목록</h2>
	<form class="search" name="frm" action="<%=request.getContextPath() %>/board/boardList.aws" method="get">
		<select name="searchType">
			<option value="subject">제목</option>
			<option value="writer">작성자</option>
		</select>
		<input type="text" name="keyword">
		<button  type="submit" class="btn">검색</button>
	</form>
</header>
<section>
<table class="listTable">
		<tr>
			<th>No</th>
			<th>제목</th>
			<th>작성자</th>
			<th>조회</th>
			<th>추천</th>
			<th>날짜</th>
		</tr>
		<%
		int num  = totalCount - (pm.getScri().getPage()-1)*pm.getScri().getPerPageNum();	
		for(BoardVo bv : blist) { 			
		
			String lvlStr = "";
			for(int i=1;i<=bv.getLevel_(); i++){
				
				lvlStr = lvlStr +"&nbsp;&nbsp;";
				
				if (i == bv.getLevel_()){
					lvlStr  = lvlStr + "ㄴ";
				}
			}			
		%>
		<tr>
			<td><%=num %></td>
			<td class="title">
			<%=lvlStr %>
			<a href="<%=request.getContextPath() %>/board/boardContents.aws?bidx=<%=bv.getBidx() %>"><%=bv.getSubject() %></a></td>
			<td><%=bv.getWriter() %></td>
			<td><%=bv.getViewcnt()%></td>
			<td><%=bv.getRecom()%></td>
			<td><%=bv.getWriteday() %></td>
		</tr>
		<%
		 num = num-1; // 여기는 한 페이지에서 15번까지 돌릴때 
		 // 게시물 한개 제목 작성자 번호 등등 찍어줬으면 다음 번호 찍도록 -1 해준것
		 
		}
		
		%>
		
	</table>
	
	<div class="btnBox">
		<a class="btn aBtn" href="<%=request.getContextPath() %>/board/boardWrite.aws">글쓰기</a>
	</div>
	
	
	<div class = "page">
		<ul>
		
		<% if (pm.isPrev()==true) { %>
		<li><a href="<%=request.getContextPath() %>/board/boardList.aws?page=<%=pm.getStartPage()-1%>&<%=param%>">◀</a></li>
		<%} %>
		
		<% for(int i = pm.getStartPage();i<=pm.getEndPage();i++) { %>
			<li
			<%if (i==pm.getScri().getPage()) {%> class="on"<%}%> > 
				<a href="<%=request.getContextPath() %>/board/boardList.aws?page=<%=i%>&<%=param%>">
				<span style="font-size:20px;"> <%=i %></span></a>
			</li>
		<%} %>
		
		<%if(pm.isNext() == true && pm.getEndPage()>0){ %>
		<li>
			<a href="<%=request.getContextPath() %>/board/boardList.aws?page=<%=pm.getEndPage()+1%>&<%=param%>">▶</a></li>
		<%} %>

		</ul>
	
	</div>



</section>



</body>
</html>