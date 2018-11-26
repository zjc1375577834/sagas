package com.zjc.sagas.model;

import java.util.Date;
import java.io.Serializable;

/**
* SagasProcessOrder
* Created by autoGenerate on 18-11-26 下午2:54 .
*/ 
public class SagasProcessOrder implements Serializable {

	private static final long serialVersionUID = 1L;


	/**
	 * 数据库字段长度:(11,0)
	 * 字段备注:自增主键
	 * 是否索引:是
	 */
	private Integer id;


	/**
	 * 数据库字段长度:(64,0)
	 * 字段备注:订单编号
	 * 是否索引:是
	 */
	private String orderNo;


	/**
	 * 数据库字段长度:(64,0)
	 * 字段备注:过程编号
	 * 是否索引:是
	 */
	private String processNo;


	/**
	 * 数据库字段长度:(128,0)
	 * 字段备注:参数
	 * 是否索引:不是
	 */
	private String param;


	/**
	 * 数据库字段长度:(64,0)
	 * 字段备注:类名
	 * 是否索引:不是
	 */
	private String className;


	/**
	 * 数据库字段长度:(64,0)
	 * 字段备注:方法名
	 * 是否索引:不是
	 */
	private String mothedName;


	/**
	 * 数据库字段长度:(1,0)
	 * 字段备注:状态
	 * 是否索引:不是
	 */
	private Integer status;


	/**
	 * 数据库字段长度:(1,0)
	 * 字段备注:序号
	 * 是否索引:不是
	 */
	private Integer order;


	/**
	 * 数据库字段长度:(4,0)
	 * 字段备注:重发次数
	 * 是否索引:不是
	 */
	private Integer reSend;


	/**
	 * 数据库字段长度:(19,0)
	 * 字段备注:创建时间
	 * 是否索引:是
	 */
	private Date createTime;


	/**
	 * 数据库字段长度:(19,0)
	 * 字段备注:修改时间
	 * 是否索引:不是
	 */
	private Date modifyTime;




	public SagasProcessOrder(){
	}


	public void setId(Integer id){
		this.id = id;
	}

	public Integer getId(){
		return id;
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


	public void setCreateTime(Date createTime){
		this.createTime = createTime;
	}

	public Date getCreateTime(){
		return createTime;
	}


	public void setModifyTime(Date modifyTime){
		this.modifyTime = modifyTime;
	}

	public Date getModifyTime(){
		return modifyTime;
	}



}

