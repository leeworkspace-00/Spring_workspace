package com.myaws.myapp.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.myaws.myapp.service.Test;

@Controller
@RequestMapping(value = "/member/")
public class MemberController {		// 컨트롤러 용도의 객체를 생성해달라고 스프링에 요청하기  > @Controller > import
	
	private static final Logger logger = LoggerFactory.getLogger(MemberController.class); // 디버깅 코드 처럼 찍어보는 코드
	
	
	
	@Autowired // ? 객체 생성해달라고 요청 어노테이션
	private Test tt;	// tt안에 주소가 나오게 됨
	
	@RequestMapping(value = "memberJoin.aws", method = RequestMethod.GET)// 매핑값 지정 @RequestMapping(value = "원하는 가상경로 값", method = RequestMethod.GET또는 post)
	public String memberJoin() {
		logger.info("memberjoin 들어옴");  // print 보다 logger를 더 권장
		logger.info("tt 값은 ? " + tt.test());
		
		
		return "WEB-INF/member/memberJoin";// 뒤에 .aws는 자동으로 붙음
	}
	
	@RequestMapping(value = "memberLogin.aws", method = RequestMethod.GET)
	public String memberLogin() {
		
		return "WEB-INF/member/memberLogin";// 뒤에 .aws는 자동으로 붙음
	}
	
	
	
	
	
	
	
}
