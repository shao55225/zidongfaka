package com.xunpay.money.utils;


import com.alibaba.fastjson.JSONObject;
import com.jfinal.plugin.activerecord.Db;
import com.xunpay.money.model.CompanyIp;
import com.xunpay.money.model.CompanyOrder;

import com.xunpay.money.model.SysConfig;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static com.xunpay.money.core.job.CuccJob.GetIp;

/***
 *
 *  2020-09-06 更新电信sessionId和最新回调
 * */
public class CtccTest2 {

    HttpClientHelperCtcc httpClientHelperCtcc = new HttpClientHelperCtcc();

    public Map topUp(String phone, String money, int i, CompanyIp companyIp, String sessionId) {
        //下单使用ip
        if (i > 1) {
            return null;
        }
        Map retMap = new HashMap();
        Map map = new HashMap();
        Map headerInfo = new HashMap();
        headerInfo.put("functionCode", "rechargePhone");

        Map requestContent = new HashMap();
        //String sessionId=getSessionId();
        requestContent.put("sessionid", sessionId);
        requestContent.put("shopid", "20001");
        requestContent.put("shop", "20001180400");
        requestContent.put("ct", "0");
        String encrPhone = encryption(phone);
        requestContent.put("phoneNumber", encrPhone);

        String encrMoney = encryption(money);
        requestContent.put("payAmount", encrMoney);
        System.out.println("金额为" + money + "加密为" + encrMoney);
        map.put("headerInfo", headerInfo);
        map.put("requestContent", requestContent);
        String params = JSONObject.toJSONString(map);
        long t = System.currentTimeMillis();
        String result = null;
        try {
            result = HttpCtccTest.postTo("http://wapzt.189.cn:8010/pc/percharge.do", params, companyIp.getIp(), companyIp.getPort());
            //result = HttpCtccTest.postTo("http://wapzt.189.cn:8010/pc/percharge.do", params, null, 0);
            System.out.println(result);
            //接收成功返回参数
            Map resultMap = new HashMap();
            resultMap = (Map) JSONObject.parseObject(result);
            Map response = (Map) resultMap.get("responseContent");
            //订单号orderId
            String orderId = response.get("orderId").toString();
            if ("".equals(orderId) || orderId == null) {
                throw new Exception();
            }
            System.out.println(response.get("orderId"));
            //跳转地址payUrl
            String payUrl = response.get("payUrl").toString();

            retMap.put("orderid", orderId);
            retMap.put("sessionid", sessionId);
            //
            //微信   retMap.put("payUrl","https://wappay.189.cn/pay/toPay.do?bankCode=WX001&sessionid="+sessionid+"&state=");
            retMap.put("payUrl", payUrl); //收银台

            long t2 = System.currentTimeMillis();
            Long sss = t2 - t;
            System.out.println("请求时间为" + sss);
        } catch (Exception e) {
            //String s = HttpClientHelper.sendGet("http://api.wandoudl.com/api/ip?app_key=75191f311013adf875075fff27cc5760&pack=210646&num=1&xy=1&type=1&lb=&mr=2&");
            //String s = HttpClientHelper.sendGet("http://ip.11jsq.com/index.php/api/entry?method=proxyServer.generate_api_url&packid=1&fa=0&fetch_key=&groupid=0&qty=1&time=1&pro=&city=&port=1&format=txt&ss=1&css=&dt=1&specialTxt=3&specialJson=&usertype=16");
            CompanyIp companyIp1 = CompanyIp.dao.findFirst("select * from company_ip where isU<3 order by  isU ");
            //  Db.update("update company_ip set isU=isU+1 where id="+companyIp.getId());
            System.out.println("ip不可用已切换ip" + companyIp1.getId());
            e.printStackTrace();
            i++;
            //    return topUp(phone,money,i,companyIp1);
        }
        return retMap;
    }

