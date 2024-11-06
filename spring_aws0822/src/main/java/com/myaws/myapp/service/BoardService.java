package com.myaws.myapp.service;

import java.util.ArrayList;

import com.myaws.myapp.domain.BoardVo;
import com.myaws.myapp.domain.searchCriteria;

public interface BoardService {		// service > interface

	public ArrayList<BoardVo> boardSelectAll(searchCriteria scri);
	public int boardTotalCount(searchCriteria scri);

	
	

}
