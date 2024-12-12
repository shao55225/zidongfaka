package com.xunpay.money.model;

import com.xunpay.money.model.annotation.Table;
import com.jfinal.plugin.activerecord.Model;

/**
 * 
 * 对应表名：company_order_settle
 * 表描述：
 * 
 * @author ModelHelper 
 * @version 1.0 
 */
@SuppressWarnings("serial")
@Table(name = "company_order_settle")
public class CompanyOrderSettle extends Model<CompanyOrderSettle> {

	public static final CompanyOrderSettle dao = new CompanyOrderSettle();


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
	 * itemid<br />
	 * <ul>
	 *   <li>非空：可以为空</li>
	 *   <li>数据类型：int</li>
	 * </ul>
	 */
	public Integer getItemId() {
		return (Integer) get("item_id");
	}

	public void setItemId(Integer itemId) {
		set("item_id", itemId);
	}

	/**
	 * 订单id<br />
	 * <ul>
	 *   <li>非空：可以为空</li>
	 *   <li>数据类型：int</li>
	 *   <li>键约束：MUL</li>
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
	 * 商户id<br />
	 * <ul>
	 *   <li>默认值：商户id</li>
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
	 * 码商id<br />
	 * <ul>
	 *   <li>默认值：码商id</li>
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
	 * 出账pid<br />
	 * <ul>
	 *   <li>非空：可以为空</li>
	 *   <li>数据类型：varchar(32)</li>
	 * </ul>
	 */
	public String getTransOut() {
		return (String) get("trans_out");
	}

	public void setTransOut(String transOut) {
		set("trans_out", transOut);
	}

	/**
	 * 入账pid<br />
	 * <ul>
	 *   <li>非空：可以为空</li>
	 *   <li>数据类型：varchar(32)</li>
	 * </ul>
	 */
	public String getTransIn() {
		return (String) get("trans_in");
	}

	public void setTransIn(String transIn) {
		set("trans_in", transIn);
	}

	/**
	 * 入账名称<br />
	 * <ul>
	 *   <li>非空：可以为空</li>
	 *   <li>数据类型：varchar(32)</li>
	 * </ul>
	 */
	public String getTransInAccount() {
		return (String) get("trans_in_account");
	}

	public void setTransInAccount(String transInAccount) {
		set("trans_in_account", transInAccount);
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
	 * 支付宝分账是否成功<br />
	 * <ul>
	 *   <li>非空：可以为空</li>
	 *   <li>数据类型：varchar(1)</li>
	 * </ul>
	 */
	public String getAlipaySuccess() {
		return (String) get("alipay_success");
	}

	public void setAlipaySuccess(String alipaySuccess) {
		set("alipay_success", alipaySuccess);
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
	 * 交易号<br />
	 * <ul>
	 *   <li>非空：可以为空</li>
	 *   <li>数据类型：varchar(64)</li>
	 * </ul>
	 */
	public String getTradeNo() {
		return (String) get("trade_no");
	}

	public void setTradeNo(String tradeNo) {
		set("trade_no", tradeNo);
	}

	/**
	 * 分账金额<br />
	 * <ul>
	 *   <li>非空：可以为空</li>
	 *   <li>数据类型：decimal</li>
	 * </ul>
	 */
	public java.math.BigDecimal getSettleMoney() {
		return (java.math.BigDecimal) get("settle_money");
	}

	public void setSettleMoney(java.math.BigDecimal settleMoney) {
		set("settle_money", settleMoney);
	}

}