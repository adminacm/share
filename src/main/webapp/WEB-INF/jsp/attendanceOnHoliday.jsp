<!doctype html>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<html>
<head>
<meta charset="utf-8">
<title>休日勤務入力画面</title>
<meta content="IE=edge,chrome=1" http-equiv="X-UA-Compatible">
<meta name="viewport" content="width=device-width, initial-scale=1.0">

<script type="text/javascript">
/* アクション提出 */
function submitAction(action) {

	document.forms[0].action = action;
	document.forms[0].submit();
}

</script>
</head>

<body>
	<form:form modelAttribute="attendanceInputForm" commandName="attendanceOnHolidayInfo">
	<%@ include file="includes/header.jsp"%>
		<div style="margin-left:50px;margin-right:50px;margin-top:50px;border-style:solid;width:500px;">
			<div style="padding:2px;">
				<b>休日勤務入力</b>
			</div><br>
			<spring:bind path="attendanceOnHolidayInfo.*" >
				<font color="red">
					<c:forEach var="item" items="${status.errorMessages}">
						${item}<br>
					</c:forEach>
				</font><br>
			</spring:bind>
			<div style="margin-top: 10px;margin-bottom:10px;" >
				<table style="margin:auto; width:300px; border: 1px solid #333;">
					<tr>
						<td width="120px">日付</td>
						<td width="180px">${attendanceOnHolidayInfo.strAtendanceDateShow}</td>
					</tr>
					<tr>
						<td width="120px">勤務区分</td>
						<td width="180px">
							<form:select path="selectedAtendanceDayKbn" style="width:80%;" >
								<form:options items="${attendanceOnHolidayInfo.atendanceDayKbnList}" itemValue="value" itemLabel="name"/>
							</form:select>
						</td>
					</tr>
					<tr>
						<td width="120px">勤務時間</td>
						<td width="180px">
							<spring:bind path="attendanceOnHolidayInfo.strAtendanceTimeStat">
								<input type="text" style="width: 40px;" name="${ status.expression }" value="${status.value }">
							</spring:bind>
							～
							<spring:bind path="attendanceOnHolidayInfo.strAtendanceTimeEnd">
								<input type="text" style="width: 40px;" name="${ status.expression }" value="${status.value }">
							</spring:bind>
						</td>
					</tr>
					<tr>
						<td width="120px">振替日</td>
						<td width="180px">
							<spring:bind path="attendanceOnHolidayInfo.strHurikaeDate">
								<input type="text" style="width: 120px;" name="${ status.expression }" value="${status.value }">
							</spring:bind>
						</td>
					</tr>
					<tr>
						<td width="120px">プロジェクト名</td>
						<td width="180px">
							<form:select path="selectedProjCd" style="width:80%;" >
								<form:option value=""></form:option>
								<form:options items="${attendanceOnHolidayInfo.projCdList}" itemValue="value" itemLabel="name"/>
							</form:select>
						</td>
					</tr>
					<tr>
						<td width="120px">業務内容</td>
						<td width="180px">
							<spring:bind path="attendanceOnHolidayInfo.strCommont">
								<textarea rows="2" cols="" style="width: 140px;" name="${ status.expression }"><c:out value="${status.value }"/></textarea>
							</spring:bind>
						</td>
					</tr>
				</table>
			</div>
			<div style="margin-top:20px;">
				<table style="margin-top: 40px;margin-bottom:10px;width: 300px;margin: auto">
					<tr>
						<td align="center" width="100px"><input type="button" value="保存" onclick="submitAction('/attendanceOnHoliday/save');" /></td>
						<td align="center" width="100px"><input type="button" value="削除" onclick="submitAction('/attendanceOnHoliday/delete');" /></td>
						<td align="center" width="100px"><input type="button" value="戻る" onclick="submitAction('/attendanceOnHoliday/back');" /></td>
					</tr>
				</table>
			</div>
		</div>
	</form:form>
</body>
</html>
