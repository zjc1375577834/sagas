package com.zjc.sagas.handler;

import com.zjc.sagas.enums.MulStatusEnum;
import com.zjc.sagas.interfaces.ProcessControl;
import com.zjc.sagas.model.SagasDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class SagasHandler {
    @Autowired
    private SagasInsertOrder sagasInsertOrder;
    @Autowired
    private ProcessControl processControl;
    public MulStatusEnum handler(List<SagasDate> list, String orderNo, Integer type) {
        boolean b = sagasInsertOrder.insertOrder(list, orderNo, type);
        if (b) {
            MulStatusEnum control = processControl.control(list, orderNo, type);
            return control;
        }
        return MulStatusEnum.ING;
    }
}
