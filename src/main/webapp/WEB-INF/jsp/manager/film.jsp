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
    <title>添加影片</title>
    <jsp:include page="/WEB-INF/jsp/pub/head_meta.jsp"/>
    <jsp:include page="/WEB-INF/jsp/pub/head_link.jsp"/>
    <jsp:include page="/WEB-INF/jsp/pub/head_script.jsp"/>
    <!--=====================CSS_Link===========================-->
    <link rel="stylesheet" href="plugins/uploadify/uploadify.css" type="text/css">
    <!--=====================JS_Link===========================-->
    <script type="text/javascript" src="plugins/uploadify/jquery.uploadify.min.js"></script>


    <style>

        * {
            margin: 0px;
            padding: 0px;
            zoom: 1;
            font-size: 12px;
            font-family: '微软雅黑';
        }

        a {
            text-decoration: none;
            color: #444;
        }

        .input {
            width: 200px;
            height: 30px;
            border: 1px solid #ccc;
            border-radius: 3px;
            background: rgba(195, 221, 236, 0.14);
            line-height: 30px;
            outline: none;
            float: left;
            margin-right: 5px;
            overflow: hidden;
            padding: 5px;
        }

        .contentType {
            width: 50%;
            min-height: 100px;
        }

        .left-con {
            width: 9%;
            height: auto;
            overflow: hidden;
            float: left;
            text-align: left;
            line-height: 35px;
            font-size: 14px;
            font-family: '微软雅黑';
            padding: 0.8%;
        }

        .right-con {
            width: 88%;
            height: auto;
            overflow: hidden;
            float: right;
            padding: 1%;
            font-size: 14px;
            font-family: '微软雅黑';
        }

        .film-info {
            width: 100%;
            height: auto;
            overflow: hidden;
        }

        .film-info li {
            width: 100%;
            height: auto;
            overflow: hidden;
            border-bottom: 1px solid #efefef;
            list-style: none;
        }

        .fixSelect {
            width: 212px;
            height: 31px;
            font-size: 14px;
            font-family: '微软雅黑';
            padding: 0px;
        }

        .btn {
            padding: 4px 8px;
            border-radius: 4px;
            background: #efefef;
            border: 1px solid #cccccc;
            text-decoration: none;
            color: #444;
            margin-right: 10px;
            display: inline-block;
        }

        .btn:hover {
            background: #1d94ff;
            border: 1px solid #1d94ff;
            color: white;
        }

        .title {
            background: #efefef;
        }

        .longInput {
            width: 50%;
        }

        .alert-box {
            padding: 4px 8px;
            border-radius: 4px;
            text-decoration: none;
            color: #444;
            margin-right: 10px;
            border: 1px solid #F3B99E;
            background: #F3D8D6;
        }

        .edit, .label-input {
            display: none;
        }

        .show {
            padding: 4px 8px;
            border: 1px solid transparent;
            text-decoration: none;
            color: #444;
            margin-right: 10px;
            display: inline-block;
            font-size: 14px;
            font-family: '微软雅黑';
        }

        .label:hover .edit {
            display: inline-block;
        }

        .zi_yuan_list {
            width: 100%;
            height: auto;
            overflow: hidden;
        }

        .zi_yuan_list li {
            list-style: none;
            height: auto;
            width: 18%;
            float: left;
            font-size: 12px;
            border: none;
            padding: 1%;
            overflow: hidden;
            font-family: '微软雅黑';
            text-align: center;

        }

        .zi_yuan_list li .show {
            width: 100%;
            overflow: hidden;
            white-space: nowrap;
            text-overflow: ellipsis;
            padding: 0px;
        }

        .del {
            color: #686868;
            padding: 5px;
            border: 1px #5B5B5B solid;
            cursor: pointer;
            border-radius: 5px;
        }

        .file-pre img, .update-pre img {
            max-width: 800px;
            vertical-align: middle;
            margin-right: 5px;
            margin-bottom: 10px;
            padding: 5px;
            width: 160px;
            border: 1px solid #dddddd;
        }

        /*box框架*/
        .box {
            width: 1190px;
            height: auto;
            overflow: hidden;
            margin: 0px auto;
        }

        .box-left {
            width: 290px;
            height: auto;
            float: left;
            overflow: hidden;
        }

        .box-right {
            width: 900px;
            height: auto;
            float: right;
            overflow: hidden;
        }

        /*box框架*/
        /*导航*/
        .nav-title {
            width: 100%;
            height: 30px;
            overflow: hidden;
            line-height: 30px;
            font-size: 12px;
            font-family: '微软雅黑';
            cursor: pointer;

        }

        .nav-icon {
            font-style: normal;
            height: 100%;
            width: 30px;
            display: inline-block;
            background: #fff url("static/img/pub/nav.jpg") -144px -27px no-repeat; /*-143px -27px*/
        }

        .nav1-ul {
            width: 60%;
            height: auto;
            overflow: hidden;
            margin: 0px auto;
        }

        .nav1-li {
            width: 100%;
            height: auto;
            overflow: hidden;
        }

        .nav2-ul {
            width: 100%;
            height: auto;
            overflow: hidden;
        }

        .nav2-li {
            width: 100%;
            height: 30px;
            overflow: hidden;
            line-height: 30px;
            text-align: center;
        }

        /*导航*/
    </style>
    <%--导航--%>
    <script>
        $(function () {
            $(".nav-title").click(function () {
                $(this).parent().find(".nav2-ul").toggle();
                if ($(this).find(".nav-icon").css("background-position") == "-144px -27px") {
                    $(this).find(".nav-icon").css("background-position", "-92px -27px");
                } else {
                    $(this).find(".nav-icon").css("background-position", "-144px -27px");
                }
            });

            $(".uploadify-button").css({"line-height":"25px"});
        })
        /**
         * 设置模板类型
         */
        function setMoban() {
            $("#res_name").val("xxxx@@集##1##集数结束##分割符号");
        }
    </script>
    <%--导航--%>
    <script>
        $(function () {

            /**
             *
             * 编辑取消按钮
             */
            $(".edit").click(function () {
                $(this).parent().parent().find(".label-input").show();
                $(this).parent().parent().find(".label").hide();
                var t = $(this).attr("t");
                if (t != undefined) {
                    $("#cancel-bt").hide();
                    $("#save-bt").hide();
                }
            })
            $(".cancel").click(function () {
                $(this).parent().parent().find(".label-input").hide();
                $(this).parent().parent().find(".label").show();

                var t = $(this).attr("t");
                if (t != undefined) {
                    var filepath = $(this).parent().parent().find(".label .update-pre div img").attr("src");

                    $.ajax({
                        type: "post",
                        url: "delFile.html",
                        cache: false,
                        data: "picsPath=" + filepath,
                        dataType: 'json',     //接受数据格式
                        success: function (rs) {
                            var data = JSON.parse(rs);
                            if (data.code == "1") {
                                $("#file_upload").show();
                                $(this).parent().parent().find(".label .update-pre div").remove();
                            } else {
                                alert("删除失败!");
                            }
                        },
                        error: function () {
                            alert("系统繁忙!");
                        }
                    });
                }

            })

            $(".save").click(function () {
                debugger;
                var key = $(this).parent().find(".update-input").attr("name");
                var key1 = "";
                var val = "";
                var val1 = "";
                var input = $(this).parent().find(".update-input");
                if (input.attr("el") == "div") {
                    val = input.val();
                } else {
                    if (key == "locName" || key == "typeName" || key =="isVip") {
                        val = input.val();
                        if(key !="isVip"){
                            val = input.find("option:selected").text();
                            key1 = input.attr("name1");
                            val1 = input.val();
                        }
                    } else if (key == "image") {
                        val = input.attr("src");
                    } else {
                        val = input.val();
                    }
                }
                var film_id = $(this).attr("film_id");
                if (val != undefined && val != "") {
                    updateFilmInfo(film_id, key, val, this);
                    if (key1 != "") {
                        setTimeout(function () {
                            updateFilmInfo(film_id, key1, val1, this);
                        }, 3000);
                    }
                } else {
                    alert("信息不完整");
                }
            })

            function updateFilmInfo(f_id, key, val, t) {
                $.ajax({
                    url: "updateFilmInfo.html",
                    type: "post",
                    dataType: "json",
                    data: "film_id=" + f_id + "&key=" + key + "&val=" + val,
                    success: function (data) {
                        if (data == "1") {
                            $(t).parent().parent().find(".label-input").hide();
                            if (key == "image") {
                                $(t).parent().parent().find(".label").show().find("div img").attr("src", val);
                                $("#file_upload").show();
                                $(".file-pre div").remove();
                            } else {
                                if (key != "loc_id" && key != "type_id") {
                                    if(key=="isVip"){
                                        if(val=="1"){
                                            val = "是";
                                        }else{
                                            val = "否";
                                        }
                                    }
                                    $(t).parent().parent().find(".label").show().find(".show").text(val);
                                }
                            }
                        } else {
                            alert("更改失败");
                        }
                    },
                    error: function () {
                        alert("系统繁忙!");
                    }
                })
            }
        })
    </script>
