package com.xunpay.money.utils;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpConnectionManager;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.SimpleHttpConnectionManager;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpConnectionManagerParams;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSON;

/**
 * Http访问操作类
 * 
 * @author Administrator
 * 
 */
public class CmccHttp {

	private static final Logger logger = Logger.getLogger(CmccHttp.class);

	/*
	 * 请求超时毫秒数
	 */
	private static final int timeOut = 40000;

	/*
	 * 编码
	 */
	private static final String charset = "UTF-8";

	private static final HttpConnectionManager httpConnectionManager;	
	static {
		// 设置请求超时毫秒数
		HttpConnectionManagerParams httpConnectionManagerParams = new HttpConnectionManagerParams();
		httpConnectionManagerParams.setConnectionTimeout(timeOut);
		httpConnectionManagerParams.setSoTimeout(timeOut);
		httpConnectionManager = new SimpleHttpConnectionManager();
		httpConnectionManager.setParams(httpConnectionManagerParams);
	}

	/**
	 * 发送Get请求
	 * 
	 * @param url
	 *            url地址
	 * @return
	 */
	public synchronized static String sendGet(String url) {
		logger.info("访问url[get]:" + url);
		String result = null;
		GetMethod getMethod = new GetMethod(url);
		// 默认恢复策略
		getMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,
				new DefaultHttpMethodRetryHandler());
		// 设置请求编码
		getMethod.getParams().setHttpElementCharset(charset);
		getMethod.getParams().setContentCharset(charset);
		getMethod.getParams().setCredentialCharset(charset);
		getMethod.getParams().setParameter("Accept-Encoding", "gzip");
		getMethod.getParams().setParameter("Referer", "https://upay.10010.com/jf_wxgz2");
		
