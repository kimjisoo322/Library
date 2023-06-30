<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script>
	
	let message = '${message}';
	if(message != null && '' != message){
		alert(message);
	}

    function validateForm(form) {  // 필수 항목 입력 확인

        if (form.title.value == "") {
            alert("제목을 입력하세요.");
            form.title.focus();
            return false;
        }
        if (form.author.value == "") {
            alert("저자명을 입력하세요.");
            form.author.focus();
            return false;
        }
        if (form.publisher.value == "") {
            alert("출판사명을 입력하세요.");
            form.publisher.focus();
            return false;
        }
    }
</script>

</head>
<body>
<%@ include file = "../common/Header.jsp" %>
<h2>도서등록</h2>


<form name="wirteFrm" method="post" accept-charset="UTF-8" enctype= "multipart/form-data"
      action="../book/insert.book" onsubmit="return validateForm(this);">
      
<table border="1" width="90%">

    <tr>
        <td>제목</td>
        <td>
            <input type="text" name="title" style="width:150px;"value="${param.title}"/>
        </td>
    </tr>
    <tr>
        <td>저자</td>
        <td>
            <input type="text" name="author" style="width:150px;" value="${param.author}"/>
        </td>
    </tr>
        <tr>
        <td>출판사</td>
        <td>
            <input type="text" name="publisher" style="width:150px;" value="${param.publisher}"/>
        </td>
    </tr>
         <tr>
        <td>책 이미지</td>
        <td>
            <input type="file" name="bookImg" style="width:150px;"/>
        </td>
    </tr>
    
	<tr>
        <td colspan="2" align="center">
        	<!-- 글쓰기 버튼 클릭 하면 글등록 -->
            <button type="submit">도서 등록</button>
            <button type="reset">RESET</button>
            <button type="button" onclick="location.href='../book/List.book';">
                이전
            </button>
        </td>
    </tr>
</table>    
</form>
</body>
</html>