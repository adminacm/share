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
	<div style="margin-left:50px;margin-right:50px;margin-top:50px;border-style:solid;width:600px;">
		<div style="padding:2px;">
			<b>勤怠入力</b>
		</div>
		<div style="margin-top: 20px;margin-bottom:10px;" >
			<table style="margin:auto; width:300px; background:#ffddff;">
				<tr>
					<td align="right" width="30px"><input type="button" value="←" onclick="submitAction('/attendanceInput/lastMonth');" /></td>
					<td align="center" width="160px" style="background: #CCFFFF">${attendanceInputForm.attDate}</td>
					<td align="left" width="30px"><input type="button" value="→" onclick="submitAction('/attendanceInput/nextMonth');" /></td>
				</tr>
			</table>
			<table style="margin:auto;width:300px;margin-top:30px;background:#ddddff;">
				<tr>
					<td align="left" width="120px">シフトコード</td>
					<td align="left" width="180px" colspan="2"><input type="text" style="width: 60px; "></td>
				</tr>
				<tr>
					<td align="left" width="120px">勤務開始時刻</td>
					<td align="left" width="180px" colspan="2"><input type="text" style="width: 28px;"><input type="text" style="width: 28px; "></td>
				</tr>
				<tr>
					<td align="left" width="120px">勤務終了時刻</td>
					<td align="left" width="180px" colspan="2"><input type="text" style="width: 28px; "><input type="text" style="width: 28px; "></td>
				</tr>
				<tr>
					<td align="left" width="120px">休暇欠勤区分</td>
					<td align="left" width="180px" colspan="2">
						<form:select path="kyukaKb" style="width:100%;" >
							<form:option value=""></form:option>
							<form:options items="${attendanceInputForm.kyukakbList}" itemValue="value" itemLabel="name"/>
						</form:select>
					</td>
				</tr>
			</table>
			<table style="margin:auto; width:300px;">
				<tr>
					<td align="left" colspan="3"><input type="button" value="計算" onclick="submitAction('/attendanceInput/lastMonth');" /></td>
				</tr>
			</table>
			<table class="table1">
				<tr>
					<td width="120px" colspan="2">休暇時間数</td>
					<td></td>
				</tr>
				<tr>
					<td width="120px" colspan="2">勤務時間数</td>
					<td></td>
				</tr>
				<tr>
					<td width="30px" rowspan="6" align="center">超過勤務</td>
					<td width="90">開始時刻</td>
					<td></td>
				</tr>
				<tr>
					<td width="90">終了時刻</td>
					<td></td>
				</tr>
				<tr>
					<td width="90">平日割増</td>
					<td></td>
				</tr>
				<tr>
					<td width="90">平日通常</td>
					<td></td>
				</tr>
				<tr>
					<td width="90">休日</td>
					<td></td>
				</tr>
				<tr>
					<td width="90">深夜</td>
					<td></td>
				</tr>
			</table>
			<table class="table2">
				<thead>
					<tr>
						<td align="center" width="200px">プロジェクト名</td>
						<td align="center" width="200px">作業</td>
						<td align="center" width="40px">時間数</td>
					</tr>
				</thead>
				<tbody>
					<c:if test="${! empty attendanceInputForm.projectList}">
						<c:forEach var="projectInfo" items="${attendanceInputForm.projectList}"  varStatus="st">
							<tr id="tr${st.index}">
								<td align="center" >
									<form:select path="projectList[${st.index}].projectId" style="width:100%;" >
										<form:option value=""></form:option>
										<form:options items="${projectInfo.projectItemList}" itemValue="value" itemLabel="name"/>
									</form:select>
								</td>
								<td align="center">
									<form:select path="projectList[${st.index}].workId" style="width:100%;" >
										<form:option value=""></form:option>
										<form:options items="${projectInfo.workItemList}" itemValue="value" itemLabel="name"/>
									</form:select>
								</td>
								<td align="center" ><form:input path="projectList[${st.index}].hours" size="4"/></td>
							</tr>
						</c:forEach>
					</c:if>
				</tbody>
			</table>
			<table style="margin:auto; width:200px;margin-top:10px;">
				<tr>
					<td align="left" width="100px">ロケーション</td>
					<td align="left" width="100px">
						<form:select path="locationId" style="width:100%;" >
							<form:options items="${attendanceInputForm.locationItemList}" itemValue="value" itemLabel="name"/>
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
		</div>
	</div>
	</form:form>
</body>
</html>
