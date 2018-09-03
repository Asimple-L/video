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
    <title><f:message key="siteName"/></title>
    <jsp:include page="/WEB-INF/jsp/pub/head_meta.jsp"/>
    <jsp:include page="/WEB-INF/jsp/pub/head_link.jsp"/>
    <jsp:include page="/WEB-INF/jsp/pub/head_script.jsp"/>
    <!--=====================CSS_Link===========================-->
    <%--右侧排行推荐--%>
    <link href="plugins/paihang/css/lanrenzhijia.css" type="text/css" rel="stylesheet" />
    <link href="public/static/css/index/index.css?v=${version}" type="text/css" rel="stylesheet" />

    <!--=====================JS_Link===========================-->
    <script type="text/javascript">
        function updateFilmSize() {
            var w = $(".col-sm-8").width() / 6;
            var isLi = false;
            if ($(".col-sm-8").width() < 500) {
                w = $(".col-sm-8").width() / 3;
                isLi = true;
            }
            w = parseInt(w - 0.5);
            $(".mox ul li").css("width", w + "px");
            var h = (w * 160) / 115;
            $(".t_img").css("height", h + "px");
            if(isLi){
                $(".case").css("height", "auto");
                $(".case").css({"overflow":"hidden"});
            }else {
                $(".case").each(function (index,ele) {
                    $(ele).css("height", ($(".mox").eq(index).height()) + "px");
                });
            }
        }

        $(document).ready(function(){
            $(".case ul li a").each(function(i){
                $(this).hover(function(){
                    $(this).parent().find(".tips").addClass("hover");
                    $(this).parent().addClass("lihover");
                },function(){
                    $(this).parent().find(".tips").removeClass("hover");
                    $(this).parent().removeClass("lihover");
                });
            });
            updateFilmSize();
            $(window).resize(function(){
                updateFilmSize();
            });
        });
    </script>
</head>
<body>
<jsp:include page="/WEB-INF/jsp/pub/head.jsp"/>
<div class="container" style="margin-top: 50px;">
    <c:if test="${u_skl==null}">
        <div class="row">
            <div class="col">
                <marquee  onmouseover='this.stop()' onmouseout='this.start()'><a id="globalLoginBtn_register_input" href="registerInput.html">注册</a>|<a data-toggle="modal" data-target="#myModal" href="javascript:;">登录</a>后观看更多高清无码视频</marquee>
            </div>
        </div>
    </c:if>
    <div class="row">
        <div class="col-sm-8">
            <c:if test="${filmTuijian.size()!=0}">
                <c:forEach items="${filmTuijian}" var="list">
                    <div class="mox">
                        <h5>最新${list.get(0).cataLogName}推荐<span style="margin-right:10px;cursor:pointer;float: right;" onclick="location.href='xl/1.html?cataLog_id=${list.get(0).cataLog_id}'">更多</span></h5>
                        <ul>
                            <c:forEach items="${list}" var="li">
                                <li class="float-left">
                                    <a href="xl/detail.html?film_id=${li.id}"><div class="t_img" title="${li.name}"><img class="lazy rounded img-fluids" data-original="${li.image}"/></div></a>
                                    <div class="t_info">
                                        <p style="color:#00AFE4;overflow: hidden;white-space: nowrap;text-overflow: ellipsis;">${li.name}</p>
                                        <p>${li.typeName}-${li.onDecade}</p>
                                    </div>
                                </li>
                            </c:forEach>
                        </ul>
                    </div>
                </c:forEach>
            </c:if>
        </div>
        <div class="col-sm-4">
            <c:if test="${filmPaiHang.size()!=0}">
                <c:forEach items="${filmPaiHang}" var="list">
                    <div class="case">
                        <ul>
                            <h5>最新${list.get(0).cataLogName}排行榜</h5>
                            <c:forEach items="${list}" var="li" varStatus="s">
                                <li><c:if test="${s.index<=2}"><span class="ph1">${s.index+1}</span></c:if><c:if test="${s.index>2}"><span class="ph">${s.index+1}</span></c:if> <a href="xl/detail.html?film_id=${li.id}" target="_blank" title="${li.name}">${fn:substring(li.name,0,16)}<span style="float: right;margin-right: 10px;">${fn:substring(li.updateTime,5,10)}</span></a>

                                    <div class="tips">
                                        <div class="tipscont"><img src="${li.image}" style="border:1px solid #ddd;padding: 4px;background: white;" width="200px" height="288px" alt="${li.name}"/></div>
                                    </div>
                                </li>
                            </c:forEach>
                        </ul>
                    </div>

                </c:forEach>
            </c:if>
        </div>
    </div>
</div>
<jsp:include page="/WEB-INF/jsp/pub/footer.jsp"/>
</body>
</html>
