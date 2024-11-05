package com.myaws.myapp.interceptor; // 세션에 회원정보 담는 역할을 하는 클래스  

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class LoginInterceptor extends HandlerInterceptorAdapter {		// 로그인 확인하는 클래스 
	
	// 로그인 후에 회원정보를 세션에 담는다.
	// 추상클래스에서 핸들 3 개 가져옴 preHandle,  postHandle,  afterConcurrentHandlingStarted
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
			// preHandle = 접속하기 전에 가로채기 > 정보  초기화 
		
		HttpSession session = request.getSession();
		if(session.getAttribute("midx") != null)  {
			session.removeAttribute("midx");
			session.removeAttribute("memberId");
			session.removeAttribute("memberName");
			session.invalidate();		// 완전 초기화 시킨다	
		}
		
		
		return true;
	}

	
	@Override
	public void postHandle(
			HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView)
			throws Exception {
		// 접속한 후에 가로채기  > 정보 담기 
		
		// RedirectAttribute 나 Model 객체에 담은 값을 꺼낸다
		String midx = modelAndView.getModel().get("midx").toString();
		String memberId =  modelAndView.getModel().get("memberId").toString();
		String memberName = modelAndView.getModel().get("memberName").toString();
		//controller에 있는 rttr에 담아둔 값 가져오겠다는 뜻
		
		modelAndView.getModel().clear();		// 파라미터 모델 값을 지운다 
		HttpSession session = request.getSession();
		// 세션을 담는다
		if(midx != null) {
			session.setAttribute("midx", midx);
			session.setAttribute("memberId", memberId);
			session.setAttribute("memberName", memberName);
			
		}
		
	}
	/*
	 * @Override public void afterConcurrentHandlingStarted( HttpServletRequest
	 * request, HttpServletResponse response, Object handler) throws Exception { }
	 */
	
	
	//
	
	

}
