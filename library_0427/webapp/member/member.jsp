<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<style>
	.tables{
		border: 1px solid black;
		width: 500px;
		height : 100px;
	}
	.tables2{
		width: 500px;
		border: 2px solid begie;
		height: 50px;
	}
</style>
<title>Insert title here</title>
</head>
<body>
 <h3> 회원 목록👨‍❤️‍👨 </h3>
 
 
<c:set var = "totalCntM" value = "${totalCntM}"></c:set>
 	
▶ 회원 총 건수 :  ${totalCntM} 건
 
 
 <!--  검색 폼  -->
 <form name ="searchForm" method ="get" >

	<input type="text" name ='pageNo' value ="${param.pageNo }"></input>
	<input type="text" name ='delNo' value ="${param.delNo }"></input>
	
	<table border ='1' width ="100%">
		<tr>
		<td align = "center">
			<select name ="searchField">
				<option value = "id"${param.searchField eq "id" ? "selected" : ""}>ID</option>
				<option value = "name"${param.searchField eq "name" ? "selected" : ""}>회원명</option>
				<option value = "grade"${param.searchField eq "grade" ? "selected" : ""}>등급</option>
			</select>
			
			<input type="text" name = "searchWord" value = "${param.searchWord}" ></input>
			<input type ="submit" value ="검색하기">
		</td>
		</tr>
	</table>
</form> 	

 
 <table border = '1' width = "90%" class = "tables">
<c:set var="mem" value="${mem}"></c:set>
	<tr>
		<td colspan ="7" class ="right">
		 <div class="button-container">
			<!--  어드민 계정인 경우 (등록, 삭제 버튼 출력) -->
			<button onclick="location.href='../book/insert.jsp';" >회원등록</button>
			<button onclick="deleteBook()">회원삭제</button>
			</div>
		</td>
	</tr>
	<tr>
		<th></th>
		<th>ID</th>
		<th>회원명</th>
		<th>관리자여부</th>
		<th>상태</th>
		<th>등급</th>
	</tr>
	
	<c:forEach items = "${mem}" var = "mem" step="1">
	 <c:if test="${empty mem}">
		<tr>
			<td colspan = "7" align = "center"> 등록된 게시물이 없습니다. </td>
		</tr>
	</c:if>
	
	<c:if test="${not empty mem}">
	
		<tr>
		<td class = "center">
				 <!--  삭제용 체크 박스 -->
			<input type = "checkbox" name = "delNo" value = "${mem.grade}"></input>
			</td>
			<td>
			<a href="../book/view.book?no=${mem.id}" class ="abook">${mem.id}</a>
			</td>
			<td>${mem.name}</td>
			<td>${mem.adminyn }</td>	
			<td>${mem.status }</td>
			<td>${mem.grade }</td>
		</tr>
	</c:if>
	</c:forEach>
	</table>

	<!--  페이징처리 -->
<table  width ="90%" class ="tables2">
	<tr>
		<td align ="center">
		<%@include file ="PageNavM.jsp"%>
		</td>
	</tr>
</table> 
</body>
</html>