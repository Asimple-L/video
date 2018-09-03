<%@ page import="java.util.Date" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/jsp/pub/include.jsp" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<c:set var="prourl" value="<%=basePath%>"></c:set>
<c:set var="proname" value="${pageContext.request.contextPath}"></c:set>
<!DOCTYPE HTML>
<html>
<head>
    <base href="<%=basePath%>">
    <title>VIP管理</title>
    <jsp:include page="/WEB-INF/jsp/pub/head_meta.jsp"/>
    <jsp:include page="/WEB-INF/jsp/pub/head_link.jsp"/>
    <jsp:include page="/WEB-INF/jsp/pub/head_script.jsp"/>
    <link rel="stylesheet" href="public/static/css/manager/film.css?v=${version}" type="text/css">
    <%--导航--%>
    <script>
        $(function () {
            $(".nav-title").click(function () {
                $(this).parent().find(".nav2-ul").toggle();
                if ($(this).find(".nav-icon").css("background-position") == "-144px -27px") {
                    $(this).find(".nav-icon").css("background-position", "-92px -27px");
                } else {
                    $(this).find(".nav-icon").css("background-position", "-144px -27px");
                }
            });
        });
    </script>
    <%--导航--%>
</head>
<body>

<!--内容 START-->
<div class="box">
    <div class="box-left">
        <!--引入导航JSP文件 -->
        <jsp:include page="navigate.jsp"/>
    </div>
    <div class="box-right">
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
<!--内容 END-->

<script type="text/javascript">
    $(function () {
        $("#create_code").click(function () {
            $.ajax({
                url:"createVipCode.html",
                type:"POST",
                dataType:"json",
                data:{
                    num:5
                },
                success:function (data) {
                    data = JSON.parse(data);
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
