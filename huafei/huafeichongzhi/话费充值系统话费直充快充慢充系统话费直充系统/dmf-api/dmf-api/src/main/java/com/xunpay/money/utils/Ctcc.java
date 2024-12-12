package com.xunpay.money.utils;




import java.io.FileReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;


import com.alibaba.fastjson.JSONObject;

public class Ctcc {

    HttpClientHelperCtcc httpClientHelperCtcc=new HttpClientHelperCtcc();

    public  Map topUp(String phone, String money){
        Map retMap=new HashMap();
        Map map=new HashMap();
        Map headerInfo=new HashMap();
        headerInfo.put("functionCode","recharge");
        Map requestContent=new HashMap();
        requestContent.put("type","1");
        requestContent.put("shop","");
        requestContent.put("shopid","20001");
        String encrPhone=encryption(phone);
        requestContent.put("phoneNumber",encrPhone);
        String encrMoney=encryption(money);
        requestContent.put("payAmount",encrMoney);

        map.put("headerInfo", headerInfo);
        map.put("requestContent", requestContent);

        long t=System.currentTimeMillis();
        String result= httpClientHelperCtcc.postJsonFile1("http://cservice.client.189.cn:9092/common/recharge.do",map,null);

        //发送下单返回结果    后期处理做判断验证码不通过就从新调用此方法继续发送
        System.out.println(result);
        //接收成功返回参数
        Map resultMap= new HashMap();
        resultMap=(Map) JSONObject.parseObject(result);
        Map response=(Map)resultMap.get("responseContent");
        //订单号orderId
        String orderId=response.get("orderId").toString();
        System.out.println(response.get("orderId"));
        //跳转地址payUrl
        String payUrl=response.get("payUrl").toString();
        //跳转获取sessionid
        String sessionid = onlinePay(payUrl);
        //获取跳转的表单内容
        String param=wxpay(sessionid);
        //提交表单内容
        String url=webWxLocation(param);

        //url="https://wx.tenpay.com/cgi-bin/mmpayweb-bin/checkmweb?prepay_id=wx2202261031659718feffeba11710986800&package=449388927&redirect_url=http%3A%2F%2Fwww.189.cn%2Ftrade%2Fpay%2Fresult.do";
       //选择支付方式获取深度链接
        String retpayUrl = wxPay(url, sessionid);
        //获取的深度链接
        System.out.println(retpayUrl+"===================");
        retMap.put("sessionId",sessionid);
        retMap.put("payUrl",retpayUrl);
        retMap.put("orderid",orderId);
        long t2=System.currentTimeMillis();
        Long sss= t2-t;
        System.out.println("请求时间为"+sss);
        return retMap;
    }

    public String wxPay(String url,String session){

        String location= httpClientHelperCtcc.wxsendGet(url,session);
        String linkTemp=null;
        String link="";
        if(location.indexOf("ok")!=-1){
            String []urlSplit=location.split("url=");

            String temp=urlSplit[1];

            int index=temp.indexOf("var");

            linkTemp=temp.substring(1,index).trim();

            int length=linkTemp.length();

            link=linkTemp.substring(0,length-2);

        }

        return link;

    }


    public  String webWxLocation(String param){
        String url="https://paygo.189.cn:9779/189pay/service";
        Map map=new HashMap();
        map.put("request_params",param);
        String location= httpClientHelperCtcc.sendPostService(url,map);
        return location;
    }

    public  String wxpay(String sessionId){
        String url="https://wappay.189.cn/pay/toPay.do?bankCode=WX001&sessionid="+sessionId+"&state=";
        String locationHtml = httpClientHelperCtcc.sendGet(url);
        Document document= Jsoup.parse(locationHtml);

        Elements script=document.select("input[name='request_params']");

        String  element=script.val();
        System.out.println(element+"----------------");
        return element;
    }


    public  String onlinePay(String payUrl){
        String result = httpClientHelperCtcc.onlinePay(payUrl);
        String [] sessionid=result.split("\\?");
        int substring = sessionid[1].indexOf("=");
        String sessid=sessionid[1].substring(substring+1,33+substring);
       return sessid;
    }


