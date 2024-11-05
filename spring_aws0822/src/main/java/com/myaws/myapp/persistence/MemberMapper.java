package com.myaws.myapp.persistence;

import java.util.*;

import com.myaws.myapp.domain.MemberVo;

public interface MemberMapper {
	
	// mybatis 에서 사용할 메서드를 정의해 놓는 곳
	// 주어진 규격
	
	// 구현문은 없고 선언만 해준다 
	// 쿼리는 > memberMapper 클래스에서..
	
	public int memberInsert(MemberVo mv);
	
	public int memberIdCheck(String memberId);		// 영속성 > 마이바티스 용 메서드 하나 더 생성

	public MemberVo memberLoginCheck(String memberId);
	
	public ArrayList <MemberVo> memberSelectAll();

	
	
}
