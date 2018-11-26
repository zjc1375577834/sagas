package com.zjc.sagas.enums;

/**
 * create by zjc on 18/11/26
 */
public enum MulStatusEnum {
    INIT("INIT",0,"初始化"),
    SUC("SUCCESS",1,"成功"),
    ING("ING",2,"处理中"),
    RING("RING",3,"会滚处理中"),
    FAIL("FAIL",4,"失败"),
    RFAIL("RFAIL",5,"会滚失败")

    ;
    private String code;
    private Integer type;
    private String msg;
    MulStatusEnum(String code, Integer type, String msg) {
        this.code = code;
        this.type = type;
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public Integer getType() {
        return type;
    }

    public String getMsg() {
        return msg;
    }
}