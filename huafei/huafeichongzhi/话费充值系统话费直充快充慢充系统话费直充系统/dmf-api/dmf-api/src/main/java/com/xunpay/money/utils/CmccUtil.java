package com.xunpay.money.utils;

import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.output.FileWriterWithEncoding;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.alibaba.fastjson.JSONObject;

public class CmccUtil {
	
	//获取完整支付链接  490185394008501864-1597458994539-3f2912192665de6b6ea3b72d21be77ae-20.html
	public  String ssoCheckHtml(String payUrl) {
		
		String location=CmccHttp.sendGetProxyLocation(payUrl,null,null,0);
		return location;
	}
	
	//获取HTML中的参数
	public  Map<String, Object> getHtmlParam(String location) {
		
		Map<String, Object> map=new HashMap<String, Object>();
		
		String htmlParam=CmccHttp.sendGetParam(location);
		Document doc=Jsoup.parse(htmlParam);
		//订单号
		String 	orderId=doc.select("input[name=orderId]").attr("value");
		String 	type=doc.select("input[name=type]").attr("value");
		String 	ipAddress=doc.select("input[name=ipAddress]").attr("value");
		String 	ts=doc.select("input[name=ts]").attr("value");
		String 	hmac=doc.select("input[name=hmac]").attr("value");
		String 	channelId=doc.select("input[name=channelId]").attr("value");
		
		map.put("orderId", orderId);
		map.put("type", type);
		map.put("ipAddress", ipAddress);
		map.put("ts", ts);
		map.put("hmac", hmac);
		map.put("channelId", channelId);
			
		return map;
	}
	
	//当前手机号是否有优惠政策
	public  boolean isYh(String phone) {
		
		boolean flag=false;

		Map<String, String> params=new HashMap<String, String>();
		Map<String, String> header=new HashMap<String, String>();
		header.put("Referer", "https://touch.10086.cn/i/mobile/rechargecredit.html?welcome=1597041893138");
		header.put("User-Agent", "Mozilla/5.0 (iPhone; CPU iPhone OS 11_0 like Mac OS X) AppleWebKit/604.1.38 (KHTML, like Gecko)");
		header.put("Content-Type", "application/json; charset=UTF-8");
		
		String enPhone=EncryptUtils.encryptBASE64(phone);
		params.put("amountFlag", "1");
		params.put("saleFlag", "1");
		params.put("enableFlag", "1");
		params.put("saleQueryType", "2");
		params.put("phoneNo", enPhone.replace("\r\n", ""));//
		params.put("enableType", "0");
		params.put("provCode", "371");
		params.put("channel", "0003");
		
		String payRule=HttpsClientHelper.postJsonFile1(CmccTest.PAY_RULE, params, header);
		JSONObject jso=JSONObject.parseObject(payRule);
		if(jso!=null) {
			String data=jso.getString("data");
			JSONObject dataStr=JSONObject.parseObject(data);
			String saleRules=dataStr.getString("saleRules");
			
			if("[]".equals(saleRules)) {
				flag=false;
				System.out.println("没有优惠");
			}else {
				flag=true;
				System.out.println("有优惠");
			}
			System.out.println(saleRules);
			
		}
	
		
		System.out.println(payRule);
		
		
		return flag;
		
	}
	
	
	//下载jsp  传wxbody支付内容
	public  String downLoad(String body,String orderNo) {
	       String t = Thread.currentThread().getContextClassLoader().getResource("").getPath();
		  try {
              //BufferedWriter out = new BufferedWriter(new FileWriterWithEncoding(System.getProperty("user.dir")+"/src/main/webapp/order/"+orderNo+".jsp","UTF-8"));//本地目录
            
              String pageUrl=t.substring(0, t.indexOf("WEB-INF"))+"order/"+orderNo+".jsp";
              System.out.println("路径为"+pageUrl);
			  BufferedWriter out = new BufferedWriter(new FileWriterWithEncoding(pageUrl,"UTF-8"));//服务器目录
              
              out.write(body);
              out.close();
              System.out.println("文件创建成功！");
          } catch (IOException e) {
        	  
              e.printStackTrace();
          }
		  return null;
	}
	
	//获取userName
	public static String getUserName(String cmccToken) {
		
		String location=CmccHttp.sendGetProxyLocation("https://login.10086.cn/SSOCheck.action?channelID=12103&backUrl=https://pay.shop.10086.cn/paygw/490373088083264673-1597646689143-20-SSOCheck.htm",cmccToken,null,0);
		System.out.println("username重定向"+location);
		String userName=CmccHttp.sendGetProxyCookie(location,null,0);
		
		return userName;
		
	}
	
