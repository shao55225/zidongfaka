package com.xunpay.money.utils;


import com.jfinal.plugin.activerecord.Db;
import com.xunpay.money.controller.OrderController;
import com.xunpay.money.model.Coke;
import com.xunpay.money.model.CompanyIp;
import com.xunpay.money.model.PhoneDelete;

import net.sf.json.JSONObject;

import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.HttpHostConnectException;

import java.io.EOFException;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.*;

public class CuccTest2 {

    //根据手机号和金额下单
    //新科令牌，提高下单成功率
    public static String topUp(String phone, String money, int number, CompanyIp companyIp, String userIp) {
        //System.out.println("ip:" + companyIp.getIp());
        //获取cookie先
        //String coke=Db.queryStr("select coke  from coke limit 1 ");
        String coke = "upay_user=958c268682bd2b1d897ff2be59edc740";
        System.out.println("联通下单手机号------->" + phone);

      /*  String chn="";

        if("20.00".equals(money)){
            chn="01";
        }else if ("30.00".equals(money)){
            chn="02";
        }else if ("50.00".equals(money)){
            chn="03";
        }else if ("100.00".equals(money)){
            chn="04";
        }else if ("300.00".equals(money)){
            chn="05";
        }*/


        if (number > 1) {
            return null;
        }
        Map restMap = new HashMap();
        //预下单
        String reservationUrl = "http://upay.10010.com/npfwap/NpfMob/mobWapBankPay/mobWapPayFeeCheck.action";
        //String reservationUrl = "https://upay.10010.com/npfwap/NpfMob/mobWapBankCharge/wapBankChargeCheck.action";

        String s = HttpClientFactory.get("https://upay.10010.com/npfwap/npfMobWeb/getArea/init?callback=jQuery214011335515604044777_1587011645778&commonBean.phoneNo=" + phone + "&_=1587011645791");
        if (!s.contains("success")){

            return null;
        }
        String area = s.split(":")[1].split("X")[0].substring(2);
        String city = s.split(":")[1].split("X")[1];

        Map paramMap = new HashMap();
        paramMap.put("commonBean.phoneNo", phone);
        paramMap.put("commonBean.areaCode", "");
        paramMap.put("commonBean.provinceCode", area);
        paramMap.put("commonBean.cityCode", city);
        paramMap.put("bussineTypeInput", "0");
        paramMap.put("commonBean.payAmount", money);
        /*paramMap.put("cardBean.cardValue", money+"00");
        paramMap.put("cardBean.cardValueCode", chn);*/
        paramMap.put("cardBean.cardValue", "");
        paramMap.put("cardBean.cardValueCode", "");
        paramMap.put("commonBean.userChooseMode", "");
        paramMap.put("commonBean.reserved1", "false");
        paramMap.put("numberType", "");
        paramMap.put("commonBean.channelType", "307");
        paramMap.put("channelKey", "");
        paramMap.put("commonBean.bussineType", "01");
        paramMap.put("commonBean.netAccount", "");
        paramMap.put("pointBean.payMethod", "");
        paramMap.put("pointBean.pointNumber", "");
        paramMap.put("browserVersion", "");
        paramMap.put("commonBean.activityType", "");
        paramMap.put("commonBean.offerate", "");
        paramMap.put("commonBean.offerateId", "");
        paramMap.put("commonBean.orgCode", "31");
        paramMap.put("commonBean.channelCode", "weixinH5");
        //paramMap.put("commonBean.channelCode", "weixinPublic");
        paramMap.put("emergencyContact", "");
        paramMap.put("commonBean.ticketNo", "");
        paramMap.put("commonBean.reserved2", "");
        paramMap.put("ticketNew", "ticket");
        paramMap.put("commonBean.numberAttribution", "");
        paramMap.put("commonBean.token", "");
        paramMap.put("commonBean.urlSign", "");
        paramMap.put("commonBean.msgTimeStamp", "");
        paramMap.put("commonBean.serviceNo", "");
        paramMap.put("commonBean.natCode", "");
        paramMap.put("commonBean.saleChannel", "null");
        paramMap.put("commonBean.deviceId", "null");
        paramMap.put("commonBean.model", "null");
        paramMap.put("commonBean.vipCode", "null");
        paramMap.put("commonBean.activityOrderId", "");


        String reservationResult = "";
        Map map = new HashMap();
        String result = null;

        try {

            result = HttpCucc.sendPostTopUp(reservationUrl, paramMap, companyIp.getIp(),companyIp.getPort(), coke,userIp);
            //result = HttpCucc.sendPostTopUp(reservationUrl, paramMap, null, 0, coke, userIp);
            System.out.println(result);
            map = JSONObject.fromObject(result);
            reservationResult = map.get("out").toString();//预下单返回状态
            System.out.println(phone + "------------------------->" + reservationResult);

            if (reservationResult.indexOf("系统繁忙") != -1) {
                throw new Exception();
            }
            if ("您的操作太频繁，请稍后再试！".equals(reservationResult)) {
                throw new NumberFormatException();
            }
        } catch (ConnectTimeoutException io) {
            String province = null;
            for (int i = 0; i < 10; i++) {
                //ip省份
                province = OrderController.getIp(userIp);
                if (province != null) {
                    break;
                }
            }
            String gotoip = province.split(":")[0];
            //下单端口
            Integer port = Integer.parseInt(province.split(":")[1].trim());

            companyIp.setIp(gotoip);
            companyIp.setPort(port);
            return topUp(phone, money, number, companyIp, userIp);
        } catch (NumberFormatException i) {
            number++;
            try {
                Thread.sleep(5000);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
            String province = null;
            for (int j = 0; j < 10; j++) {
                //ip省份
                province = OrderController.getIp(userIp);
                System.out.println("匹配到省份的ip第" + j + "次:" + province);
                if (province != null) {
                    break;
                }
            }
            String gotoip = province.split(":")[0];
            //下单端口
            Integer port = Integer.parseInt(province.split(":")[1].trim());

            companyIp.setIp(gotoip);
            companyIp.setPort(port);
            return topUp(phone, money, number, companyIp, userIp);
        } catch (Exception e) {
            number++;
            try {
                Thread.sleep(2000);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
            return topUp(phone, money, number, companyIp, userIp);

        }
        String payUrl = "";
        //返回success才算预下单成功
        if ("success".equals(reservationResult)) {
            String reservationOrderId = map.get("secstate").toString();//预下单号
            //拿到预下单号去下单 获取跳转支付链接
            payUrl = topUpTo(reservationOrderId, phone, companyIp.getIp(),companyIp.getPort(),userIp);
           // payUrl = topUpTo(reservationOrderId, phone, null, 0, userIp);
            //restMap = toPayAndpayStr(payUrl, companyIp.getIp(), companyIp.getPort());
        }

        return payUrl;
    }

    //根据手机号和金额下单
    public static String topUp2(String phone, String money, int number, CompanyIp companyIp, String userIp) {
        System.out.println("联通下单手机号------->" + phone);
        if (number > 3) {
            return null;
        }
        Map restMap = new HashMap();
        //预下单
        String reservationUrl = "https://upay.10010.com/npfwap/NpfMob/mobWapBankPay/mobWapPayFeeCheck.action";

        String s = HttpClientFactory.get("https://upay.10010.com/npfwap/npfMobWeb/getArea/init?callback=jQuery214011335515604044777_1587011645778&commonBean.phoneNo=" + phone + "&_=1587011645791");
        String area = s.split(":")[1].split("X")[0].substring(2);
        String city = s.split(":")[1].split("X")[1];

        Map paramMap = new HashMap();
        paramMap.put("commonBean.phoneNo", phone);
        paramMap.put("commonBean.areaCode", "");
        paramMap.put("commonBean.provinceCode", area);
        paramMap.put("commonBean.cityCode", city);
        paramMap.put("bussineTypeInput", "0");
        paramMap.put("commonBean.payAmount", money);
        paramMap.put("cardBean.cardValue", "");
        paramMap.put("cardBean.cardValueCode", "");
        paramMap.put("commonBean.userChooseMode", "");
        paramMap.put("invoiceBean.is_mailing", "0");
        paramMap.put("invoiceBean.need_invoice", "0");
        paramMap.put("invoiceBean.invoice_type", "");
        paramMap.put("invoiceBean.id_cardno", "");
        paramMap.put("invoiceBean.invoice_head", "");
        paramMap.put("invoiceBean.card_type", "");
        paramMap.put("commonBean.reserved1", "false");
        paramMap.put("numberType", "");
        paramMap.put("commonBean.channelType", "307");
        paramMap.put("channelKey", "wxgz2");
        paramMap.put("commonBean.bussineType", "01");
        paramMap.put("commonBean.netAccount", "");
        paramMap.put("pointBean.payMethod", "");
        paramMap.put("pointBean.pointNumber", "");
        paramMap.put("browserVersion", "");
        paramMap.put("commonBean.activityType", "");
        paramMap.put("commonBean.offerate", "");
        paramMap.put("commonBean.offerateId", "");
        paramMap.put("commonBean.orgCode", "31");
        paramMap.put("commonBean.channelCode", "weixinH5");
        paramMap.put("emergencyContact", "");
        paramMap.put("commonBean.ticketNo", "");
        paramMap.put("commonBean.reserved2", "");
        paramMap.put("ticketNew", "ticket");
        paramMap.put("commonBean.numberAttribution", "");
        paramMap.put("commonBean.token", "");
        paramMap.put("commonBean.urlSign", "");
        paramMap.put("commonBean.msgTimeStamp", "");
        paramMap.put("commonBean.serviceNo", "");
        paramMap.put("commonBean.natCode", "");
        paramMap.put("commonBean.saleChannel", "null");
        paramMap.put("commonBean.deviceId", "null");
        paramMap.put("commonBean.model", "null");
        paramMap.put("commonBean.vipCode", "null");
        paramMap.put("commonBean.activityOrderId", "");


        String reservationResult = "";
        Map map = new HashMap();
        String result = null;

        try {
            //获取cokie
            Date date = new Date();
            String a = HttpCucc.getCokie2("https://upay.10010.com/npfwap/NpfMob/needCode?channelType=307&_=" + date.getTime(), companyIp.getIp(), companyIp.getPort());
            String cokie = a.split(";")[0].substring(a.split(";")[0].indexOf(":") + 1).trim();
            System.out.println("cokie-------->" + cokie);
            result = HttpCucc.sendPostTopUp2(reservationUrl, paramMap, companyIp.getIp(), companyIp.getPort(), "upay_user=958c268682bd2b1d897ff2be59edc740");
            System.out.println(result);
            map = JSONObject.fromObject(result);
            reservationResult = map.get("out").toString();//预下单返回状态
            System.out.println(phone + "------------------------->" + reservationResult);

            if (reservationResult.indexOf("系统繁忙") != -1) {
                throw new Exception();
            }
            if ("您的操作太频繁，请稍后再试！".equals(reservationResult)) {
                throw new NumberFormatException();
            }
            if ("验证码校验异常!".equals(reservationResult)) {
                throw new NullPointerException();
            }
        } catch (IOException io) {
            Db.deleteById("company_ip", companyIp.getId());
            number++;
            CompanyIp companyIps = CompanyIp.dao.findFirst("select * from company_ip where isU<3 order by  isU ");
            return topUp(phone, money, number, companyIps, userIp);
        } catch (NullPointerException i) {
            number++;
            CompanyIp companyIps = CompanyIp.dao.findFirst("select * from company_ip where isU<3 order by  isU ");
            Db.update("update company_ip set isU=isU+1  where id=" + companyIps.getId());
            return topUp(phone, money, number, companyIps, userIp);
        } catch (NumberFormatException i) {
            number++;
            try {
                Thread.sleep(5000);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
            CompanyIp companyIps = CompanyIp.dao.findFirst("select * from company_ip where isU<3 order by  isU ");

            Db.update("update company_ip set isU=isU+1  where id=" + companyIps.getId());
            return topUp(phone, money, number, companyIps, userIp);
        } catch (Exception e) {

            number++;
            try {
                Thread.sleep(2000);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
            return topUp(phone, money, number, companyIp, userIp);
        }
        String payUrl = "";
        //返回success才算预下单成功
        if ("success".equals(reservationResult)) {
            String reservationOrderId = map.get("secstate").toString();//预下单号
            //拿到预下单号去下单 获取跳转支付链接
            //payUrl = topUpTo2(reservationOrderId, phone, companyIp.getIp(), companyIp.getPort());
            payUrl = topUpTo2(reservationOrderId, phone, null, 0);
            //restMap = toPayAndpayStr2(payUrl, companyIp.getIp(), companyIp.getPort());
        }

        return payUrl;
    }

    //正式下单 
    public static String topUpTo(String reservationOrderId, String phone, String ip, Integer port, String userIp) {
        String upUrl = "http://upay.10010.com/npfwap/NpfMob/mobWapBankPay/mobWapPayFeeApply.action";
        //String upUrl = "https://upay.10010.com/npfwap/NpfMob/mobWapBankCharge/wapBankChargeSubmit.action";
        Map parMap = new HashMap();
        parMap.put("secstate.state", reservationOrderId);//预下单号
        String resultOreder = null;
        try {
            resultOreder = HttpCucc.post(upUrl, parMap, ip, port, userIp);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String payUrl = JSONObject.fromObject(resultOreder).get("payUrl").toString();
        return payUrl;
    }

    //正式下单 
    public static String topUpTo2(String reservationOrderId, String phone, String ip, Integer port) {
        String upUrl = "https://upay.10010.com/npfwap/NpfMob/mobWapBankPay/mobWapPayFeeApply.action";
        Map parMap = new HashMap();
        parMap.put("secstate.state", reservationOrderId);//预下单号
        String resultOreder = null;
        try {
            resultOreder = HttpCucc.post2(upUrl, parMap, ip, port);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String payUrl = JSONObject.fromObject(resultOreder).get("payUrl").toString();
        return payUrl;
    }


    //访问跳转充值链接获取微信深度链接以及payStr(用于监控订单状态)
    public static Map toPayAndpayStr(String payUrl, String ip, Integer port, String userIp) {
        System.out.println("ip:" + ip);
        Map map = new HashMap();

        String lot = HttpCucc.sendGetPayUrl(payUrl, ip, port);

        String result = null;
        result = HttpCucc.sendPayUrl(lot, ip, port, userIp);

        String linkTemp = null;
        String link = "";
        String payStr = "";
        if (result.indexOf("ok") != -1) {
            //获取微信深度支付链接
            String[] urlSplit = result.split("url=");

            String temp = urlSplit[1];

            int index = temp.indexOf("var");

            linkTemp = temp.substring(1, index).trim();

            int length = linkTemp.length();

            link = linkTemp.substring(0, length - 2);

            //获取payStr
            String[] strurlit = result.split("redirect_url=");
            String strtemps = strurlit[1];
            int strindexs = strtemps.indexOf(";");

            String str = strtemps.substring(1, strindexs).trim();
            int strlengths = str.length();

            String[] Str = str.substring(0, strlengths - 1).split("\\?");
            payStr = Str[1].substring(Str[1].indexOf("=") + 1);
            //payStr = str.replace("\"","").trim();
        }
        map.put("url", link);
        map.put("payStr", payStr);
        return map;
    }

    //访问跳转充值链接获取微信深度链接以及payStr(用于监控订单状态)
    public static Map toPayAndpayStr2(String payUrl, String ip, Integer port) {
        Map map = new HashMap();
        String result = HttpCucc.sendGetPayUrl2(payUrl, ip, port);
        String linkTemp = null;
        String link = "";
        String payStr = "";
        if (result.indexOf("ok") != -1) {
            //获取微信深度支付链接
            String[] urlSplit = result.split("url=");

            String temp = urlSplit[1];

            int index = temp.indexOf("var");

            linkTemp = temp.substring(1, index).trim();

            int length = linkTemp.length();

            link = linkTemp.substring(0, length - 2);

            //获取payStr
            String[] strurlit = result.split("redirect_url=");
            String strtemps = strurlit[1];
            int strindexs = strtemps.indexOf(";");

            String str = strtemps.substring(1, strindexs).trim();

            int strlengths = str.length();

            String[] Str = str.substring(0, strlengths - 1).split("\\?");
            payStr = Str[1].substring(Str[1].indexOf("=") + 1);
        }
        map.put("url", link);
        map.put("payStr", payStr);
        return map;
    }


    public static void main(String[] art) throws IOException {
        /*String a="https://unipay.10010.com/udpNewPortal/payment/directPay?ServicePlatID=11&PayReqObj=KjMjI85mHW5axgIqXA6SEm%2F03GNJlFYuk3dqcVQvkTXVZ%2BNz35CWwTtSpKyYiCf07EeGw%2FwZM%2FYHwsDrbCk6XlVCWaes81RqNGMsfaPa4SIHbMR7e3znDR6HypiKrsTi3IVDdAckMxTtVHLhOnei6d7JZTfwbJS5U7CJA927Ip3vpGl0MMHziiFgRCJzsF4dL%2FcNOAzYTDYrf1iZq2g29mqD0UNbnnyXgOSoO2xRxYpAYI%2BX%2FBk0ODUd%2Bc2Sr3bNo5k%2F66N4bflh2qfC3zd2RQXCLRrUHYm3slOyEKpCs5WcKZ65EyScwZ5Xmd0kKQmodFrS6wzRHaOFQL4IAqgzealOoaaLBlvZftWhKadgT98%2BCJNaeirc6UHDG9eOn4AWiUolGzB7U2UfOgaec9qLZ59TjTA2ygoWfd7sE765hPp%2FAO9CsE3ZJSIUv0u2s3WxXW7kAiOo1yAdk%2BpGEIsFq4ePEheri9gELRjgQg5fascbke5zJNgWCwRhZdKFLphHRVCGpZltKUsAtQQyYg3VeUfbarGcVMkEFZj2fXkmJbVPFuaWm4tqQEq60TfYO%2B2xyf4Np3HQHp%2B%2FAzQ0tJz3qTKVpL4R6UXsvY9c4sluPDOvq3Z7YCgRsrzpJGdF2Lj6TK3qI05idoqPr0ErL88fRdyRIxEN%2FaJJ%2F6wSNscoYeecwLUgT0%2B8rw9KJ2oWBrLsfBL69ovHUnnaqzEnZPvZvZQehiZeET2HhOf3iRSPxN4iqp%2FvwyXoX3MNHdHW3Lh3ChV7ZfA3QlWpOySbzzmoQmk2mq9urAx1xir1cN6XEDna%2FNJcBwi%2FEXUA%2Fb7vV2texMv2wnuZBCwzTnOqSCDMQayYIsQUwXPUdfz6AU02HgcA0mQhUg2JDEN%2B5tGHC34I6fsOpBkWy7uorGAwQzLr0jGQeyLiWTeXKLg7PSJyYAESy2Bno4rU3nUzbtIdDpLyhdpCvI%2Ba6I8ADI9gt9xkRc5LJ9DlN%2BFHT5hnOkZ%2FSBkmvj2odwHVlZQmx9wIUSe62smpOLoLzQwf8CSK%2F9xfT4CXUzRFyKE4ApBsjfBcIwni1q9xdHsiV7JUcFBIzvXWJcl%2BkXNZzRc7zZHxk9Cc3ryAzxorh0O%2FyGYSKAIyONLfYGadO3rwaOCEc1AF%2BNJ5gnAj0gDkQqBXnDLeKUQE3cUuyI7Kzd9MkILEpeP7YJHGIfWDjW%2FxdDKL7LNnDzzslO8toU4A0xB0J6XMKeo7qMaWNP69Bkt2MUnWKmDhzK5mbBfSFxGeA8qb4uNvFTHHqMjYxxbU115l3Eck4CIlphdOs9vnEEPPDZqmH2klpUPuq9ToKTQTYcREeUKJNiBAQSv1k%2BXUnPUyk3ZZP944IkuFUfc1uQLt0famseAfkXg%3D&pageType=07";

        String x=a.split("\\?")[1].split("&")[0];
        String b=a.split("\\?")[1].split("&")[1];
        String c=a.split("\\?")[1].split("&")[2];


        String servicePlatID=x.substring(x.indexOf("=")+1);
        String payReqObj=b.substring(b.indexOf("=")+1);
        String pageType=c.substring(c.indexOf("=")+1);
        System.out.println(servicePlatID+"-------"+payReqObj+"--------"+pageType);*/


        /*long t = System.currentTimeMillis();
        String userIp="110.187.130.141";
        String s = topUp("15576584700", "1", 0, null,userIp);
        Map map = toPayAndpayStr(s, "114.55.136.158", 21443,userIp);
        System.out.println(s);
        System.out.println(map.get("url"));
        System.out.println(map.get("payStr"));
        long t2 = System.currentTimeMillis();
        Long sss = t2 - t;
        System.out.println("请求时间为" + sss);*/
        for (int i = 0; i < 500; i++) {
            String s = HttpClientFactory.get("https://upay.10010.com/npfwap/npfMobWeb/getArea/init?callback=jQuery214011335515604044777_1587011645778&commonBean.phoneNo=13042031860&_=1587011645791");
            if (!s.contains("success")){
                String area = s.split(":")[1].split("X")[0].substring(2);
                String city = s.split(":")[1].split("X")[1];
                System.out.println(s);
            }

        }

    }







}
