package com.xunpay.money.utils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 移动48接口
 **/
public class CmccService {
	
	public static String sendFlag="https://login.10086.cn/loadSendflag.htm";
	
	public static String sendToken="https://login.10086.cn/loadToken.action";
	
	public static String sendSms="https://login.10086.cn/sendRandomCodeAction.action";
	
	/**
	 * 发送短信验证码
	 **/
	public static boolean sendSms() {
		
		
		
		String html=HttpClient4.get("https://login.10086.cn/html/login/touch.html?channelID=12014&backUrl=https%3A%2F%2Ftouch.10086.cn%2Fi%2Fmobile%2Fhome.html");
			
		System.out.println("界面html为"+html);
		//flag
		String flag=HttpClient4.getHeader(sendFlag);
		System.out.println(flag);
		Map<String,Object> params=new HashMap<String,Object>();
		Map<String,Object> param=new HashMap<String,Object>();
		params.put("userName", "13652324962");
		
		
		//token
		try {
			String token=HttpClient4.post(sendToken, params);
			
			Map<String,Object> paramsH=new HashMap<String,Object>();
			paramsH.put("Xa-before", token);
			paramsH.put("User-Agent", "Mozilla/5.0 (iPhone; CPU iPhone OS 11_0 like Mac OS X) AppleWebKit/604.1.38 (KHTML, like Gecko) Version/11.0 Mobile/15A372 Safari/604.1");
			paramsH.put("Referer", "https://login.10086.cn/html/login/touch.html?channelID=12014&backUrl=https%3A%2F%2Ftouch.10086.cn%2Fi%2Fmobile%2Frechargerecord.html");
			paramsH.put("Cookie", flag);

			param.put("userName", "13652324962");
			param.put("type", "01");
			param.put("channelID", "12014");
			
			String smsStatus=HttpClient4.postAddHeader(sendSms, paramsH,param);
			
			System.out.println(token);
			System.out.println(smsStatus);
		} catch (IOException e) {
			e.printStackTrace();
		}
	
		return false;	
		
	}
	
	
	/**
	 * 登录
	 **/
	public static String loginByCode(String code) {
		
		return null;
	}
	
	/***
	 *测试方法 
	 */
	public static void main(String[] args) {
		
		sendSms();
	}
	
	public static String testIP() {
		
		
		
		return null;
	}

	
}
