package com.xunpay.money.utils;

import java.io.FileReader;
import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jfinal.log.Log4jLogger;
import com.jfinal.log.Logger;

public class Cmcc {


    public static Logger log = Log4jLogger.getLogger(Cmcc.class);


//	
//	public static void main(String[] args) {
//		
//		Cmcc cmcc =new Cmcc();
//		
//		String result=cmcc.encryption("123456");
//		
//		System.out.println(result);
//	}


    /**
     * getBefore
     * 一个验证码token
     **/
    private static String getBefore(String userName) {

        String url = "https://login.10086.cn/loadToken.action";

        String result = HttpsClientHelper.sendPostLoadToken(url, userName);

        JSONObject jsob = JSONObject.parseObject(result);

        String xaBeore = jsob.getString("result");

        return xaBeore;
    }

    /**
     * 获取验证码Flag
     * request
     * url:https://login.10086.cn/sendflag.htm?timestamp
     * GET /sendflag.htm?timestamp=1584325376777 HTTP/1.1
     * Host: login.10086.cn
     * hearder
     * type:GET
     **/
    public static String getFlag() {

        String url = "https://login.10086.cn/sendflag.htm?timestamp";

        String result = HttpsClientHelper.sendGetFlag(url);

        String str = result.split(":")[1];

        String flag = str.split(";")[0].trim();

        return flag;

    }

    /**
     * 发送验证码
     * request:
     * url:https://login.10086.cn/sendRandomCodeAction.action
     **/

    public static String sendCode(String phone) {

        String cookie = getFlag();

        //xaBefore
        String xaBefore = getBefore(phone);

        String isSuccess = "";

        String url = "https://login.10086.cn/sendRandomCodeAction.action";

        Map<String, String> maps = new HashMap<String, String>();

        maps.put("userName", phone);

        maps.put("type", "01");

        maps.put("channelID", "12014");

        String result = HttpsClientHelper.sendPostCode(url, maps, cookie, xaBefore);

        if ("0".equals(result)) {

            isSuccess = "1";

        } else {

            isSuccess = "0";
        }

        return isSuccess;

    }


    /**
     * 获取登录的短信验证码
     * 加密后传给移动方
     **/

    public static String loginCode(String smsCode) {

        return "sssssssssss";
    }

    /**
     * 移动登录
     * 需要用转换后的code来处理
     * 返回artifact
     **/

    public static String login(String phone, String loginCode) {

        String tempAttr[] = null;

        String temp = "";

        String url = "https://login.10086.cn/login.htm";

        Map<String, String> loginMap = new HashMap<String, String>();

        String resultCode = encryption(loginCode);

        //encrypt.js
        loginMap.put("accountType", "01");
        loginMap.put("pwdType", "02");
        loginMap.put("account", phone);
        loginMap.put("password", resultCode);
        loginMap.put("inputCode", "");
        loginMap.put("backUrl", "https%3A%2F%2Fshop.10086.cn%2Fi%2F%3Ff%3Drechargecredit");
        loginMap.put("rememberMe", "0");
        loginMap.put("channelID", "12003");
        loginMap.put("loginMode", "03");
        loginMap.put("protocol", "https");
        loginMap.put("timestamp", "1588044415105");

        String result[] = HttpsClientHelper.sendPostLogin(url, loginMap);

        JSONObject jsObject = JSONObject.parseObject(result[0]);

        String artifact = jsObject.getString("artifact");

        System.out.println("第一次登录返回的cookie为=====>" + result[1]);

        return artifact + "--" + result[1];
    }

    /**
     * 登录三部曲
     * ssoCheck
     **/

    public static String ssoCheck(String artifact, String cookie) {

        //https://shop.10086.cn/i/v1/auth/getArtifact?backUrl=https://shop.10086.cn/i/?f=rechargecredit&artifact=caf6aa1e10bf4054a62c0246bfcef3b0&type=00

        Date date = new Date();

        long time = date.getTime();

        //String backUrl="https://touch.10086.cn/i/mobile/home.html?welcome="+time+"";

        String url = "https://shop.10086.cn/i/v1/auth/getArtifact?backUrl=https://shop.10086.cn/i/?f=rechargecredit&artifact=" + artifact + "&type=00";

        String getArtifact2 = HttpsClientHelper.sendGetSsoCheck(url, cookie);

        System.out.println("getArtifact2为" + getArtifact2);

        return getArtifact2;
    }

    /**
     * 重定向location 到welcome访问welcome
     * 返回jsssionid
     **/

