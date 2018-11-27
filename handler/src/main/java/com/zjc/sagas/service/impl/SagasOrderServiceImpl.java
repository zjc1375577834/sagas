package com.zjc.sagas.service.impl;




import com.zjc.sagas.enums.MulStatusEnum;
import com.zjc.sagas.query.SagasOrderQuery;
import com.zjc.sagas.dao.SagasOrderDao;
import com.zjc.sagas.model.SagasOrder;
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
public class SagasOrderServiceImpl implements SagasOrderService {

    @Resource
    private SagasOrderDao sagasOrderDao;


    /**
     * 插入处理
     * @param sagasOrder
     * @return
     */
    @Override
    public int insert(SagasOrder sagasOrder) {
        Assert.notNull(sagasOrder, "插入对象为空");

        return sagasOrderDao.insert(sagasOrder);
    }

    /**
     * 根据id删除处理
     * @param id
     * @return
     */
    @Override
    public int deleteById(Integer id) {
        Assert.notNull(id, "删除记录id为空");

        return sagasOrderDao.deleteById(id);
    }

    /**
     * 根据id更新处理
     * @param sagasOrder
     * @return
     */
    @Override
    public int updateByOrderNo(SagasOrder sagasOrder) {
        Assert.notNull(sagasOrder, "更新对象为空");
        Assert.notNull(sagasOrder.getOrderNo(), "更新对象orderNo为空");

        return sagasOrderDao.updateById(sagasOrder);
    }

    /**
     * 根据id查询处理
     * @param id
     * @return
     */
    @Override
    public SagasOrder selectById(Integer id) {
        Assert.notNull(id, "查询记录id为空");

        return sagasOrderDao.selectById(id);
    }

    /**
     * 根据id查询处理 加锁
     * @param id
     * @return
     */
    @Override
    public SagasOrder selectByIdForUpdate(Integer id) {
        Assert.notNull(id, "查询记录id为空");

        return sagasOrderDao.selectByIdForUpdate(id);
    }



    /**
     * 根据条件查询信息列表
     * @param sagasOrderQuery
     * @return
     */
    @Override
    public List<SagasOrder> queryListByParam(SagasOrderQuery sagasOrderQuery) {
        Assert.notNull(sagasOrderQuery, "查询参数为空");

        return sagasOrderDao.queryListByParam(sagasOrderQuery);
    }

    /**
     * 根据条件查询信息总数目
     * @param sagasOrderQuery
     * @return
     */
    @Override
    public Long queryCountByParam(SagasOrderQuery sagasOrderQuery) {
        Assert.notNull(sagasOrderQuery, "查询参数为空");

        return sagasOrderDao.queryCountByParam(sagasOrderQuery);
    }

    @Override
    public List<SagasOrder> queryByStatus(MulStatusEnum mulStatusEnum) {
        return null;
    }
}