package com.myaws.myapp.service;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import com.myaws.myapp.domain.CommentVo;


import com.myaws.myapp.persistence.CommentMapper;

@Service // 컨트롤러와 같이 service 어노테이션 연결해주기
public class CommentServiceImpl implements CommentService { // BoardService에서 가져와서 사용

	private CommentMapper cm; 		// 매퍼클래스는 전역으로 사용 private 로 막고 꺼내쓰기

	
	  @Autowired // 객체생성 어노테이션 
	  public CommentServiceImpl(SqlSession sqlSession) {
	  this.cm = sqlSession.getMapper(CommentMapper.class); // 실행 파일이 있는지 확인하기 위해 class를 붙임 mybatis를 쓰기 위해 mapper와 연동 
	 // System.out.println("cm 값확인"+cm);
	  }// mybatis 연동
	
	

	@Override
	public ArrayList<CommentVo> commentSelectAll(int bidx) {

		ArrayList<CommentVo> clist = cm.commentSelectAll(bidx);
		return clist;
		
	}
	
}	
	


