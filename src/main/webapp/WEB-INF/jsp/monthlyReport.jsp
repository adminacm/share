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

<link href="../css/common.css" rel="stylesheet" type="text/css">

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
			<div>
				<table style="width:800px;margin-left:40px;margin-right:40px;margin-bottom:30px;border:1px solid #ccc;">
					<tr>
						<td colspan="5" style="">検索条件</td>
					</tr>
					<tr>
						<td style="padding-left:40px;">氏名</td>
						<td>
							<form:select path="userCode" style="width:100%;border:2px solid #333333;" id="usCode">
								<form:options items="${monthlyReportForm.userList}" itemValue="value" itemLabel="name"/>
							</form:select>
						</td>
						<td style="padding-left:520px;"><input type="button" value="表示切替" onclick="submitAction('/monthlyReport/search');"/></td>
					</tr>
				</table>
			</div>
			<div style="margin-top: 20px;margin-bottom:10px;background:#ffddff">
				<table style="width:800px;margin-left:40px;margin-right:40px;">
					<tr>
						<td style="border:1px solid #333333;" align="center">${monthlyReportForm.proStatus}</td>
						<td align="right" width="130px"><input type="button" value="←" onclick="submitAction('/monthlyReport/lastMonth');" /></td>
						<td align="center" width="100px;">
							<form:label path="yearMonthHyoji" id="yearMonthHyoji">${monthlyReportForm.yearMonthHyoji}</form:label>
							<form:hidden path="yearMonth"/>
						</td>
						<td align="left"><input type="button" value="→" onclick="submitAction('/monthlyReport/nextMonth');" /></td>
						<td align="right"><input type="button" value="月報提出" onclick="" /></td>
					</tr>
				</table>
			</div>
			<div>
				<table class="table1" id="resultList">
					<thead>
						<tr>
							<th rowspan="2"colspan="2">日付</th>
							<th rowspan="2">区分</th>
							<th rowspan="2">ｼﾌﾄ</th>
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
										<a href="/attendanceInput/init?attDate=${monthlyReport.date}">${monthlyReport.day}</a>
									</td>
									<td align="center" width="25PX;">
										${monthlyReport.week}
									</td>
									<td align="center" width="110PX;">
										${monthlyReport.workKbnName}
									</td>
									<td align="center" width="50PX;">
										${monthlyReport.shift}
									</td>
									<td align="center" width="50PX;">
										${monthlyReport.workSTime}
									</td>
									<td align="center" width="50PX;">
										${monthlyReport.workETime}
									</td>
								</c:if>
								<c:if test="${monthlyReport.totleFlg}">
									<td colspan="6" style="border-bottom-width: 0px; border-left-width: 0px" align="right">計</td>
								</c:if>
								<td align="center" width="45PX;">
									${monthlyReport.restHours}
								</td>
								<td align="center" width="45PX;">
									${monthlyReport.workHours}
								</td>
								<td align="center" width="50PX;">
									${monthlyReport.choSTime}
								</td>
								<td align="center" width="50PX;">
									${monthlyReport.choETime}
								</td>
								<td align="center" width="45PX;">
									${monthlyReport.choWeekday}
								</td>
								<td align="center" width="45PX;">
									${monthlyReport.choWeekdayNomal}
								</td>
								<td align="center" width="45PX;">
									${monthlyReport.choHoliday}
								</td>
								<td align="center" width="45PX;">
									${monthlyReport.mNHours}
								</td>
								<c:if test="${not monthlyReport.totleFlg}">
									<td align="center">
										${monthlyReport.locationName}
									</td>
								</c:if>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
			<div style="margin-left:40px;">
				【PJ別作業時間集計】
			</div>
			<div style="margin-left:80px;">
				<table style="width:400px">
					<c:forEach var="projectInfo" items="${monthlyReportForm.projectList}">
						<tr>
							<td style="width:200px" colspan="2">
								${projectInfo.projName}
							</td>
							<td>
								${projectInfo.projHours}
							</td>
						</tr>
						<c:if test="${not empty projectInfo.projManageHours}">
							<tr>
								<td style="width:40px">&nbsp;</td>
								<td>プロジェクト管理</td>
								<td>
									${projectInfo.projManageHours}
								</td>
							</tr>
						</c:if>
						<c:if test="${not empty projectInfo.basicDesignHours}">
							<tr>
								<td style="width:40px">&nbsp;</td>
								<td>基本設計</td>
								<td>
									${projectInfo.basicDesignHours}
								</td>
							</tr>
						</c:if>
						<c:if test="${not empty projectInfo.meetingHours}">
							<tr>
								<td style="width:40px">&nbsp;</td>
								<td>会議</td>
								<td>
									${projectInfo.meetingHours}
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
