<%@page import="membership.MemberDTO"%>
<%@page import="membership.MemberDAO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%
//로그인 폼으로부터 받은 아이디와 패스워드
String userId = request.getParameter("user_id");
String userPwd = request.getParameter("user_pw");

//web.xml에서 가져온 데이터베이스 연결 정보
String oracleDriver = application.getInitParameter("OracleDriver");
String oracleURL = application.getInitParameter("OracleURL");
String oracleId= application.getInitParameter("OracleId");
String oraclePwd = application.getInitParameter("OraclePwd");

//jdbc 연결(dto, dao 셋팅하고 작업 진행)
MemberDAO dao = new MemberDAO(oracleDriver, oracleURL, oracleId, oraclePwd); //jdbc 연결
MemberDTO dto = dao.getMemberDTO(userId, userPwd); //form으로 받은 값을 getMemberDTO 매개값으로 전달
dao.close(); //jdbc 연결 해제
//결론 : id, pw 를 넣어 찾은 값을 dto에 가지고 있도록 함.

//로그인 성공 여부에 따른 처리
if(dto.getId() != null) {
	//로그인 성공
	session.setAttribute("UserId", dto.getId()); //세션영역에 dto id값을 넣는다.
	session.setAttribute("UserName", dto.getName()); //세션영역에 dto name값을 넣는다.
	response.sendRedirect("LoginForm.jsp"); //응답을 LoginForm으로 돌아간다.
} else {
	//로그인 실패
	request.setAttribute("LoginErrMsg", "로그인 오류입니다."); //요청영역에 LoginErrMsg를 만듬
	request.getRequestDispatcher("LoginForm.jsp").forward(request, response); //LoginForm으로 request, response를 갖고 돌아간다.
}

%>    



<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>LoginProcess.jsp</title>
</head>
<body> <!-- jdbc를 활용하여 MemberDTO를 가져와 세션에 저장 -->0

</body>
</html>