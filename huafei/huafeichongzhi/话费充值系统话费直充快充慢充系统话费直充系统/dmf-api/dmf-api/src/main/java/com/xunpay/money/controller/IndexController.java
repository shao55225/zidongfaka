package com.xunpay.money.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;

import javax.servlet.http.HttpServletRequest;

import com.payment.util.Md5Utils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.jfinal.aop.Before;
import com.jfinal.ext.interceptor.POST;
import com.jfinal.plugin.activerecord.Db;
import com.xunpay.money.core.BaseController;
import com.xunpay.money.model.CompanyInfo;
import com.xunpay.money.model.CompanyOrder;
import com.xunpay.money.utils.EncryptUtils;
import com.xunpay.money.utils.HttpClientHelper;
import com.xunpay.money.utils.HttpsClientHelper;

public class IndexController extends BaseController {

	private static final Logger logger = Logger.getLogger(IndexController.class);
	
	//@Before(POST.class) //生产系统回调,接收中国移动和中国电信
	public void index() throws Exception {
		
		HttpServletRequest request=getRequest();
		
		BufferedReader buffer=null;
		
		StringBuilder sb=new StringBuilder();
		
		String str="";
		
		try {
			
			buffer=new BufferedReader(new InputStreamReader(request.getInputStream(),"UTF-8"));
			
			while((str=buffer.readLine())!=null) {
				
				sb.append(str);
			}
			
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		
		System.out.println(sb);
	}

	/**
	 * 回调支付方和话费api
	 * @param
	 * @throws Exception 
	 */
	
	public  void gateWay(StringBuilder sb) throws Exception {

	}
	

	
	




	
	
}
