package com.zjc.sagas.handler;

import com.zjc.sagas.annotation.SagasMethod;
import com.zjc.sagas.enums.ProcessStatusEnum;
import com.zjc.sagas.interfaces.AnnotationHandler;
import com.zjc.sagas.interfaces.SagasProcessor;
import com.zjc.sagas.model.SagasDate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
@Service
public class AnnotationDistribute  {
    private Map<String, AnnotationHandler> map = new HashMap<>();
    public ProcessStatusEnum handler(SagasDate sagasDate){
        AnnotationHandler handler = this.getHandler(sagasDate);
        return handler.handler(sagasDate);
    }
    ProcessStatusEnum rollbackHandler(SagasDate sagasDate) {
        AnnotationHandler handler = this.getHandler(sagasDate);
        return handler.rollbackHandler(sagasDate);
    }
    ProcessStatusEnum handlerQuery(SagasDate sagasDate) {
        AnnotationHandler handler = this.getHandler(sagasDate);
        return handler.handlerQuery(sagasDate);
    }
    ProcessStatusEnum rollbackQuery(SagasDate sagasDate) {
        AnnotationHandler handler = this.getHandler(sagasDate);
        return handler.rollbackQuery(sagasDate);
    }
    private AnnotationHandler getHandler(SagasDate sagasDate) {
        Class<? extends SagasProcessor> aClass = sagasDate.getProcessor();
        SagasMethod annotation = aClass.getAnnotation(SagasMethod.class);
        String key = "";
        if (annotation != null) {
            key = annotation.handler();
        }
        AnnotationHandler handler = map.get(key);
        if (handler == null) {
            handler = new SagasDefaultHandler();
        }
        return handler;
    }

}
