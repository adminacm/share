<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPEhtmlPUBLIC"-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<base href="<%=basePath%>">
	<title>拒絶訪問</title>
</head>
<body>
<%@ include file="../includes/header.jsp"%>
    <font size="5">Access Deny!</font><br>
    ${requestScope['SPRING_SECURITY_403_EXCEPTION'].message}
  </body>
</html>