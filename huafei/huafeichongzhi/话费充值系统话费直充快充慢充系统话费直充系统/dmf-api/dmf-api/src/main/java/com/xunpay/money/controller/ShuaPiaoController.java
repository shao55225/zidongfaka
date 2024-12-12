//package com.xunpay.money.controller;
//
//import java.math.BigDecimal;
//
//import com.xunpay.money.core.BaseController;
//import com.xunpay.money.utils.EncryptUtils;
//import com.xunpay.money.utils.HttpClientHelper;
//
//public class ShuaPiaoController extends BaseController {
//	
//
//	
// 
//    public static void main(String[] args) {
//    	
//    	
//	  BigDecimal rebate=new BigDecimal("0.975");
//		
//		//折扣金额
//		BigDecimal zkMoney=new BigDecimal("51");
//		
//		BigDecimal companyRebateMoney=rebate.multiply(zkMoney);
//		
//		System.out.println(companyRebateMoney.doubleValue());
//    	
//    	//并发测试
////    	for (int i = 1; i <101 ; i++) {
////    		
////    		test(000451+i);
////    		try {
////				Thread.sleep(100);
////			} catch (InterruptedException e) {
////				// TODO Auto-generated catch block
////				e.printStackTrace();
////			}
////		}
////    	
//	}
//
//	/**
//	 * 支付测试
//	 * **/
//	public static void test(Integer o){
//		String order_no="XX0727"+o;
//		String s_id="15859036582004330878";
//		String amount="1";
//		String notify_url="http://wwww.baidu.com";
//		String md5=amount+notify_url+order_no+s_id+"TOu9LhmzIaO2PFINumTjfCLsNIM7cadfSmng8GKVhGuquF7wKitm65BcdlLc3snf";
//		String sign= EncryptUtils.encrypt(md5).toUpperCase();
//	//	StringBuffer stringBuffer=new StringBuffer("http://154.89.8.123:8080/api/order/create?");
//		StringBuffer stringBuffer=new StringBuffer("http://localhost:9999/api/order/create?");
//		//StringBuffer stringBuffer=new StringBuffer("http://156.225.3.139:8080/api/order/create?");
//		stringBuffer.append("order_no="+order_no).
//				append("&s_id="+s_id).
//				append("&amount="+amount).
//				append("&notify_url="+notify_url).
//				append("&sign="+sign);
//		String url=stringBuffer.toString();
//		String s = HttpClientHelper.sendGet(url);
//		
//		System.out.println(s);
//	}
//	
//
//}
