<!doctype html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>
<head>
<meta charset="utf-8">
<title>超勤振替申請承認</title>

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
	<form:form modelAttribute="holidayForOvertimeApprovalForm">
	<%@ include file="includes/header.jsp"%>
		<div style="margin-left:50px;margin-right:50px;margin-top:50px;border-style:solid;width:700px;">
			<div style="padding:2px;">
				<font size="5"><b>超勤振替申請承認</b></font>
			</div>
			<div style="margin-top: 20px;margin-bottom:10px;background:#ffddff">
				<table style="width:800px;margin-left:40px;margin-right:40px;">
					<tr>
						<td style="border:1px solid #333333;width:60px" align="center">${holidayForOvertimeApprovalForm.proStatus}</td>
						<td style="width:240px" align="center">
							<input type="button" style="width:120px; height:25px" value="承認" onclick="submitAction('/holidayForOvertimeApproval/approval');" />
						</td>
						<td style="width:185px">
							<input type="button" style="width:120px; height:25px" value="差戻" onclick="submitAction('/holidayForOvertimeApproval/remand');" />
						</td>
						<td>
							<input type="button" style="width:120px; height:25px" value="戻る" onclick="submitAction('/holidayForOvertimeApproval/back');" />
						</td>
					</tr>
				</table>
			</div>
			<div>
				<table style="margin-left:70px;padding-bottom:20px">
					<tr>
						<td style="width:100px">日付</td>
						<td>${holidayForOvertimeApprovalForm.date}</td>
					</tr>
					<tr>
						<td>勤務区分</td>
						<td>${holidayForOvertimeApprovalForm.workKbnName}</td>
					</tr>
					<tr>
						<td>勤務時間</td>
						<td>${holidayForOvertimeApprovalForm.workStartTime} ～　${holidayForOvertimeApprovalForm.workEndTime}</td>
					</tr>
					<tr>
						<td>代休期限</td>
						<td>${holidayForOvertimeApprovalForm.turnedHolidayEndDate}</td>
					</tr>
					<tr>
						<td>PJ</td>
						<td>${holidayForOvertimeApprovalForm.projectName}</td>
					</tr>
					<tr>
						<td>業務内容</td>
						<td>${holidayForOvertimeApprovalForm.workDetail}</td>
					</tr>
				</table>
			</div>
		</div>	
		<input type="hidden" value="${monthlyReportApprovalForm.backUrl}"/>
	</form:form>
</body>
</html>
