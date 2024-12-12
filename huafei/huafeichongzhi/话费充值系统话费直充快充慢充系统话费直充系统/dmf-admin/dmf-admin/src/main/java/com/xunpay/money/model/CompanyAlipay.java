package com.xunpay.money.model;

import com.xunpay.money.model.annotation.Table;
import com.jfinal.plugin.activerecord.Model;

/**
 * 
 * 对应表名：company_alipay
 * 表描述：
 * 
 * @author ModelHelper 
 * @version 1.0 
 */
@SuppressWarnings("serial")
@Table(name = "company_alipay")
public class CompanyAlipay extends Model<CompanyAlipay> {

	public static final CompanyAlipay dao = new CompanyAlipay();


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

}