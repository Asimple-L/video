<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/jsp/pub/include.jsp" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<c:set var="version" value="<%=new Date().getTime()%>"></c:set>
<c:set var="prourl" value="<%=basePath%>"></c:set>
<c:set var="proname" value="${pageContext.request.contextPath}"></c:set>
<html>
<head>
    <base href="<%=basePath%>">
    <title>
        <c:if test="${film==null}">
            新增资源
        </c:if>
        <c:if test="${film!=null}">
            修改资源
        </c:if>
    </title>
    <link rel="shortcut icon" href="${pageIcon}">
    <link rel="stylesheet" href="${proname}/public/static/css/manager/addFilm.css">
    <link rel="stylesheet" href="${proname}/plugins/bootflat-admin/css/site.min.css">
    <script src="${proname}/plugins/bootflat-admin/js/site.min.js"></script>
    <script src="${proname}/public/static/js/jquery-2.0.0.min.js?v=${version}" type="text/javascript" charset="UTF-8" ></script>
    <!--=====================CSS_Link===========================-->
    <link rel="stylesheet" href="${proname}/plugins/uploadify/uploadify.css" type="text/css">
    <!--=====================JS_Link===========================-->
    <script type="text/javascript" src="${proname}/plugins/uploadify/jquery.uploadify.min.js"></script>
</head>
<body>
<jsp:include page="header.jsp"/>

<div class="container-fluid">
    <div class="row row-offcanvas row-offcanvas-left">
        <jsp:include page="navLeft.jsp"/>
        <div class="col-xs-12 col-sm-9 content">
            <div class="panel panel-default">
                <div class="panel-heading">
                    <h3 class="panel-title">
                        <a href="javascript:void(0);" class="toggle-sidebar"><span class="fa fa-angle-double-left" data-toggle="offcanvas" title="Maximize Panel"></span>
                        </a>
                        <c:if test="${film==null}">
                            影视资源添加
                        </c:if>
                        <c:if test="${film!=null}">
                            影视资源信息修改
                        </c:if>
                    </h3>
                </div>
                <div class="panel-body">
                    <div class="content-row">
                        <div class="row">

                            <%--  正文开始 --%>

                            <div style="width:100%; height: auto;overflow:hidden;margin: 0px auto;">
                                <ul class="film-info">
                                    <li class="title">
                                        <div class="left-con">
                                            <c:if test="${film==null}">
                                                添加影片
                                            </c:if>
                                            <c:if test="${film!=null}">
                                                影片修改

                                            </c:if>
                                        </div>
                                        <div class="right-con">
                                            <c:if test="${film!=null}">
                                                <a href="javascript:void(0);" class="btn btn-danger del-film-btn" style="color: #FFF;" film_id="${film.id}">删除影片</a>
                                            </c:if>
                                        </div>
                                    </li>
                                    <li>
                                        <div class="left-con">片名:</div>
                                        <div class="right-con">
                                            <c:if test="${film!=null}">
                                                <div class="update">
                                                    <div class="label my-label">
                                                        <a class="showInfo" href="javascript:;">${film.name}</a>
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
                                                    <div class="label my-label">
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
                                                    <div class="label my-label">
                                                        <a class="showInfo" href="javascript:;">${film.onDecade}</a>
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
                                                    <div class="label my-label">
                                                        <a class="showInfo" href="javascript:;">${film.status}</a>
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
                                                    <div class="label my-label">
                                                        <a class="showInfo" href="javascript:;">${film.resolution}</a>
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
                                                    <div class="label my-label">
                                                        <a class="showInfo" href="javascript:;">${film.typeName}</a>
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
                                                    <div class="label my-label">
                                                        <a class="showInfo" href="javascript:;">${film.actor}</a>
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
                                                    <div class="label my-label">
                                                        <a class="showInfo" href="javascript:;">${film.locName}</a>
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
                                                    <div class="label my-label">
                                                        <a class="showInfo" href="javascript:;">${film.plot}</a>
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
                                                    <div class="label my-label">
                                                        <a class="showInfo" href="javascript:;">
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
                                        <div class="right-con">
                                            <span class="zhuti">
                                                <c:if test="${film!=null}">
                                                    <a href="javascript:;" class="show">影片主体：${film.id}</a>
                                                </c:if>
                                            </span>
                                        </div>
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

                           <%--  正文结束 --%>

                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>


<script src="${proname}/public/static/js/mine/backHeader.js"></script>
<script src="${proname}/public/static/js/mine/addFilm.js"></script>
</body>
</html>
