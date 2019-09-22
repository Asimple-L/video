// $(function () {
    /**
     * 设置模板类型
     */
    function setMoban() {
        $("#res_name").val("xxxx@@集##1##集数结束##分割符号");
    }

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
                url: "/video/delFile",
                cache: false,
                data: "picsPath=" + filepath,
                dataType: 'json',     //接受数据格式
                success: function (rs) {
                    var data = typeof rs == "string"?JSON.parse(rs):rs;
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
        // debugger;
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
                    location.reload();
                }, 500);
            }
        } else {
            alert("信息不完整");
        }
    })

    function updateFilmInfo(f_id, key, val, t) {
        $.ajax({
            url: "/video/profile/updateFilmInfo",
            type: "post",
            dataType: "json",
            data: "film_id=" + f_id + "&key=" + key + "&val=" + val,
            success: function (data) {
                if( typeof data == "string" ) data = JSON.parse(data);
                if (data.code == "1") {
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
                    location.reload();
                } else {
                    alert("更改失败");
                }
            },
            error: function () {
                alert("系统繁忙!");
            }
        })
    }
// });

var isLoc = 0;
$(function () {

    /**
     * 同步更改二级目录
     */
    $(".cataLog_id_subClass").change(function () {
        var catalog_id = $(this).val();
        $.ajax({
            url: "/video/profile/getSubClass",
            type: "post",
            dataType: "json",
            data: "catalog_id=" + catalog_id,
            success: function (data) {
                $(".subClass_id").find("option").remove();
                var jss = typeof data == "string"?JSON.parse(data):data;
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
        url: "/video/profile/getSubClass",
        type: "POST",
        dataType: "json",
        data: "catalog_id=" + $(".cataLog_id_subClass").val(),
        success: function (data) {
            var jss;
            if( typeof data == "string" ) jss = JSON.parse(data);
            else jss = data;
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
            url: "/video/profile/getType",
            type: "POST",
            dataType: "json",
            data: "subClass_id=" + $(".subClass_id").val(),
            success: function (data) {
                $(".type_id").find("option").remove();
                var jss = typeof data == "string"?JSON.parse(data):data;
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
                url: "/video/profile/addFilm",
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
                url: "/video/profile/addRes",
                type: "POST",
                dataType: "json",
                data: "film_id=" + film_id +
                    "&name=" + res_name_val +
                    "&episodes=" + res_episodes_val +
                    "&link=" + res_link_val +
                    "&linkType=" + res_linkType_val
                ,
                success: function (data) {
                    if( typeof data == "string" ) data = JSON.parse(data);
                    if (data.id != "0") {
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
        if( typeof data == "string" ) data = JSON.parse(data);
        if (data.id != "0") {
            $("#addRes-btn").attr("film_id", data.id);
            $(".zhuti").text("影片主体：" + data.id);
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
            url: "/video/profile/updateIsUse",
            type: "post",
            dataType: "json",
            data: "res_id=" + $(this).attr("res_id"),
            success: function (data) {
                if( typeof data == "string" ) data = JSON.parse(data);
                if ("1" == data.code) {
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
                url: "/video/profile/delRes",
                type: "post",
                dataType: "json",
                data: "res_id=" + $(this).attr("res_id"),
                success: function (data) {
                    if( typeof data == "string" ) data = JSON.parse(data);
                    if (data.code == "1") {
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

    /**
     * 删除影片
     */
    $(".del-film-btn").click(function () {
        var film_id = $(this).attr("film_id");
        var isEnsure = confirm("确定删除?");
        if( isEnsure ) {
            $.ajax({
                url:"/video/profile/delFilm",
                type:"POST",
                data:{"film_id": film_id},
                success: function(data){
                    console.log(data);
                    if( typeof data == "string" ) data = JSON.parse(data);
                    if( data.code == "1" ) {
                        location.assign("/video/profile/profilePage");
                    } else {
                        alert("删除失败！");
                    }
                },
                error: function () {
                    alert("删除失败！");
                }
            })
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
    "uploader": "/video/upload",
    'cancelImg': 'plugins/uploadify/uploadify.swf',// 取消按钮图片路径
    'removeTimeout': 1,
    'method': 'post',
    'fileTypeExts': '*.gif; *.jpg; *.png',
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
    "uploader": "/video/upload",
    'cancelImg': 'plugins/uploadify/uploadify.swf',// 取消按钮图片路径
    'removeTimeout': 1,
    'method': 'post',
    'fileTypeExts': '*.flv; *.f4v; *.mp4; *.m3u8',
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
    if( typeof data == "string" ) data = JSON.parse(data);
    // var file_info = JSON.parse(JSON.parse(data))[0];
    var file_info = data[0];
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
    if( typeof data == "string" ) data = JSON.parse(data);
    // var file_info = JSON.parse(JSON.parse(data))[0].filePath;
    var file_info = data[0].filePath;
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
            url: "/video/delFile",
            cache: false,
            data: "picsPath=" + path,
            dataType: 'json',     //接受数据格式
            success: function (rs) {
                var data = typeof rs == "string"?JSON.parse(rs):rs;
                // debugger;
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

