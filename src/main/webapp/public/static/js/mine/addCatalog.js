$(function () {
    $(".add_CataLog_btn").click(function () {
        var catalog_name = $(".catalog_name").text();
        if(catalog_name!=""){
            $.ajax({
                url: "admin/addCataLog",
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
                url: "admin/addSubClass",
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
                url: "admin/addType",
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
                url: "admin/addLoc",
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
                url: "admin/addLevel",
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
                url: "admin/addDecade",
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
            url: "profile/getSubClass",
            type: "post",
            dataType: "json",
            data: "catalog_id=" + catalog_id,
            success: function (data) {
                $(".subClass_id").find("option").remove();
                if( typeof data == "string" ) data = JSON.parse(data);
                var jss = data;
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
        url: "profile/getSubClass",
        type: "POST",
        dataType: "json",
        data: "catalog_id=" + $(".cataLog_id_subClass").val(),
        success: function (data) {
            if( typeof data == "string" ) data = JSON.parse(data);
            var jss =data;
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
    if( typeof data == "string" ) data = JSON.parse(data);
    if (data.code != "0") {
        alert("添加成功！");
        location.reload();
    }
}