    public static String redirectLocation(String getArtifact2, String cookie) {

        String addCookie = null;

        String[] location_rcookie = HttpsClientHelper.sendGetLocation(getArtifact2, cookie);

        StringBuilder sb = new StringBuilder();

        addCookie = sb.append(cookie).append(location_rcookie[1]).toString();

        System.out.println("此时的cookie集合为=====>" + addCookie);


        addCookie.indexOf("jsessionid-echd-cpt-cmcc-jt");

        String result = HttpsClientHelper.sendGetWelcom(location_rcookie[0], addCookie);

        System.out.println("欢迎页面为=====>" + result);


        String[] jsessionidTemp = addCookie.split("jsessionid-echd-cpt-cmcc-jt=");

        int index = addCookie.indexOf(jsessionidTemp[1]);

        //返回可以用下单的凭证
        String subStr = addCookie.substring(index, index + 32);

        return subStr;
    }

    /**
     *
     * 需要拿到服务器端的sessionID
     * 先访问atf
     * 	//http://touch.10086.cn/i/v1/auth/getArtifact2?backUrl=http://touch.10086.cn/i/mobile/home.html&artifact=xxx，获取到jsessionid-echd-cpt-cmcc-jt
     * *

     public static String getCmccJT(String artifact,String cookie) {

     String url="https://touch.10086.cn/i/v1/auth/getArtifact2?backUrl=http://touch.10086.cn/i&artifact="+artifact+"&type=00";

     String jsessionid=HttpsClientHelper.sendGetSso(url,cookie);

     return jsessionid;
     }
     **/


    /**
     * 获取手机号归属地
     * <p>
     * return 返回归属地id
     **/

    public static String getArea(String phone) {

        String url = "https://touch.10086.cn/i/v1/res/numarea/" + phone + "?time=2020314112020255&channel=02";

        String result = HttpsClientHelper.sendGetArea(url);

        JSONObject jsonObject = new JSONObject();

        JSONObject resultJson = (JSONObject) jsonObject.parse(result);

        String dataStr = resultJson.getString("data");

        JSONObject dataHJson = (JSONObject) jsonObject.parse(dataStr);

        String prov_cd = dataHJson.getString("prov_cd");

        return prov_cd;

    }

    /**
     * 获取交易规则()
     * 返回的是交易规则的折扣
     * <p>
     * 一般只需要获取折扣即可
     * 非本机号码一般都是0.998
     **/

    public static String getrule(String phone) {

        String enPhone = EncryptUtils.encryptBASE64(phone);

        String url = "https://touch.10086.cn/i/v1/pay/payrule/13838384380";

        Map<String, String> maps = new HashMap<String, String>();

        Map<String, String> header = new HashMap<String, String>();

        header.put("Content-Type", "application/json; charset=UTF-8");

        maps.put("amountFlag", "1");

        maps.put("saleFlag", "1");

        maps.put("enableFlag", "1");

        maps.put("saleQueryType", "2");

        maps.put("phoneNo", enPhone);

        maps.put("enableType", "0");

        maps.put("provCode", "200");

        maps.put("channel", "0003");

        String result = HttpsClientHelper.postJsonFile1(url, maps, header);

        JSONObject jsOb = JSONObject.parseObject(result);

        String data = jsOb.getString("data");

        String sale = null;
        String operateId = null;

        if (data != null) {

            JSONObject dataObj = (JSONObject) jsOb.parse(data);

            String saleRules = dataObj.getString("saleRules");

            if (!"[]".equals(saleRules)) {

                JSONArray jsonArray = JSONArray.parseArray(saleRules);

                String saleCode = jsonArray.getString(0);

                JSONObject saleObjec = JSONObject.parseObject(saleCode);

                sale = saleObjec.getString("saleCode");

                operateId = saleObjec.getString("operateId");

            } else {

                sale = "1";
            }
        }

        if (operateId != null) {


            return sale + "-" + operateId;

        }

        return sale;

    }


    /**
     * param
     * {"channel":"0003","payWay":"WAP","amount":99.8,"chargeMoney":100,"choseMoney":100,"activityNO":"","operateId":3215,"homeProv":"371","numFlag":"0","source":""}
     * <p>
     * 下单
     * phone     下单手机号码
     * amount    下单金额
     * cookie    cookie
     * saleValue 折扣
     * 返回订单链接
     * https://pay.shop.10086.cn/paygw/477313620178256041-1584587221472-3a3a73ccd40df4c92be9d9a68ab0703c-20.html
     **/

