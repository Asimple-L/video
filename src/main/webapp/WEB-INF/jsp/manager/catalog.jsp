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
    <title>首页</title>
    <jsp:include page="/WEB-INF/jsp/pub/head_meta.jsp"/>
    <jsp:include page="/WEB-INF/jsp/pub/head_link.jsp"/>
    <jsp:include page="/WEB-INF/jsp/pub/head_script.jsp"/>
    <!--=====================CSS_Link===========================-->
    <!--=====================JS_Link===========================-->

    <style>
        *{
            margin: 0px;
            padding: 0px;
            zoom: 1;
            font-size: 12px;
            font-family: '微软雅黑';
        }
        a{
            text-decoration: none;
            color:#444;
        }
        .catalog_name {
            width: 200px;
            height: 25px;
            border: 1px solid #ccc;
            border-radius: 3px;
            background: #C3DDEC;
            padding-left: 10px;
            line-height: 25px;
            outline: none;
        }

        .subClass_name {
            width: 200px;
            height: 25px;
            border: 1px solid #ccc;
            border-radius: 3px;
            background: #C3DDEC;
            padding-left: 10px;
            line-height: 25px;
            outline: none;
        }

        .type_name {
            width: 200px;
            height: 25px;
            border: 1px solid #ccc;
            border-radius: 3px;
            background: #C3DDEC;
            padding-left: 10px;
            line-height: 25px;
            outline: none;
        }

        .loc_name {
            width: 200px;
            height: 25px;
            border: 1px solid #ccc;
            border-radius: 3px;
            background: #C3DDEC;
            padding-left: 10px;
            line-height: 25px;
            outline: none;
        }

        .level_name {
            width: 200px;
            height: 25px;
            border: 1px solid #ccc;
            border-radius: 3px;
            background: #C3DDEC;
            padding-left: 10px;
            line-height: 25px;
            outline: none;
        }

        .decade_name {
            width: 200px;
            height: 25px;
            border: 1px solid #ccc;
            border-radius: 3px;
            background: #C3DDEC;
            padding-left: 10px;
            line-height: 25px;
            outline: none;
        }
        /*box框架*/
        .box{
            width: 1190px;
            height:auto;
            overflow: hidden;
            margin: 0px auto;
        }
        .box-left{
            width: 290px;
            height: auto;
            float: left;
            overflow: hidden;
        }
        .box-right{
            width: 900px;
            height: auto;
            float: right;
            overflow: hidden;
        }
        /*box框架*/
        /*按钮*/
        .btn{
            padding: 4px 8px;
            border-radius:4px;
            background: #efefef;
            border:1px solid #cccccc;
            text-decoration: none;
            color:#444;
            margin-right: 10px;
            display: inline-block;
        }
        .btn:hover{
            background: #1d94ff;
            border:1px solid #1d94ff;
            color:white;
        }
        /*按钮*/


        /*导航*/
        .nav-title{
            width: 100%;
            height: 30px;
            overflow: hidden;
            line-height: 30px;
            font-size: 12px;
            font-family: '微软雅黑';
            cursor: pointer;

        }
        .nav-icon{
            font-style: normal;
            height: 100%;
            width: 30px;
            display: inline-block;
            background: #fff url("static/img/pub/nav.jpg") -144px -27px no-repeat;/*-143px -27px*/
        }
        .nav1-ul{
            width:60%;
            height: auto;
            overflow: hidden;
            margin: 0px auto;
        }
        .nav1-li{
            width:100%;
            height: auto;
            overflow: hidden;
        }
        .nav2-ul{
            width: 100%;
            height: auto;
            overflow: hidden;
        }
        .nav2-li{
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
                if($(this).find(".nav-icon").css("background-position")=="-144px -27px"){
                    $(this).find(".nav-icon").css("background-position","-92px -27px");
                }else{
                    $(this).find(".nav-icon").css("background-position","-144px -27px");
                }
            });
        })
    </script>
    <%--导航--%>
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
            <div style="width:50%;float:left;height: auto;overflow: hidden;">
                <span>一级分类</span><br/>
                <div contenteditable="true" class="catalog_name" style="display: inline-block;"></div>
                <a href="javascript:;" class="add_CataLog_btn btn">添加</a><br/><br/>

                <span>二级子分类</span>
                <select class="cataLog_id">
                    <c:forEach items="${cataLogList}" var="cataLogList_var">
                        <option value="${cataLogList_var.id}">${cataLogList_var.name}</option>
                    </c:forEach>

                </select><br/>
                <div contenteditable="true" class="subClass_name" style="display: inline-block;"></div>
                <a href="javascript:;" class="add_SubClass_btn btn">添加</a><br/><br/>

                <span>类型</span><br/>
                <select class="cataLog_id_subClass">
                    <c:forEach items="${cataLogList}" var="cataLogList_var">
                        <option value="${cataLogList_var.id}">${cataLogList_var.name}</option>
                    </c:forEach>
                </select>
                <select class="subClass_id"></select><br/>
                <div contenteditable="true" class="type_name" style="display: inline-block;"></div>
                <a href="javascript:;" class="add_Type_btn btn">添加</a><br/><br/>
                <br/>

                <h2><span style="color:blue;">一级目录</span>--><span style="color:green;">二级目录</span>--><span style="color:#fd5c11;">类型</span></h2>
                <c:forEach items="${cataLogList}" var="list">
                    <span style="color:blue;">${list.name}</span><br/>
                    <c:forEach items="${list.subClassList}" var="li">
                        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span style="color:green;">${li.name}</span>-->
                        <c:forEach items="${typeList}" var="l">
                            <c:if test="${li.id==l.subClass.id}">
                                <span style="color:#fd5c11;">${l.name}</span>
                            </c:if>
                        </c:forEach>
                        <br/>
                    </c:forEach>
                    <br/>
                </c:forEach>
            </div>
            <div style="width:50%;float:left;height: auto;overflow: hidden;">


                <span>地区</span><br/>
                <div contenteditable="true" class="loc_name" style="display: inline-block;"></div>
                <a href="javascript:;" class="add_Loc_btn btn" >添加</a><br/><br/>


                <span>级别</span><br/>
                <div contenteditable="true" class="level_name" style="display: inline-block;"></div>
                <a href="javascript:;" class="add_Level_btn btn">添加</a><br/><br/>


                <span>年代</span><br/>
                <div contenteditable="true" class="decade_name" style="display: inline-block;"></div>
                <a href="javascript:;" class="add_Decade_btn btn">添加</a><br/><br/>


                <h2>地区-->级别-->年代</h2>
                <br/>
                <span>地区--></span>
                <c:forEach items="${locList}" var="locList_list">
                    ${locList_list.name}
                </c:forEach>
                <br/>
                <span>级别--></span>
                <c:forEach items="${levelList}" var="levelList_list">
                    ${levelList_list.name}
                </c:forEach>
                <br/>

                <span>年代--></span>
                <c:forEach items="${decadeList}" var="decadeList_list">
                    ${decadeList_list.name}
                </c:forEach>
                <br/>

            </div>
        </div>
        </div>
    </div>
