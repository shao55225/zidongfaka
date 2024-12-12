package com.xunpay.money.model;

import com.xunpay.money.model.annotation.Table;
import com.jfinal.plugin.activerecord.Model;

/**
 * 
 * 对应表名：company_settle_item
 * 表描述：
 * 
 * @author ModelHelper 
 * @version 1.0 
 */
@SuppressWarnings("serial")
@Table(name = "company_settle_item")
public class CompanySettleItem extends Model<CompanySettleItem> {

	public static final CompanySettleItem dao = new CompanySettleItem();


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
	 * 姓名<br />
	 * <ul>
	 *   <li>非空：可以为空</li>
	 *   <li>数据类型：varchar(16)</li>
	 * </ul>
	 */
	public String getAlipayName() {
		return (String) get("alipay_name");
	}

	public void setAlipayName(String alipayName) {
		set("alipay_name", alipayName);
	}

	/**
	 * 支付宝账号<br />
	 * <ul>
	 *   <li>非空：可以为空</li>
	 *   <li>数据类型：varchar(32)</li>
	 * </ul>
	 */
	public String getAlipayAccount() {
		return (String) get("alipay_account");
	}

	public void setAlipayAccount(String alipayAccount) {
		set("alipay_account", alipayAccount);
	}

	/**
	 * pid<br />
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
	 * 分账次数<br />
	 * <ul>
	 *   <li>非空：可以为空</li>
	 *   <li>数据类型：int</li>
	 * </ul>
	 */
	public Integer getSettleNum() {
		return (Integer) get("settle_num");
	}

	public void setSettleNum(Integer settleNum) {
		set("settle_num", settleNum);
	}

	/**
	 * 今日收款<br />
	 * <ul>
	 *   <li>默认值：今日收款</li>
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
	 * 阈值<br />
	 * <ul>
	 *   <li>默认值：阈值</li>
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
	 * 总计金额<br />
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

}