package com.xunpay.money.utils;

import java.math.BigDecimal;
import java.util.Date;

import com.jfinal.plugin.activerecord.Db;
import com.xunpay.money.controller.NoticeController;
import com.xunpay.money.model.CompanyApiOrder;
import com.xunpay.money.model.CompanyBill;
import com.xunpay.money.model.CompanyInfo;
import com.xunpay.money.model.CompanyOrder;
import org.apache.log4j.Logger;

public class RunnableNoticeApiOrder extends Thread{
	private static final Logger logger = Logger.getLogger(RunnableNoticeApiOrder.class);


	private CompanyApiOrder companyOrder;

    public RunnableNoticeApiOrder(CompanyApiOrder companyOrder){
        this.companyOrder=companyOrder;
    }



    public void run(){



		String companyName=companyOrder.getCompanyName();
		CompanyInfo company = CompanyInfo.dao.findFirst(" select  *  from company_info where username='"+companyName+"'   ");

		/**如果充值失败退款处理 */
		if("充值失败".equals(companyOrder.getStatus())){

			Db.update("update company_info set balance=balance+"+companyOrder.getOrderMoney()+" where id="+companyOrder.getCompanyId());
			CompanyInfo company1 = CompanyInfo.dao.findFirst("select  *  from company_info where username='"+companyName+"'  ");//二次获取
			/**
			 * 写入财务明细表
			 ***/
			CompanyBill bill = new CompanyBill();
			bill.setCompanyId(company.getId());
			bill.setInMoney(companyOrder.getOrderMoney());
			bill.setType("充值退款");
			bill.setRemark("充值订单退款");
			bill.setStatus("已结算");
			bill.setOrderNo(companyOrder.getOrderNo());
			bill.setOrderMoney(companyOrder.getOrderMoney());
			bill.setBalance(company1.getBalance());
			bill.setCompanyName(company.getNickname());
			bill.setAddtime(new Date());
			bill.save();
		}


		String treaty = "";

		StringBuffer noticeUrl = new StringBuffer();

		noticeUrl.append(companyOrder.getNoticeUrl());

		if (noticeUrl.indexOf("?") > 0) {

			noticeUrl.append("&");
		} else {

			noticeUrl.append("?");
		}

		//回调规则
		//20200327001hfapi10
		//str=  apiordersn=20200327001^payType=hfapi^money=10^order^sign=sssssss

		String orderNo = companyOrder.getToken();//商务合作
		//String payType = companyOrder.getPayType();
		BigDecimal moeny = companyOrder.getOrderMoney();
		String results = "";
		String msg = "";
		if ("充值成功".equals(companyOrder.getStatus())) {
			results = "success";
			msg = "1";
		} else if ("充值异常".equals(companyOrder.getStatus())) {
			results = "success";
			msg = "2";
		} else {
			results = "fail";
			msg = "0";
		}


		String phone = companyOrder.getAppid();
		String op_no = companyOrder.getOutOrderNo();

		CompanyInfo companyInfo = CompanyInfo.dao.findFirst("select * from company_info where id=" + companyOrder.getCompanyId() + " ");

		String md5Str = results + msg + orderNo + phone + moeny.toString() + op_no + companyInfo.getMd5key();

		String md5 = EncryptUtils.encrypt(md5Str);

		String finalStr = noticeUrl.
				append("result=" + results).
				append("&msg=" + msg).
				append("&order=" + orderNo).
				append("&phone_no=" + phone).
				append("&amount=" + moeny).
				append("&op_no=" + op_no).
				append("&sign=" + md5).toString();
		System.out.println("回调参数运营商" + finalStr);
		logger.info("回调参数运营商" + finalStr);
		String noturl = finalStr;
		String result = "";
		for(int i=0;i<3;i++){
			result=HttpClientFactory.get(noturl);
			if("success".equals(result)||"SUCCESS".equals(result)){
				Db.update("update company_apiorder set notice='回调成功' where id='" + companyOrder.getId() + "' ");
				break;
			}
		}

    }
}
