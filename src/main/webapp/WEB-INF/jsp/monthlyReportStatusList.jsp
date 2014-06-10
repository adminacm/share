<!doctype html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>
<head>
<meta charset="utf-8">
<title>月報状況一覧</title>

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
	<form:form modelAttribute="monthlyReportStatusListForm">
	<%@ include file="includes/header.jsp"%>
		<div style="margin-left:50px;margin-right:50px;margin-top:50px;border-style:solid;width:850px;">
			<div style="padding:2px;">
				<b>月報状況一覧</b>
			</div>
			<table>
				<tr>
					<td style="width: 10px">&nbsp;</td>
					<td>
						<table class="tableborder">
							<tr>
								<td colspan="9" style="">検索条件</td>
							</tr>
							<tr>
								<td style="width:20px;padding-left:40px;">年</td>
								<td style="width:100px;">
									<form:select path="year" style="width:80px;border:2px solid #333333;" id="year">
										<form:options items="${monthlyReportStatusListForm.yearList}" itemValue="value" itemLabel="name"/>
									</form:select>
								</td>
								<td style="width:20px;">月</td>
								<td style="width:100px;">
									<form:select path="month" style="width:80px;border:2px solid #333333;" id="month">
										<form:options items="${monthlyReportStatusListForm.monthList}" itemValue="value" itemLabel="name"/>
									</form:select>
								</td>
								<td style="width:40px;">所属</td>
								<td style="width:120px;">
									<form:select path="affiliation" style="width:100px;border:2px solid #333333;" id="affiliation">
										<form:options items="${monthlyReportStatusListForm.affiliationList}" itemValue="value" itemLabel="name"/>
									</form:select>
								</td>
								<td style="width:40px;">状況</td>
								<td style="width:150px;">
									<form:select path="status" style="width:100px;border:2px solid #333333;" id="status">
										<form:options items="${monthlyReportStatusListForm.statusList}" itemValue="value" itemLabel="name"/>
									</form:select>
								</td>
								<td><input type="button" value="表示切替" onclick="submitAction('/monthlyReportStatusList/search');"/></td>
							</tr>
						</table>
					</td>
				</tr>
				<tr>
					<td style="width: 10px">&nbsp;</td>
					<td>
						<table class="resultStyle" id="payHolidayList" style="margin-left:2px;">
							<thead>
								<tr class="headStyle">
									<th>No.</th>
									<th>所属</th>
									<th>ID</th>
									<th>氏名</th>
									<th>申請区分</th>
									<th>申請内容</th>
									<th>状況</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach var="mRS" items="${monthlyReportStatusListForm.monthlyReportStatusList}">
									<tr>
										<td align="center">
											<a href="/monthlyReportStatusList/applyNoClick?applyNo=${mRS.applyNo}&applyKbnCd=${mRS.applyKbnCd}">${mRS.applyNo}</a>
										</td>
										<td>
											${mRS.affiliationName}
										</td>
										<td>
											${mRS.userId}
										</td>
										<td>
											${mRS.userName}
										</td>
										<td>
											${mRS.applyKbnName}
										</td>
										<td>
											${mRS.applyDetail}
										</td>
										<td>
											${mRS.statusName}
										</td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</td>
				</tr>
			</table>
			<div align="right" style="width:818px; height:50px">
				<input type="button" value="給与奉行向けCSV出力" onclick="submitAction('/monthlyReportStatusList/csvOutput');"/>
			</div>
		</div>	
	</form:form>
</body>
</html>
