<%@ page import="java.util.Date" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/jsp/pub/include.jsp" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<c:set var="prourl" value="<%=basePath%>"></c:set>
<c:set var="proname" value="${pageContext.request.contextPath}"></c:set>
<c:set var="pageurl" value="xl/1.html"/>
<!DOCTYPE HTML>
<html>
<head>
    <base href="<%=basePath%>">
    <title>最新影片下载=高清影片下载-迅雷下载</title>
    <jsp:include page="/WEB-INF/jsp/pub/head_meta.jsp"/>
    <jsp:include page="/WEB-INF/jsp/pub/head_link.jsp"/>
    <jsp:include page="/WEB-INF/jsp/pub/head_script.jsp"/>
    <!--=====================CSS_Link===========================-->
    <link rel="stylesheet" type="text/css" href="public/static/css/index/1.css?v=${version}">

    <!--=====================JS_Link===========================-->
    <script>
        $(function(){
            var hr= window.location.search;
            if(hr!=""){
                hr = hr.substring(1,hr.length);
                var hrefArray = hr.split("&");
                for(var i =0;i<hrefArray.length;i++){
                    var key = hrefArray[i].split("=")[0];
                    var val = hrefArray[i].split("=")[1];
                    $("#"+key+val).css({"color":"#fff","background":"#343a40"});
                }
            }
        });
    </script>
    <script>
        $(document).ready(function(){
            $('.weixinshowPic').hover(function() {
                $(this).addClass('weixinshowPic-hover');
            }, function() {
                $(this).removeClass('weixinshowPic-hover');
            });
            $('.dropdown').hover(function() {
                $(this).addClass('dropdown-hover');
                $(".dropdown-sub").show('slow');
            }, function() {
                $(this).removeClass('dropdown-hover');
            });

            updateFilmSize();
            $(window).resize(function(){
                updateFilmSize();
            });
        });



        function updateFilmSize() {
            var w = $(".film-list").width() / 6;
            if ($(".film-list").width() < 450) {
                w = $(".film-list").width() / 2;
            }
            w = parseInt(w)-10;
            $(".film-list li").css("width", w + "px");

            var w = $(".note-left").width();
            var h = (w * 160) / 115;
            $(".note-left").css("height", h + "px");
        }
    </script>
    <!-- 代码部分end -->
</head>
<body>
<%--头部--%>
<jsp:include page="/WEB-INF/jsp/pub/head.jsp"></jsp:include>