    public static String saveOrder(String phone, Double amount, String cookie, Double saleValue, boolean opID) {

        String url = "https://touch.10086.cn/i/v1/pay/saveorder/" + phone + "";

        Map<String, String> params = new HashMap<String, String>();

        Map<String, String> header = new HashMap<String, String>();

        header.put("Accept", "application/json, text/javascript, */*; q=0.01");
        header.put("Origin", "https://touch.10086.cn");
        header.put("User-Agent", "Mozilla/5.0 (Linux; Android 8.0; Pixel 2 Build/OPD3.170816.012) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/78.0.3904.108 Mobile Safari/537.36");
        header.put("Content-Type", "application/json; charset=UTF-8");
        header.put("Sec-Fetch-Site", "same-origin");
        header.put("Sec-Fetch-Mode", "cors");
        header.put("Referer", "https://touch.10086.cn/i/mobile/rechargecredit.html?welcome=1584427131670");
        header.put("Accept-Encoding", "gzip, deflate");
        header.put("Accept-Language", "zh-CN,zh;q=0.9");

        params.put("channel", "0003");
        params.put("payWay", "WAP");

        params.put("amount", String.valueOf((amount * saleValue)));

        params.put("chargeMoney", amount.toString());
        params.put("choseMoney", amount.toString());
        params.put("activityNO", "");
        if (opID) {

            params.put("operateId", "3215");

        } else {

            params.put("operateId", "");

        }

        params.put("homeProv", "100");
        params.put("numFlag", "0");
        params.put("source", "");

        String result = HttpsClientHelper.postJsonFile1(url, params, header);

        JSONObject jsob = new JSONObject();

        JSONObject resultJson = (JSONObject) jsob.parse(result);

        String dataStr = resultJson.getString("data");

        //付款链接
        String payUrl = null;

        if (dataStr != null && !dataStr.equals("null")) {

            JSONObject dataHJson = (JSONObject) jsob.parse(dataStr);

            //订单编号
            String orderId = dataHJson.getString("orderId");

            System.out.println(orderId);
            //充值金额
            String money = dataHJson.getString("amount");

            System.out.println(money);
            payUrl = dataHJson.getString("payUrl");

            System.out.println(payUrl);

        }

        //payUrl=null表示下单失败

        return payUrl;//这个支付链接其实也可以作为支付凭证，中间会经过重定向的过程

    }

    /**
     * 将订单链接格式分解成 订单号，时间戳和hmac
     * https://pay.shop.10086.cn/paygw/477312844178263011-1584586444993-be48cce017da15ca3f95067645260733-20.html?orderId=477312844178263011
     **/

    public static String formatOrderId(String payUrl) {

        String url[] = payUrl.split("paygw/");

        String[] urls = url[1].split("=");

        String rexResult = urls[0];

        return rexResult;
    }


    /**
     * 微信支付
     * <p>
     * 分为一次请求和二次请求
     * <p>
     * 二次请求提取深度链接 Referer: https://pay.shop.10086.cn/paygw/mobileAndBankPayH5
     * <p>
     * 返回加密的HTMl
     **/

    public static String wechatPayPre(String orderId, String timeStr, String cookie) {

        String url = "https://pay.shop.10086.cn/paygw/mobileAndBankPayH5";

        Map<String, String> maps = new HashMap<String, String>();

        maps.put("bankAbbr", "WXPAY");

        maps.put("orderId", orderId);

        maps.put("type", "C");

        maps.put("ts", timeStr);

        maps.put("hmac", "1e1268e6a4afb1a0febb4346101087fb");//这个hmac忽略不计

        maps.put("channelId", "20");

        String preHtml = HttpsClientHelper.sendPostPay(url, maps, cookie);

        return preHtml;
    }

    /**
     * 返回数组对象
     * 支付宝微信都用这个方法
     * 0为sign 1为params
     **/

    @SuppressWarnings("null")
    public static String[] getSignParam(String preHtml) {

        //html
        Document document = Jsoup.parse(preHtml);

        //获取签名
        String sign = document.select("input[name=sign]").attr("value");

        //获取参数
        String params = document.select("input[name=request_params]").attr("value");

        String[] strArray = null;

        if (sign != null && params != null) {

            strArray = new String[]{sign, params};
        }

        return strArray;

    }

    /**
     * 返回移动封装好的微信链接html
     **/

    public static String getCmccPayUrl(String[] paramer, String cookie) {

        String url = "https://pay.it.10086.cn/payprod-format/h5/dup_submit";

        Map<String, String> params = new HashMap<String, String>();

        params.put("sign", paramer[0]);

        params.put("request_params", paramer[1]);

        String payUrlHtml = HttpsClientHelper.sendPostPayUrl(url, params, cookie);

        return payUrlHtml;

    }

    /**
     * 返回h5微信支付链接
     **/

    @SuppressWarnings("null")
    public static String mwebUrl(String html) {

        StringBuilder sb = new StringBuilder();

        //html
        Document document = Jsoup.parse(html);

        //获取链接
        String action = document.select("form").attr("action");

        String[] formAction = action.split("&");

        String webUrl = sb.append(formAction[0]).append("&").append(formAction[1]).toString();

        return webUrl;

    }


    /**
     * 微信支付
     * 返回微信深度链接
     * <p>
     * get请求腾讯机器
     **/

