package com.zjc.sagas.dao;

import com.zjc.sagas.model.SagasProcessOrderHistory;
import com.zjc.sagas.query.SagasProcessOrderHistoryQuery;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 *
 * SagasProcessOrderDao接口
 * Created by AutoGenerate on 18-11-26 下午2:54 .
 */
public interface SagasProcessOrderHistoryDao {


    /**
     * 插入处理
     * @param sagasProcessOrder
     * @return
     */
    int insert(SagasProcessOrderHistory sagasProcessOrder);

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
    int updateById(@Param("sagasProcessOrder") SagasProcessOrderHistory sagasProcessOrder, @Param("olderStatus") Integer status);

    /**
     * 根据id查询处理
     * @param id
     * @return
     */
    SagasProcessOrderHistory  selectByOrderNoAndOrder(@Param("orderNo") String orderNo, @Param("order") Integer order);

    /**
     * 根据id查询处理 加锁
     * @param id
     * @return
     */
    SagasProcessOrderHistory selectByIdForUpdate(Integer id);



    /**
     * 根据条件查询信息列表
     * @param sagasProcessOrderQuery
     * @return
     */
    List<SagasProcessOrderHistory> queryListByParam(SagasProcessOrderHistoryQuery sagasProcessOrderQuery);

    /**
     * 根据条件查询信息总数目
     * @param sagasProcessOrderQuery
     * @return
     */
    Long queryCountByParam(SagasProcessOrderHistoryQuery sagasProcessOrderQuery);

}
