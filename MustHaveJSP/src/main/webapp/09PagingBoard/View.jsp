<%@page import="model1.BoardDTO"%>
<%@page import="model1.BoardDAO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%
String num = request.getParameter("num"); 	//View.jsp?num=번호 일련번호 받기

BoardDAO dao = new BoardDAO(application); 	//DAO 생성
dao.updateVisitCount(num); 					//조회수 증가 (메서드 활용하여 db에 조회수 값 증가)
BoardDTO dto = dao.selectView(num); 		//게시물 가져오기(번호를 넣어서 객체를 반환)
dao.close(); //DB연결 해제
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>View.jsp</title>
<script>
function deletePost() {
	var confirmed = confirm("정말로 삭제하겠습니까?");
	if(confirmed) {
		var form = document.writeFrm; //이름(name)이 "writeFrm"인 폼 선택
		form.method = "post"; //전송 방식
		form.action = "DeleteProcess.jsp"; //전송 경로
		form.submit(); //폼값 전송
	}
}
</script>
</head>
<body>
<jsp:include page="../Common/Link.jsp" />
<h2>회원제 게시판 - 상세보기(View)</h2>
<form name="writeFrm">
	<input type="hidden" name="num" value="<%=num %>" /> <!-- 공통 링크 -->
	
	<table border="1" width="90%">
		<tr>
			<td>번호</td>
			<td><%=dto.getNum() %></td>
			 <td>작성자</td>
            <td><%= dto.getName() %></td>
		</tr>
		 <tr>
            <td>작성일</td>
            <td><%= dto.getPostdate() %></td>
            <td>조회수</td>
            <td><%= dto.getVisitcount() %></td>
        </tr>
         <tr>
            <td>제목</td>
            <td colspan="3"><%= dto.getTitle() %></td>
        </tr>
		 <tr>
            <td>내용</td>
            <td colspan="3" height="100">
                <%= dto.getContents().replace("\r\n", "<br/>") %></td> 
        </tr>
        <tr>
            <td colspan="4" align="center">
            <%
            if (session.getAttribute("UserId") != null
                && session.getAttribute("UserId").toString().equals(dto.getId())) {
            %>
                <button type="button"
                        onclick="location.href='Edit.jsp?num=<%= dto.getNum() %>';">
                    수정하기</button>
                <button type="button" onclick="deletePost();">삭제하기</button> 
            <%
            }
            %>
                <button type="button" onclick="location.href='List.jsp';">
                    목록 보기
                </button>
            </td>
        </tr>
	</table>

</form>





</body>
</html>