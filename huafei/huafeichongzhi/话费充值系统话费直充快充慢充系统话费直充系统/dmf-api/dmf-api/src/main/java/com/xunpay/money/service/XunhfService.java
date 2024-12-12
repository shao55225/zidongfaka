package com.xunpay.money.service;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import net.sf.json.JSONObject;

import com.xunpay.money.model.CompanyOrder;
import com.xunpay.money.utils.EncryptUtils;
import com.xunpay.money.utils.HttpClientHelper;

public class XunhfService {

	public String createOrder(CompanyOrder order) {
		String url = "http://103.61.37.196:9088/api/recharge/h5/order.do";
		Map<String, String> params = new HashMap<String, String>();
		params.put("agentAcct", "xunshaozui");
		params.put("agentOrderId", order.getOrderNo());
		params.put("bizType", "E_CHARGE");
		params.put("number", "1");
		params.put("amount", order.getOrderMoney().intValue() * 100 + "");
		params.put("timestamp", "" + System.currentTimeMillis());
		params.put("callback", "http://47.92.253.105/notice/xunfh");
		params.put("sign", getSign(params, "25d55ad283aa400af464c76d713c07ad"));
		String result = HttpClientHelper.sendPost(url, params);
		System.out.println(result);
		JSONObject json = JSONObject.fromObject(result);
		if ("SUCCESS".equals(json.getString("statusCode"))) {
			return json.getString("payUrl");
		}
		return null;
	}
	
	public static String getSign(Map<String, String> map, String md5key) {
		Map<String, String> sortMap = sortMapByKey(map);
		StringBuffer sign = new StringBuffer();
		for (String key : sortMap.keySet()) {
			sign.append(key).append("=").append(sortMap.get(key)).append("&");
			System.out.println(key + " => " + sortMap.get(key));
		}
		sign.append("key=").append(md5key);
		System.out.println("签名组成:" + sign);
		return EncryptUtils.encrypt(sign.toString());
	}
	
	public static Map<String, String> sortMapByKey(Map<String, String> map) {
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
