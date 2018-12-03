package com.zjc.sagas.handler;

import com.zjc.sagas.enums.MulStatusEnum;
import com.zjc.sagas.enums.ProcessStatusEnum;
import com.zjc.sagas.interfaces.ProcessorCommit;
import com.zjc.sagas.interfaces.SagasProcessor;
import com.zjc.sagas.model.SagasContext;
import com.zjc.sagas.model.SagasDate;
import com.zjc.sagas.model.SagasOrder;
import com.zjc.sagas.model.SagasProcessOrder;
import com.zjc.sagas.service.SagasOrderService;
import com.zjc.sagas.service.SagasProcessOrderService;
import com.zjc.sagas.utils.ContextUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * create by zjc in 2018/11/28 0028
 */
@Service
public class SagasSynCommitHandler implements ProcessorCommit {
    @Autowired
    private SagasOrderService sagasOrderService;
    @Autowired
    private SagasProcessOrderService sagasProcessOrderService;
    @Autowired
    private AnnotationDistribute annotationDistribute;
    @Override
    public void initCommit(String orderNo, Integer order, Map<String, Object> result) {
        SagasOrder sagasOrder = sagasOrderService.selectByOrderNo(orderNo);
        if (sagasOrder.getStatus() != MulStatusEnum.INIT.getType() && sagasOrder.getStatus() != MulStatusEnum.ING.getType()) {
            throw new IllegalArgumentException("订单状态不合法");
        }
        SagasProcessOrder sagasProcessOrder = sagasProcessOrderService.selectByOrderNoAndOrder(orderNo, order);
        Integer status = sagasProcessOrder.getStatus();
        if (status != ProcessStatusEnum.INIT.getType() && status != ProcessStatusEnum.NOBEGIN.getType() || !sagasProcessOrder.getMothedName().equals("doCommit")) {
            throw new IllegalArgumentException("订单状态不合法");
        }
        SagasDate sagasDate = ContextUtils.get(orderNo, order);
        sagasProcessOrder.setStatus(ProcessStatusEnum.ING.getType());
        int j = sagasProcessOrderService.updateByProcessNoAndStatus(sagasProcessOrder,status );
        if(j == 0) {
            return;
        }

        ProcessStatusEnum processStatusEnum = annotationDistribute.handler(sagasDate);
        sagasProcessOrder.setStatus(processStatusEnum.getType());
//        如果并发了，则以先到的为准
        int i = sagasProcessOrderService.updateByProcessNoAndStatus(sagasProcessOrder, ProcessStatusEnum.ING.getType());
        if (i==1) {
            if (processStatusEnum.equals(ProcessStatusEnum.SUC)) {
//                result.put("isSend", true);
                result.put(sagasProcessOrder.getOrder().toString(),MulStatusEnum.SUC);
            } else if (processStatusEnum.equals(ProcessStatusEnum.ING)) {
                result.put("isSend", false);
                result.put(sagasProcessOrder.getOrder().toString(), MulStatusEnum.ING);
            } else if (ProcessStatusEnum.FAIL.equals(processStatusEnum)) {
                result.put("isSend", false);
                result.put(sagasProcessOrder.getOrder().toString(), MulStatusEnum.ROLL);
                this.failRollBack(orderNo,order,result);
            } else {
                this.initCommit(orderNo,order,result);
            }
        }
    }

