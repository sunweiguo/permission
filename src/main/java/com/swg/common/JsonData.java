package com.swg.common;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author swg.
 * @Date 2019/5/3 13:45
 * @CONTACT 317758022@qq.com
 * @DESC 返回的json数据结构定义
 */
@Data
public class JsonData {
    //标识位，表示请求是否成功
    private boolean ret;
    //信息，请求提示信息
    private String msg;
    //数据，即需要返回的数据
    private Object data;

    public JsonData(boolean ret) {
        this.ret = ret;
    }

    //成功的时候返沪数据和提示信息
    public static JsonData success(Object object, String msg) {
        JsonData jsonData = new JsonData(true);
        jsonData.data = object;
        jsonData.msg = msg;
        return jsonData;
    }

    //有的时候只需要返回数据
    public static JsonData success(Object object) {
        JsonData jsonData = new JsonData(true);
        jsonData.data = object;
        return jsonData;
    }

    //有的时候啥都不需要返回
    public static JsonData success() {
        return new JsonData(true);
    }

    //失败的时候返回失败的信息
    public static JsonData fail(String msg) {
        JsonData jsonData = new JsonData(false);
        jsonData.msg = msg;
        return jsonData;
    }

    //全局异常中返回的信息需要时map形式，所以这里提供一个转换方法
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<String, Object>();
        result.put("ret", ret);
        result.put("msg", msg);
        result.put("data", data);
        return result;
    }
}
