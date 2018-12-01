package test;


import com.alibaba.fastjson.JSON;
import com.zjc.sagas.query.SagasOrderQuery;
import com.zjc.sagas.model.SagasOrder;
import com.zjc.sagas.service.SagasOrderService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import java.util.Date;

/**
 * SagasOrderTest 测试类
 * Created by tianshu  on 18-11-26 下午2:54 .
 */
public class SagasOrderTest extends BaseTestCase {

    @Autowired
    private SagasOrderService sagasOrderService;

    /**
    * 测试插入
    */
    @Rollback(false)
    @Test
    public void testInsert() throws Throwable {

        SagasOrder po = new SagasOrder();
        po.setOrderNo("12345");
        po.setParamHash(1211);
        po.setType(1);
        po.setStatus(1);
        po.setCreateTime(new Date());

        System.out.println(" insert count: "+sagasOrderService.insert(po));
        SagasOrder sagasOrder = sagasOrderService.selectByOrderNo("1234");
        System.out.println(sagasOrder.getOrderNo());

    }




    /**
    * 测试查询总量
    */
    @Test
    public void testQueryCountByParam() {

        SagasOrderQuery sagasOrderQuery = new SagasOrderQuery();

        System.out.println(" queryCountByParam param "+ JSON.toJSONString(sagasOrderQuery) +
        "   count size :" + sagasOrderService.queryCountByParam(sagasOrderQuery));

    }

    /**
    * 测试查询list
    */
    @Test
    public void testQueryListByParam() {

        SagasOrderQuery sagasOrderQuery = new SagasOrderQuery();
        sagasOrderQuery.setStatus(1);
        sagasOrderQuery.setOffset(0);
        sagasOrderQuery.setRows(10);

        System.out.println(" queryListByParam param "+ JSON.toJSONString(sagasOrderQuery) +
        "   result size :" + sagasOrderService.queryListByParam(sagasOrderQuery).size()  +
        "   result :" + JSON.toJSONString(sagasOrderService.queryListByParam(sagasOrderQuery)));

    }

}