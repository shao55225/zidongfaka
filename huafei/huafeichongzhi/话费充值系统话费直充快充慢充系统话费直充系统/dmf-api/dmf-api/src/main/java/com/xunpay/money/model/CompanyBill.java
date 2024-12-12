package com.xunpay.money.model;

import com.xunpay.money.model.annotation.Table;
import com.jfinal.plugin.activerecord.Model;

/**
 * 
 * 对应表名：company_bill
 * 表描述：
 * 
 * @author ModelHelper 
 * @version 1.0 
 */
@SuppressWarnings("serial")
@Table(name = "company_bill")
public class CompanyBill extends Model<CompanyBill> {

	public static final CompanyBill dao = new CompanyBill();


	/**
	 * 主键<br />
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
	 * 代理id<br />
	 * <ul>
	 *   <li>非空：可以为空</li>
	 *   <li>数据类型：int</li>
	 * </ul>
	 */
	public Integer getCompanyId() {
		return (Integer) get("company_id");
	}

	public void setCompanyId(Integer companyId) {
		set("company_id", companyId);
	}

	/**
	 * 代理名<br />
	 * <ul>
	 *   <li>非空：可以为空</li>
	 *   <li>数据类型：varchar(32)</li>
	 * </ul>
	 */
	public String getCompanyName() {
		return (String) get("company_name");
	}

	public void setCompanyName(String companyName) {
		set("company_name", companyName);
	}

	/**
	 * 订单id<br />
	 * <ul>
	 *   <li>非空：可以为空</li>
	 *   <li>数据类型：int</li>
	 * </ul>
	 */
	public Integer getOrderId() {
		return (Integer) get("order_id");
	}

	public void setOrderId(Integer orderId) {
		set("order_id", orderId);
	}

	/**
	 * 订单号<br />
	 * <ul>
	 *   <li>非空：可以为空</li>
	 *   <li>数据类型：varchar(32)</li>
	 * </ul>
	 */
	public String getOrderNo() {
		return (String) get("order_no");
	}

	public void setOrderNo(String orderNo) {
		set("order_no", orderNo);
	}

	/**
	 * 订单金额<br />
	 * <ul>
	 *   <li>非空：可以为空</li>
	 *   <li>数据类型：decimal</li>
	 * </ul>
	 */
	public java.math.BigDecimal getOrderMoney() {
		return (java.math.BigDecimal) get("order_money");
	}

	public void setOrderMoney(java.math.BigDecimal orderMoney) {
		set("order_money", orderMoney);
	}

	/**
	 * 提现申请id<br />
	 * <ul>
	 *   <li>非空：可以为空</li>
	 *   <li>数据类型：int</li>
	 * </ul>
	 */
	public Integer getDrawId() {
		return (Integer) get("draw_id");
	}

	public void setDrawId(Integer drawId) {
		set("draw_id", drawId);
	}

	/**
	 * 返点<br />
	 * <ul>
	 *   <li>非空：可以为空</li>
	 *   <li>数据类型：decimal</li>
	 * </ul>
	 */
	public java.math.BigDecimal getTax() {
		return (java.math.BigDecimal) get("tax");
	}

	public void setTax(java.math.BigDecimal tax) {
		set("tax", tax);
	}

	/**
	 * 返点金额<br />
	 * <ul>
	 *   <li>非空：可以为空</li>
	 *   <li>数据类型：decimal</li>
	 * </ul>
	 */
	public java.math.BigDecimal getTaxMoney() {
		return (java.math.BigDecimal) get("tax_money");
	}

	public void setTaxMoney(java.math.BigDecimal taxMoney) {
		set("tax_money", taxMoney);
	}

	/**
	 * 入账金额<br />
	 * <ul>
	 *   <li>非空：可以为空</li>
	 *   <li>数据类型：decimal</li>
	 * </ul>
	 */
	public java.math.BigDecimal getInMoney() {
		return (java.math.BigDecimal) get("in_money");
	}

	public void setInMoney(java.math.BigDecimal inMoney) {
		set("in_money", inMoney);
	}

	/**
	 * 余额<br />
	 * <ul>
	 *   <li>非空：可以为空</li>
	 *   <li>数据类型：decimal</li>
	 * </ul>
	 */
	public java.math.BigDecimal getBalance() {
		return (java.math.BigDecimal) get("balance");
	}

	public void setBalance(java.math.BigDecimal balance) {
		set("balance", balance);
	}

	/**
	 * 类型<br />
	 * <ul>
	 *   <li>非空：可以为空</li>
	 *   <li>数据类型：varchar(16)</li>
	 * </ul>
	 */
	public String getType() {
		return (String) get("type");
	}

	public void setType(String type) {
		set("type", type);
	}

	/**
	 * 状态<br />
	 * <ul>
	 *   <li>默认值：状态</li>
	 *   <li>非空：可以为空</li>
	 *   <li>数据类型：varchar(16)</li>
	 * </ul>
	 */
	public String getStatus() {
		return (String) get("status");
	}

	public void setStatus(String status) {
		set("status", status);
	}

	/**
	 * 备注<br />
	 * <ul>
	 *   <li>非空：可以为空</li>
	 *   <li>数据类型：varchar(128)</li>
	 * </ul>
	 */
	public String getRemark() {
		return (String) get("remark");
	}

	public void setRemark(String remark) {
		set("remark", remark);
	}

	/**
	 * 插入时间<br />
	 * <ul>
	 *   <li>非空：可以为空</li>
	 *   <li>数据类型：datetime</li>
	 * </ul>
	 */
	public java.util.Date getAddtime() {
		return (java.util.Date) get("addtime");
	}

	public void setAddtime(java.util.Date addtime) {
		set("addtime", addtime);
	}

	/**
	 * 核算完结时间<br />
	 * <ul>
	 *   <li>非空：可以为空</li>
	 *   <li>数据类型：datetime</li>
	 * </ul>
	 */
	public java.util.Date getOvertime() {
		return (java.util.Date) get("overtime");
	}

	public void setOvertime(java.util.Date overtime) {
		set("overtime", overtime);
	}

}