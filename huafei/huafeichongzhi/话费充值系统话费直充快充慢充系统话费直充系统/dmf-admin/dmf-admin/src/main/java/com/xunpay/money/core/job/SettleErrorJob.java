package com.xunpay.money.core.job;

import java.util.List;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.PersistJobDataAfterExecution;

import com.jfinal.plugin.activerecord.Db;
import com.xunpay.money.model.CompanyOrder;
import com.xunpay.money.utils.HttpClientHelper;

@DisallowConcurrentExecution
@PersistJobDataAfterExecution
public class SettleErrorJob extends BaseJob {

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		List<CompanyOrder> orders = CompanyOrder.dao.find("select * from company_order where alipay_id in (select id from alipay_item where status = '在线') and settle = '分账失败'");
		String apiHost = Db.queryStr("select content from sys_config where code = 'api_host'");
		for (CompanyOrder order : orders) {
			HttpClientHelper.sendGet("http://" + apiHost + "/system/settleOrder?id=" + order.getId() + "&token=" + order.getToken());
		}
	}
	
}
