package com.zjc.sagas.handler;

import com.zjc.sagas.enums.MulStatusEnum;
import com.zjc.sagas.enums.ProcessStatusEnum;
import com.zjc.sagas.interfaces.ProcessorDistribute;
import com.zjc.sagas.model.SagasOrder;
import com.zjc.sagas.model.SagasProcessOrder;
import com.zjc.sagas.service.SagasOrderService;
import com.zjc.sagas.service.SagasProcessOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * create by zjc on 18/11/29
 */
@Service
public class SagasSynDistributeImpl implements ProcessorDistribute {
    @Autowired
    private SagasOrderService sagasOrderService;
    @Autowired
    private SagasProcessOrderService sagasProcessOrderService;
    @Autowired
    private SagasSynCommitHandler sagasSynCommitHandler;
    @Override
    public void distribute(String orderNo, Integer order, Map<String, Object> result) {
        SagasOrder sagasOrder = sagasOrderService.selectByOrderNo(orderNo);
        SagasProcessOrder sagasProcessOrder = sagasProcessOrderService.selectByOrderNoAndOrder(orderNo, order);
        String mothedName = sagasProcessOrder.getMothedName();
        boolean roll = "doCancel".equals(mothedName);
        ProcessStatusEnum statusEnum = ProcessStatusEnum.getByType(sagasProcessOrder.getStatus());
        if (sagasOrder.getStatus() == MulStatusEnum.INIT.getType() || sagasOrder.getStatus() == MulStatusEnum.ING.getType()) {
            if (roll) {
                if(statusEnum.equals(ProcessStatusEnum.INIT) || statusEnum.equals(ProcessStatusEnum.NOBEGIN)) {
                    sagasSynCommitHandler.rinitRollBack(orderNo,order,result);
                } else if (statusEnum.equals(ProcessStatusEnum.ING)) {
                    sagasSynCommitHandler.ringRollBack(orderNo,order,result);
                }else if (statusEnum.equals(ProcessStatusEnum.FAIL)) {
                    sagasSynCommitHandler.rfailRollBack(orderNo,order,result);
                } else {
                    return;
                }
            }else {
               if(statusEnum.equals(ProcessStatusEnum.INIT) || statusEnum.equals(ProcessStatusEnum.NOBEGIN)) {
                   sagasSynCommitHandler.initCommit(orderNo,order,result);
               } else if (statusEnum.equals(ProcessStatusEnum.ING)) {
                   sagasSynCommitHandler.ingCommit(orderNo,order,result);
               }else if (statusEnum.equals(ProcessStatusEnum.FAIL)) {
                   sagasSynCommitHandler.failRollBack(orderNo,order,result);
               } else {
                   return;
               }
            }
        }else if(sagasOrder.getStatus() == MulStatusEnum.ROLL.getType() || sagasOrder.getStatus() == MulStatusEnum.RING.getType()){
            if (roll) {
                if(statusEnum.equals(ProcessStatusEnum.INIT) || statusEnum.equals(ProcessStatusEnum.NOBEGIN)) {
                    sagasSynCommitHandler.rinitRollBack(orderNo,order,result);
                } else if (statusEnum.equals(ProcessStatusEnum.ING)) {
                    sagasSynCommitHandler.ringRollBack(orderNo,order,result);
                }else if (statusEnum.equals(ProcessStatusEnum.FAIL)) {
                    sagasSynCommitHandler.rfailRollBack(orderNo,order,result);
                } else {
                    return;
                }
            }else {
                if(statusEnum.equals(ProcessStatusEnum.INIT) || statusEnum.equals(ProcessStatusEnum.NOBEGIN)) {
                    sagasSynCommitHandler.failRollBack(orderNo,order,result);
                } else if (statusEnum.equals(ProcessStatusEnum.ING)) {
                    sagasSynCommitHandler.ingRollBack(orderNo,order,result);
                }else if (statusEnum.equals(ProcessStatusEnum.FAIL)) {
                    sagasSynCommitHandler.failRollBack(orderNo,order,result);
                } else {
                    sagasSynCommitHandler.sucRollBack(orderNo,order,result);
                }
            }
        }else {
            result.put("status",MulStatusEnum.getByType(sagasOrder.getType()));
            result.put("isSend",false);
        }
    }
}
