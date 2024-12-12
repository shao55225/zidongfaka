package com.xunpay.money.model;

import com.xunpay.money.model.annotation.Table;
import com.jfinal.plugin.activerecord.Model;

/**
 * 
 * 对应表名：alipay_agent
 * 表描述：
 * 
 * @author ModelHelper 
 * @version 1.0 
 */
@SuppressWarnings("serial")
@Table(name = "alipay_agent")
public class AlipayAgent extends Model<AlipayAgent> {

	public static final AlipayAgent dao = new AlipayAgent();


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
	 * 昵称<br />
	 * <ul>
	 *   <li>非空：可以为空</li>
	 *   <li>数据类型：varchar(32)</li>
	 * </ul>
	 */
	public String getNickname() {
		return (String) get("nickname");
	}

	public void setNickname(String nickname) {
		set("nickname", nickname);
	}

	/**
	 * 用户名<br />
	 * <ul>
	 *   <li>非空：可以为空</li>
	 *   <li>数据类型：varchar(32)</li>
	 *   <li>键约束：UNI</li>
	 * </ul>
	 */
	public String getUsername() {
		return (String) get("username");
	}

	public void setUsername(String username) {
		set("username", username);
	}

	/**
	 * 密码<br />
	 * <ul>
	 *   <li>非空：可以为空</li>
	 *   <li>数据类型：varchar(32)</li>
	 * </ul>
	 */
	public String getPassword() {
		return (String) get("password");
	}

	public void setPassword(String password) {
		set("password", password);
	}

	/**
	 * 支付密码<br />
	 * <ul>
	 *   <li>非空：可以为空</li>
	 *   <li>数据类型：varchar(32)</li>
	 * </ul>
	 */
	public String getPayPassword() {
		return (String) get("pay_password");
	}

	public void setPayPassword(String payPassword) {
		set("pay_password", payPassword);
	}

	/**
	 * 费率<br />
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
	 * 用户余额<br />
	 * <ul>
	 *   <li>默认值：用户余额</li>
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
	 * 添加时间<br />
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
	 * 登陆时间<br />
	 * <ul>
	 *   <li>非空：可以为空</li>
	 *   <li>数据类型：datetime</li>
	 * </ul>
	 */
	public java.util.Date getLogintime() {
		return (java.util.Date) get("logintime");
	}

	public void setLogintime(java.util.Date logintime) {
		set("logintime", logintime);
	}

	/**
	 * 登陆IP<br />
	 * <ul>
	 *   <li>非空：可以为空</li>
	 *   <li>数据类型：varchar(32)</li>
	 * </ul>
	 */
	public String getLoginip() {
		return (String) get("loginip");
	}

	public void setLoginip(String loginip) {
		set("loginip", loginip);
	}

	/**
	 * 状态<br />
	 * <ul>
	 *   <li>默认值：状态</li>
	 *   <li>非空：可以为空</li>
	 *   <li>数据类型：varchar(8)</li>
	 * </ul>
	 */
	public String getStatus() {
		return (String) get("status");
	}

	public void setStatus(String status) {
		set("status", status);
	}

	/**
	 * 上级id<br />
	 * <ul>
	 *   <li>非空：可以为空</li>
	 *   <li>数据类型：int</li>
	 * </ul>
	 */
	public Integer getPid() {
		return (Integer) get("pid");
	}

	public void setPid(Integer pid) {
		set("pid", pid);
	}

	/**
	 * 是否已经删除<br />
	 * <ul>
	 *   <li>默认值：是否已经删除</li>
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

}