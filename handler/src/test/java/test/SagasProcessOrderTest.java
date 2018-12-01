package test;


import com.alibaba.fastjson.JSON;
import com.zjc.sagas.query.SagasProcessOrderQuery;
import com.zjc.sagas.model.SagasProcessOrder;
import com.zjc.sagas.service.SagasProcessOrderService;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * SagasProcessOrderTest 测试类
 * Created by tianshu  on 18-11-26 下午2:54 .
 */
public class SagasProcessOrderTest extends BaseTestCase {

    @Autowired
    private SagasProcessOrderService sagasProcessOrderService;

    /**
    * 测试插入
    */
    @Test
    public void testInsert() throws Throwable {

        SagasProcessOrder po = new SagasProcessOrder();
//        po.setOrderNo("");
//        po.setProcessNo("");
//        po.setParam("");
//        po.setClassName("");
//        po.setMothedName("");
//        po.setOrder("");
//        po.setReSend("");

//        System.out.println(" insert count: "+sagasProcessOrderService.insert(po));
        SagasProcessOrder sagasProcessOrder = sagasProcessOrderService.selectByOrderNoAndOrder("ceshi20181201134345046615", 1);
        System.out.println(sagasProcessOrder.getOrderNo());

    }

@Test
    public void testupdate() {
        SagasProcessOrder order = new SagasProcessOrder();
        order.setOrderNo("ceshi20181201134345046615");
        order.setStatus(1);
        order.setProcessNo("process20181201134345948252");
        sagasProcessOrderService.updateByProcessNoAndStatus(order,0);
    }

    /**
    * 测试查询总量
    */
    @Test
    public void testQueryCountByParam() {

        SagasProcessOrderQuery sagasProcessOrderQuery = new SagasProcessOrderQuery();

        System.out.println(" queryCountByParam param "+ JSON.toJSONString(sagasProcessOrderQuery) +
        "   count size :" + sagasProcessOrderService.queryCountByParam(sagasProcessOrderQuery));

    }

    /**
    * 测试查询list
    */
    @Test
    public void testQueryListByParam() {

        SagasProcessOrderQuery sagasProcessOrderQuery = new SagasProcessOrderQuery();
        sagasProcessOrderQuery.setRows(10);
        sagasProcessOrderQuery.setOffset(0);
        sagasProcessOrderQuery.setOrder(1);
        System.out.println(" queryListByParam param "+ JSON.toJSONString(sagasProcessOrderQuery) +
        "   result size :" + sagasProcessOrderService.queryListByParam(sagasProcessOrderQuery).size()  +
        "   result :" + JSON.toJSONString(sagasProcessOrderService.queryListByParam(sagasProcessOrderQuery)));

    }

}