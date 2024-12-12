package com.xunpay.money.core.job;


import com.jfinal.plugin.activerecord.Db;
import com.xunpay.money.controller.NoticeController;
import com.xunpay.money.model.CompanyApiOrder;
import com.xunpay.money.model.CompanyOrder;
import com.xunpay.money.utils.EncryptUtils;
import com.xunpay.money.utils.HttpClientHelper;
import com.xunpay.money.utils.HttpClientHelperCtcc;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.util.*;

public class CtccJob2 extends BaseJob {
    private static final Logger logger = Logger.getLogger(CtccJob2.class);

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        HttpClientHelperCtcc httpClientHelperCtcc=new HttpClientHelperCtcc();

        List<CompanyOrder> companyOrderList = CompanyOrder.dao.find("select * from company_order where addtime<now() and addtime>(select date_sub(now(), interval 500 second)) and status='支付中' and trade_no=3");

        System.out.println("2号执行电信支付状态查询，查询数：" + companyOrderList.size());
        NoticeController noticController = new NoticeController();
        if (companyOrderList != null && companyOrderList.size() > 0) {
            for (CompanyOrder companyOrder : companyOrderList) {
                String url = companyOrder.getReturnUrl();
                String s=null;
               	if(url!=null) {
               		s = httpClientHelperCtcc.sendGetQuery(url);
               	}else {
               		continue;
               	}
                Document document = Jsoup.parse(s);
                Elements script = document.select("div div div");
                logger.info("订单返回充值状态:" + script.text());
                System.out.println(script.text());//返回的订单状态
                if ("充值成功".equals(script.text())) {

                    companyOrder.setStatus("充值成功");

                    //跟新订单状态
                    Db.update("update company_order set status='已支付' where id=?",companyOrder.getId());
                    
                    Db.update("update company_apiorder set status='充值成功'   where order_no=?",companyOrder.getAlipayName());

                    String apiOrderID= companyOrder.getAlipayName();
                    
                    CompanyApiOrder companyApiOrder = CompanyApiOrder.dao.findFirst(" select * from company_apiorder  where order_no='"+apiOrderID+"'  ");
                    companyApiOrder.setToken(companyOrder.getToken());
                    companyApiOrder.setReturnUrl(companyOrder.getReturnUrl());
                    companyApiOrder.update();

                    for (int i = 0; i < 3; i++) {
                    	
                    	  //回调API
                        String s3 = noticController.noticePhoneApi(companyApiOrder);
                        logger.info("电信回调API结果为--------------------------->"+s3);
                        if ("success".equals(s3)) {
                            Db.update("update company_apiorder set notice='回调成功' where order_no='"+apiOrderID+"' " );
                            break;
                        }
                        try {
							Thread.sleep(100);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
					}
                    for (int i = 0; i < 3; i++) {
                    	//回调
                        String s2 = noticController.noticeExe(companyOrder);
                        
                        logger.info("电信回调支付方结果为--------------------------->"+s2);
                        
                        if (s2.equals("success") ) {
                            Db.update("update company_order set notice='回调成功' where id=" + companyOrder.getId());
                            break;
                        }
                        try {
							Thread.sleep(100);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
                        
                    }

                    
                    
                  
                }
            }
        }

    }
}
