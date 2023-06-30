package com.util;

import javax.security.auth.message.callback.PrivateKeyCallback.Request;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CookieManager {
// 쿠키를 공통으로 쓰기 위해 객체를 생성하고 메서드를 만들어준다. 
	public CookieManager() {	
	
	}
	//(메서드생성) 이름, 값, 유지시간을 매개변수로 받아서 쿠키를 생성후 응답 객체에 담기  
	// ->쿠키를 반환하는 게 아닌 응답객체에 쿠키를 담는 역할까지 해서 매개변수에 응답객체도 담아야한다.
	public static void makeCookie(HttpServletResponse response, String cname, String cvalue, int cTime) {
		// 쿠키 생성
		Cookie cookie = new Cookie(cname, cvalue);

		// 경로 설정
		cookie.setPath("/");
		
		// 유지기간설정
		cookie.setMaxAge(cTime);
		
		// 응답객체 추가 
		response.addCookie(cookie);
	}
	//(메서드생성) 매개변수로 쿠키이름을 받아서 쿠키값을 반환 
	
	public static String readCookie(HttpServletRequest request, String cname) {
		String cookieValue = "";
		
		Cookie[] cookies = request.getCookies();
		// 쿠키 배열을 돌면서 일치하는 이름을 찾아 value 값을 반환
		if(cookies != null) {
		for(Cookie cookie : cookies) {
			String cookieName = cookie.getName();
			if(cname.equals(cookieName)) {
				cookieValue = cookie.getValue();
				break;
			}
		}
		}
		return cookieValue;
	}
		
	//(메서드생성) 매개변수로 쿠키이름을 받아서 쿠키 삭제 
	
	public static void deleteCookie(HttpServletResponse response, String cname) {
		//쿠키이름을 받아서 쿠키를 삭제하기 때문에 만든 쿠키를 가져옴
		makeCookie(response, cname, "", 0);
	}
}
