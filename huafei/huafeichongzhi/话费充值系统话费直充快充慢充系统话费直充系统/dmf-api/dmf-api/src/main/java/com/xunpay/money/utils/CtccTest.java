package com.xunpay.money.utils;


import com.alibaba.fastjson.JSONObject;
import com.jfinal.plugin.activerecord.Db;
import com.xunpay.money.model.CompanyIp;
import com.xunpay.money.model.CompanyOrder;
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

public class CtccTest {

    HttpClientHelperCtcc httpClientHelperCtcc=new HttpClientHelperCtcc();

    public  Map topUp(String phone, String money,int i,CompanyIp companyIp){
        //下单使用ip
        if(i>1){
            return null;
        }
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

       /* map.put("headerInfo", JSONObject.toJSONString(headerInfo));
        map.put("requestContent", JSONObject.toJSONString(requestContent));*/
        map.put("headerInfo", headerInfo);
        map.put("requestContent", requestContent);
        String params=JSONObject.toJSONString(map);
        long t=System.currentTimeMillis();
        String result= null;
        try {
            result = HttpCtccTest.postTo("http://cservice.client.189.cn:9092/common/recharge.do",params,companyIp.getIp(),companyIp.getPort());
            //发送下单返回结果    后期处理做判断验证码不通过就从新调用此方法继续发送
            System.out.println(result);
            //接收成功返回参数
            Map resultMap= new HashMap();
            resultMap=(Map) JSONObject.parseObject(result);
            Map response=(Map)resultMap.get("responseContent");
            //订单号orderId
            String orderId=response.get("orderId").toString();
            if("".equals(orderId)||orderId==null){
                throw new Exception();
            }
            System.out.println(response.get("orderId"));
            //跳转地址payUrl
            String payUrl=response.get("payUrl").toString();
            
            //跳转获取sessionid
            String sessionid = onlinePay(payUrl,companyIp.getIp(),companyIp.getPort());

            retMap.put("sessionId",sessionid);

            retMap.put("orderid",orderId);
            //
            //微信   retMap.put("payUrl","https://wappay.189.cn/pay/toPay.do?bankCode=WX001&sessionid="+sessionid+"&state=");
            retMap.put("payUrl",payUrl); //收银台


            //获取跳转的表单内容
           // String param=wxpay(sessionid,companyIp.getIp(),companyIp.getPort());
            //提交表单内容
          //  String url=webWxLocation(param,companyIp.getIp(),companyIp.getPort(),sessionid);
            //url="https://wx.tenpay.com/cgi-bin/mmpayweb-bin/checkmweb?prepay_id=wx2202261031659718feffeba11710986800&package=449388927&redirect_url=http%3A%2F%2Fwww.189.cn%2Ftrade%2Fpay%2Fresult.do";
            //选择支付方式获取深度链接
         //   String retpayUrl = wxPay(url, sessionid,companyIp.getIp(),companyIp.getPort());
            //获取的深度链接
         //   System.out.println(retpayUrl+"===================");
          //  retMap.put("payUrl",retpayUrl);
            long t2=System.currentTimeMillis();
            Long sss= t2-t;
            System.out.println("请求时间为"+sss);
        } catch (Exception e) {
            //String s = HttpClientHelper.sendGet("http://api.wandoudl.com/api/ip?app_key=75191f311013adf875075fff27cc5760&pack=210646&num=1&xy=1&type=1&lb=&mr=2&");
            //String s = HttpClientHelper.sendGet("http://ip.11jsq.com/index.php/api/entry?method=proxyServer.generate_api_url&packid=1&fa=0&fetch_key=&groupid=0&qty=1&time=1&pro=&city=&port=1&format=txt&ss=1&css=&dt=1&specialTxt=3&specialJson=&usertype=16");
            CompanyIp companyIp1=CompanyIp.dao.findFirst("select * from company_ip where isU<3 order by  isU ");
            Db.update("update company_ip set isU=isU+1 where id="+companyIp.getId());
            System.out.println("ip不可用已切换ip"+companyIp1.getId());
            e.printStackTrace();
            i++;
            return topUp(phone,money,i,companyIp1);
        }
        return retMap;
    }

    public String wxPay(String url,String session,String ip,Integer port){

        String location= HttpCtccTest.wxsendGet(url,session,ip,port);
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


    public  String webWxLocation(String param,String ip,Integer port,String session){
        String url="https://paygo.189.cn:9779/189pay/service";
        Map map=new HashMap();
        map.put("request_params",param);
        String location= null;
        try {
            location = HttpCtccTest.sendPostService(url,map,ip,port,session);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return location;
    }

    public  String wxpay(String sessionId,String ip,Integer port){
        String url="https://wappay.189.cn/pay/toPay.do?bankCode=WX001&sessionid="+sessionId+"&state=";
        String locationHtml = HttpCtccTest.get(url,ip,port);
        Document document= Jsoup.parse(locationHtml);

        Elements script=document.select("input[name='request_params']");

        String  element=script.val();
        System.out.println(element+"----------------");
        return element;
    }


    public  String onlinePay(String payUrl,String ip,Integer port){
        String result = HttpCtccTest.getonlinePay(payUrl,ip,port);
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



    //引用js加密
    public  String encryption(String code){
        ScriptEngine se = new ScriptEngineManager().getEngineByName("javascript");
        String t = Thread.currentThread().getContextClassLoader().getResource("").getPath();
        System.out.println("t="+t);
        String retCode="";
        try {

       // se.eval(new FileReader(System.getProperty("user.dir")+"/src/main/webapp/resource/plugins/aes.js"));//本地环境
         //se.eval(new FileReader(System.getProperty("user.dir")+"/src/main/webapp/resource/plugins/encr.js"));//本地环境
          se.eval(new FileReader(t.substring(0, t.indexOf("WEB-INF"))+"resource/plugins/aes.js"));//服务器环境
          se.eval(new FileReader(t.substring(0, t.indexOf("WEB-INF"))+"resource/plugins/encr.js"));//服务器环境
            Invocable inv = (Invocable) se;
            retCode=(String) inv.invokeFunction("encrypt", code);
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
        //System.setProperty("jna.encoding", "GBK");
        //下单


           /*CtccTest ctcc=new CtccTest();
            Map map = ctcc.topUp("18001954958", "10",0);
            String a = map.get("sessionId").toString();
            String b = map.get("payUrl").toString();
            String c = map.get("orderid").toString();
            System.out.println(a);
            System.out.println(b);
            System.out.println(c);*/
            
            /**/

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
