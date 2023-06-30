package com.library.vo;

public class Book {
	private int no;
	private String title;
	private String publisher; // 출판사 
	private String author;   // 저자 
	
	private String visitcount; // 도서 조회수 
	
	private String ofile;   // 원본파일명
	private String sfile;   // 저장된 파일명 
	private String rentyn;   // 도서 대여여부 
	
	private String rentid; // 대여자 아이디
	private String rentno;  // 대여번호 
	private String startdate; // 대여일
	private String returndate; //반납일
	private String enddate; // 반납가능일
	
	
	

	
	public Book() {

	}
	
	public Book(String title, String author, String publisher, String ofile, String sfile) {
		this.title = title;
		this.author = author;
		this.publisher = publisher;
		this.visitcount = "0";
		// 신규도서이므로 N
		this.rentyn = "N";
		this.ofile = ofile;
		this.sfile = sfile;
	
	}
	
	
	// 도서를 추가할 경우 도서명과 작가명만 알고 있으면 생성 가능
	public Book(String title, String author, String publisher) {
		this.title = title;
		this.author = author;
		this.publisher = publisher;
		this.visitcount = "0";
		// 신규도서이므로 N
		this.rentyn = "N";
	}
	
	public Book(int no, String title, String publisher,  String rentyn, String author ) {
		super();
		this.no = no;
		this.title = title;
		this.publisher =publisher;
		this.rentyn = rentyn;
		this.author = author;
		
	}


	@Override
	public String toString() {
		String rentYNStr = "";
		
		// 도서가 rentYN=Y(대여중)인 경우 대여중으로 표시
		if("Y".equals(getRentyn())) {
			rentYNStr="대여중";
		}
		return getNo()
				+ " " + getTitle()
				+ " " + getAuthor()
				+ " " + rentYNStr;
	}
	
	public int getNo() {
		return no;
	}
	public void setNo(int no) {
		this.no = no;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getRentyn() {
		return rentyn;
	}
	public void setRentyn(String rentyn) {
		this.rentyn = rentyn;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	
	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}
	public String getVisitcount() {
		return visitcount;
	}

	public void setVisitcount(String visitcount) {
		this.visitcount = visitcount;
	}


	public String getOfile() {
		return ofile;
	}

	public void setOfile(String ofile) {
		this.ofile = ofile;
	}

	public String getSfile() {
		return sfile;
	}

	public void setSfile(String sfile) {
		this.sfile = sfile;
	}

	public String getRentid() {
		return rentid;
	}

	public void setRentid(String rentid) {
		this.rentid = rentid;
	}

	public String getRentno() {
		return rentno;
	}

	public void setRentno(String rentno) {
		this.rentno = rentno;
	}

	public String getStartdate() {
		return startdate;
	}

	public void setStartdate(String startdate) {
		this.startdate = startdate;
	}

	public String getReturndate() {
		return returndate;
	}

	public void setReturndate(String returndate) {
		this.returndate = returndate;
	}

	public String getEnddate() {
		return enddate;
	}

	public void setEnddate(String enddate) {
		this.enddate = enddate;
	}


}
