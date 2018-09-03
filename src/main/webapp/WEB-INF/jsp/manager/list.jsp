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
    <title>所有影片</title>
    <jsp:include page="/WEB-INF/jsp/pub/head_meta.jsp"/>
    <jsp:include page="/WEB-INF/jsp/pub/head_link.jsp"/>
    <jsp:include page="/WEB-INF/jsp/pub/head_script.jsp"/>
    <!--=====================CSS_Link===========================-->

    <!--=====================JS_Link===========================-->
    <style>
        * {
            margin: 0px;
            padding: 0px;
            font-size: 12px;
            font-family: '微软雅黑';
        }

        a {
            text-decoration: none;
            color: #444;
        }

        /*影片列表*/
        .film-list {
            width: 98%;
            height: auto;
            padding: 1%;
            overflow: hidden;
            border-top: 1px solid #ddd;
            border-bottom: 1px solid #ddd;
        }

        .film-list li {
            list-style: none;
            float: left;
            width: 104px;
            height: 190px;
            margin: 3px;
        }

        .film-list li img {
            width: 86px;
            height: 122px;
            padding: 4px;
            border: 1px solid #ddd;
        }

        .film-info {
            width: 98%;
            padding: 1%;
            height: auto;
            overflow: hidden;
            text-overflow: ellipsis;
            white-space: nowrap;
            text-align: center;
        }

        .film-info a {
            color: #1d94ff;
        }

        .film-info p {
            color: #A9A8A8;
        }

        /*影片列表*/
        .search-box {
            width: 100%;
            height: auto;
            text-align: right;
            margin: 70px auto 0px auto;
        }

        /*box框架*/
        .box {
            width: 1190px;
            height: auto;
            overflow: hidden;
            margin: 0px auto;
        }

        .box-left {
            width: 290px;
            height: auto;
            float: left;
            overflow: hidden;
        }

        .box-right {
            width: 900px;
            height: auto;
            float: right;
            overflow: hidden;
        }

        /*box框架*/
        /*导航*/
        .nav-title {
            width: 100%;
            height: 30px;
            overflow: hidden;
            line-height: 30px;
            font-size: 12px;
            font-family: '微软雅黑';
            cursor: pointer;

        }

        .nav-icon {
            font-style: normal;
            height: 100%;
            width: 30px;
            display: inline-block;
            background: #fff url("/public/static/img/pub/nav.jpg") -144px -27px no-repeat; /*-143px -27px*/
        }

        .nav1-ul {
            width: 60%;
            height: auto;
            overflow: hidden;
            margin: 0px auto;
        }

        .nav1-li {
            width: 100%;
            height: auto;
            overflow: hidden;
        }

        .nav2-ul {
            width: 100%;
            height: auto;
            overflow: hidden;
        }

        .nav2-li {
            width: 100%;
            height: 30px;
            overflow: hidden;
            line-height: 30px;
            text-align: center;
        }

        /*导航*/
    </style>

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
        })
    </script>
    <%--导航--%>
</head>
<body>

<div class="container">
    <div class="row">
        <div class="col-sm-3">

            <%--导航--%>
                <jsp:include page="navigate.jsp"/>
            <%--导航--%>
        </div>
        <div class="col-sm-9">
        <div style="margin:0px auto;width:100%;"></div>
        <div class="search-box">
            <form action="list.html" method="post">
                <input type="search" name="name" value="${name}" autocomplete="off"
                                                   placeholder="请输入影片名称"/>
            </form>
        </div>
        <div style="width:100%;margin: 3px auto;">
            <ul class="film-list">
                <c:forEach items="${pb.beanList}" var="list">
                    <li>
                        <a href="film.html?film_id=${list.id}">
                            <div title="${list.name}"><img src="${list.image}"></div>
                        </a>
                        <div class="film-info">
                            <a href="film.html?film_id=${list.id}" title="${list.name}">${list.name}</a>
                            <p>${list.onDecade}-${list.typeName}</p>
                        </div>
                    </li>
                </c:forEach>
            </ul>
        </div>
        <div style="width:80%;margin: 100px auto;height: auto;overflow: hidden;">
            <!--

                需要修改 1 处地方

            -->
            <c:if test="${pb.tr!=0}">
                <p style="text-align: center;">
                    <c:set var="pageurl" value="list.html"/>   <!--1.修改-->
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
    </div>
    </div>
</div>
</body>
</html>
