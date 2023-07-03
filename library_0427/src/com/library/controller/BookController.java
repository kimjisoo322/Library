package com.library.controller;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.library.common.FileUtil;
import com.library.common.JSFunction;
import com.library.service.BookService;
import com.library.service.MemberService;
import com.library.vo.Book;
import com.library.vo.Criteria;

import com.library.vo.PageDto;
import com.oreilly.servlet.MultipartRequest;


@WebServlet("*.book")
public class BookController extends HttpServlet{
// list.jsp (도서목록조회)  = 서블릿 
	MemberService ms = new MemberService();
	BookService bs = new BookService();
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String uri  =  req.getRequestURI();
		System.out.println("요청 uri : " + uri);
		
		// 만약에 list가 포함되어있다면 목록을 보여줌! 
		if(uri.indexOf("List") > 0) {
			// 리스트 / 페이징 / 총건수  => bookservice에서 다 만들고 매개변수에 criteria 생성 (map.listput/ map.totalput/map) 
			System.out.println();
			
			// 검색폼 
			String searchWord = req.getParameter("searchWord");
			String searchField = req.getParameter("searchField");
			String pageNo = req.getParameter("pageNo") == null ? "1" : req.getParameter("pageNo");
			
			// 검색어, 페이징 정보 세팅 
			Criteria criteria = new Criteria(searchField, searchWord, pageNo); 
			
			req.setAttribute("list", bs.getListPage(criteria));
			
			// 총 건수 
			int totalCnt = bs.totalCount(criteria);
			req.setAttribute("totalCnt", totalCnt);
			
			// 페이징처리
			PageDto pageDto = new PageDto(totalCnt,criteria);
			req.setAttribute("pageDto", pageDto);
			
			req.getRequestDispatcher("./List.jsp").forward(req, resp);
			
		}
		
		// 도서 삭제
		else if (uri.indexOf("delete") > 0) {
		    String delNo = req.getParameter("delNo");
		    
		    int delres = bs.delete(delNo);
		    
		    if (delres > 0) {
		        req.setAttribute("message", delres + "건 도서 삭제 성공");
		    } else {
		        req.setAttribute("message", "도서 삭제 실패");
		    }
		    req.getRequestDispatcher("./List.book").forward(req, resp);
		}

		// 도서 상세보기
		else if(uri.indexOf("view") > 0) {

			// 조회수
			bs.updateVisit(req.getParameter("no"));
			
			// 책 상세보기
			Book book =  bs.selectOne(req.getParameter("no"), req.getParameter("rentno"));
			
			req.setAttribute("book", book);
			
			req.getRequestDispatcher("./view.jsp").forward(req, resp);
		}
		
	
	}
	
	@Override
		protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String uri  =  req.getRequestURI();
		// 도서 등록
		 if(uri.indexOf("insert") > 0) {
			
			 // 등록 시, 한글깨짐 방지!!!!!
			req.setCharacterEncoding("utf-8");
			
			// 파일 등록 (파일 업로드)
			//	"C:\\Users\\user\\Downloads\\library_0428\\library_0427\\webapp\\IMGES\\bookImg";
			String saveDirectory = "C:\\Users\\user\\git\\Library\\library_0427\\webapp\\IMGES\\bookImg";
			MultipartRequest mr =  FileUtil.uploadFile(req, saveDirectory, 1024*1000);
			
			String ofile = mr.getFilesystemName("bookImg");
			
			if(ofile != null && !"".equals(ofile)) {
				String sfile = FileUtil.fileNameChange(saveDirectory, ofile);
				
				int insertres = bs.insert
						(mr.getParameter("title"), mr.getParameter("author"), mr.getParameter("publisher"), 
						ofile, sfile );
				
				if(insertres > 0) {
					req.setAttribute("message", insertres + "건 도서 등록");
				} else {
					req.setAttribute("message", "도서 등록 실패");
				}
			}
			
			resp.sendRedirect("./List.book");
			//req.getRequestDispatcher("./insert.jsp").forward(req, resp);
		}
		 // 도서 대여
			else if(uri.indexOf("rent")> 0) {
				// 로그인한 사용자 인증
				HttpSession session =  req.getSession();
				if(session.getAttribute("userId") == null) {
					JSFunction.alertBack(resp, "로그인 후 이용 가능한 메뉴입니다.");
					return;
				}
				// 대여하기 - 책번호, 로그인 아이디
				Book book = new Book();
				book.setNo(Integer.parseInt(req.getParameter("no")));
				book.setRentid(session.getAttribute("userId").toString());
				
				int res = bs.rentBook(book);
				if(res > 0) {
					JSFunction.alertLocation(resp, "대여되었습니다.", "./view.book?no="+book.getNo());

				}else {
					JSFunction.alertBack(resp, "대여중 오류발생!");
				}
			}
		 	// 도서 반납
			else if (uri.indexOf("return") > 0) {
				HttpSession session = req.getSession();
				if(session.getAttribute("userId") == null) {
					JSFunction.alertBack(resp, "로그인 후 이용 가능한 메뉴입니다.");
					return;
				}
				// 반납하기 (rentno / userid) 
				Book book = new Book();
				book.setNo(Integer.parseInt(req.getParameter("no")));
				book.setRentid(session.getAttribute("userId").toString());
				book.setRentno(req.getParameter("rentno"));
				
				int res = bs.returnBook(book.getNo(), book.getRentno());
				if(res > 0) {
					JSFunction.alertLocation(resp, "반납되었습니다.", "./view.book?no="+book.getNo());
				}else {
					JSFunction.alertBack(resp, "반납 중 오류 발생 ! ㅠㅠ" );
				}
				
				
			}
	}
	
	public BookController() {
		
	}

}
