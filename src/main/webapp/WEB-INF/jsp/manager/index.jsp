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
    <title>后台首页</title>
    <link rel="shortcut icon" href="<f:message key='pageIcon'/>">
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
                            后台管理首页
                        </h3>
                    </div>
                    <div class="panel-body">
                        <div class="content-row">
                            <div class="row">
                                <div class="col-md-2"></div>
                                <div class="col-md-8">
                                    <div class="pricing">
                                        <ul>
                                            <li class="unit price-primary">
                                                <div class="price-title">
                                                    <h3>目录管理</h3>
                                                </div>
                                                <div class="price-body">
                                                    <h4>Basic</h4>
                                                    <p>Lots of clients &amp; users</p>
                                                    <ul>
                                                        <li>250 SKU's</li>
                                                        <li>1 GB Storage</li>
                                                        <li>3,5% transaction fee</li>
                                                    </ul>
                                                </div>
                                                <div class="price-foot">
                                                    <a href="${proname}/admin/index.html">
                                                        <button type="button" class="btn btn-primary">前去目录管理</button>
                                                    </a>
                                                </div>
                                            </li>
                                            <li class="unit price-success active">
                                                <div class="price-title">
                                                    <h3>影音管理</h3>
                                                </div>
                                                <div class="price-body">
                                                    <h4>Premium</h4>
                                                    <p>Lots of clients &amp; users</p>
                                                    <ul>
                                                        <li>2500 SKU's</li>
                                                        <li>5 GB Storage</li>
                                                        <li>1,5% transaction fee</li>
                                                    </ul>
                                                </div>
                                                <div class="price-foot">
                                                    <a href="${proname}/admin/index.html">
                                                        <button type="button" class="btn btn-success">前去影音管理</button>
                                                    </a>
                                                </div>
                                            </li>
                                            <li class="unit price-warning">
                                                <div class="price-title">
                                                    <h3>用户管理</h3>
                                                </div>
                                                <div class="price-body">
                                                    <h4>Unlimited</h4>
                                                    <p>Lots of clients &amp; users</p>
                                                    <ul>
                                                        <li>Unlimited SKU's</li>
                                                        <li>20 GB Storage</li>
                                                        <li>1% transaction fee</li>
                                                    </ul>
                                                </div>
                                                <div class="price-foot">
                                                    <a href="${proname}/admin/index.html">
                                                        <button type="button" class="btn btn-warning">前去用户管理</button>
                                                    </a>
                                                </div>
                                            </li>
                                        </ul>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>


    <script src="${proname}/public/static/js/mine/backHeader.js"></script>
</body>
</html>
