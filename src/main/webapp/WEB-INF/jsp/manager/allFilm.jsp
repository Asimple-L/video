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
    <title>所有影片</title>
    <link rel="shortcut icon" href="<f:message key='pageIcon'/>">
    <link rel="stylesheet" href="${proname}/plugins/bootflat-admin/css/site.min.css">
    <link rel="stylesheet" href="${proname}/public/static/css/manager/allFilm.css">
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
                        影片列表
                    </h3>
                </div>
                <div class="panel-body">
                    <div class="content-row">
                        <div class="row">

                            <%-- 正文开始 --%>
                                    <div style="margin:0px auto;width:100%;"></div>
                                    <div class="search-box">
                                        <form action="/video/admin/list.html" method="post">
                                            <input type="search" style="width: 20%;display: inline;" class="form-control search-query" name="name" value="${name}" autocomplete="off"
                                                   placeholder="请输入影片名称"/>
                                        </form>
                                    </div>
                                    <div style="width:100%;margin: 3px auto;">
                                        <ul class="film-list">
                                            <c:forEach items="${pb.beanList}" var="list">
                                                <li>
                                                    <a href="admin/film.html?film_id=${list.id}">
                                                        <div title="${list.name}"><img src="${list.image}"></div>
                                                    </a>
                                                    <div class="film-info">
                                                        <a href="admin/film.html?film_id=${list.id}" title="${list.name}">${list.name}</a>
                                                        <p>${list.onDecade}-${list.typeName}</p>
                                                    </div>
                                                </li>
                                            </c:forEach>
                                        </ul>
                                    </div>
                                    <div style="width:80%;margin: 30px auto;height: auto;overflow: hidden;">
                                        <c:if test="${pb.tr!=0}">
                                            <p style="text-align: center;">
                                                <c:set var="pageurl" value="/video/admin/list.html"/>   <!--1.修改-->
                                                <script type="text/javascript">
                                                    function _change() {
                                                        var select = document.getElementById("select");
                                                        location = "${pageurl}?${pb.url}&pc=" + select.value;
                                                    }
                                                </script>

                                                第${pb.pc }页/共${pb.tp }页
                                                <a href="${pageurl}?${pb.url }&pc=1">首页</a>
                                                <c:if test="${pb.pc > 1 }">
                                                    <a href="${pageurl}?${pb.url }&pc=${pb.pc-1 }">上一页</a>
                                                </c:if>

                                                <c:if test="${pb.pc == 1 }">
                                                    <a href="javascript:;">上一页</a>
                                                </c:if>


                                                    <%------------------------------------ --%>
                                                    <%-- 页码列表的长度自己定，10个长 --%>
                                                <c:choose>
                                                    <%-- 第一条：如果总页数<=10，那么页码列表为1 ~ tp --%>
                                                    <c:when test="${pb.tp <= 10 }">
                                                        <c:set var="begin" value="1"/>
                                                        <c:set var="end" value="${pb.tp }"/>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <%-- 第二条：按公式计算，让列表的头为当前页-4；列表的尾为当前页+5 --%>
                                                        <c:set var="begin" value="${pb.pc-4 }"/>
                                                        <c:set var="end" value="${pb.pc+5 }"/>

                                                        <%-- 第三条：第二条只适合在中间，而两端会出问题。这里处理begin出界！ --%>
                                                        <%-- 如果begin<1，那么让begin=1，相应end=10 --%>
                                                        <c:if test="${begin<1 }">
                                                            <c:set var="begin" value="1"/>
                                                            <c:set var="end" value="10"/>
                                                        </c:if>
                                                        <%-- 第四条：处理end出界。如果end>tp，那么让end=tp，相应begin=tp-9 --%>
                                                        <c:if test="${end>pb.tp }">
                                                            <c:set var="begin" value="${pb.tp-9 }"/>
                                                            <c:set var="end" value="${pb.tp }"/>
                                                        </c:if>
                                                    </c:otherwise>
                                                </c:choose>

                                                    <%-- 循环显示页码列表 --%>
                                                <c:forEach begin="${begin }" end="${end }" var="i">
                                                    <c:choose>
                                                        <c:when test="${i eq pb.pc }">${i }</c:when>
                                                        <c:otherwise>
                                                            <a href="${pageurl}?${pb.url }&pc=${i}">[${i }]</a>
                                                        </c:otherwise>
                                                    </c:choose>
                                                </c:forEach>
                                                <c:if test="${pb.pc < pb.tp }"><a href="${pageurl}?${pb.url }&pc=${pb.pc+1 }">下一页</a></c:if>
                                                <c:if test="${pb.pc == pb.tp }"><a href="javascript:;">下一页</a></c:if>
                                                <a href="${pageurl}?${pb.url}&pc=${pb.tp}">尾页</a>　
                                                <select name="pc" onchange="_change()" id="select">
                                                    <c:forEach begin="1" end="${pb.tp }" var="i">
                                                        <option value="${i }"
                                                                <c:if test="${i eq pb.pc }">selected="selected"</c:if> >${i}</option>
                                                    </c:forEach>
                                                </select>
                                                &nbsp;总共${pb.tr}部　
                                            </p>

                                        </c:if>
                                    </div>

                            <%-- 正文结束 --%>

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
