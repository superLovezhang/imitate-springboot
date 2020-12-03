package com.tyzz.mvc.pojo;

import java.util.HashMap;

/**
 * Created by IntelliJ IDEA.
 * User: zhangzhao<zhao.zhang@eventslack.com>
 * Date: 2020/12/3
 * Time: 11:14
 */
public class R extends HashMap<String, Object> {
    public R() {
        put("message", "success");
        put("code", 200);
    }

    public static R success(Object data) {
        R r = new R();
        r.put("data", data);
        return r;
    }

    public static R success(String success, Object data) {
        R r = new R();
        r.put("success", success);
        r.put("data", data);
        return r;
    }

    public static R error(String message) {
        R r = new R();
        r.put("message", message);
        r.put("code", 500);
        return r;
    }

    public static R error(String message, Integer code) {
        R r = new R();
        r.put("message", message);
        r.put("code", code);
        return r;
    }

    public R add(String key, Object value) {
        put(key, value);
        return this;
    }
}
