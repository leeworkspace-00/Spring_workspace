package com.myaws.myapp.controller;

import java.util.ArrayList;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.myaws.myapp.domain.CommentVo;
import com.myaws.myapp.service.CommentService;

@RestController// > @ResponseBody대신해서 전체 다 객체로 가져올때 사용하기 
@RequestMapping(value="/comment")
public class CommentController {
	
	
	@Autowired
	CommentService commentService;
	
	
	@RequestMapping(value="/{bidx}/commentList.aws") // 댓글을 받으려면 객체로 받아와야함 >>@ResponseBody 어노테이션 달아주기 앞에 bidx 달고 넘어올것임 > @RestController컨트롤러 자체에 적어주기
	// 글번호 담을 때 {} 중괄호 안에 bidx 적어주기 
	public JSONObject commentList(@PathVariable("bidx") int bidx			
			) {
		
		JSONObject js = new JSONObject(); // json 방식으로 리턴해줘야함 리턴값 설정
		
		ArrayList<CommentVo> clist =  commentService.commentSelectAll(bidx);	// c리스트에 결과값 담아올거임 이제 json 객체 타입으로 담기 
		js.put("clist", clist);		// json 에 담아서 리턴하는 방법 .put("이름지정", 담을 것);
		
		
		
	
		
		return js;
	}
		
		
	
	
	

}
