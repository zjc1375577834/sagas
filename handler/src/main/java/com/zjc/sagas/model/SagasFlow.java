package com.zjc.sagas.model;

import java.io.Serializable;
import java.util.Date;

/**
* SagasOrder
* Created by autoGenerate on 18-11-26 下午2:54 .
*/ 
public class SagasFlow implements Serializable {

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
	 * 数据库字段长度:(1,0)
	 * 字段备注:订单状态
	 * 是否索引:是
	 */
	private Integer status;


	/**
	 * 数据库字段长度:(5,0)
	 * 字段备注:参数hash
	 * 是否索引:是
	 */
	private Integer paramHash;


	/**
	 * 数据库字段长度:(1,0)
	 * 字段备注:类型
	 * 是否索引:不是
	 */
	private Integer type;


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




	public SagasFlow(){
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

