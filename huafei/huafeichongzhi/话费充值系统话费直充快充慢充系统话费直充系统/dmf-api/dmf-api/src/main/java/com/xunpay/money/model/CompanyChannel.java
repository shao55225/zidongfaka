package com.xunpay.money.model;

import com.jfinal.plugin.activerecord.Model;
import com.xunpay.money.model.annotation.Table;

/**
 * 
 * 瀵瑰簲琛ㄥ悕锛歝ompany_channel
 * 琛ㄦ弿杩帮細閫氶亾琛�
 * 
 * @author ModelHelper 
 * @version 1.0 
 */
@SuppressWarnings("serial")
@Table(name = "company_channel")
public class CompanyChannel extends Model<CompanyChannel> {

	public static final CompanyChannel dao = new CompanyChannel();


	/**
	 * 涓婚敭<br />
	 * <ul>
	 *   <li>闈炵┖锛氫笉鑳戒负绌�</li>
	 *   <li>鏁版嵁绫诲瀷锛歩nt</li>
	 *   <li>閿害鏉燂細PRI</li>
	 *   <li>鎵╁睍锛氳嚜鍔ㄥ闀垮垪</li>
	 * </ul>
	 */
	public Integer getId() {
		return (Integer) get("id");
	}

	public void setId(Integer id) {
		set("id", id);
	}

	/**
	 * 閫氶亾鏄电О<br />
	 * <ul>
	 *   <li>闈炵┖锛氬彲浠ヤ负绌�</li>
	 *   <li>鏁版嵁绫诲瀷锛歷archar(32)</li>
	 * </ul>
	 */
	public String getNickname() {
		return (String) get("nickname");
	}

	public void setNickname(String nickname) {
		set("nickname", nickname);
	}

	/**
	 * 鐢ㄦ埛鍚�<br />
	 * <ul>
	 *   <li>闈炵┖锛氬彲浠ヤ负绌�</li>
	 *   <li>鏁版嵁绫诲瀷锛歷archar(32)</li>
	 *   <li>閿害鏉燂細UNI</li>
	 * </ul>
	 */
	public String getName() {
		return (String) get("name");
	}

	public void setName(String name) {
		set("name", name);
	}


	/**
	 * 鐘舵��<br />
	 * <ul>
	 *   <li>榛樿鍊硷細鐘舵��</li>
	 *   <li>闈炵┖锛氬彲浠ヤ负绌�</li>
	 *   <li>鏁版嵁绫诲瀷锛歷archar(8)</li>
	 * </ul>
	 */
	public String getStatus() {
		return (String) get("status");
	}

	public void setStatus(String status) {
		set("status", status);
	}
	
	/**
	 * 閫氶亾浠ｇ爜 <br />
	 * <ul>
	 *   <li>榛樿鍊硷細鐘舵��</li>
	 *   <li>闈炵┖锛氬彲浠ヤ负绌�</li>
	 *   <li>鏁版嵁绫诲瀷锛歷archar(8)</li>
	 * </ul>
	 */
	public String getCode() {
		return (String) get("code");
	}
	
	public void setCode(String code) {
		set("code", code);
	}
	
	
	/**
	 * 璐圭巼<br />
	 * <ul>
	 *   <li>榛樿鍊硷細鐘舵��</li>
	 *   <li>闈炵┖锛氬彲浠ヤ负绌�</li>
	 *   <li>鏁版嵁绫诲瀷锛歜igdecimal(8)</li>
	 * </ul>
	 */
	public java.math.BigDecimal getWechatRebate() {
		return (java.math.BigDecimal) get("wechatrebate");
	}

	public void setWechatRebate(java.math.BigDecimal wechatrebate) {
		set("wechatrebate", wechatrebate);
	}

	/**
	 * 鏀粯瀹濊垂鐜�<br />
	 * <ul>
	 *   <li>榛樿鍊硷細鐘舵��</li>
	 *   <li>闈炵┖锛氬彲浠ヤ负绌�</li>
	 *   <li>鏁版嵁绫诲瀷锛歜igdecimal(8)</li>
	 * </ul>
	 */
	public java.math.BigDecimal getAlipayRebate() {
		return (java.math.BigDecimal) get("alipayrebate");
	}
	
	public void setAlipayRebate(java.math.BigDecimal alipayrebate) {
		set("alipayrebate", alipayrebate);
	}
	
	/**
	 * 缃戝叧璐圭巼<br />
	 * <ul>
	 *   <li>榛樿鍊硷細鐘舵��</li>
	 *   <li>闈炵┖锛氬彲浠ヤ负绌�</li>
	 *   <li>鏁版嵁绫诲瀷锛歜igdecimal(8)</li>
	 * </ul>
	 */
	public java.math.BigDecimal getGateWayRebate() {
		return (java.math.BigDecimal) get("gatewayrebate");
	}
	
	public void setGateWayRebate(java.math.BigDecimal gatewayrebate) {
		set("gatewayrebate", gatewayrebate);
	}
	
	/**
	 * 蹇嵎璐圭巼 浠ｄ粯璐圭巼<br />
	 * <ul>
	 *   <li>榛樿鍊硷細鐘舵��</li>
	 *   <li>闈炵┖锛氬彲浠ヤ负绌�</li>
	 *   <li>鏁版嵁绫诲瀷锛歜igdecimal(8)</li>
	 * </ul>
	 */
	public java.math.BigDecimal getFastRebate() {
		return (java.math.BigDecimal) get("fastrebate");
	}
	
	public void setFastRebate(java.math.BigDecimal fastrebate) {
		set("fastrebate", fastrebate);
	}

	/**
	 * 鍟嗘埛id<br />
	 * <ul>
	 *   <li>闈炵┖锛氬彲浠ヤ负绌�</li>
	 *   <li>鏁版嵁绫诲瀷锛歷archar(32)</li>
	 * </ul>
	 */
	public String getSid() {
		return (String) get("sid");
	}

	public void setSid(String sid) {
		set("sid", sid);
	}
	
	/**
	 * 鍟嗘埛key<br />
	 * <ul>
	 *   <li>闈炵┖锛氬彲浠ヤ负绌�</li>
	 *   <li>鏁版嵁绫诲瀷锛歷archar(32)</li>
	 * </ul>
	 */
	public String getSkey() {
		return (String) get("skey");
	}
	
	public void setSkey(String skey) {
		set("skey", skey);
	}
	
	
	/**
	 * 鍟嗘埛url<br />
	 * <ul>
	 *   <li>闈炵┖锛氬彲浠ヤ负绌�</li>
	 *   <li>鏁版嵁绫诲瀷锛歷archar(32)</li>
	 * </ul>
	 */
	public String getSurl() {
		return (String) get("surl");
	}
	
	public void setSurl(String surl) {
		set("surl", surl);
	}
	
	
	/**
	 * 娣诲姞鏃堕棿<br />
	 * <ul>
	 *   <li>闈炵┖锛氬彲浠ヤ负绌�</li>
	 *   <li>鏁版嵁绫诲瀷锛歞atetime</li>
	 * </ul>
	 */
	public java.util.Date getAddtime() {
		return (java.util.Date) get("addtime");
	}

	public void setAddtime(java.util.Date addtime) {
		set("addtime", addtime);
	}

	

}