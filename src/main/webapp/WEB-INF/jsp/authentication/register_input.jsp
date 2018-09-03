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
    <title>注册</title>
    <base href="<%=basePath%>">
    <jsp:include page="/WEB-INF/jsp/pub/head_meta.jsp"/>
    <jsp:include page="/WEB-INF/jsp/pub/head_link.jsp"/>
    <jsp:include page="/WEB-INF/jsp/pub/head_script.jsp"/>
    <!--=====================CSS_Link===========================-->
<body>
<div class="container">
    <h2 style="margin: 30px auto;">注册</h2>
    <form id="register_form" action="" method="post" autocomplete="off">
        <div class="form-group">
            <label for="email">邮箱：</label>
            <input type="email" name="userEmail" id="email" value="" placeholder="邮箱" class="form-control reg_email">
            <span class="tip email_hint"></span>
        </div>
        <div class="form-group">
            <label for="reg_user">用户名：</label>
            <input type="text" name="userName" class="form-control reg_user" id="reg_user" placeholder="4-12位用户名（数字或字母）">
            <span class="tip user_hint"></span>
        </div>
        <div class="form-group">
            <label for="pwd">密码：</label>
            <input type="password" name="userPasswd" class="form-control reg_password" id="pwd" placeholder="6-16位密码（数字或字母）">
            <span class="tip password_hint"></span>
        </div>
        <div class="form-group">
            <label for="reg_confirm">确认密码：</label>
            <input type="password" name="" class="form-control reg_confirm" id="reg_confirm" placeholder="确认密码">
            <span class="tip confirm_hint"></span>
        </div>
        <button type="button" class="btn btn-primary red_button">注册</button>
        <a href="javascript:;" class="btn" onclick="history.back();">返回</a>
    </form>
</div>
<script type="text/javascript" src="plugins/register/js/script.js?v=${v}"></script>
<script type="text/javascript">
    window.register_path = "register.html";
    window.head_page_path = "/video/";
</script>
</body>
</html>