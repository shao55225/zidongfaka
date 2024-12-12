package com.xunpay.money.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;

public class SignUtils {
	
	public static String sortUrlWithEncode(Map<String, String> map, String md5key) {
		Map<String, String> sortMap = sortMapByKey(map);
		StringBuffer sign = new StringBuffer();
		for (String key : sortMap.keySet()) {
			String keyVal = sortMap.get(key);
			if(key.equals("callback")){
				try {
					keyVal = URLEncoder.encode(keyVal, "utf-8");
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
			}
			sign.append(key).append("=").append(keyVal).append("&");
		}
		sign.append("key=" + md5key);
		System.out.println("签名组成:" + sign);
		return sign.toString();
	}
	
	
	public static String sortUrl(Map<String, String> map, String md5key) {
		Map<String, String> sortMap = sortMapByKey(map);
		StringBuffer sign = new StringBuffer();
		for (String key : sortMap.keySet()) {
			sign.append(key).append("=").append(sortMap.get(key)).append("&");
		}
		sign.append("key=" + md5key);
		System.out.println("签名组成:" + sign);
		return sign.toString();
	}
	
	private static Map<String, String> sortMapByKey(Map<String, String> map) {
		if (map == null || map.isEmpty()) {
			return null;
		}
		Map<String, String> sortMap = new TreeMap<>(new Comparator<String>() {
			@Override
			public int compare(String o1, String o2) {
				return o1.compareTo(o2);
			}
		});
		sortMap.putAll(map);
        return sortMap;
	}

}
