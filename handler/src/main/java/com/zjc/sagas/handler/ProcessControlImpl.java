package com.zjc.sagas.handler;

import com.zjc.sagas.annotation.Sagas;
import com.zjc.sagas.enums.MulStatusEnum;
import com.zjc.sagas.interfaces.ProcessControl;
import com.zjc.sagas.interfaces.SagasProcessor;
import com.zjc.sagas.model.SagasDate;
import com.zjc.sagas.model.SagasOrder;
import com.zjc.sagas.service.SagasOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;

/**
 * create by zjc in 2018/11/29 0029
 */
@Service
public class ProcessControlImpl implements ProcessControl {
    @Autowired
    private SagasOrderService sagasOrderService;
    @Autowired
    private SagasSynDistributeImpl sagasSynDistribute;
    @Autowired
    private SagasAsynDistributeImpl sagasAsynDistribute;
    @Override
    public MulStatusEnum control(List<SagasDate> list, String orderNo, Integer type) {
        if (list == null || list.size() ==0 || StringUtils.isEmpty(type)) {
            throw new IllegalArgumentException("参数不能为空");
        }
        HashMap<String, Object> result = new HashMap<>();
        SagasOrder sagasOrder = sagasOrderService.selectByOrderNo(orderNo);
        Integer orderStatus = sagasOrder.getStatus();
        MulStatusEnum anEnum = MulStatusEnum.getByType(orderStatus);
        MulStatusEnum resultEnum = null;
        if (anEnum.equals(MulStatusEnum.INIT )|| anEnum.equals(MulStatusEnum.ING)){
            resultEnum = MulStatusEnum.SUC;
            for (int i =0;i < list.size(); i++) {
                Boolean isSend = (Boolean)result.get("isSend");
                MulStatusEnum status = (MulStatusEnum)result.get("status");
                if (status != null && status.getType() != orderStatus) {
                    sagasOrder.setType(status.getType());
                    resultEnum = status;
                    sagasOrderService.updateByOrderNoAndStatus(sagasOrder,anEnum.getType());
                    anEnum = status;

                }
                if (Boolean.FALSE.equals(isSend)) {
                    break;
                }else {
                    boolean annotion = this.getAnnotion(list.get(i));
                    if (annotion) {
                        sagasSynDistribute.distribute(orderNo,i,result);
                    }else {
                        sagasAsynDistribute.distribute(orderNo,i,result);
                    }
                }
            }
        }
        if (anEnum.equals(MulStatusEnum.ROLL) || anEnum.equals(MulStatusEnum.RING)) {
            resultEnum = MulStatusEnum.FAIL;
            for (int i = list.size();i> 0; i--) {
                Boolean isSend = (Boolean)result.get("isSend");
                MulStatusEnum status = (MulStatusEnum)result.get("status");
                if (status != null && status.getType() != anEnum.getType()) {

                    resultEnum = status;
                    sagasOrder.setStatus(status.getType());
                    sagasOrderService.updateByOrderNoAndStatus(sagasOrder,anEnum.getType());
                    anEnum = status;
                }
                if (Boolean.FALSE.equals(isSend)) {
                    break;
                }else {
                    boolean annotion = this.getAnnotion(list.get(i));
                    if (annotion) {
                        sagasSynDistribute.distribute(orderNo,i,result);
                    }else {
                        sagasAsynDistribute.distribute(orderNo,i,result);
                    }
                }
            }
        }
        sagasOrder.setStatus(resultEnum.getType());
        sagasOrderService.updateByOrderNoAndStatus(sagasOrder,anEnum.getType());
        return anEnum;
    }
    private boolean getAnnotion(SagasDate sagasDate) {
        Class<? extends SagasProcessor> aClass = sagasDate.getProcessor().getClass();
        Sagas annotation = aClass.getAnnotation(Sagas.class);
        if (annotation == null) {
            throw new IllegalArgumentException("过程异常,缺少注解");
        }
        return annotation.isOrder();
    }

}
