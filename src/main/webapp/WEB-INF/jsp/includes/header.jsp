<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<div id="userBar" style="height:30px; margin-top: 10px; background-color:#ddffdd;padding-right: 20px;" align="right">
		<table>
			<tr>
				<td>ようこそ：</td>
				<td>${ userName }</td>
				<td><input id="loginLink" type="button" onClick="window.location.href('/login')" value="ログイン"></td>
				<td><input id="logoutLink" type="button" onClick="window.location.href('/logout')" value="ログアウト"></td>
			</tr>
		</table>
</div>
