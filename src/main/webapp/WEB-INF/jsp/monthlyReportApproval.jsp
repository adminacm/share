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
	border: 1px solid #ccc;
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
	<form:form modelAttribute="monthlyReportApprovalInfo">
		<div style="margin-left:50px;margin-right:50px;margin-top:50px;border-style:solid;width:900px;">
			<div style="padding:2px;">
				<b>月報承認</b>
			</div>
			<div style="margin-top: 20px;margin-bottom:10px;background:#ffddff">
				<table style="width:800px;margin-left:40px;margin-right:40px;">
					<tr>
						<td style="border:1px solid #333333;" align="center">${monthlyReportApprovalInfo.proStatus}</td>
						<td width="130px">
							<input type="button" value="承認" onclick="submitAction('/monthlyReportApproval/approval');" />
						</td>
						<td>
							<input type="button" value="差戻" onclick="submitAction('/monthlyReportApproval/remand');" />
						</td>
						<td>
							<input type="button" value="戻る" onclick="submitAction('/monthlyReportApproval/back');" />
						</td>
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
						<c:forEach var="approvalInfo" items="${monthlyReportApprovalInfo.monthlyReportApprovalList}">
							<tr>
								<td align="center" width="20PX;">
									${approvalInfo.day}
								</td>
								<td align="center" width="20PX;">
									${approvalInfo.week}
								</td>
								<td align="center" width="110PX;">
									${approvalInfo.workKbnName}
								</td>
								<td align="center" width="35PX;">
									${approvalInfo.shift}
								</td>
								<td align="center" width="50PX;">
									${approvalInfo.workSTime}
								</td>
								<td align="center" width="50PX;">
									${approvalInfo.workETime}
								</td>
								<td align="center" width="140PX;">
									${approvalInfo.restKbnName}
								</td>
								<td align="center" width="35PX;">
									${approvalInfo.workHours}
								</td>
								<td align="center" width="50PX;">
									${approvalInfo.choSTime}
								</td>
								<td align="center" width="50PX;">
									${approvalInfo.choETime}
								</td>
								<td align="center" width="35PX;">
									${approvalInfo.choWeekday}
								</td>
								<td align="center" width="35PX;">
									${approvalInfo.choWeekdayNomal}
								</td>
								<td align="center" width="35PX;">
									${approvalInfo.choHoliday}
								</td>
								<td align="center" width="35PX;">
									${approvalInfo.mNHours}
								</td>
								<td align="center" width="50PX;">
									${approvalInfo.locationName}
								</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
			【PJ別作業時間集計】
			<div>
				<table>
					<c:forEach var="projectInfo" items="${monthlyReportApprovalInfo.projectList}">
						<tr>
							<td>
								${projectInfo.projName}
							</td>
							<td>
								${projectInfo.projHours}
							</td>
						</tr>
						<tr>
							<td>プロジェクト管理</td>
							<td>
								${projectInfo.projManageHours}
							</td>
						</tr>
						<tr>
							<td>基本設計</td>
							<td>
								${projectInfo.basicDesignHours}
							</td>
						</tr>
						<tr>
							<td>会議</td>
							<td>
								${projectInfo.meetingHours}
							</td>
						</tr>
					</c:forEach>
				</table>
			</div>
		</div>
	</form:form>
</body>
</html>
