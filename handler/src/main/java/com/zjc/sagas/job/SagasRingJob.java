package com.zjc.sagas.job;

import com.zjc.sagas.enums.MulStatusEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SagasRingJob {
    @Autowired
    private SagasGetStatus4Job sagasGetStatus4Job;
    public void excute(){
        sagasGetStatus4Job.getSagasDateList(MulStatusEnum.RING);
    }
}
