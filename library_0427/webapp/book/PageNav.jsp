<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="com.library.vo.Criteria"%>
<%@page import="com.library.vo.PageDto"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<style>
	/* .btn:hover{
		background-color: yellow;
		
	} */
	.div{
		background-color:  rgb(217, 254, 255);
		padding: 10px;
		
	}
	.btn {
		box-shadow: 0 0 2px rgba(0, 0, 0, 0.3);
		transition: box-shadow 0.3s ease;
		width: 45px;
		border-radius: 5px;
		border-color:  rgb(217, 254, 255);
		
	}

	.btn:hover {
		background-color:  rgb(217, 254, 255);
	}
	
</style>
<title>Insert title here</title>

<script>
	function go(page){
		document.searchForm.action="./List.book";
		document.searchForm.pageNo.value=page;
		document.searchForm.submit();
	}
</script>

</head>
<body>
<div class ="div">
<c:set var="pageDto" value="${pageDto }"/>

<!-- 이전버튼 -->
<c:if test="${pageDto.prev }">
	<input type='button' value='이전' onclick='go(${pageDto.startNo-1})' class="btn">
</c:if>

<!-- 페이지번호 출력 -->
<c:forEach begin="${pageDto.startNo }" end="${pageDto.endNo }" var="i">
	<input type='button' value='${i }' onclick='go(${i})' class="btn" >
</c:forEach>

<!-- 다음버튼 -->
<c:if test="${pageDto.next }">
	<input type='button' value='다음' onclick='go(${pageDto.endNo+1})' class="btn">		
</c:if>
</div>
</body>
</html>




















