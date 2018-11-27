package com.zjc.sagas.model;

import com.zjc.sagas.interfaces.SagasProcessor;

/**
 * create by zjc on 18/11/26
 */
public class SagasDate {
    private SagasContext context;
    private SagasProcessor processor;

    public SagasContext getContext() {
        return context;
    }

    public void setContext(SagasContext context) {
        this.context = context;
    }

    public SagasProcessor getProcessor() {
        return processor;
    }

    public void setProcessor(SagasProcessor processor) {
        this.processor = processor;
    }
}
