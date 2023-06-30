package com.library.vo;

public class Criteria {

	private String searchField =""; // 검색 조건
	private String searchWorld=""; // 검색어
	
	int pageNo = 1; // 요청한 페이지 번호
	int amount = 5; // 한페이지당 보여질 게시물 수 
	
	int startNo = 1;
	int endNo= 5;
	
	public Criteria() {
		
	}
	// 페이지 번호 ( 0보다 높으면 ) 실행 
	public Criteria(int pageNo) {
		if(pageNo > 0) {
		this.pageNo = pageNo;
			endNo = pageNo * amount; // 페이지끝번호는 요청한 페이지 번호 * 게시물수  
			startNo = (pageNo * amount) - (amount -1) ; // 페이지시작번호는 요청한 페이지 번호 * 게시물수 -1  
		}
	}
	
	// pageNo는 int로 생성했지만 파라메터로 받을 때는 문자열로 받아오기 때문에 String pageNoStr을 만들어 놓은 것
	// 원래 pageNo는 int이기 때문에 생성자 안에서 pageNoStr을 int로 정수변환해서 값은 int로 세팅하지만 
	// 생성자로는 String 타입으로 주고 있기 때문에 밖에서 사용할 때 파라메터(String)으로 쓸 수 있는 것.
	public Criteria(String searchField, String searchWorld, String pageNoStr) {
		if(searchWorld != null) {
			this.searchField = searchField;
			this.searchWorld = searchWorld;
		}
		// 페이지 번호가 있으면 페이지 번호를 문자열에서 정수로 전환해서 값 세팅 / 페이지 번호가 없으면 1로 처리
		if(pageNoStr != null && !"".equals(pageNoStr)) {
			pageNo = Integer.parseInt(pageNoStr);
			if(pageNo > 0) {
				this.pageNo = pageNo;
				endNo = pageNo * amount; // 페이지끝번호는 요청한 페이지 번호 * 게시물수  
				startNo =(pageNo * amount) - (amount -1) ; // 페이지시작번호는 요청한 페이지 번호 * 게시물수 -1  
			}else {
				pageNo = 1;
			}
		}
	}
	
	public String getSearchField() {
		return searchField;
	}
	public void setSearchField(String searchField) {
		this.searchField = searchField;
	}
	public String getSearchWorld() {
		return searchWorld;
	}
	public void setSearchWorld(String searchWorld) {
		this.searchWorld = searchWorld;
	}
	public int getPageNo() {
		return pageNo;
	}
	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
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
	
	
}
