package com.xunpay.money.model;

import com.jfinal.plugin.activerecord.Model;
import com.xunpay.money.model.annotation.Table;

/**
 * 
 * 对应表名：company_channel
 * 表描述：通道表
 * 
 * @author ModelHelper 
 * @version 1.0 
 */
@SuppressWarnings("serial")
@Table(name = "company_channel")
public class CompanyChannel extends Model<CompanyChannel> {

	public static final CompanyChannel dao = new CompanyChannel();


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
	 * 通道昵称<br />
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
	public String getName() {
		return (String) get("name");
	}

	public void setName(String name) {
		set("name", name);
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
	 * 通道代码 <br />
	 * <ul>
	 *   <li>默认值：状态</li>
	 *   <li>非空：可以为空</li>
	 *   <li>数据类型：varchar(8)</li>
	 * </ul>
	 */
	public String getCode() {
		return (String) get("code");
	}
	
	public void setCode(String code) {
		set("code", code);
	}
	
	/**
	 * 通道类型<br />
	 * <ul>
	 *   <li>默认值：状态</li>
	 *   <li>非空：可以为空</li>
	 *   <li>数据类型：varchar(8)</li>
	 * </ul>
	 */
	public String getType() {
		return (String) get("type");
	}
	
	public void setType(String type) {
		set("type", type);
	}
	
	
	/**
	 * 费率<br />
	 * <ul>
	 *   <li>默认值：状态</li>
	 *   <li>非空：可以为空</li>
	 *   <li>数据类型：bigdecimal(8)</li>
	 * </ul>
	 */
	public java.math.BigDecimal getWechatRebate() {
		return (java.math.BigDecimal) get("wechatrebate");
	}

	public void setWechatRebate(java.math.BigDecimal wechatrebate) {
		set("wechatrebate", wechatrebate);
	}

	/**
	 * 支付宝费率<br />
	 * <ul>
	 *   <li>默认值：状态</li>
	 *   <li>非空：可以为空</li>
	 *   <li>数据类型：bigdecimal(8)</li>
	 * </ul>
	 */
	public java.math.BigDecimal getAlipayRebate() {
		return (java.math.BigDecimal) get("alipayrebate");
	}
	
	public void setAlipayRebate(java.math.BigDecimal alipayrebate) {
		set("alipayrebate", alipayrebate);
	}
	
	/**
	 * 网关费率<br />
	 * <ul>
	 *   <li>默认值：状态</li>
	 *   <li>非空：可以为空</li>
	 *   <li>数据类型：bigdecimal(8)</li>
	 * </ul>
	 */
	public java.math.BigDecimal getGateWayRebate() {
		return (java.math.BigDecimal) get("gatewayrebate");
	}
	
	public void setGateWayRebate(java.math.BigDecimal gatewayrebate) {
		set("gatewayrebate", gatewayrebate);
	}
	
	/**
	 * 快捷费率 代付费率<br />
	 * <ul>
	 *   <li>默认值：状态</li>
	 *   <li>非空：可以为空</li>
	 *   <li>数据类型：bigdecimal(8)</li>
	 * </ul>
	 */
	public java.math.BigDecimal getFastRebate() {
		return (java.math.BigDecimal) get("fastrebate");
	}
	
	public void setFastRebate(java.math.BigDecimal fastrebate) {
		set("fastrebate", fastrebate);
	}

	/**
	 * 商户id<br />
	 * <ul>
	 *   <li>非空：可以为空</li>
	 *   <li>数据类型：varchar(32)</li>
	 * </ul>
	 */
	public String getSid() {
		return (String) get("sid");
	}

	public void setSid(String sid) {
		set("sid", sid);
	}
	
	/**
	 * 商户key<br />
	 * <ul>
	 *   <li>非空：可以为空</li>
	 *   <li>数据类型：varchar(32)</li>
	 * </ul>
	 */
	public String getSkey() {
		return (String) get("skey");
	}
	
	public void setSkey(String skey) {
		set("skey", skey);
	}
	
	
	/**
	 * 商户url<br />
	 * <ul>
	 *   <li>非空：可以为空</li>
	 *   <li>数据类型：varchar(32)</li>
	 * </ul>
	 */
	public String getSurl() {
		return (String) get("surl");
	}
	
	public void setSurl(String surl) {
		set("surl", surl);
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

	

}