<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="com.myaws.myapp.domain.*" %>
<%
//ArrayList 객체를 화면까지 가져왔다
ArrayList<MemberVo> alist = (ArrayList<MemberVo>) request.getAttribute("alist");
//System.out.println("첫객체 아이디 출력?" +  alist.get(0).getMemberid());
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원목록보기</title>
<style>
table {
	border: 1px solid blue;
	border-collapse: collapse; /* 중복된 테두리 합치기 */
}

th, td {
	border: 1px solid green;
	padding: 10px;
}

th {
	width: 100px;
	height: 40px;
}

td {
	width: 100px;
	height: 20px;
	text-align: right;
}

thead {
	background: violet;
	color: white;
}

tfoot {
	border-bottom: 1px solid gray;
}

tbody tr:nth-child(even) {
	background: aliceblue;
}

tbody tr:hoober {
	background: black;
}
</style>
</head>
<body>
	<h3>회원목록</h3>
	<hr>
	<table>
		<thead>
			<tr>
				<th>회원번호</th>
				<th>회원아이디</th>
				<th>회원이름</th>
				<th>성별</th>
				<th>가입일</th>
			</tr>
		</thead>
		<tbody>
		
		<% 
		
		
		int num =1;
		for (MemberVo mv:alist) { %>
			<tr>
				<td><%=mv.getMidx() %></td>
				<td><%=mv.getMemberid() %></td>
				<td><%=mv.getMembername() %></td>
				<td><%=mv.getMembergender() %></td>
				<td><%=mv.getWriteday().substring(0, 10) %></td>
			</tr>
			<% 
			num=num+1;
		
		} %>

		</tbody>
		<tfoot>
			<tr>
				<td colspan="5">총 <%=num %>명입니다.</td>
			</tr>
		</tfoot>
	</table>
</body>
</html>