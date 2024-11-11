package com.myaws.myapp.controller;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.util.ArrayList;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.catalina.connector.Request;
import org.apache.commons.io.IOUtils;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.myaws.myapp.domain.BoardVo;
import com.myaws.myapp.domain.PageMaker;
import com.myaws.myapp.domain.searchCriteria;
import com.myaws.myapp.service.BoardService;
import com.myaws.myapp.service.BoardServiceImpl;
import com.myaws.myapp.util.MediaUtils;
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
			@RequestParam("attachfile")MultipartFile attachfile, // 파일은 @RequestParam("filename")MultipartFile filename)
			HttpSession session, // 회원번호 꺼내고 아이디 꺼내려고 세션값 가져옴
			HttpServletRequest request,		// 요청값 > 회원번호 가져오기 위해서 세션 생성
			RedirectAttributes rttr		// sendredirect 로 화면 보여주기 위해서  생성
			) throws IOException, Exception{	
				MultipartFile file = attachfile;// 새로운 변수에 담아서 이름을 변경해줌 ++ attachfile로 변경해서 vo에 있는 파일네임과 충돌방지 
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
		
		
	}
	@RequestMapping(value ="displayFile.aws", method = RequestMethod.GET)		// get 방식으로 넘기는 것 > 파일 이름을 넘길것
	public ResponseEntity<byte[]> displayFile(
			@RequestParam("fileName") String fileName,	// 파일이름 넘기는 파라미터
			@RequestParam(value ="down", defaultValue = "0") int down		// 다운로드를 받을것인지 화면에 보여줄건지 정하는 파라미터
			) {
		
		
		ResponseEntity<byte[]> entity = null;			//entity : 실제 
		
		InputStream in = null; 	// 데이터 흐름의 시작 
		
		try{
			String formatName = fileName.substring(fileName.lastIndexOf(".")+1);// 자리수 계산한다음에 +1해주고  확장자 확인하는 ..substring(마지막. 뒤에있는 문자를 자른다 > 확장자 확인)
			MediaType mType = MediaUtils.getMediaType(formatName);
			
			HttpHeaders headers = new HttpHeaders();			// 헤더정보 가져오기 위해 생성
			 
			in = new FileInputStream(uploadPath+fileName);		// 
			
			
			if(mType != null){		// 이미지 파일등에 해당되면
				
				if (down==1) {		// 넘어온 다운의 값이 1이면 다운로드 실행 가능하게 ~~
					fileName = fileName.substring(fileName.indexOf("_")+1);
					headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
					headers.add("Content-Disposition", "attachment; filename=\""+
							new String(fileName.getBytes("UTF-8"),"ISO-8859-1")+"\"");	
					
				}else {			// 1이 아니면 다운로드 말고 화면에 보여줄거임
					headers.setContentType(mType);	
				}
				
			}else{		// 그게 아니면 다운로드 받는 방식으로  처리
				
				fileName = fileName.substring(fileName.indexOf("_")+1);
				headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
				headers.add("Content-Disposition", "attachment; filename=\""+
						new String(fileName.getBytes("UTF-8"),"ISO-8859-1")+"\"");				
			}
			entity = new ResponseEntity<byte[]>(IOUtils.toByteArray(in), headers, HttpStatus.CREATED);	// entity  에 담겠다
			
		}catch(Exception e){
			e.printStackTrace();
			entity = new ResponseEntity<byte[]>(HttpStatus.BAD_REQUEST);		// entity에 담겠다 
		}finally{
			try {
				in.close();
			} catch (IOException e) {
				
				e.printStackTrace();
			}
		}
		
		
		return entity;
	}
	
	
	
	
	
	
	
	@RequestMapping(value="boardContents.aws")			// 글 내용 가상경로 실행시 동작하는 기능 조회수++ 글 보여주기 기능 
	public String boardContents(
			@RequestParam("bidx") int bidx,
			Model model) {	
		
		boardService.boardViewCntUpdate(bidx);		// 조회수 증가 메서드 불러와서 사용
		BoardVo bv = boardService.boardSelectOne(bidx);		// 글 내용 1개만 가져와서 보여주는 메서드 
		
		
		model.addAttribute("bv", bv); // 모델에 bv담아와서 속성값 주기 
				
		
		
		
		String path = "WEB-INF/board/boardContents"; 		// 메서드 모두 실행 후  결과는 내용페이지에서 보여준다
		return path;
	}
	
	//추천수 증가 컨트롤러
	@ResponseBody		// 객체를 받겠다는 어노테이션
	@RequestMapping(value="boardRecom.aws", method = RequestMethod.GET)			// 가상경로 boardRecom.aws 가 들어오면 실행되는 컨트롤러 
	public JSONObject boardRecom(@RequestParam("bidx") int bidx) {	// 리턴값 JSONObject 으로 설정 > @ResponseBody 해서 객체를 받는다고 설정
		
		int value = boardService.boardRecomUpdate(bidx);		// value 에 boardRecomUpdate메서드 실행하고 결과값 담기 
				
		JSONObject js = new JSONObject();		// 제이슨 객체 생성해주고 
		js.put("recom", value);		// put 로 객체에 담기 
		
		
		return js;// js 리턴해주기 
	}
	
	@RequestMapping(value="boardDelete.aws")			// 여기는 메서드 없음		
	public String boardDelete(
			@RequestParam("bidx") int bidx,
			Model model) {	
		
		model.addAttribute("bidx", bidx);
		String path = "WEB-INF/board/boardDelete"; 		
		return path;
	}
	
	
	
	
	@RequestMapping(value="boardDeleteAction.aws", method = RequestMethod.POST)		// 여기가 메서드 있음
	public String boardDeleteAction(
			@RequestParam("bidx") int bidx,
			@RequestParam("password") String password,
			RedirectAttributes rttr,
			HttpSession session) {	
		BoardVo bv = boardService.boardSelectOne(bidx);
		
		int midx = Integer.parseInt(session.getAttribute("midx").toString());
		int value = boardService.boardDeleteAction(bidx, midx, password);
		
		String path = "";
		if(bv.getMidx()==midx) {
			if(value ==1) {
				path = "redirect:/board/boardList.aws";		// 성공하면 리스트페이지로  보내주고
				
			}else {
				rttr.addFlashAttribute("msg", "비밀번호가 틀렸습니다");
				path = "redirect:/board/boardDelete.aws?bidx="+bidx;		// 비밀번호가 틀려서 실패하면 bidx 를 가지고  삭제페이지로 보내기
			}	
		}else {
			rttr.addFlashAttribute("msg", "글쓴회원이 아닙니다");
			path = "redirect:/board/boardDelete.aws?bidx="+bidx;		// 회원번호가 달라서  실패하면 bidx 가지고 삭제페이지로 보내기 메시지만 다르게 
			
		}	
		 return path;		// 경로 리턴
	}
	
	@RequestMapping(value="boardModify.aws")
	public String boardModify(@RequestParam("bidx") int bidx,Model model) {
		
		BoardVo bv = boardService.boardSelectOne(bidx);		
		model.addAttribute("bv", bv);	
		
		String path="WEB-INF/board/boardModify";
		return path;
	}
	
	@RequestMapping(value="boardModifyAction.aws")
	public String boardModifyAction(
			BoardVo bv,		// 제목 내용 작성자 비밀번호 bv에 담아오기
			@RequestParam("attachfile") MultipartFile attachfile,	//첨부파일은 멀티파트로 담아오기 
			HttpServletRequest request,		// 세션 요청하기 위한 객체 생성 > 회원번호 담을 것
			RedirectAttributes rttr) 		// 리다이렉트 속성값 담기 위한 객체 생성
					throws Exception {
		
			int value=0;
		
			MultipartFile file = attachfile;
			String uploadedFileName="";
			
			if (!file.getOriginalFilename().equals("")) {		// 원본파일 이름이 있으면	
				uploadedFileName = UploadFileUtiles.uploadFile(uploadPath, file.getOriginalFilename(), file.getBytes());	//uploadedFileName에 파일 정보들 담기
			}
			
			String midx = request.getSession().getAttribute("midx").toString();
			int midx_int = Integer.parseInt(midx);	// 회원번호 담기		
			String ip = getUserIp(request);	// 아이피 담기 
			
			bv.setUploadedFilename(uploadedFileName);   // bv 에 파일이름 세팅
			bv.setMidx(midx_int);						// 회원번호 세팅
			bv.setIp(ip);								// ip 정보 세팅
			
		
			value = boardService.boardUpdate(bv);		// 서비스에서 구현한 업데이트 메서드 안에 세팅한 파일이름, 회원번호, 아이피 정보 담아서 사용하기 
		
			String path="";
			if (value==0) {		// 성공하면
				path="redirect:/board/boardContents.aws?bidx="+bv.getBidx();	// 글번호 달고 글 내용보여주기
				
			}else {
				rttr.addFlashAttribute("msg", "실패");
				path="redirect:/board/boardModify.aws?bidx="+bv.getBidx();	// 실패하면 글번호 가지고 다시 수정하기 페이지로 보내기
				
			}		
		
			return path;		// 경로 리턴
	}
	
	@RequestMapping(value="boardReply.aws")		// 답변하기 페이지 보여주기만
	public String boardReply(@RequestParam("bidx") int bidx,  Model model) {		// 글번호 가지고 갈거고 모델에 담아서 보여주기
		
		BoardVo bv = boardService.boardSelectOne(bidx);			// 글 한개만 선택해서 가져오기  
		
		model.addAttribute("bv", bv);			// bv모델에 담기
		String path="WEB-INF/board/boardReply";	// 경로 답변하기로 보내주기  
		return path;
	}
	
	@RequestMapping(value="boardReplyAction.aws")		// 답변 동작 구현
	public String boardReplyAction(
			BoardVo bv,					// 제목, 내용, 작성자, 비밀번호 bv에 담아오기 
			@RequestParam("attachfile") MultipartFile attachfile,	// 첨부파일은 멀티파트로
			HttpServletRequest request,			// 세션정보 담기 회원번호 목적
			RedirectAttributes rttr)  			// 리다이렉트 객체 생성 
					throws Exception {
		
			int value=0;	// 결과값 초기화	
			MultipartFile file = attachfile;
			String uploadedFileName="";
			
			if (!file.getOriginalFilename().equals("")) {			// 원본파일이 있으면 
				uploadedFileName = UploadFileUtiles.uploadFile(uploadPath, file.getOriginalFilename(), file.getBytes());
			}		// 여기에 담고
			
			String midx = request.getSession().getAttribute("midx").toString();
			int midx_int = Integer.parseInt(midx);			// 회원번호 담기
			String ip = getUserIp(request);					// 아이피 정보 담기 
			
			bv.setUploadedFilename(uploadedFileName);   // bv에 파일이름 세팅해주기
			bv.setMidx(midx_int);						//bv에 회원번호 세팅해주기 
			bv.setIp(ip);								// bv에 아이피정보 세팅해주기
			
			
			int maxBidx=0;								// 
			maxBidx = boardService.boardReply(bv);		//메서드 실행하고 나온 결과값 maxBidx에 담기
			//System.out.println("maxBidx"+maxBidx);
			
			String path="";
			if (maxBidx !=0) {
				path="redirect:/board/boardContents.aws?bidx="+maxBidx;		// 결과값이 0이 아닌경우 > 성공한것이므로 글내용으로 보내기 + 여기서 사용하려고 위에 리다이렉트 객체 생성한 것
			}else {
				rttr.addFlashAttribute("msg", "실패");		// 결과값이 0인 경우 > 실패 한 것이므로 다시 답변하기 페이지로 보내기 
				path="redirect:/board/boardReply.aws?bidx="+bv.getBidx();
			}		
		
			return path;		// 결과 경로 리턴해주기
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



