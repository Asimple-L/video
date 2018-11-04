<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="col-xs-6 col-sm-3 sidebar-offcanvas" role="navigation">
    <ul class="list-group panel">
        <li class="list-group-item"><a href="/video/admin/index.html"><i class="glyphicon glyphicon-home"></i>首页</a></li>
        <li>
            <a href="#video_set" class="list-group-item " data-toggle="collapse"><i class="glyphicon glyphicon-film"></i>影音管理<span class="glyphicon glyphicon-chevron-right"></span></a>
            <div class="collapse" id="video_set">
                <a href="/video/admin/film.html" class="list-group-item">添加影视</a>
                <a href="/video/admin/list.html" class="list-group-item">所有影片</a>
            </div>
        </li>
        <li>
            <a href="#dir_set" class="list-group-item " data-toggle="collapse"><i class="glyphicon glyphicon-tasks"></i>目录管理<span class="glyphicon glyphicon-chevron-right"></span></a>
            <li class="collapse" id="dir_set">
                <a href="javascript:void(0);" class="list-group-item">添加目录</a>
                <a href="javascript:void(0);" class="list-group-item">所有目录</a>
            </li>
        </li>
        <li>
            <a href="#user_set" class="list-group-item " data-toggle="collapse"><i class="glyphicon glyphicon-user"></i>用户管理<span class="glyphicon glyphicon-chevron-right"></span></a>
            <li class="collapse" id="user_set">
                <a href="javascript:void(0);" class="list-group-item">添加用户</a>
                <a href="javascript:void(0);" class="list-group-item">所有用户</a>
                <a href="javascript:void(0);" class="list-group-item">VIP管理</a>
            </li>
        </li>
    </ul>
</div>