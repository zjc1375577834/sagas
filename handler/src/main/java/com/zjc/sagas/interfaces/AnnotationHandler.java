package com.zjc.sagas.interfaces;

import com.zjc.sagas.enums.ProcessStatusEnum;
import com.zjc.sagas.model.SagasDate;

import java.util.List;

/**
 * create by zjc on 18/11/26
 */
public interface AnnotationHandler {
    ProcessStatusEnum handler(SagasDate sagasDate);
    ProcessStatusEnum rollbackHandler(SagasDate sagasDate);
}
