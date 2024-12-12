package com.xunpay.money.core.job;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.xunpay.money.model.CompanyApiOrder;
import com.xunpay.money.model.CompanyOrder;
import com.xunpay.money.model.SysConfig;
import com.xunpay.money.utils.RunnableNoticeApiOrder;

public class ApiOrederJob2 extends BaseJob {

    Runnable runnable = null;
    private ExecutorService executor = Executors.newCachedThreadPool();

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
    	
    	   //系统配置 
       	SysConfig sysconfig=SysConfig.dao.findFirst(" select * from sys_config  where code='bufferTime' ");
     	String bufferTime=sysconfig.getContent();
     	
    	//缓冲区超时
        List<CompanyApiOrder> list = CompanyApiOrder.dao.find("select * from company_apiorder where  (status='匹配中'   or  status='待匹配' )  and  addtime>(select date_sub(now(), interval 10 minute) ) and addtime<(select date_sub(now(), interval "+Integer.parseInt(bufferTime)+" second)) ");
       
        List<CompanyOrder> compaynOrder = CompanyOrder.dao.find(" select * from company_order where  status='匹配中'   and  noticetime>(select date_sub(now(), interval 10 minute) ) and noticetime<(select date_sub(now(), interval "+Integer.parseInt(bufferTime)+" second)) ");
        
        if(compaynOrder!=null&&compaynOrder.size()>0){
        	for(CompanyOrder order:compaynOrder){
        		
        		order.setStatus("支付失败");
        		
        		order.update();
        	}
        }
        
        System.out.println("缓冲区超时-------------count-------->"+list.size());
        if (list != null && list.size() > 0) {
            for (CompanyApiOrder companyApiOrder : list) {
            	
                companyApiOrder.setStatus("充值失败");
                
                companyApiOrder.update();

                runnable=new RunnableNoticeApiOrder(companyApiOrder);

                try {
                    fun();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                	
            }
        }
    }

    public void fun() throws Exception {

        executor.submit(runnable);
    }
}
