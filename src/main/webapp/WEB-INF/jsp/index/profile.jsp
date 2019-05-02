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
    <title>个人中心</title>
    <jsp:include page="/WEB-INF/jsp/pub/head_meta.jsp"/>
    <jsp:include page="/WEB-INF/jsp/pub/head_link.jsp"/>
    <script src="${pageContext.request.contextPath}/public/static/js/jquery-2.0.0.min.js?v=${version}" type="text/javascript" charset="UTF-8" ></script>
    <script src="${pageContext.request.contextPath}/plugins/bootflat-admin/js/bootstrap.min.js"></script>
    <script src="${pageContext.request.contextPath}/plugins/jquery-slimscroll/jquery.slimscroll.min.js"></script>
    <script src="${pageContext.request.contextPath}/plugins/bootflat-admin/js/klorofil-common.js"></script>
    <script>
        function dateFtt(fmt,date) {
            var o = {
                "M+" : date.getMonth()+1,                 //月份
                "d+" : date.getDate(),                    //日
                "h+" : date.getHours(),                   //小时
                "m+" : date.getMinutes(),                 //分
                "s+" : date.getSeconds(),                 //秒
                "q+" : Math.floor((date.getMonth()+3)/3), //季度
                "S"  : date.getMilliseconds()             //毫秒
            };
            if(/(y+)/.test(fmt))
                fmt=fmt.replace(RegExp.$1, (date.getFullYear()+"").substr(4 - RegExp.$1.length));
            for(var k in o)
                if(new RegExp("("+ k +")").test(fmt))
                    fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length)));
            return fmt;
        }
    </script>
    <!--=====================CSS_Link===========================-->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/plugins/font-awesome/css/font-awesome.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/plugins/linearicons/style.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/public/static/css/manager/main.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/public/static/css/index/profile.css">
    <link href="https://fonts.googleapis.com/css?family=Source+Sans+Pro:300,400,600,700" rel="stylesheet">
    <style>
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
            height: 220px;
            margin: 6px 36px 6px 6px;
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
        .pager li {
            display: inline-block;
        }
        .pager li>a, .pager li>span {
            display: inline-block;
            padding: 5px 14px;
            background-color: #fff;
            border: 1px solid #ddd;
            border-radius: 15px;
        }
        .pager .disabled>a, .pager .disabled>a:focus, .pager .disabled>a:hover, .pager .disabled>span {
            color: #777;
            cursor: not-allowed;
            background-color: #fff;
        }
    </style>
