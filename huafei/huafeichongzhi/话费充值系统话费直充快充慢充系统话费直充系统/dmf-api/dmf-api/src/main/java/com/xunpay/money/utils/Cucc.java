package com.xunpay.money.utils;


import net.sf.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Cucc {

    //根据手机号和金额下单
    public static Map topUp(String phone, String money) {
        Map restMap=new HashMap();
        //预下单
        String reservationUrl = "https://upay.10010.com/npfwap/NpfMob/mobWapBankPay/mobWapPayFeeCheck.action";
        Map paramMap = new HashMap();
        paramMap.put("commonBean.phoneNo", phone);
        paramMap.put("bussineTypeInput", "0");
        paramMap.put("commonBean.payAmount", money);
        paramMap.put("invoiceBean.is_mailing", "0");
        paramMap.put("invoiceBean.need_invoice", "0");
        paramMap.put("commonBean.reserved1", "false");
        paramMap.put("commonBean.channelType", "307");
        paramMap.put("channelKey", "wxgz2");
        paramMap.put("commonBean.bussineType", "01");
        paramMap.put("commonBean.orgCode", "31");
        paramMap.put("commonBean.channelCode", "weixinH5");
       // paramMap.put("ticketNew", "6CQu6Quz1KyS1EvpPpBZgokHOpQk2forfWfEX5W2dmSUwCEerEF-UckT2ExqmXebNZJuZyBTeWU*");
        int port=getIPandPort();
        String result = HttpClientHelperCucc.sendPostTopUp(reservationUrl, paramMap,port);
        Map map = JSONObject.fromObject(result);
        String reservationResult = map.get("out").toString();//预下单返回状态
        System.out.println(reservationResult);
        //返回success才算预下单成功
        if ("success".equals(reservationResult)) {
            String reservationOrderId = map.get("secstate").toString();//预下单号
            //拿到预下单号去下单 获取跳转支付链接
            String payUrl = topUpTo(reservationOrderId, phone,port);
            restMap = toPayAndpayStr(payUrl,port);
        }
        return restMap;
    }

    //正式下单
    public static String topUpTo(String reservationOrderId, String phone,int port) {
        String upUrl = "http://upay.10010.com/npfwap/NpfMob/mobWapBankPay/mobWapPayFeeApply.action";
        Map parMap = new HashMap();
        parMap.put("secstate.state", reservationOrderId);//预下单号
        parMap.put("commonBean.phoneNo", phone);//手机号
        //parMap.put("ticketNew", "6CQu6Quz1KyS1EvpPpBZgokHOpQk2forfWfEX5W2dmSUwCEerEF-UckT2ExqmXebNZJuZyBTeWU*");
        String resultOreder = HttpClientHelperCucc.sendPost(upUrl, parMap,null,port);
        String payUrl = JSONObject.fromObject(resultOreder).get("payUrl").toString();
        return payUrl;
    }


    //访问跳转充值链接获取微信深度链接以及payStr(用于监控订单状态)
    public static Map toPayAndpayStr(String payUrl,int port) {
        Map map=new HashMap();
        String result = HttpClientHelperCucc.sendGetPayUrl(payUrl,port);
        String linkTemp = null;
        String link = "";
        String payStr="";
        if (result.indexOf("ok") != -1) {
            //获取微信深度支付链接
            String[] urlSplit = result.split("url=");

            String temp = urlSplit[1];

            int index = temp.indexOf("var");

            linkTemp = temp.substring(1, index).trim();

            int length = linkTemp.length();

            link = linkTemp.substring(0, length - 2);

            //获取payStr
            String [] strurlit=result.split("redirect_url=");
            String strtemps=strurlit[1];
            int strindexs = strtemps.indexOf(";");

            String str=strtemps.substring(1,strindexs).trim();

            int strlengths=str.length();

            String []Str=str.substring(0, strlengths - 1).split("\\?");
            payStr=Str[1].substring(Str[1].indexOf("=")+1);
        }
        map.put("url",link);
        map.put("payStr",payStr);
        return map;
    }


    /*
    * 获取动态ip和端口
    * */
    public static int getIPandPort(){
        String s = HttpClientHelper.sendGet("http://sapi.baibianip.com:8123/get.php?id=39679263308973462404&break=1&format=text&count=1&signature=9b0c46257c99574389236bba548df6f954382a03");
        Integer port=Integer.parseInt(s.split(":")[1]);
        return port;
    }

    public static void main(String[] art) {
        Map map = topUp("18674573737", "1.00");
        if(map.size()>0){
            System.out.println(map.get("url"));
            System.out.println(map.get("payStr"));
        }else {
            System.out.println("下单失败");
        }
        /*String s = HttpClientHelper.sendGet("http://sapi.baibianip.com:8123/get.php?id=39679263308973462404&break=1&format=text&count=1&signature=9b0c46257c99574389236bba548df6f954382a03");
        System.out.println(s);
        System.out.println(s.split(":")[0]);
        System.out.println(s.split(":")[1]);*/


    }


}
