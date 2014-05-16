<!doctype html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>
<head>
<meta charset="utf-8">
<title>休暇管理</title>

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
.tab1 {
	margin-bottom: 1px;
	float: left;
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
	<form:form modelAttribute="holidayRecordForm">
	<%@ include file="includes/header.jsp"%>
		<div style="margin-left:50px;margin-right:50px;margin-top:50px;border-style:solid;width:850px;">
			<div style="padding:2px;">
				<font size="5"><b>休暇管理</b></font>
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
										<form:options items="${holidayRecordForm.yearPeriodList}" itemValue="value" itemLabel="name"/>
									</form:select>
								</td>
								<td style="width:100px">
									<input type="button" value="表示切替" onclick="submitAction('/holidayRecord/search');" />
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
					<td><b>有給休暇</b></td>
				</tr>
				<tr>
					<td style="width: 10px">&nbsp;</td>
					<td>
						<table class="resultStyle" id="payHolidayList" style="margin-left:2px;width:440px">
							<thead>
								<tr class="headStyle">
									<th>日付</th>
									<th>休暇区分</th>
									<th>日数</th>
									<th>時間数</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach var="payHoliday" items="${holidayRecordForm.payHolidayList}">
									<tr align="center">
										<td style="width:100px">
											${payHoliday.payHolidayDate}
										</td>
										<td style="width:150px">
											${payHoliday.holidayKbnName}
										</td>
										<td style="text-align:right;width:100px">
											${payHoliday.dayQuantity}
										</td>
										<td style="text-align:right">
											${payHoliday.hourQuantity}
										</td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</td>
				</tr>
				<tr>
					<td style="width: 10px">&nbsp;</td>
					<td><b>欠勤</b></td>
				</tr>
				<tr>
					<td style="width: 10px">&nbsp;</td>
					<td>
						<table class="resultStyle" id="absenceList" style="margin-left:2px;width:300px ">
							<thead>
								<tr  class="headStyle">
									<th>日付</th>
									<th>日数</th>
									<th>時間数</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach var="absenceInfo" items="${holidayRecordForm.absenceList}">
									<tr align="center">
										<td style="width:100px">
											${absenceInfo.absentDate}
										</td>
										<td style="text-align:right;width:100px">
											${absenceInfo.dayQuantity}
										</td>
										<td style="text-align:right">
											${absenceInfo.hourQuantity}
										</td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</td>
				</tr>
				<tr>
					<td style="width: 10px">&nbsp;</td>
					<td><b>特別休暇</b></td>
				</tr>
				<tr>
					<td style="width: 10px">&nbsp;</td>
					<td>
						<table class="resultStyle" id="specialHolidayList" style="margin-left:2px;width:200px ">
							<thead>
								<tr class="headStyle">
									<th>日付</th>
									<th>日数</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach var="specialHoliday" items="${holidayRecordForm.specialHolidayList}">
									<tr align="center">
										<td style="width:100px">
											${specialHoliday.specialHolidayDate}
										</td>
										<td style="text-align:right">
											${specialHoliday.dayQuantity}
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
