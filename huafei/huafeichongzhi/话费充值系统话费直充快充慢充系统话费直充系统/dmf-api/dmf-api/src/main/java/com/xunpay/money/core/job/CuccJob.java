package com.xunpay.money.core.job;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.xunpay.money.model.CompanyIp;
import com.xunpay.money.utils.*;
import net.sf.json.JSONArray;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.jfinal.plugin.activerecord.Db;
import com.xunpay.money.controller.NoticeController;
import com.xunpay.money.model.CompanyApiOrder;
import com.xunpay.money.model.CompanyOrder;
import com.xunpay.money.model.SysConfig;

public class CuccJob extends BaseJob {
    private static final Logger logger = Logger.getLogger(CuccJob.class);

    Runnable runnable = null;
    private ExecutorService executor = Executors.newCachedThreadPool();

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        //HttpClientHelper.sendGet("http://api.wandoudl.com/api/ip?app_key=3f2cb2a6af6b0c0925d528c0132283bc&pack=213566&num=1&xy=1&type=1&lb=T&mr=1&");
        //系统配置
        List<CompanyOrder> companyOrderList = CompanyOrder.dao.find("select * from company_order where  addtime<now() and addtime>(select date_sub(now(), interval 8 minute)) and  status='支付中' and trade_no=2");
        System.out.println("执行联通支付状态查询，查询数：" + companyOrderList.size());
        NoticeController noticController = new NoticeController();
        if (companyOrderList != null && companyOrderList.size() > 0) {
            SysConfig sysconfig = SysConfig.dao.findFirst(" select * from sys_config  where code='noticeIp' ");
            String noticeIp = sysconfig.getContent();
            String baiIp = GetIp(noticeIp);
            String  ip1=baiIp.split(":")[0];
            Integer port1=Integer.parseInt(baiIp.split(":")[1].trim());
            logger.info("流冠获取ip："+ ip1);
            System.out.println("执行联通支付状态查询，ip：" + ip1);
            /*boolean isPass = true;
            while (isPass) {
                String result = HttpClientHelper.sendGetProxy("http://www.baidu.com", ip1, port1);
                //System.out.println(result);
                if (result == null) {
                    logger.info("ip不能用" + ip1);
                }
                break;
            }*/
            for (CompanyOrder companyOrder : companyOrderList) {

                String url = companyOrder.getReturnUrl();
                String s = null;
                if (url != null) {
                    s = HttpClient.get(url, ip1, port1);
                } else {
                    continue;
                }

                Document document = Jsoup.parse(s);
                //订单状态
                Elements servicePlatID = document.select("input[name='ServicePlatID']");
                Elements payRspObj = document.select("input[name='PayRspObj']");
                Elements script = document.getElementsByClass("enlarge").select("span");
                Elements province = document.select("input[name='province']");
                Elements hsnduns = document.select("input[name='hsnduns']");
                Elements sname = document.select("input[name='s']");
                logger.info("订单返回充值状态:" + script.text());
                System.out.println(script.text());//返回的订单状态
                if (servicePlatID.size() == 0) {

                    if ("充值成功".equals(script.text().trim())) {
                        //订单号
                        Elements orderId = document.getElementById("actiOrderNo").select("span");
                        //跟新订单状态
                        Db.update("update company_order set status='已支付',token=? where id=?", orderId.text(), companyOrder.getId());
                        Db.update("update company_apiorder set status='充值成功',token=? where order_no=?", orderId.text(), companyOrder.getAlipayName());
                        companyOrder.setToken(orderId.text());
                        companyOrder.setStatus("充值成功");
                        String apiOrderID = companyOrder.getAlipayName();
                        CompanyApiOrder companyApiOrder = CompanyApiOrder.dao.findFirst(" select * from company_apiorder  where order_no='" + apiOrderID + "'  ");
                        //重写数据到话费单
                        companyApiOrder.setReturnUrl(companyOrder.getReturnUrl());
                        companyApiOrder.update();
                        //回调API
                        runnable=new RunnableNoticeApiOrder(companyApiOrder);
                        try {
                            fun();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        /*String s3 = noticController.noticePhoneApi(companyApiOrder);
                        logger.info("联通回调话费API结果为--------------------------->" + s3);*/

                        //回调支付方
                        runnable=new RunnableNoticeOrder(companyOrder);
                        try {
                            fun();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                       /* String s2 = noticController.noticeExe(companyOrder);
                        logger.info("联通回调支付方结果为--------------------------->" + s2);
                        if ("success".equals(s3)) {
                            Db.update("update company_apiorder set notice='回调成功' where order_no='" + apiOrderID + "' ");
                        }

                        if ("success".equals(s2)) {
                            Db.update("update company_order set notice='回调成功' where id=" + companyOrder.getId());
                        }*/

                    } else if (script.text().trim().startsWith("充值中")) {
                        //String payurl = "https://upay.10010.com/npfwap/NpfMobCB/bankchargecb/bankChargeCallback";
                        String payurl = "https://upay.10010.com/npfwap/NpfMobCB/mobPayFeeCB/PayFeeCBFinish";
                        Map map = new HashMap();
                        map.put("PayRspObj", payRspObj.val());
                        map.put("ServicePlatID", servicePlatID.val());
                        map.put("province", province.val());
                        map.put("hsnduns", hsnduns.val());
                        map.put("s", sname.val());


                        String ip = ip1;//baiIp.split(":")[0];
                        Integer port = port1;//Integer.parseInt(baiIp.split(":")[1]);
                        String s1 = HttpClientHelperCucc.sendPost(payurl, map, ip, port);

                        Document documents = Jsoup.parse(s1);
                        Elements scripts = documents.getElementsByClass("enlarge").select("span");
                        System.out.println(scripts.text());
                        System.out.println("充值异常");

                        //订单号
                        Elements orderId = documents.getElementById("actiOrderNo").select("span");
                        //跟新订单状态

                        Db.update("update company_order set status='已支付',token=? where id=?", orderId.text(), companyOrder.getId());
                        Db.update("update company_apiorder set status='充值异常',token=? where order_no=?", orderId.text(), companyOrder.getAlipayName());
                        companyOrder.setToken(orderId.text());
                        companyOrder.setStatus("充值异常");


                        String apiOrderID = companyOrder.getAlipayName();
                        CompanyApiOrder companyApiOrder = CompanyApiOrder.dao.findFirst(" select * from company_apiorder  where order_no='" + apiOrderID + "'  ");

                        //重写数据到话费单
                        companyApiOrder.setReturnUrl(companyOrder.getReturnUrl());
                        companyApiOrder.update();
                        //回调API
                        runnable=new RunnableNoticeApiOrder(companyApiOrder);
                        try {
                            fun();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        /*String s3 = noticController.noticePhoneApi(companyApiOrder);
                        logger.info("联通回调话费API结果为--------------------------->" + s3);*/

                        //回调支付方
                        runnable=new RunnableNoticeOrder(companyOrder);
                        try {
                            fun();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        /*logger.info("联通回调支付方结果为--------------------------->" + s2);
                        if ("success".equals(s3)) {
                            Db.update("update company_apiorder set notice='回调成功' where order_no='" + apiOrderID + "' ");
                        }

                        if ("success".equals(s2)) {
                            Db.update("update company_order set notice='回调成功' where id=" + companyOrder.getId());
                        }*/
                    }


                } else {
                    //String payurl = "https://upay.10010.com/npfwap/NpfMobCB/bankchargecb/bankChargeCallback";
                    String payurl = "https://upay.10010.com/npfwap/NpfMobCB/mobPayFeeCB/PayFeeCBFinish";
                    Map map = new HashMap();
                    map.put("PayRspObj", payRspObj.val());
                    map.put("ServicePlatID", servicePlatID.val());
                    map.put("province", province.val());
                    map.put("hsnduns", hsnduns.val());
                    map.put("s", sname.val());
                    String ip = ip1;
                    Integer port = port1;
                    String s1 = HttpClientHelperCucc.sendPost(payurl, map, ip, port);

                    Document documents = Jsoup.parse(s1);
                    Elements scripts = documents.getElementsByClass("enlarge").select("span");
                    System.out.println(scripts.text());
                    logger.info("订单号：" + companyOrder.getAlipayName() + "查询联通充值状态:" + scripts.text());

                    if ("充值成功".equals(scripts.text().trim())) {
                        //订单号
                        Elements orderId = documents.getElementById("actiOrderNo").select("span");
                        //跟新订单状态

                        Db.update("update company_order set status='已支付',token=? where id=?", orderId.text(), companyOrder.getId());

                        Db.update("update company_apiorder set status='充值成功',token=? where order_no=?", orderId.text(), companyOrder.getAlipayName());

                        companyOrder.setToken(orderId.text());

                        companyOrder.setStatus("充值成功");

                        String apiOrderID = companyOrder.getAlipayName();
                        CompanyApiOrder companyApiOrder = CompanyApiOrder.dao.findFirst(" select * from company_apiorder  where order_no='" + apiOrderID + "'  ");

                        //重写数据到话费单
                        companyApiOrder.setReturnUrl(companyOrder.getReturnUrl());
                        companyApiOrder.update();

                        //回调API
                        runnable=new RunnableNoticeApiOrder(companyApiOrder);
                        try {
                            fun();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        /*String s3 = noticController.noticePhoneApi(companyApiOrder);
                        logger.info("联通回调话费API结果为--------------------------->" + s3);*/

                        //回调支付方
                        runnable=new RunnableNoticeOrder(companyOrder);
                        try {
                            fun();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        /*logger.info("联通回调支付方结果为--------------------------->" + s2);

                        if ("success".equals(s3)) {

                            Db.update("update company_apiorder set notice='回调成功' where order_no='" + apiOrderID + "' ");
                        }

                        if ("success".equals(s2)) {

                            Db.update("update company_order set notice='回调成功' where id=" + companyOrder.getId());
                        }*/
                    } else if (scripts.text().startsWith("充值中")) {

                        logger.info("充值异常");
                        //订单号
                        Elements orderId = documents.getElementById("actiOrderNo").select("span");
                        //跟新订单状态

                        Db.update("update company_order set status='已支付',token=? where id=?", orderId.text(), companyOrder.getId());
                        Db.update("update company_apiorder set status='充值异常',token=? where order_no=?", orderId.text(), companyOrder.getAlipayName());
                        companyOrder.setToken(orderId.text());
                        companyOrder.setStatus("充值异常");

                        String apiOrderID = companyOrder.getAlipayName();
                        CompanyApiOrder companyApiOrder = CompanyApiOrder.dao.findFirst(" select * from company_apiorder  where order_no='" + apiOrderID + "'  ");

                        //重写数据到话费单
                        companyApiOrder.setReturnUrl(companyOrder.getReturnUrl());
                        companyApiOrder.update();

                        //回调API
                        runnable=new RunnableNoticeApiOrder(companyApiOrder);
                        try {
                            fun();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        /*String s3 = noticController.noticePhoneApi(companyApiOrder);
                        logger.info("联通回调话费API结果为--------------------------->" + s3);*/

                        //回调支付方
                        runnable=new RunnableNoticeOrder(companyOrder);
                        try {
                            fun();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        /*logger.info("联通回调支付方结果为--------------------------->" + s2);
                        if ("success".equals(s3)) {
                            Db.update("update company_apiorder set notice='回调成功' where order_no='" + apiOrderID + "' ");
                        }

                        if ("success".equals(s2)) {
                            Db.update("update company_order set notice='回调成功' where id=" + companyOrder.getId());
                        }*/

                    }


                }
            }
        }

    }

    public static String GetIp(String noticeIp) {

        String s = HttpClientFactory.get(noticeIp);
        return s;
    }

    public void fun() throws Exception {

        executor.submit(runnable);
    }

    public static void main(String[] args) {

        //String url="https://unipay.10010.com/udpNewPortal/miniwappayment/weixinH5Return?payStr=uawKZkotZ%2B8HrCoHoCw8xHj%2F3cpcRCxvHAucS77GekgTY2UYcVPwp19fDP5GgO9D0hRxofBTguq8dZ3KL1Z00%2Fxl%2F4a9U6QK";
        // String url="https://unipay.10010.com/udpNewPortal/miniwappayment/weixinH5Return?payStr=XWxUXN6FryvAzhQHcu6xRf7pVzQ9n%2Fc7G4k5LnVRy6NFU0tmrB847pD7gJNXEcWfH9aiCui0362JRWf78mr9j%2F6USa2A94Ca";
        //  	 String url="https://unipay.10010.com/udpNewPortal/miniwappayment/weixinH5Return?payStr=tVySGnMoxbN2mGK%2FNttxDrKwfBC4g1Fel%2FwkHGEXDBqo%2BDzCunYtld6t%2B3KdqNSuFSZ082BR4I58c1gpAXxinCOUdafnWZIb";

	   	 String url="https://unipay.10010.com/udpNewPortal/miniwappayment/weixinH5Return?payStr=1whU3Png42gX2FzT20X%2Bj2h6e6wALKY83CiTlgrdI2iobdUQHDiCwFVHsqAQzHN2WAE12nwC9NXU9ucrdvLXjXnPqUqqStLQ";
	   	 String form = HttpClientHelper.sendGet(url);
	   	 Document document = Jsoup.parse(form);
         //订单状态
         Elements servicePlatID = document.select("input[name='ServicePlatID']");
         Elements payRspObj = document.select("input[name='PayRspObj']");
         Elements province = document.select("input[name='province']");
         Elements hsnduns = document.select("input[name='hsnduns']");
         Elements s = document.select("input[name='s']");

         if(servicePlatID.size()==0){


         }

         String payurl="https://upay.10010.com/npfwap/NpfMobCB/mobPayFeeCB/PayFeeCBFinish";
         Map map=new HashMap();
         map.put("PayRspObj", payRspObj.val());
         map.put("ServicePlatID",servicePlatID.val());
         map.put("province",province.val());
         map.put("hsnduns",hsnduns.val());
         map.put("s",s.val());

         String baiIp = HttpClientHelper.sendGet("http://api.wandoudl.com/api/ip?app_key=3f2cb2a6af6b0c0925d528c0132283bc&pack=213566&num=1&xy=1&type=1&lb=T&mr=1&");
         String  ip=baiIp.split(":")[0];
         Integer port=Integer.parseInt(baiIp.split(":")[1]);
         String s1 = HttpClientHelperCucc.sendPost(payurl, map,null,0);

         Document documents = Jsoup.parse(s1);
         Elements scripts = documents.getElementsByClass("enlarge").select("span");
         if(scripts.text().startsWith("充值中")) {
             Elements orderId = documents.getElementById("actiOrderNo").select("span");
        	 System.out.println("11");
        	 System.out.println(orderId.text());
         }
         System.out.println(scripts.text());
	   	 System.out.println(form);

//        String s = GetIp("http://webapi.http.zhimacangku.com/getip?num=1&type=1&pro=510000&city=0&yys=0&port=1&time=1&ts=0&ys=0&cs=0&lb=1&sb=0&pb=4&mr=1&regions=");
//        System.out.println(s);

    }


}
