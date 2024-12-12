package com.xunpay.money.utils;

import java.net.URLEncoder;

import com.jfinal.log.Log4jLogger;
import com.jfinal.log.Logger;
import com.xunpay.money.model.CompanyOrder;

public class MobileClient {
	
	//测试用
	public static void main(String[] args) {
		
		MobileClient mobileClient = new MobileClient();
		
	//	Cmcc cmcc= new Cmcc();
		String phone="13652324961";
		Double amount=100D;
		
		String cookie="jsessionid-echd-cpt-cmcc-jt=nE5CE6D505921C097D336351FAFA3D04C-1";//接口
		
		//String cookie="jsessionid-echd-cpt-cmcc-jt=n34758C1833910D6B105319ABE0289E5B-1";//浏览器
		
		//发验证码
		///String  code=mobileClient.sendCode("13652324961");
		//System.out.println(code);
		
		//登录
		//String art=cmcc.login("13652324961", "817529");
		
		//System.out.println(art);
		
		//下单
		String [] urlarry=orderSave(phone, amount, cookie);
		
		//微信支付
		String payUrl=wechat(urlarry, cookie);
		
		System.out.println(payUrl);
		
		//查询
		//String url=queryOrder("https://pay.shop.10086.cn/paygw/478089529178149741-1585363130007-c38574f45878f90d61e8486e5a820f88-20.html");
		//String url=queryOrder("https://touch.10086.cn/i/mobile/stc-recharge-result.html?orderId=475000330178409151&requestId=20200221163211314162247757892833&hmac=e4fb46ce4d80bf9652fce35d093b919b&reserved2=credit&reserved1=0003xWAP&status=0&ts=20200221163321");
								
		//System.out.println(url);
		
		//String url=mobileClient.queryOrder("https://pay.shop.10086.cn/paygw/480771548178447331-1588045149289-a97c1154ee373fe2956f08768ec15ffa-20.html");
		//System.out.println(url);
		
	//	System.out.println(getBefore(userName));
		//46361797167341480189927533934903
	}
	
	
	
	private Logger log=Log4jLogger.getLogger(MobileClient.class);
	
	
	/**
	 * 发送验证码
	 * 
	 * **/
	
	public String sendCode(String phone) {
		
	  String isSuccess=Cmcc.sendCode(phone);
		
	  return isSuccess;
	}
	
	/**
	 * 登录
	 * 返回登录的cookie，保存到数据库中
	 * 
	 * **/
	public  String login(String phone,String code) {
		
		code=URLEncoder.encode(code);
		
		String cookie=null;
		
		String artifactAtrr=null;
		
		String artifactLocation=null;
		
		String jsessionid=null;//这个才是真正的cookie

		artifactAtrr=Cmcc.login(phone, code);
		
		String [] artifact_cookie=artifactAtrr.split("--");
		
			cookie=artifact_cookie[1];
			
			if(artifact_cookie!=null) {
				
				artifactLocation=Cmcc.ssoCheck(cookie,"");
				
				System.out.println("ssoLocation为"+artifactLocation);
				//重定向
				
				jsessionid=Cmcc.redirectLocation(artifactLocation, cookie);
				
				System.out.println("登录首页情况"+jsessionid);
				
			}else {
				
				return "登录失败";
			}
			
		log.info("用户登录信息======>"+jsessionid);
		
		return jsessionid;
		
	}
	
	/**
	 * 下单
	 * 手机号，金额，cookie
	 * 
	 * **/
	
	public static  String [] orderSave(String phone,Double amount,String cookie) {
		
		//获取下单规则
		String result=Cmcc.getrule(phone);
		
		boolean isHave=false;
		
		String temp=null;
		
		String wtmAtr[]=null;
		
		String payUrl=null;
		
		if(result.indexOf("-")!=-1) {
			
			wtmAtr=result.split("-");
			
			temp=wtmAtr[0];
			
			isHave=true;
			
			payUrl=Cmcc.saveOrder(phone, amount, cookie,Double.valueOf(temp),isHave);
			
		}else {
			
			 payUrl=Cmcc.saveOrder(phone, amount, cookie,Double.valueOf(result),isHave);
			
		}
		//下单失败的情况，一般是登录超时
		if(payUrl==null) {
			
			return null;
			
		}
	
		//格式化后的支付链接
		String url=Cmcc.formatOrderId(payUrl);
		
		String urlarry[]=url.split("-");
		
		return urlarry;
	}
	
	
	/**
	 * 微信支付
	 * **/
	public static  String wechat(String urlarry[],String cookie) {
		
		String preHtml=Cmcc.wechatPayPre(urlarry[0], urlarry[1], cookie);
		
		//获取签名和参数数组
		String sign_param[]=Cmcc.getSignParam(preHtml);
		
		//提交微信支付二次请求
		String html=Cmcc.getCmccPayUrl(sign_param,cookie);
		
		//格式化html
		String webUrl=Cmcc.mwebUrl(html);
		
		//得到深度链接HTml
		String deepLinkHtml=Cmcc.wechatPay(webUrl);
		
		//解析到深度链接
		String link=Cmcc.parseWechatDeepLink(deepLinkHtml);
		
		return link;
	}
	
	/**
	 * 支付宝支付
	 * **/
	public  static String alipay(String urlarry[],String cookie) {
		
		String preHtmlAlipay=Cmcc.alipayPre(urlarry[0], urlarry[1], cookie);
		
		//获取签名和参数数组
		String sign_param_Alipay[]=Cmcc.getSignParam(preHtmlAlipay);
		
		String locationHtml=Cmcc.alipay(sign_param_Alipay, cookie);
		
		String alipayLink=Cmcc.parseAlipayDeepLink(locationHtml);
		
		return alipayLink;
	}
	
	/**
	 * 回调
	 * 
	 * **/
	public static String queryOrder(String orderUrl) {
		
		return 	Cmcc.queryOrder(orderUrl);
	}
		


}
