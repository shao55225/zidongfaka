package com.xunpay.money.model;

import com.xunpay.money.model.annotation.Table;
import com.jfinal.plugin.activerecord.Model;

/**
 * 
 * 对应表名：sys_user_resources
 * 表描述：
 * 
 * @author ModelHelper 
 * @version 1.0 
 */
@SuppressWarnings("serial")
@Table(name = "sys_user_resources")
public class SysUserResources extends Model<SysUserResources> {

	public static final SysUserResources dao = new SysUserResources();


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
	 * 用户id<br />
	 * <ul>
	 *   <li>非空：可以为空</li>
	 *   <li>数据类型：int</li>
	 * </ul>
	 */
	public Integer getUserId() {
		return (Integer) get("user_id");
	}

	public void setUserId(Integer userId) {
		set("user_id", userId);
	}

	/**
	 * 资源id<br />
	 * <ul>
	 *   <li>非空：可以为空</li>
	 *   <li>数据类型：int</li>
	 * </ul>
	 */
	public Integer getResourcesId() {
		return (Integer) get("resources_id");
	}

	public void setResourcesId(Integer resourcesId) {
		set("resources_id", resourcesId);
	}

	/**
	 * 权限<br />
	 * <ul>
	 *   <li>非空：可以为空</li>
	 *   <li>数据类型：varchar(8)</li>
	 * </ul>
	 */
	public String getAuth() {
		return (String) get("auth");
	}

	public void setAuth(String auth) {
		set("auth", auth);
	}

}