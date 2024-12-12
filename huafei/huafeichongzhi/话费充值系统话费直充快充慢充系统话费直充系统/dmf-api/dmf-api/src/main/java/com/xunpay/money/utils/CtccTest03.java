package com.xunpay.money.utils;


import com.alibaba.fastjson.JSONObject;
import com.xunpay.money.model.CompanyIp;
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
 *  2020-11-10 电信充值查询号码是否可以充值
 * */
public class CtccTest03 {

    HttpClientHelperCtcc httpClientHelperCtcc = new HttpClientHelperCtcc();

    public int topUp(String phone, String money, int i, CompanyIp companyIp, String sessionId) {
        //下单使用ip
        if (i > 1) {
            return -1;
        }
        Map retMap = new HashMap();
        Map map = new HashMap();
        Map headerInfo = new HashMap();
        headerInfo.put("functionCode", "rechargePhone");
        Map requestContent = new HashMap();
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
        int serviceCode = -1;
        String result = null;
        try {
            result = HttpCtccTest03.postTo("http://wapzt.189.cn:8010/pc/percharge.do", params, null, 0);
            //接收成功返回参数
            Map resultMap = new HashMap();
            resultMap = (Map) JSONObject.parseObject(result);
            Map response = (Map) resultMap.get("responseContent");

             serviceCode = Integer.valueOf(response.get("serviceCode").toString());

        } catch (Exception e) {

        }
        return serviceCode;
    }


    //获取电信官方sessionID
    public String getSessionId2() {
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
            String result = HttpCtccTest03.postTo("https://lm.189.cn/wapclient/callCostRecharge.do", params, null, 0);
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
            String result = HttpCtccTest03.postTo("http://wapzt.189.cn:8010/pc/percharge.do", params, null, 0);
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

            //se.eval(new FileReader(System.getProperty("user.dir") + "/src/main/webapp/resource/plugins/aes.js"));//本地环境
            //se.eval(new FileReader(System.getProperty("user.dir") + "/src/main/webapp/resource/plugins/encr.js"));//本地环境
             se.eval(new FileReader(t.substring(0, t.indexOf("WEB-INF")) + "resource/plugins/aes.js"));//服务器环境
            se.eval(new FileReader(t.substring(0, t.indexOf("WEB-INF")) + "resource/plugins/encr.js"));//服务器环境
            Invocable inv = (Invocable) se;
            retCode = (String) inv.invokeFunction("encrypt", code);
        } catch (Exception e) {
            System.out.println("异常了——-----------" + e.getMessage());
        }
        return retCode;
    }


    public static void main(String[] art) throws Exception {


        CtccTest03 test2 = new CtccTest03();
        System.out.println(test2.getSessionId2());
        String sessionId = getSessionId();
        int map = test2.topUp("18086076626", "5", 0, null, sessionId);
        System.out.println("是否可以充值："+map);

    }
}
