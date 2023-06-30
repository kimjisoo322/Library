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
<h2> 사용자 메뉴</h2>
	<%
	Object name = "";
	name = (Object)session.getAttribute("userId");
	 %>
	<%= name + "님 환영합니다." %>
   	<button onclick= "location.href='logout2.jsp'">로그아웃</button>
<ul>

	<li>도서대여</li>
	<li>도서반납</li>
</ul>
</body>
</html>