package com.zjc.sagas.job;

import com.zjc.sagas.enums.MulStatusEnum;
import com.zjc.sagas.model.SagasDate;
import com.zjc.sagas.service.SagasOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 服务重启时候执行的job
 * 处理服务中断流程
 */
@Service
public class SagasIngJob {
    @Autowired
   private SagasGetStatus4Job sagasGetStatus4Job;
    public void excute(){
        sagasGetStatus4Job.getSagasDateList(MulStatusEnum.ING);
    }
}
