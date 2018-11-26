package com.zjc.sagas.dao;

import com.zjc.sagas.Query.SagasProcessOrderQuery;
import com.zjc.sagas.model.SagasProcessOrder;

import java.util.List;

/**
 *
 * SagasProcessOrderDao接口
 * Created by AutoGenerate on 18-11-26 下午2:54 .
 */
public interface SagasProcessOrderDao {


    /**
     * 插入处理
     * @param sagasProcessOrder
     * @return
     */
    int insert(SagasProcessOrder sagasProcessOrder);

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
    int updateById(SagasProcessOrder sagasProcessOrder);

    /**
     * 根据id查询处理
     * @param id
     * @return
     */
    SagasProcessOrder selectById(Integer id);

    /**
     * 根据id查询处理 加锁
     * @param id
     * @return
     */
    SagasProcessOrder selectByIdForUpdate(Integer id);



    /**
     * 根据条件查询信息列表
     * @param sagasProcessOrderQuery
     * @return
     */
    List<SagasProcessOrder> queryListByParam(SagasProcessOrderQuery sagasProcessOrderQuery);

    /**
     * 根据条件查询信息总数目
     * @param sagasProcessOrderQuery
     * @return
     */
    Long queryCountByParam(SagasProcessOrderQuery sagasProcessOrderQuery);

}
