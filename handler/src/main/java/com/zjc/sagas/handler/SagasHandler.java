package com.zjc.sagas.handler;

import com.alibaba.fastjson.JSON;
import com.zjc.sagas.annotation.Sagas;
import com.zjc.sagas.enums.MulStatusEnum;
import com.zjc.sagas.enums.ProcessStatusEnum;
import com.zjc.sagas.interfaces.AnnotationHandler;
import com.zjc.sagas.model.SagasDate;
import com.zjc.sagas.model.SagasOrder;
import com.zjc.sagas.model.SagasProcessOrder;
import com.zjc.sagas.service.SagasOrderService;
import com.zjc.sagas.service.SagasProcessOrderService;
import com.zjc.sagas.utils.SeqCreateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * create by zjc on 18/11/26
 */
@Component
public class SagasHandler {
    private  Map<Integer, AnnotationHandler> map = new HashMap<>();
    @Autowired
    private SagasOrderService sagasOrderService;
    @Autowired
    private SagasProcessOrderService sagasProcessOrderService;
    private ThreadPoolExecutor executor ;
    public  MulStatusEnum handler(List<SagasDate> list,String orderNo,Integer type) {
        SagasOrder order = new SagasOrder();
        order.setOrderNo(orderNo);
        order.setParamHash(list.hashCode());
        order.setType(type);
        int insert = sagasOrderService.insert(order);
        if (insert == 0) {
            return MulStatusEnum.ING;
        }
        int temp = 0;
        MulStatusEnum mulStatusEnum = MulStatusEnum.SUC;
        boolean isSend = true;
        HashMap<String, Object> result = new HashMap<>();
        for (SagasDate sagasDate : list) {
            temp++;
            Sagas annotation = sagasDate.getClass().getAnnotation(Sagas.class);
            SagasProcessOrder processOrder = new SagasProcessOrder();
            processOrder.setClassName(sagasDate.getProcessor().getClass().getName());
            processOrder.setStatus(ProcessStatusEnum.INIT.getType());
            processOrder.setReSend(0);
            processOrder.setProcessNo(SeqCreateUtil.create("process"));
            processOrder.setParam(JSON.toJSONString(sagasDate.getContext()));
            processOrder.setOrderNo(orderNo);
            processOrder.setMothedName("doCommit");
            processOrder.setOrder(temp);
            sagasProcessOrderService.insert(processOrder);
            AnnotationHandler handler = map.get(type);
            if (handler == null ) {
                handler = new SagasDefaultHandler();
            }
            Object send = result.get("isSend");
            if (send != null) {
                isSend = false;
            }
            Object status = result.get("ING");
            if (status != null) {
                mulStatusEnum = MulStatusEnum.ING;
            }
            Object ring = result.get("RING");
            if (ring != null) {
                mulStatusEnum = MulStatusEnum.RING;
            }
            this.processHandler(result,isSend,annotation.isOrder(),processOrder,mulStatusEnum,handler,sagasDate);

        }
        order.setStatus(mulStatusEnum.getType());
        sagasOrderService.updateByOrderNo(order);
        return mulStatusEnum;
    }
    public MulStatusEnum handler(List<SagasDate> list,String orderNo) {
        return handler(list,orderNo,1);

    }
    void processHandler(Map<String,Object> result,boolean isSend,boolean isOrder,SagasProcessOrder processOrder,MulStatusEnum mulStatusEnum,AnnotationHandler handler,SagasDate sagasDate){
        if (isOrder) {
            ProcessStatusEnum processStatusEnum = null;
            if (isSend) {
                processStatusEnum = handler.handler(sagasDate);
                if (ProcessStatusEnum.ING.equals(processStatusEnum)) {
                    mulStatusEnum = MulStatusEnum.ING;
                    isSend = false;
                }else if(ProcessStatusEnum.FAIL.equals(processStatusEnum)) {
                    mulStatusEnum = MulStatusEnum.RING;
                    isSend = false;
                }
                processOrder.setStatus(processStatusEnum.getType());
                sagasProcessOrderService.updateById(processOrder);
            }
        }else {
            if(isSend) {
                AnnotationHandler finalHandler = handler;
                executor.execute(new Runnable() {
                    @Override
                    public void run() {
                        MulStatusEnum statusEnum = null;
                        ProcessStatusEnum processStatusEnum = finalHandler.handler(sagasDate);
                        processOrder.setStatus(processStatusEnum.getType());
                        sagasProcessOrderService.updateById(processOrder);
                        if (ProcessStatusEnum.FAIL.equals(processStatusEnum)) {
                            statusEnum = MulStatusEnum.RING;
                            result.put("isSend",false);
                            result.put("RING",statusEnum);
                        } else if (ProcessStatusEnum.ING.equals(processStatusEnum)) {
                            statusEnum = MulStatusEnum.ING;
                            result.put("ING",statusEnum);
                        }
                    }
                });
            }
        }
    }
}
