package com.zjc.sagas.dao;

import com.zjc.sagas.model.SagasBusinessLock;
import com.zjc.sagas.model.SagasFlow;
import com.zjc.sagas.query.SagasFlowQuery;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 *
 * SagasOrderDao接口
 * Created by AutoGenerate on 18-11-26 下午2:54 .
 */
public interface SagasBusinessLockDao {


    /**
     * 插入处理
     * @param
     * @return
     */
    int insert(SagasBusinessLock sagasBusinessLock);

    /**
     * 根据id删除处理
     * @param id
     * @return
     */
    int deleteById(Integer id);

    /**
     * 根据id更新处理
     * @param
     * @retur
     */
    int updateByOrderNo(@Param("sagasOrder") SagasBusinessLock sagasBusinessLock);

    /**
     * 根据id查询处理
     * @param id
     * @return
     */
    SagasBusinessLock selectByOrderNo(String orderNo);

    /**
     * 根据id查询处理 加锁
     * @param id
     * @return
     */
    SagasBusinessLock selectByIdForUpdate(Integer id);




}
