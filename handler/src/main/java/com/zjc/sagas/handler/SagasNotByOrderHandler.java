package com.zjc.sagas.handler;

import com.zjc.sagas.enums.ProcessStatusEnum;
import com.zjc.sagas.interfaces.AnnotationHandler;
import com.zjc.sagas.model.SagasDate;

/**
 * create by zjc on 18/11/26
 */
public class SagasNotByOrderHandler implements AnnotationHandler {

    @Override
    public ProcessStatusEnum handler(SagasDate sagasDate) {
        return null;
    }
}
