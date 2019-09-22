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
    <title>VIP管理</title>
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
                        VIP管理
                    </h3>
                </div>
                <div class="panel-body">
                    <div class="content-row">
                        <div class="row">
                            <div style="width:100%; height: auto;overflow:hidden;margin: 0px auto;">
                                <h5>可使用的卡号：</h5>
                                <table class="table table-bordered  table-striped">
                                    <thead class="thead-light">
                                    <tr>
                                        <th>加油卡号</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <c:forEach items="${vip_codes}" var="list">
                                        <tr>
                                            <td>${list.code}</td>
                                        </tr>
                                    </c:forEach>
                                    </tbody>
                                </table>
                                <h5>一键生成加油卡号：</h5><a class="btn" id="create_code">生成</a><br/><br/>
                                <div>
                                    <table class="table table-bordered  table-striped">
                                        <thead class="thead-light">
                                        <tr>
                                            <th>加油卡号</th>
                                        </tr>
                                        </thead>
                                        <tbody id="create_code_new"></tbody>
                                    </table>
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
<script type="text/javascript">
    $(function () {
        $("#create_code").click(function () {
            $.ajax({
                url:"admin/createVipCode",
                type:"POST",
                dataType:"json",
                data:{
                    num:5
                },
                success:function (data) {
                    if (typeof data == "string") data = JSON.parse(data);
                    if(data.code=="1"){
                        alert("生成加油卡成功");
                        for(var i=0;i<data.data.length;i++){
                            var html = [];
                            html.push("<tr>");
                            html.push("<td>");
                            html.push(data.data[i].code);
                            html.push("</td>");
                            html.push("</tr>");
                            $("#create_code_new").append(html.join(''));
                        }

                    }else{
                        alert("生成加油卡失败！加油卡号不存在");
                    }
                }
            });
        });
    })
</script>
</body>
</html>
