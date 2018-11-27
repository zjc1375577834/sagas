package test;


import com.alibaba.fastjson.JSON;
import com.zjc.sagas.query.SagasOrderQuery;
import com.zjc.sagas.model.SagasOrder;
import com.zjc.sagas.service.SagasOrderService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

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
    @Test
    public void testInsert() throws Throwable {

        SagasOrder po = new SagasOrder();
//        po.setOrderNo("");
//        po.setParamHash("");
//        po.setType("");

        System.out.println(" insert count: "+sagasOrderService.insert(po));

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

        System.out.println(" queryListByParam param "+ JSON.toJSONString(sagasOrderQuery) +
        "   result size :" + sagasOrderService.queryListByParam(sagasOrderQuery).size()  +
        "   result :" + JSON.toJSONString(sagasOrderService.queryListByParam(sagasOrderQuery)));

    }

}