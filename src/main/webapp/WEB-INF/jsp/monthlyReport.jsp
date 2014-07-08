<!doctype html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>
<head>
<meta charset="utf-8">
<title>月報</title>

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
	<form:form modelAttribute="monthlyReportForm">
	<%@ include file="includes/header.jsp"%>
		<div style="margin-left:50px;margin-right:50px;margin-top:50px;border-style:solid;width:900px;">
			<div style="padding:2px;">
				<b>月報</b>
			</div>
			<div style="margin-left: 10px;margin-top: 10px;">
			<c:forEach var="message" items="${monthlyReportForm.confirmMsgList }">
				<span style="color:red">${message }</span><br/>
			</c:forEach>
		</div>
			<div>
				<table style="width:800px;margin-left:40px;margin-right:40px;margin-bottom:30px;border:1px solid #ccc;">
					<tr>
						<td colspan="5" style="">検索条件</td>
					</tr>
					<tr>
						<td style="padding-left:30px;" width="60px" align="right">氏名</td>
						<td align="left">
							<form:select path="userCode" style="width:150%;border:2px solid #333333;" id="usCode">
								<form:options items="${monthlyReportForm.userList}" itemValue="id" itemLabel="userName"/>
							</form:select>
						</td>
						<td style="padding-left:520px;"><input type="button" value="表示切替" onclick="submitAction('/monthlyReport/search');"/></td>
					</tr>
				</table>
			</div>
			<div style="margin-top: 20px;margin-bottom:10px;background:#ffddff">
				<table style="width:800px;margin-left:40px;margin-right:40px;">
					<tr>
						<td style="border:1px solid #333333;" align="center" width="180px">${monthlyReportForm.proStatus}</td>
						<td align="right" width="130px"><input type="button" value="←" onclick="submitAction('/monthlyReport/lastMonth');" /></td>
						<td align="center" width="100px;">
							<form:label path="yearMonthHyoji" id="yearMonthHyoji">${monthlyReportForm.yearMonthHyoji}</form:label>
							<form:hidden path="yearMonth"/>
						</td>
						<td align="left"><input type="button" value="→" onclick="submitAction('/monthlyReport/nextMonth');" /></td>
						<!-- 提出状況：作成中 -->
						<c:if test="${monthlyReportForm.proStatus == '作成中'}">
							<td align="right"><input type="button" value="月報提出" onclick="submitAction('/monthlyReport/monthlyReportCommit');" /></td>
						</c:if>
					</tr>
				</table>
			</div>
			<div>
				<table class="table1" id="resultList" >
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
						<c:forEach var="monthlyReport" items="${monthlyReportForm.mRList}">
							<tr>
								<c:if test="${not monthlyReport.totleFlg}">
									<td align="center" width="25PX;">
										<a href="/attendanceInput/init?attendanceDate=${monthlyReport.date}">${monthlyReport.day}</a>
									</td>
									<td align="center" width="25PX;">${monthlyReport.week}</td>
									<td align="center">${monthlyReport.workKbnName}</td>
									<td align="center">${monthlyReport.shift}</td>
									<td align="center">${monthlyReport.workSTime}</td>
									<td align="center">${monthlyReport.workETime}</td>
									<td align="center">${monthlyReport.kyukaKbName}</td>
								</c:if>
								<c:if test="${monthlyReport.totleFlg}">
									<td colspan="7" style="border-bottom-width: 0px; border-left-width: 0px" align="right">計</td>
								</c:if>
								<td align="center">${monthlyReport.workHours}</td>
								<td align="center">${monthlyReport.choSTime}</td>
								<td align="center">${monthlyReport.choETime}</td>
								<td align="center">${monthlyReport.choWeekday}</td>
								<td align="center">${monthlyReport.choWeekdayNomal}</td>
								<td align="center">${monthlyReport.choHoliday}</td>
								<td align="center">${monthlyReport.mNHours}</td>
								<c:if test="${not monthlyReport.totleFlg}">
									<td align="center">${monthlyReport.locationName}</td>
								</c:if>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
			<c:if test="${ not empty monthlyReportForm.projWorkTimeCountVOList}">
				<div style="margin-left:40px;">
					【PJ別作業時間集計】
				</div>
			</c:if>
			<div style="margin-left:80px;">
				<table style="width:400px">
					<c:forEach var="projWorkTimeCountInfo" items="${monthlyReportForm.projWorkTimeCountVOList}">
						<c:if test="${not empty projWorkTimeCountInfo.projName}">
							<tr>
								<td style="width:200px" colspan="2">
									${projWorkTimeCountInfo.projName}
								</td>
								<td>
									${projWorkTimeCountInfo.prpjectWorkTotalHours}
								</td>
							</tr>
						</c:if>
						<c:if test="${not empty projWorkTimeCountInfo.workContentName}">
							<tr>
								<td width="30px" align="left">&nbsp;</td>
								<td width="170xp" align="left">${projWorkTimeCountInfo.workContentName}</td>
								<td align="left">
									${projWorkTimeCountInfo.workHoursNum}
								</td>
							</tr>
						</c:if>
					</c:forEach>
				</table>
			</div>
		</div>
	</form:form>
</body>
</html>
