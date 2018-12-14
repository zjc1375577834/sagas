package com.zjc.sagas.job;

import com.alibaba.fastjson.JSON;
import com.zjc.sagas.enums.MulStatusEnum;
import com.zjc.sagas.handler.ProcessControlImpl;
import com.zjc.sagas.interfaces.SagasProcessor;
import com.zjc.sagas.model.SagasContext;
import com.zjc.sagas.model.SagasDate;
import com.zjc.sagas.model.SagasOrder;
import com.zjc.sagas.model.SagasProcessOrder;
import com.zjc.sagas.query.SagasOrderQuery;
import com.zjc.sagas.query.SagasProcessOrderQuery;
import com.zjc.sagas.query.SortColumn;
import com.zjc.sagas.query.SortMode;
import com.zjc.sagas.service.SagasOrderService;
import com.zjc.sagas.service.SagasProcessOrderService;
import com.zjc.sagas.utils.SpringContextUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
public class SagasGetStatus4Job {
    @Autowired
    private SagasOrderService sagasOrderService;
    @Autowired
    private ProcessControlImpl processControl;
    @Autowired
    private SagasProcessOrderService sagasProcessOrderService;
    public void  getSagasDateList(MulStatusEnum mulStatusEnum) {
        SagasOrderQuery query = new SagasOrderQuery();
        query.setStatus(mulStatusEnum.getType());
        query.setOffset(0);
        query.setRows(100);
        List<SagasOrder> sagasOrders = sagasOrderService.queryListByParam(query);
        for (SagasOrder sagasOrder : sagasOrders) {
            LinkedList<SagasDate> sagasDates = new LinkedList<>();
            SagasProcessOrderQuery orderQuery = new SagasProcessOrderQuery();
            orderQuery.setOrderNo(sagasOrder.getOrderNo());
            orderQuery.setOffset(0);
            orderQuery.setRows(100);
            orderQuery.setSorts(new SortColumn("`order`", SortMode.ASC));
            List<SagasProcessOrder> sagasProcessOrders = sagasProcessOrderService.queryListByParam(orderQuery);
            for (SagasProcessOrder sagasProcessOrder :sagasProcessOrders) {
                try {
                    Class className =Class.forName(sagasProcessOrder.getClassName());
                    String param = sagasProcessOrder.getParam();
                    SagasContext sagasContext = JSON.parseObject(param, SagasContext.class);
                    SagasDate date = new SagasDate();
                    date.setContext(sagasContext);
                    date.setProcessor(className);
                    sagasDates.add(date);
                }catch (Exception e) {
                    e.printStackTrace();
                }
            }
            processControl.control(sagasDates,sagasOrder.getOrderNo(),sagasOrder.getType());
        }

    }
}