		//getMethod.addRequestHeader("Content-type", "application/x-www-form-urlencoded; charset=UTF-8");
		try {
			HttpClient httpClient = new HttpClient();
			httpClient.setHttpConnectionManager(httpConnectionManager);
			int status = httpClient.executeMethod(getMethod);
			if (status != HttpStatus.SC_OK) {
				logger.error("http请求失败:" + status);
				return null;
			}
			// result = new String(getMethod.getResponseBodyAsString().getBytes(charset));
			result = getMethod.getResponseBodyAsString();
		} catch (HttpException e) {
			e.printStackTrace();
			logger.error("http get请求异常:" + e.getMessage());
		} catch (IOException e) {
			e.printStackTrace();
			logger.error("http get IO流异常" + e.getMessage());
		} finally {
			// 释放连接
			getMethod.releaseConnection();
		}
		return result;
	}
	
	/**
	 * 发送Get请求
	 * 
	 * @param url
	 *            url地址
	 * @return
	 */
	public synchronized static String sendGetParam(String url) {
		logger.info("访问url[get]:" + url);
		String result = null;
		GetMethod getMethod = new GetMethod(url);
		// 默认恢复策略
		getMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,
				new DefaultHttpMethodRetryHandler());
		// 设置请求编码
		getMethod.getParams().setHttpElementCharset(charset);
		getMethod.getParams().setContentCharset(charset);
		getMethod.getParams().setCredentialCharset(charset);
		getMethod.getParams().setParameter("Accept-Encoding", "gzip");
		getMethod.getParams().setParameter("Referer", "https://upay.10010.com/jf_wxgz2");
		
		//getMethod.addRequestHeader("Content-type", "application/x-www-form-urlencoded; charset=UTF-8");
		try {
			HttpClient httpClient = new HttpClient();
			httpClient.setHttpConnectionManager(httpConnectionManager);
			int status = httpClient.executeMethod(getMethod);
			if (status != HttpStatus.SC_OK) {
				logger.error("http请求失败:" + status);
				return null;
			}
			// result = new String(getMethod.getResponseBodyAsString().getBytes(charset));
			result = getMethod.getResponseBodyAsString();
		} catch (HttpException e) {
			e.printStackTrace();
			logger.error("http get请求异常:" + e.getMessage());
		} catch (IOException e) {
			e.printStackTrace();
			logger.error("http get IO流异常" + e.getMessage());
		} finally {
			// 释放连接
			getMethod.releaseConnection();
		}
		return result;
	}
	
	/**
	 * 发送Get请求 获取location
	 * 
	 * @param url
	 *            url地址
	 * @return
	 */
	public synchronized static String sendGetLocation(String url,String ip,int port) {
		logger.info("访问url[get]:" + url);
		String result = null;
		GetMethod getMethod = new GetMethod(url);
		// 默认恢复策略
		getMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,
				new DefaultHttpMethodRetryHandler());
		// 设置请求编码
		getMethod.getParams().setHttpElementCharset(charset);
		getMethod.getParams().setContentCharset(charset);
		getMethod.getParams().setCredentialCharset(charset);
		getMethod.getParams().setParameter("Accept-Encoding", "gzip");
		
		//禁止重定向
		getMethod.setFollowRedirects(false);
		
		try {
			HttpClient httpClient = new HttpClient();
			httpClient.setHttpConnectionManager(httpConnectionManager);
			int status = httpClient.executeMethod(getMethod);
			if (status != HttpStatus.SC_MOVED_TEMPORARILY) {
				logger.error("http请求失败:" + status);
				return null;
			}
			result = getMethod.getResponseHeader("Location").toString().replace("Location:", "").trim();
			
		} catch (HttpException e) {
			e.printStackTrace();
			logger.error("http get请求异常:" + e.getMessage());
		} catch (IOException e) {
			e.printStackTrace();
			logger.error("http get IO流异常" + e.getMessage());
		} finally {
			// 释放连接
			getMethod.releaseConnection();
		}
		return result;
	}
	
	

	/**
	 * 发送Post请求
	 * 
	 * @param url
	 *            url地址
	 * @param params
	 *            请求参数
	 * @return
	 */
	public synchronized static String sendPost(String url, Map<String, String> params,String cookie) {
		logger.info("访问url[post]:" + url);
		logger.debug("post参数:" + JSON.toJSONString(params));
		String result = null;
		PostMethod postMethod = new PostMethod(url);
		// 默认恢复策略
		postMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler());
		// 设置请求编码
		postMethod.getParams().setHttpElementCharset(charset);
		postMethod.getParams().setContentCharset(charset);
		postMethod.getParams().setCredentialCharset(charset);
		postMethod.addRequestHeader("Content-Type", "application/x-www-form-urlencoded");
		postMethod.addRequestHeader("Cookie", "UserName="+cookie);
		postMethod.addRequestHeader("Referer", "https://pay.shop.10086.cn/paygw/490356647086065180-1597630302522-c60ecbbcef15a12ee29c2785855510b5-20.html");
		//postMethod.addRequestHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/65.0.3325.181 Safari/537.36");
		
		if (MapUtils.isNotEmpty(params)) {
			Set<String> keys = params.keySet();
			for (String key : keys) {
				postMethod.addParameter(key, params.get(key));
			}
		}
		try {
			HttpClient httpClient = new HttpClient();
			httpClient.setHttpConnectionManager(httpConnectionManager);
			int status = httpClient.executeMethod(postMethod);
			if (status != HttpStatus.SC_OK) {
				logger.error("http请求失败:" + status);
				logger.error("http异常堆栈:" + postMethod.getResponseBodyAsString());
				return null;
			}
			// result = new String(postMethod.getResponseBodyAsString().getBytes(charset));
			result = postMethod.getResponseBodyAsString();
			//result = postMethod.getResponseBodyAsStream().toString();
		} catch (HttpException e) {
			e.printStackTrace();
			logger.error("http post请求异常:" + e.getMessage());
		} catch (IOException e) {
			e.printStackTrace();
			logger.error("http post IO流异常" + e.getMessage());
		} finally {
			// 释放连接
			postMethod.releaseConnection();
		}
		return result;
	}

    /**
     * 发送Get 代理请求
     *
     * @param url
     *            url地址
     * @return
     */
    public synchronized static String sendGetProxy(String url,String ip,int port) {
    	
    	logger.info("访问url[get]:" + url);
    	String result = null;
    	GetMethod getMethod = new GetMethod(url);
    	
    	// 默认恢复策略
    	getMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,
    			new DefaultHttpMethodRetryHandler());
    	// 设置请求编码
    	getMethod.getParams().setHttpElementCharset(charset);
    	getMethod.getParams().setContentCharset(charset);
    	getMethod.getParams().setCredentialCharset(charset);
    	getMethod.getParams().setParameter("Accept-Encoding", "gzip");
    	
    	getMethod.addRequestHeader("Content-type", "application/x-www-form-urlencoded; charset=UTF-8");
    	try {
    		HttpClient httpClient = new HttpClient();
    		httpClient.getHostConfiguration().setProxy(ip, port);
    		httpClient.setHttpConnectionManager(httpConnectionManager);
    		int status = httpClient.executeMethod(getMethod);
    		if (status != HttpStatus.SC_OK) {
    			logger.error("http请求失败:" + status);
    			return null;
    		}
    		result = getMethod.getResponseBodyAsString();
    	} catch (HttpException e) {
    		e.printStackTrace();
    		logger.error("http get请求异常:" + e.getMessage());
    	} catch (IOException e) {
    		e.printStackTrace();
    		logger.error("http get IO流异常" + e.getMessage());
    	} finally {
    		// 释放连接
    		getMethod.releaseConnection();
    	}
    	return result;
    }
    
    
    /**
     * 发送Post 代理请求
     *
     * @param url
     *            url地址
     * @return
     */
    public synchronized static String sendPostProxy(String url, Map<String, String> params,String ip,int port) {
    	
		logger.info("访问url[post]:" + url);
		logger.debug("post参数:" + JSON.toJSONString(params));
		String result = null;
		PostMethod postMethod = new PostMethod(url);
		// 默认恢复策略
		postMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler());
		// 设置请求编码
		postMethod.getParams().setHttpElementCharset(charset);
		postMethod.getParams().setContentCharset(charset);
		postMethod.getParams().setCredentialCharset(charset);
		postMethod.addRequestHeader("Content-Type", "application/x-www-form-urlencoded");
		
		if (MapUtils.isNotEmpty(params)) {
			Set<String> keys = params.keySet();
			for (String key : keys) {
				postMethod.addParameter(key, params.get(key));
			}
		}
		try {
			HttpClient httpClient = new HttpClient();
    		httpClient.getHostConfiguration().setProxy(ip, port);
    		httpClient.setHttpConnectionManager(httpConnectionManager);
    		int status = httpClient.executeMethod(postMethod);	
    		if (status != HttpStatus.SC_OK) {
				logger.error("http请求失败:" + status);
				logger.error("http异常堆栈:" + postMethod.getResponseBodyAsString());
				return null;
			}
			result = postMethod.getResponseBodyAsString();
		} catch (HttpException e) {
			e.printStackTrace();
			logger.error("http post请求异常:" + e.getMessage());
		} catch (IOException e) {
			e.printStackTrace();
			logger.error("http post IO流异常" + e.getMessage());
		} finally {
			// 释放连接
			postMethod.releaseConnection();
		}
		return result;
    }
    
   
    /**
     * 发送Post location
     *
     * @param url
     *            url地址
     * @return
     */
    public synchronized static String sendPostLocation(String url, Map<String, String> params) {
    	
    	logger.info("访问url[post]:" + url);
    	logger.debug("post参数:" + JSON.toJSONString(params));
    	String result = null;
    	PostMethod postMethod = new PostMethod(url);
    	// 默认恢复策略
    	postMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler());
    	// 设置请求编码
    	postMethod.getParams().setHttpElementCharset(charset);
    	postMethod.getParams().setContentCharset(charset);
    	postMethod.getParams().setCredentialCharset(charset);
    	postMethod.addRequestHeader("Content-Type", "application/x-www-form-urlencoded");
    	
    	if (MapUtils.isNotEmpty(params)) {
    		Set<String> keys = params.keySet();
    		for (String key : keys) {
    			postMethod.addParameter(key, params.get(key));
    		}
    	}
    	try {
    		HttpClient httpClient = new HttpClient();
    		//httpClient.getHostConfiguration().setProxy(ip, port);
    		httpClient.setHttpConnectionManager(httpConnectionManager);
    		int status = httpClient.executeMethod(postMethod);	
    		if (status != HttpStatus.SC_MOVED_TEMPORARILY) {
    			logger.error("http请求失败:" + status);
    			logger.error("http异常堆栈:" + postMethod.getResponseBodyAsString());
    			return null;
    		}
    		
    		result = postMethod.getResponseHeader("Location").toString().replace("Location:", "").trim();
    	} catch (HttpException e) {
    		e.printStackTrace();
    		logger.error("http post请求异常:" + e.getMessage());
    	} catch (IOException e) {
    		e.printStackTrace();
    		logger.error("http post IO流异常" + e.getMessage());
    	} finally {
    		// 释放连接
    		postMethod.releaseConnection();
    	}
    	return result;
    }
    
    
    
	
	/**
	 * 发送Get代理请求 获取location
	 * 
	 * @param url
	 *            url地址
	 * @return
	 */
	public synchronized static String sendGetProxyLocation(String url,String cookie,String ip,int port) {
		logger.info("访问url[get]:" + url);
		String result = null;
		GetMethod getMethod = new GetMethod(url);
		// 默认恢复策略
		getMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,
				new DefaultHttpMethodRetryHandler());
		// 设置请求编码
		getMethod.getParams().setHttpElementCharset(charset);
		getMethod.getParams().setContentCharset(charset);
		getMethod.getParams().setCredentialCharset(charset);
		getMethod.getParams().setParameter("Accept-Encoding", "gzip");
		if(cookie!=null) {
			getMethod.setRequestHeader("Cookie","cmccssotoken="+cookie);	
		}
		
		//禁止重定向
		getMethod.setFollowRedirects(false);
		
		try {
			HttpClient httpClient = new HttpClient();
			httpClient.setHttpConnectionManager(httpConnectionManager);
			if(ip!=null && port!=0) {
				httpClient.getHostConfiguration().setProxy(ip, port);
			}
		
			int status = httpClient.executeMethod(getMethod);
			if (status != HttpStatus.SC_MOVED_TEMPORARILY) {
				logger.error("http请求失败:" + status);
				return null;
			}
			result = getMethod.getResponseHeader("Location").toString().replace("Location:", "").trim();
			
		} catch (HttpException e) {
			e.printStackTrace();
			logger.error("http get请求异常:" + e.getMessage());
		} catch (IOException e) {
			e.printStackTrace();
			logger.error("http get IO流异常" + e.getMessage());
		} finally {
			// 释放连接
			getMethod.releaseConnection();
		}
		return result;
	}
    
    
    
	/**
	 * 发送Get代理请求 获取location
	 * 
	 * @param url
	 *            url地址
	 * @return
	 */
	public synchronized static String sendGetProxyCookie(String url,String ip,int port) {
		logger.info("访问url[get]:" + url);
		String result = null;
		GetMethod getMethod = new GetMethod(url);
		// 默认恢复策略
		getMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,
				new DefaultHttpMethodRetryHandler());
		// 设置请求编码
		getMethod.getParams().setHttpElementCharset(charset);
		getMethod.getParams().setContentCharset(charset);
		getMethod.getParams().setCredentialCharset(charset);
		getMethod.getParams().setParameter("Accept-Encoding", "gzip");
	
		
		//禁止重定向
		getMethod.setFollowRedirects(false);
		
		try {
			HttpClient httpClient = new HttpClient();
			httpClient.setHttpConnectionManager(httpConnectionManager);
			if(ip!=null && port!=0) {
				httpClient.getHostConfiguration().setProxy(ip, port);
			}
		
			int status = httpClient.executeMethod(getMethod);
			if (status != HttpStatus.SC_MOVED_TEMPORARILY) {
				logger.error("http请求失败:" + status);
				return null;
			}
			result = getMethod.getResponseHeader("Set-Cookie").toString().replace("set-cookie:", "").trim();
			
		} catch (HttpException e) {
			e.printStackTrace();
			logger.error("http get请求异常:" + e.getMessage());
		} catch (IOException e) {
			e.printStackTrace();
			logger.error("http get IO流异常" + e.getMessage());
		} finally {
			// 释放连接
			getMethod.releaseConnection();
		}
		return result;
	}
    
    
	/**
	 * http请求参数
	 * @author tangshu
	 *
	 */
	public static class HttpParam {

		private String key;
		private String value;
		
		public HttpParam() {
		}
		
		public HttpParam(String key, String value) {
			this.key = key;
			this.value = value;
		}
		

		public String getKey() {
			return key;
		}

		public void setKey(String key) {
			this.key = key;
		}

		public String getValue() {
			return value;
		}

		public void setValue(String value) {
			this.value = value;
		}

	}

}