    public static String wechatPay(String webUrl) {

        String deepLinkHtml = HttpsClientHelper.sendGetDeepLink(webUrl);

        return deepLinkHtml;
    }

    /**
     * 解析微信深度链接
     **/
    public static String parseWechatDeepLink(String deepLinkHtml) {

        String link = "";

        String linkTemp = null;

        //获取支付链接成功
        if (deepLinkHtml.indexOf("ok") != -1) {

            String[] urlSplit = deepLinkHtml.split("url=");

            String temp = urlSplit[1];

            int index = temp.indexOf("var");

            linkTemp = temp.substring(1, index).trim();

            int length = linkTemp.length();

            link = linkTemp.substring(0, length - 2);


        } else {

            log.info("获取深度链接失败，页面解析错误");

        }

        return link;
    }


    /**
     * 支付宝支付
     * <p>
     * 返回支付链接
     **/

    public static String alipayPre(String orderId, String timeStr, String cookie) {

        String url = "https://pay.shop.10086.cn/paygw/mobileAndBankPayH5";

        Map<String, String> maps = new HashMap<String, String>();

        maps.put("bankAbbr", "ALIPAY");

        maps.put("orderId", orderId);

        maps.put("type", "C");

        maps.put("ts", timeStr);

        maps.put("hmac", "1e1268e6a4afb1a0febb4346101087fb");

        maps.put("channelId", "20");

        String payUrl = HttpsClientHelper.sendPostPay(url, maps, cookie);

        return payUrl;
    }


    /**
     * 返回移动封装好的支付宝链接location
     **/

    public static String alipay(String[] paramter, String cookie) {

        String url = "https://pay.it.10086.cn/payprod-format/h5/dup_submit";

        Map<String, String> params = new HashMap<String, String>();

        params.put("sign", paramter[0]);

        params.put("request_params", paramter[1]);

        String locationHtml = HttpsClientHelper.sendPostPayUrl(url, params, cookie);

        return locationHtml;

    }

    /**
     * 解析支付宝locationHTML，获取支付宝深度链接
     **/
    public static String parseAlipayDeepLink(String locationHtml) {

        String link = "";

        Document document = Jsoup.parse(locationHtml);

        Elements script = document.getElementsByTag("script");

        Element element = script.get(0);

        String locationHref = element.outerHtml();

        String str = locationHref.replace("<script>", "").trim();

        String str1 = str.replace("</script>", "").replace("window.location.href", "").trim();

        String[] gateWayArr = str1.split("https:");

        String deepLink = gateWayArr[1];

        int length = deepLink.length();

        String href = deepLink.substring(0, length - 2);

        StringBuffer sb = new StringBuffer();

        link = sb.append("https:").append(href).toString();


        return link;
    }

    /**
     * 回调
     * 通过订单url来查询是否支付
     * <p>
     * 第一次请求的响应可以来判断
     * <p>
     * 二次请求获取支付成功的凭证
     * <p>
     * 返回支付凭证
     * <p>
     * url=null说明未充值成功
     **/

    public static String queryOrder(String orderURl) {

        //boolean isPay=false;

        String locationStr = HttpsClientHelper.sendGetNotify1(orderURl);

        String voucherUrl = null;

        String url = null;

        if (locationStr != null) {

            String[] strA = locationStr.split(" ");

            String locationUrl = strA[1];


            if (locationUrl.indexOf("pay.shop.10086") != -1) {

                voucherUrl = HttpsClientHelper.sendGetNotify1(locationUrl);

                String[] urlArrar = voucherUrl.split(" ");

                url = urlArrar[1];
            }
        }
        return url;

    }


    /***
     * urlEncode
     * urlDecode
     *
     * */
    @SuppressWarnings("deprecation")
    public static String urlEncode(String str) {

        return URLEncoder.encode(str);

    }


    //引用js加密
    public static String encryption(String code) {
        // 获取JS执行引擎  System.getProperty("user.dir")+/src/main/webapp/resource/plugins/aes.js   测试使用此路径
        ScriptEngine se = new ScriptEngineManager().getEngineByName("javascript");
        String t = Thread.currentThread().getContextClassLoader().getResource("").getPath();
        String retCode = "";
        try {

            se.eval(new FileReader(System.getProperty("user.dir") + "/src/main/webapp/resource/plugins/pwd.js"));
            //se.eval(new FileReader(t.substring(0, t.indexOf("WEB-INF"))+"resource/plugins/aes.js"));
            //se.eval(new FileReader(t.substring(0, t.indexOf("WEB-INF"))+"resource/plugins/encr.js"));
            Invocable inv = (Invocable) se;
            retCode = (String) inv.invokeFunction("getPwd", code);
        } catch (Exception e) {
            System.out.println("异常了——-----------" + e.getMessage());
        }
        return retCode;
    }

    public static void main(String[] args) {

        getrule("13838384380");
    }

}
