package com.zjc.sagas.service;

import com.zjc.sagas.enums.ProcessStatusEnum;
import com.zjc.sagas.model.SagasProcessOrderHistory;
import com.zjc.sagas.query.SagasProcessOrderHistoryQuery;

import java.util.List;

/**
 *
 * SagasProcessOrderHistoryService接口
 * Created by AutoGenerate on 18-11-26 下午2:54 .
 */
public interface SagasProcessOrderHistoryService {

    /**
     * 插入处理
     * @param SagasProcessOrderHistory
     * @return
     */
    int insert(SagasProcessOrderHistory SagasProcessOrderHistory);

    /**
     * 根据id删除处理
     * @param id
     * @return
     */
    int deleteById(Integer id);

    /**
     * 根据id更新处理
     * @param SagasProcessOrderHistory
     * @return
     */
    int updateByProcessNoAndStatus(SagasProcessOrderHistory SagasProcessOrderHistory, Integer status);

    /**
     * 根据id查询处理
     * @param id
     * @return
     */
    SagasProcessOrderHistory selectByOrderNoAndOrder(String orderNo, Integer order);

    /**
     * 根据id查询处理 加锁
     * @param id
     * @return
     */
    SagasProcessOrderHistory selectByIdForUpdate(Integer id);



    /**
     * 根据条件查询信息列表
     * @param SagasProcessOrderHistoryQuery
     * @return
     */
    List<SagasProcessOrderHistory> queryListByParam(SagasProcessOrderHistoryQuery SagasProcessOrderHistoryQuery);

    /**
     * 根据条件查询信息总数目
     * @param SagasProcessOrderHistoryQuery
     * @return
     */
    Long queryCountByParam(SagasProcessOrderHistoryQuery SagasProcessOrderHistoryQuery);

    /**
     * 根据订单号查询过程单据
     * @param orderNo
     * @return
     */
    List<SagasProcessOrderHistory> queryByOrderNo(String orderNo);

    /**
     * 根据订单状态查询过程单据
     * @param statusEnum
     * @return
     */
    List<SagasProcessOrderHistory> queryByStatus(ProcessStatusEnum statusEnum);

}
