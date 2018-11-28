package com.zjc.sagas.interfaces;

import com.zjc.sagas.enums.ProcessStatusEnum;
import com.zjc.sagas.model.SagasContext;

/**
 * create by zjc on 18/11/26
 */
public interface SagasProcessor {
    /**
     * 流程执行方法
     */
    ProcessStatusEnum doCommit(SagasContext context);

    /**
     * 执行结果查询方法
     * @param context
     * @return
     */
    ProcessStatusEnum commitQuery(SagasContext context);

    /**
     * 流程回滚方法
     * @param context
     * @return
     */
    ProcessStatusEnum doCancel(SagasContext context);

    /**
     * 流程会滚方法执行结果查询
     * @param context
     * @return
     */
    ProcessStatusEnum cancelQuery(SagasContext context);
}
