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
    <%--<jsp:include page="/WEB-INF/jsp/pub/head_script.jsp"/>--%>
    <script src="${pageContext.request.contextPath}/public/static/js/jquery-2.0.0.min.js?v=${version}" type="text/javascript" charset="UTF-8" ></script>
    <script src="${pageContext.request.contextPath}/plugins/bootflat-admin/js/bootstrap.min.js"></script>
    <script src="${pageContext.request.contextPath}/plugins/jquery-slimscroll/jquery.slimscroll.min.js"></script>
    <script src="${pageContext.request.contextPath}/plugins/bootflat-admin/js/klorofil-common.js"></script>
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
            height: 190px;
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
                                                var name = uname.charAt(0);
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
                                                45 <span>上传视频</span>
                                            </div>
                                            <div class="col-md-4 stat-item">
                                                15 <span>评论</span>
                                            </div>
                                            <div class="col-md-4 stat-item">
                                                2174 <span>点赞</span>
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
                                            <li>VIP到期日 <span>24 Aug, 2016</span></li>
                                            <li>观看视频数目 <span>(124) 823409234</span></li>
                                            <li>邮箱 <span>samuel@mydomain.com</span></li>
                                            <li>Website <span>www.themeineed.com</span></li>
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
                                        <li><a href="#update-info" role="tab" data-toggle="tab">信息修改</a></li>
                                    </ul>
                                </div>
                                <div class="tab-content">
                                    <div class="tab-pane in active" id="video-mine">
                                        <div class="text-center" style="width: 100%;">
                                            <button class="btn-primary btn" style="border-radius: 15%;">我要上传</button>
                                        </div>
                                        <div style="margin: 10px auto;">
                                            <ul class="film-list">
                                                <c:forEach items="${films}" var="list">
                                                    <li>
                                                        <a href="admin/film.html?film_id=${list.id}">
                                                            <div title="${list.name}"><img src="${list.image}" style="height: 175px;width: 126px;"></div>
                                                        </a>
                                                        <div class="film-info">
                                                            <a href="admin/film.html?film_id=${list.id}" title="${list.name}"><p>${list.name}</p></a>
                                                            <p>${list.onDecade}-${list.typeName}</p>
                                                        </div>
                                                    </li>
                                                </c:forEach>
                                            </ul>
                                        </div>
                                        <div class="margin-top-30 text-center"><a href="#" class="btn btn-default">查看所有</a></div>
                                    </div>
                                    <div class="tab-pane" id="view-history">
                                        <div class="table-responsive">
                                            <table class="table project-table">
                                                <thead>
                                                <tr>
                                                    <th>Title</th>
                                                    <th>Progress</th>
                                                    <th>Leader</th>
                                                    <th>Status</th>
                                                </tr>
                                                </thead>
                                                <tbody>
                                                <tr>
                                                    <td><a href="#">Spot Media</a></td>
                                                    <td>
                                                        <div class="progress">
                                                            <div class="progress-bar" role="progressbar" aria-valuenow="60" aria-valuemin="0" aria-valuemax="100" style="width: 60%;">
                                                                <span>60% Complete</span>
                                                            </div>
                                                        </div>
                                                    </td>
                                                    <td><a href="#">Michael</a></td>
                                                    <td><span class="label label-success">ACTIVE</span></td>
                                                </tr>
                                                <tr>
                                                    <td><a href="#">E-Commerce Site</a></td>
                                                    <td>
                                                        <div class="progress">
                                                            <div class="progress-bar" role="progressbar" aria-valuenow="33" aria-valuemin="0" aria-valuemax="100" style="width: 33%;">
                                                                <span>33%</span>
                                                            </div>
                                                        </div>
                                                    </td>
                                                    <td><a href="#">Antonius</a></td>
                                                    <td><span class="label label-warning">PENDING</span></td>
                                                </tr>
                                                <tr>
                                                    <td><a href="#">Project 123GO</a></td>
                                                    <td>
                                                        <div class="progress">
                                                            <div class="progress-bar" role="progressbar" aria-valuenow="68" aria-valuemin="0" aria-valuemax="100" style="width: 68%;">
                                                                <span>68% Complete</span>
                                                            </div>
                                                        </div>
                                                    </td>
                                                    <td><a href="#">Antonius</a></td>
                                                    <td><span class="label label-success">ACTIVE</span></td>
                                                </tr>
                                                <tr>
                                                    <td><a href="#">Wordpress Theme</a></td>
                                                    <td>
                                                        <div class="progress">
                                                            <div class="progress-bar" role="progressbar" aria-valuenow="75" aria-valuemin="0" aria-valuemax="100" style="width: 75%;">
                                                                <span>75%</span>
                                                            </div>
                                                        </div>
                                                    </td>
                                                    <td><a href="#">Michael</a></td>
                                                    <td><span class="label label-success">ACTIVE</span></td>
                                                </tr>
                                                <tr>
                                                    <td><a href="#">Project 123GO</a></td>
                                                    <td>
                                                        <div class="progress">
                                                            <div class="progress-bar progress-bar-success" role="progressbar" aria-valuenow="100" aria-valuemin="0" aria-valuemax="100" style="width: 100%;">
                                                                <span>100%</span>
                                                            </div>
                                                        </div>
                                                    </td>
                                                    <td><a href="#">Antonius</a></td>
                                                    <td><span class="label label-default">CLOSED</span></td>
                                                </tr>
                                                <tr>
                                                    <td><a href="#">Redesign Landing Page</a></td>
                                                    <td>
                                                        <div class="progress">
                                                            <div class="progress-bar progress-bar-success" role="progressbar" aria-valuenow="100" aria-valuemin="0" aria-valuemax="100" style="width: 100%;">
                                                                <span>100%</span>
                                                            </div>
                                                        </div>
                                                    </td>
                                                    <td><a href="#">Jason</a></td>
                                                    <td><span class="label label-default">CLOSED</span></td>
                                                </tr>
                                                </tbody>
                                            </table>
                                        </div>
                                    </div>
                                    <div class="tab-pane in" id="my-comment">
                                        <ul class="list-unstyled activity-timeline">
                                            <li>
                                                <i class="fa fa-comment activity-icon"></i>
                                                <p>Commented on post <a href="#">Prototyping</a> <span class="timestamp">2 minutes ago</span></p>
                                            </li>
                                            <li>
                                                <i class="fa fa-cloud-upload activity-icon"></i>
                                                <p>Uploaded new file <a href="#">Proposal.docx</a> to project <a href="#">New Year Campaign</a> <span class="timestamp">7 hours ago</span></p>
                                            </li>
                                            <li>
                                                <i class="fa fa-plus activity-icon"></i>
                                                <p>Added <a href="#">Martin</a> and <a href="#">3 others colleagues</a> to project repository <span class="timestamp">Yesterday</span></p>
                                            </li>
                                            <li>
                                                <i class="fa fa-check activity-icon"></i>
                                                <p>Finished 80% of all <a href="#">assigned tasks</a> <span class="timestamp">1 day ago</span></p>
                                            </li>
                                        </ul>
                                        <%--  分页 --%>
                                        <div class="margin-top-30 text-center"><a href="#" class="btn btn-default">See all activity</a></div>
                                    </div>
                                    <div class="tab-pane in" id="update-info">
                                        修改信息
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
