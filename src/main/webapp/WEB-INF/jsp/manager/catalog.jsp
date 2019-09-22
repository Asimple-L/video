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
    <title>目录添加</title>
    <link rel="shortcut icon" href="${pageIcon}">
    <link rel="stylesheet" href="${proname}/plugins/bootflat-admin/css/site.min.css">
    <link rel="stylesheet" href="${proname}/public/static/css/manager/addCatalog.css">
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
                        目录添加
                    </h3>
                </div>
                <div class="panel-body">
                    <div class="content-row">
                        <div class="row">
                            <div style="width:50%;float:left;height: auto;overflow: hidden;">
                                <span>一级分类</span><br/>
                                <div contenteditable="true" class="catalog_name" style="display: inline-block;"></div>
                                <a href="javascript:;" class="add_CataLog_btn btn">添加</a><br/><br/>

                                <span>二级子分类</span>
                                <select class="cataLog_id">
                                    <c:forEach items="${cataLogList}" var="cataLogList_var">
                                        <option value="${cataLogList_var.id}">${cataLogList_var.name}</option>
                                    </c:forEach>

                                </select><br/>
                                <div contenteditable="true" class="subClass_name" style="display: inline-block;"></div>
                                <a href="javascript:;" class="add_SubClass_btn btn">添加</a><br/><br/>

                                <span>类型</span><br/>
                                <select class="cataLog_id_subClass">
                                    <c:forEach items="${cataLogList}" var="cataLogList_var">
                                        <option value="${cataLogList_var.id}">${cataLogList_var.name}</option>
                                    </c:forEach>
                                </select>-
                                <select class="subClass_id"></select><br/>
                                <div contenteditable="true" class="type_name" style="display: inline-block;"></div>
                                <a href="javascript:;" class="add_Type_btn btn">添加</a><br/><br/>
                                <br/>

                                <%--<h2><span style="color:blue;">一级目录</span>--><span style="color:green;">二级目录</span>--><span style="color:#fd5c11;">类型</span></h2>--%>
                                <%--<c:forEach items="${cataLogList}" var="list">--%>
                                    <%--<span style="color:blue;">${list.name}</span><br/>--%>
                                    <%--<c:forEach items="${list.subClassList}" var="li">--%>
                                        <%--&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span style="color:green;">${li.name}</span>-->--%>
                                        <%--<c:forEach items="${typeList}" var="l">--%>
                                            <%--<c:if test="${li.id==l.subClass.id}">--%>
                                                <%--<span style="color:#fd5c11;">${l.name}</span>--%>
                                            <%--</c:if>--%>
                                        <%--</c:forEach>--%>
                                        <%--<br/>--%>
                                    <%--</c:forEach>--%>
                                    <%--<br/>--%>
                                <%--</c:forEach>--%>
                            </div>
                            <div style="width:50%;float:left;height: auto;overflow: hidden;">


                                <span>地区</span><br/>
                                <div contenteditable="true" class="loc_name" style="display: inline-block;"></div>
                                <a href="javascript:;" class="add_Loc_btn btn" >添加</a><br/><br/><br/>


                                <span>级别</span><br/>
                                <div contenteditable="true" class="level_name" style="display: inline-block;"></div>
                                <a href="javascript:;" class="add_Level_btn btn">添加</a><br/><br/><br/>


                                <span>年代</span><br/>
                                <div contenteditable="true" class="decade_name" style="display: inline-block;"></div>
                                <a href="javascript:;" class="add_Decade_btn btn">添加</a><br/><br/>


                                <%--<h2>地区-->级别-->年代</h2>--%>
                                <%--<br/>--%>
                                <%--<span>地区--></span>--%>
                                <%--<c:forEach items="${locList}" var="locList_list">--%>
                                    <%--${locList_list.name}--%>
                                <%--</c:forEach>--%>
                                <%--<br/>--%>
                                <%--<span>级别--></span>--%>
                                <%--<c:forEach items="${levelList}" var="levelList_list">--%>
                                    <%--${levelList_list.name}--%>
                                <%--</c:forEach>--%>
                                <%--<br/>--%>

                                <%--<span>年代--></span>--%>
                                <%--<c:forEach items="${decadeList}" var="decadeList_list">--%>
                                    <%--${decadeList_list.name}--%>
                                <%--</c:forEach>--%>
                                <%--<br/>--%>

                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>


<script src="${proname}/public/static/js/mine/backHeader.js"></script>
<script src="${proname}/public/static/js/mine/addCatalog.js"></script>
</body>
</html>

