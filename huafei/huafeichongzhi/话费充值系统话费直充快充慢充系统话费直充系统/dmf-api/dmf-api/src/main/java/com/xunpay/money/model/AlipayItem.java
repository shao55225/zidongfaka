package com.xunpay.money.model;

import com.xunpay.money.model.annotation.Table;
import com.jfinal.plugin.activerecord.Model;

/**
 * 
 * 对应表名：alipay_item
 * 表描述：
 * 
 * @author ModelHelper 
 * @version 1.0 
 */
@SuppressWarnings("serial")
@Table(name = "alipay_item")
public class AlipayItem extends Model<AlipayItem> {

	public static final AlipayItem dao = new AlipayItem();


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
	 * 名称<br />
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
	 * 最小金额<br />
	 * <ul>
	 *   <li>默认值：最小金额</li>
	 *   <li>非空：可以为空</li>
	 *   <li>数据类型：decimal</li>
	 * </ul>
	 */
	public java.math.BigDecimal getMinMoney() {
		return (java.math.BigDecimal) get("min_money");
	}

	public void setMinMoney(java.math.BigDecimal minMoney) {
		set("min_money", minMoney);
	}

	/**
	 * 最大金额<br />
	 * <ul>
	 *   <li>默认值：最大金额</li>
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
	 * 当日限额<br />
	 * <ul>
	 *   <li>默认值：当日限额</li>
	 *   <li>非空：可以为空</li>
	 *   <li>数据类型：decimal</li>
	 * </ul>
	 */
	public java.math.BigDecimal getFullMoney() {
		return (java.math.BigDecimal) get("full_money");
	}

	public void setFullMoney(java.math.BigDecimal fullMoney) {
		set("full_money", fullMoney);
	}

	/**
	 * 总计金额<br />
	 * <ul>
	 *   <li>默认值：总计金额</li>
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

	/**
	 * 产码数<br />
	 * <ul>
	 *   <li>默认值：产码数</li>
	 *   <li>非空：可以为空</li>
	 *   <li>数据类型：int</li>
	 * </ul>
	 */
	public Integer getQrNum() {
		return (Integer) get("qr_num");
	}

	public void setQrNum(Integer qrNum) {
		set("qr_num", qrNum);
	}

	/**
	 * 支付数<br />
	 * <ul>
	 *   <li>默认值：支付数</li>
	 *   <li>非空：可以为空</li>
	 *   <li>数据类型：int</li>
	 * </ul>
	 */
	public Integer getPayNum() {
		return (Integer) get("pay_num");
	}

	public void setPayNum(Integer payNum) {
		set("pay_num", payNum);
	}

	/**
	 * 当前未支付笔数<br />
	 * <ul>
	 *   <li>默认值：当前未支付笔数</li>
	 *   <li>非空：可以为空</li>
	 *   <li>数据类型：int</li>
	 * </ul>
	 */
	public Integer getNopayNum() {
		return (Integer) get("nopay_num");
	}

	public void setNopayNum(Integer nopayNum) {
		set("nopay_num", nopayNum);
	}

	/**
	 * 支付宝基本费率<br />
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
	 * 优先级步数<br />
	 * <ul>
	 *   <li>默认值：优先级步数</li>
	 *   <li>非空：可以为空</li>
	 *   <li>数据类型：int</li>
	 * </ul>
	 */
	public Integer getStep() {
		return (Integer) get("step");
	}

	public void setStep(Integer step) {
		set("step", step);
	}

	/**
	 * 优先级<br />
	 * <ul>
	 *   <li>默认值：优先级</li>
	 *   <li>非空：可以为空</li>
	 *   <li>数据类型：int</li>
	 * </ul>
	 */
	public Integer getPri() {
		return (Integer) get("pri");
	}

	public void setPri(Integer pri) {
		set("pri", pri);
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
	 * 账号类型<br />
	 * <ul>
	 *   <li>默认值：账号类型</li>
	 *   <li>非空：可以为空</li>
	 *   <li>数据类型：varchar(32)</li>
	 * </ul>
	 */
	public String getType() {
		return (String) get("type");
	}

	public void setType(String type) {
		set("type", type);
	}

	/**
	 * 备注<br />
	 * <ul>
	 *   <li>非空：可以为空</li>
	 *   <li>数据类型：varchar(256)</li>
	 * </ul>
	 */
	public String getRemark() {
		return (String) get("remark");
	}

	public void setRemark(String remark) {
		set("remark", remark);
	}

	/**
	 * <ul>
	 *   <li>非空：可以为空</li>
	 *   <li>数据类型：varchar(64)</li>
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
	 *   <li>数据类型：varchar(64)</li>
	 * </ul>
	 */
	public String getAppid() {
		return (String) get("appid");
	}

	public void setAppid(String appid) {
		set("appid", appid);
	}

	/**
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
	 * <ul>
	 *   <li>非空：可以为空</li>
	 *   <li>数据类型：varchar(2048)</li>
	 * </ul>
	 */
	public String getRsaAlipay() {
		return (String) get("rsa_alipay");
	}

	public void setRsaAlipay(String rsaAlipay) {
		set("rsa_alipay", rsaAlipay);
	}

	/**
	 * 上传时间<br />
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
	 * 是否无敌号<br />
	 * <ul>
	 *   <li>默认值：是否无敌号</li>
	 *   <li>非空：可以为空</li>
	 *   <li>数据类型：varchar(1)</li>
	 * </ul>
	 */
	public String getIsGood() {
		return (String) get("is_good");
	}

	public void setIsGood(String isGood) {
		set("is_good", isGood);
	}

	/**
	 * 最后产码时间<br />
	 * <ul>
	 *   <li>非空：可以为空</li>
	 *   <li>数据类型：datetime</li>
	 * </ul>
	 */
	public java.util.Date getLasttime() {
		return (java.util.Date) get("lasttime");
	}

	public void setLasttime(java.util.Date lasttime) {
		set("lasttime", lasttime);
	}

}