<!doctype html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

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
	<form:form modelAttribute="attendanceOnHolidayRecordDetailInfo">
		<div style="margin-left:50px;margin-right:50px;margin-top:50px;border-style:solid;width:550px;">
			<div style="padding:2px;">
				<font size="5"><b>休日出勤管理詳細</b></font>
			</div>
			<table style="margin-left:100px;margin-top:30px;">
				<tr>
					<td style="width:150px">日付</td>
					<td>${attendanceOnHolidayRecordDetailInfo.date}</td>
				</tr>
				<tr>
					<td>勤務区分</td>
					<td>${attendanceOnHolidayRecordDetailInfo.workKbnName}</td>
				</tr>
				<tr>
					<td>勤務時間</td>
					<td>${attendanceOnHolidayRecordDetailInfo.workStartTime} ～　${attendanceOnHolidayRecordDetailInfo.workEndTime}</td>
				</tr>
				<c:if test="${not empty attendanceOnHolidayRecordDetailInfo.exchangeDate}">
					<tr>
						<td>振替日</td>
						<td>${attendanceOnHolidayRecordDetailInfo.exchangeDate}</td>
					</tr>
				</c:if>
				<c:if test="${not empty attendanceOnHolidayRecordDetailInfo.turnedHolidayEndDate}">
					<tr>
						<td>代休期限</td>
						<td>${attendanceOnHolidayRecordDetailInfo.turnedHolidayEndDate}</td>
					</tr>
				</c:if>
				<c:if test="${not empty attendanceOnHolidayRecordDetailInfo.turnedHolidayDate}">
					<tr>
						<td>代休日</td>
						<td>${attendanceOnHolidayRecordDetailInfo.turnedHolidayDate}</td>
					</tr>
				</c:if>
				<c:if test="${not empty attendanceOnHolidayRecordDetailInfo.overWorkTurnedReqDate}">
					<tr>
						<td>超勤振替申請日</td>
						<td>${attendanceOnHolidayRecordDetailInfo.overWorkTurnedReqDate}</td>
					</tr>
				</c:if>
				<tr>
					<td>プロジェクト名</td>
					<td>${attendanceOnHolidayRecordDetailInfo.projectName}</td>
				</tr>
				<tr>
					<td>業務内容</td>
					<td>${attendanceOnHolidayRecordDetailInfo.workDetail}</td>
				</tr>
			</table>
			<table style="margin-left:100px;height:80px">
				<tr>
					<td style="width:240px">
						<c:if test="${attendanceOnHolidayRecordDetailInfo.overWorkFlg}">
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
