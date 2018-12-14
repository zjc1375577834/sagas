package com.zjc.sagas.handler;

import com.zjc.sagas.enums.ProcessStatusEnum;
import com.zjc.sagas.interfaces.AnnotationHandler;
import com.zjc.sagas.interfaces.SagasProcessor;
import com.zjc.sagas.model.SagasContext;
import com.zjc.sagas.model.SagasDate;
import com.zjc.sagas.utils.SpringContextUtil;

/**
 * create by zjc on 18/11/26
 */
public class SagasDefaultHandler implements AnnotationHandler {

    @Override
    public ProcessStatusEnum handler(SagasDate sagasDate) {
        SagasContext context = sagasDate.getContext();
        Class<?extends SagasProcessor> processor = sagasDate.getProcessor();
        SagasProcessor bean = SpringContextUtil.getBean(processor);
        ProcessStatusEnum processStatusEnum = bean.doCommit(context);
        return processStatusEnum;
    }

    @Override
    public ProcessStatusEnum rollbackHandler(SagasDate sagasDate) {
        SagasContext context = sagasDate.getContext();
        Class<?extends SagasProcessor> processor = sagasDate.getProcessor();
        SagasProcessor bean = SpringContextUtil.getBean(processor);
        ProcessStatusEnum processStatusEnum = bean.doCancel(context);
        return processStatusEnum;
    }

    @Override
    public ProcessStatusEnum handlerQuery(SagasDate sagasDate) {
        SagasContext context = sagasDate.getContext();
        Class<?extends SagasProcessor> processor = sagasDate.getProcessor();
        SagasProcessor bean = SpringContextUtil.getBean(processor);
        ProcessStatusEnum processStatusEnum = bean.commitQuery(context);
        return processStatusEnum;
    }

    @Override
    public ProcessStatusEnum rollbackQuery(SagasDate sagasDate) {
        SagasContext context = sagasDate.getContext();
        Class<?extends SagasProcessor> processor = sagasDate.getProcessor();
        SagasProcessor bean = SpringContextUtil.getBean(processor);
        ProcessStatusEnum processStatusEnum = bean.cancelQuery(context);
        return processStatusEnum;

    }
}
