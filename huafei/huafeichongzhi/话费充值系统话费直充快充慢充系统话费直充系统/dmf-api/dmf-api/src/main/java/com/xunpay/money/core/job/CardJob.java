package com.xunpay.money.core.job;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.jfinal.plugin.activerecord.Db;
import com.xunpay.money.controller.NoticeController;
import com.xunpay.money.model.CompanyApiOrder;
import com.xunpay.money.model.CompanyOrder;
import com.xunpay.money.utils.EncryptUtils;
import com.xunpay.money.utils.HttpClientHelper;
import com.xunpay.money.utils.MobileClient;

/***
 * 回调卡单定时任务
 * 
 * */

public class CardJob extends BaseJob {
	
    private static final Logger logger = Logger.getLogger(CtccJob.class);
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
   
       	//卡单单独线程 卡单充值失败
        List<CompanyApiOrder> listFlase = CompanyApiOrder.dao.find(" select * from company_apiorder where  status='充值失败'  and notice='未回调' and  addtime>(select date_sub(now(), interval 4 minute) ) and addtime<(select date_sub(now(), interval 100 second)) ");

        //卡单单独线程 卡单充值成功
        List<CompanyApiOrder> listSuccess = CompanyApiOrder.dao.find(" select * from company_apiorder where  status='充值成功'  and notice='未回调' and  addtime>(select date_sub(now(), interval 10 minute) ) and addtime<(select date_sub(now(), interval 100 second)) ");
        
        logger.info("目前有卡单失败笔数-------------->"+listFlase.size());
        NoticeController noticController=new NoticeController();
        
        /**
         * **/
        if(listFlase!=null && listFlase.size()>0) {
        	
            for (CompanyApiOrder apiOrder : listFlase) {
            	
                for (int i = 0; i < 2; i++) {
                	
                    String result = noticController.noticePhoneApi(apiOrder);
                    System.out.println("充值失败卡单回调给话费API结果:--------->"+result);
                    if("success".equals(result)) {
                    	
            	        Db.update("update company_apiorder set notice='回调成功' where id=" + apiOrder.getId());
            	        break;
                    }
        		}
                
            }
        }
        logger.info("目前有卡单成功笔数-------------->"+listSuccess.size());
        
        /**
         * **/
        if(listSuccess!=null && listSuccess.size()>0) {
        	
        	for (CompanyApiOrder order : listSuccess) {
        		
        		for (int i = 0; i < 2; i++) {
        			
        			String result = noticController.noticePhoneApi(order);
        			System.out.println("充值成功卡单回调给话费API结果:--------->"+result);
        			if("success".equals(result)) {
        				
        				Db.update("update company_apiorder set notice='回调成功' where id=" + order.getId());
        				break;
        			}
        		}
        		
        	}
        }

    }
}
