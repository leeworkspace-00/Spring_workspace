<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
   <%@ page import="com.myaws.myapp.domain.*" %>  


 <% 
 BoardVo bv = (BoardVo)request.getAttribute("bv");   //강제형변환  양쪽형을 맞춰준다 
 
 String memberName = "";
 if (session.getAttribute("memberName") !=null){
	 memberName = (String)session.getAttribute("memberName");
 }
 int midx=0;
 if (session.getAttribute("midx") !=null){
	 midx = Integer.parseInt(session.getAttribute("midx").toString());
 }

 %>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>글내용</title>
<link href="/resources/boardStyle.css" rel="stylesheet">
<script src="https://code.jquery.com/jquery-latest.min.js"></script> <!-- mvc jquery-CDN주소 추가  -->
<script>

function checkImageType (fileName) {
	var  pattern = /jpg$|gif$|png$|jpeg$/i // 자바스크립트 정규 표현식 이런 파일들을 취급하겠다는 의미

	return fileName.match(pattern);
}

function getOriginalFileName(fileName){				// 실제 원본 파일 이름 추출하는 메서드
	
	var idx = fileName.lastIndexOf("_")+1;	// 언더바로 자른 마지막 순서 +1 해줘서 언더바 다음에 오는 문자들을 가져오겠다(그 문자가 파일 원본이름이기때문)
	
	return fileName.substr(idx);
	
}
function getImageLink(fileName) {		// 이미지 링크 가져오는 메서드
	var front = fileName.substr(0,12);
	var end = fileName.substr(14); 		//13번(s-) 빼고 전체 다가져와 붙여서 리턴할거임
	
	return front+end;
}


function download_() {
	//주소 사이에 s- 빼고 전체 파일이름추출 
	
	
	var downloadImage = getImageLink("<%=bv.getFilename()%>");		
	//alert(downloadImage);
	var downloadLink =  "<%=request.getContextPath() %>/board/displayFile.aws?fileName="+downloadImage+"&down=1";
	//alert(downloadLink);
	
	

	return downloadLink;
}


function commentDel(cidx)  {
	let ans = confirm("삭제하시겠습니까?");
	if(ans == true) {		// 삭제하겠다면 ?
			
		$.ajax({
			type :"get",	//	전송방식 : get방식으로 전송하겠다 선언
			url : "<%=request.getContextPath()%>/comment/commentDeleteAction.aws?cidx="+cidx,
			dataType : "json",		//json : 문서에서 {"키값":"value값","키값2:"value값2"}
			success : function(result) {		// 결과가 넘어와서 성공한 경우 받는 영역
				
				$.boardCommentList();
			},
			error : function(){  //결과가 실패했을때 받는 영역
				//alert("전송실패 테스트");
			}
		});	
	}

	return;
}

$.boardCommentList = function() {		// jquery 함수 만드는 문법 앞에 이름 = function(){~}
	$.ajax({	// 댓글쓰기 버튼
		type :"get",	//	전송방식 : get방식으로 전송하겠다 선언
		url : "<%=request.getContextPath()%>/comment/commentList.aws?bidx=<%=bv.getBidx()%>",
		dataType : "json",	
		success : function(result) {
			//alert("전송성공 테스트");
			
			var strTr = "";
				
				$(result).each(function(){
					var btnn="";
					
					 //현재로그인 사람과 댓글쓴 사람의 번호가 같을때만 나타내준다
					if (this.midx == "<%=midx%>") {
						if (this.delyn=="N"){
							btnn= "<button type='button' onclick='commentDel("+this.cidx+");'>삭제</button>";
						}			
					}
					
					
					
					
					strTr = strTr+"<tr>"
					+"<td>"+this.cidx+"</td>"
					+"<td>"+this.cwriter+"</td>"
					+"<td class='content'>"+this.ccontents+"</td>"
					+"<td>"+this.writeday+"</td>"
					+"<td>"+btnn+"</td>"
					+"</tr>";
				});
		
				var str = "<table class='replyTable'>"
				+"<tr>"
				+"<th>번호</th>"
				+"<th>작성자</th>"
				+"<th>내용</th>"
				+"<th>날짜</th>"
				+"<th>DEL</th>"
				+"</tr>"+strTr+"</table>";
				
		$("#commentListView").html(str);	
			},
			error : function(){  //결과가 실패했을때 받는 영역
				//alert("전송실패 테스트");	
			}
	
		});
	
	}
