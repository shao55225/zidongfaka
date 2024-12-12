package com.xunpay.money.controller;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.xunpay.money.core.BaseController;
import com.xunpay.money.utils.EncryptUtils;
import com.xunpay.money.utils.HttpClientHelper;
import com.xunpay.money.utils.HttpCucc;

public class SystemController extends BaseController {

	
	/**
	 * 
	 * 查订单
	 */
	public static void queryStatus(String orderId){
		String sid="15859027006319913957";
		String skey="2Kyk7wuYB1HIzGXdk17VAwjCbtL0mJbDggqUTBW3U4tdhqHI7CSfayT1ebT3S6Z5";
		String signStr=orderId+sid+skey;
		String sign= EncryptUtils.encrypt(signStr).toUpperCase();
		//StringBuffer stringBuffer=new StringBuffer("http://localhost:9999/api/order/queryStatus?");
		StringBuffer stringBuffer=new StringBuffer("http://154.89.8.123:8080/api/order/queryStatus?");
		    
		stringBuffer.append("orderId="+orderId).
				append("&sid="+sid).
				append("&sign="+sign);
		String url=stringBuffer.toString();
		String s = HttpClientHelper.sendGet(url);
		
		System.out.println(s);
	}
	
	/**
	 * 查余额
	 */
	public static void queryBanlanc(){
		String sid="15859027006319913957";
		String skey="2Kyk7wuYB1HIzGXdk17VAwjCbtL0mJbDggqUTBW3U4tdhqHI7CSfayT1ebT3S6Z5";
		String signStr=sid+skey;
		String sign= EncryptUtils.encrypt(signStr).toUpperCase();
		//StringBuffer stringBuffer=new StringBuffer("http://154.89.8.123:8080/api/order/invest?");
		StringBuffer stringBuffer=new StringBuffer("http://localhost:9999/tel-api/order/queryBanlance?");
		    
		stringBuffer.append("&sid="+sid).
				append("&sign="+sign);
		String url=stringBuffer.toString();
		String s = HttpClientHelper.sendGet(url);
		
		System.out.println(s);
	}

