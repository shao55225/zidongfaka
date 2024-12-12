package com.xunpay.money.utils;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.xunpay.money.model.CompanyIp;
import com.xunpay.money.utils.HttpClientHelper;

public class IpTest  {
	
	
	public static void main(String[] args) {

    	System.out.println("进入获取ip------------");
    	   
           String s = HttpClientHelper.sendGet("http://webapi.http.zhimacangku.com/getip?num=10&type=1&pro=&city=0&yys=0&port=1&pack=101382&ts=0&ys=0&cs=0&lb=1&sb=T&pb=4&mr=1&regions=");
           
           String []  ips=s.split("\r\n");
           
           for (int i = 0; i < ips.length; i++) {
        	   
        	   String ip=ips[i].split(":")[0];
        	   
               String port=ips[i].split(":")[1];


               System.out.println("ip--------"+ip+":"+port);
               
//               CompanyIp companyIp=new CompanyIp();
//
//              // companyIp.setId(i);
//
//               companyIp.setIp(ip);
//
//               companyIp.setPort(Integer.valueOf(port));
//
//               companyIp.save();
               
               //Db.update("update company_ip set ip=?,port=?",ip,port);
			
           }
	}
    
}
