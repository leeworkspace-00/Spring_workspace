package com.myaws.myapp.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class AuthInterceptor extends HandlerInterceptorAdapter {		// bean 등록
	
	// 인증 인터셉터 클래스 생성
	
	@Override  				// 가상경로에 해당하는 메서드 접근전에 가로채와서 확인할거임 >> 세션값이 있는지 없는지 확인 >> 세션값은 midx 회원번호
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		HttpSession session = request.getSession();
		
		if(session.getAttribute("midx") == null) {
			
			// 회원번호가 없으면 = 로그인 안되어 있는 상태면
			// 이동하려는 주소를 보관한 상태로  로그인 페이지로 보낸다
			
			saveUrl(request);// 이동하려고 했던 경로(request)를 저장한다 
			response.sendRedirect(request.getContextPath()+"/member/memberLogin.aws");
			
			 // 원래가려던페이지는 저장해둘게 
			
			return false;
			
		}else {
			
			// 회원번호가 있는 상태 = 로그인 되어 있는 상태
			
			return true;
		}
		
		
		

	}

	@Override
	public void postHandle(
			HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView)
			throws Exception {
	}
	
	
	// 로그인해서 이동하려고 하는 정보를 담는 메서드
	public void saveUrl(HttpServletRequest request) {
		// 매개변수 : HttpServletRequest request
		
		
		String uri = request.getRequestURI(); // >> 전체 주소를 뜻한다
		String param = request.getQueryString();	// >> 파라미터를 가져온다 
		
		// 만약에 파람값이 없거나 널이거나 널이라는 문자거나 빈값이면 
		if(param==null||param.equals("null")||param.equals("")) {
			param = "";
		}else {
			param = "?"+param;// param 값이 있으면 앞에 ? 붙여라
			
		}
		
		
		String locationUrl = uri+param;
		
		HttpSession session = request.getSession();
		if(request.getMethod().equals("GET"))  {		//"GET" 대문자여야함 , "GET"으로ㅓ 넘어왔을 때
			
			session.setAttribute("saveUrl", locationUrl);
		}
	}
	
	
	

}
