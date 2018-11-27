package com.zjc.sagas.handler;

import com.zjc.sagas.enums.MulStatusEnum;
import com.zjc.sagas.interfaces.AnnotationHandler;
import com.zjc.sagas.model.SagasDate;
import com.zjc.sagas.service.SagasOrderService;
import com.zjc.sagas.service.SagasProcessOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    /**
     * 这种状态是由于服务器中断这时候所有process落库只要执行发送就行 可能存在部分成功
     * @param list
     * @param type
     * @return
     */
    MulStatusEnum initHandler(List<SagasDate> list,Integer type){
        HashMap<String, Object> result = new HashMap<>();
        boolean isSend = true;
        MulStatusEnum mulStatusEnum = MulStatusEnum.SUC;
        AnnotationHandler handler = map.get(type);
        if (handler == null) {
            handler = new SagasDefaultHandler();
        }

        return null;
    }
    MulStatusEnum ingHandler(List<SagasDate> list,Integer type){
        return  null;
    }
    MulStatusEnum ringHandler(List<SagasDate> list,Integer type){
        return null;
    }
}
