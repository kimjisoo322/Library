package com.library.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.library.service.MemberService;
import com.library.vo.Criteria;
import com.library.vo.PageDto;

@WebServlet("*.member")
public class memberController extends HttpServlet {
	
	MemberService ms = new MemberService();
	
	public memberController() {
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		String uri = req.getRequestURI(); System.out.println("요청 uri : memcontroller"+ uri);
		
		 if (uri.indexOf("List")> 0) { 
			  
			  // 검색어, 페이징 정보 
			  String searchWord = req.getParameter("searchWord");
			  String searchField = req.getParameter("searchField");
			  String pageNo = req.getParameter("pageNo") == null? "1" : req.getParameter("pageNo");
			  
			  // 검색어 페이징 정보 세팅
			  Criteria criteria = new Criteria(searchField, searchWord, pageNo);
			  
			  req.setAttribute("mem", ms.getListPageM(criteria));
				  
			  // 총건수 
			  int totalCntM = ms.totalCntM(criteria);
			  req.setAttribute("totalCntM", totalCntM);
			  
			  // 페이징 처리 
			  PageDto pageDto = new PageDto(totalCntM, criteria);
			  req.setAttribute("pageDto", pageDto);
			  
			  req.getRequestDispatcher("./member.jsp").forward(req, resp); 
			  
			  
			  }
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

	}
}
