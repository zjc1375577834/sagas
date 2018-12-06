package com.zjc.sagas.service;


import com.zjc.sagas.enums.MulStatusEnum;
import com.zjc.sagas.model.SagasOrderHistory;
import com.zjc.sagas.query.SagasOrderHistoryQuery;

import java.util.List;

/**
 *
 * SagasOrderService接口
 * Created by AutoGenerate on 18-11-26 下午2:54 .
 */
public interface SagasOrderHistoryService {

    /**
     * 插入处理
     * @param sagasOrder
     * @return
     */
    int insert(SagasOrderHistory sagasOrder);

    /**
     * 根据id删除处理
     * @param id
     * @return
     */
    int deleteById(Integer id);

    /**
     * 根据id更新处理
     * @param sagasOrder
     * @return
     */
    int updateByOrderNoAndStatus(SagasOrderHistory sagasOrder, Integer status);

    /**
     * 根据id查询处理
     * @param id
     * @return
     */
    SagasOrderHistory selectByOrderNo(String orderNo);

    /**
     * 根据id查询处理 加锁
     * @param id
     * @return
     */
    SagasOrderHistory selectByIdForUpdate(Integer id);



    /**
     * 根据条件查询信息列表
     * @param sagasOrderQuery
     * @return
     */
    List<SagasOrderHistory> queryListByParam(SagasOrderHistoryQuery sagasOrderQuery);

    /**
     * 根据条件查询信息总数目
     * @param sagasOrderQuery
     * @return
     */
    Long queryCountByParam(SagasOrderHistoryQuery sagasOrderQuery);

    /**
     * 根据订单状态查询订单
     * @param mulStatusEnum
     * @return
     */
    List<SagasOrderHistory> queryByStatus(MulStatusEnum mulStatusEnum);

}
