package com.xunpay.money.utils;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.Map;

import com.jfinal.plugin.activerecord.Db;
import com.xunpay.money.model.CmccCk;
import com.xunpay.money.model.CompanyApiOrder;
import com.xunpay.money.model.CompanyIp;
import com.xunpay.money.model.CompanyOrder;
import com.xunpay.money.model.SysConfig;

public class RunnableUtil extends Thread {


    private String tradeNo;

    private BigDecimal money;

    private String appid;

    private String orderNo;

    private Integer id;

    private Date times;

    private CompanyIp companyIp;

    private CmccCk cmccCk;


    public RunnableUtil(String tradeNo, BigDecimal money, String appid, String orderNo, Integer id, Date times, CompanyIp companyIp, CmccCk cmccCk) {
        this.tradeNo = tradeNo;
        this.money = money;
        this.appid = appid;
        this.orderNo = orderNo;
        this.id = id;
        this.times = times;
        this.companyIp = companyIp;
        this.cmccCk = cmccCk;
    }


    public void run() {
        CompanyOrder order = new CompanyOrder();
        String currentOrder = "S" + System.currentTimeMillis() + (long) (Math.random() * 1.0E7D);
        String payUrl = "";
        String sessionId = "";

        CompanyApiOrder apiOrder = CompanyApiOrder.dao.findById(id);

        apiOrder.setRsaPrivate(companyIp.getIp());

        apiOrder.update();

        /**
         * 实施生产 1移动  2联通  3电信
         * **/
        int successT = 1;
        long x = System.currentTimeMillis();
        switch (Integer.valueOf(tradeNo)) {
            //移动
            case 1:
//                //硬编码
//                SysConfig sysconfig = SysConfig.dao.findFirst(" select * from sys_config  where code='risk' ");
//                // String risk=
//                String userName = sysconfig.getContent();//"2Ekg8zdp0P0GUbosRYInUw==";
//                String cmccToken = "";
//                if (cmccCk != null) {
//                    cmccToken = cmccCk.getSessionId();//"jsessionid-cmcc=nED0E568B262558BA56463ACD289009D1-1";
//                } else {
//
//                    cmccToken = "jsessionid-cmcc=nED0E568B262558BA56463ACD289009D1-1";
//                }
//                CmccTest cmccTest = new CmccTest();
//                CmccUtil util = new CmccUtil();
//
//                //看看是否有优惠
//                boolean isYh = util.isYh(appid);
//                //下单
//                String payRul = cmccTest.saveOrder(appid, money.doubleValue(), cmccToken, isYh, companyIp.getIp(), companyIp.getPort());
//                //下单失败的情况则再次换ck下单
//                if (payRul != null) {
//                    order.setTradeNo("1");
//                    order.setRemark(payRul);
//                    order.setReturnUrl("");
//
//                    String location = util.ssoCheckHtml(payRul);
//                    Map maps = util.getHtmlParam(location);
//                    Map<String, String> bodyMap = cmccTest.payAndH5(maps, userName);
//                    String body = bodyMap.get("body");
//                    String orderId = bodyMap.get("orderId");
//                    String noticeUrl = bodyMap.get("noticeUrl");
//                    String voucherUrl = bodyMap.get("voucherUrl");
//                    //下载
//                    util.downLoad(body, orderId);
//
//                    payUrl = orderId + ".jsp";
//                    order.setToken(orderId);
//                    order.setReturnUrl(noticeUrl);//查单地址
//                    order.setRsaAlipay(voucherUrl);//最终的凭证地址
//                } else {
//                    //轮回再次获取ck
//                    CmccCk ck2 = CmccCk.dao.findFirst(" select * from cmcc_ck  where isU<10 order by  id desc     ");
//                    String payU = cmccTest.saveOrder(appid, money.doubleValue(), ck2.getSessionId(), isYh, companyIp.getIp(), companyIp.getPort());
//                    if (payU == null) {
//                        successT = 0;
//                        break;
//                    }
//                    order.setTradeNo("1");
//                    order.setRemark(payRul);
//                    order.setReturnUrl("");
//
//                    String location = util.ssoCheckHtml(payU);
//                    Map maps = util.getHtmlParam(location);
//                    Map<String, String> bodyMap = cmccTest.payAndH5(maps, userName);
//                    String body = bodyMap.get("body");
//                    String orderId = bodyMap.get("orderId");
//                    String noticeUrl = bodyMap.get("noticeUrl");
//                    String voucherUrl = bodyMap.get("voucherUrl");
//                    //下载
//                    util.downLoad(body, orderId);
//
//                    payUrl = orderId + ".jsp";
//                    order.setToken(orderId);
//                    order.setReturnUrl(noticeUrl);//查单地址
//                    order.setRsaAlipay(voucherUrl);//最终的凭证地址
//                }
                order.setTradeNo("1");
                break;

            case 2:
                /*CuccTest cuccTest = new CuccTest();
                DecimalFormat decimalFormat = new DecimalFormat("0.00");
                String moneys = decimalFormat.format(money);
                String url = cuccTest.topUp(appid, moneys, 0, companyIp);
                if (url != null) {

                    order.setRemark(url);
                    order.setTradeNo("2");
                    break;
                } else {
                    successT = error();
                    break;
                }*/
               /*payUrl = cuccMap.get("url").toString();
                order.setReturnUrl("https://unipay.10010.com/udpNewPortal/miniwappayment/weixinH5Return?payStr=" + cuccMap.get("payStr").toString());*/
                order.setTradeNo("2");
                break;
            case 3:
               /* CtccTest2 ctcc = new CtccTest2();
                Integer m = money.intValue();
                String money = m.toString();
                String sesid = ctcc.getSessionId2();
                System.out.print("sesid---->" + sesid);
                Map<String, String> map = ctcc.topUp(appid, money, 0, companyIp, sesid);
                sessionId = map.get("sessionid");
                payUrl = map.get("payUrl");
                String orderid = map.get("orderid");
                order.setReturnUrl("http://wapzt.189.cn:8010/rechargeV2/rechargeV2_success.html?sessionid=" + sessionId + "&shopid=20001&channel=wap&ct=0&code=-10008&islogin=false");
                //order.setReturnUrl("https://wappay.189.cn/pay/toPayQuery.do?boId=" + sessionId);
                order.setToken(orderid);*/
                order.setTradeNo("3");
                break;
        }

        if (successT != 0) {

            long y = System.currentTimeMillis();
            long logtime = y - x;


            System.out.println("下单处理时长-----------------" + logtime + "毫秒");
            System.out.println(payUrl);
            //手机号码
            order.setAppid(appid);
            order.setTitle("支付系统下单");
            order.setOrderNo(currentOrder);
            order.setOrderMoney(money);
            order.setRsaPrivate(companyIp.getIp());
            order.setPid(sessionId);   //电信查询需要的session
            //  order.setPayType("wechat_h5_pdd");
            order.setStatus("匹配中");
            order.setNotice("未回调");
            order.setQrUrl(payUrl);
            Date time = new Date();
            order.setAddtime(time);

            //溯源订单号
            order.setAlipayName(orderNo);
            //初始化时间
            order.setNoticetime(times);
            order.save();

            Db.update("update company_apiorder set noticetime=? where id=?", time, id);
            Db.update("update company_apiorder set status='匹配中' where id=" + id);
        }
    }

    public static int error() {
        System.out.println("没有正常产生二维码");
        return 0;
    }

}
