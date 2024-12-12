package com.xunpay.money.service;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import net.sf.json.JSONObject;

import com.xunpay.money.model.AlipayItem;
import com.xunpay.money.model.CompanyOrder;
import com.xunpay.money.utils.EncryptUtils;
import com.xunpay.money.utils.HttpClientHelper;

public class Pay91Service {

	public String createOrder(CompanyOrder order) {
		AlipayItem alipay = AlipayItem.dao.findById(order.getAlipayId());
		String url = alipay.getRsaAlipay();
		Map<String, String> params = new HashMap<String, String>();
		params.put("apiCode", "YL-PAY");
		params.put("inputCharset", "UTF-8");
		params.put("signType", "MD5");
		params.put("partner", alipay.getPid());
		params.put("outOrderId", order.getOrderNo());
		params.put("amount", order.getOrderMoney().intValue() + ".00");
		params.put("payType", alipay.getAppid());
		params.put("notifyUrl", "http://47.92.253.105/notice/pay91");
		params.put("sign", getSign(params, alipay.getRsaPrivate()));
		
		String result = HttpClientHelper.sendPost(url, params);
		JSONObject json = JSONObject.fromObject(result);
		if ("0000".equals(json.getString("responseCode"))) {
			return json.getString("qrCodeUrl");
		}
		System.out.println(result);
		return null;
	}
	
	public static String getSign(Map<String, String> map, String md5key) {
		Map<String, String> sortMap = sortMapByKey(map);
		StringBuffer sign = new StringBuffer();
		for (String key : sortMap.keySet()) {
			sign.append(key).append("=").append(sortMap.get(key)).append("&");
		}
		sign.setLength(sign.length() - 1);
		sign.append(md5key);
		System.out.println("签名组成:" + sign);
		return EncryptUtils.encrypt(sign.toString()).toUpperCase();
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
