<!doctype html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>
<head>
<meta charset="utf-8">
<title>個人設定</title>

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
</head>
<body>
	<form:form modelAttribute="setupForm">
		<div style="margin-left:50px;margin-top:50px;border-style:solid;width:400px;">
			<div style="padding:2px;">
				<b>個人設定</b>
			</div>
			<div style="margin-top: 20px;margin-bottom:10px;">
				<table style="width:300px;margin-left:40px;margin-bottom:30px;">
					<tr>
						<td align="right" colspan="4"><input type="button" value="編集" onclick="submitAction('/setup/edit');" /></td>
					</tr>
					<tr>
						<td colspan="4">代理入力者</td>
					</tr>
					<tr>
						<td></td>
						<td colspan="3">${setupForm.agentName}</td>
					</tr>
					<tr>
						<td>&nbsp;</td>
					</tr>
					<tr>
						<td colspan="4">就業条件</td>
					</tr>
					<tr>
						<td></td>
						<td>標準ｼﾌﾄ</td>
						<td colspan="2">${setupForm.standardShift}</td>
					</tr>
					<tr>
						<td></td>
						<td>勤務開始時刻</td>
						<td colspan="2">${setupForm.workStart}</td>
					</tr>
					<tr>
						<td></td>
						<td>勤務終了時刻</td>
						<td colspan="2">${setupForm.workEnd}</td>
					</tr>
					<tr>
						<td></td>
						<td>入社日</td>
						<td colspan="2">${setupForm.joinDate}</td>
					</tr>
					<tr>
						<td></td>
						<td>休業開始日</td>
						<td colspan="2">${setupForm.holidayStart}</td>
					</tr>
					<tr>
						<td></td>
						<td>休業終了日</td>
						<td colspan="2">${setupForm.holidayEnd}</td>
					</tr>
					<tr>
						<td></td>
						<td>退職日</td>
						<td colspan="2">${setupForm.outDate}</td>
					</tr>
				</table>
			</div>
		</div>
	</form:form>
</body>
</html>
