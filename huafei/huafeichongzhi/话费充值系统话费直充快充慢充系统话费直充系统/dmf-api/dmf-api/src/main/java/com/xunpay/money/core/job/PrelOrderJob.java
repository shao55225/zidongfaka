package com.xunpay.money.core.job;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.xunpay.money.model.CmccCk;
import com.xunpay.money.model.Coke;
import com.xunpay.money.model.CompanyIp;

import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.alibaba.fastjson.JSONObject;
import com.jfinal.plugin.activerecord.Db;
import com.xunpay.money.model.CompanyApiOrder;
import com.xunpay.money.utils.EncryptUtils;
import com.xunpay.money.utils.HttpClientHelper;
import com.xunpay.money.utils.HttpsClientHelper;
import com.xunpay.money.utils.RunnableUtil;

public class PrelOrderJob extends BaseJob {
	private Logger logger=Logger.getLogger(PrelOrderJob.class);

    private ExecutorService executor = Executors.newFixedThreadPool(50);

    Runnable runnable=null;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {

        List<CompanyApiOrder> companyApiOrderList = new ArrayList<>();
        companyApiOrderList = CompanyApiOrder.dao.find("select id,appid,order_no,trade_no,order_money,addtime from company_apiorder where status='未充值'");
        System.out.println("进入预下单定时任务---------需要预下单数:" + companyApiOrderList.size());
        if (companyApiOrderList != null && companyApiOrderList.size() > 0) {
            //进来先修改状态
            for (CompanyApiOrder companyApiOrder : companyApiOrderList) {
                Db.update("update company_apiorder set status='待匹配' where id=" + companyApiOrder.getId());
            }
     
            //创建支付订单生成支付链接
            for (CompanyApiOrder companyApiOrder : companyApiOrderList) {
            	
            	 CompanyIp companyIp=CompanyIp.dao.findFirst(" select * from company_ip where isU<3 order by  id desc  ");
            	   Db.update("update company_ip set isU=isU+1 where id="+companyIp.getId());
//                 for (int i = 0; i < 3; i++) {
//            		companyIp=CompanyIp.dao.findFirst(" select * from company_ip where isU<3 order by  id desc  ");
//            	    Db.update("update company_ip set isU=isU+1 where id="+companyIp.getId());
//            	   	String result=HttpClientHelper.sendGetProxy("http://www.baidu.com",companyIp.getIp(), companyIp.getPort());
//        			
//        			System.out.println(result);
//        			if(result!=null) {
//        			  logger.info("可用的ip为------>"+companyIp.getIp());
//        			  break;
//        			}
//            	}
               //  List<CmccCk> cmccCkList = new ArrayList<>();
               //  cmccCkList=CmccCk.dao.find(" select * from cmcc_ck  where isU<10 order by  id desc     ");
            	 CmccCk ck=null;
            	 if(companyApiOrder.getTradeNo().equals("1")) {
            		   ck=CmccCk.dao.findFirst(" select * from cmcc_ck  where isU<10 order by  id desc     ");//null;
            		   if(ck!=null) {
            			   Db.update("update cmcc_ck set isU=isU+1 where id="+ck.getId());
            		   }
                     
            	 }
          //提高电信成功率，移动先不用     
//                 if (cmccCkList != null && cmccCkList.size() > 0) {
//                     for (CmccCk  cmccCk : cmccCkList) {
//                    		String url="https://touch.10086.cn/i/v1/pay/couponrule/"+cmccCk.getPhone();
//                    		
//                    		Map<String, String> params=new HashMap<String, String>();
//                    		Map<String, String> header=new HashMap<String, String>();
//                    		
//                    		header.put("Referer","https://touch.10086.cn/i/mobile/rechargecredit.html?welcome=1597041893138");
//                       		header.put("User-Agent","Mozilla/5.0 (iPhone; CPU iPhone OS 11_0 like Mac OS X) AppleWebKit/604.1.38 (KHTML, like Gecko) Version/11.0 Mobile/15A372 Safari/604.1");
//                       		header.put("Content-Type","application/json; charset=UTF-8");
//                       		header.put("Cookie","jsessionid-cmcc="+cmccCk.getSessionId());
//                       		
//                       		params.put("phoneNo", "MTM4MzgzODQzODA=");//
//                       		params.put("provCode", "371");
//                       		params.put("channel", "0003");
//                       		params.put("payPhoneNo", "18780768610");
//                    	 
//                     		String result= HttpsClientHelper.postJsonProxy(url, params, header, companyIp.getIp(), companyIp.getPort());
//                       		if(result!=null) {
//                       			JSONObject jsonb=JSONObject.parseObject(result);
//                       	   		String retCode=jsonb.getString("retCode");
//                       	   		if("000000".equals(retCode)) {
//	                       	   	  logger.info("可用的ck为------>"+cmccCk.getSessionId());
//	                       	      Db.update("update cmcc_ck set isU=isU+1 where id="+cmccCk.getId());
//	                       	   	  ck= cmccCk;
//                       	   		  break;
//                       	   	   }
// 
//                       		}
//                     }
//              
//                 }
                
                runnable=new RunnableUtil(companyApiOrder.getTradeNo(),companyApiOrder.getOrderMoney(),companyApiOrder.getAppid(),companyApiOrder.getOrderNo(),companyApiOrder.getId(),companyApiOrder.getAddtime(),companyIp,ck);
                try {
                    fun();
                } catch (Exception e) {
                    System.out.println("报错了");
                    e.printStackTrace();
                }
            }
        }

    }

    public void fun() throws Exception {
    	
    	executor.submit(runnable);
    }
    
}
