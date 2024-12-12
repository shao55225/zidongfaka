package com.xunpay.money.model;

import com.xunpay.money.model.annotation.Table;
import com.jfinal.plugin.activerecord.Model;

/**
 * 
 * 对应表名：sys_task
 * 表描述：
 * 
 * @author ModelHelper 
 * @version 1.0 
 */
@SuppressWarnings("serial")
@Table(name = "sys_task")
public class SysTask extends Model<SysTask> {

	public static final SysTask dao = new SysTask();


	/**
	 * 主键Id<br />
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
	 * 分组code<br />
	 * <ul>
	 *   <li>非空：可以为空</li>
	 *   <li>数据类型：varchar(50)</li>
	 * </ul>
	 */
	public String getGroupCode() {
		return (String) get("group_code");
	}

	public void setGroupCode(String groupCode) {
		set("group_code", groupCode);
	}

	/**
	 * 任务code<br />
	 * <ul>
	 *   <li>非空：可以为空</li>
	 *   <li>数据类型：varchar(50)</li>
	 * </ul>
	 */
	public String getTaskCode() {
		return (String) get("task_code");
	}

	public void setTaskCode(String taskCode) {
		set("task_code", taskCode);
	}

	/**
	 * 任务class<br />
	 * <ul>
	 *   <li>非空：可以为空</li>
	 *   <li>数据类型：varchar(100)</li>
	 * </ul>
	 */
	public String getTaskClass() {
		return (String) get("task_class");
	}

	public void setTaskClass(String taskClass) {
		set("task_class", taskClass);
	}

	/**
	 * 任务配置<br />
	 * <ul>
	 *   <li>非空：可以为空</li>
	 *   <li>数据类型：varchar(100)</li>
	 * </ul>
	 */
	public String getTaskCron() {
		return (String) get("task_cron");
	}

	public void setTaskCron(String taskCron) {
		set("task_cron", taskCron);
	}

	/**
	 * 任务名称<br />
	 * <ul>
	 *   <li>非空：可以为空</li>
	 *   <li>数据类型：varchar(50)</li>
	 * </ul>
	 */
	public String getName() {
		return (String) get("name");
	}

	public void setName(String name) {
		set("name", name);
	}

	/**
	 * 任务描述<br />
	 * <ul>
	 *   <li>非空：可以为空</li>
	 *   <li>数据类型：varchar(200)</li>
	 * </ul>
	 */
	public String getRemark() {
		return (String) get("remark");
	}

	public void setRemark(String remark) {
		set("remark", remark);
	}

	/**
	 * 状态<br />
	 * <ul>
	 *   <li>非空：可以为空</li>
	 *   <li>数据类型：varchar(10)</li>
	 * </ul>
	 */
	public String getStatus() {
		return (String) get("status");
	}

	public void setStatus(String status) {
		set("status", status);
	}

	/**
	 * 任务启动时，默认先执行一次<br />
	 * <ul>
	 *   <li>默认值：任务启动时，默认先执行一次</li>
	 *   <li>非空：可以为空</li>
	 *   <li>数据类型：varchar(1)</li>
	 * </ul>
	 */
	public String getAutoExec() {
		return (String) get("auto_exec");
	}

	public void setAutoExec(String autoExec) {
		set("auto_exec", autoExec);
	}

}