    public Map topUp2(String phone, String money, int i, CompanyIp companyIp, String sessionId) {
        //下单使用ip
        if (i > 1) {
            return null;
        }
        Map retMap = new HashMap();
        Map map = new HashMap();
        Map headerInfo = new HashMap();
        headerInfo.put("functionCode", "createOrderCallCostRecharge");

        Map requestContent = new HashMap();
        sessionId = getSessionId2(companyIp);
        requestContent.put("sessionid", sessionId);
        requestContent.put("shopId", "");
        requestContent.put("shop", "20001181307");
        requestContent.put("wxPayFlag", "wx");
        String encrPhone = encryption(phone);
        requestContent.put("phoneNumber", encrPhone);
        String encrMoney = encryption(money);
        requestContent.put("payAmount", encrMoney);
        System.out.println("金额为" + money + "加密为" + encrMoney);
        map.put("headerInfo", headerInfo);
        map.put("requestContent", requestContent);
        String params = JSONObject.toJSONString(map);
        long t = System.currentTimeMillis();
        String result = null;
        try {
            result = HttpCtccTest.postTo("https://lm.189.cn/wapclient/callCostRecharge.do", params, null, 0);
            System.out.println(result);
            //接收成功返回参数
            Map resultMap = new HashMap();
            resultMap = (Map) JSONObject.parseObject(result);
            Map response = (Map) resultMap.get("responseContent");
            //订单号orderId
            String orderId = response.get("orderId").toString();
            if ("".equals(orderId) || orderId == null) {
                throw new Exception();
            }
            System.out.println(response.get("orderId"));
            //跳转地址payUrl
            String payUrl = response.get("payUrl").toString();

            retMap.put("orderid", orderId);
            retMap.put("sessionid", sessionId);
            //
            //微信   retMap.put("payUrl","https://wappay.189.cn/pay/toPay.do?bankCode=WX001&sessionid="+sessionid+"&state=");
            retMap.put("payUrl", payUrl); //收银台

            long t2 = System.currentTimeMillis();
            Long sss = t2 - t;
            System.out.println("请求时间为" + sss);
        } catch (Exception e) {
            //String s = HttpClientHelper.sendGet("http://api.wandoudl.com/api/ip?app_key=75191f311013adf875075fff27cc5760&pack=210646&num=1&xy=1&type=1&lb=&mr=2&");
            //String s = HttpClientHelper.sendGet("http://ip.11jsq.com/index.php/api/entry?method=proxyServer.generate_api_url&packid=1&fa=0&fetch_key=&groupid=0&qty=1&time=1&pro=&city=&port=1&format=txt&ss=1&css=&dt=1&specialTxt=3&specialJson=&usertype=16");
            CompanyIp companyIp1 = CompanyIp.dao.findFirst("select * from company_ip where isU<3 order by  isU ");
            //  Db.update("update company_ip set isU=isU+1 where id="+companyIp.getId());
            System.out.println("ip不可用已切换ip" + companyIp1.getId());
            e.printStackTrace();
            i++;
            //    return topUp(phone,money,i,companyIp1);
        }
        return retMap;
    }


    public String wxPay(String url, String session, String ip, Integer port) {

        String location = HttpCtccTest.wxsendGet(url, session, ip, port);
        String linkTemp = null;
        String link = "";
        if (location.indexOf("ok") != -1) {
            String[] urlSplit = location.split("url=");

            String temp = urlSplit[1];

            int index = temp.indexOf("var");

            linkTemp = temp.substring(1, index).trim();

            int length = linkTemp.length();

            link = linkTemp.substring(0, length - 2);

        }

        return link;

    }


    public String webWxLocation(String param, String ip, Integer port, String session) {
        String url = "https://paygo.189.cn:9779/189pay/service";
        Map map = new HashMap();
        map.put("request_params", param);
        String location = null;
        try {
            location = HttpCtccTest.sendPostService(url, map, ip, port, session);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return location;
    }

    public String wxpay(String sessionId, String ip, Integer port) {
        String url = "https://wappay.189.cn/pay/toPay.do?bankCode=WX001&sessionid=" + sessionId + "&state=";
        String locationHtml = HttpCtccTest.get(url, ip, port);
        Document document = Jsoup.parse(locationHtml);

        Elements script = document.select("input[name='request_params']");

        String element = script.val();
        System.out.println(element + "----------------");
        return element;
    }


    public String onlinePay(String payUrl, String ip, Integer port) {
        String result = HttpCtccTest.getonlinePay(payUrl, ip, port);
        String[] sessionid = result.split("\\?");
        int substring = sessionid[1].indexOf("=");
        String sessid = sessionid[1].substring(substring + 1, 33 + substring);
        return sessid;
    }


    //回调查询订单状态
    public String notback(String sessionId) {
        String url = "https://wappay.189.cn/pay/toPayQuery.do?boId=" + sessionId;
        String s = httpClientHelperCtcc.sendGet(url);
        Document document = Jsoup.parse(s);
        Elements script = document.select("div div div");
        System.out.println(script.text());//返回的订单状态
        return null;
    }


    /**
     * Map转String
     *
     * @param map
     * @return
     */
    public static String getMapToString(Map<String, Object> map) {
        Set<String> keySet = map.keySet();
        //将set集合转换为数组
        String[] keyArray = keySet.toArray(new String[keySet.size()]);
        //给数组排序(升序)
        Arrays.sort(keyArray);
        //因为String拼接效率会很低的，所以转用StringBuilder
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < keyArray.length; i++) {
            // 参数值为空，则不参与签名 这个方法trim()是去空格
            if ((String.valueOf(map.get(keyArray[i]))).trim().length() > 0) {
                sb.append(keyArray[i]).append(":").append(String.valueOf(map.get(keyArray[i])).trim());
            }
            if (i != keyArray.length - 1) {
                sb.append(",");
            }
        }
        String retsb = "{" + sb + "}";
        return retsb;
    }