    @Override
    public void ingCommit(String orderNo, Integer order, Map<String, Object> result) {
        SagasOrder sagasOrder = sagasOrderService.selectByOrderNo(orderNo);
        if (sagasOrder.getStatus() != MulStatusEnum.INIT.getType() && sagasOrder.getStatus() != MulStatusEnum.ING.getType()) {
            throw new IllegalArgumentException("订单状态不合法");
        }
        SagasProcessOrder sagasProcessOrder = sagasProcessOrderService.selectByOrderNoAndOrder(orderNo, order);
        Integer status = sagasProcessOrder.getStatus();
        if (!sagasProcessOrder.getMothedName().equals("doCommit") || status != ProcessStatusEnum.ING.getType()) {
            throw new IllegalArgumentException("订单状态不合法");
        }
        SagasDate sagasDate = ContextUtils.get(orderNo, order);
        ProcessStatusEnum processStatusEnum = annotationDistribute.handlerQuery(sagasDate);
        sagasProcessOrder.setStatus(processStatusEnum.getType());
        int i = sagasProcessOrderService.updateByProcessNoAndStatus(sagasProcessOrder, status);
//        并发先到为准
        if ( i == 1) {
            if (processStatusEnum.equals(ProcessStatusEnum.SUC)) {
//                result.put("isSend", true);
                result.put(sagasProcessOrder.getOrder().toString(),MulStatusEnum.SUC);
            } else if (processStatusEnum.equals(ProcessStatusEnum.ING)) {
                result.put("isSend", false);
                result.put(sagasProcessOrder.getOrder().toString(), MulStatusEnum.ING);
            } else if (ProcessStatusEnum.FAIL.equals(processStatusEnum)) {
                result.put("isSend", false);
                result.put(sagasProcessOrder.getOrder().toString(), MulStatusEnum.ROLL);
                this.failRollBack(orderNo,order,result);
            } else if(processStatusEnum.equals(ProcessStatusEnum.NOBEGIN) || ProcessStatusEnum.INIT.equals(processStatusEnum)){
                this.initCommit(orderNo,order,result);
            }else {
                throw new IllegalArgumentException("订单状态异常");
            }
        }
    }

    @Override
    public void failRollBack(String orderNo, Integer order, Map<String, Object> result) {
        SagasOrder sagasOrder = sagasOrderService.selectByOrderNo(orderNo);
        if (sagasOrder.getStatus() != MulStatusEnum.INIT.getType() && sagasOrder.getStatus() != MulStatusEnum.ING.getType()
                && sagasOrder.getStatus() != MulStatusEnum.RING.getType() && sagasOrder.getStatus() != MulStatusEnum.ROLL.getType()) {
            throw new IllegalArgumentException("订单状态不合法");
        }
        SagasProcessOrder sagasProcessOrder = sagasProcessOrderService.selectByOrderNoAndOrder(orderNo, order);
        Integer status = sagasProcessOrder.getStatus();
        if (!sagasProcessOrder.getMothedName().equals("doCommit") || status != ProcessStatusEnum.FAIL.getType()) {
            throw new IllegalArgumentException("订单状态不合法");
        }
        sagasProcessOrder.setStatus(ProcessStatusEnum.SUC.getType());
        sagasProcessOrder.setMothedName("doCancel");
        int i = sagasProcessOrderService.updateByProcessNoAndStatus(sagasProcessOrder, status);
        if (i == 1) {
            result.put(sagasProcessOrder.getOrder().toString(),MulStatusEnum.ROLL);
        }
    }

    @Override
    public void sucRollBack(String orderNo, Integer order, Map<String, Object> result) {
        SagasOrder sagasOrder = sagasOrderService.selectByOrderNo(orderNo);
        if (sagasOrder.getStatus() != MulStatusEnum.RING.getType() && sagasOrder.getStatus() != MulStatusEnum.ROLL.getType()) {
            throw new IllegalArgumentException("订单状态不合法");
        }
        SagasProcessOrder sagasProcessOrder = sagasProcessOrderService.selectByOrderNoAndOrder(orderNo, order);
        Integer status = sagasProcessOrder.getStatus();
        if (sagasProcessOrder.getMothedName().equals("doCancel") || status != ProcessStatusEnum.SUC.getType()) {
            throw new IllegalArgumentException("订单状态不合法");
        }
        SagasDate sagasDate = ContextUtils.get(orderNo, order);
        ProcessStatusEnum processStatusEnum = annotationDistribute.rollbackHandler(sagasDate);
        sagasProcessOrder.setStatus(processStatusEnum.getType());
        sagasProcessOrder.setMothedName("doCancel");
        int i = sagasProcessOrderService.updateByProcessNoAndStatus(sagasProcessOrder, status);
        if (i == 1) {
            if (processStatusEnum.equals(ProcessStatusEnum.SUC)) {
                result.put(sagasProcessOrder.getOrder().toString(),MulStatusEnum.ROLL);
            }else if(processStatusEnum.equals(ProcessStatusEnum.ING)){
                result.put(sagasProcessOrder.getOrder().toString(),MulStatusEnum.RING);
                result.put("isSend",false);
            }else if(processStatusEnum.equals(ProcessStatusEnum.FAIL)){
                result.put(sagasProcessOrder.getOrder().toString(),MulStatusEnum.RFAIL);
                result.put("isSend",false);
                this.rfailRollBack(orderNo,order,result);
            }else {
                this.rinitRollBack(orderNo,order,result);
            }
        }
    }

