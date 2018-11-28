package com.zjc.sagas.model;

/**
 * create by zjc on 18/11/26
 */
public class SagasContext<T> {
    private T param;


    public T getParam() {
        return param;
    }

    public void setParam(T param) {
        this.param = param;
    }
}
