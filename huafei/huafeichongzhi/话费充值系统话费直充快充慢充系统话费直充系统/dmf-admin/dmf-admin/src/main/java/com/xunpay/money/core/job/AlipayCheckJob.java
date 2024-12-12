package com.xunpay.money.core.job;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.jfinal.log.Logger;
import com.jfinal.plugin.activerecord.Db;
import com.xunpay.money.model.CompanyOrder;
import com.xunpay.money.utils.DateUtils;
import com.xunpay.money.utils.HttpClientHelper;

public class AlipayCheckJob extends BaseJob {

	private static final Logger logger = Logger.getLogger(AlipayCheckJob.class);

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		// 用这个job来补单
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		c.add(Calendar.MINUTE, -15);
		List<CompanyOrder> orders = CompanyOrder.dao.find("select * from company_order where notice = '回调中' and addtime > '" + DateUtils.getYmdHmis(c.getTime()) + "'");
		for (CompanyOrder order : orders) {
			Integer id = order.getId();
			String token = order.getToken();
			String result = HttpClientHelper.sendGet("http://" + Db.queryStr("select content from sys_config where code = 'api_host'") + "/system/noticeOrder?id=" + id + "&token=" + token);
			logger.info("补单回调:" + result);
		}
	}
	
}
