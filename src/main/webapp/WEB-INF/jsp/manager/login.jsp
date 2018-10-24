<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="f"%>
<!DOCTYPE html>
<html lang="en" class="fullscreen-bg">
<head>
    <jsp:include page="/WEB-INF/jsp/pub/head_meta.jsp"/>
    <link rel="shortcut icon" href="<f:message key='pageIcon'/>">
    <title>登录</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/plugins/bootstrap4.0.0/css/bootstrap.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/plugins/font-awesome/css/font-awesome.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/plugins/linearicons/style.css">
    <!-- MAIN CSS -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/public/static/css/manager/main.css">
    <!-- FOR DEMO PURPOSES ONLY. You should remove this in your project -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/public/static/css/manager/demo.css">
    <!-- GOOGLE FONTS -->
    <link href="https://fonts.googleapis.com/css?family=Source+Sans+Pro:300,400,600,700" rel="stylesheet">
</head>
<body style="height: 100%;">
    <div id="wrapper" style="height: 100%;">
        <div class="vertical-align-wrap">
            <div class="vertical-align-middle">
                <div class="auth-box ">
                    <div class="left">
                        <div class="content">
                            <div class="header">
                                <div class="logo text-center"><img src="assets/img/logo-dark.png" alt="Klorofil Logo"></div>
                                <p class="lead">Login to your account</p>
                            </div>
                            <form class="form-auth-small" action="/admin/login.html" method="post">
                                <div class="form-group">
                                    <label for="signin-email" class="control-label sr-only">登录名</label>
                                    <input type="email" class="form-control" id="signin-email" value="samuel.gold@domain.com" placeholder="username">
                                </div>
                                <div class="form-group">
                                    <label for="signin-password" class="control-label sr-only">密码</label>
                                    <input type="password" class="form-control" id="signin-password" value="thisisthepassword" placeholder="Password">
                                </div>
                                <div class="form-group clearfix">
                                    <label class="fancy-checkbox element-left">
                                        <input type="checkbox">
                                        <span>记住密码</span>
                                    </label>
                                </div>
                                <button type="submit" class="btn btn-primary btn-lg btn-block">登录</button>
                                <div class="bottom">
                                    <span class="helper-text"><i class="fa fa-lock"></i> <a href="#">忘记密码?</a></span>
                                </div>
                            </form>
                        </div>
                    </div>
                    <div class="right">
                        <div class="overlay"></div>
                        <div class="content text">
                            <h1 class="heading">影音播放系统后台登录</h1>
                            <p>欢迎来到影音的世界</p>
                        </div>
                    </div>
                    <div class="clearfix"></div>
                </div>
            </div>
        </div>
    </div>
</body>
</html>