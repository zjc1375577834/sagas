package com.zjc.sagas.service;

import com.zjc.sagas.enums.ProcessStatusEnum;
import com.zjc.sagas.model.SagasProcessFlow;
import com.zjc.sagas.query.SagasProcessFlowQuery;

import java.util.List;

/**
 *
 * SagasProcessOrderService接口
 * Created by AutoGenerate on 18-11-26 下午2:54 .
 */
public interface SagasProcessFlowService {

    /**
     * 插入处理
     * @param sagasProcessOrder
     * @return
     */
    int insert(SagasProcessFlow sagasProcessOrder);

    /**
     * 根据id删除处理
     * @param id
     * @return
     */
    int deleteById(Integer id);

    /**
     * 根据id更新处理
     * @param sagasProcessOrder
     * @return
     */
    int updateByProcessNoAndStatus(SagasProcessFlow sagasProcessOrder, Integer status);

    /**
     * 根据id查询处理
     * @param id
     * @return
     */
    SagasProcessFlow selectByOrderNoAndOrder(String orderNo, Integer order);

    /**
     * 根据id查询处理 加锁
     * @param id
     * @return
     */
    SagasProcessFlow selectByIdForUpdate(Integer id);



    /**
     * 根据条件查询信息列表
     * @param
     * @return
     */
    List<SagasProcessFlow> queryListByParam( SagasProcessFlowQuery sagasProcessFlowQuery);

    /**
     * 根据条件查询信息总数目
     * @param
     * @return
     */
    Long queryCountByParam(SagasProcessFlowQuery sagasProcessFlowQuery);

    /**
     * 根据订单号查询过程单据
     * @param orderNo
     * @return
     */
    List<SagasProcessFlow> queryByOrderNo(String orderNo);

    /**
     * 根据订单状态查询过程单据
     * @param statusEnum
     * @return
     */
    List<SagasProcessFlow> queryByStatus(ProcessStatusEnum statusEnum);

}
