<%@ page import="java.util.*" %>

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<%
Map<String, String> maps = (HashMap<String, String>)(request.getAttribute("ITU_HTML_RESPONSE"));

response.getWriter().write(maps.get("username")+"<br>");
response.getWriter().write(maps.get("useremail")+"<br>");
%>
</body>
</html>