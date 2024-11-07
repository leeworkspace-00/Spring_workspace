package com.myaws.myapp.controller;

import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.catalina.connector.Request;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.myaws.myapp.domain.BoardVo;
import com.myaws.myapp.domain.PageMaker;
import com.myaws.myapp.domain.searchCriteria;
import com.myaws.myapp.service.BoardService;
import com.myaws.myapp.service.BoardServiceImpl;
import com.myaws.myapp.util.UploadFileUtiles;


@Controller
@RequestMapping(value="/board/")
public class BoardController {
	
	@Autowired(required=false)
	private BoardService boardService;
	// 객체자동 생성 
	// BoardService boardService 생성

	@Autowired(required=false)
	private PageMaker pm;
	// 객체자동 생성 
	// PageMaker pm 생성
	// 전역으로 사용
	
	@Resource(name = "uploadPath")		//@Autowired와  @Resource의 차이 : 객체의 참조변수의 이름을 적어줘야함 이름은 sevlet-context에 있음
	private String uploadPath;
	
	
	@RequestMapping(value="boardList.aws")		// 목록에서 처리해야할 부분 1. 글목록 2. 페이지 3. 검색 4. 
	//boardList.aws 이 값이 넘어오면 연결해줄 메서드 매핑해주기
	public String boardList(searchCriteria scri, Model model) {
		// 파라미터값은 scri 랑 model을 받는다
		
		
		int cnt = boardService.boardTotalCount(scri);
		// boradService 안에 있는 boardTotalCount 메서드 실행하고 결과값을 cnt 에 담는다
		
		pm.setScri(scri);
		pm.setTotalCount(cnt);
		//pm 안에있는 scri 랑 TotalCount를 새로 담는다
		
		
		ArrayList<BoardVo> blist =  boardService.boardSelectAll(scri);
		// blist 에 board
		
		model.addAttribute("blist", blist);		
		model.addAttribute("pm", pm);	
		
		String path="WEB-INF/board/boardList";		
		return path;
	}
	
	@RequestMapping(value="boardWrite.aws")	
	public String boardWrite() {	
		String path = "WEB-INF/board/boardWrite"; 
		return path;
	}		// 글쓰기 페이지 보여주는 컨트롤러 
	
	@RequestMapping(value="boardWriteAction.aws")	// 글쓰기 동작 메서드
	public String boardWriteAction(
			BoardVo bv,// 넘겨야할 데이터 제목, 내용, 작성자, 비밀번호(bv로 받는다)
			@RequestParam("filename")MultipartFile filename, // 파일은 @RequestParam("filename")MultipartFile filename)
			HttpSession session, // 회원번호 꺼내고 아이디 꺼내려고 세션값 가져옴
			HttpServletRequest request,		// 요청값 > 회원번호 가져오기 위해서 세션 생성
			RedirectAttributes rttr		// sendredirect 로 화면 보여주기 위해서  생성
			) throws IOException, Exception{	
				MultipartFile file = filename;// 새로운 변수에 담아서 이름을 변경해줌
				String uploadedFileName = "";
				if(! file.getOriginalFilename().equals("")) {		// ! >> 존재한다면(.equals("")없는게 아니라면)
					
					uploadedFileName = UploadFileUtiles.uploadFile(uploadPath, file.getOriginalFilename(), file.getBytes());// 업로드 위치 파일 이름 
					// 빨간줄 뜨면 throws 로 해결 위에 자동 생성 
					// BoardVo 에 가서 	private String uploadedFilename;추가하고 getter,setter 생성해주기
					
				}
				
				String midx = request.getSession().getAttribute("midx").toString();
				int midx_int = Integer.parseInt(midx);
				
				
				String ip = getUserIp(request);
				
				bv.setUploadedFilename(uploadedFileName);
				bv.setMidx(midx_int);
				bv.setIp(ip);
				
				
				String path = "";
				int value = boardService.boardInsert(bv);
				
				
				if(value == 2) { // 성공하면 목록 보여주고
					path ="redirect:/board/boardList.aws";
				}else {			// 실패하면 다시 쓰기 페이지 보여주기 
					
					rttr.addFlashAttribute("msg","입력이 잘못되었습니다");
					path = "redirect:/board/boardWrite.aws";
				}
				
		return path;	// 메서드 모두 실행하고 경로로 가기
		
		
	}@RequestMapping(value="boardContents.aws")	
	public String boardContents(@RequestParam("bidx") int bidx, Model model) {	
		
		BoardVo bv = boardService.boardSelectOne(bidx);
		
		
		model.addAttribute("bv", bv);
				
		
		
		
		String path = "WEB-INF/board/boardContents"; 
		return path;
	}
	
	
	
	
	
	
	
	
	public String getUserIp(HttpServletRequest request) throws Exception { // 클라이언트 ip 주소 뽑는 메서드 이클립스에서 가져오기 

		String ip = null;

		ip = request.getHeader("X-Forwarded-For");

		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_CLIENT_IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_X_FORWARDED_FOR");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("X-Real-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("X-RealIP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("REMOTE_ADDR");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}

		if (ip.equals("0:0:0:0:0:0:0:1") || ip.equals("127.0.0.1")) {
			InetAddress address = InetAddress.getLocalHost();
			ip = address.getHostAddress();

		}

		return ip;
	}
	
	
	
	
	
	
	
	
}

//메서드 구현 순서(boardList)

