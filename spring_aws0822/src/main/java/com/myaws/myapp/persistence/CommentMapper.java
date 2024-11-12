package com.myaws.myapp.persistence;

import java.util.ArrayList;
import java.util.HashMap;

import com.myaws.myapp.domain.BoardVo;
import com.myaws.myapp.domain.CommentVo;
import com.myaws.myapp.domain.searchCriteria;
// mybaits 용 메서드  
public interface CommentMapper {
	// 글목록
	public ArrayList<CommentVo> commentSelectAll(int bidx);
	
	
}
