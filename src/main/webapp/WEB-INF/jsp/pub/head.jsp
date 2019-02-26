<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/jsp/pub/include.jsp" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<c:set var="prourl" value="<%=basePath%>"></c:set>
<c:set var="proname" value="${pageContext.request.contextPath}"></c:set>
<div style="width: 100%;height: auto;overflow:hidden;    margin-bottom: 10px;">

    <!-- 头部begin -->
    <nav class="navbar navbar-expand-md bg-dark navbar-dark fixed-top">
        <a class="navbar-brand" href="/video/">欢迎来到<f:message key="siteName"/>最新影片资源</a>
        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#collapsibleNavbar">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="collapsibleNavbar">
            <ul class="nav navbar-nav navbar-right" >


                <%--<li class="nav-item">
                    <a class="nav-link" href="note.html">留言</a>
                </li>--%>

                <li class="nav-item">
                    <a class="nav-link" onclick="alert('请点击Ctrl+D收藏')" href="#">收藏网站</a>
                </li>

                <c:if test="${u_skl.userName!='' and u_skl.userName!=null}">
                    <c:if test="${u_skl.isVip==1}">
                        <li class="nav-item">
                            <a class="nav-link" data-toggle='tooltip' data-placement="bottom" title="<f:formatDate value='${u_skl.expireDate}' pattern='yyyy-MM-dd HH:mm:ss' />到期" href="javascript:;">${u_skl.userName}</a>
                        </li>
                    </c:if>
                    <c:if test="${u_skl.isVip==0}">
                        <li class="nav-item">
                            <a class="nav-link" data-toggle='tooltip' data-placement="bottom" title="你的车开到<f:formatDate value='${u_skl.expireDate}' pattern='yyyy-MM-dd HH:mm:ss' />没油了，请加油后继续上路！"  href="javascript:;">${u_skl.userName}</a>
                        </li>
                    </c:if>
                    <li class="nav-item">
                        <a class="nav-link" href="<f:message key="sales_link"/>"  target="_blank">加点油</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" onclick="fuelFilling()"  href="javascript:;">使用加油卡</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" id="globalLoginBtn_loginOut" href="javascript:;">退出</a>
                    </li>
                </c:if>
                <c:if test="${u_skl.userName=='' or u_skl.userName==null}">
                    <li class="nav-item">
                        <a class="nav-link" data-toggle="modal" data-target="#myModal" href="javascript:;">登录</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" id="globalLoginBtn_register_input" href="registerInput.html">注册</a>
                    </li>
                </c:if>
                <li class="nav-item">
                    <a class="nav-link" href="javascript:;">|</a>
                </li>
                <li class="nav-item">
                    <a  class="nav-link" href="index.html">首页</a>
                </li>
                <c:forEach items="${cataLogList}" var="vo">
                    <li class="nav-item">
                        <a  class="nav-link" href="xl/1.html?cataLog_id=${vo.id}">${vo.name}</a>
                    </li>
                </c:forEach>
            </ul>
        </div>
    </nav>
    <!-- 模态框 -->
    <div class="modal fade" id="myModal">
        <div class="modal-dialog">
            <div class="modal-content">

                <!-- 模态框头部 -->
                <div class="modal-header">
                    <h4 class="modal-title">登录</h4>
                    <button type="button" class="close" data-dismiss="modal">&times;</button>
                </div>

                <!-- 模态框主体 -->
                <div class="modal-body">
                    <form id="login_form">
                        <div class="form-group">
                            <label for="email">账号/邮箱</label>
                            <input type="email" name="account_l" class="form-control" id="email" placeholder="账号/邮箱">
                        </div>
                        <div class="form-group">
                            <label for="pwd">密码</label>
                            <input type="password" name="password_l" class="form-control" id="pwd" placeholder="密码">
                        </div>
                        <button type="button" class="btn btn-primary" id="login_btn">登录</button>
                    </form>
                </div>

                <!-- 模态框底部 -->
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-dismiss="modal">关闭</button>
                </div>

            </div>
        </div>
    </div>
</div>
<script>
    $(function () {
        var error_info = "${error_info}";
        if(error_info!=""){
            if("not_login"==error_info){
                alert("该资源需要登录后访问!");

            }else if("not_vip"==error_info){
                alert("你的车没油了，请加油后访问！");
            }
        }

        /**登录*/
        $("#login_btn").click(function () {
            $.ajax({
                url:"login.html",
                type:"POST",
                dataType:"json",
                data:$("#login_form").serialize(),
                success:function (data) {
                    if ( typeof data == "string" ) data = JSON.parse(data);
                    if(data.code=="1"){
                        location.reload();
                    }else{
                        if(data.error){
                            alert(data.error);
                        }else{
                            alert("用户名或密码错误！");
                        }
                    }
                }
            });
        });

        /**登出*/
        $("#globalLoginBtn_loginOut").click(function () {
            $.ajax({
                url:"logout.html",
                type:"POST",
                dataType:"json",
                success:function (data) {
                    if ( typeof data == "string" ) data = JSON.parse(data);
                    if(data.code=="1"){
                        location.reload();
                    }else{
                        alert("登出失败！请稍后重试");
                    }
                }
            });
        });

        $("[data-toggle='tooltip']").tooltip();
    });

    /**
     * 加油
     */
    function fuelFilling() {
        var vip_code =  prompt("请输入你的加油卡号：");
        if(vip_code!='' && vip_code!=undefined){
            $.ajax({
                url:"vipCodeVerification.html",
                type:"POST",
                dataType:"json",
                data:{
                    vip_code:vip_code
                },
                success:function (data) {
                    if( typeof data == "string" ) data = JSON.parse(data);
                    if(data.code=="1"){
                        alert("加油成功，继续前行");
                        location.reload();
                    }else{
                        alert("加油失败！加油卡号不存在");
                    }
                }
            });
        }else if(vip_code!=undefined){
            alert("请输入你的加油卡号!")
        }
    }
</script>
<!--登录 END-->
</div>