</head>
<body>
<div class="container">
    <div class="row">
        <div class="col-sm-3">

            <%--导航--%>
            <jsp:include page="navigate.jsp"/>
            <%--导航--%>
        </div>
        <div class="col-sm-9">
            <div style="width:100%; height: auto;overflow:hidden;margin: 0px auto;">
                <ul class="film-info">
                    <li class="title">
                        <div class="left-con">添加影片</div>
                        <div class="right-con"></div>
                    </li>
                    <li>
                        <div class="left-con">片名:</div>
                        <div class="right-con">
                            <c:if test="${film!=null}">
                                <div class="update">
                                    <div class="label">
                                        <a class="show" href="javascript:;">${film.name}</a>
                                        <a href="javascript:;" class="btn edit">编辑</a>
                                    </div>
                                    <div class="label-input">
                                        <input class="input update-input" placeholder="请填写影片名称" autocomplete="off" el="div"
                                               name="name" value="${film.name}"/>
                                        <a href="javascript:;" class="btn save" film_id="${film.id}">保存</a>
                                        <a href="javascript:;" class="btn cancel">取消</a>
                                    </div>
                                </div>
                            </c:if>

                            <c:if test="${film==null}">
                                <input class="input addFilmInput"  id="name" placeholder="请填写影片名称" autocomplete="off" value="${film.name}"/>
                                <span style="color:red;">*</span>
                            </c:if>
                        </div>
                    </li>
                    <li>
                        <div class="left-con">海报:</div>
                        <div class="right-con">
                            <c:if test="${film!=null}">
                                <div class="update">
                                    <div class="label">
                                        <div class="update-pre">
                                            <div><img src="${film.image}"/></div>
                                        </div>
                                        <a href="javascript:;" class="btn edit" t="img">编辑</a>
                                    </div>
                                    <div class="label-input">
                                        <input id="file_upload" name="file_upload" type="file">
                                        <div class="file-pre"></div>
                                        <a href="javascript:;" class="btn save" id="save-bt" film_id="${film.id}">保存</a>
                                        <a href="javascript:;" class="btn cancel" t="img" id="cancel-bt">取消</a>
                                    </div>
                                </div>
                            </c:if>

                            <c:if test="${film==null}">
                                <input id="file_upload" name="file_upload" type="file">
                                <div class="file-pre"></div>
                            </c:if>
                        </div>
                    </li>
                    <li>
                        <div class="left-con">上映年代:</div>
                        <div class="right-con">
                            <c:if test="${film!=null}">
                                <div class="update">
                                    <div class="label">
                                        <a class="show" href="javascript:;">${film.onDecade}</a>
                                        <a href="javascript:;" class="btn edit">编辑</a>
                                    </div>
                                    <div class="label-input">
                                        <select class="input fixSelect update-input" el="select" name="onDecade">
                                            <c:forEach items="${decadeList}" var="decadeList_list">
                                                <option value="${decadeList_list.name}">${decadeList_list.name}</option>
                                            </c:forEach>
                                        </select>
                                            <%-- <div class="input update-input" contenteditable="true" name="name">${film.name}</div>--%>
                                        <a href="javascript:;" class="btn save" film_id="${film.id}">保存</a>
                                        <a href="javascript:;" class="btn cancel">取消</a>
                                    </div>
                                </div>
                            </c:if>

                            <c:if test="${film==null}">
                                <select id="onDecade" class="input fixSelect">
                                    <c:forEach items="${decadeList}" var="decadeList_list">
                                        <option value="${decadeList_list.name}">${decadeList_list.name}</option>
                                    </c:forEach>
                                </select><span style="color:red;">*</span>
                            </c:if>
                        </div>
                    </li>
                    <li>
                        <div class="left-con">状态:</div>
                        <div class="right-con">
                            <c:if test="${film!=null}">
                                <div class="update">
                                    <div class="label">
                                        <a class="show" href="javascript:;">${film.status}</a>
                                        <a href="javascript:;" class="btn edit">编辑</a>
                                    </div>
                                    <div class="label-input">
                                        <input class="input update-input" placeholder="全集/更新第几集" autocomplete="off" el="div"
                                               name="status" value="${film.status}"/>
                                        <a href="javascript:;" class="btn save" film_id="${film.id}">保存</a>
                                        <a href="javascript:;" class="btn cancel">取消</a>
                                    </div>
                                </div>
                            </c:if>

                            <c:if test="${film==null}">
                                <input class="input addFilmInput" value="全集" placeholder="全集/更新第几集" autocomplete="off" id="status"/>
                                <span style="color:red;">*</span>
                            </c:if>

                        </div>
                    </li>
                    <li>
                        <div class="left-con">分辨率:</div>
                        <div class="right-con">
                            <c:if test="${film!=null}">
                                <div class="update">
                                    <div class="label">
                                        <a class="show" href="javascript:;">${film.resolution}</a>
                                        <a href="javascript:;" class="btn edit">编辑</a>
                                    </div>
                                    <div class="label-input">
                                        <select name="resolution" class="input fixSelect update-input" el="select">
                                            <option value="1080">1080p</option>
                                            <option value="720">720p</option>
                                            <option value="480">480p</option>
                                        </select>

                                            <%-- <div class="input update-input" contenteditable="true" name="name">${film.name}</div>--%>
                                        <a href="javascript:;" class="btn save" film_id="${film.id}">保存</a>
                                        <a href="javascript:;" class="btn cancel">取消</a>
                                    </div>
                                </div>
                            </c:if>

                            <c:if test="${film==null}">
                                <select id="resolution" class="input fixSelect">
                                    <option value="1080">1080p</option>
                                    <option value="720">720p</option>
                                    <option value="480">480p</option>
                                </select>
                                <span style="color:red;">*</span>
                            </c:if>
                        </div>
                    </li>
                    <li>
                        <div class="left-con">类型:</div>
                        <div class="right-con">
                            <c:if test="${film!=null}">
                                <div class="update">
                                    <div class="label">
                                        <a class="show" href="javascript:;">${film.typeName}</a>
                                        <a href="javascript:;" class="btn edit">编辑</a>
                                    </div>
                                    <div class="label-input">
                                        <select class="cataLog_id_subClass input fixSelect">
                                            <c:forEach items="${cataLogList}" var="cataLogList_var">
                                                <option value="${cataLogList_var.id}">${cataLogList_var.name}</option>
                                            </c:forEach>
                                        </select>&nbsp;
                                        <select class="subClass_id input fixSelect"></select>&nbsp;
                                        <select name="typeName" name1="type_id" class="type_id input fixSelect update-input"
                                                id="type_id" el="select"></select>

                                            <%-- <div class="input update-input" contenteditable="true" name="name">${film.name}</div>--%>
                                        <a href="javascript:;" class="btn save" film_id="${film.id}">保存</a>
                                        <a href="javascript:;" class="btn cancel">取消</a>
                                    </div>
                                </div>
                            </c:if>

                            <c:if test="${film==null}">
                                <select class="cataLog_id_subClass input fixSelect">
                                    <c:forEach items="${cataLogList}" var="cataLogList_var">
                                        <option value="${cataLogList_var.id}">${cataLogList_var.name}</option>
                                    </c:forEach>
                                </select>&nbsp;
                                <select class="subClass_id input fixSelect"></select>&nbsp;
                                <select class="type_id input fixSelect" id="type_id"></select>
                                <span style="color:red;">*</span>
                            </c:if>
                        </div>
                    </li>
                    <li>
                        <div class="left-con">演员:</div>
                        <div class="right-con">
                            <c:if test="${film!=null}">
                                <div class="update">
                                    <div class="label">
                                        <a class="show" href="javascript:;">${film.actor}</a>
                                        <a href="javascript:;" class="btn edit">编辑</a>
                                    </div>
                                    <div class="label-input">
                                        <input class="input  update-input" placeholder="请填写演员名称，多个演员请用逗号隔开" autocomplete="off" el="div"
                                               name="actor" value="${film.actor}"/>
                                        <a href="javascript:;" class="btn save" film_id="${film.id}">保存</a>
                                        <a href="javascript:;" class="btn cancel">取消</a>
                                    </div>
                                </div>
                            </c:if>

                            <c:if test="${film==null}">
                                <input class="input  addFilmInput" placeholder="请填写演员名称，多个演员请用逗号隔开" autocomplete="off" id="actor"/>
                                <span style="color:red;">*</span>
                            </c:if>
                        </div>
                    </li>
                    <li>
                        <div class="left-con">地区:</div>
                        <div class="right-con">

                            <c:if test="${film!=null}">
                                <div class="update">
                                    <div class="label">
                                        <a class="show" href="javascript:;">${film.locName}</a>
                                        <a href="javascript:;" class="btn edit">编辑</a>
                                    </div>
                                    <div class="label-input">
                                        <select name="locName" name1="loc_id" class="input fixSelect update-input"
                                                el="select">
                                            <c:forEach items="${locList}" var="locList_list">
                                                <option value="${locList_list.id}">${locList_list.name}</option>
                                            </c:forEach>
                                        </select>
                                            <%-- <div class="input update-input" contenteditable="true" name="name">${film.name}</div>--%>
                                        <a href="javascript:;" class="btn save" film_id="${film.id}">保存</a>
                                        <a href="javascript:;" class="btn cancel">取消</a>
                                    </div>
                                </div>
                            </c:if>

                            <c:if test="${film==null}">
                                <select id="locName" class="input fixSelect">
                                    <c:forEach items="${locList}" var="locList_list">
                                        <option value="${locList_list.id}">${locList_list.name}</option>
                                    </c:forEach>
                                </select>
                                <span style="color:red;">*</span>
                            </c:if>
                        </div>
                    </li>
                    <li>
                        <div class="left-con">剧情:</div>
                        <div class="right-con">
                            <c:if test="${film!=null}">
                                <div class="update">
                                    <div class="label">
                                        <a class="show" href="javascript:;">${film.plot}</a>
                                        <a href="javascript:;" class="btn edit">编辑</a>
                                    </div>
                                    <div class="label-input">
                                    <textarea class="input contentType update-input" placeholder="请简短填写剧情介绍" autocomplete="off" el="div"
                                              name="plot">${film.plot}</textarea>
                                        <a href="javascript:;" class="btn save" film_id="${film.id}">保存</a>
                                        <a href="javascript:;" class="btn cancel">取消</a>
                                    </div>
                                </div>
                            </c:if>

                            <c:if test="${film==null}">
                                <textarea class="input contentType addFilmInput" placeholder="请简短填写剧情介绍" autocomplete="off" id="plot"></textarea>
                                <span style="color:red;">*</span>
                            </c:if>

                        </div>
                    </li>
                    <li>
                        <div class="left-con">是否vip:</div>
                        <div class="right-con">
                            <c:if test="${film!=null}">
                                <div class="update">
                                    <div class="label">
                                        <a class="show" href="javascript:;">
                                            <c:if test="${film.isVip=='1'}">是</c:if>
                                            <c:if test="${film.isVip=='0'}">否</c:if>
                                        </a>
                                        <a href="javascript:;" class="btn edit">编辑</a>
                                    </div>
                                    <div class="label-input">
                                        <select name="isVip"  id="isVip"  class="input fixSelect update-input" el="select">
                                            <c:if test="${film.isVip=='1'}">
                                                <option value="">请选择</option>
                                                <option value="1" selected>是</option>
                                                <option value="0">否</option>
                                            </c:if>
                                            <c:if test="${film.isVip=='0'}">
                                                <option value="">请选择</option>
                                                <option value="1">是</option>
                                                <option value="0" selected>否</option>
                                            </c:if>
                                        </select>
                                        <a href="javascript:;" class="btn save" film_id="${film.id}">保存</a>
                                        <a href="javascript:;" class="btn cancel">取消</a>
                                    </div>
                                </div>
                            </c:if>

                            <c:if test="${film==null}">
                                <select name="isVip"  id="isVip" class="input fixSelect update-input" el="select">
                                    <option value="">请选择</option>
                                    <option value="1">是</option>
                                    <option value="0">否</option>
                                </select>
                                <span style="color:red;">*</span>
                            </c:if>
                        </div>
                    </li>
                    <li>
                        <div class="left-con"></div>
                        <div class="right-con">
                            <c:if test="${film==null}">
                                <a href="javascript:;" class="btn" id="addFilm-btn">添加影片</a>
                                <a href="javascript:;" class="btn" onclick="clearAddFilmInput()">清空</a>
                            </c:if>
                        </div>
                    </li>

                    <c:if test="${film!=null}">
                        <li class="title">
                            <div class="left-con">影片资源</div>
                            <div class="right-con"><span class="zhuti"></span></div>
                        </li>
                        <c:if test="${res==null}">
                            <li>
                                <div style="width: auto;height: auto;margin: 20px auto;text-align: center;font-size: 12px;font-family: '微软雅黑';">
                                    暂无资源
                                </div>
                            </li>
                        </c:if>
                    </c:if>
                    <c:forEach items="${res}" var="res_list">
                        <li>
                            <ul class="zi_yuan_list">
                                <li><a href="${res_list.link}" class="show" title="${res_list.name}">${res_list.name}</a>
                                </li>
                                <li>
                                    <a href="javascript:;" class="show">${res_list.linkType}</a>
                                </li>
                                <li><a href="javascript:;" class="show isUse">
                                    <c:if test="${res_list.isUse==1}">
                                        在线
                                    </c:if>
                                    <c:if test="${res_list.isUse==0}">
                                        离线
                                    </c:if>
                                </a></li>
                                <li><a href="javascript:;" class="show">${res_list.updateTime}</a></li>
                                <li>
                                    <c:if test="${res_list.isUse==1}">
                                        <a href="javascript:;" class="btn updateIsUse" res_id="${res_list.id}">离线</a>
                                    </c:if>
                                    <c:if test="${res_list.isUse==0}">
                                        <a href="javascript:;" class="btn updateIsUse" res_id="${res_list.id}">在线</a>
                                    </c:if>
                                    <a href="javascript:;" class="btn delRes" res_id="${res_list.id}">删除</a>
                                </li>
                            </ul>
                        </li>
                    </c:forEach>
                    <li class="title">
                        <div class="left-con">添加资源</div>
                        <div class="right-con"><span class="zhuti">
                <c:if test="${film!=null}">
                    <a href="javascript:;" class="show">影片主体：${film.id}</a>
                </c:if>
                </span></div>
                    </li>
                    <li>
                        <div style="width: 200px;height: auto;margin: 10px auto;">
                            <input id="file_upload_src" name="file_upload" type="file">
                        </div>
                        <div class="file-pre-file" style="margin: 10px auto;text-align: center;"></div>
                    </li>
                    <li>
                        <div class="left-con">资源名:</div>
                        <div class="right-con">
                            <input class="input longInput addResInput" placeholder="请填写资源名" autocomplete="off" id="res_name"/>
                            <span style="color:red;">*</span><span style="color:#ddd;">(若百度资源请将密码填在此处，无需填写名称)</span><a href="javascript:;"  class="btn" onclick="setMoban()">多资源模板</a></div>
                    </li>

                    <li>
                        <div class="left-con">当前集数:</div>
                        <div class="right-con">
                            <select class="input fixSelect" id="res_episodes">
                                <c:forEach var="i" begin="1" end="100" varStatus="li">
                                    <option value="${li.index}">${li.index}</option>
                                </c:forEach>
                            </select>
                            <span style="color:red;">*</span>
                        </div>
                    </li>

                    <li>
                        <div class="left-con">链接:</div>
                        <div class="right-con">
                            <textarea class="input contentType addResInput" placeholder="请填写资源链接，并在下个选择栏选择正确的资源类型" autocomplete="off" id="res_link"></textarea>
                            <span style="color:red;">*</span></div>
                    </li>

                    <li>
                        <div class="left-con">链接类型:</div>
                        <div class="right-con">
                            <select class="input fixSelect" id="res_linkType">
                                <option value="Flh">在线</option>
                                <option value="ed2k">电驴</option>
                                <option value="thunder">迅雷</option>
                                <option value="http">离线</option>
                                <option value="dupan">度盘</option>
                                <option value="other">其它</option>
                            </select>
                            <span style="color:red;">*</span>
                        </div>
                    </li>

                    <li>
                        <div class="left-con"></div>
                        <div class="right-con">
                            <a href="javascript:;" class="btn" id="addRes-btn"
                               <c:if test="${film!=null}">film_id="${film.id}"</c:if>>添加资源+</a>
                            <a href="javascript:;" class="btn" onclick="clearAddResInput()">清空</a>
                            <a href="javascript:;" class="alert-box">温馨提示：清空后可继续添加更多资源</a>
                        </div>
                    </li>
                </ul>
            </div>
        </div>
    </div>
