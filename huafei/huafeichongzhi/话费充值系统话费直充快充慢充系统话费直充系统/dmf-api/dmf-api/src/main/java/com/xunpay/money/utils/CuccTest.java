package com.xunpay.money.utils;


import com.jfinal.plugin.activerecord.Db;
import com.xunpay.money.model.Coke;
import com.xunpay.money.model.CompanyIp;
import com.xunpay.money.model.PhoneDelete;

import net.sf.json.JSONObject;

import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.HttpHostConnectException;

import java.io.EOFException;
import java.io.IOException;
import java.util.*;

public class CuccTest {

    //根据手机号和金额下单
    //新科令牌，提高下单成功率
    public static String topUp(String phone, String money, int number, CompanyIp companyIp) {

        //获取cookie先
        //String coke=Db.queryStr("select coke  from coke limit 1 ");
        String coke = "upay_user=e2e162196874115963af6c66d481aba5";
        //select coke  from coke
        System.out.println("联通下单手机号------->" + phone);

        if (number > 2) {
            return null;
        }
        Map restMap = new HashMap();
        //预下单
        String reservationUrl = "http://upay.10010.com/npfwap/NpfMob/mobWapBankPay/mobWapPayFeeCheck.action";

        Map paramMap = new HashMap();

        /*paramMap.put("commonBean.phoneNo", phone);
        paramMap.put("commonBean.areaCode", "");
        paramMap.put("commonBean.provinceCode", "074");
        paramMap.put("commonBean.cityCode", "795");
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
        paramMap.put("emergencyContact", "");
        paramMap.put("commonBean.ticketNo", "");
        paramMap.put("commonBean.reserved2", "");
        paramMap.put("ticketNew", "epasXvzBzNoxZPGvlSj-iGPNx8-Yi3tde0VxOW4Wt3cf5F4FFY02ImvBQP1czA8wIveUaZqrA1A*");
        paramMap.put("commonBean.numberAttribution", "");
        paramMap.put("commonBean.token", "");
        paramMap.put("commonBean.urlSign", "");
        paramMap.put("commonBean.msgTimeStamp", "");
        paramMap.put("commonBean.serviceNo", "");
        paramMap.put("commonBean.natCode", "");
        paramMap.put("commonBean.saleChannel", "");
        paramMap.put("commonBean.deviceId", "");
        paramMap.put("commonBean.model", "");
        paramMap.put("commonBean.vipCode", "");
        paramMap.put("commonBean.activityOrderId", "");
        paramMap.put("joinSign", "");
        paramMap.put("presentActivityId", "");*/

        String s = HttpClientFactory.get("https://upay.10010.com/npfwap/npfMobWeb/getArea/init?callback=jQuery214011335515604044777_1587011645778&commonBean.phoneNo="+phone+"&_=1587011645791");
        String area=s.split(":")[1].split("X")[0].substring(2);
        String city =s.split(":")[1].split("X")[1];
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
        paramMap.put("emergencyContact", "");
        paramMap.put("commonBean.ticketNo", "");
        paramMap.put("commonBean.reserved2", "");
        paramMap.put("ticketNew", "");
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


        String reservationResult = "";
        Map map = new HashMap();
        String result = null;

        try {
            //获取cokie
            // Date date=new Date();
            //    String a=HttpCucc.getCokie("https://upay.10010.com/npfwap/NpfMob/needCode?channelType=307&_="+date.getTime(),companyIp.getIp(),companyIp.getPort());
            //  String  cokie=a.split(";")[0].substring(a.split(";")[0].indexOf(":")+1).trim();
            //  System.out.println("cokie-------->"+cokie);
            //    System.out.println("数据库中获取的cokie-------->"+coke);
            //  if(coke==null) {

            // coke=Db.queryStr(" select coke  from coke limit 1 ");
            //   }
            result = HttpCucc.sendPostTopUp(reservationUrl, paramMap, null, 0, coke,null);
            //result = HttpCucc.sendPostTopUp(reservationUrl, paramMap, "", 0, coke);
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
                Coke cokeF = Coke.dao.findFirst("select * from coke where coke='" + coke + "'");
                cokeF.delete();

                throw new NullPointerException();
            }
        } catch (ConnectTimeoutException io) {

            number++;
           /* Db.deleteById("company_ip", companyIp.getId());
            CompanyIp companyIps=CompanyIp.dao.findFirst("select * from company_ip where isU<3 order by  isU ");*/
            return topUp2(phone, money, number, companyIp);
        } catch (NullPointerException i) {
            number++;
           /* CompanyIp companyIps=CompanyIp.dao.findFirst("select * from company_ip where isU<3 order by  isU ");
            Db.update("update company_ip set isU=isU+1  where id="+companyIps.getId());*/
            return topUp2(phone, money, number, companyIp);
        } catch (NumberFormatException i) {
            number++;
            try {
                Thread.sleep(3000);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
            /* CompanyIp companyIps=CompanyIp.dao.findFirst("select * from company_ip where isU<3 order by  isU ");
        	 
               Db.update("update company_ip set isU=isU+1  where id="+companyIps.getId());*/
            return topUp2(phone, money, number, companyIp);
        } catch (Exception e) {
            number++;
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
            return topUp2(phone, money, number, companyIp);

        }
        String payUrl = "";
        //返回success才算预下单成功
        if ("success".equals(reservationResult)) {
            String reservationOrderId = map.get("secstate").toString();//预下单号
            //拿到预下单号去下单 获取跳转支付链接
            payUrl = topUpTo(reservationOrderId, phone, null, 0);
            //payUrl = topUpTo(reservationOrderId, phone, "", 0);
            //restMap = toPayAndpayStr(payUrl, companyIp.getIp(), companyIp.getPort());
        }

        return payUrl;
    }

