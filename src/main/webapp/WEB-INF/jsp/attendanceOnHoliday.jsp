<!doctype html>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<html>
<head>
<meta charset="utf-8">
<title>休日勤務入力画面</title>
<meta content="IE=edge,chrome=1" http-equiv="X-UA-Compatible">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<script src="//code.jquery.com/jquery-1.11.0.min.js"></script>
<script type="text/javascript">
/* アクション提出 */
function submitAction(action) {

	document.forms[0].action = action;
	document.forms[0].submit();
}
/* 時刻のフォーマット */
function formatHHmm(e) {
	var time = e.value;
	
	if (time.length == 4) {
		e.value = time.substring(0, 2) + ":" + time.substring(2, 4);
	}
}
/* 振替日のフォーマット */
function formatDate(e) {
	
	$.ajax({
		url : "/attendanceOnHoliday/changeDate",
		data:$("#atendanceOnHolidayForm").serialize(),
		dataType : 'text',
		success : function(data) {
			e.value = data;
		}
	});
}
</script>
</head>

<body>
	<form:form modelAttribute="atendanceOnHolidayForm">
	<%@ include file="includes/header.jsp"%>
		<div style="margin-left:50px;margin-right:50px;margin-top:50px;border-style:solid;width:500px;">
			<div style="padding:2px;">
				<b>休日勤務入力</b>
			</div><br>
			<div style="margin-left: 10px;margin-top: 10px;">
				<c:forEach var="message" items="${atendanceOnHolidayForm.confirmMsgList}">
					<span style="color:red">${message }</span><br/>
				</c:forEach>
			</div>
			<div style="margin-top: 20px;" >
				<table style="margin:auto; width:300px;">
					<tr>
						<td align="left" width="80">社員番号</td>
						<td align="left" width="50">${atendanceOnHolidayForm.taishoUserId}</td>
						<td align="left" width="50">氏名</td>
						<td align="left" width="150">${atendanceOnHolidayForm.taishoUserName}</td>
					</tr>
				</table>
			</div>
			<div style="margin-top: 10px;margin-bottom:10px;" >
				<table style="margin:auto; width:300px; border: 1px solid #333;">
					<tr>
						<td width="120px">日付</td>
						<td width="180px">${atendanceOnHolidayForm.strAtendanceDateShow}</td>
					</tr>
					<tr>
						<td width="120px">勤務区分</td>
						<td width="180px">
							<form:select path="selectedAtendanceDayKbn" style="width:80%;" >
								<form:options items="${atendanceOnHolidayForm.atendanceDayKbnList}" itemValue="code" itemLabel="name"/>
							</form:select>
						</td>
					</tr>
					<tr>
						<td width="120px">勤務時間</td>
						<td width="180px">
							<form:input style="width: 40px;" path="strAtendanceTimeStat" maxlength="4" onblur="formatHHmm(this)"></form:input>
							～
							<form:input style="width: 40px;" path="strAtendanceTimeEnd" maxlength="4" onblur="formatHHmm(this)"></form:input>
						</td>
					</tr>
					<tr>
						<td width="120px">振替日</td>
						<td width="180px">
							<form:input style="width: 100px;" path="strHurikaeDate" maxlength="8" onblur="formatDate(this)"></form:input>
						</td>
					</tr>
					<tr>
						<td width="120px">プロジェクト名</td>
						<td width="180px">
						
							<form:select path="selectedProjCd" style="width:99%;" >
								<form:option value=""></form:option>
								<form:options items="${atendanceOnHolidayForm.projCdList}" itemValue="projectCode" itemLabel="projectName"/>
							</form:select>
						</td>
					</tr>
					<tr>
						<td width="120px">業務内容</td>
						<td width="180px">
							<form:textarea path="strCommont" rows="2" style="width: 140px;"/>
						</td>
					</tr>
				</table>
			</div>
			<div style="margin-top:20px;">
				<table style="margin-top: 40px;margin-bottom:10px;width: 300px;margin: auto">
					<tr>
						<td align="center" width="100px"><input type="button" value="保存" onclick="submitAction('/attendanceOnHoliday/save');" /></td>
						<td align="center" width="100px"><input type="button" value="削除" onclick="submitAction('/attendanceOnHoliday/delete');" /></td>
						<td align="center" width="100px"><input type="button" value="戻る" onclick="submitAction('/attendanceOnHoliday/back');" /></td>
					</tr>
				</table>
			</div>
		</div>
	</form:form>
</body>
</html>
