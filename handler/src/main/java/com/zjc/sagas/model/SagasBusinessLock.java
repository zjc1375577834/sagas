package com.zjc.sagas.model;

import java.io.Serializable;
import java.util.Date;

/**
* SagasOrder
* Created by autoGenerate on 18-11-26 下午2:54 .
*/ 
public class SagasBusinessLock implements Serializable {

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



	private Integer type;

	/**
	 * 线程数
	 */
	private Integer thread;


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




	public SagasBusinessLock(){
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


	public Integer getThread() {
		return thread;
	}

	public void setThread(Integer thread) {
		this.thread = thread;
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

