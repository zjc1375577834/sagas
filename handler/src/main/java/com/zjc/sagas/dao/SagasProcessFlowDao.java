package com.zjc.sagas.dao;

import com.zjc.sagas.model.SagasProcessFlow;
import com.zjc.sagas.query.SagasProcessFlowQuery;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 *
 * SagasProcessOrderDao接口
 * Created by AutoGenerate on 18-11-26 下午2:54 .
 */
public interface SagasProcessFlowDao {


    /**
     * 插入处理
     * @param
     * @return
     */
    int insert(SagasProcessFlow sagasProcessFlow);

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
    int updateById(@Param("sagasProcessOrder") SagasProcessFlow sagasProcessFlow, @Param("olderStatus") Integer status);

    /**
     * 根据id查询处理
     * @param id
     * @return
     */
    SagasProcessFlow  selectByOrderNoAndOrder(@Param("orderNo") String orderNo, @Param("order") Integer order);

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
    List<SagasProcessFlow> queryListByParam(SagasProcessFlowQuery sagasProcessFlowQuery);

    /**
     * 根据条件查询信息总数目
     * @param
     * @return
     */
    Long queryCountByParam(SagasProcessFlowQuery sagasProcessFlowQuery);

}
