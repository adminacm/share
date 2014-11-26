<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<div id="userBar" style="height:30px; margin-top: 10px; background-color:#eeffdd;padding-right: 20px;" align="right">
		<table>
			<tr>
				<c:if test="${ not empty userName }">
					<td>ようこそ：</td>
					<td><c:out value="${ userName }"/></td>
					<td><a href="/login">メニュー</a></td>
				</c:if>
				<c:if test="${ empty userName }">
					<td><a href="/login">ログイン</a></td>
				</c:if>
				<c:if test="${ !empty userName }">
					<td><a href="/logout">ログアウト</a></td>
				</c:if>
			</tr>
		</table>
</div>
