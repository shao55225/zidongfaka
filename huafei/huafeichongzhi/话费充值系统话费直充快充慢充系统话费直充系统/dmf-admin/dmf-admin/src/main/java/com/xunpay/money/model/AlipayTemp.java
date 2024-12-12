package com.xunpay.money.model;

import com.xunpay.money.model.annotation.Table;
import com.jfinal.plugin.activerecord.Model;

/**
 * 
 * 对应表名：alipay_temp
 * 表描述：
 * 
 * @author ModelHelper 
 * @version 1.0 
 */
@SuppressWarnings("serial")
@Table(name = "alipay_temp")
public class AlipayTemp extends Model<AlipayTemp> {

	public static final AlipayTemp dao = new AlipayTemp();


	/**
	 * <ul>
	 *   <li>非空：不能为空</li>
	 *   <li>数据类型：int</li>
	 *   <li>键约束：PRI</li>
	 *   <li>扩展：自动增长列</li>
	 * </ul>
	 */
	public Integer getId() {
		return (Integer) get("id");
	}

	public void setId(Integer id) {
		set("id", id);
	}

	/**
	 * <ul>
	 *   <li>非空：可以为空</li>
	 *   <li>数据类型：varchar(32)</li>
	 * </ul>
	 */
	public String getPid() {
		return (String) get("pid");
	}

	public void setPid(String pid) {
		set("pid", pid);
	}

	/**
	 * <ul>
	 *   <li>非空：可以为空</li>
	 *   <li>数据类型：decimal</li>
	 * </ul>
	 */
	public java.math.BigDecimal getTodayMoney() {
		return (java.math.BigDecimal) get("today_money");
	}

	public void setTodayMoney(java.math.BigDecimal todayMoney) {
		set("today_money", todayMoney);
	}

	/**
	 * <ul>
	 *   <li>非空：可以为空</li>
	 *   <li>数据类型：decimal</li>
	 * </ul>
	 */
	public java.math.BigDecimal getMaxMoney() {
		return (java.math.BigDecimal) get("max_money");
	}

	public void setMaxMoney(java.math.BigDecimal maxMoney) {
		set("max_money", maxMoney);
	}

	/**
	 * <ul>
	 *   <li>非空：可以为空</li>
	 *   <li>数据类型：decimal</li>
	 * </ul>
	 */
	public java.math.BigDecimal getTotalMoney() {
		return (java.math.BigDecimal) get("total_money");
	}

	public void setTotalMoney(java.math.BigDecimal totalMoney) {
		set("total_money", totalMoney);
	}

}