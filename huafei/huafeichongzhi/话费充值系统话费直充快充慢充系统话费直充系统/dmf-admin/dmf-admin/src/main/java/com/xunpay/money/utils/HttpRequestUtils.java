package com.xunpay.money.utils;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

public class HttpRequestUtils {

	public static String getIpAddress(HttpServletRequest request) {
		String ip = request.getHeader("NGINX-IP");
		if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}
	
	public static String getLocationByIp(String ip) {
		String result = HttpClientHelper.sendGet("http://ip.taobao.com/service/getIpInfo.php?ip=" + ip);
		JSONObject json = JSON.parseObject(result);
		if ("0".equals(json.getString("code"))) {
			JSONObject data = json.getJSONObject("data");
			StringBuilder ret = new StringBuilder();
			if (StringUtils.isNotEmpty(data.getString("country"))) {
				ret.append(data.getString("country"));
			}
			if (StringUtils.isNotEmpty(data.getString("region"))) {
				ret.append("-").append(data.getString("region"));
			}
			if (StringUtils.isNotEmpty(data.getString("city"))) {
				ret.append("-").append(data.getString("city"));
			}
			if (StringUtils.isNotEmpty(data.getString("isp"))) {
				ret.append("(").append(data.getString("isp")).append(")");
			}
			return ret.toString();
		}
		return StringUtils.EMPTY;
	}
	
}
