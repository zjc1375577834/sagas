package com.zjc.sagas.enums;

/**
 * create by zjc on 18/11/26
 */
public enum ProcessStatusEnum {
    INIT("INIT",0,"初始化"),
    SUC("SUCCESS",1,"成功"),
    ING("ING",2,"处理中"),
    FAIL("FAIL",3,"失败"),
    NOBEGIN("NOBEGIN",4,"订单未发送成功"),


            ;
    private String code;
    private Integer type;
    private String msg;
    ProcessStatusEnum(String code, Integer type, String msg) {
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
    public static ProcessStatusEnum getByMsg(String msg) {
        for(ProcessStatusEnum baseEnum : ProcessStatusEnum.values()) {
            if (baseEnum.msg.equals(msg)) {
                return baseEnum;
            }
        }
        throw new IllegalArgumentException("不存在这种枚举");

    }
    public static ProcessStatusEnum getByCode(String code) {
        for(ProcessStatusEnum baseEnum : ProcessStatusEnum.values()) {
            if (baseEnum.code.equals(code)) {
                return baseEnum;
            }
        }
        throw new IllegalArgumentException("不存在这种枚举");

    }
    public static ProcessStatusEnum getByType(Integer type) {
        for(ProcessStatusEnum baseEnum : ProcessStatusEnum.values()) {
            if (baseEnum.type.equals(type)) {
                return baseEnum;
            }
        }
        throw new IllegalArgumentException("不存在这种枚举");

    }
}
