package com.xunpay.money.core.job;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.jfinal.plugin.activerecord.Db;

public class ClearTodayDataJob extends BaseJob {

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		Db.update("update company_settle_item set today_money = 0");
	}
	
}
