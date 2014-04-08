<!doctype html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>
<head>
    <meta charset="utf-8">
    <title>Menu</title>

    <meta content="IE=edge,chrome=1" http-equiv="X-UA-Compatible">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <link href="//netdna.bootstrapcdn.com/bootstrap/2.3.2/css/bootstrap.min.css" rel="stylesheet">

    <!--
      IMPORTANT:
      This is Heroku specific styling. Remove to customize.
    -->
    <link href="http://heroku.github.com/template-app-bootstrap/heroku.css" rel="stylesheet">
    <!-- /// -->

</head>

<body>
<form:form modelAttribute="menueForm">
	<div class="container">
	    <div class="row">
	        <div class="span8 offset2">
	            <div class="page-header">
	                <h1>アルゴー原価管理</h1>
	            </div>
	            <div class="row">
	            <span style="font-size:19px;">案件管理</span>
	            <ul>
	            	<li style="margin-left:20px;"><a href="/opportunity/init">案件一覧</a></li>
	            </ul>
	            <span style="font-size:19px;">就業管理</span>
	            	<ul>
	            		<li style="margin-left:20px;"><a href="/menu/att">勤怠入力</a></li>
	            		<li style="margin-left:20px;"><a href="/monthlyReport/init?newMonth=">月報</a></li>
	            		<li style="margin-left:20px;"><a href="/holidayRecord/init">休暇管理</a></li>
	            		<li style="margin-left:20px;"><a href="/atendanceOnHoliday/init">休日出勤管理</a></li>
	            		<li style="margin-left:20px;"><a href="/setup/init">個人設定</a></li>
	            		<li style="margin-left:20px;"><a href="/approvalList/init">承認一覧</a></li>
	            		<li style="margin-left:20px;"><a href="/monthlyReportStatusList/init">月報提出状況一覧</a></li>
	            	</ul>
	            </div>
	        </div>
	    </div>
	</div>
</form:form>
</body>
</html>
