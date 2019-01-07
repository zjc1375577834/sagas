package com.zjc.sagas.service.impl;


import com.zjc.sagas.dao.SagasProcessFlowDao;
import com.zjc.sagas.enums.ProcessStatusEnum;
import com.zjc.sagas.model.SagasProcessFlow;
import com.zjc.sagas.query.SagasProcessFlowQuery;
import com.zjc.sagas.service.SagasProcessFlowService;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.List;

/**
 * SagasProcessOrderService 实现类
 * Created by AutoGenerate  on 18-11-26 下午2:54 .
 */
@Service
public class SagasProcessFlowServiceImpl implements SagasProcessFlowService {

    @Resource
    private SagasProcessFlowDao sagasProcessFlowDao;


    /**
     * 插入处理
     * @param
     * @return
     */
    @Override
    public int insert(SagasProcessFlow sagasProcessFlow) {
        Assert.notNull(sagasProcessFlow, "插入对象为空");

        return sagasProcessFlowDao.insert(sagasProcessFlow);
    }

    /**
     * 根据id删除处理
     * @param id
     * @return
     */
    @Override
    public int deleteById(Integer id) {
        Assert.notNull(id, "删除记录id为空");

        return sagasProcessFlowDao.deleteById(id);
    }

    /**
     * 根据id更新处理
     * @param
     * @return
     */
    @Override
    public int updateByProcessNoAndStatus(SagasProcessFlow sagasProcessFlow,Integer status) {
        Assert.notNull(sagasProcessFlow, "更新对象为空");
        Assert.notNull(sagasProcessFlow.getProcessNo(), "更新对象id为空");

        return sagasProcessFlowDao.updateById(sagasProcessFlow,status);
    }

    /**
     * 根据id查询处理
     * @param
     * @return
     */
    @Override
    public SagasProcessFlow selectByOrderNoAndOrder(String orderNo,Integer order) {
        Assert.notNull(order, "查询记录order为空");
        Assert.notNull(orderNo, "查询记录orderNo为空");

        return sagasProcessFlowDao.selectByOrderNoAndOrder(orderNo,order);
    }

    /**
     * 根据id查询处理 加锁
     * @param id
     * @return
     */
    @Override
    public SagasProcessFlow selectByIdForUpdate(Integer id) {
        Assert.notNull(id, "查询记录id为空");

        return sagasProcessFlowDao.selectByIdForUpdate(id);
    }



    /**
     * 根据条件查询信息列表
     * @param
     * @return
     */
    @Override
    public List<SagasProcessFlow> queryListByParam(SagasProcessFlowQuery sagasProcessFlowQuery) {
        Assert.notNull(sagasProcessFlowQuery, "查询参数为空");

        return sagasProcessFlowDao.queryListByParam(sagasProcessFlowQuery);
    }

    /**
     * 根据条件查询信息总数目
     * @param
     * @return
     */
    @Override
    public Long queryCountByParam(SagasProcessFlowQuery sagasProcessFlowQuery) {
        Assert.notNull(sagasProcessFlowQuery, "查询参数为空");

        return sagasProcessFlowDao.queryCountByParam(sagasProcessFlowQuery);
    }

    @Override
    public List<SagasProcessFlow> queryByOrderNo(String orderNo) {
        return null;
    }

    @Override
    public List<SagasProcessFlow> queryByStatus(ProcessStatusEnum statusEnum) {
        return null;
    }
}