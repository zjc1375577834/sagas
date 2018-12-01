package com.zjc.sagas.utils;

import com.zjc.sagas.model.SagasDate;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * create by zjc in 2018/11/28 0028
 */
public class ContextUtils {
    private static Map<String, List<SagasDate>> map = new ConcurrentHashMap<>();
    public static void put(String key,List<SagasDate> list) {
        map.put(key,list);
    }
    public static List<SagasDate> get(String key) {
        return map.get(key);
    }
    public static SagasDate get(String key , Integer order) {
        List<SagasDate> list = map.get(key);
        if (list == null) {
            throw new IllegalArgumentException("当前缓存不存在该信息");
        }
        if (list.size() < order || order <0) {
            throw new IllegalArgumentException("序号不合法");
        }
        return list.get(order);
    }
    public static void  delete(String key){
        map.remove(key);
    }
}
