package com.myaws.myapp.service;

import java.util.ArrayList;

import org.json.simple.JSONObject;

import com.myaws.myapp.domain.BoardVo;
import com.myaws.myapp.domain.CommentVo;
import com.myaws.myapp.domain.searchCriteria;



public interface CommentService {		// service > interface

	// 해당하는 글번호에 달린 댓글전체  보여주는 메서드 선언
	public ArrayList<CommentVo> commentSelectAll(int bidx, int block); // 글번호, 댓글 가져올 개수 = block
	// 댓글 쓰기 동작
	public int commentInsert (CommentVo cv);
	
	
	// 댓글 삭제 동작
	public int commentDelete(CommentVo cv);
	
	public int commentTotalCnt(int bidx);
		
	
	
	

}
