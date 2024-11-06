package com.myaws.myapp.controller;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


import com.myaws.myapp.domain.BoardVo;
import com.myaws.myapp.domain.PageMaker;
import com.myaws.myapp.domain.searchCriteria;
import com.myaws.myapp.service.BoardService;
import com.myaws.myapp.service.BoardServiceImpl;


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



