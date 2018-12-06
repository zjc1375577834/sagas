package com.zjc.sagas.dao;

import com.zjc.sagas.model.SagasOrderHistory;
import com.zjc.sagas.query.SagasOrderHistoryQuery;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 *
 * SagasOrderDao接口
 * Created by AutoGenerate on 18-11-26 下午2:54 .
 */
public interface SagasOrderHistoryDao {


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
     * @retur
     */
    int updateByOrderNo(@Param("sagasOrder") SagasOrderHistory sagasOrder, @Param("olderStatus") Integer status);

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

}
