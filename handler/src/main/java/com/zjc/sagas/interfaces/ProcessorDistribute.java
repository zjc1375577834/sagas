package com.zjc.sagas.interfaces;

import java.util.Map;

/**
 * create by zjc on 18/11/29
 */
public interface ProcessorDistribute {
    void distribute(String orderNo, Integer order, Map<String, Object> result);
}
