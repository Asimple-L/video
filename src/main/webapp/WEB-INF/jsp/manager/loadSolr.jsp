<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/jsp/pub/include.jsp" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<c:set var="version" value="<%=new Date().getTime()%>"></c:set>
<c:set var="prourl" value="<%=basePath%>"></c:set>
<c:set var="proname" value="${pageContext.request.contextPath}"></c:set>
<html>
<head>
    <base href="<%=basePath%>">
    <title>数据导入</title>
    <link rel="shortcut icon" href="${pageIcon}">
    <link rel="stylesheet" href="${proname}/plugins/bootflat-admin/css/site.min.css">
    <script src="${proname}/public/static/js/jquery-2.0.0.min.js?v=${version}" type="text/javascript" charset="UTF-8" ></script>
    <script src="${proname}/plugins/bootflat-admin/js/site.min.js"></script>
</head>
<body>
<jsp:include page="header.jsp"/>

<div class="container-fluid">
    <div class="row row-offcanvas row-offcanvas-left">
        <jsp:include page="navLeft.jsp"/>
        <div class="col-xs-12 col-sm-9 content">
            <div class="panel panel-default">
                <div class="panel-heading">
                    <h3 class="panel-title">
                        <a href="javascript:void(0);" class="toggle-sidebar">
                            <span class="fa fa-angle-double-left" data-toggle="offcanvas" title="Maximize Panel"></span>
                        </a>
                        后台管理
                    </h3>
                </div>
                <div class="panel-body">
                    <div class="content-row">
                        <div class="row">
                            <div class="text-center">
                                <span class="btn btn-primary" onclick="loadIn(false)">一键导入</span>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<script src="${proname}/public/static/js/mine/backHeader.js"></script>
<script>
    function loadIn(flag) {
        if( flag ) {
            $.ajax({
                url: "/video/admin/loadIn",
                type: "POST",
                dataType: "json",
                success:function (data) {
                    if ( typeof data == "string" ) data = JSON.parse(data);
                    if( data.code=="1" ) {
                        alert("导入数据成功~~");
                    } else {
                        alert("导入出错~");
                    }
                }
            });
        } else {
            alert("功能禁用!");
        }
    }
</script>
</body>
</html>
