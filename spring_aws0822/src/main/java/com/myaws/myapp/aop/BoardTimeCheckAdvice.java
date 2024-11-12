package com.myaws.myapp.aop;

import java.util.Arrays;

import org.apache.jasper.tagplugins.jstl.core.Out;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;



@Component
@Aspect
public class BoardTimeCheckAdvice {
	
	private static final Logger logger = LoggerFactory.getLogger(BoardTimeCheckAdvice.class);
	
	@Around("execution(* com.myaws.myapp.service.BoardService*.*(..))")  //com.myaws.myapp.service.BoardService로 시작하는 모든 메서드들은 동작하기 전과 후에 일단 찍어보겠다.
	public Object timelog(ProceedingJoinPoint pjp) throws Throwable {		//ProceedingJoinPoint 진행상황에 대한 모든 정보를 수집하는 클래스
		Object result = null;
		logger.info("시작하는 AOP");
		logger.info("매개변수 : " +Arrays.toString(pjp.getArgs()));
		
		long startTime = System.currentTimeMillis();
		
		
		
		
		result = pjp.proceed();
		
		long endTime = System.currentTimeMillis(); 
		logger.info("끝나는 AOP");
		
		long durTime = endTime- startTime;
		logger.info(pjp.getSignature().getName()+"걸린시간"+durTime);
		
		
		return result;
		
	}
	

}