$(document).ready(function(){
			//  시작하자마자 동작해야함 페이지 들어오자마자 댓글 있는거 보여줘야함
	
	$("#dUrl").html(getOriginalFileName("<%=bv.getFilename()%>"));	
			// 실제 파일이름 보여주는 j쿼리
			
	$("#dUrl").click(function(){	
		$("#dUrl").attr("href",download_());
		return;
		
	});
	$.boardCommentList();
	
	
	$("#btn").click(function() {		// 추천버튼 클릭 함수
		//alert("추천버튼을 클릭했습니다");
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
				//alert("전송실패 테스트");	
			}
	
		});
		

	});
		
	$("#cmtBtn").click(function(){		// 댓글쓰기 버튼
		
		//alert("댓글버튼확인");
		let loginCheck = "<%=midx%>";
		if (loginCheck == "" || loginCheck == "null" || loginCheck == null){		//로그인 체크하고
			alert=("로그인해주세요");			
			return;
		}

		let cwriter =$("#cwriter").val();	// 작성자 데이터 담고 
		let ccontents =$("#ccontents").val(); // 내용담아
		
		if(cwriter =="" ) {	// 내용이 없으면 
			alert("내용을 입력해주세요");
			$("#cwriter").focus();		// 깜빡깜빡
			return;
		}	else if(ccontents=="") {		// 작성자가 없으면
			alert("작성자를 입력해주세요");			
			$("#ccontents").focus();		// 깜빡깜빡
			return;

			
		}
		
		 $.ajax({	
			type :"post",	
			url : "<%=request.getContextPath()%>/comment/commentWriteAction.aws",
	
			data : {"cwriter":cwriter,
				"ccontents":ccontents,
				"bidx":"<%=bv.getBidx()%>",
				"midx":"<%=midx%>"
				},
				dataType : "json",		
				
				success : function(result) {		
					//alert("댓글전송성공테스트");
					if(result.value ==1){		// 만약에 리턴값이 1 이면 ~ 실행됨 아니면  전송실패됨
						$("#ccontents").val("");
					}				
					$.boardCommentList();
				},
			error : function(){  
				//alert("전송실패 테스트");	
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
	<img src="<%=request.getContextPath() %>/board/displayFile.aws?fileName=<%=bv.getFilename()%>">		<!-- 컨트롤러에서 displayFile  설정해주기   -->
	<%} %>
 	<p>
 	
 	<a id = "dUrl" href = "" class = "fileDown">
 	첨부파일 다운로드	</a>
 	
 	
 	
	
	
	</p>	 

</article>



<div class="btnBox">
	<a class="btn aBtn" href="<%=request.getContextPath() %>/board/boardModify.aws?bidx=<%=bv.getBidx()%>">수정</a>
	<a class="btn aBtn" href="<%=request.getContextPath() %>/board/boardDelete.aws?bidx=<%=bv.getBidx()%>">삭제</a>
	<a class="btn aBtn" href="<%=request.getContextPath() %>/board/boardReply.aws?bidx=<%=bv.getBidx()%>">답변</a>
	<a class="btn aBtn" href="<%=request.getContextPath() %>/board/boardList.aws">목록</a>
</div>

<article class="commentContents">
	<form name="frm">
		<p class="commentWriter" style="width:100px;">
		<input type="text" id="cwriter" name="cwriter" value="<%=memberName%>" readonly="readonly" style="width:100px;border:0px;">
		</p>	
		<input type="text" id="ccontents"  name="ccontents">
		<button type="button" id="cmtBtn" class="replyBtn">댓글쓰기</button>
	</form>
	
	<div id="commentListView"></div>
	
</article>

</body>
</html>