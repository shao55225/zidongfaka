package com.xunpay.money.core.job;


import java.util.List;

import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.alibaba.fastjson.JSONObject;
import com.jfinal.plugin.activerecord.Db;
import com.xunpay.money.controller.NoticeController;
import com.xunpay.money.model.CompanyApiOrder;
import com.xunpay.money.model.CompanyOrder;
import com.xunpay.money.utils.CmccHttp;

public class CmccJob2 extends BaseJob {
    private static final Logger logger = Logger.getLogger(CmccJob2.class);

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {

        List<CompanyOrder> companyOrderList = CompanyOrder.dao.find("select * from company_order where addtime<now() and addtime>(select date_sub(now(), interval 300 second)) and status='支付中' and trade_no=1");

        System.out.println("执行移动支付状态查询，查询数：" + companyOrderList.size());
        NoticeController noticController = new NoticeController();
        if (companyOrderList != null && companyOrderList.size() > 0) {
            for (CompanyOrder companyOrder : companyOrderList) {
                String url = companyOrder.getRsaAlipay();
               	if(url!=null) {
               		
               	 String temp[]=url.split("orderId=");
        		 String callBackUrl="https://touch.10086.cn/i/v1/pay/paycallback/?orderId="+temp[1];
        		 String resutlStr= CmccHttp.sendGet(callBackUrl);
        		 JSONObject jso= JSONObject.parseObject(resutlStr);
        		 JSONObject dataObject=JSONObject.parseObject(jso.getString("data"));
        		 String status= dataObject.getString("payOrderStatus");
        		 if(status.equals("4")) {
        			 
        			 logger.info("充值成功");
                     companyOrder.setStatus("充值成功");
                     //跟新订单状态
                     Db.update("update company_order set status='已支付' where id=?",companyOrder.getId());
                     
                     Db.update("update company_apiorder set status='充值成功'   where order_no=?",companyOrder.getAlipayName());

                     String apiOrderID= companyOrder.getAlipayName();
                     
                     CompanyApiOrder companyApiOrder = CompanyApiOrder.dao.findFirst(" select * from company_apiorder  where order_no='"+apiOrderID+"'  ");
                     companyApiOrder.setToken(companyOrder.getToken());
                     companyApiOrder.setReturnUrl(companyOrder.getReturnUrl());
                     companyApiOrder.update();

                     	  //回调API
                         String s3 = noticController.noticePhoneApi(companyApiOrder);
                         logger.info("移动回调API结果为--------------------------->"+s3);
                         if ("success".equals(s3)) {
                             Db.update("update company_apiorder set notice='回调成功' where order_no='"+apiOrderID+"' " );
                         }
 						
                     	//回调
                         String s2 = noticController.noticeExe(companyOrder);
                         logger.info("移动回调支付方结果为--------------------------->"+s2);
                         
                         if (s2.equals("success") ) {
                             Db.update("update company_order set notice='回调成功' where id=" + companyOrder.getId());
                         }
        			
        		 }
               		
               	}else {
               		continue;
               	}
             
                  
            }
        }

    }
}