   public static void main(String [] art) throws InterruptedException {
//		String payUrl="https://unipay.10010.com/udpNewPortal/payment/directPay?ServicePlatID=11&PayReqObj=KjMjI85mHW6nfDIcYgG8PIPybll0QMzh5q3D2dKDrb4%2FoPKagmxzpdVKb%2FCqRrH%2FHtyPacnMLqkCYn%2BY6LMLV6w0jeU1W0BsSYsLSGhWo7x4sGKZxebhfS0XKISEEirwqNLlpOLnBYxweDWcdk4issPJhgvnVKqBg3w8eHItdNcE8dMTD5Ug2Bj%2B13fjVpppKoYy19QJ9GP5bmyZjd67uer4Umn6ooXxXLRvgYn%2BV2zs6DJ1a%2BcxYdVllkPQakHE1Jy%2B7vE28oBftLPaU63kRhmVbgbbErGsFthdryIQ778L%2FtCIVcWXbJn6KAsvDNgYzFMaq63fr9WTxaq%2FruJo8jM7jZ1cPUFcE1hZ4BeCHdL6iFksZlF01ulb5ANB9ieLArPzs0Trg13oAk3i7t6ivfplouFRiY%2BpuSMMI2yRNfpNunG9YhQzn6uAq9FEVTrUuXay79HdUEbXOsppGR4NxU7AVustGxxXsLKFu%2B9HMw%2FoUlCg%2BepSrw0TsgL7MmhdwD%2FqSrTQtS%2BbHaeozOeOSuhZLOySjiQrNKzxn1sn9CWhLd%2FF2cgpRVLIRgBP4EIFsmGbuTT6ReWk2L%2BZvD5Hmq9zPA5QArz0KzVLNZNWzrKweQORyr%2B0dZl00iGC6I9m8K4WdTRbGoX%2B6eskzCgobER1N3kpNGoSNFSHnbGT%2BejFFwW9RWqqEWanov%2FfrZGQAUsbs9ZbtV%2Fc%2BXs7eGeA5CENx9YlJMzBe1u0UWXmc9Nm3QfyNPe6GBxLIj910HPavKHyQVpsXE%2FyBEc3x0L88eNd5fj1r5wNpWQz5PUG3gBm71WrMDcyoZPdxbgzLfxPQ4O4qQkqfzPl2ZRC94iLDg9Wlrxd2skXvrbSJD%2FJIMTGEc48ZRQ7ETPys4HzECnFrZcTJZfpOf9BFZxkUJF7y1LcAVq%2FpFfe0e36bYM8Xs1Tam04bSKrWgunpniesGvUOFYrK7RjRPVQfr%2B9quf8j12fa46NbT0m8%2FigfDynJDh14QzKBIA6wZhm3Jf4C28mXZHufeOEC%2FuE3I9M6OZUoIEtMZI2sFYgwwFd6UkTQ7fj8AXcE2oLehQ8vi4nK1oBnflh6D4JQXtG9cgyg35RAvXdeOCo0Uu6j8HmJcix6tQxblkwtSmCaZyVRyRMiCUBe5xvaXDVOX3yz0F8KPf2y9OAQupO1k1jFzWGm2I6Xc%2FhFP0DUulswE31GjJSQNvYrFR3V2J93em1tia2gM%2B3Hy5VZEphV6Z9%2BAHA5G5GFdgH84xSreOR7hvt0ihBjszQSxS4ki%2FneWTXy53oZBf8cJlbbREBju6uKrUvhWtjgGRTwR9B2j0qB%2FdS7sgHWq10m%2FCsVshpG%2F0JWD0%2BAqDQXCYHOQIe1duFbuMDwwaLjjw7SRyAk%2FQHSczARO0Bkca7&pageType=07";
//		Map<String, String> map =toPayAndpayStr(payUrl, null, 0);
//		 
//		 System.out.println(map);

	
		//并发测试
//		for (int i= 60 ; i < 70; i++) {
//		
//		  top(110+i,"138383843"+i);	
//			Thread.sleep(3000);
//			
//		}
		//getCK
		top(89,"13393777089");
	   //test(494820);
		
		//queryStatus("s10777");
	    //queryBanlanc();
//		double money=20;
//		double DisCount=0.9;
//		BigDecimal   val1=new BigDecimal(money);
//		BigDecimal   val2=new BigDecimal(DisCount);
//		String dis_money = String.valueOf(val1.multiply(val2));
//		System.out.println(dis_money);

	}



	/**
	 * 支付测试
	 * **/
	public static void test(Integer o){
		String order_no="XX0727"+o;
		String s_id="15859036582004330878";
		String amount="10";
		String notify_url="http://wwww.baidu.com";
		String md5=amount+notify_url+order_no+s_id+"TOu9LhmzIaO2PFINumTjfCLsNIM7cadfSmng8GKVhGuquF7wKitm65BcdlLc3snf";
		String sign= EncryptUtils.encrypt(md5).toUpperCase();
	//	StringBuffer stringBuffer=new StringBuffer("http://154.89.8.123:8080/api/order/create?");
	//	StringBuffer stringBuffer=new StringBuffer("http://154.89.10.111:8080/api/order/create?");
		StringBuffer stringBuffer=new StringBuffer("http://localhost:9999/api/order/create?"); 
	//	StringBuffer stringBuffer=new StringBuffer("http://156.225.3.139:8080/api/order/create?");
			//StringBuffer stringBuffer=new StringBuffer("http://156.225.2.37:8080/api/order/create?");
		//	StringBuffer stringBuffer=new StringBuffer("http://154.89.8.27:8080/api/order/create?");
		//	StringBuffer stringBuffer=new StringBuffer("http://103.140.151.234:8080/api/order/create?");
		stringBuffer.append("order_no="+order_no).
				append("&s_id="+s_id).
				append("&amount="+amount).
				append("&notify_url="+notify_url).
				append("&sign="+sign);
		String url=stringBuffer.toString();
		String s = HttpClientHelper.sendGet(url);
		
		System.out.println(s);
	}
	

