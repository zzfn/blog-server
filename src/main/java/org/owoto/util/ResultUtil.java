package org.owoto.util;

import java.util.HashMap;
import java.util.Map;

public class ResultUtil {
    public static Object success(Object object) {
        Map<String, Object> map = new HashMap<>();
        map.put("code", 0);
        map.put("message", "success");
        map.put("data", object);
        return map;
    }

    public static Object error(String message) {
        Map<String, Object> map = new HashMap<>();
        map.put("code", 1);
        map.put("message", message);
        map.put("data", null);
        return map;
    }

    public static Object error(int code, String message) {
        Map<String, Object> map = new HashMap<>();
        map.put("code", code);
        map.put("message", message);
        map.put("data", null);
        return map;
    }
}