	//获取微信支付的回调地址   redirect_url='
	public  String getWechNotice(String sign,String request_params) {
		
		Map<String,String> paramMap=new HashMap<String, String>();
		paramMap.put("sign", sign);
		paramMap.put("request_params", request_params);
		String wxForm=CmccHttp.sendPost(CmccTest.PAY_SUBMIT,paramMap,null);
		Document document=Jsoup.parse(wxForm);
		
		//获取链接
		String 	wxAction=document.select("form").attr("action");
		String wxUrl []=wxAction.split("&redirect_url=");
		String noticeUrl=wxUrl[1];
		return noticeUrl;
	}
	
	//获取最终的凭证地址（充值成功则是永久保存）
    public  String  getVoucher(String noticeUrl) {
    	
        String jsHtml=CmccHttp.sendGet(noticeUrl);
        System.out.println("回调页面第一步的参数"+jsHtml);
        Document doc=Jsoup.parse(jsHtml);
	    Elements script=doc.getElementsByTag("script");
	  //  System.out.println("长度"+);
	    Element element=script.get(script.size()-1);
		String outHtml= element.outerHtml();
		String sign_params=outHtml.replace("$(function () {", "").replace("})", "").replace("$(\"#request_params\").val(JSON.stringify(request_params));", "").replace("$(\"#sign\").val(JSON.stringify(sign));", "");
		String temp[]=sign_params.split("var sign =");
		String request_params=temp[0].replace("var request_params = ", "").replace("<script type=\"text/javascript\">", "");
		
		String request_p=request_params.trim();
		String request=request_p.substring(0,request_p.length()-1);
		
		String sign_p=temp[1].replace("var request_params = ", "").replace("</script>", "");
		
		String sign_t=sign_p.trim();
		String sign=sign_t.substring(0,sign_t.length()-1);
		
		Map<String,String> map=new HashMap<String, String>();
		
		map.put("request_params",request);
		map.put("sign",sign);
	    System.out.println("s"+map);
        //二次请求
		String locationVoucher=CmccHttp.sendPostLocation(CmccTest.CALLBACK2,map);
    	return locationVoucher;
    }
	

