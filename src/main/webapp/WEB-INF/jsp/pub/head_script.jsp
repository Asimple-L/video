<%@ page import="java.util.Date" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<c:set var="version" value="<%=new Date().getTime()%>"></c:set>
<script src="public/static/js/jquery-2.0.0.min.js?v=${version}" type="text/javascript" charset="UTF-8" ></script>
<script type="text/javascript" src="public/static/js/jquery.lazyload.min.js?v=${version}"></script>
<script src="public/static/js/base/base.js?v=${version}" type="text/javascript" charset="UTF-8" ></script>
<!--IE8只能支持jQuery1.9-->
<!--[if lte IE 8]>
<script src="http://cdn.bootcss.com/jquery/1.9.0/jquery.min.js"></script>
<![endif]-->

<!--BootStrap 框架-->
<script src="plugins/bootstrap4.0.0/js/bootstrap.bundle.js?v=${version}" type="text/javascript" charset="UTF-8" ></script>
<script src="plugins/bootstrap4.0.0/js/popper.min.js?v=${version}" type="text/javascript" charset="UTF-8" ></script>
<script src="plugins/bootstrap4.0.0/js/bootstrap.js?v=${version}" type="text/javascript" charset="UTF-8" ></script>

<%--niceScroll插件引入--%>
<script src="public/static/js/base/jquery.nicescroll.js?v=${version}" type="text/javascript" charset="UTF-8" ></script>