package com.myaws.myapp.aop;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@Aspect
//@Slf4j : 롬복 라이브러리 추가할 때 사용
public class SampleAdvice {
	
	private static final Logger logger = LoggerFactory.getLogger(SampleAdvice.class);
	
	@Before("execution(* com.myaws.myapp.service.BoardService*.*(..))")//com.myaws.myapp.service.BoardService로 시작하는 모든 메서드들은 동작하기 전에 일단 찍어보겠다.
	public void startLog() {
		logger.info("----------------------");
		logger.info("AOP로그 테스트 중입니다");
		logger.info("----------------------");
		// 실행하면 로그 두번찍힘 > 리스트 들어갈때 서비스 메서드 2개 실행되서 그렇다
		
		//System.out.println("테스트"); 로그가 안나올 경우에 확인용으로 찍어본 코드 잘 나오면 주석처리해주기 
		
		
		
	}

}
