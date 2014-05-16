<!doctype html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>
<head>
<meta charset="utf-8">
<title>休日出勤管理</title>

<meta content="IE=edge,chrome=1" http-equiv="X-UA-Compatible">
<meta name="viewport" content="width=device-width, initial-scale=1.0">

<STYLE TYPE="text/CSS">   
.tableborder {
	width: 800px;
	height: 100%;
	margin: 0px auto;
	margin-bottom: 20px;
	background-color: #F0FBEB;
	white-space: nowrap;
	border: 1px solid #9BDF70;
	border-top-color: #0080FF;
	border-top-style: dotted;
	border-top-width: 3px
}
.resultStyle {
	width: 800px;
	height: 100%;
	margin: 0px auto;
	margin-bottom: 20px;
	border: 1px solid #73BF00;
	background-color: #FFFFF7;
}
.headStyle {
	margin-left: 20px;
	background-color: #007500;
	height: 100%;
	font-weight: 600;
	text-align: center;
	color: white;
	border: 1px;
	style: border-collapse:collapse;
	frame: hsides;
	rules: rows;
	width: 50px;
	border-bottom: 1px solid #CCC;
}

</STYLE>

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
	<form:form modelAttribute="attendanceOnHolidayRecordForm">
	<%@ include file="includes/header.jsp"%>
		<div style="margin-left:50px;margin-right:50px;margin-top:50px;border-style:solid;width:850px;">
			<div style="padding:2px;">
				<font size="5"><b>休日出勤管理</b></font>
			</div>
			<table>
				<tr>
					<td style="width: 10px">&nbsp;</td>
					<td>
						<table class="tableborder">
							<tr>
								<td colspan="3" style="">検索条件</td>
							</tr>
							<tr>
								<td style="padding-left:25px;width:30px">年度</td>
								<td style="width:130px">
									<form:select path="yearPeriod" style="width:100%;border:2px solid #333333;" id="yearPeriod">
										<form:options items="${attendanceOnHolidayRecordForm.yearPeriodList}" itemValue="value" itemLabel="name"/>
									</form:select>
								</td>
								<td style="padding-left:25px;width:30px">氏名</td>
								<td style="width:130px">
									<form:select path="userName" style="width:100%;border:2px solid #333333;" id="userName">
										<form:options items="${attendanceOnHolidayRecordForm.userNameList}" itemValue="value" itemLabel="name"/>
									</form:select>
								</td>
								<td style="width:100px;padding-left:100px;">
									<input type="button" value="表示切替" onclick="submitAction('/attendanceOnHolidayRecord/search');" />
								</td>
								<td>&nbsp;</td>
							</tr>
							<tr>
								<td>&nbsp;</td>
							</tr>
						</table>
					</td>
				</tr>
				<tr>
					<td style="width: 10px">&nbsp;</td>
					<td><b>休日振替勤務</b></td>
				</tr>
				<tr>
					<td style="width: 10px">&nbsp;</td>
					<td>
						<table class="resultStyle" id="holidayExchangeWorkList" style="margin-left:2px;width:200px">
							<thead>
								<tr class="headStyle">
									<th>日付</th>
									<th>振替休日</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach var="holidayExchangeWork" items="${attendanceOnHolidayRecordForm.holidayExchangeWorkList}">
									<tr align="center">
										<td style="width:100px">
											<a href="/attendanceOnHolidayRecordDetail/init?date=${holidayExchangeWork.holidayTurnedWorkingDate}&workKbn=03">${holidayExchangeWork.holidayTurnedWorkingDate}</a>
										</td>
										<td>
											${holidayExchangeWork.workingDayTurnedHolidayDate}
										</td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</td>
				</tr>
				<tr>
					<td style="width: 10px">&nbsp;</td>
					<td><b>休日勤務</b></td>
				</tr>
				<tr>
					<td style="width: 10px">&nbsp;</td>
					<td>
						<table class="resultStyle" id="holidayOverWorkList" style="margin-left:2px;width:450px ">
							<thead>
								<tr class="headStyle">
									<th>日付</th>
									<th>代休期限</th>
									<th>代休日</th>
									<th>超勤振替申請日</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach var="holidayOverWork" items="${attendanceOnHolidayRecordForm.holidayOverWorkList}">
									<tr align="center">
										<td style="width:100px">
											<a href="/attendanceOnHolidayRecordDetail/init?date=${holidayOverWork.holidayOverWorkDate}&workKbn=01">${holidayOverWork.holidayOverWorkDate}</a>
										</td>
										<td style="width:100px">
											${holidayOverWork.turnedHolidayEndDate}
										</td>
										<td style="width:100px">
											${holidayOverWork.turnedHolidayDate}
										</td>
										<td>
											${holidayOverWork.overWorkTurnedReqDate}
										</td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</td>
				</tr>
			</table>
		</div>	
	</form:form>
</body>
</html>
