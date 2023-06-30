<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<style>
	*{
		margin: 0 auto;
	}
	body{
		background-color: rgb(255, 245, 232);
		padding-left: 30px;
		padding-right: 30px;
		
	}
	.button-container {
		padding-bottom : 10px;
    	text-align: right;
    	width: 100%;
	}
	.abook{
		padding-left: 10px;
		text-align: center;
		color: rgb(148, 89, 216);
		font-weight: bold;
	}
	.btn1{
		border-color : white;
		background: rgb(253, 171, 185);
		color : white;
		width: 150px;
	}
	.btn2{
		border-color : white;
		background: rgb(161, 198, 228);
		color : white;
		width: 150px;
	}
	.tables{
		
		width : 100%;
		height : 200px;
		border-radius: 10px;
		border-collapse: collapse ;
		border-width: 1px;
	}

	.tables th{
		background-color:  rgb(217, 254, 255); 
		padding: 10px;
	}
	.tables td{
		background-color: rgb(255, 245, 232);
	}
	.tables2{
		width: 100%;
		border: 2px solid rgb(217, 254, 255);
		height: 50px;
		border-collapse: collapse;
	}
</style>

<title>Insert title here</title>

<script>
	
	let message = '${message}';
	if(message != null && '' != message){
		alert(message);
	}

	function deleteBook(){
		// 체크박스가 선택된 요소의 value 값을 ,로 연결
		delNoList = document.querySelectorAll("[name=delNo]:checked");
		
		let delNo = "";
		delNoList.forEach((e)=>{
			delNo += e.value +',';
		});
		delNo = delNo.substr(0, delNo.length-1);
		
		// 삭제 요청 (delete from book where no in (16,17,18))
		searchForm.action = "../book/delete.book";
		searchForm.delNo.value = delNo;
		searchForm.submit();
	}
	
	
</script>
</head>
<body>
<%@ include file = "../common/Header.jsp" %>

<c:set var = "totalCnt" value = "${totalCnt}"></c:set>
 	
 	
 <div>
  총 건수 :  ${totalCnt} 건 🎵
 </div>	
<head>
<c:set var="fileList" value="${list}"></c:set>

<!--  도서목록  -->
<c:if test="${sessionScope.adminYN  eq 'Y'}" var = "res">
<div class="button-container">
			<!--  어드민 계정인 경우 (등록, 삭제 버튼 출력) -->
			<button onclick="location.href='../book/insert.jsp';" class ="btn1">도서등록</button>
			<button onclick="deleteBook()" class ="btn2">도서삭제</button>
			</div>
</c:if>
<c:if test="${not res}">
	<tr>
		<td colspan ="7" class ="right">
		 <div class="button-container">
			<!--  어드민 계정인 경우 (등록, 삭제 버튼 출력)-->
			
			<button onclick="setAction('./rent.book')" class ="btn1">도서 대여 바로가기 </button>
			<button onclick="ReturnBook()" class ="btn2">도서 반납 바로가기</button>
			</div>
		</td>
	</tr>
	</c:if>
<%@ include file = "../common/SearchForm.jsp" %>
<table width = "90%" class= "tables">

	<tr>
		<th></th>
		<th>책번호</th>
		<th>제목</th>
		<th>저자</th>
		<th>출판사</th>
		<th>대여여부</th>
		<th>첨부</th>

	</tr>
	
	<c:forEach items = "${list}" var = "book" step="1">
	 <c:if test="${empty list}">
		<tr>
			<td colspan = "6" align = "center"> 등록된 게시물이 없습니다. </td>
		</tr>
	</c:if>
	
	<c:if test="${not empty list}">
	
		<tr>
			<td class = "center">
				 <!--  삭제용 체크 박스 -->
			<input type = "checkbox" name = "delNo" value = "${book.no}"></input>
			</td>
			<td>
			<a href="../book/view.book?no=${book.no}" class ="abook">${book.no}</a>
			</td>
			<td>${book.title}</td>
			<td>${book.author }</td>	
			<td>${book.publisher }</td>
			<td>${book.rentyn }</td>
			<td>다운로드</td>
		</tr>
	</c:if>
	</c:forEach>
	
	
	</table>

<!--  페이징처리 -->
<table  width ="90%" class ="tables2">
	<tr>
		<td align ="center">
		<%@include file ="PageNav.jsp"%>
			</td>
	</tr>
</table> 
</head>
</body>
</html>