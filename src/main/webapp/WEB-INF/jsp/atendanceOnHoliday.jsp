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
    <link href="//netdna.bootstrapcdn.com/bootstrap/2.3.2/css/bootstrap.min.css" rel="stylesheet">
</head>

<body>
<br/>
  <p style="text-align:left;font-size:30px"><b>休日勤務入力</b><br></p>  
  <table align="left">
    <form action = "save" method = "POST" commandName="atendanceOnHolidayInfo" >  
    <tr><td>
    <spring:bind path="atendanceOnHolidayInfo.strAtendanceDate">日付:
    <input style="text-align:left;" type ="text" name="strAtendanceDate" value ="<c:out value = "${status.value}" />" /></td>
    <td><font color= "red"><c:out value="${status.errorMessage}"/></font></td></tr>
    </spring:bind>
    
　　    <tr><td>
    <spring:bind path="atendanceOnHolidayInfo.strAtendanceTimeStat">勤務時間：
    <input style="text-align:left;" width = "10px" type = "text" name="strAtendanceTimeStat" value ="<c:out value = "${status.value}"/>" />
    <font color= "red"><c:out value="${status.errorMessage}"/></font></spring:bind>～
    <spring:bind path="atendanceOnHolidayInfo.strAtendanceTimeEnd">
    <input style="text-align:left;" type = "text" path="strAtendanceTimeEnd" value ="<c:out value = "${status.value}"/>" />
    <font color= "red"><c:out value="${status.errorMessage}"/></font></spring:bind></td></tr>
           
    <tr><td><spring:bind path="atendanceOnHolidayInfo.strHurikaeDate">振替日：
      　<input style="text-align:left;" type = "text" name="strHurikaeDate" /></spring:bind></td></tr>

    <tr><td><spring:bind path="atendanceOnHolidayInfo.strCommont">業務内容：
    <input style="text-align:left;" type = "text" name="strAtendanceDate" value ="<c:out value = "${status.value}"/>" />
    <font color= "red"><c:out value="${status.errorMessage}"/></font></spring:bind></td></tr><br/>
    <tr>
    <td align="left">    
    <input type="submit" value="保存" class="btn"/>    
    &nbsp;
    <input type="submit" value="削除" class="btn"/>
    &nbsp;
    <input type="submit" value="戻る" class="btn"/>
    </td>
    </tr>
    <br/>
    </form>
  <table>
</body>
</html>
