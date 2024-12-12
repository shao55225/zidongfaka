package com.xunpay.money.core.job;

import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.apache.log4j.Logger;

import com.xunpay.money.utils.RunnableNoticeOrder;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.xunpay.money.model.CompanyApiOrder;
import com.xunpay.money.model.CompanyOrder;
import com.xunpay.money.model.SysConfig;
import com.xunpay.money.utils.RunnableNoticeApiOrder;

public class OrderJob extends BaseJob {
    private static final Logger logger = Logger.getLogger(OrderJob.class);

    Runnable runnable = null;
    private ExecutorService executor = Executors.newCachedThreadPool();

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        try {
            //系统配置
            SysConfig sysconfig = SysConfig.dao.findFirst(" select * from sys_config  where code='payTime' ");
            String payTime = sysconfig.getContent();
            //  and  addtime>(select date_sub(now(), interval 10 minute) ) and addtime<(select date_sub(now(), interval  " + Integer.parseInt(payTime) + "  second))
            List<CompanyApiOrder> list = CompanyApiOrder.dao.find(" select * from company_apiorder where status='充值中'  ");
            //and  noticetime>(select date_sub(now(), interval 10 minute) ) and noticetime<(select date_sub(now(), interval " + Integer.parseInt(payTime) + " second))
            List<CompanyOrder> compaynOrder = CompanyOrder.dao.find(" select * from company_order where status='支付中' ");
            System.out.println("支付中充值中回调给运营商-------------count-------->" + list.size());
            if (list != null && list.size() > 0) {
                for (CompanyApiOrder companyOrder : list) {
                    //获取订单的时间大于支付超时时间
                    if (calLastedTime(companyOrder.getSettletime()) > Integer.valueOf(payTime)) {
                        logger.info("支付超时回调给运营商订单号：" + companyOrder.getOrderNo() + "***拉起支付时间：" + companyOrder.getSettletime() + "***回调失败时间：" + new Date());
                        companyOrder.setStatus("充值失败");
                        //回调充值失败的时间
                        companyOrder.setNoticetime(new Date());
                        companyOrder.update();
                        runnable = new RunnableNoticeApiOrder(companyOrder);
                        try {
                            fun();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
            /**
             * 回调给四方
             */
//            if (compaynOrder != null && compaynOrder.size() > 0) {
//
//                for (CompanyOrder order : compaynOrder) {
//
//                    order.setStatus("支付失败" );
//
//                    order.update();
//                    runnable = new RunnableNoticeOrder(order);
//                    try {
//                        fun();
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//
//                }
//            }

            if (compaynOrder != null && compaynOrder.size() > 0) {
                for (CompanyOrder order : compaynOrder) {
                    //获取订单的时间大于支付超时时间
                    if (calLastedTime(order.getSettletime()) > Integer.valueOf(payTime)) {
                        order.setStatus("支付超时");
                        order.setAddtime(order.getSettletime());
                        order.setSettletime(new Date());

                        order.update();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void fun() throws Exception {

        executor.submit(runnable);
    }

    /**
     * 当前时间减去某一个时间
     *
     * @param startDate
     * @return
     */
    public static int calLastedTime(Date startDate) {
        long a = new Date().getTime();
        long b = startDate.getTime();
        int c = (int) ((a - b) / 1000);
        return c;
    }
}
