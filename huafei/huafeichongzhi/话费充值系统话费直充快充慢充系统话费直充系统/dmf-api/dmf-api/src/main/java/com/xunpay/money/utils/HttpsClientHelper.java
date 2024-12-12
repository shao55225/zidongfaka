package com.xunpay.money.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.httpclient.Cookie;
import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HostConfiguration;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpVersion;
import org.apache.commons.httpclient.cookie.CookiePolicy;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpClientParams;
import org.apache.commons.httpclient.protocol.Protocol;
import org.apache.commons.httpclient.protocol.ProtocolSocketFactory;
import org.apache.http.params.CoreProtocolPNames;

import net.sf.json.JSONObject;

public class HttpsClientHelper {

	/**
	 * 登录
	 * **/
	public static String []  sendGetLogin(String url) {
		ProtocolSocketFactory fcty = new MySecureProtocolSocketFactory();  
		Protocol.registerProtocol("https", new Protocol("https", fcty, 443));
		GetMethod getMethod = new GetMethod(url);
		String charset = "utf-8";
		getMethod.getParams().setHttpElementCharset(charset);
		getMethod.getParams().setContentCharset(charset);
		getMethod.getParams().setCredentialCharset(charset);
		
		getMethod.addRequestHeader("Accept","application/json, text/javascript, */*; q=0.01");
		getMethod.addRequestHeader("X-Requested-With","XMLHttpRequest");
		getMethod.addRequestHeader("Sec-Fetch-Site","same-origin");
		getMethod.addRequestHeader("Sec-Fetch-Site","same-origin");
		getMethod.addRequestHeader("Sec-Fetch-Mode","cors");
		getMethod.addRequestHeader("Sec-Fetch-Mode","https://login.10086.cn");
		getMethod.addRequestHeader("User-Agent", "Mozilla/5.0 (Linux; Android 8.0; Pixel 2 Build/OPD3.170816.012) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/78.0.3904.108 Mobile Safari/537.36");
		getMethod.addRequestHeader("Referer","https://login.10086.cn/html/login/touch.html?channelID=12003&backUrl=https://shop.10086.cn/i/?f=rechargecredit");
		
		String result [] = new String[2];
		try {
			HttpClient httpClient = new HttpClient();
			int status = httpClient.executeMethod(getMethod);
			if (status != 200) {
				return null;
			}
			  
			  Header header= getMethod.getResponseHeader("Set-Cookie");
			  
			  Header[] headers = getMethod.getResponseHeaders("Set-Cookie");
			  
			  StringBuffer sb=new StringBuffer();
			  
			  for(Header h:headers) {
				  
				 String temp= h.toString().replace("Set-Cookie:", " ").trim();
				 
				 String [] tempAttr= temp.split(";");
				 
				 sb.append(tempAttr[0]).append(";");
				  
				  System.out.println("原生登录返回cookie为"+h);
			  }
			  
			  System.out.println("处理后的cookie为"+sb.toString());
			  
			  result[0]=getMethod.getResponseBodyAsString();
			  
			 // String cookie=header.toString().replace("set-cookie:", " ").trim();
			  
			  result[1]=sb.toString();
			
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
	
	/**
	 * post登录
	 * **/
	
	public static String []  sendPostLogin(String url,Map<String, String> paramter) {
		
		ProtocolSocketFactory fcty = new MySecureProtocolSocketFactory();  
		Protocol.registerProtocol("https", new Protocol("https", fcty, 443));
		PostMethod postMethod = new PostMethod(url);
		String charset = "utf-8";
		postMethod.getParams().setHttpElementCharset(charset);
		postMethod.getParams().setContentCharset(charset);
		postMethod.getParams().setCredentialCharset(charset);
		
		postMethod.addRequestHeader("Accept","application/json, text/javascript, */*; q=0.01");
		postMethod.addRequestHeader("X-Requested-With","XMLHttpRequest");
		postMethod.addRequestHeader("Sec-Fetch-Site","same-origin");
		postMethod.addRequestHeader("Sec-Fetch-Site","same-origin");
		postMethod.addRequestHeader("Sec-Fetch-Mode","cors");
		postMethod.addRequestHeader("Sec-Fetch-Mode","https://login.10086.cn");
		postMethod.addRequestHeader("User-Agent", "Mozilla/5.0 (Linux; Android 8.0; Pixel 2 Build/OPD3.170816.012) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/78.0.3904.108 Mobile Safari/537.36");
		postMethod.addRequestHeader("Referer","https://login.10086.cn/html/login/touch.html?channelID=12003&backUrl=https://shop.10086.cn/i/?f=rechargecredit");
		
		
		if (MapUtils.isNotEmpty(paramter)) {
			Set<String> keys = paramter.keySet();
			for (String key : keys) {
				postMethod.addParameter(key, paramter.get(key));
			}
		}
		
		String result [] = new String[2];
		try {
			HttpClient httpClient = new HttpClient();
			int status = httpClient.executeMethod(postMethod);
			if (status != 200) {
				return null;
			}
			
			Header header= postMethod.getResponseHeader("Set-Cookie");
			
			Header[] headers = postMethod.getResponseHeaders("Set-Cookie");
			
			StringBuffer sb=new StringBuffer();
			
			for(Header h:headers) {
				
				String temp= h.toString().replace("Set-Cookie:", " ").trim();
				
				String [] tempAttr= temp.split(";");
				
				sb.append(tempAttr[0]).append(";");
				
				System.out.println("原生登录返回cookie为"+h);
			}
			
			System.out.println("处理后的cookie为"+sb.toString());
			
			result[0]=postMethod.getResponseBodyAsString();
			
			// String cookie=header.toString().replace("set-cookie:", " ").trim();
			
			result[1]=sb.toString();
			
		} catch (HttpException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			// 释放连接
			postMethod.releaseConnection();
		}
		// 错误 [{"assertAcceptURL":"https://shop.10086.cn/i/v1/auth/getArtifact","code":"6002","desc":"短信随机码不正确或已过期，请重新获取","islocal":false,"result":"8"}, nocode13652324961=null;c=null;CITY_INFO=100|10;]
		//正确  {"artifact":"caf6aa1e10bf4054a62c0246bfcef3b0","assertAcceptURL":"https://shop.10086.cn/i/v1/auth/getArtifact","code":"0000","desc":"认证成功","islocal":false,"result":"0","type":"00","uid":"a3b1e2558c6349b5ad70ab6550666481"}
		return result;
	}
	
	
	/**
	 * sso获取
	 * **/
	public static String sendGetSso(String url,String cookie) {
		ProtocolSocketFactory fcty = new MySecureProtocolSocketFactory();  
		Protocol.registerProtocol("https", new Protocol("https", fcty, 443));
		GetMethod getMethod = new GetMethod(url);
		String charset = "utf-8";
		getMethod.getParams().setHttpElementCharset(charset);
		getMethod.getParams().setContentCharset(charset);
		getMethod.getParams().setCredentialCharset(charset);
		
		getMethod.addRequestHeader("Cookie",cookie);
		
		String result = null;
		
		try {
			
			HttpClient httpClient = new HttpClient();
			
			httpClient.getParams().setCookiePolicy(CookiePolicy.BROWSER_COMPATIBILITY);
			  
			int status = httpClient.executeMethod(getMethod);
			
		    Cookie[] cookies = httpClient.getState().getCookies();
		    
		    if(cookies.length!=0) {
		    	
		        result=cookies[0].toString();
		    }
		    
		
			
			//result=header.toString();
			
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
	
	/**
	 * ssoCheck
	 * 
	 * **/
	public static String sendGetSsoCheck(String url,String cookie) {
		ProtocolSocketFactory fcty = new MySecureProtocolSocketFactory();  
		
		Protocol.registerProtocol("https", new Protocol("https", fcty, 443));
		
		HostConfiguration hostconfig = new HostConfiguration(); 
		//hostconfig.setHost(url); 
		hostconfig.getParams().setParameter(HttpClientParams.PROTOCOL_VERSION, HttpVersion.HTTP_1_0); 	

		GetMethod getMethod = new GetMethod(url);
		String charset = "utf-8";
		getMethod.getParams().setHttpElementCharset(charset);
		getMethod.getParams().setContentCharset(charset);
		getMethod.getParams().setCredentialCharset(charset);
		
		getMethod.addRequestHeader("Cookie",cookie);
		
		getMethod.setFollowRedirects(false);
		
		getMethod.addRequestHeader("Accept","application/json, text/javascript, */*; q=0.01");

		getMethod.addRequestHeader("User-Agent", "Mozilla/5.0 (Linux; Android 8.0; Pixel 2 Build/OPD3.170816.012) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/78.0.3904.108 Mobile Safari/537.36");
		
		//getMethod.addRequestHeader("Referer",backUrl);
		
		
		String result = null;
		
		try {
			
			HttpClient httpClient = new HttpClient();
			
			int status = httpClient.executeMethod(hostconfig,getMethod);
			
			Header[] location=getMethod.getResponseHeaders();
			
			getMethod.getResponseHeader("Set-Cookie");
			
			//Location: https://touch.10086.cn/i/v1/auth/getArtifact2?artifact=-1&backUrl=https%3A%2F%2Ftouch.10086.cn%2Fi%2Fmobile%2Fhome.html%3Fwelcome%3D1584778857573
			
			//Header location=getMethod.getResponseHeader("Location");
		//
			result=location[2].toString().replace("Location:", " ").trim();
			
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
	
	/**
	 * location重定向登录验证
	 * **/
	
	public static String [] sendGetLocation(String url,String cookie) {
		ProtocolSocketFactory fcty = new MySecureProtocolSocketFactory();  
		Protocol.registerProtocol("https", new Protocol("https", fcty, 443));
		GetMethod getMethod = new GetMethod(url);
		String charset = "utf-8";
		getMethod.getParams().setHttpElementCharset(charset);
		getMethod.getParams().setContentCharset(charset);
		getMethod.getParams().setCredentialCharset(charset);
		
		getMethod.addRequestHeader("Cookie",cookie);
		
		getMethod.setFollowRedirects(false);
		
		//StringBuffer tmpcookies = new StringBuffer();
		
		String [] result = new String [2];
		
		try {
			
			HttpClient httpClient = new HttpClient();
			
			httpClient.getParams().setCookiePolicy(CookiePolicy.BROWSER_COMPATIBILITY);
			
			int status = httpClient.executeMethod(getMethod);
			
			Header location=getMethod.getResponseHeader("Location");
			
			Header returnCookie=getMethod.getResponseHeader("Set-Cookie");
			//Location: https://touch.10086.cn/i/mobile/home.html?welcome=1584779355118

			result[0]=location.toString().replace("Location:", " ").trim();
			
			String str=returnCookie.toString().split(";")[0];
			
			String newStr=str.replace("set-cookie:", " ").trim();
			
			newStr=newStr+";";
			
			result[1]=newStr;
			
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
	
	/**
	 * welcome
	 * **/
	
	public static String sendGetWelcom(String url,String cookie) {
		ProtocolSocketFactory fcty = new MySecureProtocolSocketFactory();  
		Protocol.registerProtocol("https", new Protocol("https", fcty, 443));
		GetMethod getMethod = new GetMethod(url);
		String charset = "utf-8";
		getMethod.getParams().setHttpElementCharset(charset);
		getMethod.getParams().setContentCharset(charset);
		getMethod.getParams().setCredentialCharset(charset);
		getMethod.addRequestHeader("Cookie",cookie);
		
		getMethod.addRequestHeader("Host","touch.10086.cn");
		
		String result = null;
		
		try {
			
			HttpClient httpClient = new HttpClient();
			
			int status = httpClient.executeMethod(getMethod);
			
			result=getMethod.getResponseBodyAsString();
			
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
	
	/**
	 * 获取归属地
	 * **/
	public static String sendGetArea(String url) {
		ProtocolSocketFactory fcty = new MySecureProtocolSocketFactory();  
		Protocol.registerProtocol("https", new Protocol("https", fcty, 443));
		GetMethod getMethod = new GetMethod(url);
		String charset = "utf-8";
		getMethod.getParams().setHttpElementCharset(charset);
		getMethod.getParams().setContentCharset(charset);
		getMethod.getParams().setCredentialCharset(charset);
		
		getMethod.addRequestHeader("Referer","https://touch.10086.cn/i/mobile/rechargecredit.html?welcome=1584154041087");
	
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

	/**
	 * 原生get
	 * */
	public static String sendGet(String url) {
		ProtocolSocketFactory fcty = new MySecureProtocolSocketFactory();  
		Protocol.registerProtocol("https", new Protocol("https", fcty, 443));
		GetMethod getMethod = new GetMethod(url);
		String charset = "utf-8";
		getMethod.getParams().setHttpElementCharset(charset);
		getMethod.getParams().setContentCharset(charset);
		getMethod.getParams().setCredentialCharset(charset);
		
		getMethod.addRequestHeader("User-Agent", "Mozilla/5.0 (Linux; Android 8.0; Pixel 2 Build/OPD3.170816.012) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/78.0.3904.108 Mobile Safari/537.36");
		
		String result = null;
		try {
			HttpClient httpClient = new HttpClient();
			int status = httpClient.executeMethod(getMethod);
			if (status != 200) {
				return null;
			}
			result = getMethod.getResponseBodyAsString();
			
			Header[] h=getMethod.getResponseHeaders();
			
			String flag= h[3].toString();
			
			System.out.println(flag);
			
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
	
	
	/**
	 * get 请求，获取微信深度链接
	 * */
	public static String sendGetDeepLink(String url) {
		ProtocolSocketFactory fcty = new MySecureProtocolSocketFactory();  
		Protocol.registerProtocol("https", new Protocol("https", fcty, 443));
		GetMethod getMethod = new GetMethod(url);
		String charset = "utf-8";
		getMethod.getParams().setHttpElementCharset(charset);
		getMethod.getParams().setContentCharset(charset);
		getMethod.getParams().setCredentialCharset(charset);
	
		getMethod.addRequestHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/45.0.2454.101 Safari/537.36 QQBrowser/9.2.5584.400");
		getMethod.addRequestHeader("Referer", "https://pay.shop.10086.cn/paygw/mobileAndBankPayH5");
		
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
	
	/**
	 * 回调第一次get请求
	 * */
	public static String sendGetNotify1(String url) {
		ProtocolSocketFactory fcty = new MySecureProtocolSocketFactory();  
		Protocol.registerProtocol("https", new Protocol("https", fcty, 443));
		GetMethod getMethod = new GetMethod(url);
		String charset = "utf-8";
		getMethod.getParams().setHttpElementCharset(charset);
		getMethod.getParams().setContentCharset(charset);
		getMethod.getParams().setCredentialCharset(charset);
		//禁止重定向
		getMethod.setFollowRedirects(false);

		
		String result = null;
		try {
			HttpClient httpClient = new HttpClient();
			int status = httpClient.executeMethod(getMethod);
			if ( status != 302 ) {
				return null;
			}
	
			result = getMethod.getResponseHeader("Location").toString();
		
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
	
	/**
	 * 发送验证码前戏
	 * **/
	public static String sendGetFlag(String url) {
		ProtocolSocketFactory fcty = new MySecureProtocolSocketFactory();  
		Protocol.registerProtocol("https", new Protocol("https", fcty, 443));
		GetMethod getMethod = new GetMethod(url);
		String charset = "utf-8";
		getMethod.getParams().setHttpElementCharset(charset);
		getMethod.getParams().setContentCharset(charset);
		getMethod.getParams().setCredentialCharset(charset);
		
		getMethod.addRequestHeader("User-Agent", "Mozilla/5.0 (Linux; Android 8.0; Pixel 2 Build/OPD3.170816.012) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/78.0.3904.108 Mobile Safari/537.36");
		
		String result = null;
		try {
			HttpClient httpClient = new HttpClient();
			int status = httpClient.executeMethod(getMethod);
			if (status != 200) {
				return null;
			}
			result = getMethod.getResponseBodyAsString();
			
			Header[] h=getMethod.getResponseHeaders();
			
			String flag= h[3].toString();
			
			result=flag;
			
			System.out.println(flag);
			
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
	
	/**
	 * 获取微信h5支付链接post
	 * 支付宝也一样用这个方法
	 * **/
	public static String sendPostPayUrl(String url, Map<String, String> params,String cookie) {
		ProtocolSocketFactory fcty = new MySecureProtocolSocketFactory();  
		Protocol.registerProtocol("https", new Protocol("https", fcty, 443));
		PostMethod postMethod = new PostMethod(url);
		String charset = "utf-8";
		postMethod.getParams().setHttpElementCharset(charset);
		postMethod.getParams().setContentCharset(charset);
		postMethod.getParams().setCredentialCharset(charset);
		
		postMethod.addParameter("Content-Type", "application/x-www-form-urlencoded");
		postMethod.addParameter("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/78.0.3904.108 Safari/537.36");
		postMethod.addParameter("Referer", "https://pay.shop.10086.cn/paygw/mobileAndBankPayH5");
		postMethod.addParameter("Cookie", cookie);
		
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
	
	
	
	/**
	 *
	 * **/
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
	
	
	
	/**
	 * loadToken.action
	 * **/
	
	public static String sendPostLoadToken(String url, String userName) {
		
	
		ProtocolSocketFactory fcty = new MySecureProtocolSocketFactory();  
		Protocol.registerProtocol("https", new Protocol("https", fcty, 443));
		PostMethod postMethod = new PostMethod(url);
		String charset = "utf-8";
		postMethod.getParams().setHttpElementCharset(charset);
		postMethod.getParams().setContentCharset(charset);
		postMethod.getParams().setCredentialCharset(charset);
		
		postMethod.addRequestHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
		postMethod.addRequestHeader("Referer", "https://login.10086.cn/html/login/touch.html");
		postMethod.addRequestHeader("X-Requested-With", "XMLHttpRequest");
		postMethod.addRequestHeader("User-Agent", "Mozilla/5.0 (iPhone; CPU iPhone OS 11_0 like Mac OS X) AppleWebKit/604.1.38 (KHTML, like Gecko) Version/11.0 Mobile/15A372 Safari/604.1");

		postMethod.addParameter("userName", userName);
		
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
	
	
	
	
	/**
	 * 发送验证码
	 * **/
	
	public static String sendPostCode(String url, Map<String, String> params,String cookie,String xaBefore) {
		ProtocolSocketFactory fcty = new MySecureProtocolSocketFactory();  
		Protocol.registerProtocol("https", new Protocol("https", fcty, 443));
		PostMethod postMethod = new PostMethod(url);
		String charset = "utf-8";
		postMethod.getParams().setHttpElementCharset(charset);
		postMethod.getParams().setContentCharset(charset);
		postMethod.getParams().setCredentialCharset(charset);
		
		postMethod.addRequestHeader("Accept", "application/json, text/javascript, */*; q=0.01");
		postMethod.addRequestHeader("Origin", "https://login.10086.cn");
		postMethod.addRequestHeader("X-Requested-With", "XMLHttpRequest");
		postMethod.addRequestHeader("Xa-before", xaBefore);
		postMethod.addRequestHeader("User-Agent", "Mozilla/5.0 (Linux; Android 8.0; Pixel 2 Build/OPD3.170816.012) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/78.0.3904.108 Mobile Safari/537.36");
		//	postMethod.addRequestHeader("User-Agent", "Mozilla/5.0 (Linux; Android 8.0; Pixel 2 Build/OPD3.170816.012) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/78.0.3904.108 Mobile Safari/537.36");
		postMethod.addRequestHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
		postMethod.addRequestHeader("Sec-Fetch-Site", "same-origin");
		postMethod.addRequestHeader("Sec-Fetch-Mode", "cors");
		postMethod.addRequestHeader("Referer", "https://login.10086.cn/html/login/touch.html");
		//postMethod.addRequestHeader("Referer", "https://login.10086.cn/html/login/touch.html?channelID=12014&backUrl=https%3A%2F%2Ftouch.10086.cn%2Fi%2Fmobile%2Fhome.html%3FWT.ac%3Dbottom_nav_3%26welcome%3D1585538640818");
		postMethod.addRequestHeader("Accept-Encoding", "gzip, deflate");
		postMethod.addRequestHeader("Accept-Language", "zh-CN,zh;q=0.9");
		postMethod.addRequestHeader("Cookie", cookie);
		
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
	
	
	/**
	 * 
	 * 支付
	 * @param  
	 * 
	 * **/
	
	public static String sendPostPay(String url, Map<String, String> params, String cookie) {
		
		ProtocolSocketFactory fcty = new MySecureProtocolSocketFactory();  
		Protocol.registerProtocol("https", new Protocol("https", fcty, 443));
		PostMethod postMethod = new PostMethod(url);
		String charset = "utf-8";
		postMethod.getParams().setHttpElementCharset(charset);
		postMethod.getParams().setContentCharset(charset);
		postMethod.getParams().setCredentialCharset(charset);
		
		postMethod.addRequestHeader("Accept", "application/json, text/javascript, */*; q=0.01");
		postMethod.addRequestHeader("Origin", "https://login.10086.cn");
		postMethod.addRequestHeader("X-Requested-With", "XMLHttpRequest");
		postMethod.addRequestHeader("Xa-before", "52785864465760005734358287952974");
		postMethod.addRequestHeader("User-Agent", "Mozilla/5.0 (Linux; Android 8.0; Pixel 2 Build/OPD3.170816.012) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/78.0.3904.108 Mobile Safari/537.36");
		postMethod.addRequestHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
		postMethod.addRequestHeader("Sec-Fetch-Site", "same-origin");
		postMethod.addRequestHeader("Sec-Fetch-Mode", "cors");
		postMethod.addRequestHeader("Referer", "https://login.10086.cn/html/login/touch.html");
		postMethod.addRequestHeader("Accept-Encoding", "gzip, deflate");
		postMethod.addRequestHeader("Accept-Language", "zh-CN,zh;q=0.9");
		postMethod.addRequestHeader("Cookie", cookie);
		
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
	
	/**
	 * 获取优惠规则
	 * **/
	
	public static String sendPostRule(String url, Map<String, String> params) {
		ProtocolSocketFactory fcty = new MySecureProtocolSocketFactory();  
		Protocol.registerProtocol("https", new Protocol("https", fcty, 443));
		PostMethod postMethod = new PostMethod(url);
		String charset = "utf-8";
		postMethod.getParams().setHttpElementCharset(charset);
		postMethod.getParams().setContentCharset(charset);
		postMethod.getParams().setCredentialCharset(charset);
		
		postMethod.addRequestHeader("Content-Type","application/json; charset=UTF-8");
	
		//postMethod.add
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
	
	/**
	 * 
	 * payload
	 * **/
	public String sendCPICPostRequest(String requestUrl, String cookStr, String licenceNo, String referer, String host) {

	    String payload = "{}";
	  


	    StringBuffer jsonString;
	    try {
	        URL url = new URL(requestUrl);
	        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
	        connection.setDoInput(true);
	        connection.setDoOutput(true);
	        connection.setRequestMethod("POST");
	        connection.setRequestProperty("Accept", "application/json");
	        connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");


	        OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream(), "UTF-8");
	        writer.write(payload);
	        writer.close();
	        BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
	        jsonString = new StringBuffer();
	        String line;
	        while ((line = br.readLine()) != null) {
	            jsonString.append(line);
	        }
	        br.close();
	        connection.disconnect();
	    } catch (Exception e) {
	        throw new RuntimeException(e.getMessage());
	    }
	    return jsonString.toString();
	}
	
	/**
	 * payload请求
	 * 下单支付都要用到
	 * **/
	public  static String postJsonFile1(String url, Map params, Map<String, String> headers) {

        JSONObject jsonObject = JSONObject.fromObject(params);
        URL uUrl = null;
        HttpURLConnection conn = null;
        BufferedWriter out = null;
        BufferedReader in = null;
        try {
            //创建和初始化连接
            uUrl = new URL(url);
        
            conn = (HttpURLConnection) uUrl.openConnection();
            conn.setRequestProperty("content-type", "application/json");
            
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setDoInput(true);
        
            //指定请求header参数
            if (headers != null && headers.size() > 0) {
                Set<String> headerSet = headers.keySet();
                for (String key : headerSet) {
                    conn.setRequestProperty(key, headers.get(key));
                }
            }
            
            //conn.get

            out = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream(), "utf-8"));
            out.write(jsonObject.toString());
            out.flush();

            //接收返回结果
            StringBuilder result = new StringBuilder();
            in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
            if (in != null) {
                String line = "";
                while ((line = in.readLine()) != null) {
                    result.append(line);
                }
            }
            
            System.out.println(result);
            return result.toString();

        } catch (Exception e) {
     
            try {
                byte[] buf = new byte[100];
                InputStream es = conn.getErrorStream();
                if (es != null) {
                    while (es.read(buf) > 0) {
                        ;
                    }
                    es.close();
                }
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            //关闭连接
            if (conn != null) {
                conn.disconnect();
            }
        }

        return null;
    }
	
	
	
	/**
	 * payload请求
	 * 下单支付都要用到
	 * **/
	public  static String postJsonProxy(String url, Map params, Map<String, String> headers,String ip,int port) {
		
		JSONObject jsonObject = JSONObject.fromObject(params);
		URL uUrl = null;
		HttpURLConnection conn = null;
		BufferedWriter out = null;
		BufferedReader in = null;
		try {
			//创建和初始化连接
			uUrl = new URL(url);
			if(ip!=null && port !=0) {
				InetSocketAddress addr = new InetSocketAddress(ip,port);  
			    Proxy proxy = new Proxy(Proxy.Type.HTTP, addr); 
				conn = (HttpURLConnection) uUrl.openConnection(proxy);
			  }
			else {
				conn = (HttpURLConnection) uUrl.openConnection();
			}
			conn.setRequestProperty("content-type", "application/json");
			
			conn.setRequestMethod("POST");
			conn.setDoOutput(true);
			conn.setDoInput(true);
			
			//指定请求header参数
			if (headers != null && headers.size() > 0) {
				Set<String> headerSet = headers.keySet();
				for (String key : headerSet) {
					conn.setRequestProperty(key, headers.get(key));
				}
			}
			
			//conn.get
			
			out = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream(), "utf-8"));
			out.write(jsonObject.toString());
			out.flush();
			
			//接收返回结果
			StringBuilder result = new StringBuilder();
			in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
			if (in != null) {
				String line = "";
				while ((line = in.readLine()) != null) {
					result.append(line);
				}
			}
			
			System.out.println(result);
			return result.toString();
			
		} catch (Exception e) {
			
			try {
				byte[] buf = new byte[100];
				InputStream es = conn.getErrorStream();
				if (es != null) {
					while (es.read(buf) > 0) {
						;
					}
					es.close();
				}
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		} finally {
			try {
				if (out != null) {
					out.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				if (in != null) {
					in.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			//关闭连接
			if (conn != null) {
				conn.disconnect();
			}
		}
		
		return null;
	}
	
	
	
}