</div>
<script>
    var isLoc = 0;
    $(function () {

        /**
         * 同步更改二级目录
         */
        $(".cataLog_id_subClass").change(function () {
            var catalog_id = $(this).val();
            $.ajax({
                url: "getSubClass.html",
                type: "post",
                dataType: "json",
                data: "catalog_id=" + catalog_id,
                success: function (data) {
                    $(".subClass_id").find("option").remove();
                    var jss = JSON.parse(data);
                    for (var i = 0; i < jss.length; i++) {
                        var op = "<option value='" + jss[i].id + "'>" + jss[i].name + "</option>";
                        $(".subClass_id").append($(op));
                    }
                    getType();
                },
                error: function () {
                    alert("系统繁忙!");
                }
            });
        });

        /**
         * 同步更改type类型
         */
        $(".subClass_id").change(function () {
            getType();
        });

        /**
         * 初始目录
         */
        $.ajax({
            url: "getSubClass.html",
            type: "POST",
            dataType: "json",
            data: "catalog_id=" + $(".cataLog_id_subClass").val(),
            success: function (data) {
                var jss = JSON.parse(data);
                for (var i = 0; i < jss.length; i++) {
                    var op1 = "<option value='" + jss[i].id + "'>" + jss[i].name + "</option>";
                    $(".subClass_id").append($(op1));
                }
                getType();
            },
            error: function () {
                alert("系统繁忙!");
            }
        });

        /**
         * 获取类型
         */
        function getType() {
            $.ajax({
                url: "getType.html",
                type: "POST",
                dataType: "json",
                data: "subClass_id=" + $(".subClass_id").val(),
                success: function (data) {
                    $(".type_id").find("option").remove();
                    var jss = JSON.parse(data);
                    for (var i = 0; i < jss.length; i++) {
                        var op1 = "<option value='" + jss[i].id + "'>" + jss[i].name + "</option>";
                        $(".type_id").append($(op1));
                    }
                },
                error: function () {
                    alert("系统繁忙!");
                }
            });
        }


        /**
         * 添加film
         */
        $("#addFilm-btn").click(function () {

            var name_val = $("#name").val();
            var image_val = $("#image").attr("src");
            var onDecade_val = $("#onDecade").val();
            var status_val = $("#status").val();
            var resolution_val = $("#resolution").val();
            var typeName_val = $("#type_id").find("option:selected").text();
            var type_id_val = $("#type_id").val();
            var actor_val = $("#actor").val();
            var locName_val = $("#locName").find("option:selected").text();
            var loc_id_val = $("#locName").val();
            var plot_val = $("#plot").val();
            var is_vip = $("#isVip").val();
            if (name_val == ""
                    || image_val == ""
                    || image_val == undefined
                    || onDecade_val == ""
                    || status_val == ""
                    || resolution_val == ""
                    || typeName_val == ""
                    || type_id_val == ""
                    || actor_val == ""
                    || locName_val == ""
                    || loc_id_val == ""
                    || plot_val == ""
                    || is_vip == ""
            ) {
                if (name_val == "") {
                    alert("片名为空");
                } else if (image_val == "") {
                    alert("海报为空");
                } else if (onDecade_val == "") {
                    alert("年代为空");
                } else if (status_val == "") {
                    alert("状态为空");
                } else if (resolution_val == "") {
                    alert("分辨率为空");
                } else if (typeName_val == "") {
                    alert("类型名称为空");
                } else if (type_id_val == "") {
                    alert("类型编码为空");
                } else if (actor_val == "") {
                    alert("演员为空");
                } else if (locName_val == "") {
                    alert("地区名为空");
                } else if (loc_id_val == "") {
                    alert("地区编码为空");
                } else if (plot_val == "") {
                    alert("剧情为空");
                } else if (image_val == undefined) {
                    alert("海报为空哟");
                }else if (is_vip == undefined) {
                    alert("请选择是否为VIP");
                }
            } else {
                //添加影片
                $.ajax({
                    url: "addFilm.html",
                    type: "post",
                    dataType: "json",
                    data: "name=" + name_val +
                    "&image=" + image_val +
                    "&onDecade=" + onDecade_val +
                    "&status=" + status_val +
                    "&resolution=" + resolution_val +
                    "&typeName=" + typeName_val +
                    "&type_id=" + type_id_val +
                    "&actor=" + actor_val +
                    "&locName=" + locName_val +
                    "&loc_id=" + loc_id_val +
                    "&plot=" + plot_val+
                    "&isVip=" + is_vip,
                    success: addSuccess,
                    error: function () {
                        alert("系统繁忙！");
                    }
                });
            }

        });



        /**
         * 添加影片资源
         */
        $("#addRes-btn").click(function () {
            var film_id = $(this).attr("film_id");

            var res_name_val = $("#res_name").val();
            var res_episodes_val = $("#res_episodes").val();
            var res_link_val = $("#res_link").val();
            var res_linkType_val = $("#res_linkType").val();
            if (film_id == undefined || film_id === "") {
                alert("没有影片主体");
            } else if (res_name_val == ""
                    || res_episodes_val == ""
                    || res_link_val == ""
                    || res_linkType_val == ""
            ) {
                alert("信息未填写完整，请检查");
            } else {
                $.ajax({
                    url: "addRes.html",
                    type: "POST",
                    dataType: "json",
                    data: "film_id=" + film_id +
                    "&name=" + res_name_val +
                    "&episodes=" + res_episodes_val +
                    "&link=" + res_link_val +
                    "&linkType=" + res_linkType_val
                    ,
                    success: function (data) {
                        if (data != "0") {
                            alert("添加成功");
                            var film = "${film.id}";
                            if (film != "") {
                                location.reload();
                            }

                        } else {
                            alert("添加失败");
                        }
                    },
                    error: function () {
                        alert("系统繁忙!");
                    }
                });
            }
        });


        /**
         * 添加影片成功
         */
        function addSuccess(data) {

            if (data != "0") {
                $("#addRes-btn").attr("film_id", data);
                $(".zhuti").text("影片主体：" + data);
                alert("添加影片成功");
            } else {
                alert("添加影片失败");
            }
        }

        /**
         * 更改资源在线离线状态
         */
        $(".updateIsUse").click(function () {
            $.ajax({
                url: "updateIsUse.html",
                type: "post",
                dataType: "json",
                data: "res_id=" + $(this).attr("res_id"),
                success: function (data) {
                    if ("1" == data) {
                        alert("修改成功");
                        location.reload();
                    } else {
                        alert("修改失败");
                    }
                }
            });
        })

        /**
         * 删除资源
         */
        $(".delRes").click(function () {

            var isEnsure = confirm("确定删除?");
            if (isEnsure) {
                $.ajax({
                    url: "delRes.html",
                    type: "post",
                    dataType: "json",
                    data: "res_id=" + $(this).attr("res_id"),
                    success: function (data) {
                        if (data == "1") {
                            alert("删除成功");
                            location.reload();
                        }
                    },
                    error: function () {
                        alert("删除失败!");
                    }
                });
            }
        })

    })

    /**
     *
     * 清空资源表单
     */
    function clearAddResInput() {
        $(".addResInput").val("");
    }


    /**
     *
     * 清空影片表单
     */
    function clearAddFilmInput() {
        $(".addFilmInput").val("");
    }

    /**
     * 文件上传
     */
    var timestamp = new Date().getTime();
    $("#file_upload").uploadify({
        'formData': {
            'timestamp': timestamp,
            'childPath': 'filmPic',
            'token': 'unique_salt' + timestamp
        },// 设置想后台传递的参数 如果设置该参数，那么method应该设置为get，才能得到参数
        "width": 120,
        "height": 30,
        "swf": "plugins/uploadify/uploadify.swf",
        "fileObjName": "download",
        "buttonText": "上传海报",
        "uploader": "upload.html",
        'cancelImg': 'plugins/uploadify/uploadify.swf',// 取消按钮图片路径
        'removeTimeout': 1,
        'method': 'post',
        'fileTypeExts': '*.jpg',
        'simUploadLimit': 1,
        'auto': true,// 当选中文件后是否自动提交
        'uploadLimit': 0,
        'multi': false,//支持多文件上传
        "onUploadSuccess": uploadFinish,
        'onFallback': function () {
            alert('未检测到兼容版本的Flash.');
        }
    });


    $("#file_upload_src").uploadify({
        'formData': {
            'timestamp': timestamp,
            'childPath': 'filmFile',
            'token': 'unique_salt' + timestamp
        },// 设置想后台传递的参数 如果设置该参数，那么method应该设置为get，才能得到参数
        "width": 120,
        "height": 30,
        "swf": "plugins/uploadify/uploadify.swf",
        "fileObjName": "download",
        "buttonText": "上传本地影片",
        "uploader": "upload.html",
        'cancelImg': 'plugins/uploadify/uploadify.swf',// 取消按钮图片路径
        'removeTimeout': 1,
        'method': 'post',
        'fileTypeExts': '*',
        'simUploadLimit': 1,
        'auto': true,// 当选中文件后是否自动提交
        'uploadLimit': 0,
        'multi': false,//支持多文件上传
        "onUploadSuccess": uploadFileFinish,
        'onFallback': function () {
            alert('未检测到兼容版本的Flash.');
        }
    });


    function uploadFileFinish(file, data) {
        var file_info = JSON.parse(JSON.parse(data))[0];
        var filePath = file_info.filePath;
        var file_name = file_info.fileName;
        var org_file_name = file_name;
        file_name = file_name.substr(0,file_name.lastIndexOf("."));
        isLoc = 1;
        /**设置资源名称*/
        $("#res_name").val(file_name);

        /**设置资源链接*/
        $("#res_link").val(filePath);

        $(".file-pre-file").append($("<div path='"+filePath+"'>"+org_file_name+"<a onClick='del(this,1)' class='del'>删除</a></div>"));
    }

    function uploadFinish(file, data) {
        var file_info = JSON.parse(JSON.parse(data))[0].filePath;
        $(".file-pre").append($("<div><img src='" + file_info + "' class='update-input' id='image' el='img' name='image'/><a onClick='del(this,2)' class='del'>删除</a></div>"));
        setTimeout(function () {
            $("#cancel-bt").show();
            $("#save-bt").show();
            $("#file_upload").hide();
        }, 1000);
    }


    /**删除图片*/
    function del(e,type) {
        if (confirm("确定删除吗")) {
            var path = $(e).parent("div").find("img").attr("src");
            if(type==1){
                path =  $(e).parent("div").attr("path");
            }
            $.ajax({
                type: "post",
                url: "delFile.html",
                cache: false,
                data: "picsPath=" + path,
                dataType: 'json',     //接受数据格式
                success: function (rs) {
                    var data = JSON.parse(rs);
                    debugger;
                    if (data.code == "1") {
                        $(e).parent("div").remove();
                        if(type!=1){
                            $("#file_upload").show();
                        }else{
                            location.reload();
                        }
                    } else {
                        alert("删除失败!");
                    }
                },
                error: function () {
                    alert("系统繁忙!");
                }
            });
        }
    }
</script>
</body>
</html>
