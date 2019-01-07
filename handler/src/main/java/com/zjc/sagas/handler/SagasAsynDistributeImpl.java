package com.zjc.sagas.handler;

import com.zjc.sagas.dao.SagasBusinessLockDao;
import com.zjc.sagas.enums.MulStatusEnum;
import com.zjc.sagas.enums.ProcessStatusEnum;
import com.zjc.sagas.interfaces.ProcessorDistribute;
import com.zjc.sagas.model.SagasBusinessLock;
import com.zjc.sagas.model.SagasOrder;
import com.zjc.sagas.model.SagasProcessOrder;
import com.zjc.sagas.service.SagasOrderService;
import com.zjc.sagas.service.SagasProcessOrderService;
import com.zjc.sagas.utils.ContextUtils;
import com.zjc.sagas.utils.ThreadPoolUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * create by zjc on 18/11/29
 */
@Service
public class SagasAsynDistributeImpl implements ProcessorDistribute {
    @Autowired
    private SagasOrderService sagasOrderService;
    @Autowired
    private SagasProcessOrderService sagasProcessOrderService;
    @Autowired
    private SagasAsynCommitHandler sagasAsynCommitHandler;
    @Autowired
    private SagasBusinessLockDao sagasBusinessLockDao;
    @Override
    public void distribute(String orderNo, Integer order, Map<String, Object> result,Integer type) {
        ThreadPoolUtils.getInstance().execute(new Runnable() {
            @Override
            public void run() {
                try {
                    asynistribute(orderNo, order, result, type);
                }catch (Exception e) {
                    throw new RuntimeException(e);
                }
                finally {
                    SagasBusinessLock lock = sagasBusinessLockDao.selectByOrderNo(orderNo);
                    SagasBusinessLock businessLock = sagasBusinessLockDao.selectByIdForUpdate(lock.getId());
                    businessLock.setThread(businessLock.getThread() - 1);
                    sagasBusinessLockDao.updateByOrderNo(businessLock);
                    if (businessLock.getThread() == 0) {
                        ContextUtils.delete(orderNo,type);
                    }
                }

            }
        });
    }
    private void asynistribute(String orderNo, Integer order, Map<String, Object> result,Integer type){
        SagasOrder sagasOrder = sagasOrderService.selectByOrderNo(orderNo);
        SagasProcessOrder sagasProcessOrder = sagasProcessOrderService.selectByOrderNoAndOrder(orderNo, order);
        String mothedName = sagasProcessOrder.getMothedName();
        boolean roll = "doCancel".equals(mothedName);
        ProcessStatusEnum statusEnum = ProcessStatusEnum.getByType(sagasProcessOrder.getStatus());
        if (sagasOrder.getStatus() == MulStatusEnum.INIT.getType() || sagasOrder.getStatus() == MulStatusEnum.ING.getType()) {
            if (roll) {
                if(statusEnum.equals(ProcessStatusEnum.INIT) || statusEnum.equals(ProcessStatusEnum.NOBEGIN)) {
                    sagasAsynCommitHandler.rinitRollBack(orderNo,order,result,type);
                } else if (statusEnum.equals(ProcessStatusEnum.ING)) {
                    sagasAsynCommitHandler.ringRollBack(orderNo,order,result,type);
                }else if (statusEnum.equals(ProcessStatusEnum.FAIL)) {
                    sagasAsynCommitHandler.rfailRollBack(orderNo,order,result,type);
                } else {
                    return;
                }
            }else {
                if(statusEnum.equals(ProcessStatusEnum.INIT) || statusEnum.equals(ProcessStatusEnum.NOBEGIN)) {
                    sagasAsynCommitHandler.initCommit(orderNo,order,result,type);
                } else if (statusEnum.equals(ProcessStatusEnum.ING)) {
                    sagasAsynCommitHandler.ingCommit(orderNo,order,result,type);
                }else if (statusEnum.equals(ProcessStatusEnum.FAIL)) {
                    sagasAsynCommitHandler.failRollBack(orderNo,order,result,type);
                } else {
                    return;
                }
            }
        }else if(sagasOrder.getStatus() == MulStatusEnum.ROLL.getType() || sagasOrder.getStatus() == MulStatusEnum.RING.getType()){
            if (roll) {
                if(statusEnum.equals(ProcessStatusEnum.INIT) || statusEnum.equals(ProcessStatusEnum.NOBEGIN)) {
                    sagasAsynCommitHandler.rinitRollBack(orderNo,order,result,type);
                } else if (statusEnum.equals(ProcessStatusEnum.ING)) {
                    sagasAsynCommitHandler.ringRollBack(orderNo,order,result,type);
                }else if (statusEnum.equals(ProcessStatusEnum.FAIL)) {
                    sagasAsynCommitHandler.rfailRollBack(orderNo,order,result,type);
                } else {
                    return;
                }
            }else {
                if(statusEnum.equals(ProcessStatusEnum.INIT) || statusEnum.equals(ProcessStatusEnum.NOBEGIN)) {
                    sagasAsynCommitHandler.failRollBack(orderNo,order,result,type);
                } else if (statusEnum.equals(ProcessStatusEnum.ING)) {
                    sagasAsynCommitHandler.ingRollBack(orderNo,order,result,type);
                }else if (statusEnum.equals(ProcessStatusEnum.FAIL)) {
                    sagasAsynCommitHandler.failRollBack(orderNo,order,result,type);
                } else {
                    sagasAsynCommitHandler.sucRollBack(orderNo,order,result,type);
                }
            }
        }else {
            result.put("status",MulStatusEnum.getByType(sagasOrder.getType()));
            result.put("isSend",false);
        }
    }
}
