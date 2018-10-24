<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/jsp/pub/include.jsp" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<c:set var="prourl" value="<%=basePath%>"></c:set>
<c:set var="proname" value="${pageContext.request.contextPath}"></c:set>
<html>
<head>
    <base href="<%=basePath%>">
    <title>后台首页</title>
    <jsp:include page="/WEB-INF/jsp/pub/head_meta.jsp"/>
    <jsp:include page="/WEB-INF/jsp/pub/head_link.jsp"/>
    <jsp:include page="/WEB-INF/jsp/pub/head_script.jsp"/>
    <!--=====================CSS_Link===========================-->
    <link rel="stylesheet" href="plugins/uploadify/uploadify.css" type="text/css">
    <!--=====================JS_Link===========================-->
    <script type="text/javascript" src="plugins/uploadify/jquery.uploadify.min.js"></script>
</head>
<body>
    <jsp:include page="header.jsp"/>
</body>
</html>
