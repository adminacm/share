<!doctype html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>
<head>
<meta charset="utf-8">
<title>月報承認</title>

<meta content="IE=edge,chrome=1" http-equiv="X-UA-Compatible">
<meta name="viewport" content="width=device-width, initial-scale=1.0">

<script type="text/javascript">
/* アクション提出 */
function submitAction(action) {

	document.forms[0].action = action;
	document.forms[0].submit();
}

</script>
<style type="text/css">

.table1 {
	width:800px;
	margin-top:10px;
	margin-left:40px;
	margin-bottom: 30px;
	white-space: nowrap;
	border-collapse:collapse;
}

.table1 th {
	white-space: nowrap;
	padding:2px;
	align:center;
	font-weight:normal;
	border: 1px solid #ccc;
}

.table1 td {
	white-space: normal;
	padding:2px;
	font-weight:normal;
	border: 1px solid #ccc;
}
</style>
</head>
<body>
	<form:form modelAttribute="monthlyReportApprovalForm">
	<%@ include file="includes/header.jsp"%>
		<div style="margin-left:50px;margin-right:50px;margin-top:50px;border-style:solid;width:900px;">
			<div style="padding:2px;">
				<b>月報承認</b>
			</div>
			<div style="margin-top: 20px;margin-bottom:10px;background:#ffddff">
				<table style="width:800px;margin-left:40px;">
					<tr>
						<td style="border:1px solid #333333;width:80px" align="center"><c:out value="${monthlyReportApprovalForm.proStatusName}"/></td>
						<!-- 提出の場合 -->
						<c:if test="${ monthlyReportApprovalForm.proStatus == '02' }">
							<td style="width:240px" align="right">
								<input type="button" style="width:120px; height:25px" value="承認" onclick="submitAction('/monthlyReportApproval/approval');" />
							</td>
							<td style="width:240px" align="right">
								<input type="button" style="width:120px; height:25px" value="差戻" onclick="submitAction('/monthlyReportApproval/remand');" />
							</td>
						</c:if>
						<!-- 提出以外の場合 -->
						<c:if test="${ monthlyReportApprovalForm.proStatus != '02' }">
							<td style="width:240px" align="right"></td>
							<td style="width:240px" align="right"></td>
						</c:if>
						<td style="width:240px" align="right">
							<input type="button" style="width:120px; height:25px" value="戻る" onclick="submitAction('/monthlyReportApproval/back');" />
						</td>
					</tr>
				</table>
			</div>
			<div style="margin-top: 20px;margin-bottom:10px;background:#ffdddd">
				<table style="width:800px;margin-left:40px;">
					<tr>
						<td style="width:120px" align="left"><c:out value="${ monthlyReportApprovalForm.reportMoth }"/></td>
						<td style="width:70px" align="left">社員番号</td>
						<td style="width:60px" align="left"><c:out value="${monthlyReportApprovalForm.taishoUserId}"/></td>
						<td style="width:60" align="left">氏名</td>
						<td style="width:490px" align="left"><c:out value="${monthlyReportApprovalForm.taishoUserName}"/></td>
					</tr>
				</table>
			</div>
			<div>
				<table class="table1" id="resultList">
					<thead>
						<tr>
							<th rowspan="2"colspan="2" width="50">日付</th>
							<th rowspan="2" width="100">区分</th>
							<th rowspan="2" width="50">ｼﾌﾄ</th>
							<th rowspan="2">出勤</th>
							<th rowspan="2">退勤</th>
							<th rowspan="2">休暇</th>
							<th rowspan="2">勤務<br>時間<br>数
							</th>
							<th colspan="6" style="height:25px;">超勤</th>
							<th rowspan="2">ﾛｹｰｼｮﾝ</th>
						</tr>
						<tr>
							<th>開始</th>
							<th>終了</th>
							<th>平増</th>
							<th>平常</th>
							<th>休日</th>
							<th>深夜</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="approvalInfo" items="${monthlyReportApprovalForm.monthlyReportApprovalList}">
							<tr>
								<c:if test="${not approvalInfo.totleFlg}">
									<td align="center"><c:out value="${approvalInfo.day}"/></td>
									<td align="center"><c:out value="${approvalInfo.week}"/></td>
									<td align="center"><c:out value="${approvalInfo.workKbnName}"/></td>
									<td align="center"><c:out value="${approvalInfo.shift}"/></td>
									<td align="center"><c:out value="${approvalInfo.workSTime}"/></td>
									<td align="center"><c:out value="${approvalInfo.workETime}"/></td>
									<td align="center"><c:out value="${approvalInfo.kyukaKbName}"/></td>
								</c:if>
								<c:if test="${approvalInfo.totleFlg}">
									<td colspan="7" style="border-bottom-width: 0px; border-left-width: 0px" align="right">計</td>
								</c:if>
								<td align="center"><c:out value="${approvalInfo.workHours}"/></td>
								<td align="center"><c:out value="${approvalInfo.choSTime}"/></td>
								<td align="center"><c:out value="${approvalInfo.choETime}"/></td>
								<td align="center"><c:out value="${approvalInfo.choWeekday}"/></td>
								<td align="center"><c:out value="${approvalInfo.choWeekdayNomal}"/></td>
								<td align="center"><c:out value="${approvalInfo.choHoliday}"/></td>
								<td align="center"><c:out value="${approvalInfo.mNHours}"/></td>
								<c:if test="${not approvalInfo.totleFlg}">
									<td align="center"><c:out value="${approvalInfo.locationName}"/></td>
								</c:if>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
			<c:if test="${ not empty monthlyReportApprovalForm.projectList}">
				<div style="margin-left:40px;">
					【PJ別作業時間集計】
				</div>
			</c:if>
			<div style="margin-left:80px;">
				<table style="width:400px">
					<c:forEach var="projWorkTimeCountInfo" items="${monthlyReportApprovalForm.projectList}">
						<c:if test="${not empty projWorkTimeCountInfo.projName}">
							<tr>
								<td style="width:200px" colspan="2">
									<c:out value="${projWorkTimeCountInfo.projName}"/>
								</td>
								<td>
									<c:out value="${projWorkTimeCountInfo.prpjectWorkTotalHours}"/>
								</td>
							</tr>
						</c:if>
						<c:if test="${not empty projWorkTimeCountInfo.workContentName}">
							<tr>
								<td width="30px" align="left">&nbsp;</td>
								<td width="170xp" align="left"><c:out value="${projWorkTimeCountInfo.workContentName}"/></td>
								<td align="left">
									<c:out value="${projWorkTimeCountInfo.workHoursNum}"/>
								</td>
							</tr>
						</c:if>
					</c:forEach>
				</table>
			</div>
		</div>
		<input type="hidden" value="${monthlyReportApprovalForm.backUrl}"/>
	</form:form>
</body>
</html>
