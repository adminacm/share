<!doctype html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>
<head>
<meta charset="utf-8">
<title>承認一覧</title>

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
	<form:form modelAttribute="approvalListForm">
		<div style="margin-left:50px;margin-right:50px;margin-top:50px;border-style:solid;width:850px;">
			<div style="padding:2px;">
				<font size="5"><b>承認一覧</b></font>
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
								<td style="padding-left:40px;">状況</td>
								<td>
									<form:select path="status" style="width:100%;border:2px solid #333333;" id="status">
												<form:options items="${approvalListForm.statusList}" itemValue="value" itemLabel="name"/>
									</form:select>
								</td>
								<td style="padding-left:220px;"><input type="button" value="表示切替" onclick="submitAction('/approvalList/search');"/></td>
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
						<table class="resultStyle" id="payHolidayList" style="margin-left:2px;">
							<thead>
								<tr class="headStyle">
									<th>No.</th>
									<th>申請区分</th>
									<th>申請内容</th>
									<th>状況</th>
									<th>所属</th>
									<th>氏名</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach var="approval" items="${approvalListForm.approvalList}">
									<tr>
										<td align="center">
											<a href="submitAction('/approvalList/noClick?${approval.no}&${approval.applyKbn}');">${approval.no}</a>
										</td>
										<td>
											${approval.applyKbn}
										</td>
										<td>
											${approval.applyDetail}
										</td>
										<td>
											${approval.status}
										</td>
										<td>
											${approval.affiliation}
										</td>
										<td>
											${approval.name}
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
