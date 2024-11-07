package com.myaws.myapp.persistence;

import java.util.ArrayList;
import java.util.HashMap;

import com.myaws.myapp.domain.BoardVo;
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

}
