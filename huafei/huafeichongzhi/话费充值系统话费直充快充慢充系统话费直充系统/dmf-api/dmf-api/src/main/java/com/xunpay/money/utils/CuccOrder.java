package com.xunpay.money.utils;


import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

public class CuccOrder {

  
	 //联通实时下单
    public static Map<String,String> topUp(String phone, String money,String ip,int port) {
    	//select coke  from coke
        System.out.println("联通下单手机号------->" + phone);
  
        Map restMap = new HashMap();
        //预下单
        String reservationUrl = "http://upay.10010.com/npfwap/NpfMob/mobWapBankPay/mobWapPayFeeCheck.action";

        Map paramMap = new HashMap();

        paramMap.put("commonBean.phoneNo", phone);
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
        paramMap.put("presentActivityId", "");


        String reservationResult = "";
        Map map = new HashMap();
        String result = null;

            Date date=new Date();
            try {
				result = HttpCucc.sendPostTopUp(reservationUrl, paramMap,ip, port, "upay_user=066ed2ce375da1f166c2c8aa7d11a0e4",null);
				
			} catch (IOException e) {
				e.printStackTrace();
			}
            System.out.println(result);
            map = JSONObject.fromObject(result);
            reservationResult = map.get("out").toString();//预下单返回状态
            System.out.println(phone+"------------------------->"+reservationResult);

        

        String payUrl="";
        //返回success才算预下单成功
        if ("success".equals(reservationResult)) {
            String reservationOrderId = map.get("secstate").toString();//预下单号
            //拿到预下单号去下单 获取跳转支付链接
             payUrl = topUpTo(reservationOrderId, phone, ip, port);
            restMap = toPayAndpayStr(payUrl,ip, port);
        }

        return restMap;
        
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
    
//    //正式下单 
//    public static String topUpTo2(String reservationOrderId, String phone, String ip, Integer port) {
//    	  String upUrl = "https://upay.10010.com/npfwap/NpfMob/mobWapBankPay/mobWapPayFeeApply.action";
//    	Map parMap = new HashMap();
//    	parMap.put("secstate.state", reservationOrderId);//预下单号
//    	//parMap.put("commonBean.phoneNo", phone);//手机号
//    	String resultOreder = null;
//    	try {
//    		resultOreder = HttpCucc.post2(upUrl, parMap, ip, port);
//    	} catch (IOException e) {
//    		e.printStackTrace();
//    	}
//    	String payUrl = JSONObject.fromObject(resultOreder).get("payUrl").toString();
//    	return payUrl;
//    }


    //访问跳转充值链接获取微信深度链接以及payStr(用于监控订单状态)
    public static Map toPayAndpayStr(String payUrl, String ip, Integer port) {
        Map map = new HashMap();
        String result = null;
            result = HttpCucc.sendGetPayUrl(payUrl,ip,port);
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
//    	String payUrl="https://unipay.10010.com/udpNewPortal/payment/directPay?ServicePlatID=11&PayReqObj=KjMjI85mHW6nfDIcYgG8PDkTC0i2MO7%2BvoO%2Fcgf24fo%2B6XEwXvr2yR0IVj11e4fRxEAseZipwcjSd49%2B25kx0PsrZEI9hgQlO1EcDQaP7Ndfj6TY5N7UoBsCv2bcUOvB5sIJmhNaLyKnnGST6HW8%2B1Mzl53m5thZ5PqgvSIYdj%2BFURo3aoV5On7kIMLQIJv144WA8fIJlU34%2FlVDrejZWvXUi3HK3SjArc6EU01qCnJtmLiwMVkk6I16cXk2YbN0fm3hWKBQ%2FGkWvx1NrFzBBL%2FCQ8vqoY02WLEIl83i9fVAajAtN42uE2tp8SRSL2NnnKfV7FG1vUmaIWueuUE77o5W828ijDvf2ad5icDnqB8hgBRcchDGlXXeHNHNJ0egk49gdVNwdiHHDeFlPtsETmFUE8%2FUcmMdVNuVmHLA3DizujYHr1nOxQILMXDeZv4qYMY4D1MGVVJHkTVrV17P6Zf9HhFizl4nFgNC%2BjTYHn0nQqr3PyHCvwOY0f4BqCAyL5V9vf7jAGtxjyAZfWQV52rPLYgwYMwjeSpeEwil4yaZeTX2TZqX72wgwxVOsaF5IKusRSETpbXBN6UjSyHLPMzsC2aTfWv5hFDWVqnXHS9uyLqQ6FHbIytkzVH7Qf0hEJ%2Be4%2Fiw9aNizvmmc0u8632FYpNAmcYCOah7ruocHPKi9pAIjZ6DajpZ150q4fpTOKv2faF6EOiUSN24XKbdTwf5f66ujO74%2B9KaLZdNq1sCgdNLCs5YlKlJ%2FK5tAcUlFyJmqUW6MqiGGta59PjpThd%2FNyuQWhJJs4%2B4aT5QHCOwSZH8wbDnTPjMNw%2BTDJ8iDqMp2tE%2BivXu23GDvcoEbpV2bilHUs1xDT5IbpjhIVU%2BnD9SfTELObwbo2l9Q%2FDXetFa5K%2FHt5YVHv17YUr9SJHSRbvn9633Ik3IrglIVaRGXH7lvbaOI3AuIMCnzXcDywalIpJYEmMVhBjUresDWiYjsw4jTGXKBRk4Qo8UbeVm%2F4uvGCL2KyKc3DPI0bJJDSE6EqHS5pFPIJdkDk%2BYFJ3XycWjA2wVc%2BreQjBtMGzrvKmU35CYvkDcqBV37vjnRrDx9RSGiYdP4TMGk09V5Ff9fB1sHPLrdXIay%2BXnOQetnGM%2BAtOYDoK1vfwx1YuodQrJLI5jukDILif%2F3zmEJ3C%2BP6m%2Fvi9OLopRtPdEGvGYKGBo%2BljHxJPpOalW%2FYy3%2BquXtTxt3t%2FnEy1gZVLw1KEYXhSQbDjL%2FDspGBWPonjLoG7a5U3frskqZHJ2m9m6ymHj%2BIYU8D0MYtAqoqEmeKG6jx%2FZHYUTLlM2vz0DoR6LhHaWSlwSDdmPTb1C3qgK6Mu1kRyfljKAOyjz4br2hMpppXLgCtR7LIJMwPyLBX%2Fx%2FwliLnF8WVi6ebE3wIe6&pageType=07";
//    
//    	System.out.println(	toPayAndpayStr(payUrl,  "", ));
//    	
    	
      System.out.println("最终结果为"+topUp("15674540001", "1","106.92.102.202",3617));
      
 //    System.out.println(topUpTo("20081315573206596486", "15674540001", "175.6.139.69",28803));
      
//        String a="https://unipay.10010.com/udpNewPortal/payment/directPay?ServicePlatID=11&PayReqObj=KjMjI85mHW5axgIqXA6SEm%2F03GNJlFYuk3dqcVQvkTXVZ%2BNz35CWwTtSpKyYiCf07EeGw%2FwZM%2FYHwsDrbCk6XlVCWaes81RqNGMsfaPa4SIHbMR7e3znDR6HypiKrsTi3IVDdAckMxTtVHLhOnei6d7JZTfwbJS5U7CJA927Ip3vpGl0MMHziiFgRCJzsF4dL%2FcNOAzYTDYrf1iZq2g29mqD0UNbnnyXgOSoO2xRxYpAYI%2BX%2FBk0ODUd%2Bc2Sr3bNo5k%2F66N4bflh2qfC3zd2RQXCLRrUHYm3slOyEKpCs5WcKZ65EyScwZ5Xmd0kKQmodFrS6wzRHaOFQL4IAqgzealOoaaLBlvZftWhKadgT98%2BCJNaeirc6UHDG9eOn4AWiUolGzB7U2UfOgaec9qLZ59TjTA2ygoWfd7sE765hPp%2FAO9CsE3ZJSIUv0u2s3WxXW7kAiOo1yAdk%2BpGEIsFq4ePEheri9gELRjgQg5fascbke5zJNgWCwRhZdKFLphHRVCGpZltKUsAtQQyYg3VeUfbarGcVMkEFZj2fXkmJbVPFuaWm4tqQEq60TfYO%2B2xyf4Np3HQHp%2B%2FAzQ0tJz3qTKVpL4R6UXsvY9c4sluPDOvq3Z7YCgRsrzpJGdF2Lj6TK3qI05idoqPr0ErL88fRdyRIxEN%2FaJJ%2F6wSNscoYeecwLUgT0%2B8rw9KJ2oWBrLsfBL69ovHUnnaqzEnZPvZvZQehiZeET2HhOf3iRSPxN4iqp%2FvwyXoX3MNHdHW3Lh3ChV7ZfA3QlWpOySbzzmoQmk2mq9urAx1xir1cN6XEDna%2FNJcBwi%2FEXUA%2Fb7vV2texMv2wnuZBCwzTnOqSCDMQayYIsQUwXPUdfz6AU02HgcA0mQhUg2JDEN%2B5tGHC34I6fsOpBkWy7uorGAwQzLr0jGQeyLiWTeXKLg7PSJyYAESy2Bno4rU3nUzbtIdDpLyhdpCvI%2Ba6I8ADI9gt9xkRc5LJ9DlN%2BFHT5hnOkZ%2FSBkmvj2odwHVlZQmx9wIUSe62smpOLoLzQwf8CSK%2F9xfT4CXUzRFyKE4ApBsjfBcIwni1q9xdHsiV7JUcFBIzvXWJcl%2BkXNZzRc7zZHxk9Cc3ryAzxorh0O%2FyGYSKAIyONLfYGadO3rwaOCEc1AF%2BNJ5gnAj0gDkQqBXnDLeKUQE3cUuyI7Kzd9MkILEpeP7YJHGIfWDjW%2FxdDKL7LNnDzzslO8toU4A0xB0J6XMKeo7qMaWNP69Bkt2MUnWKmDhzK5mbBfSFxGeA8qb4uNvFTHHqMjYxxbU115l3Eck4CIlphdOs9vnEEPPDZqmH2klpUPuq9ToKTQTYcREeUKJNiBAQSv1k%2BXUnPUyk3ZZP944IkuFUfc1uQLt0famseAfkXg%3D&pageType=07";
//
//        String x=a.split("\\?")[1].split("&")[0];
//        String b=a.split("\\?")[1].split("&")[1];
//        String c=a.split("\\?")[1].split("&")[2];
//
//
//        String servicePlatID=x.substring(x.indexOf("=")+1);
//        String payReqObj=b.substring(b.indexOf("=")+1);
//        String pageType=c.substring(c.indexOf("=")+1);
//        System.out.println(servicePlatID+"-------"+payReqObj+"--------"+pageType);


    }


}
