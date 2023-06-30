<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<%@ include file = "common/Header.jsp" %>
<h2> 관리자 메뉴</h2>
	<%
	// 세션에 저장된 멤버 객체로부터 아이디를 출력한다.
	Object name = "";
	name = (Object)session.getAttribute("userId");
	
	 %>
	<%= name+ "님 환영합니다." %>
   	<button onclick= "location.href='logout2.jsp'">로그아웃</button>
<ul>

	<li>도서등록</li>
	<li>도서 삭제 - 도서 상세보기 삭제 버튼을 이용하세요.</li>
	<li>관리자등록</li>
	<li>관리자삭제</li>
</ul>
</body>
</html>