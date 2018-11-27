package com.zjc.sagas.handler;

import com.zjc.sagas.annotation.Sagas;
import com.zjc.sagas.enums.MulStatusEnum;
import com.zjc.sagas.enums.ProcessStatusEnum;
import com.zjc.sagas.interfaces.AnnotationHandler;
import com.zjc.sagas.interfaces.SagasProcessor;
import com.zjc.sagas.model.SagasContext;
import com.zjc.sagas.model.SagasDate;
import com.zjc.sagas.model.SagasOrder;
import com.zjc.sagas.model.SagasProcessOrder;
import com.zjc.sagas.query.SagasProcessOrderQuery;
import com.zjc.sagas.query.SortColumn;
import com.zjc.sagas.query.SortMode;
import com.zjc.sagas.service.SagasOrderService;
import com.zjc.sagas.service.SagasProcessOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * create by zjc on 18/11/27
 */
@Service
public class SagasExceptionHandler {
    Map<Integer, AnnotationHandler> map = SagasHandler.map;
    @Autowired
    private SagasOrderService sagasOrderService;
    @Autowired
    private SagasProcessOrderService sagasProcessOrderService;
    @Autowired
    private SagasHandler sagasHandler;
    private ThreadPoolExecutor executor ;
    /**
     * 这种状态是由于服务器中断这时候所有process落库只要执行发送就行 可能存在部分成功
     * @param list
     * @param type
     * @return
     */
    MulStatusEnum initHandler(List<SagasDate> list,String orderNo,Integer type){
        HashMap<String, Object> result = new HashMap<>();
        Boolean isSend = true;
        MulStatusEnum mulStatusEnum = MulStatusEnum.SUC;
        AnnotationHandler handler = map.get(type);

        if (handler == null) {
            handler = new SagasDefaultHandler();
        }
        SagasOrder sagasOrder = sagasOrderService.selectByOrderNo(orderNo);
        if (sagasOrder == null) {
            throw new IllegalArgumentException("订单不存在");
        }
        if (sagasOrder.getStatus() != MulStatusEnum.INIT.getType()){
            throw new IllegalArgumentException("订单状态不对");
        }
        this.checkProcess(orderNo);
        int temp = 0;
        for(SagasDate sagasDate : list) {
            temp++;
            Sagas annotation = sagasDate.getClass().getAnnotation(Sagas.class);
            if (annotation == null) {
                throw new IllegalArgumentException("PROCESS异常");
            }
            SagasProcessOrder processOrder = this.getProcessOrder(orderNo, temp);
            this.getMapResult(result,isSend,mulStatusEnum);
            sagasHandler.processHandler(result, isSend,annotation.isOrder(),processOrder,handler,sagasDate);
        }
        SagasOrder order = new SagasOrder();
        order.setOrderNo(orderNo);
        order.setStatus(mulStatusEnum.getType());
        sagasOrderService.updateByOrderNo(order);
        return mulStatusEnum;
    }
    MulStatusEnum ingHandler(List<SagasDate> list,String orderNo,Integer type){
        HashMap<String, Object> result = new HashMap<>();
        Boolean isSend = true;
        MulStatusEnum mulStatusEnum = MulStatusEnum.SUC;
        AnnotationHandler handler = map.get(type);
        if (handler == null) {
            handler = new SagasDefaultHandler();
        }
        boolean checkProcess = this.checkProcess(orderNo);
        SagasOrder order = new SagasOrder();
        order.setOrderNo(orderNo);
        if (checkProcess == false) {
            order.setStatus(MulStatusEnum.RING.getType());
            sagasOrderService.updateByOrderNo(order);
            this.failHandler(list,orderNo,type);
        }
        List<SagasProcessOrder> processOrders = this.getProcessOrders(orderNo,SortMode.ASC);
        for(int i = 0 ; i < processOrders.size(); i++) {
            SagasProcessOrder processOrder = processOrders.get(i);
            if (processOrder.getStatus().equals(ProcessStatusEnum.ING.getType())) {
                SagasDate sagasDate = list.get(i);
                SagasProcessor processor = sagasDate.getProcessor();
                SagasContext context = sagasDate.getContext();
                ProcessStatusEnum query = processor.CommitQuery(context);
                processOrder.setStatus(query.getType());
                sagasProcessOrderService.updateById(processOrder);
                if (query.equals(ProcessStatusEnum.ING)){
                    return MulStatusEnum.ING;
                }else if(query.equals(ProcessStatusEnum.SUC) || query.equals(ProcessStatusEnum.NOBEGIN)) {

                    for(int j = 0; j < list.size(); j++) {
                        Sagas annotation = sagasDate.getClass().getAnnotation(Sagas.class);
                        if (annotation == null) {
                            throw new IllegalArgumentException("PROCESS异常");
                        }
                        this.getMapResult(result,isSend,mulStatusEnum);
                        sagasHandler.processHandler(result,isSend,annotation.isOrder(),this.getProcessOrder(orderNo,j),handler,list.get(j));
                    }
                }else if(query.equals(ProcessStatusEnum.FAIL)) {
                    mulStatusEnum = MulStatusEnum.RING;
                    MulStatusEnum mulStatusEnum1 = this.failHandler(list, orderNo, type);
                }
            }
        }
        SagasOrder sagasOrder = new SagasOrder();
        sagasOrder.setStatus(mulStatusEnum.getType());
        sagasOrder.setOrderNo(orderNo);
        sagasOrderService.updateByOrderNo(sagasOrder);

        return  mulStatusEnum;
    }
    MulStatusEnum failHandler(List<SagasDate> list,String orderNo,Integer type){
        SagasOrder sagasOrder = this.getSagasOrder(orderNo);
        if (sagasOrder.getStatus() <MulStatusEnum.RING.getType()){
            sagasOrder.setStatus(MulStatusEnum.RING.getType());
            sagasOrderService.updateByOrderNo(sagasOrder);
        }else if(sagasOrder.getStatus() > MulStatusEnum.RING.getType()){
            throw new IllegalArgumentException("订单状态异常");
        }
        AnnotationHandler handler = map.get(type);
        if (handler == null) {
            handler = new SagasDefaultHandler();
        }
        HashMap<String, Object> result = new HashMap<>();
        Boolean isSend = true;
        MulStatusEnum mulStatusEnum = MulStatusEnum.FAIL;
        for(int i = list.size() ;i > 0;i--){
            SagasDate sagasDate = list.get(i);
            this.getMapResult(result,isSend,mulStatusEnum);
            Sagas annotation = sagasDate.getClass().getAnnotation(Sagas.class);
            if (annotation == null) {
                throw new IllegalArgumentException("PROCESS异常");
            }
            this.getRollbackMapResult(result,isSend,mulStatusEnum);
            this.ringProcessHandler(result,isSend,annotation.isOrder(),this.getProcessOrder(orderNo,i),handler,sagasDate);
        }
        sagasOrder.setStatus(mulStatusEnum.getType());
        sagasOrderService.updateByOrderNo(sagasOrder);
        return mulStatusEnum;
    }
    MulStatusEnum ringHandler(List<SagasDate> list,String orderNo,Integer type){
        HashMap<String, Object> result = new HashMap<>();
        Boolean isSend = true;
        MulStatusEnum mulStatusEnum = MulStatusEnum.FAIL;
        AnnotationHandler handler = map.get(type);
        if (handler == null) {
            handler = new SagasDefaultHandler();
        }
        SagasOrder sagasOrder = this.getSagasOrder(orderNo);
        if (sagasOrder.getStatus() != MulStatusEnum.RING.getType()) {
            throw new IllegalArgumentException("订单状态异常");
        }
        List<SagasProcessOrder> processOrders = this.getProcessOrders(orderNo, SortMode.DESC);
        for (int i = 0; i < processOrders.size(); i++ ) {
            SagasDate sagasDate = list.get(i);
            SagasProcessOrder processOrder = processOrders.get(i);
            SagasProcessor processor = sagasDate.getProcessor();
            SagasContext context = sagasDate.getContext();
            Sagas annotation = sagasDate.getClass().getAnnotation(Sagas.class);
            this.getRollbackMapResult(result,isSend,mulStatusEnum);
            if (annotation == null) {
                throw new IllegalArgumentException("PROCESS异常");
            }
            if (processOrder.getStatus() == ProcessStatusEnum.ING.getType()) {
                ProcessStatusEnum processStatusEnum = sagasDate.getProcessor().CommitQuery(sagasDate.getContext());
                processOrder.setStatus(processStatusEnum.getType());
                sagasProcessOrderService.updateById(processOrder);
                if (processStatusEnum.equals(ProcessStatusEnum.SUC)) {
                    return this.ringHandler(list,orderNo,type);
                }else if(processStatusEnum.equals(ProcessStatusEnum.FAIL) || processStatusEnum.equals(ProcessStatusEnum.NOBEGIN) || processStatusEnum.equals(ProcessStatusEnum.INIT)){
                    return this.ringHandler(list,orderNo,type);
                }else if(processStatusEnum.equals(ProcessStatusEnum.RING)){
                    return MulStatusEnum.RING;
                }else {
                    throw new IllegalArgumentException("订单状态异常");
                }

            }else if(processOrder.getStatus() == ProcessStatusEnum.RFAIL.getType()) {
                sagasOrder.setStatus(MulStatusEnum.RFAIL.getType());
                sagasOrderService.updateByOrderNo(sagasOrder);
                return MulStatusEnum.RFAIL;
            }else if(processOrder.getStatus() == ProcessStatusEnum.RING.getType()) {
                ProcessStatusEnum processStatusEnum = processor.CancelQuery(context);
                processOrder.setStatus(processStatusEnum.getType());
                sagasProcessOrderService.updateById(processOrder);
                if (processStatusEnum.equals(ProcessStatusEnum.RING)) {
                    return MulStatusEnum.RING;
                }else if(processStatusEnum.equals(ProcessStatusEnum.RSUC)) {
                    this.ringProcessHandler(result,isSend,annotation.isOrder(),processOrder,handler,sagasDate);
                }else if(processStatusEnum.equals(ProcessStatusEnum.RFAIL)){
                    sagasOrder.setStatus(MulStatusEnum.RFAIL.getType());
                    sagasOrderService.updateByOrderNo(sagasOrder);
                    return MulStatusEnum.RFAIL;
                }else if(processStatusEnum.equals(ProcessStatusEnum.NOBEGIN)){
                    ProcessStatusEnum anEnum = processor.CancelQuery(context);
                    return this.ringHandler(list,orderNo,type);
                }
            } else if (processOrder.getStatus() == ProcessStatusEnum.FAIL.getType()) {
                this.ringProcessHandler(result,isSend,annotation.isOrder(),processOrder,handler,sagasDate);
            }

        }
        return mulStatusEnum;
    }
    private boolean checkProcess(String orderNo) {
        SagasProcessOrderQuery query = new SagasProcessOrderQuery();
        query.setOrderNo(orderNo);
        List<SagasProcessOrder> processOrders = sagasProcessOrderService.queryListByParam(query);
        if (processOrders == null || processOrders.size() == 0) {
            throw new IllegalArgumentException("过程单据异常");
        }
        for (SagasProcessOrder order : processOrders) {
            Integer status = order.getStatus();
            if(status == ProcessStatusEnum.FAIL.getType()) {
                return false;
            }
        }
        return true;
    }
    private SagasProcessOrder getProcessOrder(String orderNo,int order) {
        SagasProcessOrderQuery query = new SagasProcessOrderQuery();
        query.setOrderNo(orderNo);
        query.setOrder(order);
        List<SagasProcessOrder> processOrders = sagasProcessOrderService.queryListByParam(query);
        if (processOrders == null || processOrders.size() == 0) {
            throw new IllegalArgumentException("过程单据异常");
        }
        return processOrders.get(0);
    }
    private SagasOrder getSagasOrder(String orderNo) {
        return sagasOrderService.selectByOrderNo(orderNo);
    }
    private List<SagasProcessOrder> getProcessOrders(String orderNo,SortMode mode) {
        SagasProcessOrderQuery query = new SagasProcessOrderQuery();
        query.setOrderNo(orderNo);
        query.setSorts(new SortColumn("order",mode));
        List<SagasProcessOrder> processOrders = sagasProcessOrderService.queryListByParam(query);
        if (processOrders == null || processOrders.size() == 0) {
            throw new IllegalArgumentException("过程单据异常");
        }
        return processOrders;
    }
    private void getMapResult(Map<String,Object> result,Boolean isSend,MulStatusEnum mulStatusEnum){
        Object send = result.get("isSend");
        if (send != null) {
            isSend = false;
        }
        Object status = result.get("ING");
        if (status != null && mulStatusEnum == MulStatusEnum.SUC) {
            mulStatusEnum = MulStatusEnum.ING;
        }
        Object ring = result.get("RING");
        if (ring != null) {
            mulStatusEnum = MulStatusEnum.RING;
        }
    }
    private void getRollbackMapResult(Map<String,Object> result,Boolean isSend,MulStatusEnum mulStatusEnum){
        Object send = result.get("isSend");
        if (send != null) {
            isSend = false;
        }
        Object status = result.get("RING");
        if (status != null && mulStatusEnum == MulStatusEnum.FAIL) {
            mulStatusEnum = MulStatusEnum.RING;
        }
        Object ring = result.get("FAIL");
        if (ring != null) {
            mulStatusEnum = MulStatusEnum.RFAIL;
        }
    }
    void ringProcessHandler(Map<String,Object> result,boolean isSend,boolean isOrder,SagasProcessOrder processOrder,AnnotationHandler handler,SagasDate sagasDate){
        if (processOrder.getStatus() != ProcessStatusEnum.SUC.getType()) {
            return;
        }
        if (isOrder) {
            ProcessStatusEnum processStatusEnum = null;
            if (isSend) {
                processStatusEnum = handler.rollbackHandler(sagasDate);
                processOrder.setStatus(ProcessStatusEnum.RSUC.getType());
                if (ProcessStatusEnum.ING.equals(processStatusEnum)) {
                    result.put("isSend",false);
                    result.put("RING",MulStatusEnum.RING);
                    processOrder.setStatus(ProcessStatusEnum.RING.getType());


                }else if(ProcessStatusEnum.FAIL.equals(processStatusEnum)) {
                    result.put("FAIL",MulStatusEnum.RFAIL);
                    result.put("isSend",false);
                    processOrder.setStatus(ProcessStatusEnum.RFAIL.getType());
                }
                sagasProcessOrderService.updateById(processOrder);
            }
        }else {
            if(isSend) {
                AnnotationHandler finalHandler = handler;
                executor.execute(new Runnable() {
                    @Override
                    public void run() {
                        MulStatusEnum statusEnum = null;
                        ProcessStatusEnum processStatusEnum = finalHandler.rollbackHandler(sagasDate);
                        processOrder.setStatus(ProcessStatusEnum.RSUC.getType());

                        if (ProcessStatusEnum.ING.equals(processStatusEnum)) {
                            statusEnum = MulStatusEnum.RING;
                            result.put("RING",statusEnum);
                            processOrder.setStatus(ProcessStatusEnum.RING.getType());
                        } else if (ProcessStatusEnum.FAIL.equals(processStatusEnum)) {
                            result.put("isSend",false);
                            statusEnum = MulStatusEnum.RFAIL;
                            result.put("FAIL",statusEnum);
                            processOrder.setStatus(ProcessStatusEnum.RFAIL.getType());
                        }
                        sagasProcessOrderService.updateById(processOrder);

                    }
                });
            }
        }

    }
}
