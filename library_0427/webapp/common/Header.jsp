<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<style>
/* 	*{
		padding: 0;
		margin: 0;
	}
	
	body{
		margin: 0 auto;
		width: 500px;
		border: 1px solid black;
	}
	*/
	*{
		margin:0;
		padding-top: 0px;
		
	}
    .menu-container {
	        display: flex;
	        justify-content: space-between;
	        align-items: center;
    } 
    #table{
    	border-collapse: collapse;
    	border: 0px solid white;
    	background-color:  rgb(217, 254, 255); 
    	
    }
    .tableHeader{
    	
    }
	a{
	
		text-decoration: none;
		color: black;
		display: inline-block;
		padding: 20px;
		
	}
	a:hover{
		color: white;
		background-color: rgb(253, 211, 218);
		border-radius: 10px;
	}
	
</style>
<title>Insert title here</title>
</head>
<body>
<!--  관리자 일때 보여주는 메뉴  ( 회원관리 / 도서관리 )  / 사용자일때 보여주는 메뉴  -->
<header>
<!--	userId : ${sessionScope.userId } 
		adminYn : ${sessionScope.adminYN }
 -->	

			
<table border="1" width="100%" id="table">
  <tr>
    <td align="center" class ="tableHeader">
        <div class = "menu-container">
        <!-- 관리자인 경우 -->
       <c:if test="${sessionScope.adminYN eq 'Y'}" var ="res">
       	<div>👨🏻‍🔧 관리자 페이지</div>
       	<div><a href="../member/List.member"> 📚 회원관리</a></div>
       	<div><a href="./List.jsp">📚 도서관리</a></div>
       	<div><a href="../login.jsp">📚 로그아웃</a></div>
       </c:if>
       
        
       <c:if test="${not res}">
		<div> 👨‍❤️‍👨 ${userId}님 환영합니다</div>
		<div><a href= "./List.jsp">📚 도서관 이용안내</a></div>
		<div><a href= "./List.jsp"> 📚 내서재</a></div>
		<div><a href="../login.jsp">📚 로그아웃</a></div>
		</c:if>
			        <!--  로그인 전 사용자  -->
			<c:if test="${empty sessionScope.userId }" var = "res1">
			</c:if>
	<!--  사용자 -->
	<c:if test="${not res1 }">
		</c:if>
	</div>
		</td>
	</table>


	
</header>
</body>
</html>