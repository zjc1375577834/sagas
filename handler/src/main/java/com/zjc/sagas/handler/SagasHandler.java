package com.zjc.sagas.handler;

import com.zjc.sagas.enums.MulStatusEnum;
import com.zjc.sagas.model.SagasDate;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class SagasHandler {
    @Autowired
    private SagasInsertOrder sagasInsertOrder;
    @Autowired
    private ProcessControlImpl processControl;
    public MulStatusEnum handler(List<SagasDate> list, String orderNo, Integer type) {
        boolean b = sagasInsertOrder.insertOrder(list, orderNo, type);
        if (b) {
            MulStatusEnum control = processControl.control(list, orderNo, type);
            return control;
        }
        return MulStatusEnum.ING;
    }
}
