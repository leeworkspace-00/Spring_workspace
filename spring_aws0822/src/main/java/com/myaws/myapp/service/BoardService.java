package com.myaws.myapp.service;

import java.util.ArrayList;

import com.myaws.myapp.domain.BoardVo;
import com.myaws.myapp.domain.searchCriteria;

public interface BoardService {		// service > interface

	// 글목록 보여주는 메서드 선언
	public ArrayList<BoardVo> boardSelectAll(searchCriteria scri);
	public int boardTotalCount(searchCriteria scri);
	
	// 글쓰기 메서드 선언
	public int boardInsert(BoardVo bv);
	
	public BoardVo boardSelectOne(int bidx);
	
	// 조회수 메서드
	public int boardViewCntUpdate(int bidx);
	//추천하기 기능 메서드
	public int boardRecomUpdate(int bidx);
	
	//public int boardDelete(int bidx, int midx, String password);
	
	
	public int boardDeleteAction(int bidx, int midx, String password);
	

	public int boardUpdate(BoardVo bv);
	public int boardReply(BoardVo bv);
	
	
	

}
