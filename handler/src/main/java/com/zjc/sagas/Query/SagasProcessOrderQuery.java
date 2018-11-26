package com.zjc.sagas.Query;

import com.tongbanjie.xd.commons.qo.BaseQO;
import java.util.Date;

/**
 * 查询对象
 * Created by AutoGenerate on 18-11-26 下午2:54 .
 */
public class SagasProcessOrderQuery extends BaseQuery {


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
    * 数据库字段长度:(64,0)
    * 字段备注:过程编号
    */
    private String processNo;


    /**
    * 索引字段:不是
    * 数据库字段长度:(128,0)
    * 字段备注:参数
    */
    private String param;


    /**
    * 索引字段:不是
    * 数据库字段长度:(64,0)
    * 字段备注:类名
    */
    private String className;


    /**
    * 索引字段:不是
    * 数据库字段长度:(64,0)
    * 字段备注:方法名
    */
    private String mothedName;


    /**
    * 索引字段:不是
    * 数据库字段长度:(1,0)
    * 字段备注:状态
    */
    private Integer status;


    /**
    * 索引字段:不是
    * 数据库字段长度:(1,0)
    * 字段备注:序号
    */
    private Integer order;


    /**
    * 索引字段:不是
    * 数据库字段长度:(4,0)
    * 字段备注:重发次数
    */
    private Integer reSend;


    public SagasProcessOrderQuery(){
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

    public void setProcessNo(String processNo){
        this.processNo = processNo;
    }

    public String getProcessNo(){
        return processNo;
    }

    public void setParam(String param){
        this.param = param;
    }

    public String getParam(){
        return param;
    }

    public void setClassName(String className){
        this.className = className;
    }

    public String getClassName(){
        return className;
    }

    public void setMothedName(String mothedName){
        this.mothedName = mothedName;
    }

    public String getMothedName(){
        return mothedName;
    }

    public void setStatus(Integer status){
        this.status = status;
    }

    public Integer getStatus(){
        return status;
    }

    public void setOrder(Integer order){
        this.order = order;
    }

    public Integer getOrder(){
        return order;
    }

    public void setReSend(Integer reSend){
        this.reSend = reSend;
    }

    public Integer getReSend(){
        return reSend;
    }


}