package com.xunpay.money.utils;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.protocol.Protocol;
import org.apache.commons.httpclient.protocol.ProtocolSocketFactory;

public class HttpsClientHelper {

	public static String sendGet(String url) {
		ProtocolSocketFactory fcty = new MySecureProtocolSocketFactory();  
		Protocol.registerProtocol("https", new Protocol("https", fcty, 443));
		GetMethod getMethod = new GetMethod(url);
		String charset = "utf-8";
		getMethod.getParams().setHttpElementCharset(charset);
		getMethod.getParams().setContentCharset(charset);
		getMethod.getParams().setCredentialCharset(charset);
		String result = null;
		try {
			HttpClient httpClient = new HttpClient();
			int status = httpClient.executeMethod(getMethod);
			if (status != 200) {
				return null;
			}
			result = getMethod.getResponseBodyAsString();
		} catch (HttpException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			// 释放连接
			getMethod.releaseConnection();
		}
		return result;
	}
	
	public static String sendPost(String url, Map<String, String> params) {
		ProtocolSocketFactory fcty = new MySecureProtocolSocketFactory();  
		Protocol.registerProtocol("https", new Protocol("https", fcty, 443));
		PostMethod postMethod = new PostMethod(url);
		String charset = "utf-8";
		postMethod.getParams().setHttpElementCharset(charset);
		postMethod.getParams().setContentCharset(charset);
		postMethod.getParams().setCredentialCharset(charset);
		if (MapUtils.isNotEmpty(params)) {
			Set<String> keys = params.keySet();
			for (String key : keys) {
				postMethod.addParameter(key, params.get(key));
			}
		}
		String result = null;
		try {
			HttpClient httpClient = new HttpClient();
			int status = httpClient.executeMethod(postMethod);
			if (status != 200) {
				return null;
			}
			result = postMethod.getResponseBodyAsString();
		} catch (HttpException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			// 释放连接
			postMethod.releaseConnection();
		}
		return result;
	}
	
}
