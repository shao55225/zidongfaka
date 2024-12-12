package com.xunpay.money.model;

import com.xunpay.money.model.annotation.Table;
import com.jfinal.plugin.activerecord.Model;

/**
 * 
 * 对应表名：company_order_notice
 * 表描述：
 * 
 * @author ModelHelper 
 * @version 1.0 
 */
@SuppressWarnings("serial")
@Table(name = "company_order_notice")
public class CompanyOrderNotice extends Model<CompanyOrderNotice> {

	public static final CompanyOrderNotice dao = new CompanyOrderNotice();


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
	 * 外部订单号<br />
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
	 * 通知地址<br />
	 * <ul>
	 *   <li>非空：可以为空</li>
	 *   <li>数据类型：varchar(512)</li>
	 * </ul>
	 */
	public String getNoticeUrl() {
		return (String) get("notice_url");
	}

	public void setNoticeUrl(String noticeUrl) {
		set("notice_url", noticeUrl);
	}

	/**
	 * 通知时间<br />
	 * <ul>
	 *   <li>非空：可以为空</li>
	 *   <li>数据类型：datetime</li>
	 * </ul>
	 */
	public java.util.Date getNoticeTime() {
		return (java.util.Date) get("notice_time");
	}

	public void setNoticeTime(java.util.Date noticeTime) {
		set("notice_time", noticeTime);
	}

	/**
	 * 返回结果<br />
	 * <ul>
	 *   <li>非空：可以为空</li>
	 *   <li>数据类型：text(65535)</li>
	 * </ul>
	 */
	public String getNoticeResult() {
		return (String) get("notice_result");
	}

	public void setNoticeResult(String noticeResult) {
		set("notice_result", noticeResult);
	}

	/**
	 * 回调次数<br />
	 * <ul>
	 *   <li>非空：可以为空</li>
	 *   <li>数据类型：int</li>
	 * </ul>
	 */
	public Integer getNoticeNum() {
		return (Integer) get("notice_num");
	}

	public void setNoticeNum(Integer noticeNum) {
		set("notice_num", noticeNum);
	}

}