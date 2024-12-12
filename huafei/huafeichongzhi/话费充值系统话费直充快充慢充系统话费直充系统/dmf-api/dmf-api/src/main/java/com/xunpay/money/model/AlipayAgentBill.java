package com.xunpay.money.model;

import com.xunpay.money.model.annotation.Table;
import com.jfinal.plugin.activerecord.Model;

/**
 * 
 * 对应表名：alipay_agent_bill
 * 表描述：
 * 
 * @author ModelHelper 
 * @version 1.0 
 */
@SuppressWarnings("serial")
@Table(name = "alipay_agent_bill")
public class AlipayAgentBill extends Model<AlipayAgentBill> {

	public static final AlipayAgentBill dao = new AlipayAgentBill();


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
	public Integer getAgentId() {
		return (Integer) get("agent_id");
	}

	public void setAgentId(Integer agentId) {
		set("agent_id", agentId);
	}

	/**
	 * 代理名<br />
	 * <ul>
	 *   <li>非空：可以为空</li>
	 *   <li>数据类型：varchar(32)</li>
	 * </ul>
	 */
	public String getAgentName() {
		return (String) get("agent_name");
	}

	public void setAgentName(String agentName) {
		set("agent_name", agentName);
	}

	/**
	 * 支付宝id<br />
	 * <ul>
	 *   <li>非空：可以为空</li>
	 *   <li>数据类型：int</li>
	 * </ul>
	 */
	public Integer getAlipayId() {
		return (Integer) get("alipay_id");
	}

	public void setAlipayId(Integer alipayId) {
		set("alipay_id", alipayId);
	}

	/**
	 * 支付宝名称<br />
	 * <ul>
	 *   <li>非空：可以为空</li>
	 *   <li>数据类型：varchar(32)</li>
	 * </ul>
	 */
	public String getAlipayName() {
		return (String) get("alipay_name");
	}

	public void setAlipayName(String alipayName) {
		set("alipay_name", alipayName);
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
	 *   <li>数据类型：varchar(64)</li>
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
	 * 返点<br />
	 * <ul>
	 *   <li>非空：可以为空</li>
	 *   <li>数据类型：decimal</li>
	 * </ul>
	 */
	public java.math.BigDecimal getRebate() {
		return (java.math.BigDecimal) get("rebate");
	}

	public void setRebate(java.math.BigDecimal rebate) {
		set("rebate", rebate);
	}

	/**
	 * 返点金额<br />
	 * <ul>
	 *   <li>非空：可以为空</li>
	 *   <li>数据类型：decimal</li>
	 * </ul>
	 */
	public java.math.BigDecimal getRebateMoney() {
		return (java.math.BigDecimal) get("rebate_money");
	}

	public void setRebateMoney(java.math.BigDecimal rebateMoney) {
		set("rebate_money", rebateMoney);
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

}