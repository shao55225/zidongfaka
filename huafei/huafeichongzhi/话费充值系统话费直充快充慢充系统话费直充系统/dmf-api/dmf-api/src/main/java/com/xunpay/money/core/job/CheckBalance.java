package com.xunpay.money.core.job;


import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.jfinal.plugin.activerecord.Db;
import com.xunpay.money.controller.NoticeController;
import com.xunpay.money.model.CompanyApiOrder;
import com.xunpay.money.model.CompanyBill;
import com.xunpay.money.model.CompanyOrder;
import com.xunpay.money.utils.EncryptUtils;
import com.xunpay.money.utils.HttpClientHelper;
import com.xunpay.money.utils.MobileClient;

/***
 * 校验商户余额
 * */

public class CheckBalance extends BaseJob {
	
    private static final Logger logger = Logger.getLogger(CheckBalance.class);
    
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
	    	/**
	    	 * 获取当天折扣流水
	    	 */
    		BigDecimal orderMoney = null;
    		Date date = new Date();
    		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    		SimpleDateFormat hms = new SimpleDateFormat("HH:mm:ss");

    		String time=hms.format(date);
    		String currentDay = format.format(date);
    		String startDate = currentDay + " 00:00:00";
    		String endDate = currentDay + " 23:59:59";
    		String sql = "select sum(order_money)  from company_apiorder  where  company_id='62'  and   addtime>='" + startDate + "' and  addtime<='" + endDate + "' and status='充值成功' ";
    		 System.out.println("客户余额校准--准备1-------->" );
    		 orderMoney = Db.queryBigDecimal(sql);
    		 double waterMoney= orderMoney == null ? 0 : orderMoney.doubleValue();
    		 System.out.println("客户余额校准--准备2-------->" );
    		/**
    		 * 获取最近一次的余额变动记录
    		 */
    		String billSql="select balance  from company_bill order by id  desc  limit  1";
    		BigDecimal billBalance=Db.queryBigDecimal(billSql);
    		double balance= billBalance == null ? 0 : billBalance.doubleValue();
    		 System.out.println("客户余额校准--准备3-------->" );
    		if(balance>=waterMoney) {
    			double finalWB=balance-waterMoney;
    			  
        		Db.update("update company_info set balance="+finalWB+" where id='62'");
        		  System.out.println("客户余额校准4-----结束------>" );
    		}
    		//查询一次商户余额
    		BigDecimal bigDeci=Db.queryBigDecimal("select balance from company_info where id='62' ");
    		if(time.startsWith("20:47")) {
    			//自动备份一次数据到bill
    	         CompanyBill  bill =new CompanyBill();
    	         //bill.setInMoney(bigDeci);
    	         bill.setAddtime(date);
    	         bill.setRemark("每天凌晨写入校验金额");
    	         bill.setBalance(bigDeci);
    	         bill.setCompanyId(71);
    
    	         bill.save();
    			
    		}
    		
    	
    }
}
