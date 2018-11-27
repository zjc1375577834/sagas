package com.zjc.sagas.dao.impl;



import com.zjc.sagas.query.SagasOrderQuery;
import com.zjc.sagas.dao.SagasOrderDao;
import com.zjc.sagas.model.SagasOrder;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;


import java.util.Date;
import java.util.List;

/**
 * SagasOrderDao 实现类
 * Created by AutoGenerate  on 18-11-26 下午2:54 .
 */
@Repository
public class SagasOrderDaoImpl extends SqlSessionDaoSupport implements SagasOrderDao {


    /**
     * mybatis mapper的命名空间，包路径加类名
     */
    private static final String NAME_SPACE = SagasOrder.class.getName();

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
    * 校验SagasOrder必填项
    * @param sagasOrder
    */
    private void assertPO(SagasOrder sagasOrder){
        Assert.hasText(sagasOrder.getOrderNo(), "订单编号不能为空");
        Assert.notNull(sagasOrder.getParamHash(), "参数hash必须有值");
        Assert.notNull(sagasOrder.getType(), "类型必须有值");
        Assert.notNull(sagasOrder.getCreateTime(), "创建时间必须有值");
    }

    /**
     * 插入处理
     * @param sagasOrder
     * @return
     */
    @Override
    public int insert(SagasOrder sagasOrder) {

        Assert.notNull(sagasOrder, "插入对象不能为空");
        sagasOrder.setCreateTime(new Date());
        // 数据库插入 modifyTime设置为空
        sagasOrder.setModifyTime(null);

        assertPO(sagasOrder);
        return getSqlSession().insert(generateStatement("insert"), sagasOrder);
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
     * @param sagasOrder
     * @return
     */
    @Override
    public int updateByOrderNo(SagasOrder sagasOrder) {
        Assert.notNull(sagasOrder, "更新对象为空");
        Assert.notNull(sagasOrder.getOrderNo(), "更新对象orderNo为空");
        sagasOrder.setModifyTime(new Date());

        return getSqlSession().update(generateStatement("updateById"), sagasOrder);
    }

    /**
     * 根据id查询处理
     * @param id
     * @return
     */
    @Override
    public SagasOrder selectByOrderNo(String  orderNo) {
        Assert.notNull(orderNo, "查询记录id为空");

        return getSqlSession().selectOne(generateStatement("selectById"), orderNo);
    }

    /**
     * 根据id查询处理 加锁
     * @param id
     * @return
     */
    @Override
    public SagasOrder selectByIdForUpdate(Integer id) {
        Assert.notNull(id, "查询记录id为空");

        return getSqlSession().selectOne(generateStatement("selectByIdForUpdate"), id);
    }



    /**
     * 根据条件查询信息列表
     * @param sagasOrderQuery
     * @return
     */
    @Override
    public List<SagasOrder> queryListByParam(SagasOrderQuery sagasOrderQuery) {
        Assert.notNull(sagasOrderQuery, "查询参数为空");
        sagasOrderQuery.checkBaseQuery();

        checkIndexCondition(sagasOrderQuery);

        return getSqlSession().selectList(generateStatement("queryListByParam"), sagasOrderQuery);
    }

    /**
     * 根据条件查询信息总数目
     * @param sagasOrderQuery
     * @return
     */
    @Override
    public Long queryCountByParam(SagasOrderQuery sagasOrderQuery) {
        Assert.notNull(sagasOrderQuery, "查询参数为空");

        checkIndexCondition(sagasOrderQuery);

        return getSqlSession().selectOne(generateStatement("queryCountByParam"), sagasOrderQuery);
    }


    private void checkIndexCondition(SagasOrderQuery sagasOrderQuery) {
        if(
            sagasOrderQuery.getOrderNo() == null &&
            sagasOrderQuery.getParamHash() == null &&
            sagasOrderQuery.getStatus() == null &&
            sagasOrderQuery.getEgtCreateTime() == null &&
            sagasOrderQuery.getLtCreateTime() == null) {
            throw new RuntimeException("亲，请必须通过索引查询！");
        }
    }
}