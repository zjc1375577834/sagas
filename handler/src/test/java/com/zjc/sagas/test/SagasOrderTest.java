package com.zjc.sagas.test;


import com.alibaba.fastjson.JSON;
import com.zjc.sagas.model.SagasFlow;
import com.zjc.sagas.model.SagasProcessFlow;
import com.zjc.sagas.query.SagasOrderQuery;
import com.zjc.sagas.model.SagasOrder;
import com.zjc.sagas.service.SagasFlowService;
import com.zjc.sagas.service.SagasOrderService;
import com.zjc.sagas.service.SagasProcessFlowService;
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
    @Autowired
    private SagasFlowService sagasFlowService;
    @Autowired
    private SagasProcessFlowService sagasProcessFlowService;

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

//        System.out.println(" insert count: "+sagasOrderService.insert(po));
//        SagasOrder sagasOrder = sagasOrderService.selectByOrderNo("1234");
//        System.out.println(sagasOrder.getOrderNo());
        SagasFlow flow = new SagasFlow();
        flow.setOrderNo("12112");
        flow.setParamHash(12112);
        flow.setType(1);
        flow.setStatus(1);
        flow.setCreateTime(new Date());
        int insert = sagasFlowService.insert(flow);
        System.out.println(insert);
        SagasProcessFlow flow1 = new SagasProcessFlow();
        flow1.setOrderNo("12121");
        flow1.setProcessNo("121212");
        flow1.setParam("121");
        flow1.setClassName("");
        flow1.setOrder(1);
        flow1.setMothedName("");
        flow1.setReSend(1);

        flow1.setCreateTime(new Date());
        int insert1 = sagasProcessFlowService.insert(flow1);
        System.out.println(insert1);

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