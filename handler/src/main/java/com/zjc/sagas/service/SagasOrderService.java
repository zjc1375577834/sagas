package com.zjc.sagas.service;


import com.zjc.sagas.Query.SagasOrderQuery;
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
    int updateById(SagasOrder sagasOrder);

    /**
     * 根据id查询处理
     * @param id
     * @return
     */
    SagasOrder selectById(Integer id);

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

}