	public static void main(String[] args) {
		//获取最终的凭证地址
		//String url="https://pay.it.10086.cn/payprod-format/cashier/wxreturn?Params=TWNoSWQ9TjAwNTUwMDAxJk5vdGlmeVRpbWU9MjAyMDA4MTgxMTMxMTAmT3JkZXJObz0xNTk3NzIxNDE5NTk1MTQ3MjExMzY1OTEyMDAwNjEmUGF5VHJhbnM9MDA1NTIwMjAwODE4MTEzMTEwMDU3MDMyODUzNDE0OTkmUGF5bWVudD0xMDAwJlBheW1lbnRUeXBlPVdFSVhJTi1XQVAmUHJvZHVjdE5hbWU95Li6MTM2KioqKjQ5NjHor53otLnlhYXlgLwmUmVxRGF0ZT0yMDIwMDgxOCZSZXFTeXM9MDA1NSZSZXR1cm5VUkw9aHR0cHM6Ly9wYXkuc2hvcC4xMDA4Ni5jbi9pcGhvbmVfcGF5Z3cvdW5pcGF5Q2FsbGJhY2syJlJzcENvZGU9MDEwQTAwJlJzcERlc2M95oiQ5YqfJlVwYXlUcmFuc0lkPTIwMjAwODE4MjY3NzM3MjkyNTYxNDA3MDM3MTE2NDcyJlZlcnNpb249MS4wJlNpZ25WYWx1ZT1BVzNmUFEzaDFYbUNteEtxOXc5TjBPb21WVnBSVC9ZWVE1dWlQck5seDFNaHhHUm0vaGpVc0ZrTUd1LzVLQ0pmUGx4NnlBbG1HSXczOFdqbllDTlljU0JZQ0JwNXBBMGxrckZ4bzJFRnllRjBmcURFQ1V5bWJlZy9VWlRjbW5XYzM0MU5YbGZEY3FpY1A0RlMvUEdPR2ZlNko1dEFCTnNUTDNQOGtxRXg5ZkE9JkNlcklEPTExRDk2Njg=";
		
		//String voucherUrl=getVoucher(url);
//		
		//System.out.println(voucherUrl);
		
		//https://pay.shop.10086.cn/paygw/490185394008501864-1597458994539-20-SSOCheck.htm
		
		//String payUrl="https://pay.shop.10086.cn/paygw/490446451075587084-1597720051944-20-SSOCheck.htm";

		
		//String location=ssoCheckHtml(payUrl);
		//Map<String,Object> param=getHtmlParam(location);
//		downLoad("<body>\r\n" + 
//				"	    <form name=\"postSubmit\" method=\"post\" action=\"https://pay.it.10086.cn/payprod-format/h5/dup_submit\" >\r\n" + 
//				"	    \r\n" + 
//				"	    <input type=\"hidden\" name=\"sign\" value=\"{&quot;CerID&quot;:&quot;10f0683&quot;,&quot;SignValue&quot;:&quot;DwTu+i4ZbejuuDbUPuIb+N6anKO7NGkP6ba6PDp78ciP8dq387b0awDHd6EF4vCTVvWcf/LnEa1slxb9Qzpkc/S2Yz3ubHsnvZbtDMz7i5ed9oPVHXLwJDSg8zZ3hZvTsUpRhhbnMYbCDzlpVY9cnG3/HZSmCTE4zmH+Lfg0Pi8=&quot;}\" />\r\n" + 
//				"	    \r\n" + 
//				"	    <input type=\"hidden\" name=\"request_params\" value=\"{&quot;MerActivityID&quot;:&quot;&quot;,&quot;ProductName&quot;:&quot;为136****4961话费充值&quot;,&quot;MchId&quot;:&quot;N00550001&quot;,&quot;CustomParam&quot;:&quot;490202441098263445|20|0003xWAP&quot;,&quot;OrderNo&quot;:&quot;159747604194805203590131200061&quot;,&quot;ReturnURL&quot;:&quot;https://pay.shop.10086.cn/iphone_paygw/unipayCallback2&quot;,&quot;ReqChannel&quot;:&quot;2110&quot;,&quot;IDValue&quot;:&quot;&quot;,&quot;IDType&quot;:&quot;&quot;,&quot;OrderDate&quot;:&quot;20200815&quot;,&quot;BusiType&quot;:&quot;1011&quot;,&quot;Gift&quot;:&quot;0&quot;,&quot;Payment&quot;:&quot;2000&quot;,&quot;TimeoutExpress&quot;:&quot;30m&quot;,&quot;ExtraParams&quot;:{&quot;ClientIP&quot;:&quot;223.149.106.235&quot;},&quot;Version&quot;:&quot;1.0&quot;,&quot;NotifyURL&quot;:&quot;https://pay.shop.10086.cn/iphone_paygw/unipayNotify2&quot;,&quot;PaymentType&quot;:&quot;WEIXIN-WAP&quot;,&quot;ProductDesc&quot;:&quot;话费充值&quot;,&quot;ReqDateTime&quot;:&quot;20200815153558&quot;,&quot;ReqSys&quot;:&quot;0055&quot;}\" />\r\n" + 
//				"	     \r\n" + 
//				"		\r\n" + 
//				"	    </form>   \r\n" + 
//				"	   <script>  \r\n" + 
//				"	      document.postSubmit.submit();   \r\n" + 
//				"	    </script>\r\n" + 
//				"</body>") ;
//		
//		
		
	//	isYh("13838384381");
		
		//2          2Ekg8zdp0P0GUbosRYInUw==z
//		String userName="2Ekg8zdp0P0GUbosRYInUw==";
//		String location=ssoCheckHtml("https://pay.shop.10086.cn/paygw/490452114042986818-1597725715105-20-SSOCheck.htm");
//		Map maps=getHtmlParam(location);
//		CmccTest.payAndH5(maps,userName);
//		System.out.println(maps);
		//test();
//	
		//下载
		//downLoad("中午", "111");
		//3
		//4
		// 5 回调
		//https://touch.10086.cn/i/mobile/stc-recharge-result.html?
	   // String voucherUrl="https://touch.10086.cn/i/v1/pay/paycallback/?orderId=490446451075587084&requestId=20200818111125873776878147371204&hmac=d7abdb95a7e2dd48911d3fcce4262bac&reserved2=credit&reserved1=0003xWAP&status=0&ts=20200818111359";
		//CmccTest.orderStatus(voucherUrl);
		//System.out.println("userName-------->"+getUserName("cbf760d299b147dfbda9acfacd36a2e2@.10086.cn"));
		
		
		
		
	}
	
	
//	public static String test() {
//		
//		String url="{\"CerID\":\"11D9668\",\"SignValue\":\"e72LT+P2aW8fSk7h7fgzrYqI0Ej8rXTA7Mox4Tdf+uOaIlwBSkCzi61XMKVI7+kqyPvRmnFwRDNjV54PUTZ+Cx2W/LQZ/YUh7SNQHLieaBi5pRBG1qpDoe+xLrivpz9Xm6HuJMi57UCcJ2MbmK7lJhHnE5+XHyonS7uml9OCoyE=\"};";
//		String temp=url.substring(0,url.length()-1);
//		System.out.println(temp);
//		return null;
//		
//	}
}
