package com.zjc.sagas.service;


import com.zjc.sagas.enums.MulStatusEnum;
import com.zjc.sagas.model.SagasFlow;
import com.zjc.sagas.query.SagasFlowQuery;

import java.util.List;

/**
 *
 * SagasOrderService接口
 * Created by AutoGenerate on 18-11-26 下午2:54 .
 */
public interface SagasFlowService {

    /**
     * 插入处理
     * @param sagasFlow
     * @return
     */
    int insert(SagasFlow sagasFlow);

    /**
     * 根据id删除处理
     * @param id
     * @return
     */
    int deleteById(Integer id);

    /**
     * 根据id更新处理
     * @param
     * @return
     */
    int updateByOrderNoAndStatus(SagasFlow sagasFlow, Integer status);

    /**
     * 根据id查询处理
     * @param id
     * @return
     */
    SagasFlow selectByOrderNo(String orderNo);

    /**
     * 根据id查询处理 加锁
     * @param id
     * @return
     */
    SagasFlow selectByIdForUpdate(Integer id);



    /**
     * 根据条件查询信息列表
     * @param
     * @return
     */
    List<SagasFlow> queryListByParam(SagasFlowQuery sagasFlowQuery);

    /**
     * 根据条件查询信息总数目
     * @param
     * @return
     */
    Long queryCountByParam( SagasFlowQuery sagasFlowQuery);

    /**
     * 根据订单状态查询订单
     * @param mulStatusEnum
     * @return
     */
    List<SagasFlow> queryByStatus(MulStatusEnum mulStatusEnum);

}
