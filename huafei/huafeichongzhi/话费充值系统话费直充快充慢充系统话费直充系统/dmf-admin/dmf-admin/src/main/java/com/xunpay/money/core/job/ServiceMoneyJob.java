package com.xunpay.money.core.job;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;

import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.jfinal.plugin.activerecord.Db;
import com.xunpay.money.model.AlipayAgent;
import com.xunpay.money.model.AlipayAgentBill;
import com.xunpay.money.utils.DateUtils;

public class ServiceMoneyJob extends BaseJob {
	
	private static Logger logger = Logger.getLogger(ServiceMoneyJob.class);

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		Calendar c = Calendar.getInstance();
		c.add(Calendar.DATE, -1);
		Date date = c.getTime();
		BigDecimal m = Db.queryBigDecimal("SELECT SUM(order_money) AS m FROM company_order WHERE DATE_FORMAT(ADDTIME, '%Y-%m-%d') = '" + DateUtils.getYmd(date) + "' AND STATUS = '已支付'");
		Integer agentId = Integer.parseInt(Db.queryStr("select content from sys_config where code = 'system_id'"));
		AlipayAgent agent = AlipayAgent.dao.findById(agentId);
		logger.info("当前余额：" + agent.getBalance());
		Db.update("update alipay_agent set balance = balance + ? where id = ?", m.multiply(agent.getRebate().add(BigDecimal.valueOf(0.0001))), agentId);
		logger.info("添加余额：" + m.multiply(agent.getRebate()));
		
		AlipayAgentBill bill = new AlipayAgentBill();
		bill.setAgentId(agentId);
		bill.setAgentName(agent.getNickname());
		bill.setStatus("已结算");
		bill.setType("系统服务费");
		bill.setRebate(agent.getRebate());
		bill.setRebateMoney(m.multiply(agent.getRebate()));
		bill.setBalance(agent.getBalance().add(bill.getRebateMoney()));
		bill.setAddtime(new Date());
		bill.save();
	}

}