	/**
	 * 充值测试
	 * **/
	public static void top(Integer orderid,String phones){
			
		String phone=phones;
		String order_no="s"+orderid;
		String s_id="15859027006319913957";
		String amount="10";
		String notify_url="http://fast.mynatapp.cc/api/order/create";
		String md5=amount+notify_url+order_no+phone+s_id+"2Kyk7wuYB1HIzGXdk17VAwjCbtL0mJbDggqUTBW3U4tdhqHI7CSfayT1ebT3S6Z5";
		String sign= EncryptUtils.encrypt(md5).toUpperCase();

		   StringBuffer stringBuffer=new StringBuffer("http://127.0.0.1:8080/api/order/invest?");
	
		stringBuffer.append("phone="+phone).
				append("&order_no="+order_no).
				append("&s_id="+s_id).
				append("&amount="+amount).
				append("&notify_url="+notify_url).
				append("&sign="+sign);
		String url=stringBuffer.toString();
		String s = HttpClientHelper.sendPost(url,null);
		System.out.println(s);
	}
	
	
	
	

    
//    
//    	
//    	public static void main(String[] args) {
//            try {
//            	
//               // BufferedWriter out = new BufferedWriter(new OutputStreamWriter (new FileOutputStream ("d:\\test.html",true),"UTF-8"));
//                BufferedWriter out = new BufferedWriter(new FileWriter("d:\\test1.html"));
//                
//                out.write("\r\n" + 
//                		"<!DOCTYPE html PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\" \"http://www.w3.org/TR/html4/loose.dtd\">\r\n" + 
//                		"<html>\r\n" + 
//                		"<head>\r\n" + 
//                		"<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\r\n" + 
//                		"</head>\r\n" + 
//                		"<body>\r\n" + 
//                		"	    <form name=\"postSubmit\" method=\"post\" action=\"https://pay.it.10086.cn/payprod-format/h5/dup_submit\" >\r\n" + 
//                		"	    \r\n" + 
//                		"	    <input type=\"hidden\" name=\"sign\" value=\"{&quot;CerID&quot;:&quot;10f0683&quot;,&quot;SignValue&quot;:&quot;gGy2/mlBTwMVUNHo/GnE7ktaX/L18DB1466kzlEdq7k0iO7p0Tqb5B7Gb4cyPAJYFOO9U4qfxgdA/LQ5K21lBK0n8He/z9wCZf8gKQVXC6UirD+G94YJxtjCtRS8z8XcMHg95kx2yXCxdOhQy3pTzJdzPVVEXqlBfhFPFnFbuKQ=&quot;}\" />\r\n" + 
//                		"	    \r\n" + 
//                		"	    <input type=\"hidden\" name=\"request_params\" value=\"{&quot;MerActivityID&quot;:&quot;&quot;,&quot;ProductName&quot;:&quot;为138****4382话费充值&quot;,&quot;MchId&quot;:&quot;N00550001&quot;,&quot;CustomParam&quot;:&quot;489769197066364027|20|0003xWAP&quot;,&quot;OrderNo&quot;:&quot;159704279793903897436161200071&quot;,&quot;ReturnURL&quot;:&quot;https://pay.shop.10086.cn/iphone_paygw/unipayCallback2&quot;,&quot;ReqChannel&quot;:&quot;2510&quot;,&quot;IDValue&quot;:&quot;&quot;,&quot;IDType&quot;:&quot;&quot;,&quot;OrderDate&quot;:&quot;20200810&quot;,&quot;BusiType&quot;:&quot;1012&quot;,&quot;Gift&quot;:&quot;20&quot;,&quot;Payment&quot;:&quot;9980&quot;,&quot;TimeoutExpress&quot;:&quot;30m&quot;,&quot;ExtraParams&quot;:{&quot;ClientIP&quot;:&quot;223.149.180.45&quot;},&quot;Version&quot;:&quot;1.0&quot;,&quot;NotifyURL&quot;:&quot;https://pay.shop.10086.cn/iphone_paygw/unipayNotify2&quot;,&quot;PaymentType&quot;:&quot;WEIXIN-WAP&quot;,&quot;ProductDesc&quot;:&quot;话费充值&quot;,&quot;ReqDateTime&quot;:&quot;20200810150438&quot;,&quot;ReqSys&quot;:&quot;0055&quot;}\" />\r\n" + 
//                		"	     \r\n" + 
//                		"		\r\n" + 
//                		"	    </form>   \r\n" + 
//                		"	   <script>  \r\n" + 
//                		"	      document.postSubmit.submit();   \r\n" + 
//                		"	    </script>\r\n" + 
//                		"</body>\r\n" + 
//                		"</html>");
//                out.close();
//                System.out.println("文件创建成功！");
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//
//


	
   
	
}
