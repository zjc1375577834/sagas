package com.zjc.sagas.dao;

import com.zjc.sagas.model.SagasFlow;
import com.zjc.sagas.query.SagasFlowQuery;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 *
 * SagasOrderDao接口
 * Created by AutoGenerate on 18-11-26 下午2:54 .
 */
public interface SagasFlowDao {


    /**
     * 插入处理
     * @param
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
     * @retur
     */
    int updateByOrderNo(@Param("sagasOrder") SagasFlow sagasFlow, @Param("olderStatus") Integer status);

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
    Long queryCountByParam(SagasFlowQuery sagasFlowQuery);

}
