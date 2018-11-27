package com.zjc.sagas.service;


import com.zjc.sagas.enums.MulStatusEnum;
import com.zjc.sagas.query.SagasOrderQuery;
import com.zjc.sagas.model.SagasOrder;

import java.util.List;

/**
 *
 * SagasOrderService接口
 * Created by AutoGenerate on 18-11-26 下午2:54 .
 */
public interface SagasOrderService {

    /**
     * 插入处理
     * @param sagasOrder
     * @return
     */
    int insert(SagasOrder sagasOrder);

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
    int updateByOrderNo(SagasOrder sagasOrder);

    /**
     * 根据id查询处理
     * @param id
     * @return
     */
    SagasOrder selectByOrderNo(String orderNo);

    /**
     * 根据id查询处理 加锁
     * @param id
     * @return
     */
    SagasOrder selectByIdForUpdate(Integer id);



    /**
     * 根据条件查询信息列表
     * @param sagasOrderQuery
     * @return
     */
    List<SagasOrder> queryListByParam(SagasOrderQuery sagasOrderQuery);

    /**
     * 根据条件查询信息总数目
     * @param sagasOrderQuery
     * @return
     */
    Long queryCountByParam(SagasOrderQuery sagasOrderQuery);

    /**
     * 根据订单状态查询订单
     * @param mulStatusEnum
     * @return
     */
    List<SagasOrder> queryByStatus(MulStatusEnum mulStatusEnum);

}
