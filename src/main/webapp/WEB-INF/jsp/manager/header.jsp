<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<nav role="navigation" class="navbar navbar-custom">
    <div class="container-fluid">
        <div class="navbar-header">
            <button data-target="#bs-content-row-navbar-collapse-5" data-toggle="collapse" class="navbar-toggle" type="button">
                <span class="sr-only">菜单</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a href="${pageContext.request.contextPath}/admin/index.html" class="navbar-brand">影音播放系统后台管理</a>
        </div>

        <div id="bs-content-row-navbar-collapse-5" class="collapse navbar-collapse">
            <ul class="nav navbar-nav navbar-right">
                <li class="dropdown">
                    <a data-toggle="dropdown" class="dropdown-toggle" href="#">${sessionScope.adminUser.userName}<b class="caret"></b></a>
                    <ul role="menu" class="dropdown-menu">
                        <li class="dropdown-header">菜单</li>
                        <li><a href="#">个人中心</a></li>
                        <li id="logout"><a href="javascript:void(0);">退出登录</a></li>
                    </ul>
                </li>
            </ul>
        </div>
    </div>
</nav>
