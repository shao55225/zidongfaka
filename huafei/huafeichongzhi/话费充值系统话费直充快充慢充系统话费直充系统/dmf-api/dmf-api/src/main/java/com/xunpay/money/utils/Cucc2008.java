package com.xunpay.money.utils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import net.sf.json.JSONArray;

public class Cucc2008 {
	
	public static final String SAVA_ORDER="https://uywap.10010.com/re/pay/getPayUrl";
	
	
	public static Map<String,Object> getPayUrl(String phone,String amount){
		
		Map<String, Object> resultMap=new HashMap<String, Object>();
		
		Map<String, Object> header=new HashMap<String, Object>();
		header.put("Referer", "https://uywap.10010.com/npage/pay/OnLinePay.html");
		header.put("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
		header.put("User-Agent", "Mozilla/5.0 (Linux; Android 8.0.0; Pixel 2 XL Build/OPD1.170816.004) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/78.0.3904.108 Mobile Safari/537.36");
		
		Map<String, Object> params=new HashMap<String, Object>();
	
	  //phoneType=mobile&phoneno=15674540001&payFee=100&otherFee=&payfeerate=0.995&wechatFlag=false  标准金额
		//phoneType=mobile&phoneno=15674540001&payFee=&otherFee=100&payfeerate=1&wechatFlag=false       自定义金额
		params.put("phoneType", "mobile");
		params.put("phoneno", phone);
		params.put("payFee", amount);
		params.put("otherFee", "");
		params.put("payfeerate", "0.995");
		params.put("wechatFlag", "false");
			
		String orderid=null;
		String payurl=null;
			try {
				String result=HttpClient4.postAddHeader(SAVA_ORDER, header, params);
				
				if(result!=null) {
					JSONObject object=JSONObject.parseObject(result);
					orderid=object.getString("orderid");
					payurl=object.getString("payurl");
					resultMap.put("orderId", orderid);
					resultMap.put("payUrl", payurl);
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
	   return resultMap;
		
	}
	
	//监控订单回调

	public static String noticeOrder(String orderNo) {
	
		String status="";
		String orderId="";
		String result=HttpClient4.get("https://uywap.10010.com/re/pay/getPaymentRecord?orderid="+orderNo);
		if(result!=null) {
			JSONObject object=JSONObject.parseObject(result);
			String respcode=object.getString("respcode");
			if(respcode.equals("0000")) {
				status="充值成功";
				orderId=object.getString("transactionid");
				System.out.println("流水号"+orderId);
			}else {
				status="充值失败";
			}
		}
		
		return status;
	}
	
	
	
	public static void main(String[] args) {
		//https://uywap.10010.com/re/pay/getPaymentRecord?orderid=uywap2020082111595257294
//		String phone="17670404030";
//		String amount="100";
//		Map map=getPayUrl(phone, amount);
//		
//		System.out.println(map);
//		
//		String orderNo="uywap2020082112095469707";
//		String status=noticeOrder(orderNo);
//		System.out.println(status);

		String baiIp =HttpClientHelper.sendGetProxy("http://api.hailiangip.com:8422/api/getIp?type=1&num=1&pid=1&unbindTime=60&cid=&orderId=O20102510054012706769&time=1603678627&sign=a537f75408ac02a71b149329c4bd5360&noDuplicate=0&dataType=0&lineSeparator=0&singleIp=0","",0);
		net.sf.json.JSONObject json = net.sf.json.JSONObject.fromObject(baiIp);
		String ip1="";
		Integer port1 = 0;
		if (json.getInt("code") == 0) {
			JSONArray orderList = json.getJSONArray("data");
			net.sf.json.JSONObject json1 = net.sf.json.JSONObject.fromObject(((net.sf.json.JSONObject) orderList.get(0)).toString());
			ip1 = json1.get("ip").toString();
			port1 = Integer.valueOf(json1.get("port").toString());

		}
		System.out.println("执行联通支付状态查询，ip：" + ip1);
		boolean isPass = true;
		while (isPass) {
			String result = HttpClientHelper.sendGetProxy("http://www.baidu.com", ip1, port1);
			System.out.println(result);
			if (result == null) {

			}
			break;
		}
		
	}

	
	

}
