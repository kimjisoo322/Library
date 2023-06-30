package com.library.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.library.dao.BookDao;
import com.library.vo.Book;
import com.library.vo.Criteria;
import com.library.vo.PageDto;

public class BookService {
	BookDao dao = new BookDao();
	
	/**
	 * 책 리스트를 조회 합니다.
	 * @return
	 */
	public List<Book> getList(){
		List<Book> list = dao.getList();
		for(Book book : list) {
			System.out.println(book);
		}
		System.out.println("도서목록 : " + list);
		return list;
	}

	/**
	 * 도서 정보 입력 ( 등록 ) 
	 */
	public int insert(String title, String author, String publisher, String ofile, String sfile) {
		Book book = new Book(title, author, publisher, ofile, sfile);
		
		System.out.println(" book " + book);
		
		int res = dao.insert(book);
		if(res > 0) {
			System.out.println(res + "건 입력 되었습니다.");
		} else {
			System.err.println("입력중 오류가 발생 하였습니다.");
			System.err.println("관리자에게 문의 해주세요");
		}
		System.out.println("도서등록 : " +res);
		return res; 
	}
	

	public int delete(String no) {
		int res = dao.delete(no);
		if(res > 0) {
			System.out.println(res+"건 삭제되었습니다.");
		} else {
			System.err.println("삭제중 오류가 발생 하였습니다.");
			System.err.println("관리자에게 문의 해주세요");
		}
		System.out.println(res);
		return res;
	}
	
	// 도서 대여 
	public int rentBook(Book book) {
		int res = dao.rentBook(book);
		return res;
	}

	// 도서 반납
	public int returnBook(int no, String rentno) {
		int res = dao.returnBook(no, rentno);
		return res;
	}
	// 도서 반납
	/*
	 * public void returnBook(int no) { // 반납가능한 도서인지 확인 String rentYN =
	 *   dao.getRentYN(no); // Y 면 반납하기 가능 if("N".equals(rentYN)) {
	 * System.err.println("반납 가능한 상태가 아닙니다."); } else if ("".equals(rentYN)) {
	 * System.out.println("없는 도서 번호 입니다."); }
	 * 
	 * // 반납처리 int res = dao.update(no, "N");
	 * 
	 * if(res>0) { System.out.println(res + "건 반납 되었습니다."); }else {
	 * System.out.println("반납 처리 중 오류가 발생 하였습니다.");
	 * System.out.println("관리자에게 문의 해주세요"); } }
	 */
		// 총 건수
	public int totalCount(Criteria criteria) {
		int res = dao.totalCount(criteria);
		if(res > 0) {
			System.out.println(res + "건");
		}else {
			System.err.println(res  +"건 조회불가능");
		}
		return res;
	}

	// 리스트 조회 (검색조건 추가) + 페이징처리
	public Object getListPage(Criteria criteira) {
		List<Book> list = dao.getListPage(criteira);
		for(Book book : list) {
			System.out.println(book);
		}
		return list;
	}
	
	// 도서 상세보기
	public Book selectOne(String no, String rentno) {
		Book book  = dao.selectOne(no,rentno);
		System.out.println("상세보기 : " +book);
		return book;
	}
	
	// 조회수 
	public int updateVisit(String no) {
		int res = dao.updateVisit(no);
		System.out.println(" 조회수 :  " + res);
		return res;
	}
}

