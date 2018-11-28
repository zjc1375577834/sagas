package com.zjc.sagas.dao.impl;


import com.zjc.sagas.query.SagasProcessOrderQuery;
import com.zjc.sagas.dao.SagasProcessOrderDao;

import com.zjc.sagas.model.SagasProcessOrder;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;


import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * SagasProcessOrderDao 实现类
 * Created by AutoGenerate  on 18-11-26 下午2:54 .
 */
@Repository
public class SagasProcessOrderDaoImpl extends SqlSessionDaoSupport implements SagasProcessOrderDao {


    /**
     * mybatis mapper的命名空间，包路径加类名
     */
    private static final String NAME_SPACE = SagasProcessOrder.class.getName();

    /**
     * 返回mybatis 执行代码语句块定位字符串，是namespace.id 组成
     *
     * @param id id主键
     * @return namespace.id
     */
    public String generateStatement(String id) {
        return NAME_SPACE.concat(".").concat(id);
    }

    /**
    * 校验SagasProcessOrder必填项
    * @param sagasProcessOrder
    */
    private void assertPO(SagasProcessOrder sagasProcessOrder){
        Assert.hasText(sagasProcessOrder.getOrderNo(), "订单编号不能为空");
        Assert.hasText(sagasProcessOrder.getProcessNo(), "过程编号不能为空");
        Assert.hasText(sagasProcessOrder.getParam(), "参数不能为空");
        Assert.hasText(sagasProcessOrder.getClassName(), "类名不能为空");
        Assert.hasText(sagasProcessOrder.getMothedName(), "方法名不能为空");
        Assert.notNull(sagasProcessOrder.getOrder(), "序号必须有值");
        Assert.notNull(sagasProcessOrder.getReSend(), "重发次数必须有值");
        Assert.notNull(sagasProcessOrder.getCreateTime(), "创建时间必须有值");
    }

    /**
     * 插入处理
     * @param sagasProcessOrder
     * @return
     */
    @Override
    public int insert(SagasProcessOrder sagasProcessOrder) {

        Assert.notNull(sagasProcessOrder, "插入对象不能为空");
        sagasProcessOrder.setCreateTime(new Date());
        // 数据库插入 modifyTime设置为空
        sagasProcessOrder.setModifyTime(null);

        assertPO(sagasProcessOrder);
        return getSqlSession().insert(generateStatement("insert"), sagasProcessOrder);
    }

    /**
     * 根据id删除处理
     * @param id
     * @return
     */
    @Override
    public int deleteById(Integer id) {
        Assert.notNull(id, "删除记录id为空");

        return getSqlSession().delete(generateStatement("deleteById"), id);
    }

    /**
     * 根据id更新处理
     * @param sagasProcessOrder
     * @return
     */
    @Override
    public int updateById(SagasProcessOrder sagasProcessOrder,Integer status) {
        Assert.notNull(sagasProcessOrder, "更新对象为空");
        Assert.notNull(sagasProcessOrder.getProcessNo(), "更新对象id为空");
        sagasProcessOrder.setModifyTime(new Date());
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("sagasProcessOrder",sagasProcessOrder);
        map.put("olderStatus",status);
        return getSqlSession().update(generateStatement("updateById"), map);
    }

    /**
     * 根据id查询处理
     * @param id
     * @return
     */
    @Override
    public SagasProcessOrder selectByOrderNoAndOrder(String orderNo,Integer order) {
        Assert.notNull(orderNo, "更新对象为空");
        Assert.notNull(order, "更新对象为空");

        Map<String,Object> map = new HashMap<String,Object>();
        map.put("orderNo",orderNo);
        map.put("order",order);

        return getSqlSession().selectOne(generateStatement("selectById"), map);
    }

    /**
     * 根据id查询处理 加锁
     * @param id
     * @return
     */
    @Override
    public SagasProcessOrder selectByIdForUpdate(Integer id) {
        Assert.notNull(id, "查询记录id为空");

        return getSqlSession().selectOne(generateStatement("selectByIdForUpdate"), id);
    }



    /**
     * 根据条件查询信息列表
     * @param sagasProcessOrderQuery
     * @return
     */
    @Override
    public List<SagasProcessOrder> queryListByParam(SagasProcessOrderQuery sagasProcessOrderQuery) {
        Assert.notNull(sagasProcessOrderQuery, "查询参数为空");
        sagasProcessOrderQuery.checkBaseQuery();

        checkIndexCondition(sagasProcessOrderQuery);

        return getSqlSession().selectList(generateStatement("queryListByParam"), sagasProcessOrderQuery);
    }

    /**
     * 根据条件查询信息总数目
     * @param sagasProcessOrderQuery
     * @return
     */
    @Override
    public Long queryCountByParam(SagasProcessOrderQuery sagasProcessOrderQuery) {
        Assert.notNull(sagasProcessOrderQuery, "查询参数为空");

        checkIndexCondition(sagasProcessOrderQuery);

        return getSqlSession().selectOne(generateStatement("queryCountByParam"), sagasProcessOrderQuery);
    }


    private void checkIndexCondition(SagasProcessOrderQuery sagasProcessOrderQuery) {
        if(
            sagasProcessOrderQuery.getOrderNo() == null &&
            sagasProcessOrderQuery.getProcessNo() == null &&
            sagasProcessOrderQuery.getEgtCreateTime() == null &&
            sagasProcessOrderQuery.getLtCreateTime() == null) {
            throw new RuntimeException("亲，请必须通过索引查询！");
        }
    }
}