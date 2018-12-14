package com.zjc.sagas.model;

import com.zjc.sagas.interfaces.SagasProcessor;

/**
 * create by zjc on 18/11/26
 */
public class SagasDate {
    private SagasContext context;
    private Class<?extends SagasProcessor> processor;

    public SagasContext getContext() {
        return context;
    }

    public void setContext(SagasContext context) {
        this.context = context;
    }

    public Class<?extends SagasProcessor> getProcessor() {
        return processor;
    }

    public void setProcessor(Class<?extends SagasProcessor> processor) {
        this.processor = processor;
    }
}
