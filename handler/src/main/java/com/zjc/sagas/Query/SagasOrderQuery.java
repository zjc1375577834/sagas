package com.zjc.sagas.Query;

import com.tongbanjie.xd.commons.qo.BaseQO;
import java.util.Date;

/**
 * 查询对象
 * Created by AutoGenerate on 18-11-26 下午2:54 .
 */
public class SagasOrderQuery extends BaseQuery {


    /**
    * 索引字段:是
    * 数据库字段长度:(19,0)
    * 字段备注:大于等于创建时间
    */
    private Date egtCreateTime;

    /**
    * 索引字段:是
    * 数据库字段长度:(19,0)
    * 字段备注:小于创建时间
    */
    private Date ltCreateTime;


    /**
    * 索引字段:是
    * 数据库字段长度:(64,0)
    * 字段备注:订单编号
    */
    private String orderNo;


    /**
    * 索引字段:是
    * 数据库字段长度:(1,0)
    * 字段备注:订单状态
    */
    private Integer status;


    /**
    * 索引字段:是
    * 数据库字段长度:(5,0)
    * 字段备注:参数hash
    */
    private Integer paramHash;


    /**
    * 索引字段:不是
    * 数据库字段长度:(1,0)
    * 字段备注:类型
    */
    private Integer type;


    public SagasOrderQuery(){
    }


    public void setEgtCreateTime(Date egtCreateTime){
        this.egtCreateTime = egtCreateTime;
    }

    public Date getEgtCreateTime(){
        return egtCreateTime;
    }

    public void setLtCreateTime(Date ltCreateTime){
        this.ltCreateTime = ltCreateTime;
    }

    public Date getLtCreateTime(){
        return ltCreateTime;
    }

    public void setOrderNo(String orderNo){
        this.orderNo = orderNo;
    }

    public String getOrderNo(){
        return orderNo;
    }

    public void setStatus(Integer status){
        this.status = status;
    }

    public Integer getStatus(){
        return status;
    }

    public void setParamHash(Integer paramHash){
        this.paramHash = paramHash;
    }

    public Integer getParamHash(){
        return paramHash;
    }

    public void setType(Integer type){
        this.type = type;
    }

    public Integer getType(){
        return type;
    }


}