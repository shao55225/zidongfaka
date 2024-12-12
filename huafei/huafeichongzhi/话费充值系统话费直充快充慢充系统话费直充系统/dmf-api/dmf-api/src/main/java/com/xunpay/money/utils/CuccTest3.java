package com.xunpay.money.utils;


import com.jfinal.plugin.activerecord.Db;
import com.xunpay.money.model.Coke;
import com.xunpay.money.model.CompanyIp;
import net.sf.json.JSONObject;
import org.apache.http.conn.ConnectTimeoutException;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class CuccTest3 {

    //根据手机号和金额下单
    //新科令牌，提高下单成功率
    public static String topUp(String phone, String money, int number, CompanyIp companyIp) {
        //获取cookie先
        String coke = "upay_user=958c268682bd2b1d897ff2be59edc740";
        System.out.println("联通下单手机号------->" + phone);
        if (number > 1) {
            return null;
        }
        //预下单
        String reservationUrl = "https://upay.10010.com/npfwap/NpfMob/mobWapBankCharge/wapBankChargeCheck.action";
        String provUrl = HttpClientFactory.get("https://upay.10010.com/npfwap/npfMobWeb/getArea/init?callback=jQuery21408207682219567979_1604365531107&commonBean.phoneNo="+phone+"&_=1604365531114");

        String area = provUrl.split(":")[1].split("X")[0].substring(2);
        String city = provUrl.split(":")[1].split("X")[1];

        Map paramMap = new HashMap();
        paramMap.put("commonBean.phoneNo", phone);
        paramMap.put("commonBean.areaCode", "");
        paramMap.put("commonBean.provinceCode", area);
        paramMap.put("commonBean.cityCode", city);
        paramMap.put("bussineTypeInput", "0");
        paramMap.put("commonBean.payAmount", money);
        paramMap.put("cardBean.cardValue", money + "00");
        paramMap.put("cardBean.cardValueCode", "01");
        paramMap.put("commonBean.userChooseMode", "0");
        paramMap.put("commonBean.reserved1", "false");
        paramMap.put("numberType", "");
        paramMap.put("commonBean.channelType", "307");
        paramMap.put("channelKey", "");
        paramMap.put("commonBean.bussineType", "06");
        paramMap.put("commonBean.netAccount", "");
        paramMap.put("pointBean.payMethod", "");
        paramMap.put("pointBean.pointNumber", "");
        paramMap.put("browserVersion", "");
        paramMap.put("commonBean.activityType", "");
        paramMap.put("commonBean.offerate", "1");
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
        paramMap.put("joinSign", "");
        paramMap.put("presentActivityId", "");
        paramMap.put("sinoUnionShortAddr", "");
        paramMap.put("caliSignBean.token", "");
        paramMap.put("aliSignBean.sessionid", "");
        paramMap.put("aliSignBean.sig", "");



        String reservationResult = "";
        Map map = new HashMap();
        String result = null;
        try {
            //获取cokie
           /* Date date=new Date();
            String a=HttpCucc.getCokie("https://upay.10010.com/npfwap/NpfMob/needCode?channelType=307&_="+date.getTime(),null,0);
            String  cokie=a.split(";")[0].substring(a.split(";")[0].indexOf(":")+1).trim();
            System.out.println("cokie-------->"+cokie);*/
            System.out.println("数据库中获取的cokie-------->" + coke);
            if (coke == null) {
                coke = Db.queryStr(" select coke  from coke limit 1 ");
            }
            result = HttpCucc3.sendPostTopUp(reservationUrl, paramMap, null,0, coke);
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
            Db.deleteById("company_ip", companyIp.getId());
            number++;
            CompanyIp companyIps = CompanyIp.dao.findFirst("select * from company_ip where isU<3 order by  isU ");
            return topUp(phone, money, number, companyIps);
        } catch (NullPointerException i) {
            number++;
            CompanyIp companyIps = CompanyIp.dao.findFirst("select * from company_ip where isU<3 order by  isU ");
            Db.update("update company_ip set isU=isU+1  where id=" + companyIps.getId());
            return topUp(phone, money, number, companyIps);
        } catch (NumberFormatException i) {
            number++;
            try {
                Thread.sleep(5000);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
            CompanyIp companyIps = CompanyIp.dao.findFirst("select * from company_ip where isU<3 order by  isU ");
            Db.update("update company_ip set isU=isU+1  where id=" + companyIps.getId());
            return topUp(phone, money, number, companyIps);
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
            payUrl = topUpTo(reservationOrderId, phone, null,0,area);
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
            return topUp(phone, money, number, companyIps);
        } catch (NullPointerException i) {
            number++;
            CompanyIp companyIps = CompanyIp.dao.findFirst("select * from company_ip where isU<3 order by  isU ");
            Db.update("update company_ip set isU=isU+1  where id=" + companyIps.getId());
            return topUp(phone, money, number, companyIps);
        } catch (NumberFormatException i) {
            number++;
            try {
                Thread.sleep(5000);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
            CompanyIp companyIps = CompanyIp.dao.findFirst("select * from company_ip where isU<3 order by  isU ");

            Db.update("update company_ip set isU=isU+1  where id=" + companyIps.getId());
            return topUp(phone, money, number, companyIps);
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
            payUrl = topUpTo2(reservationOrderId, phone, companyIp.getIp(), companyIp.getPort());
            //restMap = toPayAndpayStr2(payUrl, companyIp.getIp(), companyIp.getPort());
        }

        return payUrl;
    }

    //正式下单 
    public static String topUpTo(String reservationOrderId, String phone, String ip, Integer port,String area) {
        String upUrl = "https://upay.10010.com/npfwap/NpfMob/mobWapBankCharge/wapBankChargeSubmit.action";
        Map parMap = new HashMap();
        parMap.put("secstate.state", reservationOrderId);//预下单号
        parMap.put("commonBean.phoneNo", phone);
        parMap.put("commonBean.provinceCode", area);
        parMap.put("browserVersion", "");
        parMap.put("channelKey", "");
        parMap.put("ticketNew", "ticket");
        String resultOreder = null;
        try {
            resultOreder = HttpCucc3.post(upUrl, parMap, ip, port);
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
    public static Map toPayAndpayStr(String payUrl, String ip, Integer port) {
        Map map = new HashMap();
        String result = null;
        result = HttpCucc3.sendGetPayUrl2(payUrl, ip, port);
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


        String s = topUp("18623992032", "20", 0, null);
        Map map = toPayAndpayStr(s, "114.55.136.158", 21443);
        System.out.println(s);
        System.out.println(map.get("url"));
    }


}