    //回调查询订单状态
    public  String notback(String sessionId){
        String url="https://wappay.189.cn/pay/toPayQuery.do?boId="+sessionId;
        String s = httpClientHelperCtcc.sendGet(url);
        Document document= Jsoup.parse(s);
        Elements script=document.select("div div div");
        System.out.println(script.text());//返回的订单状态
        return null;
    }




    /**
     *
     * Map转String
     * @param map
     * @return
     */
    public static String getMapToString(Map<String,Object> map){
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
            if(i != keyArray.length-1){
                sb.append(",");
            }
        }
        String retsb="{"+sb+"}";
        return retsb;
    }

    //获取验证码
//    public static String getyzm(){
//    	
//        byte [] s = HttpClientHelperCtcc.sendGetVerCode("http://aaa.votegoo.com/captcha");
//        
//       // byte [] s = HttpClientHelperCtcc.sendGetVerCode("http://wapzt.189.cn/wap/getPicRandomCode.do?param=1584771683819&sessionid=2f31f7df70284c6386ddb2702d08f2e2&type=2");
//        
//        String outcode = HttpClientHelperCtcc.getCode(s);
//        Map map=JSONObject.fromObject(outcode);
//        if("false".equals(map.get("result"))){
//           return getyzm();
//        }
//        Map map1=JSONObject.fromObject(map.get("data"));
//        String yzm=map1.get("val").toString();
//        System.out.println(map.get("data"));
//        System.out.println(map1.get("val"));
//        return yzm;
//    }



    //引用js加密
    public  String encryption(String code){
        // 获取JS执行引擎  System.getProperty("user.dir")+/src/main/webapp/resource/plugins/aes.js   测试使用此路径
        ScriptEngine se = new ScriptEngineManager().getEngineByName("javascript");
        String t = Thread.currentThread().getContextClassLoader().getResource("").getPath();
        String retCode="";
        try {


            //se.eval(new FileReader(System.getProperty("user.dir")+"/src/main/webapp/resource/plugins/aes.js"));
            //se.eval(new FileReader(System.getProperty("user.dir")+"/src/main/webapp/resource/plugins/encr.js"));//服务器环境
        	//System.out.println(t.substring(0, t.indexOf("WEB-INF"))+"resource/plugins/aes.js");
        	se.eval(new FileReader(t.substring(0, t.indexOf("WEB-INF"))+"resource/plugins/aes.js"));
            se.eval(new FileReader(t.substring(0, t.indexOf("WEB-INF"))+"resource/plugins/encr.js"));//本地环境
            Invocable inv = (Invocable) se;
            retCode=(String) inv.invokeFunction("encrypt", code);//encrypt 为js的 方法名 code 为传入的手机号或者其它参数
        }catch (Exception e){
            System.out.println("异常了——-----------"+e.getMessage());
        }
       return retCode;
    }

    private static String StrToBinstr(String str) {
        char[] strChar=str.toCharArray();
        String result="";
        for(int i=0;i<strChar.length;i++){
            result +=Integer.toBinaryString(strChar[i]);
        }
        return result;
    }


    public static void main(String [] art)throws Exception{
    	
    	//getyzm();
    	
        //System.setProperty("jna.encoding", "GBK");
        //下单

//        for(int i=0;i<10;i++) {
//            Ctcc ctcc=new Ctcc();
//            Map map = ctcc.topUp("18001954958", "10");
//            String a = map.get("sessionId").toString();
//            String b = map.get("payUrl").toString();
//            String c = map.get("orderid").toString();
//            System.out.println(a);
//            System.out.println(b);
//            System.out.println(c);
//        }
        //查询订单回调状态
        //notback("9ad3119b3abf4727802f5a221996fedd");


        /*byte [] s = HttpClientHelperCtcc.sendGetVerCode("http://wapzt.189.cn/wap/getPicRandomCode.do?param=1584771683819&sessionid=2f31f7df70284c6386ddb2702d08f2e2&type=2");
        String outcode = HttpClientHelperCtcc.getCode(s);
        Map map=JSONObject.fromObject(outcode);
        Map map1=JSONObject.fromObject(map.get("data"));
        System.out.println(map.get("data"));
        System.out.println(map1.get("val"));*/

    }
}
