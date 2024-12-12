package com.xunpay.money.model;

import com.xunpay.money.model.annotation.Table;
import com.jfinal.plugin.activerecord.Model;

/**
 * 
 * 对应表名：company_draw
 * 表描述：
 * 
 * @author ModelHelper 
 * @version 1.0 
 */
@SuppressWarnings("serial")
@Table(name = "company_draw")
public class CompanyDraw extends Model<CompanyDraw> {

	public static final CompanyDraw dao = new CompanyDraw();


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
	 * 代理名称<br />
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
	 * 提现金额<br />
	 * <ul>
	 *   <li>非空：可以为空</li>
	 *   <li>数据类型：decimal</li>
	 * </ul>
	 */
	public java.math.BigDecimal getDrawMoney() {
		return (java.math.BigDecimal) get("draw_money");
	}

	public void setDrawMoney(java.math.BigDecimal drawMoney) {
		set("draw_money", drawMoney);
	}

	/**
	 * 手续费<br />
	 * <ul>
	 *   <li>非空：可以为空</li>
	 *   <li>数据类型：decimal</li>
	 * </ul>
	 */
	public java.math.BigDecimal getChargeMoney() {
		return (java.math.BigDecimal) get("charge_money");
	}

	public void setChargeMoney(java.math.BigDecimal chargeMoney) {
		set("charge_money", chargeMoney);
	}

	/**
	 * 提现方式<br />
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
	 * 名称<br />
	 * <ul>
	 *   <li>非空：可以为空</li>
	 *   <li>数据类型：varchar(64)</li>
	 * </ul>
	 */
	public String getRealname() {
		return (String) get("realname");
	}

	public void setRealname(String realname) {
		set("realname", realname);
	}

	/**
	 * 提现账号<br />
	 * <ul>
	 *   <li>非空：可以为空</li>
	 *   <li>数据类型：varchar(64)</li>
	 * </ul>
	 */
	public String getAccount() {
		return (String) get("account");
	}

	public void setAccount(String account) {
		set("account", account);
	}

	/**
	 * 开户行<br />
	 * <ul>
	 *   <li>非空：可以为空</li>
	 *   <li>数据类型：varchar(128)</li>
	 * </ul>
	 */
	public String getOpenBank() {
		return (String) get("open_bank");
	}

	public void setOpenBank(String openBank) {
		set("open_bank", openBank);
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
	 * 申请时间<br />
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
	 * 转账时间<br />
	 * <ul>
	 *   <li>非空：可以为空</li>
	 *   <li>数据类型：datetime</li>
	 * </ul>
	 */
	public java.util.Date getDrawtime() {
		return (java.util.Date) get("drawtime");
	}

	public void setDrawtime(java.util.Date drawtime) {
		set("drawtime", drawtime);
	}

	/**
	 * 系统操作用户<br />
	 * <ul>
	 *   <li>非空：可以为空</li>
	 *   <li>数据类型：varchar(32)</li>
	 * </ul>
	 */
	public String getSysUsername() {
		return (String) get("sys_username");
	}

	public void setSysUsername(String sysUsername) {
		set("sys_username", sysUsername);
	}

}