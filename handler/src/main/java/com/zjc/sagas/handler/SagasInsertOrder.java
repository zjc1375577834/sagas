package com.zjc.sagas.handler;

import com.alibaba.fastjson.JSON;
import com.zjc.sagas.dao.SagasProcessOrderHistoryDao;
import com.zjc.sagas.enums.MulStatusEnum;
import com.zjc.sagas.enums.ProcessStatusEnum;
import com.zjc.sagas.model.*;
import com.zjc.sagas.service.SagasOrderHistoryService;
import com.zjc.sagas.service.SagasOrderService;
import com.zjc.sagas.service.SagasProcessOrderHistoryService;
import com.zjc.sagas.service.SagasProcessOrderService;
import com.zjc.sagas.utils.BeanCopyUtils;
import com.zjc.sagas.utils.ContextUtils;
import com.zjc.sagas.utils.SeqCreateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.beans.BeanCopier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * create by zjc in 2018/11/28 0028
 */
@Service
public class SagasInsertOrder {
    @Autowired
    private SagasOrderService sagasOrderService;
    @Autowired
    private SagasProcessOrderService sagasProcessOrderService;
    @Autowired
    private SagasOrderHistoryService sagasOrderHistoryService;
    @Autowired
    private SagasProcessOrderHistoryService sagasProcessOrderHistoryService;
    @Transactional
    public boolean insertOrder(List<SagasDate> list,String orderNo,Integer type){
        SagasOrder order = new SagasOrder();
        order.setOrderNo(orderNo);
        order.setParamHash(list.hashCode());
        order.setType(type);
        order.setStatus(MulStatusEnum.INIT.getType());
        order.setCreateTime(new Date());
        int insert = sagasOrderService.insert(order);
        int insert1 = sagasOrderHistoryService.insert(BeanCopyUtils.copy(order));
        if (insert == 0) {
            return false;
        }
        int temp = 0;
        for(SagasDate sagasDate : list){
            SagasProcessOrder processOrder = new SagasProcessOrder();
            processOrder.setClassName(sagasDate.getProcessor().getClass().getName());
            processOrder.setStatus(ProcessStatusEnum.INIT.getType());
            processOrder.setReSend(0);
            processOrder.setProcessNo(SeqCreateUtil.create("process"));
            processOrder.setParam(JSON.toJSONString(sagasDate.getContext()));
            processOrder.setOrderNo(orderNo);
            processOrder.setMothedName("doCommit");
            processOrder.setOrder(temp);
            processOrder.setCreateTime(new Date());
            sagasProcessOrderService.insert(processOrder);
            sagasProcessOrderHistoryService.insert(BeanCopyUtils.copy(processOrder));
            temp++;
        }
        ContextUtils.put(orderNo,list);
        return true;
    }

}
