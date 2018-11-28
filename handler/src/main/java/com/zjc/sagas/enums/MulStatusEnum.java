package com.zjc.sagas.enums;

/**
 * create by zjc on 18/11/26
 */
public enum MulStatusEnum {
    INIT("INIT",0,"初始化"),
    SUC("SUCCESS",1,"成功"),
    ING("ING",2,"处理中"),
    ROLL("ROLL",3,"回滚中"),
    RING("RING",4,"会滚处理中"),
    FAIL("FAIL",5,"失败"),
    RFAIL("RFAIL",6,"会滚失败")

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
    public static MulStatusEnum getByMsg(String msg) {
        for(MulStatusEnum baseEnum : MulStatusEnum.values()) {
            if (baseEnum.msg.equals(msg)) {
                return baseEnum;
            }
        }
        throw new IllegalArgumentException("不存在这种枚举");

    }
    public static MulStatusEnum getByCode(String code) {
        for(MulStatusEnum baseEnum : MulStatusEnum.values()) {
            if (baseEnum.code.equals(code)) {
                return baseEnum;
            }
        }
        throw new IllegalArgumentException("不存在这种枚举");

    }
    public static MulStatusEnum getByType(Integer type) {
        for(MulStatusEnum baseEnum : MulStatusEnum.values()) {
            if (baseEnum.type.equals(type)) {
                return baseEnum;
            }
        }
        throw new IllegalArgumentException("不存在这种枚举");

    }
}
