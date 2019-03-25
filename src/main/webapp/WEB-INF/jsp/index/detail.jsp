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
    <title>${film.name}-${film.subClassName}-${film.typeName}-<f:message key="siteName"/></title>
    <jsp:include page="/WEB-INF/jsp/pub/head_meta.jsp"/>
    <jsp:include page="/WEB-INF/jsp/pub/head_link.jsp"/>
    <jsp:include page="/WEB-INF/jsp/pub/head_script.jsp"/>
    <!--=====================CSS_Link===========================-->
    <!--=====================JS_Link===========================-->
    <script src="public/static/js/download/base64.js?v=${version}"></script>

    <%--导入--%>
    <script>var sitePath = ''</script>
    <script src="public/static/js/download/function1.js?v=${version}"></script>
    <script type="text/javascript" src="public/static/js/download/function2.js?v=${version}"></script>
    <%--导入--%>

    <%--评分插件--%>
    <script src="public/static/js/detail/jquery.raty.min.js?v=${version}" type="text/javascript"></script>
    <link rel="stylesheet" type="text/css" href="public/static/css/index/detail.css?v=${version}">
    <script type="text/javascript">
        window.prourl = "${prourl}";
        window.filmId = "${film.id}";
        window.filmSrc = Base64.decode("${src}");
    </script>
    <script type="text/javascript">

        /**窗口变化重新计算大小*/
        function updateFilmSize() {
            var w = $(".player").width();
            var h = Math.floor( (w * 9) / 16);
            $(".player").css("height", h + "px");
        }

        $(document).ready(function(){
            updateFilmSize();
            $(window).resize(function(){
                updateFilmSize();
            });
        });

        function updateLikeImgSize() {
            var w = $(".img-list").width() / 8;
            if ($(".img-list").width() < 500) {
                w = $(".img-list").width() / 3;
            }
            w = parseInt(w-8);
            $(".img-list li").css("width", w + "px");
            var h = (w * 160) / 115;
            $(".img-list li").css("height", h + "px");
        }

        $(function(){
            updateLikeImgSize();
            updateFilmSize();
            $(window).resize(function(){
                updateLikeImgSize();
                updateFilmSize();
            });
        });
    </script>
