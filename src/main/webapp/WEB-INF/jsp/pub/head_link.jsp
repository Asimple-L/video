<%@ page import="java.util.Date" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="f"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<f:setBundle basename="ApplicationResources"/>
<c:set var="version" value="<%=new Date().getTime()%>"></c:set>
<link rel="shortcut icon" href="<f:message key='pageIcon'/>">
<link rel="stylesheet" href="public/static/css/base.css?v=${version}"/>
<link rel="stylesheet" href="plugins/topNav/css/head.css?v=${version}" />

<!--BootStrap 框架-->
<link rel="stylesheet" type="text/css" href="plugins/bootstrap4.0.0/css/bootstrap.css?v=${version}">
<link rel="stylesheet" type="text/css" href="plugins/bootstrap4.0.0/css/bootstrap-grid.css?v=${version}">
<link rel="stylesheet" type="text/css" href="plugins/bootstrap4.0.0/css/bootstrap-reboot.css?v=${version}">
