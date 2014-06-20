package com.github.jfwilson.rxjson.core;

import java.util.LinkedHashMap;

public interface Maps {

    public static LinkedHashMap<String, Object> map(String k1, Object v1, String k2, Object v2) {
        LinkedHashMap<String, Object> map = new LinkedHashMap<>(2);
        map.put(k1, v1);
        map.put(k2, v2);
        return map;
    }

    public static LinkedHashMap<String, Object> map(String k1, Object v1, String k2, Object v2, String k3, Object v3) {
        LinkedHashMap<String, Object> map = map(k1, v1, k2, v2);
        map.put(k3, v3);
        return map;
    }
}
