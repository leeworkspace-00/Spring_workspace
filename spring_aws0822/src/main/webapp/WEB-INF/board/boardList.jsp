<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.util.*" %>   
<%@ page import="com.myaws.myapp.domain.*" %>  
<%@ taglib prefix = "c" uri ="http://java.sun.com/jsp/jstl/core"%>		<!-- jstl을 사용하겠다는 선언 어노테이션으로 -->

<%--  <%
 ArrayList<BoardVo> blist = (ArrayList<BoardVo>)request.getAttribute("blist"); // 형변환 때문에 경고라인 뜬거 걱정 말기
	 	
	PageMaker pm = (PageMaker)request.getAttribute("pm");
	int totalCount = pm.getTotalCount();
	String keyword= pm.getScri().getKeyword();
	String searchType = pm.getScri().getSearchType();  jsp 코드 빼는 과정
	String param = "keyword="+keyword+"&searchType="+searchType+"";		
 %> --%>
 
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>글목록</title>
<link href="/resources/boardStyle.css" rel="stylesheet">
<script>
	function postwrite() {
		var link = '${pageContext.request.contextPath}/board/boardWrite.aws';		
		location.href =link;	
	}
</script>
</head>

<body style="width: 800px; margin: 0 auto;">
<header>
	<h2 class = "mainTitle">글목록</h2>
	<form class="search" name="frm" action="${pageContext.request.contextPath}/board/boardList.aws" method="get">
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
<%-- 		<%
		int num  = totalCount - (pm.getScri().getPage()-1)*pm.getScri().getPerPageNum();	
		//for(BoardVo bv : blist) { 			
		
			String lvlStr = "";
			for(int i=1;i<=bv.getLevel_(); i++){
				
				lvlStr = lvlStr +"&nbsp;&nbsp;";
				
				if (i == bv.getLevel_()){
					lvlStr  = lvlStr + "ㄴ";
				}
			}			
		%>
		 --%>
		
		<c:forEach items ="${blist}" var = "bv" varStatus = "status" >
		<!--  글 목록 보여주는 코드 -->

		<tr>
			<td>${pm.totalCount-(status.index+(pm.scri.page-1)*pm.scri.perPageNum) }</td>
			<td class="title">
			
			<%-- <%=lvlStr %> --%>
			<c:forEach var = "i" begin = "1" end = "${bv.level_}" step = "1">
				&nbsp;&nbsp;
				<c:if test="${i==bv.level_}">
					└
				</c:if>
			</c:forEach>
			
			<a href="${pageContext.request.contextPath}/board/boardContents.aws?bidx=${bv.bidx}">${bv.subject}</a></td>
			<td>${bv.writer}</td>
			<td>${bv.viewcnt}</td>
			<td>${bv.recom}</td>
			<td>${bv.writeday}</td>
		</tr>
		
		</c:forEach>
</table>
		
<%-- 		<%
		 num = num-1; // 여기는 한 페이지에서 15번까지 돌릴때 
		 // 게시물 한개 제목 작성자 번호 등등 찍어줬으면 다음 번호 찍도록 -1 해준것
			}
		%> --%>
			
		
	
	
	<div class="btnBox">
		<a class="btn aBtn" href="${pageContext.request.contextPath}/board/boardWrite.aws">글쓰기</a>
	</div>
	
	<c:set var = "queryParam" value = "keyword=${pm.scri.keyword}&searchType=${pm.scri.searchType}"></c:set>
	<div class = "page">
	<!--  페이징  -->
		<ul>
		<!-- 이전 페이지 버튼 -->
		<c:if test="${pm.prev == ture}">
			<li><a href="${pageContext.request.contextPath}/board/boardList.aws?page=${pm.startPage-1}&${queryParam}">◀</a></li>
		</c:if>
		
		<!--  현재 페이지  -->
		<c:forEach var = "i" begin = "${pm.startPage}" end = "${pm.endPage}" step = "1">
			<li <c:if test="${i==pm.scri.page}">class='on'</c:if>>
				<a href="${pageContext.request.contextPath}/board/boardList.aws?page=${i}&${queryParam}">
				<span style="font-size:20px;"> ${i}</span></a>
			</li>
			</c:forEach>
			
			<!-- 다음페이지 버튼 -->
		
		<c:if test="${pm.next&&pm.endPage>0 }">
		<li>
			<a href="${pageContext.request.contextPath}/board/boardList.aws?page=${pm.endPage+1}&${queryParam}">▶</a></li>
		</c:if>
		
	</ul>
</div>
			
		
		


		<%-- <% if (pm.isPrev()==true) { %>
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
		<%} %> --%>

		
	




</section>



</body>
</html>