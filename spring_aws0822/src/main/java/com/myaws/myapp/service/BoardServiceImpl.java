package com.myaws.myapp.service;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
	
	  // 글 목록에 필요한 메서드 호출
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

	@Override
	@Transactional
	public int boardInsert(BoardVo bv) {		// 터진 강낭콩 같은 기호 : 트렌젝션이 걸렸다는 의미
		
		int value = bm.boardInsert(bv);			// 결과값은 1또는 0
		int maxBidx = bv.getBidx();
		int value2 = bm.boardOriginbidxUpdate(maxBidx);// 업데이트 구문 실행		// 결과값 1또는 0
		// 메서드 두개 실행중에 정전이 나서 오류가 발생하면 안되니 
		// 트렌젝션으로 묶어준다 @Transactional
		
		
		
		return value+value2;		// 성공하면 2 아니면 0
	}

	@Override
	public BoardVo boardSelectOne(int bidx) {		// 글 내용 1 개 가져와서 보여주는 메서드 
		BoardVo bv =  bm.boardSelectOne(bidx); 
		return bv;
	}

	@Override
	public int boardViewCntUpdate(int bidx) {		// 조회수 증가 메서드 
		 int cnt= bm.boardViewCntUpdate(bidx);
		return cnt;
	}

	@Override
	public int boardRecomUpdate(int bidx) {		// 추천 클릭시 추천수만 증가하는 메서드  
		BoardVo bv = new BoardVo();
		bv.setBidx(bidx);
		
		int cnt = bm.boardRecomUpdate(bv);
		int recom = bv.getRecom();
		return recom;
		
		
	}
	// 글삭제 동작 메서드 구현
	@Override
	public int boardDeleteAction(int bidx, int midx, String password) {
		HashMap<String, Object> hm = new HashMap<String, Object>();		// 해쉬맵으로 받아오자
		hm.put("bidx", bidx);
		hm.put("midx", midx);
		hm.put("password", password);
		
		int cnt = bm.boardDeleteAction(hm);
		
		
		
		return cnt;
		
	
	}
	@Override
	public int boardUpdate(BoardVo bv) {
		
		int value = bm.boardUpdate(bv);		
		return value;
	}

	@Transactional		// 두가지 이상 쿼리문 작동할 때 트랜잭션 걸기 
	@Override
	public int boardReply(BoardVo bv) {
	
		int value = bm.boardReplyUpdate(bv);
		//System.out.println("value"+value);
		
		int value2 = bm.boardReplyInsert(bv);
		int maxBidx = bv.getBidx();
		return maxBidx;
	}
	
}	
	
	
	
	

// 글삭제 페이지 보여주기 
	/*
	 * @Override public int boardDelete(int bidx, int midx, String password) {
	 * 
	 * HashMap<String, Object> hm = new HashMap<String, Object>(); // 해쉬맵으로 받아오자
	 * hm.put("bidx", bidx); hm.put("midx", midx); hm.put("password", password);
	 * 
	 * int cnt = bm.boardDelete(hm);
	 * 
	 * return cnt; }
	 */
	

	
	
	
	
	// 글쓰기 메서드 호출


