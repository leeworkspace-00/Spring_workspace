package com.myaws.myapp.service;


import java.util.ArrayList;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.myaws.myapp.domain.MemberVo;
import com.myaws.myapp.persistence.MemberMapper;

@Service
public class MemberServiceImpl implements MemberService  {

	private MemberMapper mm; // 매퍼 클래스 자체를 미리 생성 >> private 으로 막아두고 꺼내쓴다
	
	
	 @Autowired(required=false)
	   public MemberServiceImpl(SqlSession sqlSession) {
		 //System.out.println("sqlSession:"+sqlSession);
	     this.mm = sqlSession.getMapper(MemberMapper.class); // 실행 파일이 있는지 확인하기 위해 class를 붙임
	                                                 // mybatis를 쓰기 위해 mapper와 연동
	   }
		
	@Override
	public int memberInsert(MemberVo mv) {		
		int value = mm.memberInsert(mv);
		System.out.println("value"+value);
		return value;  // 서비스에서 선언하면 impl에 자동으로 빨간줄 > 메서드 생성 해줌	
	}

	public int memberIdCheck(String memberId) {
		int value = mm.memberIdCheck(memberId);
		
		return value;  // 서비스에서 선언하면 impl에 자동으로 빨간줄 > 메서드 생성 해줌
	}
	//@Override
	public MemberVo memberLoginCheck(String memberId) {
		MemberVo mv = mm.memberLoginCheck(memberId);		
		return mv;	
	}

	@Override
	public ArrayList<MemberVo> memberSelectAll() {
	
		ArrayList<MemberVo> alist = mm.memberSelectAll();	// 매퍼 클래스 불러서 안에 있는 쿼리문 실행시키기		
		return alist;
	}
}
