package com.xunpay.money.utils;

import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.alibaba.fastjson.JSONObject;
import com.jfinal.log.Logger;
public class CmccTest {
	
	
	private Logger logger=Logger.getLogger(CmccTest.class);
	

	
	//下单接口
	public  static final String   SAVE_ORDER="https://touch.10086.cn/i/v1/pay/saveorder/";
	
	//支付接口
	public  static final String   PAY_H5="https://pay.shop.10086.cn/paygw/mobileAndBankPayH5";
	
	//获取微信redirect 接口
	public  static final String   PAY_SUBMIT="https://pay.it.10086.cn/payprod-format/h5/dup_submit";
	
	//回调接口
	public  static final String   CALLBACK2="https://pay.shop.10086.cn/iphone_paygw/unipayCallback2";
	
	//优惠接口
	public  static final String   PAY_RULE="https://touch.10086.cn/i/v1/pay/payrule/13838384380";
	
	/***
	 * 通过获取的ck下单
	 * @author jarrychrist
	 * @param  String  phone   String amount  String jsessionid   
	 * @return String url 
	 */
	public static String saveOrder(String phone,Double amount,String jsessionid,boolean isYh,String ip,int port) {
	
		String url=SAVE_ORDER+phone;
		
		Map<String, String> params=new HashMap<String, String>();
		
		Map<String, String> header=new HashMap<String, String>();
		
		header.put("Referer","https://touch.10086.cn/i/mobile/rechargecredit.html?welcome=1597041893138");
		header.put("User-Agent"," Mozilla/5.0 (iPhone; CPU iPhone OS 11_0 like Mac OS X) AppleWebKit/604.1.38 (KHTML, like Gecko) Version/11.0 Mobile/15A372 Safari/604.1");
		header.put("Content-Type","application/json; charset=UTF-8");
		header.put("Cookie","jsessionid-cmcc="+jsessionid);
		header.put("payPhoneNo",phone);
		
		params.put("channel", "0003");
		params.put("payWay", "WAP");
		params.put("chargeMoney", amount.toString());
		params.put("choseMoney", amount.toString());
		params.put("activityNO", "");
		if(isYh) {
			//有优惠
			params.put("operateId", "3215");
			params.put("amount", String.valueOf(amount*0.998));
		}else {
			//无优惠
			params.put("amount", String.valueOf(amount));
			params.put("operateId", "");
			
		}
		
		params.put("homeProv", "100");
		params.put("numFlag", "0");
		params.put("source", "");
				
		String result=HttpsClientHelper.postJsonProxy(url, params, header,null,0);
		System.out.println("下单返回的结果为"+result);
		JSONObject jsob=new JSONObject();
		JSONObject resultJson=(JSONObject) jsob.parse(result);
		String dataStr=resultJson.getString("data");
		
		//收银台链接s
		String payUrl=null;
		if( dataStr!=null && !dataStr.equals("null") ) {
			
			JSONObject dataHJson=(JSONObject)jsob.parse(dataStr);
			payUrl=dataHJson.getString("payUrl");
		}
		//payUrl=null表示下单失败
		return payUrl;
		
	}
	
	
	
	/***
	 * 支付 通过加载支付参数返回生成的jsp
	 * @author jarrychrist
	 * @param 
	 * @return   String payUrl
	 */
	public  Map<String,String> payAndH5(Map<String,String> map,String userName) {
		
	    Map<String,String> resultMap=new HashMap<String, String>();
		
		
	    Map<String,String> formParam=new HashMap<String, String>();
	    formParam.put("bankAbbr", "WXPAY");
	    formParam.put("orderId", map.get("orderId"));
	    formParam.put("type", map.get("type"));
	  //	  formParam.put("ipAddress", map.get("ipAddress"));
	    formParam.put("ts", map.get("ts"));
	    formParam.put("hmac", map.get("hmac"));
	    formParam.put("channelId", map.get("channelId"));
	    
		String body=CmccHttp.sendPost(PAY_H5, formParam,userName);
		System.out.println(body);
		Document doc=Jsoup.parse(body);
		//订单号
		String 	sign=doc.select("input[name=sign]").attr("value");
		String 	request_params=doc.select("input[name=request_params]").attr("value");
		CmccUtil util=new CmccUtil();
		//查单地址
		String noticeUrl=util.getWechNotice(sign, request_params);
		
		@SuppressWarnings("deprecation")
		String decodeUrl=URLDecoder.decode(noticeUrl);
		//最终的回调地址
		String voucherUrl=util.getVoucher(decodeUrl);
		System.out.println("回调地址"+voucherUrl);
		//CmccUtil.downLoad(body);
		//下载
		
		resultMap.put("orderId", map.get("orderId"));//官方订单号
		resultMap.put("noticeUrl", noticeUrl);//回调查单地址
		resultMap.put("voucherUrl", voucherUrl);//凭证地址
		resultMap.put("body", body);//官方订单号
		
		return resultMap;
	}
	
