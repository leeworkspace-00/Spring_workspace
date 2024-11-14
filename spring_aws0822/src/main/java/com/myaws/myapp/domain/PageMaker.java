package com.myaws.myapp.domain;

import org.springframework.stereotype.Component;

@Component // 빈 등록 서비스나 컨트롤러 대신 객체생성해달라고 요청

public class PageMaker {
	private int displayPageNum = 10; // 페이지 목록번호 리스트 "<< < 12345678910 > >>"
	private int startPage; // 그 목록의 시작 번호를 담는 변수 "<< < 1 11 21 31 41 ..."
	private int endPage; // 목록의 끝 번호를 담는 변수 "...10 20 30 40 50 60 > >>"
	private int totalCount; // 총 게시물의 개수를 담는 변수

	private boolean prev; // 이전버튼
	private boolean next; // 다음버튼
	

	private searchCriteria scri;

	// private Criteria cri; // 가지고 다닐 페이지 번호

	public searchCriteria getScri() {
		return scri;
	}

	public void setScri(searchCriteria scri) {
		this.scri = scri;
	}

	public int getDisplayPageNum() {
		return displayPageNum;
	}

	public void setDisplayPageNum(int displayPageNum) {
		this.displayPageNum = displayPageNum;
	}

	public int getStartPage() {
		return startPage;
	}

	public void setStartPage(int startPage) {
		this.startPage = startPage;
	}

	public int getEndPage() {
		return endPage;
	}

	public void setEndPage(int endPage) {
		this.endPage = endPage;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) { // 총 게시물이 몇개인지 받는 메서드
		this.totalCount = totalCount;
		calcData(); // 페이지 목록 리스트 번호를 나타내주기 위한 계산식

	}

	public boolean isPrev() {
		return prev;
	}

	public void setPrev(boolean prev) {
		this.prev = prev;
	}

	public boolean isNext() {
		return next;
	}

	public void setNext(boolean next) {
		this.next = next;
	}

	/*
	 * public Criteria getCri() { return cri; }
	 * 
	 * public void setCri(Criteria cri) { this.cri = cri; }
	 */

	private void calcData() { // 페이지 목록 리스트 번호를 나타내주기 위한 계산식

		// 기본적으로 1에서 부터 10까지 나타나게 설정한다(페이지네비게이션에서)
		endPage = (int) (Math.ceil(scri.getPage() / (double) displayPageNum) * displayPageNum);
		// Math.ceil : 모두 올림처리하는 메서드
		// displayPageNum은 10으로 세팅했고 ,..... 암튼 endPageeh 10임
		// 2. endPage설정완료했으면 > 시작페이지 설정
		startPage = endPage - displayPageNum + 1;
		// endPage에서 displayPageNum에 1더한 값을 빼면 시작페이지 값임
		// 3. 실제 게시물 수에 따라서 endPage를 구하겠다
		int tempEndPage = (int) (Math.ceil(totalCount / (double) scri.getPerPageNum()));
		// 진짜 개수를 가져와서 보여지는 페이지 개수로 나눠서 보여줄거임

		// 4. 설정한 endPage와 실제 endPage를 비교해서 최종 endPage를 구한다
		if (endPage > tempEndPage) {
			endPage = tempEndPage; //
		}
		// 5. 이전 다음 버튼 만들기
		// 첫 페이지가 1이면 이전 페이지가 없으니까 보여줄 필요가 없다~
		prev = (startPage == 1 ? false : true); // > 삼항 연산자 사용
		// startPage가 1이면 false고 아니면 true다

		// 6. 다음버튼 : 마지막 페이지 곱하기 보여지는 게시물 총게시물 개수보다 크거나 같으면 다음 버튼을 보여줘야한다.
		next = (endPage * scri.getPerPageNum() >= totalCount ? false : true);
		//

	}

}
