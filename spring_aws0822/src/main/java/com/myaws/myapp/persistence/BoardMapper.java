package com.myaws.myapp.persistence;

import java.util.ArrayList;
import java.util.HashMap;

import com.myaws.myapp.domain.BoardVo;
import com.myaws.myapp.domain.CommentVo;
import com.myaws.myapp.domain.searchCriteria;
// mybaits 용 메서드  
public interface BoardMapper {
	// 글목록
	public ArrayList<BoardVo> boardSelectAll(HashMap<String,Object> hm);
	public int boardTotalCount(searchCriteria scri);
	
	// 글내용
	public int boardInsert(BoardVo bv);
	public int boardOriginbidxUpdate(int bidx);
	public BoardVo boardSelectOne(int bidx);
	
	// 조회수 
	public int boardViewCntUpdate(int bidx);
	
	public int boardRecomUpdate(BoardVo bv);
	// 글 삭제 기능
	//public int boardDelete(HashMap hm);		// 마이바티스메서드는 해시맵으로 
	//1108
	public int boardDeleteAction(HashMap hm);
	
	
	//241111
	public int boardUpdate(BoardVo bv);
	public int boardReplyUpdate(BoardVo bv);
	public int boardReplyInsert(BoardVo bv);
	
	
}