<div class="container" style="margin-top: 50px;">
    <c:if test="${u_skl==null}">
        <div class="row">
            <div class="col">
                <marquee  onmouseover='this.stop()' onmouseout='this.start()'><a id="globalLoginBtn_register_input" href="registerInput.html">注册</a>|<a data-toggle="modal" data-target="#myModal" href="javascript:;">登录</a>后观看更多高清无码视频</marquee>
            </div>
        </div>
    </c:if>
    <div class="row">
        <div class="col-sm-2">
            <ul class="user-nav">
                <a href="xl/1.html"><div class="user-nav-title">全部分类</div></a>
                <c:forEach items="${cataLogList}" var="list">
                    <c:if test="${list.id == cataLog_id}">
                    <a href="xl/1.html?cataLog_id=${list.id}" ><li style="color: #343a40;background: #b5b5b5;">${list.name}</li></a>
                    </c:if>
                    <c:if test="${list.id != cataLog_id}">
                        <a href="xl/1.html?cataLog_id=${list.id}"><li>${list.name}</li></a>
                    </c:if>
                </c:forEach>
            </ul>
        </div>
        <div class="col-sm-10">
            <ul class="user-box-right-search" style="margin-top: 10px;">
                <c:if test="${!fn:contains(pb.url, 'cataLog=')}">
                    <c:if test="${cataLogList.size()!=0}">
                        <li>
                            <div class="user-box-right-search-left">分类</div>
                            <div class="user-box-right-search-right">
                                <ul>
                                    <c:forEach items="${cataLogList}" var="l1">
                                        <a href="${pageurl}?<%--之前有参数--%><c:if test="${pb.url!=null}"><%--包含自己--%><c:if test="${fn:indexOf(pb.url, 'cataLog_id')!=-1}"><%--自己不在头部--%><c:if test="${fn:indexOf(pb.url, 'cataLog_id')>0}">${fn:replace(pb.url,fn:substring(pb.url,fn:indexOf(pb.url, "cataLog_id")-1, fn:indexOf(pb.url, "cataLog_id")+43),"") }&cataLog_id=${l1.id}</c:if><%--自己不在头部--%><%--自己在头部--%><c:if test="${fn:indexOf(pb.url, 'cataLog_id')==0}"><c:if test="${pb.url.length()>45}">${fn:replace(pb.url,fn:substring(pb.url,fn:indexOf(pb.url, "cataLog_id"), fn:indexOf(pb.url, "cataLog_id")+44),"") }&cataLog_id=${l1.id}</c:if><c:if test="${pb.url.length()<45}">${fn:replace(pb.url,fn:substring(pb.url,fn:indexOf(pb.url, "cataLog_id"), fn:indexOf(pb.url, "cataLog_id")+44),"") }cataLog_id=${l1.id}</c:if></c:if><%--自己在头部--%></c:if><%--包含自己--%><%--不包含自己--%><c:if test="${fn:indexOf(pb.url, 'cataLog_id')==-1}">${pb.url}&cataLog_id=${l1.id}</c:if><%--不包含自己--%></c:if><%--之前有参数--%><%--之前没有参数--%><c:if test="${pb.url==null}">cataLog_id=${l1.id}</c:if>"><%--之前没有参数--%>
                                            <li id="cataLog_id${l1.id}">${l1.name}</li></a>
                                    </c:forEach>
                                </ul>
                            </div>
                        </li>
                    </c:if>

                    <c:if test="${subClassList!=null&&subClassList.size()!=0}">
                        <li>
                            <div class="user-box-right-search-left">子类</div>
                            <div class="user-box-right-search-right">
                                <ul>
                                    <c:forEach items="${subClassList}" var="l1">
                                        <a href="${pageurl}?<%--之前有参数--%><c:if test="${pb.url!=null}"><%--包含自己--%><c:if test="${fn:indexOf(pb.url, 'subClass_id')!=-1}"><%--自己不在头部--%><c:if test="${fn:indexOf(pb.url, 'subClass_id')>0}">${fn:replace(pb.url,fn:substring(pb.url,fn:indexOf(pb.url, "subClass_id")-1, fn:indexOf(pb.url, "subClass_id")+44),"") }&subClass_id=${l1.id}</c:if><%--自己不在头部--%><%--自己在头部--%><c:if test="${fn:indexOf(pb.url, 'subClass_id')==0}"><c:if test="${pb.url.length()>45}">${fn:replace(pb.url,fn:substring(pb.url,fn:indexOf(pb.url, "subClass_id"), fn:indexOf(pb.url, "subClass_id")+45),"") }&subClass_id=${l1.id}</c:if><c:if test="${pb.url.length()<45}">${fn:replace(pb.url,fn:substring(pb.url,fn:indexOf(pb.url, "subClass_id"), fn:indexOf(pb.url, "subClass_id")+45),"") }subClass_id=${l1.id}</c:if></c:if><%--自己在头部--%></c:if><%--包含自己--%><%--不包含自己--%><c:if test="${fn:indexOf(pb.url, 'subClass_id')==-1}">${pb.url}&subClass_id=${l1.id}</c:if><%--不包含自己--%></c:if><%--之前有参数--%><%--之前没有参数--%><c:if test="${pb.url==null}">subClass_id=${l1.id}</c:if>"><%--之前没有参数--%>
                                            <li id="subClass_id${l1.id}">${l1.name}</li></a>
                                    </c:forEach>
                                </ul>
                            </div>
                        </li>
                    </c:if>
                </c:if>
                <c:if test="${typeList!=null&&typeList.size()!=0}">
                    <li>
                        <div class="user-box-right-search-left">类型</div>
                        <div class="user-box-right-search-right">
                            <ul>
                                <c:forEach items="${typeList}" var="l1">
                                    <a href="${pageurl}?<%--之前有参数--%><c:if test="${pb.url!=null}"><%--包含自己--%><c:if test="${fn:indexOf(pb.url, 'type_id')!=-1}"><%--自己不在头部--%><c:if test="${fn:indexOf(pb.url, 'type_id')>0}">${fn:replace(pb.url,fn:substring(pb.url,fn:indexOf(pb.url, "type_id")-1, fn:indexOf(pb.url, "type_id")+40),"") }&type_id=${l1.id}</c:if><%--自己不在头部--%><%--自己在头部--%><c:if test="${fn:indexOf(pb.url, 'type_id')==0}"><c:if test="${pb.url.length()>45}">${fn:replace(pb.url,fn:substring(pb.url,fn:indexOf(pb.url, "type_id"), fn:indexOf(pb.url, "type_id")+41),"") }&type_id=${l1.id}</c:if><c:if test="${pb.url.length()<45}">${fn:replace(pb.url,fn:substring(pb.url,fn:indexOf(pb.url, "type_id"), fn:indexOf(pb.url, "type_id")+41),"") }type_id=${l1.id}</c:if></c:if><%--自己在头部--%></c:if><%--包含自己--%><%--不包含自己--%><c:if test="${fn:indexOf(pb.url, 'type_id')==-1}">${pb.url}&type_id=${l1.id}</c:if><%--不包含自己--%></c:if><%--之前有参数--%><%--之前没有参数--%><c:if test="${pb.url==null}">type_id=${l1.id}</c:if>"><%--之前没有参数--%>
                                        <li id="type_id${l1.id}">${l1.name}</li></a>
                                </c:forEach>
                            </ul>
                        </div>
                    </li>
                </c:if>
                <li>
                    <div class="user-box-right-search-left">地区</div>
                    <div class="user-box-right-search-right">
                        <ul>
                            <c:forEach items="${locList}" var="l1">
                                <a href="${pageurl}?<%--之前有参数--%><c:if test="${pb.url!=null}"><%--包含自己--%><c:if test="${fn:indexOf(pb.url, 'loc_id')!=-1}"><%--自己不在头部--%><c:if test="${fn:indexOf(pb.url, 'loc_id')>0}">${fn:replace(pb.url,fn:substring(pb.url,fn:indexOf(pb.url, "loc_id")-1, fn:indexOf(pb.url, "loc_id")+39),"") }&loc_id=${l1.id}</c:if><%--自己不在头部--%><%--自己在头部--%><c:if test="${fn:indexOf(pb.url, 'loc_id')==0}"><c:if test="${pb.url.length()>45}">${fn:replace(pb.url,fn:substring(pb.url,fn:indexOf(pb.url, "loc_id"), fn:indexOf(pb.url, "loc_id")+40),"") }&loc_id=${l1.id}</c:if><c:if test="${pb.url.length()<45}">${fn:replace(pb.url,fn:substring(pb.url,fn:indexOf(pb.url, "loc_id"), fn:indexOf(pb.url, "loc_id")+39),"") }loc_id=${l1.id}</c:if></c:if><%--自己在头部--%></c:if><%--包含自己--%><%--不包含自己--%><c:if test="${fn:indexOf(pb.url, 'loc_id')==-1}">${pb.url}&loc_id=${l1.id}</c:if><%--不包含自己--%></c:if><%--之前有参数--%><%--之前没有参数--%><c:if test="${pb.url==null}">loc_id=${l1.id}</c:if>"><%--之前没有参数--%>
                                    <li id="loc_id${l1.id}">${l1.name}</li></a>
                            </c:forEach>
                        </ul>
                    </div>
                </li>
                <li>
                    <div class="user-box-right-search-left">年代</div>
                    <div class="user-box-right-search-right">
                        <ul>
                            <c:forEach items="${decadeList}" var="l1">
                                <a href="${pageurl}?<%--之前有参数--%><c:if test="${pb.url!=null}"><%--包含自己--%><c:if test="${fn:indexOf(pb.url, 'onDecade')!=-1}"><%--自己不在头部--%><c:if test="${fn:indexOf(pb.url, 'onDecade')>0}">${fn:replace(pb.url,fn:substring(pb.url,fn:indexOf(pb.url, "onDecade")-1, fn:indexOf(pb.url, "onDecade")+41),"") }&onDecade=${l1.name}</c:if><%--自己不在头部--%><%--自己在头部--%><c:if test="${fn:indexOf(pb.url, 'onDecade')==0}"><c:if test="${pb.url.length()>45}">${fn:replace(pb.url,fn:substring(pb.url,fn:indexOf(pb.url, "onDecade"), fn:indexOf(pb.url, "onDecade")+42),"") }&onDecade=${l1.name}</c:if><c:if test="${pb.url.length()<45}">${fn:replace(pb.url,fn:substring(pb.url,fn:indexOf(pb.url, "onDecade"), fn:indexOf(pb.url, "onDecade")+41),"") }onDecade=${l1.name}</c:if></c:if><%--自己在头部--%></c:if><%--包含自己--%><%--不包含自己--%><c:if test="${fn:indexOf(pb.url, 'onDecade')==-1}">${pb.url}&onDecade=${l1.name}</c:if><%--不包含自己--%></c:if><%--之前有参数--%><%--之前没有参数--%><c:if test="${pb.url==null}">onDecade=${l1.name}</c:if>"><%--之前没有参数--%>
                                    <li id="onDecade${l1.name}">${l1.name}</li></a>
                            </c:forEach>
                        </ul>
                    </div>
                </li>
            </ul>
            <c:if test="${pb.tr!=0}">
                <ul class="film-list">
                    <c:forEach items="${pb.beanList}" var="list">
                        <li>
                            <a href="xl/detail.html?film_id=${list.id}">
                                <div  class="note-left" title="${list.name}"><img class="lazy rounded img-fluids" data-original="${list.image}" /></div>
                            </a>
                            <div class="film-info">
                                <div class="info">
                                    <h2><a class="film-info-a" href="xl/detail.html?film_id=${list.id}"
                                           title="${list.name}"
                                           target="_blank">${list.name}</a><em> ${list.onDecade}</em></h2>
                                    <em class="star star<c:if test="${list.evaluation>=1&&list.evaluation<2}">1</c:if><c:if test="${list.evaluation>=2&&list.evaluation<4}">2</c:if><c:if test="${list.evaluation>=4&&list.evaluation<6}">3</c:if><c:if test="${list.evaluation>=6&&list.evaluation<8}">4</c:if><c:if test="${list.evaluation>=8&&list.evaluation<=10}">5</c:if>"></em>
                                    <p>主演：${list.actor}</p>
                                    <p><i>状态：${list.status}</i>&nbsp;<i>地区：${list.locName}</i></p>
                                    <p><i>类型：${list.typeName}</i><i>更新：${fn:substring(list.updateTime,5,10)}</i></p>
                                    <p></p>
                                    <span>
                                <a href="xl/detail.html?film_id=${list.id}#kan" class="watch-btn" target="_blank">观看</a>
                                <a href="xl/detail.html?film_id=${list.id}#down" class="download-btn" target="_blank">下载</a>
                            </span>
                                </div>
                            </div>
                        </li>
                    </c:forEach>
                </ul>
            </c:if>
            <c:if test="${pb.tr==0}">
                <ul class="mlist">对不起，没有找到任何记录,<a target="_blank" href="note.html"><font color="red"><b>请您在此留言</b></font></a>，我们尽快为你添加喜欢的数据<div class="cr"></div></ul>
            </c:if>
            <div style="width:100%;margin: 5px auto;height: auto;overflow: hidden;">
                <c:if test="${pb.tr!=0}">
                    <p style="text-align: right;">
                        <!--1.修改-->
                        <script type="text/javascript">
                            function _change() {
                                var select = document.getElementById("select");
                                location = "${pageurl}?${pb.url}&pc=" + select.value;
                            }
                        </script>

                        第${pb.pc }页/共${pb.tp }页

                        <c:if test="${pb.pc > 1 }"><a href="${pageurl}?${pb.url }&pc=1" class="use-btn">首页</a>&nbsp;<a href="${pageurl}?${pb.url }&pc=${pb.pc-1 }" class="use-btn">上一页</a></c:if>
                        <c:if test="${pb.pc == 1 }"><a href="javascript:;" class="nouse-btn">首页</a>&nbsp;<a href="javascript:;" class="nouse-btn">上一页</a></c:if>
                            <%------------------------------------ --%>
                            <%-- 页码列表的长度自己定，10个长 --%>
                        <c:choose>
                            <%-- 第一条：如果总页数<=10，那么页码列表为1 ~ tp --%>
                            <c:when test="${pb.tp <= 10 }">
                                <c:set var="begin" value="1"/>
                                <c:set var="end" value="${pb.tp }"/>
                            </c:when>
                            <c:otherwise>
                                <%-- 第二条：按公式计算，让列表的头为当前页-4；列表的尾为当前页+5 --%>
                                <c:set var="begin" value="${pb.pc-4 }"/>
                                <c:set var="end" value="${pb.pc+5 }"/>

                                <%-- 第三条：第二条只适合在中间，而两端会出问题。这里处理begin出界！ --%>
                                <%-- 如果begin<1，那么让begin=1，相应end=10 --%>
                                <c:if test="${begin<1 }">
                                    <c:set var="begin" value="1"/>
                                    <c:set var="end" value="10"/>
                                </c:if>
                                <%-- 第四条：处理end出界。如果end>tp，那么让end=tp，相应begin=tp-9 --%>
                                <c:if test="${end>pb.tp }">
                                    <c:set var="begin" value="${pb.tp-9 }"/>
                                    <c:set var="end" value="${pb.tp }"/>
                                </c:if>
                            </c:otherwise>
                        </c:choose>

                            <%-- 循环显示页码列表 --%>
                        <c:forEach begin="${begin }" end="${end }" var="i">
                            <c:choose>
                                <c:when test="${i eq pb.pc }"><a href="javascript:;" class="nouse-btn">${i}</a></c:when>
                                <c:otherwise>
                                    <a href="${pageurl}?${pb.url }&pc=${i}" class="use-btn">${i}</a>
                                </c:otherwise>
                            </c:choose>
                        </c:forEach>
                        <c:if test="${pb.pc < pb.tp }"><a href="${pageurl}?${pb.url }&pc=${pb.pc+1 }" class="use-btn">下一页</a>&nbsp;<a href="${pageurl}?${pb.url}&pc=${pb.tp}" class="use-btn">尾页</a></c:if>
                        <c:if test="${pb.pc == pb.tp }"><a href="javascript:;" class="nouse-btn">下一页</a>&nbsp;<a href="javascript:;" class="nouse-btn">尾页</a></c:if>
                            <%--<select name="pc" onchange="_change()" id="select">
                                <c:forEach begin="1" end="${pb.tp }" var="i">
                                    <option value="${i }"
                                            <c:if test="${i eq pb.pc }">selected="selected"</c:if> >${i}</option>
                                </c:forEach>
                            </select>--%>
                        总共${pb.tr}部　
                    </p>

                </c:if>
            </div>
        </div>
    </div>
</div>
<jsp:include page="/WEB-INF/jsp/pub/footer.jsp"></jsp:include>
</body>
</html>
