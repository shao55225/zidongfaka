package com.xunpay.money.core.job;

import org.quartz.Job;

public abstract class BaseJob implements Job {

	private Integer taskId;

	public Integer getTaskId() {
		return taskId;
	}

	public void setTaskId(Integer taskId) {
		this.taskId = taskId;
	}
	
}