    //获取验证码
    /*public static String getyzm(){
        byte [] s = HttpClientHelperCtcc.sendGetVerCode("http://wapzt.189.cn/wap/getPicRandomCode.do?param=1584771683819&sessionid=2f31f7df70284c6386ddb2702d08f2e2&type=2");
        String outcode = HttpClientHelperCtcc.getCode(s);
        Map map=JSONObject.fromObject(outcode);
        if("false".equals(map.get("result"))){
           return getyzm();
        }
        Map map1=JSONObject.fromObject(map.get("data"));
        String yzm=map1.get("val").toString();
        System.out.println(map.get("data"));
        System.out.println(map1.get("val"));
        return yzm;
    }*/

    //获取电信官方sessionID
    public static String getSessionId() {

        String sessionid = null;
        Map map = new HashMap();
        Map headerInfo = new HashMap();
        headerInfo.put("functionCode", "queryRechargeDiscount");

        Map requestContent = new HashMap();
        requestContent.put("sessionid", "");
        requestContent.put("configCode", "");

        map.put("headerInfo", headerInfo);
        map.put("requestContent", requestContent);
        String params = JSONObject.toJSONString(map);

        try {
            String result = HttpCtccTest.postTo("http://wapzt.189.cn:8010/pc/percharge.do", params, null, 0);
            Map resultMap = new HashMap();
            resultMap = (Map) JSONObject.parseObject(result);
            Map response = (Map) resultMap.get("responseContent");
            //订单号orderId
            sessionid = response.get("sessionid").toString();

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return sessionid;
    }

    //获取电信官方sessionID
    public String getSessionId2(CompanyIp companyIp) {

        String sessionid = null;
        Map map = new HashMap();
        Map headerInfo = new HashMap();
        headerInfo.put("functionCode", "getRechargeConfInfo");

        Map requestContent = new HashMap();
        requestContent.put("sessionid", "");
        requestContent.put("clientType", "0");
        requestContent.put("configCode", "");
        requestContent.put("wxPayFlag", "wx");

        map.put("headerInfo", headerInfo);
        map.put("requestContent", requestContent);
        String params = JSONObject.toJSONString(map);

        try {
            String result = HttpCtccTest.postTo("https://lm.189.cn/wapclient/callCostRecharge.do", params, companyIp.getIp(), companyIp.getPort());
           // String result = HttpCtccTest.postTo("https://lm.189.cn/wapclient/callCostRecharge.do", params, null, 0);
            Map resultMap = new HashMap();
            resultMap = (Map) JSONObject.parseObject(result);
            Map response = (Map) resultMap.get("responseContent");
            //订单号orderId
            sessionid = response.get("sessionid").toString();

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return sessionid;
    }


    //引用js加密
    public String encryption(String code) {
        ScriptEngine se = new ScriptEngineManager().getEngineByName("javascript");
        String t = Thread.currentThread().getContextClassLoader().getResource("").getPath();
        System.out.println("t=" + t);
        String retCode = "";
        try {

//            se.eval(new FileReader(System.getProperty("user.dir") + "/src/main/webapp/resource/plugins/aes.js"));//本地环境
//            se.eval(new FileReader(System.getProperty("user.dir") + "/src/main/webapp/resource/plugins/encr.js"));//本地环境
            se.eval(new FileReader(t.substring(0, t.indexOf("WEB-INF")) + "resource/plugins/aes.js"));//服务器环境
            se.eval(new FileReader(t.substring(0, t.indexOf("WEB-INF")) + "resource/plugins/encr.js"));//服务器环境
            Invocable inv = (Invocable) se;
            retCode = (String) inv.invokeFunction("encrypt", code);
        } catch (Exception e) {
            System.out.println("异常了——-----------" + e.getMessage());
        }
        return retCode;
    }

    private static String StrToBinstr(String str) {
        char[] strChar = str.toCharArray();
        String result = "";
        for (int i = 0; i < strChar.length; i++) {
            result += Integer.toBinaryString(strChar[i]);
        }
        return result;
    }


    /**
     * 回调测试
     **/
    public String notice(String sessionId) {
        String payStatusName = null;
        Map map = new HashMap();
        Map headerInfo = new HashMap();
        headerInfo.put("functionCode", "rechargePaymentStatus");

        Map requestContent = new HashMap();
        requestContent.put("sessionid", sessionId);
        requestContent.put("configCode", "");
        map.put("headerInfo", headerInfo);
        map.put("requestContent", requestContent);
        String params = JSONObject.toJSONString(map);
        SysConfig sysconfig = SysConfig.dao.findFirst(" select * from sys_config  where code='noticeIp' ");
        String noticeIp = sysconfig.getContent();
        String baiIp = GetIp(noticeIp);
        String  ip1=baiIp.split(":")[0];
        Integer port1=Integer.parseInt(baiIp.split(":")[1].trim());
        System.out.println("执行电信支付状态查询，ip：" + ip1);

        try {
            String result = HttpCtccTest.postTo("http://wapzt.189.cn:8010/pc/percharge.do", params, ip1, port1);
            System.out.println(result);
            Map resultMap = new HashMap();
            resultMap = (Map) JSONObject.parseObject(result);
            Map response = (Map) resultMap.get("responseContent");
            //订单号orderId
            payStatusName = response.get("payStatusName").toString();


        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return payStatusName;
    }

    public static void main(String[] art) throws Exception {
        //System.setProperty("jna.encoding", "GBK");
        //下单

//
//           CtccTest2 ctcc=new CtccTest2();
//            Map map = ctcc.topUp("17775927880", "5",0,null);
//            String a = map.get("sessionid").toString();
//            String b = map.get("payUrl").toString();
//            String c = map.get("orderid").toString();
//            System.out.println(a);
//            System.out.println(b);
//            System.out.println(c);
//
//
//
//    	String status=noticeTest(a);
//        System.out.println(status);
        //查询订单回调状态
        //notback("9ad3119b3abf4727802f5a221996fedd");
        //getSessionId();
//        CtccTest2 test2 = new CtccTest2();
//        System.out.println(test2.getSessionId2());
//        Map map = test2.topUp2("18001954953", "10", 0, null, "2a069c30cfc340988dd965ea1ed96e2b");
//        System.out.println("输出");
        /*byte [] s = HttpClientHelperCtcc.sendGetVerCode("http://wapzt.189.cn/wap/getPicRandomCode.do?param=1584771683819&sessionid=2f31f7df70284c6386ddb2702d08f2e2&type=2");
        String outcode = HttpClientHelperCtcc.getCode(s);
        Map map=JSONObject.fromObject(outcode);
        Map map1=JSONObject.fromObject(map.get("data"));
        System.out.println(map.get("data"));
        System.out.println(map1.get("val"));*/

       /* String payStatusName = null;
        Map map = new HashMap();
        Map headerInfo = new HashMap();
        headerInfo.put("functionCode", "rechargePaymentStatus");

        Map requestContent = new HashMap();
        requestContent.put("sessionid", "4dc1b526705945aebf1efe63727e1bb4");
        requestContent.put("configCode", "");
        map.put("headerInfo", headerInfo);
        map.put("requestContent", requestContent);
        String params = JSONObject.toJSONString(map);

        try {
            String result = HttpCtccTest.postTo("http://wapzt.189.cn:8010/pc/percharge.do", params, null, 0);
            System.out.println(result);
            Map resultMap = new HashMap();
            resultMap = (Map) JSONObject.parseObject(result);
            Map response = (Map) resultMap.get("responseContent");
            //订单号orderId
            payStatusName = response.get("payStatusName").toString();


        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        System.out.println(payStatusName);*/


        CtccTest2 test2 = new CtccTest2();
        System.out.println(test2.getSessionId2(null));
        String sessionId = getSessionId();
        Map map = test2.topUp("18001954953", "5", 0, null, sessionId);
        System.out.println(map.get("payUrl"));
        System.out.println("输出");

    }
}
