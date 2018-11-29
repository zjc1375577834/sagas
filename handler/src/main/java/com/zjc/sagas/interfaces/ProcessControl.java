package com.zjc.sagas.interfaces;

import com.zjc.sagas.enums.MulStatusEnum;
import com.zjc.sagas.model.SagasDate;

import java.util.List;

/**
 * create by zjc in 2018/11/29 0029
 */
public interface ProcessControl {
    MulStatusEnum control(List<SagasDate> list, String orderNo, Integer type);
}