	/***
	 *  回调 监控订单回调
	 * @author jarrychrist
	 * @param
	 * @return  String noticeUrl
	 * 0 未充值 1已充值
	 * 官方状态 0 未支付 2支付成功 3支付失败 4充值成功 5充值失败  6-12 订单退费状态
	 */
	 public  int orderStatus(String voucherUrl) {
		 
		 int falg=0;
		 
		 String temp[]=voucherUrl.split("orderId=");
		 String callBackUrl="https://touch.10086.cn/i/v1/pay/paycallback/?orderId="+temp[1];
		 String resutlStr= CmccHttp.sendGet(callBackUrl);
		 JSONObject jso= JSONObject.parseObject(resutlStr);
		 JSONObject dataObject=JSONObject.parseObject(jso.getString("data"));
		 String status= dataObject.getString("payOrderStatus");
		 if(status.equals("4")) {
			 System.out.println("充值成功");
			 falg=1;
		 }else {
			 System.out.println("充值失败");
			 falg=0;
		 }
		 
		 return  falg;
	 }
	
	

	//测试类
	public static void main(String[] args) {

	    /*String voucherUrl="https://touch.10086.cn/i/mobile/stc-recharge-result.html?orderId=490447819025468622&requestId=20200818113019752358266138500077&hmac=f7affc6bcf3c1ff7b28ead0c58d8d11c&reserved2=credit&reserved1=0003xWAP&status=0&ts=20200818163300";
		orderStatus(voucherUrl);
		
		String s=saveOrder("13838384380", 10D, "jsessionid-cmcc=nF6C4B86FDDC58C833AE24CD4A62F53A1-1", true,"47.112.156.197",36898);

		System.out.println("s"+s);

		String phone="18780768610";
		String ck="jsessionid-cmcc=n2047EFDDAA607CCE0605CEF2EAE23E60-2";

   		String url="https://touch.10086.cn/i/v1/pay/couponrule/"+phone;

   		Map<String, String> params=new HashMap<String, String>();

   		Map<String, String> header=new HashMap<String, String>();

   		header.put("Referer","https://touch.10086.cn/i/mobile/rechargecredit.html?welcome=1597041893138");
   		header.put("User-Agent","Mozilla/5.0 (iPhone; CPU iPhone OS 11_0 like Mac OS X) AppleWebKit/604.1.38 (KHTML, like Gecko) Version/11.0 Mobile/15A372 Safari/604.1");
   		header.put("Content-Type","application/json; charset=UTF-8");
   		header.put("Cookie",ck);

   		params.put("phoneNo", "MTM4MzgzODQzODA=");//
   		params.put("provCode", "371");
   		params.put("channel", "0003");
   		params.put("payPhoneNo", "18780768610");

   		String result= HttpsClientHelper.postJsonProxy(url, params, header, null, 0);
   		if(result!=null) {
   			JSONObject jsonb=JSONObject.parseObject(result);
   	   		String retCode=jsonb.getString("retCode");
   	   		if("000000".equals(retCode)) {
   	   			System.out.println("当前可用的ck为"+ck);

   	   		}else {
   	   		System.out.println("当前不可用的ck为"+ck);
   	   		}
   		}


   		System.out.println(result);*/


		String s = saveOrder("18381833437", 30.00, "n3A944A4BCCB190FE05CE72EB9DE9CDDB-1", true, "", 0);
		System.out.println(s);


	}


	
//	
//	
//	
//	public String getIp() {
//		return ip;
//	}
//
//	public void setIp(String ip) {
//		this.ip = ip;
//	}
//
//	public int getPort() {
//		return port;
//	}
//
//
//	public void setPort(int port) {
//		this.port = port;
//	}
//

}
