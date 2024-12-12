package com.xunpay.money.model;

import com.xunpay.money.model.annotation.Table;
import com.jfinal.plugin.activerecord.Model;

/**
 * 
 * 对应表名：sys_config
 * 表描述：
 * 
 * @author ModelHelper 
 * @version 1.0 
 */
@SuppressWarnings("serial")
@Table(name = "sys_config")
public class SysConfig extends Model<SysConfig> {

	public static final SysConfig dao = new SysConfig();


	/**
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
	 * <ul>
	 *   <li>非空：可以为空</li>
	 *   <li>数据类型：varchar(32)</li>
	 * </ul>
	 */
	public String getCode() {
		return (String) get("code");
	}

	public void setCode(String code) {
		set("code", code);
	}

	/**
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
	 * <ul>
	 *   <li>非空：可以为空</li>
	 *   <li>数据类型：text(65535)</li>
	 * </ul>
	 */
	public String getContent() {
		return (String) get("content");
	}

	public void setContent(String content) {
		set("content", content);
	}

	/**
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