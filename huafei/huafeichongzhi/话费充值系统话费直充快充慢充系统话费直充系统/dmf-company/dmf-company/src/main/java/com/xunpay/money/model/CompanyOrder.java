package com.xunpay.money.model;

import com.xunpay.money.model.annotation.Table;
import com.jfinal.plugin.activerecord.Model;

/**
 * 
 * 对应表名：company_order
 * 表描述：
 * 
 * @author ModelHelper 
 * @version 1.0 
 */
@SuppressWarnings("serial")
@Table(name = "company_order")
public class CompanyOrder extends Model<CompanyOrder> {

	public static final CompanyOrder dao = new CompanyOrder();


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
	 * 订单标题<br />
	 * <ul>
	 *   <li>非空：可以为空</li>
	 *   <li>数据类型：varchar(64)</li>
	 * </ul>
	 */
	public String getTitle() {
		return (String) get("title");
	}

	public void setTitle(String title) {
		set("title", title);
	}

	/**
	 * 订单号<br />
	 * <ul>
	 *   <li>非空：可以为空</li>
	 *   <li>数据类型：varchar(64)</li>
	 *   <li>键约束：MUL</li>
	 * </ul>
	 */
	public String getOrderNo() {
		return (String) get("order_no");
	}

	public void setOrderNo(String orderNo) {
		set("order_no", orderNo);
	}

	/**
	 * 外部订单<br />
	 * <ul>
	 *   <li>非空：可以为空</li>
	 *   <li>数据类型：varchar(64)</li>
	 * </ul>
	 */
	public String getOutOrderNo() {
		return (String) get("out_order_no");
	}

