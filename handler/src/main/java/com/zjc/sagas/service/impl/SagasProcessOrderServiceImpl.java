package com.zjc.sagas.service.impl;


import com.zjc.sagas.dao.SagasProcessOrderDao;


import com.zjc.sagas.enums.ProcessStatusEnum;
import com.zjc.sagas.model.SagasProcessOrder;
import com.zjc.sagas.query.SagasProcessOrderQuery;
import com.zjc.sagas.service.SagasProcessOrderService;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.Resource;

import java.util.List;

/**
 * SagasProcessOrderService 实现类
 * Created by AutoGenerate  on 18-11-26 下午2:54 .
 */
@Service
public class SagasProcessOrderServiceImpl implements SagasProcessOrderService {

    @Resource
    private SagasProcessOrderDao sagasProcessOrderDao;


    /**
     * 插入处理
     * @param sagasProcessOrder
     * @return
     */
    @Override
    public int insert(SagasProcessOrder sagasProcessOrder) {
        Assert.notNull(sagasProcessOrder, "插入对象为空");

        return sagasProcessOrderDao.insert(sagasProcessOrder);
    }

    /**
     * 根据id删除处理
     * @param id
     * @return
     */
    @Override
    public int deleteById(Integer id) {
        Assert.notNull(id, "删除记录id为空");

        return sagasProcessOrderDao.deleteById(id);
    }

    /**
     * 根据id更新处理
     * @param sagasProcessOrder
     * @return
     */
    @Override
    public int updateByProcessNoAndStatus(SagasProcessOrder sagasProcessOrder,Integer status) {
        Assert.notNull(sagasProcessOrder, "更新对象为空");
        Assert.notNull(sagasProcessOrder.getId(), "更新对象id为空");

        return sagasProcessOrderDao.updateById(sagasProcessOrder,status);
    }

    /**
     * 根据id查询处理
     * @param
     * @return
     */
    @Override
    public SagasProcessOrder selectByOrderNoAndOrder(String orderNo,Integer order) {
        Assert.notNull(order, "查询记录order为空");
        Assert.notNull(orderNo, "查询记录orderNo为空");

        return sagasProcessOrderDao.selectByOrderNoAndOrder(orderNo,order);
    }

    /**
     * 根据id查询处理 加锁
     * @param id
     * @return
     */
    @Override
    public SagasProcessOrder selectByIdForUpdate(Integer id) {
        Assert.notNull(id, "查询记录id为空");

        return sagasProcessOrderDao.selectByIdForUpdate(id);
    }



    /**
     * 根据条件查询信息列表
     * @param sagasProcessOrderQuery
     * @return
     */
    @Override
    public List<SagasProcessOrder> queryListByParam(SagasProcessOrderQuery sagasProcessOrderQuery) {
        Assert.notNull(sagasProcessOrderQuery, "查询参数为空");

        return sagasProcessOrderDao.queryListByParam(sagasProcessOrderQuery);
    }

    /**
     * 根据条件查询信息总数目
     * @param sagasProcessOrderQuery
     * @return
     */
    @Override
    public Long queryCountByParam(SagasProcessOrderQuery sagasProcessOrderQuery) {
        Assert.notNull(sagasProcessOrderQuery, "查询参数为空");

        return sagasProcessOrderDao.queryCountByParam(sagasProcessOrderQuery);
    }

    @Override
    public List<SagasProcessOrder> queryByOrderNo(String orderNo) {
        return null;
    }

    @Override
    public List<SagasProcessOrder> queryByStatus(ProcessStatusEnum statusEnum) {
        return null;
    }
}