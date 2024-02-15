<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="./IsLoggedIn.jsp" %> <!-- 로그인 확인에 대한 코드 삽입 (주의사항 : html5태그 중복 제거) -->
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
	<script type="text/javascript">
		function validateForm(form) { 		/* validateForm을 호출하면 form의 매개값을 받는다. */
			if(form.title.value == "") { 	/* 폼안에 제목의 값을 확인하여 빈칸이면 */
				alert("제목을 입력하세요."); 		/* 경고창과 메세지를 출력 */
				form.title.focus(); 		/* 제목박스에 커서를 이동한다. */
				return false; 				/* 결과로 false값을 리턴한다. */
			}
			if(form.content.value == "") { 	/* 폼안에 내용의 값을 확인하여 빈칸이면 */
				alert("내용을 입력하세요.") 		/* 경고창과 메세지를 출력 */
				form.content.focus(); 		/* 제목박스에 커서를 이동한다. */
				return false; 				/* 결과로 false값을 리턴한다. */
			}
		} //폼 내용 검증
	</script>
<title>Write.jsp</title>
</head>
<body>
	<jsp:include page="../Common/Link.jsp"/>
		<h2>회원제 게시판 - 글쓰기(Write)</h2>
		<form name="writeFrm" method="post" action="WriteProcess.jsp"
			onsubmit="return validateForm(this);">
			<table border="1" width="90%">
				<tr>
					<td>제목</td>
					<td>
						<input type="text" name="title" style="width:90%;"/>
					</td>
				</tr>
				<tr>
					<td>내용</td>
					<td>
						<textarea name="content" style="width:90%; height:100px;"></textarea>
					</td>
				</tr>
				<tr>
					<td colspan="2" aligh="center">
						<button type="submit">작성완료</button>
						<button type="reset">다시입력</button>
						<button type="button" onclick="location.href='List.jsp';">목록 보기</button>
					</td>
				</tr>
			</table>
		</form>
</body>
</html>