</head>
<body>
<%--头部--%>
<jsp:include page="/WEB-INF/jsp/pub/head.jsp"></jsp:include>
<div class="container" style="margin-top: 50px;" >
    <c:if test="${u_skl==null}">
        <div class="row">
            <div class="col">
                <marquee  onmouseover='this.stop()' onmouseout='this.start()'><a id="globalLoginBtn_register_input" href="registerInput.html">注册</a>|<a data-toggle="modal" data-target="#myModal" href="javascript:;">登录</a>后观看更多高清无码视频</marquee>
            </div>
        </div>
    </c:if>

    <div class="row" style="background: #dcdfe2;">
        <div class="col">
            <%--*当前位置:--%>
            <div class="wz">
                当前位置: <a href="/">首页</a>
                &nbsp;&nbsp;»&nbsp;&nbsp;<a href="xl/1.html?cataLog_id=${film.cataLog_id}">${film.cataLogName}</a>
                &nbsp;&nbsp;»&nbsp;&nbsp;<a href="xl/1.html?subClass_id=${film.subClass_id}">${film.subClassName}</a>
                &nbsp;&nbsp;»&nbsp;&nbsp;<a href="xl/1.html?type_id=${film.type_id}">${film.typeName}</a>
                &nbsp;&nbsp;»&nbsp;&nbsp;${film.name}
            </div>
        </div>
    </div>

    <div class="row">
        <div class="col-sm-8">
            <div class="row">
                <div class="col-sm-4" style="margin-top: 10px;margin-bottom: 10px;">
                    <div class="pic">
                        <img class="lazy" data-original="${film.image}" alt="${film.name}">
                        <a href="xl/detail.html?film_id=${film.id}#kan">立即播放</a>
                    </div>
                </div>
                <div class="col-sm-8" style="margin-top: 10px;margin-bottom: 10px;">
                    <div class="info" style="background: #fff;padding: 10px;">
                        <h1>${film.name}</h1>
                        <ul>
                            <li>
                                <span>上映年代：</span>${film.onDecade}&nbsp;&nbsp;
                                <span>状态：</span>${film.status}
                            </li>
                            <li>
                                <span>类型：</span>
                                <a href="xl/1.html?type=${film.type_id}" target="_blank">${film.typeName}</a>
                            </li>
                            <li>
                                <span>主演：</span>
                                <a href="javascript:;">${film.actor}</a>
                            </li>
                            <li>
                                <span>地区：</span>${film.locName}
                            </li>
                            <li>
                                <span>更新日期：</span>${film.updateTime}&nbsp;&nbsp;
                            </li>
                            <li>
                                <span>剧情：</span>${fn:substring(film.plot, 0, 100)}[<a href="xl/detail.html?film_id=${film.id}#desc">详细</a>]
                            </li>
                            <div class="pfen">
                                <p>影片评价</p>
                                <div id="star" data-score="${fn:split(film.evaluation,".")[0]}" data-number="10"></div>
                                <div class="fen" id="filmStarScoreTip">
                                <span class="no c1" id="filmStarScore">
                                    ${fn:split(film.evaluation,".")[0]}
                                <c:if test="${fn:containsIgnoreCase(film.evaluation,'.')}">
                                    <i>.${fn:split(film.evaluation,".")[1]}</i>
                                </c:if>
                                </span>
                                    <c:if test="${film.evaluation>=1&&film.evaluation<2}">很烂</c:if>
                                    <c:if test="${film.evaluation>=2&&film.evaluation<4}">一般</c:if>
                                    <c:if test="${film.evaluation>=4&&film.evaluation<6}">不妨一看</c:if>
                                    <c:if test="${film.evaluation>=6&&film.evaluation<8}">比较精彩</c:if>
                                    <c:if test="${film.evaluation>=8&&film.evaluation<=10}">棒极了</c:if>
                                    (${totalRatys}评)

                                    <%--分享--%>
                                    <div class="bdsharebuttonbox" style="float: right;">
                                        <a href="#" class="bds_more" data-cmd="more"></a>
                                        <a href="#" class="bds_qzone" data-cmd="qzone"></a>
                                        <a href="#" class="bds_tsina" data-cmd="tsina"></a>
                                        <a href="#" class="bds_tqq" data-cmd="tqq"></a>
                                        <a href="#" class="bds_renren" data-cmd="renren"></a>
                                        <a href="#" class="bds_weixin" data-cmd="weixin"></a>
                                    </div>
                                    <script>window._bd_share_config = {
                                        "common": {
                                            "bdSnsKey": {},
                                            "bdText": "",
                                            "bdMini": "2",
                                            "bdPic": "",
                                            "bdStyle": "0",
                                            "bdSize": "16"
                                        },
                                        "share": {},
                                        "image": {
                                            "viewList": ["qzone", "tsina", "tqq", "renren", "weixin"],
                                            "viewText": "分享到：",
                                            "viewSize": "16"
                                        },
                                        "selectShare": {
                                            "bdContainerClass": null,
                                            "bdSelectMiniList": ["qzone", "tsina", "tqq", "renren", "weixin"]
                                        }
                                    };
                                    with (document)0[(getElementsByTagName('head')[0] || body).appendChild(createElement('script')).src = 'http://bdimg.share.baidu.com/static/api/js/share.js?v=89860593.js?cdnversion=' + ~(-new Date() / 36e5)];</script>
                                    <%--分享--%>
                                </div>
                            </div>
                        </ul>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col">
                    <c:forEach items="${resListDupan}" var="reslist" varStatus="i">
                        <c:if test="${i.index==0}">
                            <div class="updatetps clearfix">更新小提示：度盘：${reslist.link} 密码：${reslist.name}</div>
                        </c:if>
                    </c:forEach>
                </div>
            </div>
        </div>
        <div class="col-sm-4" style="margin-top: 10px;margin-bottom: 10px;">
            <div class="infoad" >
                <div class="skydrive">
                    <p><span>友情提示：</span>欢迎大家使用网盘连接下载！</p>
                    <ul>
                        <c:forEach items="${resListDupan}" var="relist">
                            <li class="wbaidu"><a href="${relist.link}" target="_blank">密码：${relist.name}</a></li>
                        </c:forEach>
                    </ul>
                </div>
            </div>
        </div>
    </div>

    <!--间隔-->
    <div class="row" style="background: #dcdfe2;height: 10px;"></div>

    <div class="row">
        <div class="col">
            <div class="endpage clearfix">
                <div class="ulike">
                    <div class="title"><span>猜你喜欢</span></div>
                    <ul class="img-list imglist clearfix">
                        <c:forEach items="${films}" var="list" begin="0" end="8">
                            <li><a class="play-img" href="xl/detail.html?film_id=${list.id}" title="${list.name}"
                                   target="_blank">
                                <img class="lazy" data-original="${list.image}" src="" alt="${list.name}"><i></i><em>
                                <c:if test="${list.resolution=='1080'}">1280高清</c:if>
                                <c:if test="${list.resolution!='1080'}">${list.resolution}P</c:if>
                            </em>
                            </a>
                                <h5><a href="xl/detail.html?film_id=${list.id}" title="${list.name}"
                                       target="_blank">${list.name}</a></h5></li>
                        </c:forEach>
                    </ul>
                </div>
            </div>
        </div>
    </div>
    <!--间隔-->
    <div class="row" style="background: #dcdfe2;height: 10px;"></div>

    <div class="row">
        <a id="kan"></a>
        <div class="col">
            <!--在线播放 START-->
           <c:if test="${resListFlh.size()!=0}">
            <div style="background: white;width: 100%;height: auto;margin-top:10px;padding: 5px;">
                <div class="row">
                    <div class="col-sm-6">
                        <p><span class="title-icon">在线播放</span></p>
                    </div>
                    <div class="col-sm-6">
                        <p>
                        <span  class="title-con">
                            此处支持在线播放，若不能播放请下载后观看或者点击
                            <a href="javascript:;" onclick="fk('y1')" style="color:red;">失效一键反馈</a>
                        </span>
                        </p>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col">
                    <c:if test="${src!=null}">
                    <div class="player">
                        <iframe src="xl/pc.html" height="100%" width="100%" frameborder="0" scrolling="no"></iframe>
                    </div>
                    </c:if>
                    <div class="mad-box-form">
                        <c:forEach items="${resListFlh}" var="li">
                            <a class="mad-a" href="javascript:;" data="${li.link}" j="${li.episodes}" id="flh${li.episodes}" onclick="Flh(this)">${li.episodes}集</a>
                        </c:forEach>
                    </div>
                </div>
            </div>
            </c:if>
            <!--在线播放 END-->
        </div>
    </div>

    <div class="row" style="margin-top: 10px;">
        <div class="col">
            <%--下载地址--%>
            <c:if test="${resListHttp.size()!=0||resListOther.size()!=0||resListThunder.size()!=0||resListEd2k.size()!=0}">
                <div class="mox">
                    <a id="down"></a>
                        <%--下载地址1--%>
                    <c:if test="${resListEd2k.size()!=0}">
                        <div style="background: white;width: 100%;height: auto;padding: 5px;">
                            <div class="row">
                                <div class="col-sm-6">
                                     <span class="title-icon">下载地址</span>
                                </div>
                                <div class="col-sm-6">
                                    <span class="title-con">
                                        此处支持迅雷、QQ旋风、小米路由下载，同时支持迅雷影音播放，若不能下载请点击
                                        <a href="javascript:;" onclick="fk('x2')" style="color:red;">失效一键反馈</a>
                                    </span>
                                </div>
                            </div>
                        </div>

                        <div class="row">
                            <div class="col">
                                <div class="ndownlist">
                                    <script>
                                        <c:if test="${resListEd2k.size()!=0}">
                                        var GvodUrls = "<c:forEach items="${resListEd2k}" var="relist" varStatus="s"><c:if test="${s.index>0}">###${relist.link}</c:if><c:if test="${s.index==0}">${relist.link}</c:if></c:forEach>";
                                        </c:if>
                                    </script>
                                    <c:if test="${resListEd2k.size()!=0}">
                                        <script src="public/static/js/download/xunlei.js"></script>
                                        <script src="public/static/js/download/check.js"></script>
                                        <ul>
                                            <script>
                                                var mvName = "";
                                                var j = 0;
                                                for (var i = 0; i < GvodUrlLen; i++) {
                                                    mvName = get_movie_name(GvodUrlArray[i], '/');
                                                    j++;
                                                    if (j == 1) {
                                                        uclass = "class=post2";
                                                        j = -1;
                                                    } else {
                                                        uclass = "";
                                                    }
                                                    xmhref = "https://d.miwifi.com/d2r/?url=" + Base64.encodeURI(ThunderEncode(GvodUrlArray[i])) + "&src=xunbo";
                                                    document.writeln('<li><div class="row" style="padding: 4px 0px;"><div class="col-sm-6"><a style="margin-left: 10px;" oncontextmenu=ThunderNetwork_SetHref_b(this) onclick="return xunbotask(this)" href="javascript:void(0)" thunderResTitle="" thunderType="" thunderPid="20369" thunderHref="' + ThunderEncode(GvodUrlArray[i]) + '" >' + getSubstr(GvodUrlArray[i]) + '</a></div><div class="col-sm-6"><span><a class=d5 href="' + ThunderEncode(GvodUrlArray[i]) + '" target=_blank title="迅雷高速下载">迅雷</a><a class=d1 href="javascript:video(0);" onclick="start(\'' + GvodUrlArray[i] + '\')" title="迅雷影音播放">看看</a><a class=d2 href="http://lixian.vip.xunlei.com/lixian_login.html?referfrom=union&ucid=20369&furl=' + encodeURIComponent(ThunderEncode(GvodUrlArray[i])) + '" target=_blank title="迅雷离线下载">离线</a><a href="###" qhref="' + GvodUrlArray[i] + '" onclick="XFLIB.startDownload(this,event,21590)" oncontextmenu = "OnContextClick(this, event)" class=d3 title="QQ旋风下载">旋风</a><a href="' + xmhref + '" target=_blank class=d4 title="小米路由下载">小米</a></span></div></div></li>');
                                                }
                                            </script>
                                        </ul>
                                    </c:if>
                                </div>
                            </div>
                        </div>
                    </c:if>
                    <%--下载地址1--%>

                    <%--下载地址2--%>
                    <c:if test="${resListHttp.size()!=0||resListOther.size()!=0||resListThunder.size()!=0}">
                        <div style="background: white;width: 100%;height: auto;padding: 5px;">
                            <div class="row">
                                <div class="col-sm-6">
                                    <span class="title-icon">下载地址</span>
                                </div>
                                <div class="col-sm-6">
                                    <span class="title-con">
                                        此处支持本地下载、迅雷下载，若不能下载请点击
                                        <a href="javascript:;" onclick="fk('x2')" style="color:red;">失效一键反馈</a>
                                    </span>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col">
                                <div class="ndownlist">
                                    <ul class="thunder">
                                        <c:forEach items="${resListHttp}" var="relist">
                                        <li>
                                            <div class="row">
                                                <div class="col-sm-6">
                                                    <i><input type="checkbox" value="${relist.id}" name="CopyAddr1"/></i>
                                                    <p style="width:300px;height: auto;overflow: hidden;white-space: nowrap;text-overflow: ellipsis;">${relist.name}</p>
                                                </div>

                                                <div class="col-sm-6">
                                                    <a href="${relist.link}" style="float: right" class="btn">下载</a>
                                                </div>
                                            </div>
                                        </li>
                                        </c:forEach>

                                        <c:forEach items="${resListThunder}" var="relist">
                                        <li>
                                            <div class="row">
                                                <div class="col-sm-6">
                                                    <i><input type="checkbox" value="${relist.id}" name="CopyAddr1"/></i>
                                                    <p style="width:300px;height: auto;overflow: hidden;white-space: nowrap;text-overflow: ellipsis;">${relist.name}</p>
                                                </div>
                                                <div class="col-sm-6">
                                                    <a href="${relist.link}" style="float: right" class="btn">下载</a>
                                                </div>
                                            </div>
                                        </li>
                                        </c:forEach>

                                        <c:forEach items="${resListOther}" var="relist">
                                            <li>
                                                <div class="row">
                                                    <div class="col-sm-6">
                                                        <i><input type="checkbox" value="${relist.id}" name="CopyAddr1"/></i>
                                                        <p style="width:300px;height: auto;overflow: hidden;white-space: nowrap;text-overflow: ellipsis;">${relist.name}</p>
                                                    </div>

                                                    <div class="col-sm-6">
                                                        <a href="${relist.link}" style="float: right" class="btn">下载</a>
                                                    </div>
                                                </div>
                                            </li>
                                        </c:forEach>
                                    </ul>
                                </div>
                            </div>
                        </div>
                    </c:if>
                    <%--下载地址2--%>
                </div>
            </c:if>
        </div>
    </div>
    <a name="desc"></a>
    <div class="row" style="margin-top: 10px;">
        <div class="col">
            <div class="endtext">
                <div class="title"><span>剧情介绍</span></div>
                ${film.plot}
            </div>
            <%--下载地址--%>
        </div>
    </div>

   <%-- <div class="row" style="margin-top: 10px;">
        <div class="col">
            &lt;%&ndash;畅言&ndash;%&gt;
            <div id="SOHUCS" sid="film${film.id}"></div>
            <script type="text/javascript">
                (function () {
                    var appid = 'cysEMKRat';
                    var conf = 'prod_1997f817c9e4aa13f7b57d8c6af800b6';
                    var width = window.innerWidth || document.documentElement.clientWidth;
                    if (width < 960) {
                        window.document.write('<script id="changyan_mobile_js" charset="utf-8" type="text/javascript" src="http://changyan.sohu.com/upload/mobile/wap-js/changyan_mobile.js?client_id=' + appid + '&conf=' + conf + '"><\/script>');
                    } else {
                        var loadJs = function (d, a) {
                            var c = document.getElementsByTagName("head")[0] || document.head || document.documentElement;
                            var b = document.createElement("script");
                            b.setAttribute("type", "text/javascript");
                            b.setAttribute("charset", "UTF-8");
                            b.setAttribute("src", d);
                            if (typeof a === "function") {
                                if (window.attachEvent) {
                                    b.onreadystatechange = function () {
                                        var e = b.readyState;
                                        if (e === "loaded" || e === "complete") {
                                            b.onreadystatechange = null;
                                            a()
                                        }
                                    }
                                } else {
                                    b.onload = a
                                }
                            }
                            c.appendChild(b)
                        };
                        loadJs("http://changyan.sohu.com/upload/changyan.js", function () {
                            window.changyan.api.config({appid: appid, conf: conf})
                        });
                    }
                })();
            </script>
            &lt;%&ndash;畅言&ndash;%&gt;
        </div>
    </div>--%>
