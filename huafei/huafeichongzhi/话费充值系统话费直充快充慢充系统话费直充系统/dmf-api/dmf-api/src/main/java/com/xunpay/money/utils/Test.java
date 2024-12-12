package com.xunpay.money.utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.alibaba.fastjson.JSONArray;

import net.sf.json.JSONObject;

public class Test  implements Runnable {
	
		
		
	  public static void main(String[] args) throws Exception {
		  
	        ExecutorService executorService = Executors.newFixedThreadPool(50);
	 
	        executorService.execute(new Runnable() {
	        	
	            public void run() {
	            	
	                System.out.println("Asynchronous task");
	            }
	        });
	 
	        executorService.shutdown();
	    }

		
	
		
		//String str="h";
		
		//System.out.println(str.hashCode());
		
		//getIp();
		//String s="https://unipay.10010.com/udpNewPortal/payment/directPay?ServicePlatID=11&PayReqObj=KjMjI85mHW5axgIqXA6SEuUBtWowlbVxoSN%2BC7em3pA3xtjYeM43JAw8unq3ofauzDAxSWms920xlJm%2FNWKFdYrEfU5G1srS%2Bd6nyoMp2k%2FVwwxluO7owx8%2BOz2Ibyw%2FMuItOBzlPIumAmDMeuCzP0Fg43VBGtc9t55tvK0jNLAwmicMXUU6v5uRNmHKvNaC4hx8zs3c8t2CKVbXL%2B%2F6PMI8w69%2BMqNOMvKg7XqGYBLmI1bU%2FCIuMm7xj6xym5f9G5kejT6XMVtuxJv6MufpCoVL2TzhgzdNuVa1bU2cQaH4E9Oh5izT6smghfTfEn%2F2KUnDMCM5BgeCRViJtgf2RvKgGuRM1T%2BQUptEMLP6Qlrn4TWY6LWZaxgJKR2vb%2ByGBIDToqjKdaTTlvxg5LlcEclpKAjIWQVd7%2BgVGnzeqFTKDR4%2BgMZ4YeyH1TBUsnh2njdkdHUohX0xMYZnEDnA7RFBpJq4vmek8Jnyzxa7kxa%2BR0tVbKwh%2B2nZ5SHcPfovGoZnqf11h6%2BbuOXYJlGAgLkOpfxlW%2BNrw%2FW%2Ft1nYccfkTyAUAFwEtVN2wHVut1a9BDqbyCUIsxeWBi1aeTX9TMqbRq%2B1TBfjOFhaEgJzFW17cdCs3uNM7V%2B%2BDmeIlcF0GZzZXbeF3cwmxgojN0vyvHC3yjwxjdzqWDV%2F22kcWBxuDO32kBZvcBM1izaGWGjMEf%2BWVyK%2F6uj35s1ZwZ59G7F7DDsnvL3KE6EFCqWRXEQof7wbX1TiPC8%2FDYJWb0sbF84k2RllZc7Y6chG%2BTSiAf7VZImPJVBzTOl3dcXDk1rYxnQdGJG48KFugR6UG7qx%2F30vyB%2FbI57xUvUOQn64oFzffU6pWws2BLCrZGujx0VZH13ZzwR0kE9yHA8PFw42SssJIavDCJYg2%2F7ur3WdFs3m5NO2aniRSKw06Na8iFN%2BCsGjZaq86JXEF9UHO5wB1ooorSws%2BZN1vVhsojeCC884pWpc0GLjiubJo0Xue7lSg%2BfnHg%2F11LT05oCoXq2KswclGizP2mT1D7tZVA%2FBLNtl%2BSa5%2BHcX53iINy7Nvnm1w95n7jzGm4jcAKYK7sBNzgycn%2BGYuXTd9yI%2BMu2uutTHxUZkmKgB4QtAXitlVXGOSbYgIdZD6GUwwgxJ7H6HFrZSl1m8TNck98OQXcyUWV4e%2FXdlWLx4LKFLCeJnKxT1Rsg58wsq2fwL56St%2BnkwXKNp4O%2FojZyp0ZSbWGbzaPfloXwAbZa0WN3X0wBUbXeZ%2FxnPjYVYwtAKoqyDHkaFN5k4%2FoUPk0i%2Fi2AS9%2FyPXb8a6DYcOPTskFzOY1ZItxh8CDaYDDiOdsu%2FyFxlGriwe6jul8P4rVwjuYvrs5hp%2FtWNGwFLAbet3q%2BpK20vLdA%3D&pageType=0";
		
		//、System.out.println(s.length());
		//Test test=new Test();
		
		//new Thread(test).start();
	
		