    /**
     * 理论上这个状态不应该存在 因为这是同步的实现，为防止万一还是保留实现，不建议调用
     * @param orderNo
     * @param order
     * @param result
     */
    @Override
    public void ingRollBack(String orderNo, Integer order, Map<String, Object> result) {
        SagasOrder sagasOrder = sagasOrderService.selectByOrderNo(orderNo);
        if (sagasOrder.getStatus() != MulStatusEnum.RING.getType() && sagasOrder.getStatus() != MulStatusEnum.ROLL.getType()) {
            throw new IllegalArgumentException("订单状态不合法");
        }
        SagasProcessOrder sagasProcessOrder = sagasProcessOrderService.selectByOrderNoAndOrder(orderNo, order);
        Integer status = sagasProcessOrder.getStatus();
        if (sagasProcessOrder.getMothedName().equals("doCancel") || status != ProcessStatusEnum.ING.getType()) {
            throw new IllegalArgumentException("订单状态不合法");
        }
        SagasDate sagasDate = ContextUtils.get(orderNo, order);
        ProcessStatusEnum processStatusEnum = annotationDistribute.handlerQuery(sagasDate);
        sagasProcessOrder.setStatus(processStatusEnum.getType());
        sagasProcessOrderService.updateByProcessNoAndStatus(sagasProcessOrder,status);
        if (processStatusEnum.equals(ProcessStatusEnum.SUC)) {
            this.sucRollBack(orderNo,order,result);
        }else if(processStatusEnum.equals(ProcessStatusEnum.FAIL)){
            this.failRollBack(orderNo,order,result);
        }else if(processStatusEnum.equals(ProcessStatusEnum.NOBEGIN) || ProcessStatusEnum.INIT.equals(processStatusEnum)) {
            this.failRollBack(orderNo,order,result);
        }else if(processStatusEnum.equals(ProcessStatusEnum.ING)) {
            result.put("isSend",false);
            result.put(sagasProcessOrder.getOrder().toString(),MulStatusEnum.RING);
        }else {
            throw new IllegalArgumentException("订单状态异常");
        }
    }

    @Override
    public void ringRollBack(String orderNo, Integer order, Map<String, Object> result) {
        SagasOrder sagasOrder = sagasOrderService.selectByOrderNo(orderNo);
        if (sagasOrder.getStatus() != MulStatusEnum.RING.getType() && sagasOrder.getStatus() != MulStatusEnum.ROLL.getType()) {
            throw new IllegalArgumentException("订单状态不合法");
        }
        SagasProcessOrder sagasProcessOrder = sagasProcessOrderService.selectByOrderNoAndOrder(orderNo, order);
        Integer status = sagasProcessOrder.getStatus();
        if (!sagasProcessOrder.getMothedName().equals("doCancel") || status != ProcessStatusEnum.ING.getType()) {
            throw new IllegalArgumentException("订单状态不合法");
        }
        SagasDate sagasDate = ContextUtils.get(orderNo, order);
        ProcessStatusEnum processStatusEnum = annotationDistribute.rollbackQuery(sagasDate);
        sagasProcessOrder.setStatus(processStatusEnum.getType());
        int i = sagasProcessOrderService.updateByProcessNoAndStatus(sagasProcessOrder, status);
        if (i == 1){
            if (processStatusEnum.equals(ProcessStatusEnum.SUC)) {
//                result.put("isSend",true);
                result.put(sagasProcessOrder.getOrder().toString(),MulStatusEnum.ROLL);
            }else if (processStatusEnum.equals(ProcessStatusEnum.FAIL)) {
                result.put("isSend",false);
                result.put(sagasProcessOrder.getOrder().toString(),MulStatusEnum.RFAIL);
                this.rfailRollBack(orderNo,order,result);
            }else if(processStatusEnum.equals(ProcessStatusEnum.ING)){
                result.put("isSend",false);
                result.put(sagasProcessOrder.getOrder().toString(),MulStatusEnum.RING);
            }else if(processStatusEnum.equals(ProcessStatusEnum.NOBEGIN) || ProcessStatusEnum.INIT.equals(processStatusEnum)) {
                this.sucRollBack(orderNo,order,result);
            }
        }
    }

