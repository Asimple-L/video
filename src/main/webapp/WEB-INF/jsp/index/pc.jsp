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
<html lang="en"><head>
    <meta charset="UTF-8">
    <title>what?</title>
    <base href="<%=basePath%>">
    <script src="public/static/js/download/base64.js?v=${version}"></script>
    <style>
        html,body{
            padding: 0px;
            margin: 0px;
            background-color: #000;
            height: 100%;
        }
        #video{
            width: 100%;
            height: 100%;
        }
    </style>
<body>
<script type="text/javascript" src="plugins/ckplayer/ckplayer.js?v=${version}"></script>
<div id="video"></div>
<script type="text/javascript">
    var videoObject = {
        container: '#video',//“#”代表容器的ID，“.”或“”代表容器的class
        variable: 'player',//该属性必需设置，值等于下面的new chplayer()的对象
        poster:null,//封面图片
        video:window.parent.filmSrc//视频地址
    };
    var player=new ckplayer(videoObject);
</script>
</body>
</html>