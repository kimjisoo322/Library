package com.library.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.library.service.MemberService;
import com.library.vo.Member;
import com.util.CookieManager;

/**
 * Servlet implementation class LoginActionController
 */
@WebServlet("/login/LoginAction.do")
public class LoginActionController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public LoginActionController() {

    }
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// name 속성의 값을 매개값으로 넘겨주면 value 속성의 값을 반환합니다.  // 관리자이면 도서 등록버튼 보여줌
				String id = request.getParameter("userId");
				String pw = request.getParameter("userpw");
				
				String saveCheck = request.getParameter("savecheck");
				System.out.print("SAVECHECK" + saveCheck);
				// 체크박스가 체크되었을 경우, 아이디를 쿠키에 저장
				if(saveCheck != null && "Y".equals(saveCheck)){
					CookieManager.makeCookie(response, "userId", id, 60*60*27*7);
				}
				
				MemberService service = new MemberService();
				Member member = service.login(id, pw);
				
				if(member != null && !member.equals("")){
					HttpSession session = request.getSession();
					
					session.setAttribute("member", member);
					session.setAttribute("userId", member.getId());
					
					System.out.print("확인)" +member.getId() + "님 환영합니다.");
					
					
					if("Y".equals(member.getAdminyn())){
						//session.Scope ${adminYN} = Y  
						// 관리자인 경우 adminYN = Y
						session.setAttribute("adminYN", "Y");
					
					}
					response.sendRedirect("../book/List.book");
					//request.getRequestDispatcher("../book/List.book").forward(request, response);
				} else{
					response.sendRedirect("login.jsp?error=Y");	
				}
	}

}
