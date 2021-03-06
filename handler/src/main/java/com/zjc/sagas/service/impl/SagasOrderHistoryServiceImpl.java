package com.zjc.sagas.service.impl;


import com.zjc.sagas.dao.SagasFlowDao;
import com.zjc.sagas.dao.SagasOrderHistoryDao;
import com.zjc.sagas.enums.MulStatusEnum;
import com.zjc.sagas.model.SagasOrderHistory;
import com.zjc.sagas.query.SagasOrderHistoryQuery;
import com.zjc.sagas.query.SagasProcessOrderHistoryQuery;
import com.zjc.sagas.service.SagasOrderHistoryService;
import com.zjc.sagas.utils.BeanCopyUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.List;

/**
 * SagasOrderHistoryService 实现类
 * Created by AutoGenerate  on 18-11-26 下午2:54 .
 */
@Service
public class SagasOrderHistoryServiceImpl implements SagasOrderHistoryService {

    @Resource
    private SagasOrderHistoryDao SagasOrderHistoryDao;
    @Resource
    private SagasFlowDao sagasFlowDao;



    /**
     * 插入处理
     * @param
     * @return
     */
    @Override
    public int insert(SagasOrderHistory sagasOrderHistory) {
        Assert.notNull(sagasOrderHistory, "插入对象为空");

        sagasFlowDao.insert(BeanCopyUtils.copy(sagasOrderHistory));
        return SagasOrderHistoryDao.insert(sagasOrderHistory);
    }

    /**
     * 根据id删除处理
     * @param id
     * @return
     */
    @Override
    public int deleteById(Integer id) {
        Assert.notNull(id, "删除记录id为空");

        return SagasOrderHistoryDao.deleteById(id);
    }

    /**
     * 根据id更新处理
     * @param sagasOrderHistory
     * @return
     */
    @Override
    public int updateByOrderNoAndStatus(SagasOrderHistory sagasOrderHistory, Integer status) {
        Assert.notNull(sagasOrderHistory, "更新对象为空");
        Assert.notNull(sagasOrderHistory.getOrderNo(), "更新对象orderNo为空");
        sagasFlowDao.insert(BeanCopyUtils.copy(sagasOrderHistory));

        return SagasOrderHistoryDao.updateByOrderNo(sagasOrderHistory,status);
    }

    /**
     * 根据id查询处理
     * @param orderNo
     * @return
     */
    @Override
    public SagasOrderHistory selectByOrderNo(String orderNo) {
        Assert.notNull(orderNo, "查询记录id为空");

        return SagasOrderHistoryDao.selectByOrderNo(orderNo);
    }

    /**
     * 根据id查询处理 加锁
     * @param id
     * @return
     */
    @Override
    public SagasOrderHistory selectByIdForUpdate(Integer id) {
        Assert.notNull(id, "查询记录id为空");

        return SagasOrderHistoryDao.selectByIdForUpdate(id);
    }

    @Override
    public List<SagasOrderHistory> queryListByParam(SagasOrderHistoryQuery sagasOrderQuery) {
        Assert.notNull(sagasOrderQuery, "查询参数为空");

        return SagasOrderHistoryDao.queryListByParam(sagasOrderQuery);
    }


    /**
     * 根据条件查询信息列表
     * @param SagasOrderHistoryQuery
     * @return
     */


    /**
     * 根据条件查询信息总数目
     * @param SagasOrderHistoryQuery
     * @return
     */
    @Override
    public Long queryCountByParam(SagasOrderHistoryQuery SagasOrderHistoryQuery) {
        Assert.notNull(SagasOrderHistoryQuery, "查询参数为空");

        return SagasOrderHistoryDao.queryCountByParam(SagasOrderHistoryQuery);
    }

    @Override
    public List<SagasOrderHistory> queryByStatus(MulStatusEnum mulStatusEnum) {
        return null;
    }
}