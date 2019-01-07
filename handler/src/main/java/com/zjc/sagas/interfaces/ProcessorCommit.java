package com.zjc.sagas.interfaces;

import java.util.Map;

/**
 * create by zjc in 2018/11/28 0028
 */
public interface ProcessorCommit {
    /**
     * INIT 或者NOBIGIN时候可以调用执行CIMMIT方法
     * @param orderNo
     * @param order
     * @param result
     */
    void initCommit(String orderNo, Integer order, Map<String,Object> result,Integer type);

    /**
     * ING订单中执行CIMMIT方法
     * @param orderNo
     * @param order
     * @param result
     */
    void ingCommit(String orderNo, Integer order, Map<String,Object> result,Integer type);

    /**
     * 订单失败，开始执行回滚
     * @param orderNo
     * @param order
     * @param result
     */
    void failRollBack(String orderNo, Integer order, Map<String,Object> result,Integer type);

    /**
     * 订单成功执行回滚
     * @param orderNo
     * @param order
     * @param result
     */
    void sucRollBack(String orderNo, Integer order, Map<String,Object> result,Integer type);

    /**
     * 订单处理中执行回滚
     * @param orderNo
     * @param order
     * @param result
     */
    void ingRollBack(String orderNo, Integer order, Map<String,Object> result,Integer type);

    /**
     * 订单回滚处理中执行回滚
     * @param orderNo
     * @param order
     * @param result
     */
    void ringRollBack(String orderNo, Integer order, Map<String,Object> result,Integer type);

    /**
     * 订单回滚未发送成执行回滚方法
     * @param orderNo
     * @param order
     * @param result
     */
    void rinitRollBack(String orderNo, Integer order, Map<String,Object> result,Integer type);

    /**
     * 订单回滚失败执行回滚方法
     * @param orderNo
     * @param order
     * @param result
     */
    void rfailRollBack(String orderNo, Integer order, Map<String,Object> result,Integer type);
}
