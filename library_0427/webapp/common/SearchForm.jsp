<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<style>
	.formtable{
		border-collapse: collapse;
		border: 1px solid white;
		background-color:  rgb(255, 245, 232); 
		width :100%;
	}
	.formtd{
		padding: 10px;
	}

</style>
<title>Insert title here</title>
</head>
<body>
<!--  검색폼 (저자, 제목명)  -->

<form name ="searchForm" method ="get" >

	<input type="text" name ='pageNo' value ="${param.pageNo }" hidden></input>
	<input type="text" name ='delNo' value ="${param.delNo }" hidden></input>
	
	<table border ='1' width ="100%" class = "formtable">
		<tr>
		<td align = "center" class = "formtd">
			<select name ="searchField">
				<option value = "title"${param.searchField eq "title" ? "selected" : ""}>도서명</option>
				<option value = "author"${param.searchField eq "author" ? "selected" : ""}>저자명</option>
			</select>
			
			<input type="text" name = "searchWord" value = "${param.searchWord}" ></input>
			<input type ="submit" value ="검색하기">
		</td>
		</tr>
	</table>
</form> 	

</body>
</html>