</div>
<script type="text/javascript">

    var l = location.href;
    if (l.indexOf("&j=") != -1) {
        var js = l.substring(l.indexOf("&j="), l.indexOf("#kan")).split("=")[1];
        $("#mad" + js).css({"background": "#517FAF", "border": "1px solid #517FAF", "color": "#fff"});
    } else if (l.indexOf("&j1=") != -1) {
        var js = l.substring(l.indexOf("&j1="), l.indexOf("#kan")).split("=")[1];
        $("#flh" + js).css({"background": "#517FAF", "border": "1px solid #517FAF", "color": "#fff"});
    }


    /*在线播放*/
    function Flh(e) {
        var f = $(e).attr("data");
        var j = $(e).attr("j");
        if (f != "") {
            location.href = "${prourl}xl/detail.html?film_id=${film.id}&src=" + Base64.encodeURI(f) + "&j=" + j + "#kan";
        }
    }
    /*在线播放*/

    $('#star').raty({
        hints: ['很烂', '很烂', '一般', '一般', '不妨一看', '不妨一看', '比较精彩', '比较精彩', '棒极了', '棒极了'],
        number: 10,
        path: "public/static/img/star",
        size: 24,
        starOff: 'star-off-big.png',
        starOn: 'star-on-big.png',
        precision: true,
        score: function () {
            return $(this).attr('data-score');
        },
        click: function (score, evt) {

            /*获取是否存在cookie评分值*/
            if (getCookie("raty${film.id}") == null) {
                /**
                 * 未评分
                 */
                saveRaty("${film.id}", score);
            } else {
                alert("你已经评过分了，你的评分" + getCookie("raty${film.id}") + "分");
                location.reload();
            }

        }
    });

    function saveRaty(film_id, score) {
        $.ajax({
            url: "xl/addRaty.html",
            type: "post",
            dataType: "json",
            data: "film_id=" + film_id + "&score=" + score,
            success: function (data) {
                if( typeof data == "string" ) data = JSON.parse(data);
                if (data.code == '1') {
                    alert("评分成功,你的评分" + score + "分");
                    setCookie("raty${film.id}", score);
                } else {
                    alert("评分失败,请稍后重试！");
                }
                location.reload();
            }
        });
    }

    /*失效一键反馈*/
    function fk(e) {
        /*获取是否存在cookie评分值*/
        if (getCookie(e+"${film.id}") == null) {
            /**
             * 未评分
             */
            var title = "";
            var content = "";
            switch (e) {
                case "y1":
                    title = "在线点播1失效反馈";
                    content = "胖哥温馨提示：你好，有一位本网站忠实粉丝惊奇的发现${film.cataLogName}-${film.subClassName}-${film.typeName}-<span  style='color:#00AFE4;'>[${film.name}]</span>的在线点播1已失效，请前往<a href='${prourl}xl/detail.html?film_id=${film.id}'>查看</a>是否失效，若失效请去<a href='${prourl}film.html?film_id=${film.id}'>更改</a>";
                    break;
                case "y2":
                    title = "在线点播2失效反馈";
                    content = "胖哥温馨提示：你好，有一位本网站忠实粉丝惊奇的发现${film.cataLogName}-${film.subClassName}-${film.typeName}-<span  style='color:#00AFE4;'>[${film.name}]</span>的在线点播2已失效，请前往<a href='${prourl}xl/detail.html?film_id=${film.id}'>查看</a>是否失效，若失效请去<a href='${prourl}film.html?film_id=${film.id}'>更改</a>";
                    break;
                case "x1":
                    title = "下载地址1失效反馈";
                    content = "胖哥温馨提示：你好，有一位本网站忠实粉丝惊奇的发现${film.cataLogName}-${film.subClassName}-${film.typeName}-<span  style='color:#00AFE4;'>[${film.name}]</span>的下载地址1已失效，请前往<a href='${prourl}xl/detail.html?film_id=${film.id}'>查看</a>是否失效，若失效请去<a href='${prourl}film.html?film_id=${film.id}'>更改</a>";
                    break;
                case "x2":
                    title = "下载地址2失效反馈";
                    content = "胖哥温馨提示：你好，有一位本网站忠实粉丝惊奇的发现${film.cataLogName}-${film.subClassName}-${film.typeName}-<span  style='color:#00AFE4;>[${film.name}]</span>的下载地址2已失效，请前往<a href='${prourl}xl/detail.html?film_id=${film.id}'>查看</a>是否失效，若失效请去<a href='${prourl}film.html?film_id=${film.id}'>更改</a>";
                    break;
                default:
                    alert("参数错误");
                    break;
            }
            if(title!=""&&content!=""){
                $.ajax({
                    url: "sendEmail.html",
                    type: "post",
                    dataType: "json",
                    data: "title=" + title + "&content=" + content,
                    success: function (data) {
                        if (JSON.parse(data).code == '1') {
                            alert("反馈成功，我们会尽快处理");
                            setCookie(e+"${film.id}", e);
                        } else {
                            alert("反馈失败,请稍后重试！");
                        }
                    }
                });
            }
        } else {
            alert("你已经反馈过了，谢谢你的支持");
        }
    }

    /*对cookie的操作*/
    var username = document.cookie.split(";")[0].split("=")[1];
    //JS操作cookies方法!
    //写cookies
    function setCookie(name, value) {
        var Days = 30;
        var exp = new Date();
        exp.setTime(exp.getTime() + Days * 24 * 60 * 60 * 1000);
        document.cookie = name + "=" + escape(value) + ";expires=" + exp.toGMTString();
    }

    /*读取cookies*/
    function getCookie(name) {
        var arr, reg = new RegExp("(^| )" + name + "=([^;]*)(;|$)");
        if (arr = document.cookie.match(reg))
            return unescape(arr[2]);
        else
            return null;
    }

    /*删除cookie*/
    function delCookie(name) {
        var exp = new Date();
        exp.setTime(exp.getTime() - 1);
        var cval = getCookie(name);
        if (cval != null)
            document.cookie = name + "=" + cval + ";expires=" + exp.toGMTString();
    }
</script>
<%--头部--%>
<jsp:include page="/WEB-INF/jsp/pub/footer.jsp"></jsp:include>
</body>
</html>
