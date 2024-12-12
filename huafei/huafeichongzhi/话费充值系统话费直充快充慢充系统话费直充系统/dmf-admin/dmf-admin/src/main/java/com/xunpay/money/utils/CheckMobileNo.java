package com.xunpay.money.utils;

import java.util.HashMap;
import java.util.Map;

public class CheckMobileNo {
	
	private static final String url = "https://kyfw.12306.cn/otn/regist/getRandCode";  
	
	@SuppressWarnings("static-access")
	public static boolean check(String telphone) {
    	try{
    		HttpsClientHelper http = new HttpsClientHelper();
    		Map<String,String> createMap = new HashMap<String,String>();  
            createMap.put("loginUserDTO.user_name","bbb378cd222");  
            createMap.put("userDTO.password","1qaz2wsx");  
            createMap.put("confirmPassWord", "1qaz2wsx");  
            createMap.put("loginUserDTO.name","%E5%94%90%E6%81%95");
            createMap.put("loginUserDTO.id_type_code","1"); 
            createMap.put("loginUserDTO.id_no","430202198907051018"); 
            createMap.put("userDTO.country_code","CN"); 
            createMap.put("userDTO.born_date","1991-03-17"); 
            createMap.put("userDTO.email",""); 
            createMap.put("userDTO.mobile_no", telphone); 
            createMap.put("passenger_type","1"); 
            createMap.put("studentInfoDTO.province_code","11"); 
            createMap.put("studentInfoDTO.school_code",""); 
            createMap.put("studentInfoDTO.school_name","%E7%AE%80%E7%A0%81%2F%E6%B1%89%E5%AD%97"); 
            createMap.put("studentInfoDTO.department",""); 
            createMap.put("studentInfoDTO.school_class",""); 
            createMap.put("studentInfoDTO.student_no",""); 
            createMap.put("studentInfoDTO.school_system","1"); 
            createMap.put("studentInfoDTO.enter_year","2017"); 
            createMap.put("studentInfoDTO.preference_card_no",""); 
            createMap.put("studentInfoDTO.preference_from_station_name","%E7%AE%80%E7%A0%81%2F%E6%B1%89%E5%AD%97"); 
            createMap.put("studentInfoDTO.prefe rence_from_station_code",""); 
            createMap.put("studentInfoDTO.preference_to_station_name","%E7%AE%80%E7%A0%81%2F%E6%B1%89%E5%AD%97"); 
            createMap.put("studentInfoDTO.preference_to_station_code",""); 
            String httpOrgCreateTestRtn = http.sendPost(url, createMap);  
            return httpOrgCreateTestRtn.indexOf("您输入的手机号码已被其他注册") == -1;
    	}catch(Exception e){}
        
        return true;
	}
	
}
