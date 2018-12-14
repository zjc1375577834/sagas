package com.zjc.sagas.job;

import com.zjc.sagas.enums.MulStatusEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 这个是在服务重启的时候需要执行的job
 * 处理中断流程
 */
@Service
public class SagasRollJob {
    @Autowired
    private SagasGetStatus4Job sagasGetStatus4Job;
    public void excute(){
        sagasGetStatus4Job.getSagasDateList(MulStatusEnum.ROLL);
    }
}
