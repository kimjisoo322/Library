<%@page import="com.util.CookieManager"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<style>
	body{
		background-color:rgb(231, 254, 255);
	}
	*{	
		text-decoration: none;
		color: black;
	}
	

</style>

</head>
<body>

 
  <%
  	String userId = CookieManager.readCookie(request, "userId");
   		Object name = "";
   		if(session.getAttribute("member") != null){
   			name = (Object)session.getAttribute("member");
   		}
   		if(name != null && !name.equals("")){
   		// 로그인 되었다고 판단	
  %>
   	 <%
   		} else {
   		
   		}	
    %>
    <%
   		String error = request.getParameter("error");
   		if("Y".equals(error) && error != null){
   			out.print("<script>alert('아이디, 비밀번호 확인')</script>");
   	%>
   	<% 	}	
   		
    %>    


<form action="./login/LoginAction.do" method="post">
<aside id='rightside'>
	<div class='side1'>
<div class='loginbox'>
	<div id='login'>	
	

<input type="text" name="userId" id="userId" placeholder='ID를 입력해주세요.' value ="<%=userId %>">
<input type="password" name="userpw" id="userpw" placeholder='PW를 입력해주세요.'>
                
	</div>
	
	<div id='button'>
         <input type="submit" value="로그인">
	</div>
</div>

  <div id='info'>       
  					<!--  userid가 비어있지 않으면 checked 체크해주고 아니면 ""  -->
   <input type ="checkbox" name = "savecheck" value="Y" <%=!userId.equals("")? "checked" : ""%>>아이디저장하기<br>  
    <a href="">회원가입</a>
    <a href="">ID찾기</a>
    <a href="">PW찾기</a>
</div>
      
</aside>
</form>     
</body>
</html>