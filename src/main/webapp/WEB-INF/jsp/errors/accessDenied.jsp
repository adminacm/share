<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPEhtmlPUBLIC"-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<title>拒絶訪問</title>
</head>
<body>
<%@ include file="../includes/header.jsp"%>
    <font size="5">Access Deny!</font><br>
    ${requestScope['SPRING_SECURITY_403_EXCEPTION'].message}
  </body>
</html>