    @Override
    public void rinitRollBack(String orderNo, Integer order, Map<String, Object> result) {
        SagasOrder sagasOrder = sagasOrderService.selectByOrderNo(orderNo);
        if (sagasOrder.getStatus() != MulStatusEnum.RING.getType() && sagasOrder.getStatus() != MulStatusEnum.ROLL.getType()) {
            throw new IllegalArgumentException("订单状态不合法");
        }
        SagasProcessOrder sagasProcessOrder = sagasProcessOrderService.selectByOrderNoAndOrder(orderNo, order);
        Integer status = sagasProcessOrder.getStatus();
        if (!sagasProcessOrder.getMothedName().equals("doCancel") || status != ProcessStatusEnum.INIT.getType() && status != ProcessStatusEnum.NOBEGIN.getType()) {
            throw new IllegalArgumentException("订单状态不合法");
        }
        SagasDate sagasDate = ContextUtils.get(orderNo,order);
        ProcessStatusEnum processStatusEnum = annotationDistribute.rollbackHandler(sagasDate);
        sagasProcessOrder.setStatus(processStatusEnum.getType());
        int i = sagasProcessOrderService.updateByProcessNoAndStatus(sagasProcessOrder, status);
        if (i == 1) {
            if (processStatusEnum.equals(ProcessStatusEnum.SUC)) {
                result.put(sagasProcessOrder.getOrder().toString(),MulStatusEnum.ROLL);
            }else if(processStatusEnum.equals(ProcessStatusEnum.ING)){
                result.put(sagasProcessOrder.getOrder().toString(),MulStatusEnum.RING);
                result.put("isSend",false);
            }else if(processStatusEnum.equals(ProcessStatusEnum.FAIL)){
                result.put(sagasProcessOrder.getOrder().toString(),MulStatusEnum.RFAIL);
                result.put("isSend",false);
                this.rfailRollBack(orderNo,order,result);
            }else {
                this.rinitRollBack(orderNo,order,result);
            }
        }
    }

    @Override
    public void rfailRollBack(String orderNo, Integer order, Map<String, Object> result) {
        SagasOrder sagasOrder = sagasOrderService.selectByOrderNo(orderNo);
        if (sagasOrder.getStatus() != MulStatusEnum.RING.getType() && sagasOrder.getStatus() != MulStatusEnum.ROLL.getType()
        && sagasOrder.getStatus() != MulStatusEnum.RFAIL.getType()) {
            throw new IllegalArgumentException("订单状态不合法");
        }
        SagasProcessOrder sagasProcessOrder = sagasProcessOrderService.selectByOrderNoAndOrder(orderNo, order);
        Integer status = sagasProcessOrder.getStatus();
        if (!sagasProcessOrder.getMothedName().equals("doCancel") || status != ProcessStatusEnum.FAIL.getType()) {
            throw new IllegalArgumentException("订单状态不合法");
        }
        result.put(sagasProcessOrder.getOrder().toString(),MulStatusEnum.RFAIL);
        result.put("isSend",false);
    }
}
