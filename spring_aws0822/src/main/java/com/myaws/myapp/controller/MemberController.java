package com.myaws.myapp.controller;

import java.util.ArrayList;

import javax.servlet.http.HttpSession;

import org.apache.ibatis.session.SqlSession;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.myaws.myapp.domain.MemberVo;
import com.myaws.myapp.service.MemberService;

@Controller
@RequestMapping(value = "/member/")
public class MemberController { // 컨트롤러 용도의 객체를 생성해달라고 스프링에 요청하기 > @Controller > import

	private static final Logger logger = LoggerFactory.getLogger(MemberController.class); // 디버깅 코드 처럼 찍어보는 코드

	@Autowired
	private MemberService memberService;

	@Autowired(required = false) // 주입해주기
	private BCryptPasswordEncoder bCryptPasswordEncoder; // 암호화

	@Autowired
	SqlSession sqlSession;

	@RequestMapping(value = "memberJoin.aws", method = RequestMethod.GET) // 매핑값 지정 @RequestMapping(value = "원하는 가상경로
																			// 값", method = RequestMethod.GET또는 post)
	public String memberJoin() {
		logger.info("memberjoin 들어옴");
		return "WEB-INF/member/memberJoin";

		// logger.info("memberjoin 들어옴"); // println 보다 logger를 더 권장
		// logger.info("sqlSession"+sqlSession);
		// logger.info("memberService"+memberService);
	}

	@RequestMapping(value = "memberLogin.aws", method = RequestMethod.GET)
	public String memberLogin() {
		logger.info("memberLogin 들어옴");
		return "WEB-INF/member/memberLogin";// 뒤에 .aws는 자동으로 붙음
	}

	// 회원가입 동작 매핑하기
	@RequestMapping(value = "memberJoinAction.aws", method = RequestMethod.POST)
	public String memberJoinAction(MemberVo mv) {
		logger.info("memberJoinAction 들어옴");
		// pom.xml에 라이브러리 3개 추가 하고 컨트롤러로 돌아와서 암호화 모듈 불러오기
		String memberpwd_enc = bCryptPasswordEncoder.encode(mv.getMemberpwd()); // 이게 비밀번호 암호화 시키는 코드
		mv.setMemberpwd(memberpwd_enc);
		int value = memberService.memberInsert(mv);
		String path = "";
		if (value == 1) {
			path = "redirect:/";
		} else if (value == 0) {
			path = "redirect:/member/memberJoin.aws";
		}
		return path;
		// logger.info("value : " + value);
		// logger.info("memberjoinAction 들어옴"); // println 보다 logger를 더 권장
		// logger.info("value 값 확인" + value);
	}

	@ResponseBody // 결과값은 객체로 보낸다는 의미의 어노테이션
	@RequestMapping(value = "memberIdCheck.aws", method = RequestMethod.POST) // 아이디 중복 체크 동작 메서드 구현
	public JSONObject JSONObject(@RequestParam("memberId") String memberId) { // String memberId =
																				// request.getParameter("memberid"); >>
																				// @RequestParam("memberId") String
																				// memberId
		// MemberDao mv = new MemberDao(); // 스프링에서는 객체를 직접 할 필요가 없다 > 직접한다면 ? pojo 방식임
		int cnt = memberService.memberIdCheck(memberId);
//그전 dao에 메서드 만들었던것 처럼 여기서는 service로 가서 메서드 선언 인터페이스 생성 구현 > 그다음 마이바티스 메서드 가져오고 매퍼로 가서  쿼리 작성해준다

		JSONObject obj = new JSONObject();
		// json방식으로 간단히 출력하는 라이브러리 추가하기
		// pom.xml에 json-simple 라이브러리 추가하기
		obj.put("cnt", cnt); // 객체명.put("담을 값이름",담을 값);

		return obj;
	}

	@RequestMapping(value = "memberLoginAction.aws", method = RequestMethod.POST)
	public String memberLoginAction(@RequestParam("memberid") String memberid,
			@RequestParam("memberpwd") String memberpwd, RedirectAttributes rttr, // 한번만 담아서 사용하는 클래스 생성 > 클래스명 지정
			// 모델의 기능도 가지고 있다
			HttpSession session

	) {

		MemberVo mv = memberService.memberLoginCheck(memberid);
		// 저장된 비밀번호 가져오기
		String path = ""; // path 초기화
		if (mv != null) { // 객체값이 널이 아니면 => mv 에 뭐라도 담았으면

			String reservedPwd = mv.getMemberpwd(); // reservedPwd 변수선언해주고 memberpwd 가져와서 담기

			if (bCryptPasswordEncoder.matches(memberpwd, reservedPwd)) { // 암호화 해줬던 암호랑 가져온 비밀번호랑 같은지 매칭시켜보고 맞으면

				rttr.addAttribute("midx", mv.getMidx());
				rttr.addAttribute("memberId", mv.getMemberid());
				rttr.addAttribute("memberName", mv.getMembername());

				// ========= 이전 url 기억하기 ============
				if (session.getAttribute("saveUrl") != null) {
					path = "redirect:" + session.getAttribute("saveUrl").toString();
					// logger.info("saveUrl 값 확인" + path); >> saveUrl 값
					// 확인redirect:/member/memberList.aws

				} else {
					path = "redirect:/";

				}

				// 주소를 메인페이지로 이동
				// 넘어온 값을 매치스로 비교해서 같은 값이면 로그인 성공
			} else { // null이면 ==> 잘못된 결과면 다시 로그인 페이지로 이동한다

				
				  
				 
				rttr.addFlashAttribute("msg", "아이디/비밀번호를 확인해주세요"); // 한번 사용하고 없어질 세션. 값을 사용한 후에 지워버림
				path = "redirect:/member/memberLogin.aws";

			}

		} else {
			
			  
			 
			rttr.addFlashAttribute("msg", "해당하는 아이디가 없습니다");
			path = "redirect:/member/memberLogin.aws";
		}
		return path; // path 값 리턴
	}

	@RequestMapping(value = "memberList.aws", method = RequestMethod.GET)
	public String memberList(Model model) {
		// logger.info("memberList 들어옴");

		ArrayList<MemberVo> alist = memberService.memberSelectAll();

		model.addAttribute("alist", alist);

		return "WEB-INF/member/memberList";// 뒤에 .aws는 자동으로 붙음
	}

	@RequestMapping(value = "memberLogout.aws", method = RequestMethod.GET) // 로그아웃 동작 HttpSession session 매개변수
	public String memberLogout(HttpSession session) {
		// logger.info("memberLogout 들어옴");

		session.removeAttribute("midx");
		session.removeAttribute("memberId");
		session.removeAttribute("memberName");
		session.invalidate(); // 세션값 완전 초기화 = 초기화 되면 세션값이 없으므로 로그아웃되는것

		return "redirect:/";
	}

}
