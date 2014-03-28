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
	width:700px;
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
	white-space: nowrap;
	padding:2px;
	font-weight:normal;
	border: 1px solid #ccc;
}
</style>
</head>
<body>
	<form:form modelAttribute="approvalListForm">
		<div style="margin-left:50px;margin-right:50px;margin-top:50px;border-style:solid;width:800px;">
			<div style="padding:2px;">
				<b>承認一覧</b>
			</div>
			<div>
				<table style="width:700px;margin-left:40px;margin-right:40px;margin-bottom:30px;border:1px solid #ccc;">
					<tr>
						<td colspan="5" style="">検索条件</td>
					</tr>
					<tr>
						<td style="padding-left:40px;">状況</td>
						<td>
							<form:select path="status" style="width:100%;border:2px solid #333333;" id="status">
										<form:options items="${approvalListForm.statusList}" itemValue="value" itemLabel="name"/>
							</form:select>
						</td>
						<td style="padding-left:220px;"><input type="button" value="表示切替" onclick="submitAction('/approvalList/search');"/></td>
					</tr>
				</table>
			</div>
			<div>
				<table class="table1" id="resultList">
					<thead>
						<tr>
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
			</div>
		</div>	
	</form:form>
</body>
</html>
