package com.zjc.sagas.service.impl;


import com.zjc.sagas.dao.SagasProcessOrderHistoryDao;
import com.zjc.sagas.enums.ProcessStatusEnum;
import com.zjc.sagas.model.SagasProcessOrderHistory;
import com.zjc.sagas.query.SagasProcessOrderHistoryQuery;
import com.zjc.sagas.service.SagasProcessOrderHistoryService;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.List;

/**
 * SagasProcessOrderHistoryService 实现类
 * Created by AutoGenerate  on 18-11-26 下午2:54 .
 */
@Service
public class SagasProcessOrderHistoryServiceImpl implements SagasProcessOrderHistoryService {

    @Resource
    private SagasProcessOrderHistoryDao SagasProcessOrderHistoryDao;


    /**
     * 插入处理
     * @param SagasProcessOrderHistory
     * @return
     */
    @Override
    public int insert(SagasProcessOrderHistory SagasProcessOrderHistory) {
        Assert.notNull(SagasProcessOrderHistory, "插入对象为空");

        return SagasProcessOrderHistoryDao.insert(SagasProcessOrderHistory);
    }

    /**
     * 根据id删除处理
     * @param id
     * @return
     */
    @Override
    public int deleteById(Integer id) {
        Assert.notNull(id, "删除记录id为空");

        return SagasProcessOrderHistoryDao.deleteById(id);
    }

    /**
     * 根据id更新处理
     * @param SagasProcessOrderHistory
     * @return
     */
    @Override
    public int updateByProcessNoAndStatus(SagasProcessOrderHistory SagasProcessOrderHistory,Integer status) {
        Assert.notNull(SagasProcessOrderHistory, "更新对象为空");
        Assert.notNull(SagasProcessOrderHistory.getProcessNo(), "更新对象id为空");

        return SagasProcessOrderHistoryDao.updateById(SagasProcessOrderHistory,status);
    }

    /**
     * 根据id查询处理
     * @param
     * @return
     */
    @Override
    public SagasProcessOrderHistory selectByOrderNoAndOrder(String orderNo,Integer order) {
        Assert.notNull(order, "查询记录order为空");
        Assert.notNull(orderNo, "查询记录orderNo为空");

        return SagasProcessOrderHistoryDao.selectByOrderNoAndOrder(orderNo,order);
    }

    /**
     * 根据id查询处理 加锁
     * @param id
     * @return
     */
    @Override
    public SagasProcessOrderHistory selectByIdForUpdate(Integer id) {
        Assert.notNull(id, "查询记录id为空");

        return SagasProcessOrderHistoryDao.selectByIdForUpdate(id);
    }



    /**
     * 根据条件查询信息列表
     * @param SagasProcessOrderHistoryQuery
     * @return
     */
    @Override
    public List<SagasProcessOrderHistory> queryListByParam(SagasProcessOrderHistoryQuery SagasProcessOrderHistoryQuery) {
        Assert.notNull(SagasProcessOrderHistoryQuery, "查询参数为空");

        return SagasProcessOrderHistoryDao.queryListByParam(SagasProcessOrderHistoryQuery);
    }

    /**
     * 根据条件查询信息总数目
     * @param SagasProcessOrderHistoryQuery
     * @return
     */
    @Override
    public Long queryCountByParam(SagasProcessOrderHistoryQuery SagasProcessOrderHistoryQuery) {
        Assert.notNull(SagasProcessOrderHistoryQuery, "查询参数为空");

        return SagasProcessOrderHistoryDao.queryCountByParam(SagasProcessOrderHistoryQuery);
    }

    @Override
    public List<SagasProcessOrderHistory> queryByOrderNo(String orderNo) {
        return null;
    }

    @Override
    public List<SagasProcessOrderHistory> queryByStatus(ProcessStatusEnum statusEnum) {
        return null;
    }
}