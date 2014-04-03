<!doctype html>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<html>
<head>
<meta charset="utf-8">
<title>休日勤務入力画面</title>
<meta content="IE=edge,chrome=1" http-equiv="X-UA-Compatible">
<meta name="viewport" content="width=device-width, initial-scale=1.0">

</head>

<body>
	<form:form modelAttribute="attendanceInputForm" commandName="attendanceOnHolidayInfo">
		<div style="margin-left:50px;margin-right:50px;margin-top:50px;border-style:solid;width:400px;">
			<div style="padding:2px;">
				<b>休日勤務入力</b>
			</div>
			<div style="margin-top: 10px;margin-bottom:10px;" >
				<table style="margin:auto; width:300px; border: 1px solid #333;">
					<tr>
						<td width="120px">日付</td>
						<td width="180px">${attendanceOnHolidayInfo.strAtendanceDate}</td>
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
								<input type="text" style="width: 40px;" name="strAtendanceTimeStat" value='<c:out value="${status.value }"/>'>
							</spring:bind>
							～
							<spring:bind path="attendanceOnHolidayInfo.strAtendanceTimeEnd">
								<input type="text" style="width: 40px;" name="strAtendanceTimeEnd" value='<c:out value="${status.value }"/>'>
							</spring:bind>
						</td>
					</tr>
					<spring:bind path="attendanceOnHolidayInfo.strAtendanceTimeStat">
						<c:if test="${ not empty status.errorMessage}">
							<tr>
								<td width="180px" colspan="2">
									<font color="red"><c:out value="${status.errorMessage}" /></font>
								</td>
							</tr>
						</c:if>
					</spring:bind>
					<spring:bind path="attendanceOnHolidayInfo.strAtendanceTimeEnd">
						<c:if test="${ not empty status.errorMessage}">
							<tr>
								<td width="180px" colspan="2">
									<font color="red"><c:out value="${status.errorMessage}" /></font>
								</td>
							</tr>
						</c:if>
					</spring:bind>
					<tr>
						<td width="120px">振替日</td>
						<td width="180px">
							<spring:bind path="attendanceOnHolidayInfo.strHurikaeDate">
								<input type="text" style="width: 120px;" name="strHurikaeDate" value='<c:out value="${status.value }"/>'>
								<font color="red"><c:out value="${status.errorMessage}" /></font>
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
						<td colspan="2">
							<spring:bind path="attendanceOnHolidayInfo.selectedProjCd">
								<font color="red"><c:out value="${status.errorMessage}" /></font>
							</spring:bind>
						</td>
					</tr>
					<tr>
						<td width="120px">業務内容</td>
						<td width="180px">
							<spring:bind path="attendanceOnHolidayInfo.strCommont">
								<textarea rows="2" cols="" style="width: 140px;" name="strCommont"><c:out value="${status.value }"/></textarea>
								<font color="red"><c:out value="${status.errorMessage}" /></font>
							</spring:bind>
						</td>
					</tr>
				</table>
			</div>
		</div>
	</form:form>
</body>
</html>
