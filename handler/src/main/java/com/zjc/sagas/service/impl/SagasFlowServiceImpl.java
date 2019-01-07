package com.zjc.sagas.service.impl;


import com.zjc.sagas.dao.SagasFlowDao;
import com.zjc.sagas.dao.SagasOrderDao;
import com.zjc.sagas.enums.MulStatusEnum;
import com.zjc.sagas.model.SagasFlow;
import com.zjc.sagas.model.SagasOrder;
import com.zjc.sagas.query.SagasFlowQuery;
import com.zjc.sagas.query.SagasOrderQuery;
import com.zjc.sagas.service.SagasFlowService;
import com.zjc.sagas.service.SagasOrderService;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.List;

/**
 * SagasOrderService 实现类
 * Created by AutoGenerate  on 18-11-26 下午2:54 .
 */
@Service
public class SagasFlowServiceImpl implements SagasFlowService {

    @Resource
    private SagasFlowDao sagasFlowDao;



    /**
     * 插入处理
     * @param
     * @return
     */
    @Override
    public int insert(SagasFlow sagasFlow) {
        Assert.notNull(sagasFlow, "插入对象为空");

        return sagasFlowDao.insert(sagasFlow);
    }

    /**
     * 根据id删除处理
     * @param id
     * @return
     */
    @Override
    public int deleteById(Integer id) {
        Assert.notNull(id, "删除记录id为空");

        return sagasFlowDao.deleteById(id);
    }

    /**
     * 根据id更新处理
     * @param sagasOrder
     * @return
     */
    @Override
    public int updateByOrderNoAndStatus(SagasFlow sagasFlow, Integer status) {
        Assert.notNull(sagasFlow, "更新对象为空");
        Assert.notNull(sagasFlow.getOrderNo(), "更新对象orderNo为空");

        return sagasFlowDao.updateByOrderNo(sagasFlow,status);
    }

    /**
     * 根据id查询处理
     * @param orderNo
     * @return
     */
    @Override
    public SagasFlow selectByOrderNo(String orderNo) {
        Assert.notNull(orderNo, "查询记录id为空");

        return sagasFlowDao.selectByOrderNo(orderNo);
    }

    /**
     * 根据id查询处理 加锁
     * @param id
     * @return
     */
    @Override
    public SagasFlow selectByIdForUpdate(Integer id) {
        Assert.notNull(id, "查询记录id为空");

        return sagasFlowDao.selectByIdForUpdate(id);
    }



    /**
     * 根据条件查询信息列表
     * @param
     * @return
     */
    @Override
    public List<SagasFlow> queryListByParam(SagasFlowQuery sagasFlowQuery) {
        Assert.notNull(sagasFlowQuery, "查询参数为空");

        return sagasFlowDao.queryListByParam(sagasFlowQuery);
    }

    /**
     * 根据条件查询信息总数目
     * @param
     * @return
     */
    @Override
    public Long queryCountByParam(SagasFlowQuery sagasFlowQuery) {
        Assert.notNull(sagasFlowQuery, "查询参数为空");

        return sagasFlowDao.queryCountByParam(sagasFlowQuery);
    }

    @Override
    public List<SagasFlow> queryByStatus(MulStatusEnum mulStatusEnum) {
        return null;
    }
}