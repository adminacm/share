<!doctype html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>
<head>
<meta charset="utf-8">
<title>個人設定変更</title>

<meta content="IE=edge,chrome=1" http-equiv="X-UA-Compatible">
<meta name="viewport" content="width=device-width, initial-scale=1.0">

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
	<%@ include file="includes/header.jsp"%>
		<div style="margin-left:50px;margin-top:50px;border-style:solid;width:400px;">
			<div style="padding:2px;">
				<b>個人設定変更</b>
			</div>
			<div style="margin-left: 10px;margin-top: 10px;">
				<c:forEach var="message" items="${setupForm.confirmMsgList }">
					<span style="color:red">${message }</span><br/>
				</c:forEach>
			</div>
			<div style="margin-top: 20px;margin-bottom:10px;">
				<table style="margin-left:40px;">
					<tr>
						<td style="width:120px;">代理入力者</td>
						<td>
							<form:select path="agentCd" style="width:145px;border:2px solid #333333;" id="agentCd">
								<form:option value=""></form:option>
								<form:options items="${setupForm.agentList}" itemValue="id" itemLabel="userName"/>
							</form:select>
						</td>
					</tr>
					<tr>
						<td>&nbsp;</td>
					</tr>
					<tr>
						<td>1日の勤務時間数</td>
						<td>
							<form:select path="oneDayKinmuHours" style="width:145px;border:2px solid #333333;" id="oneDayKinmuHours">
								<form:options items="${setupForm.oneDayMayKinmuHoursList}" />
							</form:select>
						</td>
					</tr>
					<tr>
						<td>標準ｼﾌﾄ</td>
						<td>
							<form:select path="standardShift" style="width:145px;border:2px solid #333333;" id="standardShift" onchange="submitAction('/setup/shiftChange');">
								<form:options items="${setupForm.standardShiftList}" itemValue="shiftCode" itemLabel="shiftCode"/>
							</form:select>
						</td>
					</tr>
					<tr>
						<td>勤務開始時刻</td>
						<td colspan="2">${setupForm.workStartTime}</td>
					</tr>
					<tr>
						<td>勤務終了時刻</td>
						<td colspan="2">${setupForm.workEndTime}</td>
					</tr>
					<tr>
						<td>入社日</td>
						<td>
							<form:input style="width:70px;border:2px solid #333333;" path="joinDate" maxlength="10"></form:input>
						</td>
					</tr>
					<tr>
						<td>休業開始日</td>
						<td>
							<form:input style="width:70px;border:2px solid #333333;" path="holidayStart" maxlength="10"></form:input>
						</td>
					</tr>
					<tr>
						<td>休業終了日</td>
						<td>
							<form:input style="width:70px;border:2px solid #333333;" path="holidayEnd" maxlength="10"></form:input>
						</td>
					</tr>
					<tr>
						<td>退職日</td>
						<td>
							<form:input style="width:70px;border:2px solid #333333;" path ="outDate" maxlength="10"></form:input>
						</td>
					</tr>
				</table>
			</div>
			<div style="margin-top: 20px;margin-bottom:30px;">
				<table style="margin-left:80px;">
					<tr>
						<td style="width:80px;">
							<input type="button" style="width:60px;" value="保存" onclick="submitAction('/setup/save');" />
						</td>
						<td style="width:40px;">&nbsp;</td>
						<td>
							<input type="button" style="width:60px;" value="戻る" onclick="submitAction('/setup/cancel');" />
						</td>
					</tr>
				</table>
			</div>
		</div>
	</form:form>
</body>
</html>
