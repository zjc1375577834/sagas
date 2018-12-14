package com.zjc.sagas.handler;

import com.zjc.sagas.annotation.Sagas;
import com.zjc.sagas.enums.MulStatusEnum;
import com.zjc.sagas.enums.ProcessStatusEnum;
import com.zjc.sagas.interfaces.ProcessControl;
import com.zjc.sagas.interfaces.SagasProcessor;
import com.zjc.sagas.model.*;
import com.zjc.sagas.query.SagasProcessOrderQuery;
import com.zjc.sagas.service.SagasOrderHistoryService;
import com.zjc.sagas.service.SagasOrderService;
import com.zjc.sagas.service.SagasProcessOrderHistoryService;
import com.zjc.sagas.service.SagasProcessOrderService;
import com.zjc.sagas.utils.BeanCopyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    @Autowired
    private SagasOrderHistoryService sagasOrderHistoryService;
    @Autowired
    private SagasProcessOrderService sagasProcessOrderService;
    @Autowired
    private SagasProcessOrderHistoryService sagasProcessOrderHistoryService;
    @Override
    public MulStatusEnum control(List<SagasDate> list, String orderNo, Integer type) {
        if (list == null || list.size() ==0 || StringUtils.isEmpty(type)) {
            throw new IllegalArgumentException("参数不能为空");
        }
        HashMap<String, Object> result = new HashMap<>();
        SagasOrder sagasOrder = sagasOrderService.selectByOrderNo(orderNo);
        Integer orderStatus = sagasOrder.getStatus();
        MulStatusEnum anEnum = MulStatusEnum.getByType(orderStatus);
        Boolean isSend = null;
        MulStatusEnum resultEnum = null;
        if (anEnum.equals(MulStatusEnum.INIT )|| anEnum.equals(MulStatusEnum.ING)){
            resultEnum = MulStatusEnum.SUC;
            for (int i =0;i < list.size(); i++) {

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
                isSend = (Boolean)result.get("isSend");

            }
            MulStatusEnum mulstatus = this.getCommitMulstatus(result, list.size());
            if (mulstatus != null && mulstatus.getType() != anEnum.getType()) {
                resultEnum = mulstatus;
                sagasOrder.setStatus(mulstatus.getType());
                sagasOrderService.updateByOrderNoAndStatus(sagasOrder,anEnum.getType());
                anEnum = mulstatus;
            }


        }
        if (anEnum.equals(MulStatusEnum.ROLL) || anEnum.equals(MulStatusEnum.RING)) {
            resultEnum = MulStatusEnum.FAIL;
            isSend = true;
            result.put("isSend",true);
            result.remove("status");
            for (int i = list.size()-1;i>=0 ; i--) {
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
                isSend = (Boolean)result.get("isSend");
                MulStatusEnum mulstatus = this.getRollMulstatus(result, list.size());
                if (mulstatus != null && mulstatus.getType() != anEnum.getType()) {
                    resultEnum = mulstatus;
                    sagasOrder.setStatus(mulstatus.getType());
                    sagasOrderService.updateByOrderNoAndStatus(sagasOrder,anEnum.getType());
                    anEnum = mulstatus;
                }
            }
        }
        sagasOrder.setStatus(resultEnum.getType());
        if(resultEnum.equals(MulStatusEnum.SUC) || resultEnum.equals(MulStatusEnum.FAIL)) {
            sagasOrderHistoryService.updateByOrderNoAndStatus(BeanCopyUtils.copy(sagasOrder),MulStatusEnum.INIT.getType());
            sagasOrderService.deleteById(sagasOrder.getId());
            this.updateProcessOrder(orderNo);
        }else {
            sagasOrderService.updateByOrderNoAndStatus(sagasOrder, anEnum.getType());
        }

        return resultEnum;
    }
    private boolean getAnnotion(SagasDate sagasDate) {
        Class<? extends SagasProcessor> aClass = sagasDate.getProcessor().getClass();
        Sagas annotation = aClass.getAnnotation(Sagas.class);
        if (annotation == null) {
            throw new IllegalArgumentException("过程异常,缺少注解");
        }
        return annotation.isOrder();
    }
    private MulStatusEnum getCommitMulstatus(Map result,int size){
        MulStatusEnum statusEnum = MulStatusEnum.SUC;
        for (int i = 0; i < size ; i++) {
            Object o = result.get(i + "");
            if (o == null) {
                if (statusEnum.getType() < MulStatusEnum.ING.getType()) {
                    statusEnum = MulStatusEnum.ING;
                }
            }else {
                MulStatusEnum status = (MulStatusEnum)o;
                if (statusEnum.getType() < status.getType()) {
                    statusEnum = status;
                }
            }

        }
        return statusEnum;
    }
    private MulStatusEnum getRollMulstatus(Map result, int size){
        MulStatusEnum mulstatus = this.getCommitMulstatus(result, size);
        if (mulstatus == MulStatusEnum.ING) {
            return MulStatusEnum.RING;
        }
        return mulstatus;
    }
    private void updateProcessOrder(String orderNo) {
        SagasProcessOrderQuery query = new SagasProcessOrderQuery();
        query.setOrderNo(orderNo);
        query.setRows(1000);
        query.setOffset(0);
        List<SagasProcessOrder> sagasProcessOrders = sagasProcessOrderService.queryListByParam(query);
        for (int i = 0; i< sagasProcessOrders.size(); i++) {
            sagasProcessOrderHistoryService.updateByProcessNoAndStatus(BeanCopyUtils.copy(sagasProcessOrders.get(i)), ProcessStatusEnum.INIT.getType());
            sagasOrderHistoryService.deleteById(sagasProcessOrders.get(i).getId());
        }
    }

}
