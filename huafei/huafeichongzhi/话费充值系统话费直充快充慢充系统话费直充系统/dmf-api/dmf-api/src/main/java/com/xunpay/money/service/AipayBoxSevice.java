package com.xunpay.money.service;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import net.sf.json.JSONObject;

import com.xunpay.money.model.CompanyOrder;
import com.xunpay.money.utils.EncryptUtils;
import com.xunpay.money.utils.HttpClientHelper;
import com.xunpay.money.utils.RandomUtils;

public class AipayBoxSevice {

	public String createOrder(CompanyOrder order) {
		String url = "https://openapi.aipaybox.com/pay/submit";
		Map<String, String> params = new HashMap<String, String>();
		params.put("version", "1.0.1");
		params.put("pay_method", "alipay_qr_code_cny");
		params.put("pay_channel", "100005");
		params.put("mch_id", "10000059");
		params.put("out_trade_no", order.getOrderNo());
		params.put("user_out_fee", order.getOrderMoney().toString());
		params.put("user_out_type", "CC");
		params.put("mch_in_type", "CC");
		params.put("detail", "充值");
		params.put("attach", "TEST");
		params.put("notify_url", "http://39.98.89.111/notice/aipaybox");
		params.put("nonce_str", RandomUtils.randomString(22));
		params.put("spbill_create_ip", "39.98.89.111");
		params.put("sign_type", "MD5");
		params.put("sign", getSign(params, "006f11fa028233bef79af740181cb42d"));
		System.out.println("签名以后：" + params.get("sign"));
		String result = HttpClientHelper.sendPost(url, params);
		JSONObject json = JSONObject.fromObject(result);
		if (json.getBoolean("success")) {
			return json.getJSONObject("payload").getString("code_page");
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