	public void setOutOrderNo(String outOrderNo) {
		set("out_order_no", outOrderNo);
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
	 * 商户id<br />
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
	 * 商户名称<br />
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
	 * 支付宝费率<br />
	 * <ul>
	 *   <li>非空：可以为空</li>
	 *   <li>数据类型：decimal</li>
	 * </ul>
	 */
	public java.math.BigDecimal getAlipayRebate() {
		return (java.math.BigDecimal) get("alipay_rebate");
	}

	public void setAlipayRebate(java.math.BigDecimal alipayRebate) {
		set("alipay_rebate", alipayRebate);
	}

	/**
	 * 支付类型<br />
	 * <ul>
	 *   <li>非空：可以为空</li>
	 *   <li>数据类型：varchar(16)</li>
	 * </ul>
	 */
	public String getPayType() {
		return (String) get("pay_type");
	}

	public void setPayType(String payType) {
		set("pay_type", payType);
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
	 * 代理名称<br />
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
	 * 自有支付宝id<br />
	 * <ul>
	 *   <li>非空：可以为空</li>
	 *   <li>数据类型：int</li>
	 * </ul>
	 */
	public Integer getSelfId() {
		return (Integer) get("self_id");
	}

	public void setSelfId(Integer selfId) {
		set("self_id", selfId);
	}

	/**
	 * 自有支付宝名称<br />
	 * <ul>
	 *   <li>非空：可以为空</li>
	 *   <li>数据类型：varchar(32)</li>
	 * </ul>
	 */
	public String getSelfName() {
		return (String) get("self_name");
	}

	public void setSelfName(String selfName) {
		set("self_name", selfName);
	}

	/**
	 * 二维码地址<br />
	 * <ul>
	 *   <li>非空：可以为空</li>
	 *   <li>数据类型：varchar(512)</li>
	 * </ul>
	 */
	public String getQrUrl() {
		return (String) get("qr_url");
	}

	public void setQrUrl(String qrUrl) {
		set("qr_url", qrUrl);
	}

	/**
	 * 回调地址<br />
	 * <ul>
	 *   <li>非空：可以为空</li>
	 *   <li>数据类型：varchar(248)</li>
	 * </ul>
	 */
	public String getNoticeUrl() {
		return (String) get("notice_url");
	}

	public void setNoticeUrl(String noticeUrl) {
		set("notice_url", noticeUrl);
	}

	/**
	 * 前台跳转地址<br />
	 * <ul>
	 *   <li>非空：可以为空</li>
	 *   <li>数据类型：varchar(248)</li>
	 * </ul>
	 */
	public String getReturnUrl() {
		return (String) get("return_url");
	}

	public void setReturnUrl(String returnUrl) {
		set("return_url", returnUrl);
	}

	/**
	 * 商户费率<br />
	 * <ul>
	 *   <li>非空：可以为空</li>
	 *   <li>数据类型：decimal</li>
	 * </ul>
	 */
	public java.math.BigDecimal getCompanyRebate() {
		return (java.math.BigDecimal) get("company_rebate");
	}

	public void setCompanyRebate(java.math.BigDecimal companyRebate) {
		set("company_rebate", companyRebate);
	}

	/**
	 * 商户服务费<br />
	 * <ul>
	 *   <li>非空：可以为空</li>
	 *   <li>数据类型：decimal</li>
	 * </ul>
	 */
	public java.math.BigDecimal getCompanyRebateMoney() {
		return (java.math.BigDecimal) get("company_rebate_money");
	}

	public void setCompanyRebateMoney(java.math.BigDecimal companyRebateMoney) {
		set("company_rebate_money", companyRebateMoney);
	}

	/**
	 * 码商费率<br />
	 * <ul>
	 *   <li>非空：可以为空</li>
	 *   <li>数据类型：decimal</li>
	 * </ul>
	 */
	public java.math.BigDecimal getAgentRebate() {
		return (java.math.BigDecimal) get("agent_rebate");
	}

	public void setAgentRebate(java.math.BigDecimal agentRebate) {
		set("agent_rebate", agentRebate);
	}

	/**
	 * 码商返点金额<br />
	 * <ul>
	 *   <li>非空：可以为空</li>
	 *   <li>数据类型：decimal</li>
	 * </ul>
	 */
	public java.math.BigDecimal getAgentRebateMoney() {
		return (java.math.BigDecimal) get("agent_rebate_money");
	}

	public void setAgentRebateMoney(java.math.BigDecimal agentRebateMoney) {
		set("agent_rebate_money", agentRebateMoney);
	}

	/**
	 * 支付状态<br />
	 * <ul>
	 *   <li>默认值：支付状态</li>
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
	 * 回调状态<br />
	 * <ul>
	 *   <li>默认值：回调状态</li>
	 *   <li>非空：可以为空</li>
	 *   <li>数据类型：varchar(16)</li>
	 * </ul>
	 */
	public String getNotice() {
		return (String) get("notice");
	}

	public void setNotice(String notice) {
		set("notice", notice);
	}

	/**
	 * 分账状态<br />
	 * <ul>
	 *   <li>默认值：分账状态</li>
	 *   <li>非空：可以为空</li>
	 *   <li>数据类型：varchar(16)</li>
	 * </ul>
	 */
	public String getSettle() {
		return (String) get("settle");
	}

	public void setSettle(String settle) {
		set("settle", settle);
	}

	/**
	 * 系统账单结算状态<br />
	 * <ul>
	 *   <li>默认值：系统账单结算状态</li>
	 *   <li>非空：可以为空</li>
	 *   <li>数据类型：varchar(1)</li>
	 * </ul>
	 */
	public String getIsBill() {
		return (String) get("is_bill");
	}

	public void setIsBill(String isBill) {
		set("is_bill", isBill);
	}

	/**
	 * 下单时间<br />
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
	 * 支付时间<br />
	 * <ul>
	 *   <li>非空：可以为空</li>
	 *   <li>数据类型：varchar(20)</li>
	 * </ul>
	 */
	public String getPaytime() {
		return (String) get("paytime");
	}

	public void setPaytime(String paytime) {
		set("paytime", paytime);
	}

	/**
	 * <ul>
	 *   <li>非空：可以为空</li>
	 *   <li>数据类型：datetime</li>
	 * </ul>
	 */
	public java.util.Date getSettletime() {
		return (java.util.Date) get("settletime");
	}

	public void setSettletime(java.util.Date settletime) {
		set("settletime", settletime);
	}

	/**
	 * <ul>
	 *   <li>非空：可以为空</li>
	 *   <li>数据类型：text(65535)</li>
	 * </ul>
	 */
	public String getSettleresult() {
		return (String) get("settleresult");
	}

	public void setSettleresult(String settleresult) {
		set("settleresult", settleresult);
	}

	/**
	 * 回调时间<br />
	 * <ul>
	 *   <li>非空：可以为空</li>
	 *   <li>数据类型：datetime</li>
	 * </ul>
	 */
	public java.util.Date getNoticetime() {
		return (java.util.Date) get("noticetime");
	}

	public void setNoticetime(java.util.Date noticetime) {
		set("noticetime", noticetime);
	}

	/**
	 * 回调返回<br />
	 * <ul>
	 *   <li>非空：可以为空</li>
	 *   <li>数据类型：text(65535)</li>
	 * </ul>
	 */
	public String getNoticeresult() {
		return (String) get("noticeresult");
	}

	public void setNoticeresult(String noticeresult) {
		set("noticeresult", noticeresult);
	}

	/**
	 * 支付宝pid<br />
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
	 * appid<br />
	 * <ul>
	 *   <li>非空：可以为空</li>
	 *   <li>数据类型：varchar(32)</li>
	 * </ul>
	 */
	public String getAppid() {
		return (String) get("appid");
	}

	public void setAppid(String appid) {
		set("appid", appid);
	}

	/**
	 * 私钥<br />
	 * <ul>
	 *   <li>非空：可以为空</li>
	 *   <li>数据类型：varchar(2048)</li>
	 * </ul>
	 */
	public String getRsaPrivate() {
		return (String) get("rsa_private");
	}

	public void setRsaPrivate(String rsaPrivate) {
		set("rsa_private", rsaPrivate);
	}

	/**
	 * 支付宝公钥<br />
	 * <ul>
	 *   <li>非空：可以为空</li>
	 *   <li>数据类型：varchar(1024)</li>
	 * </ul>
	 */
	public String getRsaAlipay() {
		return (String) get("rsa_alipay");
	}

	public void setRsaAlipay(String rsaAlipay) {
		set("rsa_alipay", rsaAlipay);
	}

	/**
	 * 是否已删除<br />
	 * <ul>
	 *   <li>默认值：是否已删除</li>
	 *   <li>非空：可以为空</li>
	 *   <li>数据类型：varchar(1)</li>
	 * </ul>
	 */
	public String getIsDel() {
		return (String) get("is_del");
	}

	public void setIsDel(String isDel) {
		set("is_del", isDel);
	}

	/**
	 * 备注信息<br />
	 * <ul>
	 *   <li>非空：可以为空</li>
	 *   <li>数据类型：text(65535)</li>
	 * </ul>
	 */
	public String getRemark() {
		return (String) get("remark");
	}

	public void setRemark(String remark) {
		set("remark", remark);
	}

	/**
	 * 令牌<br />
	 * <ul>
	 *   <li>非空：可以为空</li>
	 *   <li>数据类型：varchar(36)</li>
	 *   <li>键约束：MUL</li>
	 * </ul>
	 */
	public String getToken() {
		return (String) get("token");
	}

	public void setToken(String token) {
		set("token", token);
	}

	/**
	 * 付款人支付宝账号<br />
	 * <ul>
	 *   <li>非空：可以为空</li>
	 *   <li>数据类型：varchar(32)</li>
	 * </ul>
	 */
	public String getBuyerLogonId() {
		return (String) get("buyer_logon_id");
	}

	public void setBuyerLogonId(String buyerLogonId) {
		set("buyer_logon_id", buyerLogonId);
	}

	/**
	 * 付款人pid<br />
	 * <ul>
	 *   <li>非空：可以为空</li>
	 *   <li>数据类型：varchar(32)</li>
	 * </ul>
	 */
	public String getBuyerId() {
		return (String) get("buyer_id");
	}

	public void setBuyerId(String buyerId) {
		set("buyer_id", buyerId);
	}

}