package com.library.vo;
 // 페이지블럭dto
public class PageDto {

	int startNo; //페이지 블럭 시작번호
	int endNo;   //페이지 블럭 끝번호 
	int realEnd; //게시물의 끝 페이지번호 ( << 끝페이지로 이동 >>) 
	boolean prev, next; // 이전, 다음 버튼(true면 보여주고 false면 보여주지 않음) 
	
	int total;   //총 게시물의 수 
	Criteria criteria;  //페이지 번호, 페이지당 게시물 수
	
	
	public PageDto() {

	}

	// 페이지블럭 생성시 필요한 총 게시물 수, 페이지번호, 페이지당 게시물 수
	public PageDto(int total, Criteria criteria) {
		this.total = total;
		this.criteria = criteria;
		
		// 페이지 블럭의 끝번호 구하기 
		// 7페이지 요청 (7/10.0) -> 올림처리 -> 10 
		endNo = (int)Math.ceil(criteria.pageNo/5.0) * 5; 
		startNo = endNo - (5-1);
		
		// 끝 페이지 번호 
		// 55/10 = 5페이지 밖에 안나와서 형변환하고 올림처리-> 더블타입으로 계산 후 올림처리 
		realEnd = (int)(Math.ceil((total*1.0)/criteria.getAmount()));
		
		// 끝번호는 진짜 끝페이지 보다 크면 끝페이지 아니면 끝번호 
		// 예를 들어서 만약 끝번호가 6페이지면 진짜 끝페이지보다 작아 그럼 6페이지만 보여주고 
		// 게시물의 끝페이지보다 큰 경우, 게시물의 끝페이지로 설정 
		endNo = endNo>realEnd? realEnd : endNo;

		// <   앞, 뒤로 이동   >
		prev = startNo > 1 ? true : false;
		next = endNo == realEnd ? false : true;
	}


	public int getStartNo() {
		return startNo;
	}


	public void setStartNo(int startNo) {
		this.startNo = startNo;
	}


	public int getEndNo() {
		return endNo;
	}


	public void setEndNo(int endNo) {
		this.endNo = endNo;
	}


	public int getRealEnd() {
		return realEnd;
	}


	public void setRealEnd(int realEnd) {
		this.realEnd = realEnd;
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


	public int getTotal() {
		return total;
	}


	public void setTotal(int total) {
		this.total = total;
	}


	public Criteria getCriteria() {
		return criteria;
	}


	public void setCriteria(Criteria criteria) {
		this.criteria = criteria;
	}
}
