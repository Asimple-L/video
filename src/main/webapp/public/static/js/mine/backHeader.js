// 退出登录
$("#logout").click(function () {
    $.ajax({
        url: "logout",
        type: "POST",
        dataType: "json",
        success: function(data) {
            if( typeof data == "string" ) data = JSON.parse(data);
            if( data.code == "1" ) {
                // 退出登录成功，跳转都后台登录页面
                window.location.href = "/video/admin/login";
            } else {
                alert("系统繁忙");
            }
        },
        error: function() {
            alert("系统繁忙");
        }
    })
});

// 更换标题名称
function changeTitle(obj) {
    var tt = $(obj).text();
    $(".panel-title").html('<a href="javascript:void(0);" class="toggle-sidebar"><span class="fa fa-angle-double-left" data-toggle="offcanvas" title="Maximize Panel"></span></a>'+tt);
}
