package com.zjc.sagas.handler;

import com.zjc.sagas.enums.ProcessStatusEnum;
import com.zjc.sagas.interfaces.AnnotationHandler;
import com.zjc.sagas.interfaces.SagasProcessor;
import com.zjc.sagas.model.SagasContext;
import com.zjc.sagas.model.SagasDate;

/**
 * create by zjc on 18/11/26
 */
public class SagasDefaultHandler implements AnnotationHandler {

    @Override
    public ProcessStatusEnum handler(SagasDate sagasDate) {
        SagasContext context = sagasDate.getContext();
        SagasProcessor processor = sagasDate.getProcessor();
        ProcessStatusEnum processStatusEnum = processor.doCommit(context);
        return processStatusEnum;
    }
}
