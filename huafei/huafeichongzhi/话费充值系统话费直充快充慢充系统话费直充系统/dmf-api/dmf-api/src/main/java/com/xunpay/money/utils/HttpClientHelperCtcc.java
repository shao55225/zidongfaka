package com.xunpay.money.utils;

import com.alibaba.fastjson.JSON;
import com.payment.util.Md5Utils;
import net.sf.json.JSONObject;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.httpclient.*;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpConnectionManagerParams;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.httpclient.protocol.Protocol;
import org.apache.commons.httpclient.protocol.ProtocolSocketFactory;
import org.apache.log4j.Logger;
import sun.misc.BASE64Encoder;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * Http访问操作类
 * 
 * @author Administrator
 * 
 */
public class HttpClientHelperCtcc {

	private static final Logger logger = Logger.getLogger(HttpClientHelperCtcc.class);

	/*
	 * 请求超时毫秒数
	 */
	private static final int timeOut = 50000;

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
	public  synchronized  static String  sendGet(String url) {
		//ProtocolSocketFactory fcty = new MySecureProtocolSocketFactory();
		//Protocol.registerProtocol("https", new Protocol("https", fcty, 443));
		logger.info("访问url[get]:" + url);
		String result = null;
		String resultAttr[] = new String[2];
		GetMethod getMethod = new GetMethod(url);
		// 默认恢复策略
		getMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,
				new DefaultHttpMethodRetryHandler());
		// 设置请求编码
		getMethod.setFollowRedirects(false);
		getMethod.getParams().setHttpElementCharset(charset);
		getMethod.getParams().setContentCharset(charset);
		getMethod.getParams().setCredentialCharset(charset);
		getMethod.getParams().setParameter("Accept-Encoding", "gzip");

