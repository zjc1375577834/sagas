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

import java.util.Map;

/**
 * create by zjc in 2018/11/28 0028
 */
public class SagasDirectCommitHandler implements ProcessorCommit {
    @Autowired
    private SagasOrderService sagasOrderService;
    @Autowired
    private SagasProcessOrderService sagasProcessOrderService;
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
        SagasProcessor processor = sagasDate.getProcessor();
        SagasContext context = sagasDate.getContext();
        ProcessStatusEnum processStatusEnum = processor.doCommit(context);
        sagasProcessOrder.setStatus(processStatusEnum.getType());
//        如果并发了，则以先到的为准
        int i = sagasProcessOrderService.updateByProcessNoAndStatus(sagasProcessOrder, status);
        if (i==1) {
            if (processStatusEnum.equals(ProcessStatusEnum.SUC)) {
                result.put("isSend", true);
            } else if (processStatusEnum.equals(ProcessStatusEnum.ING)) {
                result.put("isSend", false);
                result.put("status", MulStatusEnum.ING);
            } else if (ProcessStatusEnum.FAIL.equals(processStatusEnum)) {
                result.put("isSend", false);
                result.put("status", MulStatusEnum.ROLL);
                this.failRollBack(orderNo,order,result);
            } else {
                throw new IllegalArgumentException("订单状态异常");
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
        SagasProcessor processor = sagasDate.getProcessor();
        SagasContext context = sagasDate.getContext();
        ProcessStatusEnum processStatusEnum = processor.commitQuery(context);
        sagasProcessOrder.setStatus(processStatusEnum.getType());
        int i = sagasProcessOrderService.updateByProcessNoAndStatus(sagasProcessOrder, status);
//        并发先到为准
        if ( i == 1) {
            if (processStatusEnum.equals(ProcessStatusEnum.SUC)) {
                result.put("isSend", true);
            } else if (processStatusEnum.equals(ProcessStatusEnum.ING)) {
                result.put("isSend", false);
                result.put("status", MulStatusEnum.ING);
            } else if (ProcessStatusEnum.FAIL.equals(processStatusEnum)) {
                result.put("isSend", false);
                result.put("status", MulStatusEnum.ROLL);
                this.failRollBack(orderNo,order,result);
            } else if(processStatusEnum.equals(ProcessStatusEnum.NOBEGIN)){
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
            result.put("status",MulStatusEnum.ROLL);
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
        SagasProcessor processor = sagasDate.getProcessor();
        SagasContext context = sagasDate.getContext();
        ProcessStatusEnum processStatusEnum = processor.doCancel(context);
        sagasProcessOrder.setStatus(processStatusEnum.getType());
        sagasProcessOrder.setMothedName("doCancel");
        int i = sagasProcessOrderService.updateByProcessNoAndStatus(sagasProcessOrder, status);
        if (i == 1) {
            if (processStatusEnum.equals(ProcessStatusEnum.SUC)) {
                result.put("status",MulStatusEnum.ROLL);
            }else if(processStatusEnum.equals(ProcessStatusEnum.ING)){
                result.put("status",MulStatusEnum.RING);
                result.put("isSend",false);
            }else if(processStatusEnum.equals(ProcessStatusEnum.FAIL)){
                result.put("status",MulStatusEnum.RFAIL);
                result.put("isSend",false);
                this.rfailRollBack(orderNo,order,result);
            }else {
                throw new IllegalArgumentException("订单状态异常");
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
        SagasProcessor processor = sagasDate.getProcessor();
        SagasContext context = sagasDate.getContext();
        ProcessStatusEnum processStatusEnum = processor.commitQuery(context);
        sagasProcessOrder.setStatus(processStatusEnum.getType());
        sagasProcessOrderService.updateByProcessNoAndStatus(sagasProcessOrder,status);
        if (processStatusEnum.equals(ProcessStatusEnum.SUC)) {
            this.sucRollBack(orderNo,order,result);
        }else if(processStatusEnum.equals(ProcessStatusEnum.FAIL)){
            this.failRollBack(orderNo,order,result);
        }else if(processStatusEnum.equals(ProcessStatusEnum.NOBEGIN)) {
            this.failRollBack(orderNo,order,result);
        }else if(processStatusEnum.equals(ProcessStatusEnum.ING)) {
            result.put("isSend",false);
            result.put("status",MulStatusEnum.RING);
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
        SagasProcessor processor = sagasDate.getProcessor();
        SagasContext context = sagasDate.getContext();
        ProcessStatusEnum processStatusEnum = processor.cancelQuery(context);
        sagasProcessOrder.setStatus(processStatusEnum.getType());
        int i = sagasProcessOrderService.updateByProcessNoAndStatus(sagasProcessOrder, status);
        if (i == 1){
            if (processStatusEnum.equals(ProcessStatusEnum.SUC)) {
                result.put("isSend",true);
                result.put("status",MulStatusEnum.ROLL);
            }else if (processStatusEnum.equals(ProcessStatusEnum.FAIL)) {
                result.put("isSend",false);
                result.put("status",MulStatusEnum.RFAIL);
                this.rfailRollBack(orderNo,order,result);
            }else if(processStatusEnum.equals(ProcessStatusEnum.ING)){
                result.put("isSend",false);
                result.put("status",MulStatusEnum.RING);
            }else if(processStatusEnum.equals(ProcessStatusEnum.NOBEGIN)) {
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
        if (!sagasProcessOrder.getMothedName().equals("doCancel") || status != ProcessStatusEnum.INIT.getType()) {
            throw new IllegalArgumentException("订单状态不合法");
        }
        sagasProcessOrder.setStatus(ProcessStatusEnum.SUC.getType());
        sagasProcessOrder.setMothedName("doCancel");
        sagasProcessOrderService.updateByProcessNoAndStatus(sagasProcessOrder,status);
        result.put("status",MulStatusEnum.ROLL);
        result.put("isSend",true);
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
        result.put("status",MulStatusEnum.RFAIL);
        result.put("isSend",false);
    }
}
