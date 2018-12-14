package com.zjc.sagas.test;

import com.zjc.sagas.annotation.Sagas;
import com.zjc.sagas.enums.ProcessStatusEnum;
import com.zjc.sagas.interfaces.SagasProcessor;
import com.zjc.sagas.model.SagasContext;
import org.springframework.stereotype.Service;

@Sagas(isOrder = false)
@Service
public class SagasProcessorImpl1 implements SagasProcessor {
    @Override
    public ProcessStatusEnum doCommit(SagasContext context) {
        Boolean param = (Boolean) context.getParam();
        if (param.equals(true)){
            return ProcessStatusEnum.SUC;
        }
        return ProcessStatusEnum.FAIL;
    }

    @Override
    public ProcessStatusEnum commitQuery(SagasContext context) {
        Boolean param = (Boolean) context.getParam();
        if (param.equals(true)){
            return ProcessStatusEnum.SUC;
        }
        return ProcessStatusEnum.FAIL;
    }

    @Override
    public ProcessStatusEnum doCancel(SagasContext context) {
        Boolean param = (Boolean) context.getParam();
        if (param.equals(true)){
            return ProcessStatusEnum.SUC;
        }
        return ProcessStatusEnum.ING;
    }

    @Override
    public ProcessStatusEnum cancelQuery(SagasContext context) {
        Boolean param = (Boolean) context.getParam();
        if (param.equals(true)){
            return ProcessStatusEnum.SUC;
        }
        return ProcessStatusEnum.ING;
    }
}
