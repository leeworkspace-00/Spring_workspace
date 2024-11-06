package com.myaws.myapp.service;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myaws.myapp.domain.BoardVo;
import com.myaws.myapp.domain.searchCriteria;
import com.myaws.myapp.persistence.BoardMapper;

@Service // 컨트롤러와 같이 service 어노테이션 연결해주기
public class BoardServiceImpl implements BoardService { // BoardService에서 가져와서 사용

	private BoardMapper bm; 		// 매퍼클래스는 전역으로 사용 private 로 막고 꺼내쓰기

	
	  @Autowired // 객체생성 어노테이션 
	  public BoardServiceImpl(SqlSession sqlSession) {

	  this.bm = sqlSession.getMapper(BoardMapper.class); // 실행 파일이 있는지 확인하기 위해 class를 붙임 mybatis를 쓰기 위해 mapper와 연동 
	  }// mybatis 연동
	

	@Override
	public ArrayList<BoardVo> boardSelectAll(searchCriteria scri) {
		HashMap<String,Object> hm = new HashMap<String,Object>();
		
		hm.put("startPageNum", (scri.getPage()-1)*scri.getPerPageNum());	
		hm.put("searchType",scri.getSearchType());
		hm.put("perPageNum",scri.getPerPageNum());
		hm.put("keyword",scri.getKeyword());
		
		
		ArrayList<BoardVo> blist = bm.boardSelectAll(hm);
		return blist;
	}


	@Override
	public int boardTotalCount(searchCriteria scri) {		
		int cnt = bm.boardTotalCount(scri);	
		return cnt;
	}

}
