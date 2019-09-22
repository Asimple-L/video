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
    <title>用户管理</title>
    <link rel="shortcut icon" href="${pageIcon}">
    <link rel="stylesheet" href="${proname}/plugins/bootflat-admin/css/site.min.css">
    <script src="${proname}/public/static/js/jquery-2.0.0.min.js?v=${version}" type="text/javascript" charset="UTF-8" ></script>
    <script src="${proname}/plugins/bootflat-admin/js/site.min.js"></script>
    <style>
        label.toggle span.handle {
            top: 0px;
        }
        table tr td {
            vertical-align: middle!important;
            text-align: center;
        }
    </style>
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
                        所有用户
                    </h3>
                </div>
                <div class="panel-body">
                    <div class="content-row">
                        <div class="row">

                            <%-- 正文开始 --%>
                            <c:if test="${pb != null && pb.beanList != null}">
                                <table class="table table-bordered table-hover">
                                    <thead>
                                        <tr>
                                            <th>编号</th>
                                            <th>用户名</th>
                                            <th>邮箱</th>
                                            <th>VIP</th>
                                            <th>VIP到期时间</th>
                                            <th>管理员</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach items="${pb.beanList}" var="item" varStatus="index">
                                            <tr>
                                                <td>${index.index+1}</td>
                                                <td>${item.userName}</td>
                                                <td>${item.userEmail}</td>
                                                <td>
                                                    <c:choose>
                                                        <c:when test="${item.isVip==0}">
                                                            <label class="toggle">
                                                                <input type="checkbox">
                                                                <span class="handle" data="${item.id}" onclick="changeInfo(this,'vip')"></span>
                                                            </label>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <label class="toggle">
                                                                <input type="checkbox" checked>
                                                                <span class="handle" data="${item.id}" onclick="changeInfo(this,'vip')"></span>
                                                            </label>
                                                        </c:otherwise>
                                                    </c:choose>
                                                </td>
                                                <td>
                                                    <c:choose>
                                                        <c:when test="${item.expireDate==null || item.expireDate==''}">
                                                            用户未开通过VIP
                                                        </c:when>
                                                        <c:otherwise>
                                                            <f:formatDate value="${item.expireDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
                                                        </c:otherwise>
                                                    </c:choose>
                                                </td>
                                                <td>
                                                    <c:choose>
                                                        <c:when test="${item.isManager==0}">
                                                            <label class="toggle">
                                                                <input type="checkbox">
                                                                <span class="handle" data="${item.id}" onclick="changeInfo(this,'manager')"></span>
                                                            </label>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <label class="toggle">
                                                                <input type="checkbox" checked>
                                                                <span class="handle" data="${item.id}" onclick="changeInfo(this,'manager')"></span>
                                                            </label>
                                                        </c:otherwise>
                                                    </c:choose>
                                                </td>
                                            </tr>
                                        </c:forEach>
                                    </tbody>
                                </table>
                                <div class="row">
                                    <div class="col-md-4"></div>
                                    <div class="col-md-8">
                                        <ul class="pagination">
                                            <li><a href="admin/userList?pc=1">首页</a></li>
                                            <c:choose>
                                                <c:when test="${pb.pc!=pb.tp}">
                                                    <li class="active">
                                                        <a href="admin/userList?pc=${pb.pc-1}">上一页</a>
                                                    </li>
                                                </c:when>
                                                <c:otherwise>
                                                    <li class="disabled"><a href="javascript:void(0);">上一页</a></li>
                                                </c:otherwise>
                                            </c:choose>
                                            <c:if test="${pb.pc-2>0}">
                                                <li><a href="admin/userList?pc=${pb.pc-2}">${pb.pc-2}</a></li>
                                            </c:if>
                                            <c:if test="${pb.pc-1>0}">
                                                <li><a href="admin/userList?pc=${pb.pc-1}">${pb.pc-1}</a></li>
                                            </c:if>
                                            <li class="active"><a href="javascript:void(0);">${pb.pc}</a></li>
                                            <c:if test="${pb.pc+1<=pb.tp}">
                                                <li><a href="admin/userList?pc=${pb.pc+1}">${pb.pc+1}</a></li>
                                            </c:if>
                                            <c:if test="${pb.pc+2<=pb.tp}">
                                                <li><a href="admin/userList?pc=${pb.pc+2}">${pb.pc+2}</a></li>
                                            </c:if>
                                            <c:if test="${pb.pc+2<pb.tp}">
                                                <li><a href="javascript:void(0);">...</a></li>
                                                <li><a href="dmin/userList?pc=${pb.tp}">${pb.tp}</a></li>
                                            </c:if>
                                            <c:choose>
                                                <c:when test="${pb.pc!=pb.tp}">
                                                    <li class="active"><a href="admin/userList?pc=${pb.pc+1}">下一页</a></li>
                                                </c:when>
                                                <c:otherwise>
                                                    <li class="disabled"><a href="javascript:void(0);">下一页</a></li>
                                                </c:otherwise>
                                            </c:choose>
                                            <li><a href="admin/userList?pc=${pb.tp}">尾页</a></li>
                                        </ul>
                                    </div>
                                </div>
                            </c:if>


                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>


<script src="${proname}/public/static/js/mine/backHeader.js"></script>
<script>
    function changeInfo(obj, context) {
        var uid = $(obj).attr("data");
        $.ajax({
           url:'admin/updateUser',
           type:"POST",
           data:{
               uid: uid,
               key: context
           },
           success:function (data) {
               if( typeof data == "string" ) data = JSON.parse(data);
               if( data.code == "0" ) alert("修改失败！");
               else alert("修改成功！");
               location.reload();
           },
            error:function () {
                alert("系统繁忙，请稍后重试！");
                location.reload();
            }
        });
    }
</script>
</body>
</html>
