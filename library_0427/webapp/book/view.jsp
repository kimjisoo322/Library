<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<script type="text/javascript">
	function setAction(action){
		viewForm.action=action;
	}
</script>
<style>
	body{
		background-color: rgb(255, 245, 232);
	}
	#tables{
		border-collapse: collapse;
		padding: 10px;
		width: 100%;
		height : 150px;
	}
	#tables td{
		background-color: rgb(255, 245, 232) ; 
		padding: 20px;
		text-align: center;
	}
	.bookname{
		text-align: center;
	}
	td {
	  position: relative;
	  width: 40px; /* 테이블 셀의 너비 */
	  height: 40px; /* 테이블 셀의 높이 */
	}
	
	td img {
	  position: absolute;
	  top: 0;
	  left: 0;
	  width: 100%;
	  height: 100%;
	}

	.btn1{
		border-color : rgb(253, 171, 185);
		background: rgb(253, 171, 185);
		color : white;
		width: 100px;
		height: 30px;
		border-radius: 20px;
	}
	.btn2{
		border-color : rgb(161, 198, 228);
		background: rgb(161, 198, 228);
		color : white;
		width: 100px;
		height: 30px;
		border-radius: 20px;
	}
	.button-container {
  		text-align: center;
 		 margin-top: 20px; /* 필요한 여백 조정 */
	}
	
</style>
<title>파일 첨부형 게시판</title>
</head>
<body>
<div class= "bookname">
<h3> 선택하신 도서는 📚 ${ book.title } 입니다 </h3> 
</div>
<form name = "viewForm" method = "post">

<c:set var="book" value="${requestScope.book }"></c:set>
 <input type="text" name ='no' value ="${book.no}" hidden ></input>
 <input type="text" name ='rentno' value ="${book.rentno}" hidden></input>
<%--  ★세션ID  : ${sessionScope.userId }
 ★대여ID  : ${book.rentid } --%>

<c:choose>
 <c:when test="${empty book.rentid }">
 	<h4> 🌱 대여 가능한 도서  </h4>
 </c:when>
 <c:when test="${not empty book.rentid }">
 	 <h4> 🌱 대여중 🌱 고객ID : ${book.rentid }</h4>
 </c:when>
</c:choose>


<table border="1" width="90%" id = "tables">
    <colgroup>
        <col width="30%"/> 
        <col width="15%"/> <col width="20%"/>
        <col width="15%"/> <col width="20%"/>
    </colgroup>
    
    <!-- 게시글 정보 -->
<tr>
	<td rowspan="3">
  	<img src="${pageContext.request.contextPath}/IMGES/bookImg/${book.sfile}" alt="Book Image" width="400" height="200">
	</td>
    <td>책번호</td>
    <td>${ book.no }</td>
    <td>제목</td> 
    <td>${ book.title }</td>
</tr>
<tr>
    <td>저자</td> 
    <td>${ book.author }</td>
    <td>출판사</td> 
    <td>${ book.publisher }</td>
</tr>
<tr>
    <td>대여여부</td> 
    <td>${ book.rentyn }</td>
     <td>조회수</td> 
    <td>${ book.visitcount }</td> 
</tr>

<tr>
    <td >첨부파일</td>
    <td colspan = "4">
        <c:if test="${ not empty book.ofile }">
            ${ book.ofile }
            <a href="../download.book?ofile=${ book.ofile }&sfile=${ book.sfile }" >
                [다운로드]
            </a>
        </c:if>
    </td>
</tr>
 <tr>
        <td>대여</td> 
        	<c:choose>
        		<c:when test="${empty book.rentno}">
	        		<td colspan="4"><button onclick="setAction('./rent.book')" class="btn1">대여하기</button></td>
	        				
        		</c:when>
        		<c:when test="${book.rentid eq sessionScope.userId }"> 
        			<td><button onclick="setAction('./return.book')" class="btn1">반납하기</button></td>
        			<td>대여기간</td> <td>${ book.startdate } ~ ${ book.enddate }</td>
        		</c:when>
        		<c:otherwise>
        			<td>대여중</td>
        			<td>대여기간</td> <td>${ book.startdate } ~ ${ book.enddate }</td>
        		</c:otherwise>
        	</c:choose>
    </tr>
  <!--   <tr>
    	<td colspan="5" align="center">
    	   <div>
      <button type="button" onclick="location.href='../book/List.book';" class ="btn3">
                이전
            </button>
    </div>
    	</td>
    
    </tr> -->
 
<%--         <td colspan="5" align="center">
            <button type="button" onclick="location.href='./edit.book?no=${dto.no}';" class ="btn1">
                수정하기
            </button>
            <button type="button" onclick="location.href='./delete.book?delNo=${ dto.no }';" class ="btn2">
            	삭제하기
            </button>
             <button type="button" onclick="location.href='../book/List.book';" class ="btn3">
                이전
            </button>
           	<c:if test=""></c:if>
        </td> --%>
 

</table>

	<div class="button-container">
 	 <button type="button" onclick="location.href='../book/List.book';" class="btn2">
    이전
  	</button>
	</div>
</form>
</body>
</html>