	@Override
	public void run() {
		
		String ip="183.165.61.195";
		
		Integer port=3617;
		
		
		   Map restMap = new HashMap();//走的是令牌接口
	        //预下单
	        String reservationUrl = "https://upay.10010.com/npfwap/NpfMob/mobWapBankPay/broadWapPayFeeCheck.action";
	        
	        String phone="15674540003";

	        String s = HttpClientFactory.get("https://upay.10010.com/npfwap/npfMobWeb/getArea/init?callback=jQuery214011335515604044777_1587011645778&commonBean.phoneNo="+phone+"&_=1587011645791");
	        String area=s.split(":")[1].split("X")[0].substring(2);
	        String city =s.split(":")[1].split("X")[1];

	        Map paramMap = new HashMap();
	        paramMap.put("commonBean.phoneNo", phone);
	        paramMap.put("commonBean.areaCode", "");
	        paramMap.put("commonBean.provinceCode", area);
	        paramMap.put("commonBean.cityCode", city);
	        paramMap.put("bussineTypeInput", "0");
	        paramMap.put("commonBean.payAmount", "20");
	        paramMap.put("cardBean.cardValue", "");
	        paramMap.put("cardBean.cardValueCode", "");
	       // paramMap.put("commonBean.payAmount", "20");
	     //   paramMap.put("cardBean.cardValue", "2000");
	     //   paramMap.put("cardBean.cardValueCode", "01");
	        
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
	        paramMap.put("commonBean.bussineType", "06");
	        paramMap.put("commonBean.netAccount", "");
	        paramMap.put("pointBean.payMethod", "");
	        paramMap.put("pointBean.pointNumber", "");
	        paramMap.put("browserVersion", "");
	        paramMap.put("commonBean.activityType", "");
	        paramMap.put("commonBean.offerate", "1");
	        paramMap.put("commonBean.offerateId", "");
	        paramMap.put("commonBean.orgCode", "31");
	        paramMap.put("commonBean.channelCode", "weixinH5");
	        paramMap.put("emergencyContact", "");
	        paramMap.put("commonBean.ticketNo", "");
	        paramMap.put("commonBean.reserved2", "");
	        paramMap.put("ticketNew", "");
	        paramMap.put("commonBean.numberAttribution", "");
	        paramMap.put("commonBean.token", "");
	        paramMap.put("commonBean.urlSign", "");
	        paramMap.put("commonBean.msgTimeStamp", "");
	        paramMap.put("commonBean.serviceNo", "");
	        paramMap.put("commonBean.natCode", "");
	        paramMap.put("commonBean.saleChannel", "null");
	        paramMap.put("commonBean.deviceId", "null");
	        paramMap.put("commonBean.model", "null");
	        paramMap.put("commonBean.vipCode", "null");


	        String reservationResult = "";
	        Map map = new HashMap();
	        String result = null;
			//	{"out":"您的操作太频繁，请稍后再试！"}
				//   获取cokie
//		        Date date=new Date();
//		        String a=HttpCucc.getCokie("https://upay.10010.com/npfwap/NpfMob/needCode?channelType=307&_="+date.getTime(),ip,port);
//		        String  cokie=a.split(";")[0].substring(a.split(";")[0].indexOf(":")+1).trim();
//		        System.out.println("cokie-------->"+cokie);
//		          cokie="upay_user=a79ef7d606356617b09b688dcddd21da";
		        try {
		        	result = HttpCucc.sendPostTopUp(reservationUrl, paramMap, ip, port, "upay_user=f47cec58251bdd64d07755b0250dbbfb",null);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		        System.out.println(result);
		        
		        map = JSONObject.fromObject(result);
		        
		        reservationResult = map.get("out").toString();//预下单返回状态
		        
		        System.out.println(reservationResult);
		        
		        if (!"success".equals(reservationResult)) {
		        	
		        	System.out.println("客官预下单失败了哦!!!");
		        	
		        	
		        }
				
	        		
		
		
	}
	
	
	public static void getIp() {
		
	
		
		String ip="223.149.107.219";
	//	String DATATYPE="txt";
		String url="http://api.ip138.com/query/?ip="+ip;
		
		String token="66ce52c0d3b681ce28d3130c82a44aae";
		
		 String result = HttpClientFactory.getIp(url, token);
		 
		 JSONObject jso=JSONObject.fromObject(result);
		 
		 String proStr= jso.getString("data");
		 
		  System.out.println(proStr);
		  
		  
		
	}

    

	
//	
//	
//	MobileClient mobileClient =new MobileClient();
//	
//	System.out.println(mobileClient.sendCode("13652324964"));
//	
//	mobileClient.login("13652324961", "562737");
//	
//	
//	System.out.println(mobileClient.sendCode("13652324961"));
//	
//	String url=mobileClient.queryOrder("https:pay.shop.10086.cn/paygw/478287375178492891-1585560975652-07a6616a9802e115d6e8fb562c9c7a67-20.html");
//	
//	System.out.println(url);
//	
//	String phone="13838384380";
//	
//	String cookie="jsessionid-echd-cpt-cmcc-jt=D5BC7B1D84ABAF1BFDB18EE466EE6039";
//	
//	Double amount=10D;	
//	
//	String param[]=MobileClient.orderSave(phone, amount, cookie);
//	param=MobileClient.orderSave(phone, Double.valueOf(money), cookie);
//	//微信深度链接
//	System.out.println(MobileClient.wechat(param, cookie));
//   
//	//支付宝深度链接
//	System.out.println(alipay(param, cookie));
		

}
