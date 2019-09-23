package com.asimple.util;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;

/**
 * @Description 返回数据包装类
 * @Author Asimple
 * @date 2019/9/23 21:43
 */
public class ResponseReturnUtil {

    private static JSONObject returnData(String code, String msg, Object data) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code", code);
        if( StringUtils.isNotEmpty(msg) ) {
            jsonObject.put("msg", msg);
        } else {
            jsonObject.put("msg", "调用成功!");
        }
        if( null != data ) {
            jsonObject.put("data", data);
        }
        return jsonObject;
    }

    public static JSONObject returnSuccessMsgAndData(String msg, Object data) {
        return returnData("1", msg, data);
    }

    public static JSONObject returnErrorMsgAndData(String msg, Object data) {
        return returnData("0", msg, data);
    }

    public static JSONObject returnSuccessWithoutData(String msg) {
        return returnSuccessMsgAndData(msg, null);
    }

    public static JSONObject returnErrorWithoutData(String msg) {
        return returnErrorMsgAndData(msg, null);
    }

    public static JSONObject returnSuccessWithoutMsg(Object data) {
        return returnSuccessMsgAndData(null, data);
    }

    public static JSONObject returnErrorWithoutMsg(Object data) {
        return returnErrorMsgAndData(null, data);
    }

}
