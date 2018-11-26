package com.zjc.sagas.service.impl;


import com.zjc.sagas.inner.service.SagasProcessOrderService;
import com.zjc.sagas.inner.dao.SagasProcessOrderDao;
import com.zjc.sagas.facade.model.SagasProcessOrder;

import com.zjc.sagas.facade.query.SagasProcessOrderQuery;

import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.Resource;

import java.util.Date;
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
    public int updateById(SagasProcessOrder sagasProcessOrder) {
        Assert.notNull(sagasProcessOrder, "更新对象为空");
        Assert.notNull(sagasProcessOrder.getId(), "更新对象id为空");

        return sagasProcessOrderDao.updateById(sagasProcessOrder);
    }

    /**
     * 根据id查询处理
     * @param id
     * @return
     */
    @Override
    public SagasProcessOrder selectById(Integer id) {
        Assert.notNull(id, "查询记录id为空");

        return sagasProcessOrderDao.selectById(id);
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
}