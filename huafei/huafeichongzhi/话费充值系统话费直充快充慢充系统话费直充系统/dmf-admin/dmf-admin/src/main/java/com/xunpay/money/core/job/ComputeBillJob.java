package com.xunpay.money.core.job;

import java.util.List;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.PersistJobDataAfterExecution;

import com.jfinal.log.Logger;
import com.jfinal.plugin.activerecord.Db;
import com.xunpay.money.model.AlipayAgentBill;
import com.xunpay.money.model.CompanyBill;

@DisallowConcurrentExecution
@PersistJobDataAfterExecution
public class ComputeBillJob extends BaseJob {
	
	private static final Logger logger = Logger.getLogger(ComputeBillJob.class);

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		// 结算码商的
		List<AlipayAgentBill> bills = AlipayAgentBill.dao.find("select * from alipay_agent_bill where status = '核算中'");
		logger.info("码商账单结算：" + bills.size());
		for (AlipayAgentBill bill : bills) {
			bill.setStatus("已结算");
			bill.setBalance(Db.queryBigDecimal("select balance from alipay_agent where id = ?", bill.getAgentId()).add(bill.getRebateMoney()));
			bill.update();
			Db.update("update alipay_agent set balance = balance + ? where id = ?", bill.getRebateMoney(), bill.getAgentId());
			logger.info("供应商(" + bill.getId() + ")结算完成：【" + bill.getAgentName() + "】 余额 -> " + bill.getBalance());
		}
		
		// 结算商户的
		List<CompanyBill> companyBills = CompanyBill.dao.find("select * from company_bill where status = '核算中'");
		
		logger.info("商户账单结算日志=========>：" + companyBills.size());
		for (CompanyBill bill : companyBills) {
			bill.setStatus("已结算");
			bill.setBalance(Db.queryBigDecimal("select balance from company_info where id = ?", bill.getCompanyId()).add(bill.getInMoney()));
			bill.update();
			//Db.update("update company_info set balance = balance + ? where id = ?", bill.getInMoney(), bill.getCompanyId());
			logger.info("商户账单结算日志=========>(" + bill.getId() + "）结算完成：【" + bill.getCompanyName() + "】商户账单结算日志=========>  余额 -> " + bill.getBalance());
		}
		
	}
	
}
