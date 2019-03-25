<%@ page import="java.util.Date" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/jsp/pub/include.jsp" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<c:set var="prourl" value="<%=basePath%>"></c:set>
<c:set var="proname" value="${pageContext.request.contextPath}"></c:set>
<html lang="en"><head> <meta charset="UTF-8">
    <title>what?</title>
    <base href="<%=basePath%>">
    <script src="${proname}/public/static/js/download/base64.js?v=${version}">

    </script> <link href="${proname}/plugins/danmuplayer/css/scojs.css" rel="stylesheet">
    <link href="${proname}/plugins/danmuplayer/css/colpick.css" rel="stylesheet">
    <link href="${proname}/plugins/danmuplayer/css/bootstrap.css" rel="stylesheet">
    <link href="${proname}/plugins/danmuplayer/css/main.css" rel="stylesheet">
    <style>
        html, body{
            padding: 0px;
            margin: 0px;
            background-color: #fff;
            height: 100%;
        }
        .glyphicon {
            top: 2px;
            line-height: 2;
        }
    </style>
<body>
<div id="danmup"> </div>
<div style="display: none">
    <span class="glyphicon" aria-hidden="true">&#xe072</span>
    <span class="glyphicon" aria-hidden="true">&#xe073</span>
    <span class="glyphicon" aria-hidden="true">&#xe242</span>
    <span class="glyphicon" aria-hidden="true">&#xe115</span>
    <span class="glyphicon" aria-hidden="true">&#xe111</span>
    <span class="glyphicon" aria-hidden="true">&#xe096</span>
    <span class="glyphicon" aria-hidden="true">&#xe097</span>
</div>
<script src="${proname}/plugins/danmuplayer/jquery-2.1.4.min.js?v=${version}"></script>
<script src="${proname}/plugins/danmuplayer/danmuplayer.min.js?v=${version}"></script>
<script>
    var flag = true;
    $("#danmup").DanmuPlayer({
        src:window.parent.filmSrc,
        height: "93%", //区域的高度
        width: "100%" //区域的宽度
        ,urlToGetDanmu:"xl/queryBullet.html?film_id="+window.parent.filmId
        ,urlToPostDanmu:"xl/saveBullet.html?film_id="+window.parent.filmId
    });
    //进入全屏
    function FullScreen() {
        var ele = document.documentElement;
        if (ele .requestFullscreen) {
            ele .requestFullscreen();
        } else if (ele .mozRequestFullScreen) {
            ele .mozRequestFullScreen();
        } else if (ele .webkitRequestFullScreen) {
            ele .webkitRequestFullScreen();
        }
    }
    //退出全屏
    function exitFullscreen() {
        var de = document;
        if (de.exitFullscreen) {
            de.exitFullscreen();
        } else if (de.mozCancelFullScreen) {
            de.mozCancelFullScreen();
        } else if (de.webkitCancelFullScreen) {
            de.webkitCancelFullScreen();
        }
    }
    $(".full-screen").on('click',function(){
        if( flag ) {
            FullScreen();
            flag = false;
        } else {
            exitFullscreen();
            flag = true;
        }
    });
</script>
</body>
</html>