		getMethod.addRequestHeader("User-Agent", "Mozilla/5.0 (iPhone; CPU iPhone OS 8_0 like Mac OS X) AppleWebKit/600.1.4 (KHTML, like Gecko) Mobile/12A365 MicroMessenger/5.4.1 NetType/WIF");
		try {
			HttpClient httpClient = new HttpClient();
			
			httpClient.setHttpConnectionManager(httpConnectionManager);
			int status = httpClient.executeMethod(getMethod);
			if (status != 302) {
				logger.error("http请求失败:" + status);
				return null;
			}
			// result = new String(getMethod.getResponseBodyAsString().getBytes(charset));
			
			//result = getMethod.getResponseHeader("Set-Cookie").toString();
			
			result = getMethod.getResponseHeader("Location").toString();
			resultAttr[0]=getMethod.getResponseHeader("Set-Cookie").toString();
			resultAttr[1]=getMethod.getResponseHeader("Location").toString();
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
	public  synchronized  static String  sendGetQuery(String url ) {

		logger.info("访问url[get]:" + url);
		String result = null;
		GetMethod getMethod = new GetMethod(url);
		// 默认恢复策略
		getMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,
				new DefaultHttpMethodRetryHandler());
		// 设置请求编码
		getMethod.setFollowRedirects(false);
		getMethod.getParams().setHttpElementCharset(charset);
		getMethod.getParams().setContentCharset(charset);
		getMethod.getParams().setCredentialCharset(charset);
		getMethod.getParams().setParameter("Accept-Encoding", "gzip");
		getMethod.addRequestHeader("User-Agent", "Mozilla/5.0 (iPhone; CPU iPhone OS 8_0 like Mac OS X) AppleWebKit/600.1.4 (KHTML, like Gecko) Mobile/12A365 MicroMessenger/5.4.1 NetType/WIF");
		
		try {
			HttpClient httpClient = new HttpClient();
			httpClient.setHttpConnectionManager(httpConnectionManager);
			int status = httpClient.executeMethod(getMethod);
			if (status != 200) {
				logger.error("http请求失败:" + status);
				return null;
			}

			result=getMethod.getResponseBodyAsString();
			
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
	public  synchronized  static String  sendGetCookie(String url) {
		//ProtocolSocketFactory fcty = new MySecureProtocolSocketFactory();
		//Protocol.registerProtocol("https", new Protocol("https", fcty, 443));
		logger.info("访问url[get]:" + url);
		String result = null;
		GetMethod getMethod = new GetMethod(url);
		// 默认恢复策略
		getMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,
				new DefaultHttpMethodRetryHandler());
		// 设置请求编码
		getMethod.setFollowRedirects(false);
		getMethod.getParams().setHttpElementCharset(charset);
		getMethod.getParams().setContentCharset(charset);
		getMethod.getParams().setCredentialCharset(charset);
		getMethod.getParams().setParameter("Accept-Encoding", "gzip");
		
		getMethod.addRequestHeader("User-Agent", "Mozilla/5.0 (iPhone; CPU iPhone OS 8_0 like Mac OS X) AppleWebKit/600.1.4 (KHTML, like Gecko) Mobile/12A365 MicroMessenger/5.4.1 NetType/WIF");
		try {
			HttpClient httpClient = new HttpClient();
			httpClient.setHttpConnectionManager(httpConnectionManager);
			int status = httpClient.executeMethod(getMethod);
			if (status != 302) {
				logger.error("http请求失败:" + status);
				return null;
			}
			result=getMethod.getResponseHeader("Set-Cookie").toString().replace("Set-Cookie:","").trim();//.toString();
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
	public  synchronized  static String  sendGet3(String url) {
		//ProtocolSocketFactory fcty = new MySecureProtocolSocketFactory();
		//Protocol.registerProtocol("https", new Protocol("https", fcty, 443));
		logger.info("访问url[get]:" + url);
		String result = null;
		GetMethod getMethod = new GetMethod(url);
		// 默认恢复策略
		getMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,
				new DefaultHttpMethodRetryHandler());
		// 设置请求编码
		getMethod.setFollowRedirects(false);
		getMethod.getParams().setHttpElementCharset(charset);
		getMethod.getParams().setContentCharset(charset);
		getMethod.getParams().setCredentialCharset(charset);
		getMethod.getParams().setParameter("Accept-Encoding", "gzip");
		
		getMethod.addRequestHeader("User-Agent", "Mozilla/5.0 (iPhone; CPU iPhone OS 8_0 like Mac OS X) AppleWebKit/600.1.4 (KHTML, like Gecko) Mobile/12A365 MicroMessenger/5.4.1 NetType/WIF");
		try {
			HttpClient httpClient = new HttpClient();
			
			httpClient.setHttpConnectionManager(httpConnectionManager);
			int status = httpClient.executeMethod(getMethod);
			if (status != 301) {
				logger.error("http请求失败:" + status);
				return null;
			}
			
			result = getMethod.getResponseHeader("Location").toString().replace("Location:","").trim();
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
	public  synchronized  static int  sendGetCode(String url) {
		//ProtocolSocketFactory fcty = new MySecureProtocolSocketFactory();
		//Protocol.registerProtocol("https", new Protocol("https", fcty, 443));
		logger.info("访问url[get]:" + url);
		String result = null;
		GetMethod getMethod = new GetMethod(url);
		// 默认恢复策略
		getMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,
				new DefaultHttpMethodRetryHandler());
		// 设置请求编码
		getMethod.setFollowRedirects(false);
		getMethod.getParams().setHttpElementCharset(charset);
		getMethod.getParams().setContentCharset(charset);
		getMethod.getParams().setCredentialCharset(charset);
		getMethod.getParams().setParameter("Accept-Encoding", "gzip");
		
		getMethod.addRequestHeader("User-Agent", "Mozilla/5.0 (iPhone; CPU iPhone OS 8_0 like Mac OS X) AppleWebKit/600.1.4 (KHTML, like Gecko) Mobile/12A365 MicroMessenger/5.4.1 NetType/WIF");
		int status =0;
		try {
			HttpClient httpClient = new HttpClient();
			
			httpClient.setHttpConnectionManager(httpConnectionManager);
			 status = httpClient.executeMethod(getMethod);
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
		
		return status;
	}

	/**
	 * 发送Get请求
	 *
	 * @param url
	 *            url地址
	 * @return
	 */
	public synchronized static byte [] sendGetVerCode(String url,String cookie) {
		ProtocolSocketFactory fcty = new MySecureProtocolSocketFactory();
		Protocol.registerProtocol("https", new Protocol("https", fcty, 443));
		logger.info("访问url[get]:" + url);
		byte [] result = null;
		GetMethod getMethod = new GetMethod(url);
		// 默认恢复策略
		getMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,
				new DefaultHttpMethodRetryHandler());
		// 设置请求编码
		getMethod.getParams().setHttpElementCharset(charset);
		getMethod.getParams().setContentCharset(charset);
		getMethod.getParams().setCredentialCharset(charset);
		//getMethod.getParams().setParameter("Accept-Encoding", "gzip");
		getMethod.addRequestHeader("Content-type", "image/jpeg");
		getMethod.addRequestHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9");
		getMethod.addRequestHeader("Accept-Encoding", "gzip, deflate");
		getMethod.addRequestHeader("Cookie", cookie);

		try {
			HttpClient httpClient = new HttpClient();
			httpClient.setHttpConnectionManager(httpConnectionManager);
			int status = httpClient.executeMethod(getMethod);
			if (status != HttpStatus.SC_OK) {
				logger.error("http请求失败:" + status);
				return null;
			}
			// result = new String(getMethod.getResponseBodyAsString().getBytes(charset));
			result = getMethod.getResponseBody();
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
    public  static String sendPostCode(String url, Map<String, String> params,String cookie) {
    	
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
        postMethod.setFollowRedirects(false);
        postMethod.addRequestHeader("Content-type", "application/x-www-form-urlencoded; charset=UTF-8");
        postMethod.addRequestHeader("Cookie", cookie);
        postMethod.addRequestHeader("Referer", ShuaPiao.Referer);
        postMethod.addRequestHeader("User-Agent", ShuaPiao.UA);
        postMethod.addRequestHeader("Content-Type", "application/json, text/javascript, */*; q=0.01");
        
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
             result = new String(postMethod.getResponseBodyAsString().getBytes(charset));
            //result =postMethod.getResponseHeader("Location").toString().replace("Location:","").trim();
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
     * 发送Get请求
     *
     * @param url
     *            url地址
     * @return
     */
    public  static String wxsendGet(String url,String session) {
    	//session="e2219a8d3f8e457196968f6d7177776d";
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
      //  getMethod.getParams().setParameter("Accept-Encoding", "gzip");
        //getMethod.getParams().se("Referer","https://wappay.189.cn/pay/toPay.do?bankCode=WX001&sessionid="+session+"&state=");
		getMethod.addRequestHeader("Referer","https://wappay.189.cn/pay/toPay.do?bankCode=WX001&sessionid="+session+"&state=");
		//getMethod.addRequestHeader("Accept-Encoding", "gzip");
		getMethod.addRequestHeader("Content-Type","text/html; charset=utf-8");
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
	public  static String onlinePay(String url) {
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
		getMethod.setFollowRedirects(false);
		getMethod.addRequestHeader("Content-type", "application/x-www-form-urlencoded; charset=UTF-8");
		try {
			HttpClient httpClient = new HttpClient();
			httpClient.setHttpConnectionManager(httpConnectionManager);
			int status = httpClient.executeMethod(getMethod);
			/*if (status != HttpStatus.SC_OK) {
				logger.error("http请求失败:" + status);
				return null;
			}*/
			// result = new String(getMethod.getResponseBodyAsString().getBytes(charset));
			result = getMethod.getResponseHeader("Location").toString().replace("Location:","").trim();
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
	public  static String sendPost(String url, Map<String, String> params) {
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
        postMethod.setFollowRedirects(false);
		postMethod.addRequestHeader("Content-type", "application/x-www-form-urlencoded; charset=UTF-8");
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
			// result = new String(postMethod.getResponseBodyAsString().getBytes(charset));
            result = postMethod.getResponseHeader("Location").toString().replace("Location:","").trim();
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
	 * 发送Post请求
	 *
	 * @param url
	 *            url地址
	 * @param params
	 *            请求参数
	 * @return
	 */
	public  static String sendPostLy(String url, Map<String, String> params) {
		logger.info("访问url[post]:" + url);
		//logger.debug("post参数:" + JSON.toJSONString(params));
		String result = null;
		PostMethod postMethod = new PostMethod(url);
		// 默认恢复策略
		postMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler());
		// 设置请求编码
		postMethod.getParams().setHttpElementCharset(charset);
		postMethod.getParams().setContentCharset(charset);
		postMethod.getParams().setCredentialCharset(charset);
		postMethod.setFollowRedirects(false);
		postMethod.addRequestHeader("Content-type", "application/image/jpeg");
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
			// result = new String(postMethod.getResponseBodyAsString().getBytes(charset));
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
     *
     * **/
    public static String sendPostService(String url, Map<String, String> params) {
        ProtocolSocketFactory fcty = new MySecureProtocolSocketFactory();
        Protocol.registerProtocol("https", new Protocol("https", fcty, 443));
        PostMethod postMethod = new PostMethod(url);
        String charset = "utf-8";
        postMethod.getParams().setHttpElementCharset(charset);
        postMethod.getParams().setContentCharset(charset);
        postMethod.getParams().setCredentialCharset(charset);
        postMethod.setFollowRedirects(false);
        postMethod.addRequestHeader("Content-type", "application/x-www-form-urlencoded");

        postMethod.addParameter("request_params", params.get("request_params"));

        String result = null;
        try {
            HttpClient httpClient = new HttpClient();
            int status = httpClient.executeMethod(postMethod);
            result = postMethod.getResponseHeader("Location").toString().replace("Location:","").trim();
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
     * 发送Post请求
     *
     * @param url
     *            url地址
     * @param params
     *            请求参数
     * @return
     */
    public  static String wxpayPost(String url, Map<String, String> params) {
    	
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
        postMethod.setFollowRedirects(false);
        postMethod.addRequestHeader("Content-type", "application/x-www-form-urlencoded; charset=UTF-8");

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
            // result = new String(postMethod.getResponseBodyAsString().getBytes(charset));
            result =postMethod.getResponseHeader("Location").toString().replace("Location:","").trim();
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
			conn.setRequestProperty("content-type", "application/x-www-form-urlencoded; charset=UTF-8");
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


	/**
	 * payload请求
	 * 下单支付都要用到
	 * **/
	public  static String wxPostService(String url, Map params) {

		JSONObject jsonObject = JSONObject.fromObject(params);
		URL uUrl = null;
		HttpURLConnection conn = null;
		BufferedWriter out = null;
		BufferedReader in = null;
		try {
			//创建和初始化连接
			uUrl = new URL(url);
			conn = (HttpURLConnection) uUrl.openConnection();
			conn.setRequestProperty("content-type", "application/x-www-form-urlencoded;charset=UTF-8");
			conn.setRequestMethod("POST");
			conn.setDoOutput(true);
			conn.setDoInput(true);

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
	 * 参数信息
	 * @return
	 */
	private static Map<String, String> getParamMap() {
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("user_name", "b521123123");
		paramMap.put("user_pw", "521123321aSDF");
		paramMap.put("yzm_minlen", "4");
		paramMap.put("yzm_maxlen", "4");
		paramMap.put("yzmtype_mark", "1009");
		paramMap.put("zztool_token", "");
		return paramMap;
	}


	public static String getCode(byte [] imgbytes){
		String BOUNDARY = "---------------------------68163001211748"; //boundary就是request头和上传文件内容的分隔符
		String str="http://v1-http-api.jsdama.com/api.php?mod=php&act=upload";
		Map<String, String> paramMap = getParamMap();
		ByteArrayOutputStream bout=null;
		try {
			URL url=new URL(str);
			HttpURLConnection connection=(HttpURLConnection)url.openConnection();
			connection.setDoInput(true);
			connection.setDoOutput(true);
			connection.setRequestMethod("POST");
			connection.setRequestProperty("content-type", "multipart/form-data; boundary="+BOUNDARY);
			connection.setConnectTimeout(30000);
			connection.setReadTimeout(30000);

			OutputStream out = new DataOutputStream(connection.getOutputStream());
			// 普通参数
			if (paramMap != null) {
				StringBuffer strBuf = new StringBuffer();
				Iterator<Map.Entry<String, String>> iter = paramMap.entrySet().iterator();
				while (iter.hasNext()) {
					Map.Entry<String,String> entry = iter.next();
					String inputName = entry.getKey();
					String inputValue = entry.getValue();
					strBuf.append("\r\n").append("--").append(BOUNDARY).append("\r\n");
					strBuf.append("Content-Disposition: form-data; name=\""
							+ inputName + "\"\r\n\r\n");
					strBuf.append(inputValue);
				}
				out.write(strBuf.toString().getBytes());
			}

			// 图片文件
			if (imgbytes != null) {
				String contentType = "image/jpeg";//这里看情况设置
				StringBuffer strBuf = new StringBuffer();
				strBuf.append("\r\n").append("--").append(BOUNDARY).append("\r\n");
				strBuf.append("Content-Disposition: form-data; name=\""
						+ "upload" + "\"; filename=\"" + "yzm"+ "\"\r\n");
				strBuf.append("Content-Type:" + contentType + "\r\n\r\n");
				out.write(strBuf.toString().getBytes());
				out.write(imgbytes);

			}
			byte[] endData = ("\r\n--" + BOUNDARY + "--\r\n").getBytes();
			out.write(endData);
			out.flush();
			out.close();

			//读取URLConnection的响应
			InputStream in = connection.getInputStream();
			 bout = new ByteArrayOutputStream();
			byte[] buf = new byte[1024];
			while (true) {
				int rc = in.read(buf);
				if (rc <= 0) {
					break;
				} else {
					bout.write(buf, 0, rc);
				}
			}
			in.close();
			//结果输出
			System.out.println(new String(bout.toByteArray()));
		} catch (Exception e) {
			e.printStackTrace();

		}

		return new String(bout.toByteArray());
	}


	public static void main(String [] art){
		/*String s="尊敬的中国移动用户，您好！欢迎使用中国移动网上商城，您的短信随机码是：246131。中国移动不会以任何方式向您索取该密码，请勿告知他人。";
		String a=s.substring(s.indexOf("：")+1,s.indexOf("：")+7);
		System.out.println(a);*/
		String a=Md5Utils.getMd5("123321");
		System.out.println(a);
	}

}
