package com.asimple.util;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;

/**
 * @description 返回数据包装类
 * @author Asimple
 * @date 2019/9/23 21:43
 */
public class ResponseReturnUtil {

    private static JSONObject returnData(String code, String msg, Object data) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code", code);
        if( StringUtils.isNotEmpty(msg) ) {
            jsonObject.put("message", msg);
        } else {
            jsonObject.put("message", "调用成功!");
        }
        if( null != data ) {
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

    public static JSONObject returnSuccessWithoutMsgAndData(){
        return returnSuccessWithMsg(null);
    }

}