<script>
    $(function () {
        $(".add_CataLog_btn").click(function () {
            var catalog_name = $(".catalog_name").text();
            if(catalog_name!=""){
                $.ajax({
                    url: "addCataLog.html",
                    type: "POST",
                    dataType: "json",
                    data: "name=" + catalog_name,
                    success: backFunction,
                    error: function () {
                        alert("系统繁忙!");
                    }
                });
            }else{
                alert("字段为空！");
            }
        });

        $(".add_SubClass_btn").click(function () {
            var subClass_name = $(".subClass_name").text();
            if(subClass_name!="") {
                $.ajax({
                    url: "addSubClass.html",
                    type: "POST",
                    dataType: "json",
                    data: "cataLog_id=" + $(".cataLog_id").val() + "&name=" + $(".subClass_name").text(),
                    success: backFunction,
                    error: function () {
                        alert("系统繁忙!");
                    }
                });
            }else{
                alert("字段为空！");
            }
        });

        $(".add_Type_btn").click(function () {
            var type_name = $(".type_name").text();
            if(type_name!=""){
                $.ajax({
                    url: "addType.html",
                    type: "POST",
                    dataType: "json",
                    data: "subClass_id=" + $(".subClass_id").val() + "&name=" + $(".type_name").text(),
                    success: backFunction,
                    error: function () {
                        alert("系统繁忙!");
                    }
                });
            }else{
                alert("字段为空！");
            }
        });

        $(".add_Loc_btn").click(function () {
            var loc_name = $(".loc_name").text();
            if(loc_name!=""){
                $.ajax({
                    url: "addLoc.html",
                    type: "POST",
                    dataType: "json",
                    data: "name=" + $(".loc_name").text(),
                    success: backFunction,
                    error: function () {
                        alert("系统繁忙!");
                    }
                });
            }else{
                alert("字段为空！");
            }
        });

        $(".add_Level_btn").click(function () {
            var level_name = $(".level_name").text();
            if(level_name!="") {
                $.ajax({
                    url: "addLevel.html",
                    type: "POST",
                    dataType: "json",
                    data: "name=" + $(".level_name").text(),
                    success: backFunction,
                    error: function () {
                        alert("系统繁忙!");
                    }
                });
            }else{
                alert("字段为空！");
            }
        });

        $(".add_Decade_btn").click(function () {
            var decade_name = $(".decade_name").text();
            if(decade_name!="") {
                $.ajax({
                    url: "addDecade.html",
                    type: "POST",
                    dataType: "json",
                    data: "name=" + $(".decade_name").text(),
                    success: backFunction,
                    error: function () {
                        alert("系统繁忙!");
                    }
                });
            }else{
                alert("字段为空！");
            }
        });

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
                },
                error: function () {
                    alert("系统繁忙!");
                }
            });
        });

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
            },
            error: function () {
                alert("系统繁忙!");
            }
        });
    })

    function backFunction(data) {
        if (data != "0") {
            location.reload();
        }
    }
</script>
</body>
</html>
