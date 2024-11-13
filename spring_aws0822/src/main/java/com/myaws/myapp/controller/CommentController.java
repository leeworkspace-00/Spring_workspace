package com.myaws.myapp.controller;

import java.net.InetAddress;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.myaws.myapp.domain.CommentVo;
import com.myaws.myapp.service.CommentService;
import com.myaws.myapp.util.UserIp;



@RestController// > @ResponseBody대신해서 전체 다 객체로 가져올때 사용하기 
@RequestMapping(value="/comment/")
public class CommentController {
	@Autowired(required=false)		// 널값도 허용하겠다는 의미
	private UserIp userIp;
	
	
	@Autowired
	CommentService commentService;
	
	
	@RequestMapping(value="{bidx}/{block}/commentList.aws") // 댓글을 받으려면 객체로 받아와야함 >>@ResponseBody 어노테이션 달아주기 앞에 bidx 달고 넘어올것임 > @RestController컨트롤러 자체에 적어주기
	// 글번호 담을 때 {} 중괄호 안에 bidx 적어주기 
	public JSONObject commentList(
			@PathVariable("bidx") int bidx,
			@PathVariable("block") int block
			) {
		
		String moreView = "";
		int nextBlock = 0;		// 현재 블럭기준값 선언
		
		
		int cnt = commentService.commentTotalCnt(bidx);
		
		if(cnt > block*15) {		// cnt 값이 15개 보다 크면 
			moreView = "Y";			// 더보기 yes로  
			nextBlock = block+1;
		}else {
			moreView = "N";			// 더보기 yes로  
			
			nextBlock = block;
			
		}
		
		
		
		ArrayList<CommentVo> clist =  commentService.commentSelectAll(bidx,block);	// c리스트에 결과값 담아올거임 이제 json 객체 타입으로 담기 
		JSONObject js = new JSONObject(); // json 방식으로 리턴해줘야함 리턴값 설정
		js.put("clist", clist);		// json 에 담아서 리턴하는 방법 .put("이름지정", 담을 것);
		js.put("moreView", moreView);	// 더보기 객체에 담기
		js.put("nextBlock", nextBlock);
		return js;
	}
	
	
	
	// 댓글 쓰기 동작 컨트롤러
	@RequestMapping(value="commentWriteAction.aws") 
	public JSONObject commentWriteAction(
			CommentVo cv,		// 작성자 내용 날짜 등등 cv에 담아오기 
			HttpServletRequest request
			) throws Exception {
		// 작성자  내용 회원번호 게시ㅣ글번호 

		String cip = userIp.getUserIp(request);		
		cv.setCip(cip);
		int value = commentService.commentInsert(cv);
		
		JSONObject js = new JSONObject(); // ajax 사용하므로 json 방식으로 리턴해줘야함 리턴값 설정
		js.put("value", value);
		

		return js;
	}
	
	
	
	
	
	
	
	@RequestMapping(value="{cidx}/commentDeleteAction.aws", method = RequestMethod.GET) 
	// 글번호 담을 때 {} 중괄호 안에 bidx 적어주기 
	public JSONObject commentDeleteAction(
			CommentVo cv,
			@PathVariable("cidx") int cidx,
			HttpServletRequest request
			) throws Exception  {
		// 작성자  내용 회원번호 게시ㅣ글번호 
		
		int midx = Integer.parseInt(request.getSession().getAttribute("midx").toString());
		String cip = userIp.getUserIp(request);	
		cv.setMidx(midx);
		cv.setCidx(cidx);
		cv.setCip(cip);
			

		
		
		int value = commentService.commentDelete(cv);
		
		JSONObject js = new JSONObject(); // ajax 사용하므로 json 방식으로 리턴해줘야함 리턴값 설정
		js.put("value", value);
		//System.out.println("value  =========> " + value);

		return js;
	}

}
