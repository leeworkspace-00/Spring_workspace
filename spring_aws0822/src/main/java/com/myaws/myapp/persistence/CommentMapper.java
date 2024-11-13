package com.myaws.myapp.persistence;

import java.util.ArrayList;
import java.util.HashMap;

import com.myaws.myapp.domain.BoardVo;
import com.myaws.myapp.domain.CommentVo;
import com.myaws.myapp.domain.searchCriteria;
// mybaits 용 메서드  
public interface CommentMapper {
	// 글목록
	public ArrayList<CommentVo> commentSelectAll(int bidx, int block);
	
	// 댓글 달기 
	public int commentInsert(CommentVo cv);
	
	// 댓글 삭제 
	public int commentDelete(CommentVo cv);
	
	public int commentTotalCnt(int bidx);
}
