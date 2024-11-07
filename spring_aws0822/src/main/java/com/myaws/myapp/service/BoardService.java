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

	
	

}
