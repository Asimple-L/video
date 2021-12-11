package com.asimple.util;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author Asimple
 * @description 返回数据包装类
 * @date 2019/9/23 21:43
 */
public class ResponseReturnUtil {

    public static final String USER_REGISTER_ERROR = "注册失败！邮箱或者用户名已存在!";

    public static final String USER_REGISTER_SUC = "注册成功,已自动登录!";

    public static final String USER_LOGIN_ERROR = "登录失败，用户不存在或密码错误！";

    public static final String LOGIN_SUC = "登录成功!";

    public static final String LOGIN_FIRST = "请先登录!";

    public static final String UPDATE_SUC_NEED_LOGIN = "修改成功,请重新登录!";

    public static final String PASSWORD_ERROR = "修改失败,请检查输入!";

    public static final String USE_VIP_CARD_FAIL = "加油失败，请稍后重试!";

    public static final String VIP_CARD_NOT_FOUND = "VIP加油卡号不存在!";

    public static final String UPDATE_FAIL = "更新失败!";

    public static final String OPERATION_SUC = "操作成功!";

    public static final String OPERATION_ERROR = "操作失败!";

    public static final String FUNCTION_NOT_OPEN = "功能暂未开放!";

    public static final String PERMISSION_DENIED = "权限不足!";

    public static final String PARAM_MISS = "缺少必要参数!";

    private static JSONObject returnData(String code, String msg, Object data) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code", code);
        if (StringUtils.isNotEmpty(msg)) {
            jsonObject.put("message", msg);
        } else {
            jsonObject.put("message", "调用成功!");
        }
        if (null != data) {
            jsonObject.put("data", data);
        }
        return jsonObject;
    }

    public static JSONObject returnSuccessMsgAndData(String msg, Object data) {
        return returnData(VideoKeyNameUtil.SUCCESS_CODE, msg, data);
    }

    public static JSONObject returnErrorMsgAndData(String msg, Object data) {
        return returnData(VideoKeyNameUtil.ERROR_CODE, msg, data);
    }

    public static JSONObject returnSuccessWithMsg(String msg) {
        return returnSuccessMsgAndData(msg, null);
    }

    public static JSONObject returnErrorWithMsg(String msg) {
        return returnErrorMsgAndData(msg, null);
    }

    public static JSONObject returnSuccessWithData(Object data) {
        return returnSuccessMsgAndData(null, data);
    }

    public static JSONObject returnErrorWithData(Object data) {
        return returnErrorMsgAndData(null, data);
    }

    public static JSONObject returnSuccessWithoutMsgAndData() {
        return returnSuccessWithMsg(null);
    }

    public static void returnJson(HttpServletResponse response, String json) {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=utf-8");
        try (PrintWriter writer = response.getWriter()) {
            writer.print(json);
        } catch (IOException e) {
            LogUtil.error("response error");
        }
    }

}
