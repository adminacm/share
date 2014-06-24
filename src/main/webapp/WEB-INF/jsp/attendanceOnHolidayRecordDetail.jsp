<!doctype html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="argo.cost.common.constant.*"%>
<html>
<head>
<meta charset="utf-8">
<title>休日出勤管理詳細</title>

<meta content="IE=edge,chrome=1" http-equiv="X-UA-Compatible">
<meta name="viewport" content="width=device-width, initial-scale=1.0">

<link href="../css/common.css" rel="stylesheet" type="text/css">

<script type="text/javascript">
/* アクション提出 */
function submitAction(action) {

	document.forms[0].action = action;
	document.forms[0].submit();
}

</script>
</head>
<body>
	<form:form modelAttribute="attendanceOnHolidayRecordDetailForm">
	<%@ include file="includes/header.jsp"%>
		<div style="margin-left:50px;margin-right:50px;margin-top:50px;border-style:solid;width:550px;">
			<div style="padding:2px;">
				<font size="5"><b>休日出勤管理詳細</b></font>
			</div>
			<table style="margin-left:100px;margin-top:30px;">
				<tr>
					<td>社員番号</td>
					<td>${attendanceOnHolidayRecordForm.taishoUserId}</td>
				</tr>
				<tr>
					<td>氏名</td>
					<td>${attendanceOnHolidayRecordForm.taishoUserName}</td>
				</tr>
				<tr>
					<td style="width:150px">日付</td>
					<td>${attendanceOnHolidayRecordDetailForm.holidayWorkDate}</td>
				</tr>
				<tr>
					<td>勤務区分</td>
					<td>${attendanceOnHolidayRecordDetailForm.workKbnName}</td>
				</tr>
				<tr>
					<td>勤務時間</td>
					<td>${attendanceOnHolidayRecordDetailForm.workStartTime}&nbsp;&nbsp;～&nbsp;${attendanceOnHolidayRecordDetailForm.workEndTime}</td>
				</tr>
				<c:if test="${not empty attendanceOnHolidayRecordDetailForm.exchangeDate}">
					<tr>
						<td>振替日</td>
						<td>${attendanceOnHolidayRecordDetailForm.exchangeDate}</td>
					</tr>
				</c:if>
				<!-- 休日勤務 -->
				<c:if test="${attendanceOnHolidayRecordDetailForm.workKbn == '02'}">
					<c:if test="${not empty attendanceOnHolidayRecordDetailForm.turnedHolidayEndDate}">
						<tr>
							<td>代休期限</td>
							<td>${attendanceOnHolidayRecordDetailForm.turnedHolidayEndDate}</td>
						</tr>
					</c:if>
					<c:if test="${not empty attendanceOnHolidayRecordDetailForm.turnedHolidayDate}">
						<tr>
							<td>代休日</td>
							<td>${attendanceOnHolidayRecordDetailForm.turnedHolidayDate}</td>
						</tr>
					</c:if>
					<c:if test="${not empty attendanceOnHolidayRecordDetailForm.overWorkTurnedReqDate}">
						<tr>
							<td>超勤振替申請日</td>
							<td>${attendanceOnHolidayRecordDetailForm.overWorkTurnedReqDate}</td>
						</tr>
					</c:if>
				</c:if>
				<tr>
					<td>プロジェクト名</td>
					<td>${attendanceOnHolidayRecordDetailForm.projectName}</td>
				</tr>
				<tr>
					<td>業務内容</td>
					<td>${attendanceOnHolidayRecordDetailForm.workDetail}</td>
				</tr>
			</table>
			<table style="margin-left:100px;height:80px">
				<tr>
					<td style="width:240px">
						<c:if test="${attendanceOnHolidayRecordDetailForm.overWorkFlg}">
							<input type="button" style="width:150px" value="超勤に振替える" onclick="submitAction('/attendanceOnHolidayRecordDetail/overWorkExchange');" />
						</c:if>
					</td>
					<td style="align:right">
						<input type="button" style="width:70px" value="戻る" onclick="submitAction('/attendanceOnHolidayRecordDetail/back');" />
					</td>
				</tr>
			</table>
		</div>	
	</form:form>
</body>
</html>