    //根据手机号和金额下单
    public static String topUp2(String phone, String money, int number, CompanyIp companyIp) {
        System.out.println("联通下单手机号------->" + phone);
        if (number > 3) {
            return null;
        }
        Map restMap = new HashMap();
        //预下单
        String reservationUrl = "https://upay.10010.com/npfwap/NpfMob/mobWapBankPay/mobWapPayFeeCheck.action";

        String s = HttpClientFactory.get("https://upay.10010.com/npfwap/npfMobWeb/getArea/init?callback=jQuery214011335515604044777_1587011645778&commonBean.phoneNo=" + phone + "&_=1587011645791");
        if (s.contains("您的操作太频繁，请稍后再试")){
            System.out.println("联通下单手机号限制:" + phone);
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
        paramMap.put("commonBean.payAmount", "19.98");
        paramMap.put("cardBean.cardValue", money + "00");
        paramMap.put("cardBean.cardValueCode", "01");
        paramMap.put("commonBean.userChooseMode", "0");
        paramMap.put("invoiceBean.is_mailing", "0");
        paramMap.put("invoiceBean.need_invoice", "0");
        paramMap.put("invoiceBean.invoice_type", "");
        paramMap.put("invoiceBean.id_cardno", "");
        paramMap.put("invoiceBean.invoice_head", "");
        paramMap.put("invoiceBean.card_type", "");
        paramMap.put("commonBean.reserved1", "false");
        paramMap.put("numberType", "");
        paramMap.put("commonBean.channelType", "307");
        paramMap.put("channelKey", "wxgz");
        paramMap.put("commonBean.bussineType", "90");
        paramMap.put("commonBean.netAccount", "");
        paramMap.put("pointBean.payMethod", "");
        paramMap.put("pointBean.pointNumber", "");
        paramMap.put("browserVersion", "");
        paramMap.put("commonBean.activityType", "");
        paramMap.put("commonBean.offerate", "0.999");
        paramMap.put("commonBean.offerateId", "");
        paramMap.put("commonBean.orgCode", "31");
        paramMap.put("commonBean.channelCode", "weixinH5");
        paramMap.put("emergencyContact", "");
        paramMap.put("commonBean.ticketNo", "");
        paramMap.put("commonBean.reserved2", "");
        paramMap.put("ticketNew", "ticket");
        paramMap.put("commonBean.numberAttribution", "0");
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
    		/*Date date=new Date();
    		String a=HttpCucc.getCokie2("https://upay.10010.com/npfwap/NpfMob/needCode?channelType=307&_="+date.getTime(),companyIp.getIp(),companyIp.getPort());
    		String  cokie=a.split(";")[0].substring(a.split(";")[0].indexOf(":")+1).trim();
    		System.out.println("cokie-------->"+cokie);*/
            result = HttpCucc.sendPostTopUp2(reservationUrl, paramMap, null,0, "upay_user=8280398e486d66b4154403569e598a6c");
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

            number++;
          /*  Db.deleteById("company_ip", companyIp.getId());
    		CompanyIp companyIps=CompanyIp.dao.findFirst("select * from company_ip where isU<3 order by  isU ");*/
            return topUp(phone, money, number, companyIp);
        } catch (NullPointerException i) {
            number++;
    		/*CompanyIp companyIps=CompanyIp.dao.findFirst("select * from company_ip where isU<3 order by  isU ");
    		 Db.update("update company_ip set isU=isU+1  where id="+companyIps.getId());*/
            return topUp(phone, money, number, companyIp);
        } catch (NumberFormatException i) {
            number++;
            try {
                Thread.sleep(5000);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
    		/*CompanyIp companyIps=CompanyIp.dao.findFirst("select * from company_ip where isU<3 order by  isU ");
    		
    		  Db.update("update company_ip set isU=isU+1  where id="+companyIps.getId());*/
            return topUp(phone, money, number, companyIp);
        } catch (Exception e) {

            number++;
            try {
                Thread.sleep(2000);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
            return topUp(phone, money, number, companyIp);
        }
        String payUrl = "";
        //返回success才算预下单成功
        if ("success".equals(reservationResult)) {
            String reservationOrderId = map.get("secstate").toString();//预下单号
            //拿到预下单号去下单 获取跳转支付链接
            payUrl = topUpTo2(reservationOrderId, phone, null,0);
            //restMap = toPayAndpayStr2(payUrl, companyIp.getIp(), companyIp.getPort());
        }

        return payUrl;
    }

    //正式下单 
    public static String topUpTo(String reservationOrderId, String phone, String ip, Integer port) {
        //  String upUrl = "http://upay.10010.com/npfwap/NpfMob/mobWapBankPay/mobWapPayFeeApply.action";
        String upUrl = "http://upay.10010.com/npfwap/NpfMob/mobWapBankPay/mobWapPayFeeApply.action";
        Map parMap = new HashMap();
        parMap.put("secstate.state", reservationOrderId);//预下单号
        //parMap.put("commonBean.phoneNo", phone);//手机号
        //parMap.put("ticketNew", "6CQu6Quz1KyS1EvpPpBZgokHOpQk2forfWfEX5W2dmSUwCEerEF-UckT2ExqmXebNZJuZyBTeWU*");
        String resultOreder = null;
        try {
            resultOreder = HttpCucc.post(upUrl, parMap, ip, port,null);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String payUrl = JSONObject.fromObject(resultOreder).get("payUrl").toString();
        return payUrl;
    }

    //正式下单 
    public static String topUpTo2(String reservationOrderId, String phone, String ip, Integer port) {
        //String upUrl = "https://upay.10010.com/npfwap/NpfMob/mobWapBankPay/mobWapPayFeeApply.action";
        String upUrl = "https://upay.10010.com/npfwap/NpfMob/mobWapBankCharge/wapBankChargeSubmit.action";
        Map parMap = new HashMap();
        parMap.put("secstate.state", reservationOrderId);//预下单号
        //parMap.put("commonBean.phoneNo", phone);//手机号
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
    public static Map toPayAndpayStr(String payUrl, String ip, Integer port) {
        Map map = new HashMap();
        String result = null;
        result = HttpCucc.sendGetPayUrl(payUrl, ip, port);
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

    //访问跳转充值链接获取微信深度链接以及payStr(用于监控订单状态)
    public static Map toPayAndpayStr2(String payUrl, String ip, Integer port) {
        Map map = new HashMap();
        String result = HttpCucc.sendGetPayUrl2(payUrl, ip, port);
//        String linkTemp = null;
//        String link = "";
//        String payStr = "";
//        if (result.indexOf("ok") != -1) {
//            //获取微信深度支付链接
//            String[] urlSplit = result.split("url=");
//
//            String temp = urlSplit[1];
//
//            int index = temp.indexOf("var");
//
//            linkTemp = temp.substring(1, index).trim();
//
//            int length = linkTemp.length();
//
//            link = linkTemp.substring(0, length - 2);
//
//            //获取payStr
//            String[] strurlit = result.split("redirect_url=");
//            String strtemps = strurlit[1];
//            int strindexs = strtemps.indexOf(";");
//
//            String str = strtemps.substring(1, strindexs).trim();
//
//            int strlengths = str.length();
//
//            String[] Str = str.substring(0, strlengths - 1).split("\\?");
//            payStr = Str[1].substring(Str[1].indexOf("=") + 1);
//        }
        map.put("url", result);
        map.put("payStr", result);
        return map;
    }


    public static void main(String[] art) throws IOException {
       /* String a="https://unipay.10010.com/udpNewPortal/payment/directPay?ServicePlatID=11&PayReqObj=KjMjI85mHW5axgIqXA6SEm%2F03GNJlFYuk3dqcVQvkTXVZ%2BNz35CWwTtSpKyYiCf07EeGw%2FwZM%2FYHwsDrbCk6XlVCWaes81RqNGMsfaPa4SIHbMR7e3znDR6HypiKrsTi3IVDdAckMxTtVHLhOnei6d7JZTfwbJS5U7CJA927Ip3vpGl0MMHziiFgRCJzsF4dL%2FcNOAzYTDYrf1iZq2g29mqD0UNbnnyXgOSoO2xRxYpAYI%2BX%2FBk0ODUd%2Bc2Sr3bNo5k%2F66N4bflh2qfC3zd2RQXCLRrUHYm3slOyEKpCs5WcKZ65EyScwZ5Xmd0kKQmodFrS6wzRHaOFQL4IAqgzealOoaaLBlvZftWhKadgT98%2BCJNaeirc6UHDG9eOn4AWiUolGzB7U2UfOgaec9qLZ59TjTA2ygoWfd7sE765hPp%2FAO9CsE3ZJSIUv0u2s3WxXW7kAiOo1yAdk%2BpGEIsFq4ePEheri9gELRjgQg5fascbke5zJNgWCwRhZdKFLphHRVCGpZltKUsAtQQyYg3VeUfbarGcVMkEFZj2fXkmJbVPFuaWm4tqQEq60TfYO%2B2xyf4Np3HQHp%2B%2FAzQ0tJz3qTKVpL4R6UXsvY9c4sluPDOvq3Z7YCgRsrzpJGdF2Lj6TK3qI05idoqPr0ErL88fRdyRIxEN%2FaJJ%2F6wSNscoYeecwLUgT0%2B8rw9KJ2oWBrLsfBL69ovHUnnaqzEnZPvZvZQehiZeET2HhOf3iRSPxN4iqp%2FvwyXoX3MNHdHW3Lh3ChV7ZfA3QlWpOySbzzmoQmk2mq9urAx1xir1cN6XEDna%2FNJcBwi%2FEXUA%2Fb7vV2texMv2wnuZBCwzTnOqSCDMQayYIsQUwXPUdfz6AU02HgcA0mQhUg2JDEN%2B5tGHC34I6fsOpBkWy7uorGAwQzLr0jGQeyLiWTeXKLg7PSJyYAESy2Bno4rU3nUzbtIdDpLyhdpCvI%2Ba6I8ADI9gt9xkRc5LJ9DlN%2BFHT5hnOkZ%2FSBkmvj2odwHVlZQmx9wIUSe62smpOLoLzQwf8CSK%2F9xfT4CXUzRFyKE4ApBsjfBcIwni1q9xdHsiV7JUcFBIzvXWJcl%2BkXNZzRc7zZHxk9Cc3ryAzxorh0O%2FyGYSKAIyONLfYGadO3rwaOCEc1AF%2BNJ5gnAj0gDkQqBXnDLeKUQE3cUuyI7Kzd9MkILEpeP7YJHGIfWDjW%2FxdDKL7LNnDzzslO8toU4A0xB0J6XMKeo7qMaWNP69Bkt2MUnWKmDhzK5mbBfSFxGeA8qb4uNvFTHHqMjYxxbU115l3Eck4CIlphdOs9vnEEPPDZqmH2klpUPuq9ToKTQTYcREeUKJNiBAQSv1k%2BXUnPUyk3ZZP944IkuFUfc1uQLt0famseAfkXg%3D&pageType=07";

        String x=a.split("\\?")[1].split("&")[0];
        String b=a.split("\\?")[1].split("&")[1];
        String c=a.split("\\?")[1].split("&")[2];


        String servicePlatID=x.substring(x.indexOf("=")+1);
        String payReqObj=b.substring(b.indexOf("=")+1);
        String pageType=c.substring(c.indexOf("=")+1);
        System.out.println(servicePlatID+"-------"+payReqObj+"--------"+pageType);*/

//        String s = topUp("17501609937", "20", 1, null);
//        System.out.println(s);
//        Map map = toPayAndpayStr(s, "", 0);
//        System.out.println(map.get("payStr"));
//        System.out.println(map.get("url"));

/*
        String s = HttpClientFactory.get("https://upay.10010.com/npfwap/npfMobWeb/getArea/init?callback=jQuery214006743604835499428_1602742652010&commonBean.phoneNo=18512318032&_=1602742652013");

        if (s.contains("您的操作太频繁，请稍后再试")){
            System.out.println("号码限制");
        }else {
            String area = s.split(":")[1].split("X")[0].substring(2);
            String city = s.split(":")[1].split("X")[1];
            System.out.println("1111:"+area);
            System.out.println("2222:"+city);
        }*/

        String s = topUp2("18381833437", "20", 0, null);
        Map map = toPayAndpayStr2(s, "114.55.136.158", 21443);
        System.out.println("地址为："+map.get("url"));

    }


}
