<!doctype html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>
<head>
<meta charset="utf-8">
<title>給与システム用ファイル出力</title>

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
	<form:form modelAttribute="makeKyuyoFileForm">
	<%@ include file="includes/header.jsp"%>
		<div style="margin-left:50px;margin-right:50px;margin-top:50px;border-style:solid;width:850px;">
			<div style="padding:2px;">
				<b>給与システム用ファイル出力</b>
			</div>
			<table>
				<tr>
					<td style="width: 10px">&nbsp;</td>
					<td>
						<table class="tableborder">
							<tr>
								<td style="width:20px;padding-left:40px;">処理年月</td>
								<td style="width:100px;">
								<td width="100px">
							　　　　	<form:input style="width: 80px;" path="dealYearMonth" maxlength="10"></form:input>
						　　　　　　　</td>
								
								<td><input type="button" value="ファイル作成" onclick="submitAction('/makeKyuyoFile/makeFile');"/></td>
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
									<th>ファイル名</th>
									<th>処理年月</th>
									<th>作成者</th>
									<th>作成日時</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach var="makeKyuyoFileIchiraninfo" items="${makeKyuyoFileForm.makeKyuyoFileIchiranList}">
									<tr>
										<td align="center">
											<a href="/makeKyuyoFile/madeKyuyoFileNameClick?madeKyuyoFileName=${mRS.madeKyuyoFileName}">${makeKyuyoFileIchiraninfo.madeKyuyoFileName}</a>
										</td>
										<td>
											${makeKyuyoFileIchiraninfo.dealYearMonth}
										</td>
										<td>
											${makeKyuyoFileIchiraninfo.createdUserName}
										</td>
										<td>
											${makeKyuyoFileIchiraninfo.createdDateTime}
										</td>
										<td>
											${makeKyuyoFileIchiraninfo.applyDetail}
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
