package com.zjc.sagas.handler;

import com.zjc.sagas.enums.MulStatusEnum;
import com.zjc.sagas.enums.ProcessStatusEnum;
import com.zjc.sagas.interfaces.ProcessorDistribute;
import com.zjc.sagas.model.SagasOrder;
import com.zjc.sagas.model.SagasProcessOrder;
import com.zjc.sagas.service.SagasOrderService;
import com.zjc.sagas.service.SagasProcessOrderService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

/**
 * create by zjc on 18/11/29
 */
public class SagasAsynDistributeImpl implements ProcessorDistribute {
    @Autowired
    private SagasOrderService sagasOrderService;
    @Autowired
    private SagasProcessOrderService sagasProcessOrderService;
    @Override
    public void distribute(String orderNo, Integer order, Map<String, Object> result) {
        SagasOrder sagasOrder = sagasOrderService.selectByOrderNo(orderNo);
        SagasProcessOrder sagasProcessOrder = sagasProcessOrderService.selectByOrderNoAndOrder(orderNo, order);
        if (sagasOrder.getStatus() == MulStatusEnum.INIT.getType() || sagasOrder.getStatus() == MulStatusEnum.ING.getType()) {
            String mothedName = sagasProcessOrder.getMothedName();
            boolean roll = "doCancel".equals(mothedName);
            ProcessStatusEnum statusEnum = ProcessStatusEnum.getByType(sagasProcessOrder.getStatus());
            if (roll) {

            }else {
                
            }
        }
    }
}