</head>
<body>
    <jsp:include page="/WEB-INF/jsp/pub/head.jsp"/>
    <!-- WRAPPER -->
    <div id="wrapper">
        <!-- MAIN -->
        <div class="main" style="float: none;width: 100%;">
            <!-- MAIN CONTENT -->
            <div class="main-content">
                <div class="container-fluid">
                    <div class="panel panel-profile">
                        <div class="clearfix">
                            <!-- LEFT COLUMN -->
                            <div class="profile-left">
                                <!-- PROFILE HEADER -->
                                <div class="profile-header">
                                    <div class="overlay"></div>
                                    <div class="profile-main">
                                        <img src="" class="img-circle headImg" style="border-radius: 50%;" alt="${u_skl.userName}">
                                        <canvas id="headImg" style="display:none"></canvas>
                                        <script type="text/javascript">
                                            $(function(){
                                                textToImg('${u_skl.userName}');
                                            });

                                            function textToImg(uname) {
                                                var name = uname.charAt(0).toUpperCase();
                                                var fontSize = 37;
                                                var fontWeight = 'bold';

                                                var canvas = document.getElementById('headImg');
                                                var img1 = document.getElementById('headImg');
                                                canvas.width = 75;
                                                canvas.height = 75;
                                                var context = canvas.getContext('2d');
                                                context.fillStyle = 'rgb(153, 169, 191)';
                                                context.fillRect(0, 0, canvas.width, canvas.height);
                                                context.fillStyle = '#fff';
                                                context.font = fontWeight + ' ' + fontSize + 'px sans-serif';
                                                context.textAlign = 'center';
                                                context.textBaseline="middle";
                                                context.fillText(name, fontSize, fontSize);
                                                $('.headImg').attr('src',canvas.toDataURL("image/png"));
                                            };
                                        </script>
                                        <h3 class="name">${u_skl.userName}</h3>
                                        <c:if test="${u_skl.isVip==1}">
                                            <span class="online-status status-available">VIP</span>
                                        </c:if>
                                    </div>
                                    <div class="profile-stat">
                                        <div class="row">
                                            <div class="col-md-4 stat-item">
                                                ${myFilmsCount} <span>上传视频</span>
                                            </div>
                                            <div class="col-md-4 stat-item">
                                                ${commentCount} <span>评论</span>
                                            </div>
                                            <div class="col-md-4 stat-item">
                                                ${totalLike} <span>点赞</span>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <!-- END PROFILE HEADER -->
                                <!-- PROFILE DETAIL -->
                                <div class="profile-detail">
                                    <div class="profile-info">
                                        <h4 class="heading">用户信息</h4>
                                        <ul class="list-unstyled list-justify">
                                            <li>观看视频数目 <span>${viewCount}</span></li>
                                            <li>邮箱 <span>${u_skl.userEmail}</span></li>
                                        </ul>
                                    </div>
                                </div>
                                <!-- END PROFILE DETAIL -->
                            </div>
                            <!-- END LEFT COLUMN -->
                            <!-- RIGHT COLUMN -->
                            <div class="profile-right">
                                <h4 class="heading">用户权限</h4>
                                <!-- AWARDS -->
                                <div class="awards">
                                    <div class="row">
                                        <div class="col-md-3 col-sm-6">
                                            <div class="award-item">
                                                <div class="hexagon">
                                                    <span class="lnr lnr-sun award-icon"></span>
                                                </div>
                                                <span>视频发布</span>
                                            </div>
                                        </div>
                                        <div class="col-md-3 col-sm-6">
                                            <div class="award-item">
                                                <div class="hexagon">
                                                    <span class="lnr lnr-clock award-icon"></span>
                                                </div>
                                                <span>观看视频</span>
                                            </div>
                                        </div>
                                        <div class="col-md-3 col-sm-6">
                                            <div class="award-item">
                                                <div class="hexagon">
                                                    <span class="lnr lnr-magic-wand award-icon"></span>
                                                </div>
                                                <span>评论</span>
                                            </div>
                                        </div>
                                        <div class="col-md-3 col-sm-6">
                                            <div class="award-item">
                                                <div class="hexagon">
                                                    <span class="lnr lnr-heart award-icon"></span>
                                                </div>
                                                <span>资料管理</span>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <!-- END AWARDS -->
                                <!-- TABBED CONTENT -->
                                <div class="custom-tabs-line tabs-line-bottom left-aligned">
                                    <ul class="nav" role="tablist">
                                        <li class="active"><a href="#video-mine" role="tab" data-toggle="tab">我的视频</a></li>
                                        <li><a href="#view-history" role="tab" data-toggle="tab">浏览记录</a></li>
                                        <li><a href="#my-comment" role="tab" data-toggle="tab">我的评论</a></li>
                                        <li><a href="#update-info" role="tab" data-toggle="tab">修改密码</a></li>
                                    </ul>
                                </div>
                                <div class="tab-content">
                                    <%-- 我的视频 --%>
                                    <div class="tab-pane in active" id="video-mine">
                                        <div class="text-center" style="width: 100%;">
                                            <a href="/video/profile/share.html">
                                                <button class="btn-primary btn" style="border-radius: 15%;">我要上传</button>
                                            </a>
                                        </div>
                                        <c:if test="${films!=null && films.size()>0}">
                                            <div style="margin: 10px auto;">
                                                <ul class="film-list" id="my-films">
                                                    <c:forEach items="${films}" var="list">
                                                        <li>
                                                            <a href="/video/profile/share.html?film_id=${list.id}">
                                                                <div title="${list.name}"><img src="${list.image}" style="height: 175px;width: 126px;"></div>
                                                            </a>
                                                            <div class="film-info">
                                                                <a href="/video/profile/share.html?film_id=${list.id}" title="${list.name}"><p>${list.name}</p></a>
                                                                <p>${list.onDecade}-${list.typeName}</p>
                                                            </div>
                                                        </li>
                                                    </c:forEach>
                                                </ul>
                                            </div>
                                            <div class="margin-top-30 text-center">
                                                <nav aria-label="...">
                                                    <ul class="pager" id="my-films-page">
                                                        <c:if test="${pageNo!=1}">
                                                            <li>
                                                                <a href="javascript:;" onclick="changePage(${pageNo-1})">上一页</a>
                                                            </li>
                                                        </c:if>
                                                        <c:if test="${pageNo==1}">
                                                            <li class="disabled">
                                                                <a href="javascript:;">上一页</a>
                                                            </li>
                                                        </c:if>
                                                        <li><p>${pageNo}/${totalPage}</p></li>
                                                        <c:if test="${pageNo!=totalPage}">
                                                            <li>
                                                                <a href="javascript:;" onclick="changePage(${pageNo+1})">下一页</a>
                                                            </li>
                                                        </c:if>
                                                        <c:if test="${pageNo==totalPage}">
                                                            <li class="disabled">
                                                                <a href="javascript:;">下一页</a>
                                                            </li>
                                                        </c:if>
                                                    </ul>
                                                </nav>
                                            </div>
                                            <script>
                                                // 分页AJAX请求
                                                function changePage(pc) {
                                                    $.ajax({
                                                        url:"/video/profile/getFilmAjax.html",
                                                        type: "POST",
                                                        dataType: "json",
                                                        data:{pc:pc, type:'my-films'},
                                                        success:function(data) {
                                                            if( typeof data == "string" ) data = JSON.parse(data);
                                                            var films = data.films;
                                                            var pageNo = data.pageNo;
                                                            var totalPage = data.totalPage;
                                                            var htmlStr = '';
                                                            for(var i=0; i<films.length; i++) {
                                                                var film = films[i];
                                                                htmlStr = htmlStr+'<li><a href="/video/profile/share.html?film_id='+film.id+'">';
                                                                htmlStr = htmlStr+'<div title="'+film.name+'"><img src="'+film.image+'" style="height: 175px;width: 126px;"></div>';
                                                                htmlStr = htmlStr+'</a><div class="film-info"><a href="/video/profile/share.html?film_id='+film.id+'" title="';
                                                                htmlStr = htmlStr+film.name+'"><p>'+film.name+'</p></a><p>'+film.onDecade+'-'+film.typeName+'</p></div></li>';
                                                            }
                                                            $("#my-films").html(htmlStr);
                                                            htmlStr = '';
                                                            if( pageNo!=1 ) {
                                                                htmlStr = htmlStr + '<li><a href="javascript:;" onclick="changePage(';
                                                                htmlStr = htmlStr + (pageNo-1)+ ')">上一页</a></li>';
                                                            } else {
                                                                htmlStr = htmlStr + '<li class="disabled"><a href="javascript:;">上一页</a></li>';
                                                            }
                                                            htmlStr = htmlStr + '<li><p>'+ pageNo + '/' + totalPage + '</p></li>';
                                                            if( pageNo!=totalPage ) {
                                                                htmlStr = htmlStr + '<li><a href="javascript:;" onclick="changePage(';
                                                                htmlStr = htmlStr + (pageNo+1)+ ')">下一页</a></li>';
                                                            } else {
                                                                htmlStr = htmlStr + '<li class="disabled"><a href="javascript:;">下一页</a></li>';
                                                            }
                                                            $("#my-films-page").html(htmlStr);
                                                        },
                                                        error:function() {
                                                            alert("请求出错！");
                                                        }
                                                    });
                                                }
                                            </script>
                                        </c:if>
                                    </div>
                                    <%-- 浏览历史 --%>
                                    <div class="tab-pane" id="view-history">
                                        <c:if test="${viewHistoryList!=null && viewHistoryList.size()>0}">
                                            <div style="margin: 10px auto;">
                                                <ul class="film-list" id="my-view-history">
                                                    <c:forEach items="${viewHistoryList}" var="list">
                                                        <li>
                                                            <a href="/video/xl/detail.html?film_id=${list.film.id}">
                                                                <div title="上次浏览时间:<f:formatDate value="${list.date_view}" pattern="yyyy-MM-dd HH:mm:ss" />"><img src="${list.film.image}" style="height: 175px;width: 126px;"></div>
                                                            </a>
                                                            <div class="film-info">
                                                                <a href="/video/xl/detail.html?film_id=${list.id}" title="${list.film.name}"><p>${list.film.name}</p></a>
                                                                <p>${list.film.onDecade}-${list.film.typeName}</p>
                                                            </div>
                                                        </li>
                                                    </c:forEach>
                                                </ul>
                                            </div>
                                            <div class="margin-top-30 text-center">
                                                <nav aria-label="...">
                                                    <ul class="pager" id="my-view-history-page">
                                                        <c:if test="${viewHistoryPage!=1}">
                                                            <li>
                                                                <a href="javascript:;" onclick="changeViewHistoryPage(${viewHistoryPage-1})">上一页</a>
                                                            </li>
                                                        </c:if>
                                                        <c:if test="${viewHistoryPage==1}">
                                                            <li class="disabled">
                                                                <a href="javascript:;">上一页</a>
                                                            </li>
                                                        </c:if>
                                                        <li><p>${viewHistoryPage}/${viewHistoryAllPage}</p></li>
                                                        <c:if test="${viewHistoryPage!=viewHistoryAllPage}">
                                                            <li>
                                                                <a href="javascript:;" onclick="changeViewHistoryPage(${viewHistoryPage+1})">下一页</a>
                                                            </li>
                                                        </c:if>
                                                        <c:if test="${viewHistoryPage==viewHistoryAllPage}">
                                                            <li class="disabled">
                                                                <a href="javascript:;">下一页</a>
                                                            </li>
                                                        </c:if>
                                                    </ul>
                                                </nav>
                                            </div>
                                            <script>
                                                // 分页AJAX请求
                                                function changeViewHistoryPage(pc) {
                                                    $.ajax({
                                                        url:"/video/profile/getFilmAjax.html",
                                                        type: "POST",
                                                        dataType: "json",
                                                        data:{pc:pc, type:'view-history'},
                                                        success:function(data) {
                                                            if( typeof data == "string" ) data = JSON.parse(data);
                                                            var films = data.viewHistoryList;
                                                            var pageNo = data.pageNo;
                                                            var totalPage = data.totalPage;
                                                            var htmlStr = '';
                                                            for(var i=0; i<films.length; i++) {
                                                                var mapList = films[i];
                                                                htmlStr = htmlStr+'<li><a href="/video/xl/detail.html?film_id='+mapList.film.id+'">';
                                                                htmlStr = htmlStr+'<div title="上次浏览时间:'+dateFtt("yyyy-MM-dd hh:mm:ss", new Date(mapList.date_view.time))+'"><img src="'+mapList.film.image+'" style="height: 175px;width: 126px;"></div>';
                                                                htmlStr = htmlStr+'</a><div class="film-info"><a href="/video/xl/detail.html?film_id='+mapList.film.id+'" title="';
                                                                htmlStr = htmlStr+mapList.film.name+'"><p>'+mapList.film.name+'</p></a><p>'+mapList.film.onDecade+'-'+mapList.film.typeName+'</p></div></li>';
                                                            }
                                                            $("#my-view-history").html(htmlStr);
                                                            htmlStr = '';
                                                            if( pageNo!=1 ) {
                                                                htmlStr = htmlStr + '<li><a href="javascript:;" onclick="changeViewHistoryPage(';
                                                                htmlStr = htmlStr + (pageNo-1)+ ')">上一页</a></li>';
                                                            } else {
                                                                htmlStr = htmlStr + '<li class="disabled"><a href="javascript:;">上一页</a></li>';
                                                            }
                                                            htmlStr = htmlStr + '<li><p>'+ pageNo + '/' + totalPage + '</p></li>';
                                                            if( pageNo!=totalPage ) {
                                                                htmlStr = htmlStr + '<li><a href="javascript:;" onclick="changeViewHistoryPage(';
                                                                htmlStr = htmlStr + (pageNo+1)+ ')">下一页</a></li>';
                                                            } else {
                                                                htmlStr = htmlStr + '<li class="disabled"><a href="javascript:;">下一页</a></li>';
                                                            }
                                                            $("#my-view-history-page").html(htmlStr);
                                                        },
                                                        error:function() {
                                                            alert("请求出错！");
                                                        }
                                                    });
                                                }
                                            </script>
                                        </c:if>
                                    </div>
                                    <%-- 我的评论 --%>
                                    <div class="tab-pane in" id="my-comment">
                                        <ul class="list-unstyled activity-timeline">
                                            <c:forEach items="${comments}" var="list">
                                                <li>
                                                    <i class="fa fa-comment activity-icon"></i>
                                                    <p>${list.context} <span class="timestamp"><f:formatDate value="${list.date_update}" pattern="yyyy-MM-dd HH:mm:ss" /></span></p>
                                                </li>
                                            </c:forEach>
                                        </ul>
                                        <%--  分页 --%>
                                        <div class="margin-top-30 text-center">
                                            <nav aria-label="...">
                                                <ul class="pager" id="my-comment-page">
                                                    <c:if test="${commentPage!=1}">
                                                        <li>
                                                            <a href="javascript:;" onclick="changeComment(${commentPage-1})">上一页</a>
                                                        </li>
                                                    </c:if>
                                                    <c:if test="${commentPage==1}">
                                                        <li class="disabled">
                                                            <a href="javascript:;">上一页</a>
                                                        </li>
                                                    </c:if>
                                                    <li><p>${commentPage}/${commentAllPage}</p></li>
                                                    <c:if test="${commentPage!=commentAllPage}">
                                                        <li>
                                                            <a href="javascript:;" onclick="changeComment(${commentPage+1})">下一页</a>
                                                        </li>
                                                    </c:if>
                                                    <c:if test="${commentPage==commentAllPage}">
                                                        <li class="disabled">
                                                            <a href="javascript:;">下一页</a>
                                                        </li>
                                                    </c:if>
                                                </ul>
                                            </nav>
                                        </div>
                                        <script>
                                            // 分页AJAX请求
                                            function changeComment(pc) {
                                                $.ajax({
                                                    url:"/video/profile/getMyComments.html",
                                                    type: "POST",
                                                    dataType: "json",
                                                    data:{pc:pc},
                                                    success:function(data) {
                                                        if( typeof data == "string" ) data = JSON.parse(data);
                                                        var comments = data.commentList;
                                                        var pageNo = data.pageNo;
                                                        var totalPage = data.totalPage;
                                                        var htmlStr = '<ul class="list-unstyled activity-timeline">';
                                                        for(var i=0; i<comments.length; i++) {
                                                            var comment = comments[i];
                                                            htmlStr = htmlStr + '<li><i class="fa fa-comment activity-icon"></i>';
                                                            htmlStr = htmlStr + '<p>'+comment.context+'<span class="timestamp">'+dateFtt("yyyy-MM-dd hh:mm:ss", new Date(comment.date_update.time))+'</span></p></li>';
                                                        }
                                                        htmlStr = htmlStr + '</ul>';
                                                        $("#my-comment").html(htmlStr);
                                                        htmlStr = '';
                                                        if( pageNo!=1 ) {
                                                            htmlStr = htmlStr + '<li><a href="javascript:;" onclick="changeComment(';
                                                            htmlStr = htmlStr + (pageNo-1)+ ')">上一页</a></li>';
                                                        } else {
                                                            htmlStr = htmlStr + '<li class="disabled"><a href="javascript:;">上一页</a></li>';
                                                        }
                                                        htmlStr = htmlStr + '<li><p>'+ pageNo + '/' + totalPage + '</p></li>';
                                                        if( pageNo!=totalPage ) {
                                                            htmlStr = htmlStr + '<li><a href="javascript:;" onclick="changeComment(';
                                                            htmlStr = htmlStr + (pageNo+1)+ ')">下一页</a></li>';
                                                        } else {
                                                            htmlStr = htmlStr + '<li class="disabled"><a href="javascript:;">下一页</a></li>';
                                                        }
                                                        $("#my-comment-page").html(htmlStr);
                                                    },
                                                    error:function() {
                                                        alert("请求出错！");
                                                    }
                                                });
                                            }
                                        </script>
                                    </div>
                                    <%--修改资料--%>
                                    <div class="tab-pane in" id="update-info">
                                        <div class="margin-top-30 text-center">
                                            <label for="oldPwd" class="control-label sr-only">旧密码：</label>
                                            <input type="password" class="form-control" name="oldPwd" id="oldPwd" placeholder="旧密码" autocomplete="off">
                                        </div>
                                        <div class="margin-top-30 text-center">
                                            <label for="rePwd" class="control-label sr-only">再次输入：</label>
                                            <input type="password" class="form-control" name="rePwd" id="rePwd" placeholder="再次输入旧密码" autocomplete="off">
                                        </div>
                                        <div class="margin-top-30 text-center">
                                            <label for="newPwd" class="control-label sr-only">新密码：</label>
                                            <input type="password" class="form-control" name="newPwd" id="newPwd" placeholder="新密码" autocomplete="off">
                                        </div>
                                        <div class="margin-top-30 text-center"><a href="javascript:;" class="btn btn-default" onclick="updatePassword()">提交修改</a></div>
                                        <script>
                                            function updatePassword() {
                                                var oldPwd = document.getElementById("oldPwd").value;
                                                var rePwd = document.getElementById("rePwd").value;
                                                var newPwd = document.getElementById("newPwd").value;
                                                if( oldPwd!=rePwd ) {
                                                    alert("两次旧密码输入不一致,请重新输入!");
                                                } else {
                                                    $.ajax({
                                                       url:"/video/updatePassword.html",
                                                       type:"POST",
                                                       dataType: "json",
                                                       data:{oldPwd:oldPwd, newPwd:newPwd},
                                                       success:function (data) {
                                                           if( typeof data == "string" ) data = JSON.parse(data);
                                                           var code = data.code;
                                                           if( code=='1' ) {
                                                               alert("修改成功!请使用新密码重新登录~~")
                                                               location.href='/video/index.html';
                                                           } else {
                                                               alert(data.msg);
                                                           }
                                                       },
                                                       error:function () {
                                                           alert("系统出错!");
                                                       }
                                                    });
                                                }
                                            }
                                        </script>
                                    </div>
                                </div>
                                <!-- END TABBED CONTENT -->
                            </div>
                            <!-- END RIGHT COLUMN -->
                        </div>
                    </div>
                </div>
            </div>
            <!-- END MAIN CONTENT -->
        </div>
        <!-- END MAIN -->
        <div class="clearfix"></div>
    </div>
    <jsp:include page="/WEB-INF/jsp/pub/footer.jsp"/>
</body>
</html>
