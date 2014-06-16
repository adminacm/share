<!doctype html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>
<head>
<meta charset="utf-8">
<title>勤怠入力</title>

<meta content="IE=edge,chrome=1" http-equiv="X-UA-Compatible">
<meta name="viewport" content="width=device-width, initial-scale=1.0">

<script type="text/javascript">
/* アクション提出 */
function submitAction(action) {

	document.forms[0].action = action;
	document.forms[0].submit();
}

</script>
<style type="text/css">

.table1 {
	width:200px;
	border: 1px solid #FFFFFF;
	border-collapse:collapse;
	margin-left:150px;
	background: #CCFFFF;
	
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
	border: 1px solid #FFFFFF;
}

.table2 {
	width:400px;
	border: 1px solid #333;
	border-collapse:collapse;
	margin:auto;
	margin-top:20px;
	
}

.table2 td {
	white-space: normal;
	padding:2px;
	font-weight:normal;
	border: 1px solid #333;
}
</style>
</head>
<body>
	<form:form modelAttribute="attendanceInputForm">
	<%@ include file="includes/header.jsp"%>
	<div style="margin-left:50px;margin-right:50px;margin-top:50px;border-style:solid;width:600px;">
		<div style="padding:2px;">
			<b>勤怠入力</b>
		</div>
		<div style="margin-left: 10px;margin-top: 10px;">
			<c:forEach var="message" items="${attendanceInputForm.confirmMsgList }">
				<span style="color:red">${message }</span><br/>
			</c:forEach>
		</div>
		<div style="margin-top: 20px;margin-bottom:10px;" >
			<table style="margin:auto; width:300px;">
				<tr>
					<td align="right" width="30px"><input type="button" value="←" onclick="submitAction('/attendanceInput/lastDay');" /></td>
					<!-- 勤務日 -->
					<c:if test="${attendanceInputForm.workDayKbn != '01'}">
						<td align="center" width="160px" style="background: #FF99CC">${attendanceInputForm.attDateShow}</td>
					</c:if>
					<!-- 社休日 -->
					<c:if test="${attendanceInputForm.workDayKbn == '01'}">
						<td align="center" width="160px" style="background: #CCFFFF">${attendanceInputForm.attDateShow}</td>
					</c:if>
					<td align="left" width="30px"><input type="button" value="→" onclick="submitAction('/attendanceInput/nextDay');" /></td>
				</tr>
			</table>
			<!-- 勤務日情報 -->
			<table style="margin:auto; width:300px;margin-top:10px;">
				<tr>
					<td align="left" width="120px">勤務日区分</td>
					<td align="left" width="180px">${attendanceInputForm.workDayKbnName}</td>
				</tr>
			</table>
			<!-- 社休日 -->
			<c:if test="${attendanceInputForm.workDayKbn != '01'}">
				<table style="margin:auto; width:300px;">
					<tr>
						<td align="center" ><input type="button" value="休日勤務入力" onclick="submitAction('/attendanceInput/attendanceOnHoliday');" /></td>
					</tr>
				</table>
			</c:if>
			<c:if test="${attendanceInputForm.workDayKbn == '03'}">
				<table style="margin:auto;width:300px;border: 1px solid #333;">
					<tr>
						<td colspan="2" align="center"><b>［休日勤務］</b></td>
					</tr>
					<tr>
						<td align="left" width="150px">休日勤務区分</td>
						<td align="left" width="150px">${attendanceInputForm.holidayAttendance.kinmuKbnName}</td>
					</tr>
					<tr>
						<td align="left" width="150px">勤務時間</td>
						<td align="left" width="150px">${attendanceInputForm.holidayAttendance.kinmuStartTime}～${attendanceInputForm.holidayAttendance.kinmuEndTime}</td>
					</tr>
					<tr>
						<td align="left" width="150px">振替日</td>
						<td align="left" width="150px">${attendanceInputForm.holidayAttendance.furikaeDate}</td>
					</tr>
					<tr>
						<td align="left" width="150px">プロジェクト名</td>
						<td align="left" width="150px">${attendanceInputForm.holidayAttendance.projectName}</td>
					</tr>
					<tr>
						<td align="left" width="150px">業務内容</td>
						<td align="left" width="150px">${attendanceInputForm.holidayAttendance.workDetail}</td>
					</tr>
				</table>
			</c:if>
			<c:if test="${attendanceInputForm.workDayKbn == '01' || attendanceInputForm.workDayKbn == '03'}">
				<table style="margin:auto;width:300px;">
					<tr>
						<td align="left" width="120px">シフトコード</td>
						<td align="left" width="180px" colspan="2">
							<form:input path="shiftCd" style="width: 60px; " />
						</td>
					</tr>
					<tr>
						<td align="left" width="120px">勤務開始時刻</td>
						<td align="left" width="180px" colspan="2"><form:input path="workSHour" maxlength="2" cssStyle="width:15%"/><form:input path="workSMinute"  maxlength="2" cssStyle="width:15%"/></td>
					</tr>
					<tr>
						<td align="left" width="120px">勤務終了時刻</td>
						<td align="left" width="180px" colspan="2"><form:input path="workEHour" maxlength="2" cssStyle="width:15%"/><form:input path="workEMinute"  maxlength="2" cssStyle="width:15%"/></td>
					</tr>
					<tr>
						<td align="left" width="120px">休暇欠勤区分</td>
						<td align="left" width="180px" colspan="2">
							<form:select path="kyukaKb" style="width:100%;" >
								<form:option value=""></form:option>
								<form:options items="${attendanceInputForm.kyukakbList}" itemValue="code" itemLabel="name"/>
							</form:select>
						</td>
					</tr>
				</table>
				<table style="margin:auto; width:300px;">
					<tr>
						<td align="left" colspan="3"><input type="button" value="計算" onclick="submitAction('/attendanceInput/count');" /></td>
					</tr>
				</table>
				<table class="table1">
					<tr>
						<td width="120px" colspan="2">休暇時間数</td>
						<td>
							<c:if test="${attendanceInputForm.kyukaHours != 0.0 && attendanceInputForm.kyukaHours != null}">
								${attendanceInputForm.kyukaHours}h
							</c:if>
						</td>
					</tr>
					<tr>
						<td width="120px" colspan="2">勤務時間数</td>
						<td>
							<c:if test="${attendanceInputForm.workHours != 0.0 && attendanceInputForm.workHours != null}">
								${attendanceInputForm.workHours}h
							</c:if>
						</td>
					</tr>
					<tr>
						<td width="30px" rowspan="6" align="center">超過勤務</td>
						<td width="90">開始時刻</td>
						<td>
							<c:if test="${! empty attendanceInputForm.choSTime}">
								${attendanceInputForm.choSTime}
							</c:if>
						</td>
					</tr>
					<tr>
						<td width="90">終了時刻</td>
						<td>
							<c:if test="${! empty attendanceInputForm.choETime}">
								${attendanceInputForm.choETime}
							</c:if>
						</td>
					</tr>
					<tr>
						<td width="90">平日割増</td>
						<td>
							<c:if test="${attendanceInputForm.choWeekday != 0.0 && attendanceInputForm.choWeekday != null}">
								${attendanceInputForm.choWeekday}h
							</c:if>
						</td>
					</tr>
					<tr>
						<td width="90">平日通常</td>
						<td>
							<c:if test="${attendanceInputForm.choWeekdayNomal != 0.0 && attendanceInputForm.choWeekdayNomal != null}">
								${attendanceInputForm.choWeekdayNomal}h
							</c:if>
						</td>
					</tr>
					<tr>
						<td width="90">休日</td>
						<td>
							<c:if test="${attendanceInputForm.choHoliday != 0.0 && attendanceInputForm.choHoliday != null}">
								${attendanceInputForm.choHoliday}h
							</c:if>
						</td>
					</tr>
					<tr>
						<td width="90">深夜</td>
						<td>
							<c:if test="${attendanceInputForm.mNHours != 0.0 && attendanceInputForm.mNHours != null}">
								${attendanceInputForm.mNHours}h
							</c:if>
						</td>
					</tr>
				</table>
				<table class="table2">
					<thead>
						<tr>
							<td align="center" width="180px">プロジェクト名</td>
							<td align="center" width="160px">作業</td>
							<td align="center" width="50px">時間数</td>
						</tr>
					</thead>
					<tbody>
						<c:if test="${! empty attendanceInputForm.projectList}">
							<c:forEach var="projectInfo" items="${attendanceInputForm.projectList}"  varStatus="st">
								<tr id="tr${st.index}">
									<td align="center" >
										<form:select path="projectList[${st.index}].projectId" style="width:100%;" >
											<form:option value=""></form:option>
											<form:options items="${projectInfo.projectItemList}" itemValue="code" itemLabel="name"/>
										</form:select>
									</td>
									<td align="center">
										<form:select path="projectList[${st.index}].workId" style="width:100%;" >
											<form:option value=""></form:option>
											<form:options items="${projectInfo.workItemList}" itemValue="code" itemLabel="name"/>
										</form:select>
									</td>
									<td align="center" ><form:input path="projectList[${st.index}].hours" size="4"/></td>
								</tr>
							</c:forEach>
						</c:if>
					</tbody>
				</table>
				<input type="button" value="行追加" onclick="submitAction('/attendanceInput/add');" style="margin-left:100px;"/>
				<table style="margin:auto; width:200px;margin-top:10px;">
					<tr>
						<td align="left" width="100px">ロケーション</td>
						<td align="left" width="100px">
							<form:select path="locationId" style="width:100%;" >
								<form:options items="${attendanceInputForm.locationItemList}" itemValue="code" itemLabel="name"/>
							</form:select>
						</td>
					</tr>
				</table>
				<table style="margin:auto; width:200px;">
					<tr>
						<td align="left" width="100px"><input type="button" value="保存" onclick="submitAction('/attendanceInput/save');" /></td>
						<td align="right" width="100px"><input type="button" value="戻る" onclick="submitAction('/attendanceInput/back');" /></td>
					</tr>
				</table>
			</c:if>
		</div>
	</div>
	</form:form>
</body>
</html>
