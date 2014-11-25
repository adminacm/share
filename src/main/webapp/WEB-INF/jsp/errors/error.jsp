<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" isErrorPage="true"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>システムエラー</title>
  </head>
  <body>
  <%@ include file="../includes/header.jsp"%>
  <div align="center">
  	<font size="5"  color="red">予期せぬエラーが発生した</font><br>
  	<font size="5"  color="red"><%=exception.getMessage()%> </font><br>
  	<font size="5"  color="red">再ログインしてください！</font><br>
  </div>
  </body>
</html>
