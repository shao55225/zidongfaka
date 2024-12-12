package com.xunpay.money.utils;

import java.util.HashMap;
import java.util.Map;

import com.xunpay.money.core.BaseController;

import net.sf.json.JSONObject;

public class ShuaPiao extends BaseController {
	
	public static final String UA="Mozilla/5.0 (iPhone; CPU iPhone OS 12_4_4 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) Mobile/15E148 MicroMessenger/7.0.12(0x17000c31) NetType/WIFI Language/zh_CN";
									//
	public static final String Referer="http://aaa.votegoo.com/vote/frontend/joinner/party_id/10542/parent_id/4652/player_id/200921/t/1591840224?from=singlemessage&isappinstalled=0";
						
	//获取微信cookie
	  public  String getSessions(){
	    	
	        String wxsession = HttpClientHelperCtcc.sendGet("http://v.qddlive.cn/member/logintest/wxLogin");
	        System.out.println("获取到的session为"+wxsession);
	        redirect("http://v.qddlive.cn/member/logintest/wxLogin");
	        
	        return wxsession;
	  }

    //获取验证码
    public  String getyzm(String cookie){
    	
    	
        byte [] s = HttpClientHelperCtcc.sendGetVerCode("http://aaa.votegoo.com/captcha",cookie);
        
       // byte [] s = HttpClientHelperCtcc.sendGetVerCode("http://wapzt.189.cn/wap/getPicRandomCode.do?param=1584771683819&sessionid=2f31f7df70284c6386ddb2702d08f2e2&type=2");
        
        String outcode = HttpClientHelperCtcc.getCode(s);
        Map map=JSONObject.fromObject(outcode);
        if("false".equals(map.get("result"))){
           return getyzm(cookie);
        }
        Map map1=JSONObject.fromObject(map.get("data"));
        String yzm=map1.get("val").toString();
        System.out.println(map.get("data"));
        System.out.println(map1.get("val"));
        return yzm;
    }
    
    //校验验证码
    public static boolean  checkCode(String cookie,String yzm) {
    	
    	Map<String, String> map=new HashMap<String, String>();
    	map.put("code", yzm);
    	
    	String result=HttpClientHelperCtcc.sendPostCode("http://aaa.votegoo.com/vote/frontendApi/validateCaptcha",map,cookie);
    
    	System.out.println(result);
    	
    	if(result.indexOf("true")!=-1) {
    		
    		return true;
    	}
    
    	return false;
    }
    
    //投票
    public static String  vote(String cookie) {
	  
    	Map<String, String> map=new HashMap<String, String>();
    	
    	map.put("pext_id", "10542");
    	map.put("id", "200921");
    	
    	String result=HttpClientHelperCtcc.sendPostCode("http://aaa.votegoo.com/vote/frontendApi/vote",map,cookie);
    	if("".equals(result) ||  result==null) {
    		System.out.println("session不可用");
    		return null;
    	}	
    	JSONObject jsonobj=JSONObject.fromObject(result);
    	
    	String coke=jsonobj.getString("code");
    	
    	System.out.println(result);
    	if("1".equals(coke)) {
    		
    		System.out.println("投票成功");
    		
    	}else {
    		
    		System.out.println("投票失败");
    	}
    
    	return null;
    }
    
    
    //测试
    public static void main(String[] args) {
    	
    	//getSession();
		
//    	String cookie="PHPSESSID=v07f7iteaj27glsjdbdl8pg8i5";
//    	
//    	String yzm=getyzm(cookie);
//    	
//    	checkCode(cookie, yzm);
//    	
//    	vote(cookie);
//    	
	}
    
    


}
