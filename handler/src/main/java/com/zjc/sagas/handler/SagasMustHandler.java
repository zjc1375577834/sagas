package com.zjc.sagas.handler;

import com.zjc.sagas.enums.ProcessStatusEnum;
import com.zjc.sagas.interfaces.AnnotationHandler;
import com.zjc.sagas.interfaces.SagasProcessor;
import com.zjc.sagas.model.SagasContext;
import com.zjc.sagas.model.SagasDate;

/**
 * create by zjc in 2018-12-04
 */
public class SagasMustHandler implements AnnotationHandler {
    @Override
    public ProcessStatusEnum handler(SagasDate sagasDate) {
        SagasContext context = sagasDate.getContext();
        SagasProcessor processor = sagasDate.getProcessor();
        ProcessStatusEnum processStatusEnum = processor.doCommit(context);
        if (processStatusEnum.equals(ProcessStatusEnum.FAIL)) {
            return ProcessStatusEnum.NOBEGIN;
        }
        return processStatusEnum;
    }

    @Override
    public ProcessStatusEnum rollbackHandler(SagasDate sagasDate) {
        SagasContext context = sagasDate.getContext();
        SagasProcessor processor = sagasDate.getProcessor();
        ProcessStatusEnum processStatusEnum = processor.doCancel(context);
        if (processStatusEnum.equals(ProcessStatusEnum.FAIL)) {
            return ProcessStatusEnum.NOBEGIN;
        }
        return processStatusEnum;
    }

    @Override
    public ProcessStatusEnum handlerQuery(SagasDate sagasDate) {
        SagasContext context = sagasDate.getContext();
        SagasProcessor processor = sagasDate.getProcessor();
        ProcessStatusEnum processStatusEnum = processor.commitQuery(context);
        if (processStatusEnum.equals(ProcessStatusEnum.FAIL)) {
            return ProcessStatusEnum.NOBEGIN;
        }
        return processStatusEnum;
    }

    @Override
    public ProcessStatusEnum rollbackQuery(SagasDate sagasDate) {
        SagasContext context = sagasDate.getContext();
        SagasProcessor processor = sagasDate.getProcessor();
        ProcessStatusEnum processStatusEnum = processor.cancelQuery(context);
        if (processStatusEnum.equals(ProcessStatusEnum.FAIL)) {
            return ProcessStatusEnum.NOBEGIN;
        }
        return processStatusEnum;

    }
}
