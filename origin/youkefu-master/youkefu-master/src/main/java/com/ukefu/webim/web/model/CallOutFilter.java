package com.ukefu.webim.web.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * 批次表，导入批次
 * @author iceworld
 *
 */
@Entity
@Table(name = "uk_act_filter_his")
@org.hibernate.annotations.Proxy(lazy = false)
public class CallOutFilter implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2258870729818431384L;
	
	private String id ;
	
	private String orgi ;		//租户ID
	private String organ ;		//创建部门
	private String creater ;	//创建人
	
	private String actid ;		//活动ID
	private String batid ;		//批次ID
	private String filterid ;	//筛选表单ID
	
	private Date createtime = new Date();	//创建时间
	
	private Date updatetime = new Date();
	
	private String datastatus;	//数据状态（逻辑删除）
	private String status ;		//状态		正常，已处理完，已过期
	
	private int namenum ;		//批次名单总数
	
	private int renum ;		//回收数量
	private int reorgannum ;		//回收到部门数量
	
	private int execnum ;		//执行次数
	private String exectype ;	//任务类型
	
	private int assigned ;		//已分配到坐席
	private int assignedorgan ;	//已分配到部门
	private int notassigned;	//未分配
	private String description ;//备注
	
	private String name;		//导入的任务名称 ， 自动生成， 规则为 yyyyMMdd--ORDER
	
	private int assignedai;	//已分配到AI
	private int assignedforecast ;	//已分配到队列
	@Id
	@Column(length = 32)
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getOrgi() {
		return orgi;
	}

	public void setOrgi(String orgi) {
		this.orgi = orgi;
	}

	public String getOrgan() {
		return organ;
	}

	public void setOrgan(String organ) {
		this.organ = organ;
	}
	public String getCreater() {
		return creater;
	}

	public void setCreater(String creater) {
		this.creater = creater;
	}

	public String getActid() {
		return actid;
	}

	public void setActid(String actid) {
		this.actid = actid;
	}

	public String getBatid() {
		return batid;
	}

	public void setBatid(String batid) {
		this.batid = batid;
	}

	public String getFilterid() {
		return filterid;
	}

	public void setFilterid(String filterid) {
		this.filterid = filterid;
	}

	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	public Date getUpdatetime() {
		return updatetime;
	}

	public void setUpdatetime(Date updatetime) {
		this.updatetime = updatetime;
	}

	public String getDatastatus() {
		return datastatus;
	}

	public void setDatastatus(String datastatus) {
		this.datastatus = datastatus;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getNamenum() {
		return namenum;
	}

	public void setNamenum(int namenum) {
		this.namenum = namenum;
	}

	public int getExecnum() {
		return execnum;
	}

	public void setExecnum(int execnum) {
		this.execnum = execnum;
	}

	public int getAssigned() {
		return assigned;
	}

	public void setAssigned(int assigned) {
		this.assigned = assigned;
	}

	public int getNotassigned() {
		return notassigned;
	}

	public void setNotassigned(int notassigned) {
		this.notassigned = notassigned;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAssignedorgan() {
		return assignedorgan;
	}

	public void setAssignedorgan(int assignedorgan) {
		this.assignedorgan = assignedorgan;
	}

	public String getExectype() {
		return exectype;
	}

	public void setExectype(String exectype) {
		this.exectype = exectype;
	}

	public int getRenum() {
		return renum;
	}

	public void setRenum(int renum) {
		this.renum = renum;
	}

	public int getReorgannum() {
		return reorgannum;
	}

	public void setReorgannum(int reorgannum) {
		this.reorgannum = reorgannum;
	}

	public int getAssignedai() {
		return assignedai;
	}

	public void setAssignedai(int assignedai) {
		this.assignedai = assignedai;
	}

	public int getAssignedforecast() {
		return assignedforecast;
	}

	public void setAssignedforecast(int assignedforecast) {
		this.assignedforecast = assignedforecast;
	}
	
}
