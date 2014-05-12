<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ page import="org.springframework.security.web.WebAttributes" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPEhtmlPUBLIC"-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<title>登録</title>
</head>
<body>
<%@ include file="includes/header.jsp"%>
	<span style="color:red">${errorMessages} ${sessionScope.SPRING_SECURITY_LAST_EXCEPTION.message }</span>
	<form action="j_spring_security_check" method="POST">
		<table>
			<tr>
				<td>User</td>
				<td><input type='text'name='j_username' id="Username"></td>
			</tr>
			<tr>
				<td>PassWord</td>
				<td><input type='password'name='j_password'></td>
			</tr>
			<tr>
				<td><input type="reset" name="reset"></td>
				<td><input type='submit'name='login'></td>
			</tr>
		</table>
		<br><br><br><br><br><br><br>
		※ 一般ユーザ「ID:user01」、「Pwd：user01」、「権限：ROLE_USER」<br>
		※ 管理者「ID:admin」、「Pwd：admin」、「権限：ROLE_USER,ROLE_ADMIN」<br>
		&nbsp;&nbsp;&nbsp;「Login画面」は権限のリミットなし。<br>
		&nbsp;&nbsp;&nbsp;「Menu画面」の権限リミット：ROLE_USER<br>
		&nbsp;&nbsp;&nbsp;「案件一覧画面」の権限リミット：ROLE_ADMIN<br>
	</form>
</body>
</html>