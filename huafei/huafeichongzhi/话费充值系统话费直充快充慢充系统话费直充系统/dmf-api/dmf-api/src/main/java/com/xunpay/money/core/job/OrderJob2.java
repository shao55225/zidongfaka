package com.xunpay.money.core.job;

import com.xunpay.money.controller.NoticeController;
import com.xunpay.money.model.CompanyApiOrder;
import com.xunpay.money.model.CompanyOrder;
import com.xunpay.money.model.SysConfig;
import com.xunpay.money.utils.RunnableNoticeApiOrder;
import com.xunpay.money.utils.RunnableNoticeOrder;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class OrderJob2 extends BaseJob {

    Runnable runnable = null;
    private ExecutorService executor = Executors.newCachedThreadPool();

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
    	   //系统配置 
       	SysConfig sysconfig=SysConfig.dao.findFirst(" select * from sys_config  where code='payTime' ");
     	String payTime=sysconfig.getContent();

        List<CompanyApiOrder> list = CompanyApiOrder.dao.find(" select * from company_apiorder where status='充值中'  and  addtime>(select date_sub(now(), interval 10 minute) ) and addtime<(select date_sub(now(), interval  "+Integer.parseInt(payTime)+"  second)) ");

        List<CompanyOrder> compaynOrder = CompanyOrder.dao.find(" select * from company_order where status='支付中'  and  noticetime>(select date_sub(now(), interval 10 minute) ) and noticetime<(select date_sub(now(), interval "+Integer.parseInt(payTime)+" second)) ");

        System.out.println("支付超时回调-------------count-------->" + list.size());


        if (list != null && list.size() > 0) {

            for (CompanyApiOrder companyOrder : list) {


                companyOrder.setStatus("充值失败");

                companyOrder.update();

                runnable=new RunnableNoticeApiOrder(companyOrder);
                try {
                    fun();
                } catch (Exception e) {
                    e.printStackTrace();
                }



            }
        }

        if (compaynOrder != null && compaynOrder.size() > 0) {

            for (CompanyOrder order : compaynOrder) {

                order.setStatus("支付失败");

                order.update();

            }
        }
    }

    public void fun() throws Exception {

        executor.submit(runnable);
    }
}
