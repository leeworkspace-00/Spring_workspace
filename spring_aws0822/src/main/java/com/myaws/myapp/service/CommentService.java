package com.myaws.myapp.service;

import java.util.ArrayList;

import com.myaws.myapp.domain.BoardVo;
import com.myaws.myapp.domain.CommentVo;
import com.myaws.myapp.domain.searchCriteria;



public interface CommentService {		// service > interface

	// 해당하는 글번호에 달린 댓글전체  보여주는 메서드 선언
	public ArrayList<CommentVo> commentSelectAll(int bidx);
	
	
		
	
	
	

}