	/*
	 * 1. controller 
	 * 1-1. @Autowired 걸고 객체 생성하기 
	 * @Autowired(required=false)
		private BoardService boardService;
		@Autowired(required=false)
		private PageMaker pm;
	  
	 * 1-2. 메서드와 연결하기 위해 @RequestMapping 어노테이션 사용
	 * 	@RequestMapping(value="boardList.aws")
	public String boardList() {
		
		return null;
	} // 초기에는 이렇게 자동 생성됨
	 * 
	 * 2. service 단으로 간다
	 * 2.1 인터페이스 생성 new > interface 생성하고 > BoardService 만들어주기
	 * 그 안에 메서드 작성하기 
	 * public interface BoardService {

	public ArrayList<BoardVo> boardSelectAll(searchCriteria scri);
	public int boardTotalCount(searchCriteria scri);
}
	 * 
	 * 
	 * 3. mapper.xml 에서 사용할 쿼리문 작성하기
	 * 3-1. select 문 작성
	 * 
	 * <select id = "boardSelectAll" parameterType="HashMap" resultType="bv">		<!-- resultType="mv" >> 여러개 담기  하나를 담아도 mv로 담아오자 -->   
		SELECT * FROM board where delyn='N' 

			<include refid = "search"/>
		
			ORDER BY originbidx desc, depth asc limit #{startPageNum}, #{perPageNum} 		
		</select>
	  	
	<select id ="boardTotalCount" parameterType="scri" resultType="int" >
		SELECT count(*) as cnt FROM board where delyn='N'	
		<include refid = "search"/>
	</select>
	
	 * 
	 * 3-2. 검색기능에 필요한 if 문 작성
	 * <sql id = "search">
  
  	<if test="searchType != null and searchType.equals('writer')">
  		and writer like concat('%',#{keyword},'%')
  	</if>  
  	
  	
  	<if test="searchType != null and searchType.equals('subject')">
  		and subject like concat('%',#{keyword},'%')
  	</if>       
  ===================== mapper.xml 에 쿼리 추가한 부분============================
  </sql>
	 * 
	 * 
	 * 4. serviceImpl 클래스 생성
	 * 4-1. mybatis 와 연동하기 
	 * public class BoardServiceImpl implements BoardService {

	private BoardMapper bm; 		// 매퍼클래스

	
	  @Autowired // 객체생성 어노테이션 
	  public BoardServiceImpl(SqlSession sqlSession) {
	  this.bm = sqlSession.getMapper(BoardMapper.class); 
	  }
	  
	  4-2. 연동시키고 나면 클래스이름(BoardServiceImpl) 에 레드라인 생기고 add 해주기 
	  
	 * @Override
		public ArrayList<BoardVo> boardSelectAll(searchCriteria scri) {
			// TODO Auto-generated method stub
			return null;
		}
		//초기에 생성되는 override
	 * 4-3. 작성해둔 메서드 모두 추가해주고 고칠부분 정리해주기 
	 
	 * 
	 * 5. 마지막으로 controller 에서 @RequestMapping 아래에 완성된 메서드 호출해서 사용하기 
	 * @RequestMapping(value="boardList.aws")		
			public String boardList(searchCriteria scri, Model model) {
				// 파라미터값은 scri 랑 model을 받는다
		
		
			int cnt = boardService.boardTotalCount(scri);
			// boradService 안에 있는 boardTotalCount 메서드 실행하고 결과값을 cnt 에 담는다
			
			pm.setScri(scri);
			pm.setTotalCount(cnt);
			//pm 안에있는 scri 랑 TotalCount를 새로 담는다
			
			
			ArrayList<BoardVo> blist =  boardService.boardSelectAll(scri);
			// blist 에 boardSelectAll 메서드 실행하고 난 결과값 담아서 
			
			model.addAttribute("blist", blist);		
			model.addAttribute("pm", pm);	
			// model 에 pm, blist 라는 이름으로 담아서 갈거임 
			 * controller 에서 파라미터 값으로 scri, model 을 받아야한다
			 * 
			
			
			String path="WEB-INF/board/boardList";		
			return path;		// 리턴값은 paath 에 담아서 가상주소로 넘겨준다
	}
	 * 
	
	 */



