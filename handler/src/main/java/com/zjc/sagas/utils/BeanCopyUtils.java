package com.zjc.sagas.utils;

import com.zjc.sagas.model.SagasOrder;
import com.zjc.sagas.model.SagasOrderHistory;
import com.zjc.sagas.model.SagasProcessOrder;
import com.zjc.sagas.model.SagasProcessOrderHistory;
import org.springframework.cglib.beans.BeanCopier;
import org.springframework.util.CollectionUtils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * create by zjc in 2018-12-05
 */
public class BeanCopyUtils {
    /**
     * beanCopier缓存
     * (A拷贝到B,确定一个beanCopier)
     */
    private static Map<Class<?>, Map<Class<?>, BeanCopier>> beanCopierMap = new ConcurrentHashMap<>();

    /**  
      * <p> 拷贝方法 </p>
     * 
      * @param sourceBean
      * @param targetBean
      */
    public static <S, T> void copy(S sourceBean, T targetBean) {
        @SuppressWarnings("unchecked")
        Class<S> sourceClass = (Class<S>) sourceBean.getClass();
        @SuppressWarnings("unchecked")
        Class<T> targetClass = (Class<T>) targetBean.getClass();

        BeanCopier beanCopier = getBeanCopier(sourceClass, targetClass);
        beanCopier.copy(sourceBean, targetBean, null);
    }

    /**  
      * <p> 转换方法 </p>
     * 
      * @param sourceBean 原对象
      * @param targetClass 目标类
      * @return T
      */
    public static <S, T> T convert(S sourceBean, Class<T> targetClass) {
        try {
            assert sourceBean != null;
            T targetBean = targetClass.newInstance();
            copy(sourceBean, targetBean);
            return targetBean;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**  
      * <p> 获取Bean拷贝 </p>
     * 
      * @param sourceClass 原对象
      * @param targetClass 目标类
      * @return
      */
    private static <S, T> BeanCopier getBeanCopier(Class<S> sourceClass, Class<T> targetClass) {
        Map<Class<?>, BeanCopier> map = beanCopierMap.get(sourceClass);
        if (CollectionUtils.isEmpty(map)) {
            BeanCopier newBeanCopier = BeanCopier.create(sourceClass, targetClass, false);
            Map<Class<?>, BeanCopier> newMap = new ConcurrentHashMap<>();
            newMap.put(targetClass, newBeanCopier);
            beanCopierMap.put(sourceClass, newMap);
            return newBeanCopier;
        }

        BeanCopier beanCopier = map.get(targetClass);
        if (beanCopier == null) {
            BeanCopier newBeanCopier = BeanCopier.create(sourceClass, targetClass, false);
            map.put(targetClass, newBeanCopier);

            return newBeanCopier;
        }

        return beanCopier;
    }
    public static SagasOrderHistory copy(SagasOrder sagasOrder) {
        if (sagasOrder == null) {
            return null;
        }
        SagasOrderHistory history = new SagasOrderHistory();
        history.setCreateTime(sagasOrder.getCreateTime());
        history.setId(sagasOrder.getId());
        history.setModifyTime(sagasOrder.getModifyTime());
        history.setOrderNo(sagasOrder.getOrderNo());
        history.setParamHash(sagasOrder.getParamHash());
        history.setStatus(sagasOrder.getStatus());
        history.setType(sagasOrder.getType());
        return history;
    }

    public static SagasProcessOrderHistory copy(SagasProcessOrder processOrder) {
        if (processOrder == null) {
            return null;
        }
        SagasProcessOrderHistory history = new SagasProcessOrderHistory();
        history.setClassName(processOrder.getClassName());
        history.setStatus(processOrder.getStatus());
        history.setReSend(processOrder.getReSend());
        history.setProcessNo(processOrder.getProcessNo());
        history.setParam(processOrder.getParam());
        history.setOrderNo(processOrder.getOrderNo());
        history.setMothedName(processOrder.getMothedName());
        history.setModifyTime(processOrder.getModifyTime());
        history.setOrder(processOrder.getOrder());
        history.setId(processOrder.getId());
        history.setCreateTime(processOrder.getCreateTime());
        return history;
    }
}
