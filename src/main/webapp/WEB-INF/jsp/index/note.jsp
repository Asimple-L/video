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
    <title>留言求片</title>
    <link rel="stylesheet" href="${proname}/plugins/bootflat-admin/css/site.min.css">
    <jsp:include page="/WEB-INF/jsp/pub/head_meta.jsp"/>
    <jsp:include page="/WEB-INF/jsp/pub/head_link.jsp"/>
    <jsp:include page="/WEB-INF/jsp/pub/head_script.jsp"/>
    <!--=====================CSS_Link===========================-->
    <style>
        .content-main {
            margin-top: 20px;
        }
        .po {
            cursor: pointer;
        }
    </style>
    <!--=====================JS_Link===========================-->
</head>
<body>
    <jsp:include page="/WEB-INF/jsp/pub/head.jsp"></jsp:include>
    <div class="container" style="margin-top: 68px;">
        <div id="editor"></div>
        <div class="btn btn-primary" style="width: 100%;margin-top: 10px;" onclick="submitCommentText()">
            发表评论
        </div>
    </div>
    <div class="container content-main">
        <div class="row">
            <c:forEach items="${pb.beanList}" var="list">
                <div class="col-md-6">
                    <div class="panel panel-default">
                        <div class="panel-heading">
                            <h3 class="panel-title">${list.context}</h3>
                        </div>
                        <div class="panel-body">
                            <div class="row">
                                <div class="col-md-6">
                                    <span class="po">
                                        <i class="glyphicon glyphicon-time"></i>
                                        <f:formatDate value='${list.date_update}' pattern='yyyy-MM-dd HH:mm:ss' />
                                    </span>
                                </div>
                                <div class="col-md-2">
                                    <span class="po">
                                        <i class="glyphicon glyphicon-user"></i>
                                        ${list.user.userName}
                                    </span>
                                </div>
                                <div class="col-md-2" onclick="like('${list.id}')">
                                    <span class="po">
                                        <i class="glyphicon glyphicon-thumbs-up"></i>
                                        ${list.likeNum}
                                    </span>
                                </div>
                                <div class="col-md-2" onclick="unlike('${list.id}')">
                                    <span class="po">
                                        <i class="glyphicon glyphicon-thumbs-down"></i>
                                        ${list.unlikeNum}
                                    </span>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </c:forEach>
        </div>
    </div>
    <jsp:include page="/WEB-INF/jsp/pub/footer.jsp"></jsp:include>
    <script type="text/javascript" src="${proname}/plugins/wangEditor/wangEditor.min.js"></script>
    <script type="text/javascript">
        var E = window.wangEditor;
        var editor = new E('#editor');
        editor.create();

        function submitCommentText() {
            var text = editor.txt.text();
            var htmlStr = editor.txt.html();
            if( text!=null && text.trim()!="" ) {
                $.ajax({
                    url:"/video/saveComment.html",
                    type:"POST",
                    dataType:"JSON",
                    data:{context:htmlStr},
                    success:function (data) {
                        if( typeof data == "string" ) data = JSON.parse(data);
                        alert(data.msg);
                        location.reload();
                    },
                    error:function () {
                        alert("提交失败!");
                    }
                });
            }
        }
    </script>
    <script type="text/javascript">
        //整个文档高度
        var totalHeight = $(document).height();
        //浏览器可视窗口高度
        var seeHeight = $(window).height();
        //浏览器可视窗口顶端距离网页顶端的高度（垂直偏移）
        var scrollTop = $(window).scrollTop();
        //添加滚动事件
        $(window).scroll(function(){
            scrollTop = $(window).scrollTop();
            totalHeight = $(document).height();
            if(scrollTop+seeHeight>totalHeight){
                // alert("加载下一页!");
            }
        })
    </script>
    <script type="text/javascript">
        function unlike(id) {
            changeNum("1", id);
        }

        function like(id) {
            changeNum("0", id)
        }

        function changeNum(type, id) {
            $.ajax({
                url:"/video/changeLikeNum.html",
                type:"POST",
                dataType:"JSON",
                data:{type:type, id: id},
                success:function (data) {
                    if( typeof data == "string" ) data = JSON.parse(data);
                    if( data.code == "000000" ) {
                        if( type=="0" ) alert("点赞成功!");
                        else alert("踩一踩成功!");
                    } else {
                        alert("操作失败!");
                    }
                    location.reload();
                },
                error: function () {
                    alert("系统繁忙,请稍后重试!");
                }
            });
        }

    </script>
</body>
</html>